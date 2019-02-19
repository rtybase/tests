# Service how-to guide

This README contains the instructions on how to build and run locally the Anagram Service. 
Make sure Java 8 and Maven 3.2.x are installed. Included shell scripts are for Linux/MacOS
only. Unfortunately, no Windows scripts are provided, but shell scripts are easy to
reproduce just by looking at the content of the shell scripts.

## 1. Build the code

From the command line
```
mvn clean verify
```
The build will produce the "anagram-service-0.0.1-SNAPSHOT-dist.tar.gz" file in the "target/" folder.

## 2. Running the service

Unpack the tar.gz from the "target/" folder.
```
tar -xvzf anagram-service-0.0.1-SNAPSHOT-dist.tar.gz
```

This will produce a local folder "anagram-service/". Open it
```
cd anagram-service/
```

Start the service
```
./bin/run.sh
```

If you need to configure the service differently, check "etc/" folder for more details (e.g. anagram-service.yaml).

## 3. Check the service is running

The following folder "bin/" contains a test script. Open the folder and execute
```
./service-api-test.sh
```

There will be 5 calls to the service

- one to healthcheck endpoint
- one call to add words with HTTP status 204
- two calls to search for words
- one call to delete a word with HTTP status 204

## 4. Running the client

As simple as
```
 ./bin/run_client.sh
```
