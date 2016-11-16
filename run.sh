#!/bin/sh

javac **/*.java
cd out/production/1090
jar -cvfe planes.jar com.benburwell.planes.gui.Main1090 **
java -jar planes.jar

