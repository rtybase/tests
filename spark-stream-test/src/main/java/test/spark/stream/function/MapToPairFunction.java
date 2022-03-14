package test.spark.stream.function;

import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;
import test.spark.stream.core.PriceInformation;

public class MapToPairFunction implements PairFunction<PriceInformation, String, PriceInformation> {

	private static final long serialVersionUID = -1888758727388492803L;

	@Override
	public Tuple2<String, PriceInformation> call(PriceInformation priceInformation) throws Exception {
		return new Tuple2<>(priceInformation.assetName, priceInformation);
	}
}
