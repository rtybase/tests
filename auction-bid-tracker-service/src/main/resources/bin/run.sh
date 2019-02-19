JAR_NAME=`find lib -name auction-bid-tracker-service*.jar`

java -Xmx256m -Xms128m -jar $JAR_NAME server etc/auction-bid-tracker-service.yaml