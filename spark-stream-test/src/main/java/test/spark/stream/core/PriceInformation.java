package test.spark.stream.core;

import java.io.Serializable;
import java.util.Objects;

public class PriceInformation implements Serializable {
	private static final long serialVersionUID = -1318765421200944040L;

	public final String assetName;
	public final String priceDate;
	public final String publishDate;
	public final double price;

	public PriceInformation(String assetName, String priceDate, String publishDate, double price) {
		this.assetName = assetName;
		this.priceDate = priceDate;
		this.publishDate = publishDate;
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(assetName);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (getClass() != o.getClass()) {
			return false;
		}

		final PriceInformation other = (PriceInformation) o;
		return Objects.equals(assetName, other.assetName) 
				&& Objects.equals(priceDate, other.priceDate)
				&& Objects.equals(publishDate, other.publishDate)
				&& Double.compare(price, other.price) == 0;
	}

	@Override
	public String toString() {
		return String.format("assetName=%s, price=%f as of %s, latest update on %s",
				assetName,
				price,
				priceDate,
				publishDate);
	}
}
