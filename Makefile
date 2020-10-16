default: build

ArgumentParser.class: ArgumentParser.java
	javac ArgumentParser.java

CityNameList.class: CityNameList.java
	javac -classpath .:json-simple-1.1.jar CityNameList.java

Data.class: Data.java
	javac Data.java

RedBlackTree.class: RedBlackTree.java
	javac RedBlackTree.java

Weather.class: Weather.java
	javac Weather.java

WeatherApp.class: WeatherApp.java
	javac -classpath .:json-simple-1.1.jar WeatherApp.java

WeatherTree.class: WeatherTree.java
	javac -classpath .:json-simple-1.1.jar WeatherTree.java

build: ArgumentParser.class CityNameList.class Data.class RedBlackTree.class Weather.class WeatherApp.class WeatherTree.class

clean:
	$(RM) *.class
	$(RM) .*.weather_app_cache

run: build
	echo "to run the file: ./weatherapp {commands}"

