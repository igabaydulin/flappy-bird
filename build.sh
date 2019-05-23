#!/bin/bash

rm -r build
mkdir build
javac -cp src/java src/java/com/github/igabaydulin/flappybird/gui/MainFrame.java -d build
cp -r src/resources build
cd build
jar -cmvf resources/META-INF/MANIFEST.MF flappy-bird.jar *
