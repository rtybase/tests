package test.spark.stream.function;

import java.io.Serializable;

import org.apache.spark.api.java.Optional;

import scala.Function1;
import test.spark.stream.core.PriceInformation;

public class FilterFunction implements Function1<Optional<PriceInformation>, Object>, Serializable {

	private static final long serialVersionUID = 341295779890228075L;

	@Override
	public Object apply(Optional<PriceInformation> obj) {
		return obj.isPresent();
	}
}
