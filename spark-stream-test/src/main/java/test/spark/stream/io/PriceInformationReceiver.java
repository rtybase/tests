package test.spark.stream.io;

import static test.spark.stream.config.Constants.BATCH_DURATION_IN_SECONDS;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.receiver.Receiver;

import test.spark.stream.core.PriceInformation;

public class PriceInformationReceiver extends Receiver<PriceInformation> {
	private static final long serialVersionUID = 3965021241968286984L;

	private final String csvFileName;

	public PriceInformationReceiver(String csvFileName) {
		super(StorageLevel.MEMORY_AND_DISK_2());

		this.csvFileName = Objects.requireNonNull(csvFileName, "csvFileName must not be null!!");
	}

	@Override
	public void onStart() {
		new Thread(this::receive).start();
	}

	@Override
	public void onStop() {

	}

	private void receive() {
		while (!isStopped()) {
			try (CsvFileReader reader = new CsvFileReader(csvFileName)) {
				while (reader.hasNext()) {
					store(reader.next());
				}

				sleep();
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException | InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void sleep() throws InterruptedException {
		final long startTimeMSec = System.currentTimeMillis();
		final long totalWaitingTimeMSec = Durations.seconds(BATCH_DURATION_IN_SECONDS).milliseconds();

		while ((System.currentTimeMillis() - startTimeMSec) < totalWaitingTimeMSec && !isStopped()) {
			Thread.sleep(1000);
		}
	}
}
