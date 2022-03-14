package test.spark.stream.config;

import java.util.Objects;
import java.util.Optional;

public class CommandLineParser {
	private final String[] commandLineArguments;

	public CommandLineParser(String[] commandLineArguments) {
		this.commandLineArguments = Objects.requireNonNull(commandLineArguments,
				"commandLineArguments must not be null!");
	}
 
	public String getValueForMandatory(String argumentName) {
		final String result = getValueFor(argumentName);
		if (result == null) {
			throw new IllegalArgumentException(
					String.format("'%s' was not provided in the command line!", argumentName));
		}
		return result;
	}

	public Optional<String> getValueForOptional(String argumentName) {
		return Optional.ofNullable(getValueFor(argumentName));
	}

	private String getValueFor(String argumentName) {
		Objects.requireNonNull(argumentName, "argumentName must not be null!");
		final String argumentPrefix = String.format("-%s=", argumentName.toLowerCase());

		for (String argument : commandLineArguments) {
			if (argument.toLowerCase().startsWith(argumentPrefix)) {
				return argument.substring(argumentPrefix.length());
			}
		}
		return null;
	}
}
