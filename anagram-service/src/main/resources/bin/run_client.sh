JAR_NAME=`find lib -name anagram-service*.jar`

java -cp $JAR_NAME com.test.anagram.service.AnagramServiceClient etc/client.properties