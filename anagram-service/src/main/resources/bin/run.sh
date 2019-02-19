JAR_NAME=`find lib -name anagram-service*.jar`

java -Xmx256m -Xms128m -jar $JAR_NAME server etc/anagram-service.yaml