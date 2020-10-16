import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Data implements java.io.Serializable {

    private Weather[] weatherList;
    private int index = 0;
    private static String[] line;

    /**
     * The default constructor that initializes the weatherList
     */
    public Data() {
        this.weatherList = new Weather[1000];
    }

    /**
     * This method will organize the weather information of the specific city into the Weather Object
     *
     * @param name the name of a city
     * @return Weather Object which includes all weather information of this city
     * @throws IOException for the FileReader and readLine
     */
    public Weather update(String name) throws IOException {
        URL web = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + name
            + "&units=metric&appid=b6656b936ee428f356f6db943c263655");
        BufferedReader content = new BufferedReader(new InputStreamReader(web.openStream()));
        String inputLine;
        String cityName = "";
        String longitude = "";
        String latitude = "";
        String weatherDescription = "";
        String temperature = "";
        String apparentTemperature = "";
        String minTemperature = "";
        String maxTemperature = "";
        String pressure = "";
        String humidity = "";
        String windSpeed = "";
        String visibility = "";
        inputLine = content.readLine();
        // Extract the information of city
        int citIndex = inputLine.indexOf("name");
        for (int i = citIndex + 7; i < inputLine.length(); i++) {
            if ((int) inputLine.charAt(i) == 34)
                break;
            cityName += inputLine.charAt(i);
        }
        // Extract the information of longitude
        int lonIndex = inputLine.indexOf("lon");
        for (int i = lonIndex + 5; i < inputLine.length(); i++) {
            if ((int) inputLine.charAt(i) == 44)
                break;
            longitude += inputLine.charAt(i);
        }
        // Extract the information of latitude
        int latIndex = inputLine.indexOf("lat");
        for (int i = latIndex + 5; i < inputLine.length(); i++) {
            if ((int) inputLine.charAt(i) == 125)
                break;
            latitude += inputLine.charAt(i);
        }
        // Extract the information of weatherDescription
        int wetIndex = inputLine.indexOf("description");
        for (int i = wetIndex + 14; i < inputLine.length(); i++) {
            if ((int) inputLine.charAt(i) == 34)
                break;
            weatherDescription += inputLine.charAt(i);
        }
        // Extract the information of temperature
        int temIndex = inputLine.indexOf("temp");
        for (int i = temIndex + 6; i < inputLine.length(); i++) {
            if ((int) inputLine.charAt(i) == 44)
                break;
            temperature += inputLine.charAt(i);
        }
        // Extract the information of apparentTemperature
        int appIndex = inputLine.indexOf("feels_like");
        for (int i = appIndex + 12; i < inputLine.length(); i++) {
            if ((int) inputLine.charAt(i) == 44)
                break;
            apparentTemperature += inputLine.charAt(i);
        }
        // Extract the information of minTemperature
        int minIndex = inputLine.indexOf("temp_min");
        for (int i = minIndex + 10; i < inputLine.length(); i++) {
            if ((int) inputLine.charAt(i) == 44)
                break;
            minTemperature += inputLine.charAt(i);
        }
        // Extract the information of maxTemperature
        int maxIndex = inputLine.indexOf("temp_max");
        for (int i = maxIndex + 10; i < inputLine.length(); i++) {
            if ((int) inputLine.charAt(i) == 44)
                break;
            maxTemperature += inputLine.charAt(i);
        }
        // Extract the information of pressure
        int preIndex = inputLine.indexOf("pressure");
        for (int i = preIndex + 10; i < inputLine.length(); i++) {
            if ((int) inputLine.charAt(i) == 44)
                break;
            pressure += inputLine.charAt(i);
        }
        // Extract the information of humidity
        int humIndex = inputLine.indexOf("humidity");
        for (int i = humIndex + 10; i < inputLine.length(); i++) {
            if ((int) inputLine.charAt(i) == 125)
                break;
            humidity += inputLine.charAt(i);
        }
        // Extract the information of windSpeed
        int winIndex = inputLine.indexOf("speed");
        for (int i = winIndex + 7; i < inputLine.length(); i++) {
            if ((int) inputLine.charAt(i) == 44)
                break;
            windSpeed += inputLine.charAt(i);
        }
        // Extract the information of visibility
        int visIndex = inputLine.indexOf("visibility");
        for (int i = visIndex + 12; i < inputLine.length(); i++) {
            if ((int) inputLine.charAt(i) == 44)
                break;
            visibility += inputLine.charAt(i);
        }

        Weather city =
            new Weather(cityName, Double.parseDouble(longitude), Double.parseDouble(latitude),
                weatherDescription, Double.parseDouble(temperature),
                Double.parseDouble(apparentTemperature), Double.parseDouble(minTemperature),
                Double.parseDouble(maxTemperature), Double.parseDouble(pressure),
                Double.parseDouble(humidity), Double.parseDouble(windSpeed),
                Double.parseDouble(visibility));


        return city;
    }


    /**
     * This method comes to get the weather information of a city within the array
     * by entering the index. It is generally used to do the test
     *
     * @param index the specific index of the city in the weatherList
     * @return the Weather object
     */
    public Weather get(int index) {
        Weather city = weatherList[index];
        return city;
    }

    /**
     * The accessor method of acquiring the list of weather information of a city
     *
     * @return the list of weather information for the city
     * @throws IOException for the FileReader and readLine
     */
    public Weather[] getWeatherList() throws IOException {
        return weatherList;
    }

}
