// --== CS400 File Header Information ==--
// Name: Jiahe Jin
// Email: jjin82@wisc.edu
// Team: JB
// Role: Back End Developer
// TA: Harper
// Lecturer: Florian Heimerl
// Notes to Grader: The previous of this file has a problem when reading the api, so that I rewrite
// this class by using the specific tool of json-simple-1.1.jar to read more precisely for each data.
// The way I used is similar to the way I wrote in CityNameList. And All of our classes implements the
// java.io.Serializable to serialize the file into caches to store the data for next time of use.
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * This class serves to load the specific weather data from the api.
 *
 * @author Tianwei Bao, Jiahe Jin
 */
public class Data implements java.io.Serializable {

    /**
     * This method will organize the weather information of the specific city into the Weather Object
     *
     * @param name the name of a city
     * @return Weather Object which includes all weather information of this city
     * @throws IOException for the FileReader and readLine
     */
    public Weather update(String name) throws IOException, ParseException {

        // Read Information of a city from the api
        URL web = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + name
            + "&units=metric&appid=b6656b936ee428f356f6db943c263655");
        BufferedReader content = new BufferedReader(new InputStreamReader(web.openStream()));
        String inputLine = content.readLine();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(inputLine);

        // Get specific data from the jsonObject
        String cityName = jsonObject.get("name").toString(); // 1) Get cityName
        String longitude =
            ((JSONObject) jsonObject.get("coord")).get("lon").toString(); // 2) Get longitude
        String latitude =
            ((JSONObject) jsonObject.get("coord")).get("lat").toString(); // 3) Get Latitude
        String weatherDescription =
            ((JSONObject) ((JSONArray) jsonObject.get("weather")).get(0)).get("description")
                .toString(); // 4) Get Weather Description
        String temperature =
            ((JSONObject) jsonObject.get("main")).get("temp").toString(); // 5) Get Temperature
        String apparentTemperature = ((JSONObject) jsonObject.get("main")).get("feels_like")
            .toString(); // 6) Get apparentTemperature
        String minTemperature = ((JSONObject) jsonObject.get("main")).get("temp_min")
            .toString(); // 7) Get minTemperature
        String maxTemperature = ((JSONObject) jsonObject.get("main")).get("temp_max")
            .toString(); // 8) Get maxTemperature
        String pressure =
            ((JSONObject) jsonObject.get("main")).get("pressure").toString(); // 9) Get Pressure
        String humidity =
            ((JSONObject) jsonObject.get("main")).get("humidity").toString();  // 10) Get Humidity
        String windSpeed =
            ((JSONObject) jsonObject.get("wind")).get("speed").toString(); // 11) Get Wind speed
        String visibility = jsonObject.get("visibility").toString(); // 12) Get Visibility

        Weather city =
            new Weather(cityName, Double.parseDouble(longitude), Double.parseDouble(latitude),
                weatherDescription, Double.parseDouble(temperature),
                Double.parseDouble(apparentTemperature), Double.parseDouble(minTemperature),
                Double.parseDouble(maxTemperature), Double.parseDouble(pressure),
                Double.parseDouble(humidity), Double.parseDouble(windSpeed),
                Double.parseDouble(visibility));
        return city;
    }



}
