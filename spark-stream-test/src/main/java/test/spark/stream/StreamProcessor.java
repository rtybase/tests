package test.spark.stream;

import static test.spark.stream.config.Constants.CHECKPOINT_PATH_ARGUMENT;
import static test.spark.stream.config.Constants.CSV_FILE_PATH_ARGUMENT;
import static test.spark.stream.config.Constants.HADOOP_HOME_DIR_ARGUMENT;

import java.io.File;
import java.util.Optional;

import org.apache.spark.streaming.api.java.JavaStreamingContext;

import test.spark.stream.config.CommandLineParser;
import test.spark.stream.function.ContextCreationFunction;

public class StreamProcessor {

	public static void main(String[] args) throws InterruptedException {
		final CommandLineParser parser = new CommandLineParser(args);

		Optional<String> hadoopHomeDirArg = parser.getValueForOptional(HADOOP_HOME_DIR_ARGUMENT);

		hadoopHomeDirArg.ifPresent(hadoopHomeDir -> {
			File folder = new File(hadoopHomeDir);
			System.setProperty("hadoop.home.dir", folder.getAbsolutePath());
			System.out.println("hadoop.home.dir=" + folder.getAbsolutePath());
		});

		System.setProperty("spark.cleaner.referenceTracking.cleanCheckpoints", "true");

		final JavaStreamingContext jssc = JavaStreamingContext.getOrCreate(
				parser.getValueForMandatory(CHECKPOINT_PATH_ARGUMENT),
				new ContextCreationFunction(
						parser.getValueForMandatory(CSV_FILE_PATH_ARGUMENT),
						parser.getValueForMandatory(CHECKPOINT_PATH_ARGUMENT)));

		jssc.start();
		jssc.awaitTermination();
		jssc.close();
	}
}
