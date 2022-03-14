package test.spark.stream.function;

import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function3;
import org.apache.spark.streaming.State;

import test.spark.stream.core.PriceInformation;

public class MapWithStateFunction
		implements Function3<String, Optional<PriceInformation>, State<PriceInformation>, Optional<PriceInformation>> {

	private static final long serialVersionUID = -2260431388354448709L;

	@Override
	public Optional<PriceInformation> call(String assetName, Optional<PriceInformation> priceInformation,
			State<PriceInformation> state) throws Exception {
		final PriceInformation update = priceInformation.get();

		if (state.exists()) {
			PriceInformation currentState = state.get();
			if (!currentState.equals(update)) {
				state.update(update);
			} else {
				return Optional.empty();
			}
		} else {
			state.update(update);
		}
		return priceInformation;
	}

}
