package test.spark.stream.function;

import java.io.Serializable;

import org.apache.spark.api.java.Optional;

import scala.Function1;
import test.spark.stream.core.PriceInformation;

public class MapOptionalFunction implements Function1<Optional<PriceInformation>, PriceInformation>, Serializable {

	private static final long serialVersionUID = 2182793618493007634L;

	@Override
	public PriceInformation apply(Optional<PriceInformation> obj) {
		return obj.get();
	}
}
