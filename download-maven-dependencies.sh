#!/bin/bash

for confluentDependencies in {"kafka-protobuf-types/7.3.1","kafka-protobuf-provider/7.3.1","kafka-json-schema-provider/7.3.1","kafka-schema-registry-maven-plugin/7.3.1","common-utils/7.3.1","kafka-schema-serializer/7.3.1","kafka-schema-registry-client/7.3.1","common-parent/7.3.1"," common/7.3.1","rest-utils-parent/7.3.1","kafka-schema-registry-parent/7.3.1","kafka-avro-serializer/7.3.1"}; do

  # Get the first item in the split string
  artifact="$(echo $confluentDependencies | cut -d'/' -f1)"

  # Get the second item in the split string
  version="$(echo $confluentDependencies | cut -d'/' -f2)"

  echo "artifact $artifact version $version"

  mvnPath="/home/raogc/.m2/repository/io/confluent/$artifact/$version"
  # Confirm the result by outputting it to screen
  echo "maven path $mvnPath"

  if [ ! -d $mvnPath ]; then
    mkdir -p "$mvnPath"
  fi

  if [ -d $mvnPath ]; then
    echo "$mvnPath exists"
  fi

  urlBase="https://packages.confluent.io/maven/io/confluent"
  artifactPath="$urlBase/$artifact/$version"
  artifactBaseFilePath="$artifactPath/$artifact-$version"
  jarFile="$artifactBaseFilePath.jar"
  pomFile="$artifactBaseFilePath.pom"

  echo "jar $jarFile pom $pomFile"

  wget $jarFile -P $mvnPath
  wget $pomFile -P $mvnPath

  ls $mvnPath
done


for apacheDependencies in {"kafka-clients/7.3.1-ccs"}; do
  # Get the first item in the split string
  artifact="$(echo apacheDependencies | cut -d'/' -f1)"

  # Get the second item in the split string
  version="$(echo apacheDependencies | cut -d'/' -f2)

   mvnPath="/home/raogc/.m2/repository/org/apache/kafka/$artifact/$version"
    # Confirm the result by outputting it to screen
    echo "maven path $mvnPath"

    if [ ! -d $mvnPath ]; then
      mkdir -p "$mvnPath"
    fi

    if [ -d $mvnPath ]; then
      echo "$mvnPath exists"
    fi

    urlBase="https://packages.confluent.io/maven/org/apache/kafka"
    artifactPath="$urlBase/$artifact/$version"
    artifactBaseFilePath="$artifactPath/$artifact-$version"
    jarFile="$artifactBaseFilePath.jar"
    pomFile="$artifactBaseFilePath.pom"

    echo "jar $jarFile pom $pomFile"

    wget $jarFile -P $mvnPath
    wget $pomFile -P $mvnPath

    ls $mvnPath
done

