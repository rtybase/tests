#!/bin/bash
set -o pipefail
set -ue

CHECKPOINT_DIR="./checkpoints"
LOGS_DIR="./logs"
CSV_FILE="./inputs/prices.csv"
HADOOP_HOME_DIR="./hadoop_home"

#JAVA_BIN="$JAVA_HOME/bin/java"
JAVA_BIN="java"
JAR="${project.artifactId}-${project.version}.jar"

PATH=$HADOOP_HOME_DIR/bin:$PATH

echo "Starting ${project.artifactId}"
echo "$JAVA_BIN -Dlogs_dir=$LOGS_DIR -jar $JAR -checkpoint-dir=$CHECKPOINT_DIR -csv-file=$CSV_FILE -hadoop-home-dir=$HADOOP_HOME_DIR"
$JAVA_BIN -Dlogs_dir=$LOGS_DIR -jar $JAR -checkpoint-dir=$CHECKPOINT_DIR -csv-file=$CSV_FILE -hadoop-home-dir=$HADOOP_HOME_DIR