package test.spark.stream.io;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import test.spark.stream.core.PriceInformation;

public class CsvFileReader implements Closeable, Iterator<PriceInformation>, Serializable {
	private static final long serialVersionUID = -6088326256900426637L;

	private final CSVReader reader;
	private String[] nextLine;

	public CsvFileReader(String fileName) throws FileNotFoundException {
		reader = new CSVReader(new FileReader(fileName));
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}

	@Override
	public boolean hasNext() {
		try {
			nextLine = reader.readNext();
			return hasGoodLine();
		} catch (CsvValidationException | IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public PriceInformation next() {
		if (hasGoodLine()) {
			return new PriceInformation(
					nextLine[0],
					nextLine[1],
					nextLine[2],
					Double.parseDouble(nextLine[3])
				);
		}
		return null;
	}

	private boolean hasGoodLine() {
		return nextLine != null && nextLine.length >= 4;
	}
}
