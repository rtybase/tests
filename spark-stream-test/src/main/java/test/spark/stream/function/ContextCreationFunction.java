package test.spark.stream.function;

import static test.spark.stream.config.Constants.BATCH_DURATION_IN_SECONDS;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function0;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaMapWithStateDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import test.spark.stream.core.PriceInformation;
import test.spark.stream.io.PriceInformationReceiver;

public class ContextCreationFunction implements Function0<JavaStreamingContext> {
	private static final long serialVersionUID = -1193765175703052944L;

	private final String csvFilePath;
	private final String checkpointFolderPath;

	public ContextCreationFunction(String csvFilePath, String checkpointFolderPath) {
		this.csvFilePath = csvFilePath;
		this.checkpointFolderPath = checkpointFolderPath;
	}

	@Override
	public JavaStreamingContext call() throws Exception {
		final SparkConf conf = new SparkConf().setMaster("local[2]")
				.setAppName("PriceChangeTracker")
				.set("spark.streaming.stopGracefullyOnShutdown", "true");

		final JavaStreamingContext jssc = new JavaStreamingContext(conf,
				Durations.seconds(BATCH_DURATION_IN_SECONDS));
		jssc.checkpoint(checkpointFolderPath);

		final JavaDStream<PriceInformation> stream = jssc.receiverStream(
				new PriceInformationReceiver(csvFilePath));

		final JavaPairDStream<String, PriceInformation> pairs = stream
				.mapToPair(new MapToPairFunction());

		final JavaMapWithStateDStream<String, PriceInformation, PriceInformation, Optional<PriceInformation>> stateStream = pairs
				.mapWithState(StateSpec.function(new MapWithStateFunction()));

		stateStream.checkpoint(Durations.seconds(BATCH_DURATION_IN_SECONDS))
			.filter(new FilterFunction())
			.map(new MapOptionalFunction(),
					scala.reflect.ClassTag$.MODULE$.apply(PriceInformation.class))
			.print();

		return jssc;
	}
}
