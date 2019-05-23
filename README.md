# Flappy Bird
This project was carried out as a laboratory work during my student days. It is a full copy of the famous mobile game "Flappy Bird". The project was written in a short time (1-2 days, I do not remember exactly) and does contain a lot of stupid code. But in the end it works! 😊

<p align="center">
  <img src="https://github.com/igabaydulin/flappy-bird/blob/master/resources/main-screenshot.png" height="250">
</p>

## JAR build
```
./build.sh
```

`build.sh` source:
```bash
#!/bin/bash

rm -r build
mkdir build
javac -cp src/java src/java/com/github/igabaydulin/flappybird/gui/MainFrame.java -d build
cp -r src/resources build
cd build
jar -cmvf resources/META-INF/MANIFEST.MF flappy-bird.jar *
```

`./build/flappy-bird.jar` is a generated jar file

## JAR file execution
```
java -jar flappy-bird.jar
```

## Contribution
Not sure if anyone would want to contribute otherwise feel free to create issues, pull requests and so on. You can also email me at igabaydulin@gmail.com

## Future
It definetly needs refactoring, but maybe it also shoud be rewritten using different libs. I will do it if I have free time ヽ(•‿•)ノ
