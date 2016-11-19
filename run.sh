#!/bin/sh

export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8

mkdir -p out/production/1090
javac -d out/production/1090 $(find src -name '*.java')
cd out/production/1090
jar -cvfe planes.jar com.benburwell.planes.gui.Main1090 **
java -jar planes.jar

