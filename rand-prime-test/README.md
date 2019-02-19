## Randomizer and Prime Checker

This is a multi-module maven project, consisting of 3 modules

* prime-checker service, a UDP based server receiving incoming datagrams with integers for prime checking.

* randomizer client, a UDP client randomly generating integers and sending them to prime-checker.

* rand-prime-test-commons - a set of classes used by both randomizer and prime-checker.

Make sure you have JAVA 8 and Maven 3.2.x installed to be able to build it.

#### 1. Build

The quickest way to build the project is, from where the project was unpacked, e.g.

```
ls -la
... .
... ..
... README.md
... pom.xml
... prime-checker
... rand-prime-test-commons
... randomizer
```

to perform

```
mvn clean verify
```

#### 2. Run the prime-checker

Assuming the project builds successfully, it will produce the following JAR under 

```
ls -la prime-checker/target/
...
... prime-checker-1.0.0-SNAPSHOT.jar
...
```

Change the folder

```
cd prime-checker/target/
```

and

```
java -jar prime-checker-1.0.0-SNAPSHOT.jar
```

this will start the service with the default configurations (printed to STDOUT). For more granular configurations, use this as an example

```
java -jar prime-checker-1.0.0-SNAPSHOT.jar --port=1111 --queuesize=128 --threads=4
```

User CTRL+C to stop the application.

#### 3. Run the randomizer

Assuming the project builds successfully, it will produce the following JAR under 

```
ls -la randomizer/target/
...
... randomizer-1.0.0-SNAPSHOT.jar
...
```

Change the folder (in a different terminal, ideally)

```
cd randomizer/target
```

and

```
java -jar randomizer-1.0.0-SNAPSHOT.jar
```

this will start the service with the default configurations (printed to STDOUT). For more granular configurations, use this as an example

```
java -jar randomizer-1.0.0-SNAPSHOT.jar --host=localhost --port=1111
```

User CTRL+C to stop the application.

