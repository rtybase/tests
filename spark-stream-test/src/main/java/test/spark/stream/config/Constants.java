package test.spark.stream.config;

public class Constants {
	public static final long BATCH_DURATION_IN_SECONDS = 30;

	public static final String CSV_FILE_PATH_ARGUMENT = "csv-file";
	public static final String CHECKPOINT_PATH_ARGUMENT = "checkpoint-dir";
	public static final String HADOOP_HOME_DIR_ARGUMENT = "hadoop-home-dir";
	
	private Constants() {	
	}
}
