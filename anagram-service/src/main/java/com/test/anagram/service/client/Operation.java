package com.test.anagram.service.client;

public enum Operation {
	ADD {
		@Override
		public void execute(ServiceClient client, String word) {
			client.add(word);
			System.out.println("Completed!");
		}
	},
	DELETE {
		@Override
		public void execute(ServiceClient client, String word) {
			boolean succeeded = client.remove(word);
			if (succeeded) {
				System.out.println("Completed!");
			} else {
				System.out.println("Provided word wasn't found!");
			}
		}
	},
	PRINT {
		@Override
		public void execute(ServiceClient client, String word) {
			System.out.println("Result: " + client.searchFor(word));
		}
	},
	UNKNOWN {
		@Override
		public void execute(ServiceClient client, String word) {
			// nothing to do
		}
	};
	public abstract void execute(ServiceClient client, String word);
}
