// --== CS400 File Header Information ==--
// Name: Jiahe Jin
// Email: jjin82@wisc.edu
// Team: JB
// Role: Back End Developer
// TA: Harper
// Lecturer: Florian Heimerl
// Notes to Grader: This class extends our own RedBlackTree. And All of our classes implements the
// java.io.Serializable to serialize the file into caches to store the data for next time of use.
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.*;

/**
 * This class will store the Weather Information Objects in the RedBlackTree. Some methods included
 * in it will generate the specific Weather Information. And to be serialized, this class implements
 * the java.io.Serializable.
 *
 * @author Jiahe Jin
 */
public class WeatherTree extends RedBlackTree implements java.io.Serializable {
    final Data dataBase = new Data();


    /**
     * The default constructor builds the fundamental WeatherTree which only includes the sample city
     * Madison's weather information. It will also set up the markedTime as the baseline for each time
     * the app being opened and closed.
     */
    @SuppressWarnings("unchecked") public WeatherTree() {
        try {
            this.insert(dataBase.update("Madison"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("Failing to set up the WeatherApp.");
        }
    }

    /**
     * The private helper method for lookup to find the city inside the RBT.
     */
    private static Weather lookup(Node current, String city) {
        if (current == null) {
            return null;
        }
        if (((Weather) current.data).getCity().equalsIgnoreCase(city))
            return (Weather) current.data;
        if (city.compareTo(((Weather) current.data).getCity()) < 0) {
            // current < city; look in left subtree
            return lookup(current.leftChild, city);
        } else {
            // current > city; look in right subtree
            return lookup(current.rightChild, city);
        }
    }

    /**
     * The private helper method for lookupNode to find the city inside the RBT.
     *
     * @return the node of city that is being found
     */
    private static Node lookupNode(Node current, String city) {
        if (current == null) {
            return null;
        }
        if (((Weather) current.data).getCity().equalsIgnoreCase(city))
            return current;
        if (city.compareTo(((Weather) current.data).getCity()) < 0) {
            // current < city; look in left subtree
            return lookupNode(current.leftChild, city);
        } else {
            // current > city; look in right subtree
            return lookupNode(current.rightChild, city);
        }
    }

    /**
     * This method updates the Weather information for the specific city.
     *
     * @param city the name of a city
     */
    @SuppressWarnings("unchecked") private void update(String city) {
        try {
            Weather weatherInfo = dataBase.update(city);
            this.insert(weatherInfo);
        } catch (IOException | ParseException e) {
            System.out.println("Failing to update the newest Weather Information for " + city);
        }
    }

    /**
     * This method looks up if the Weather Object of the specific city
     *
     * @param city the name of a city
     * @return the Weather Object of the specific city.
     */
    public Weather lookup(String city) {
        return lookup(root, city);
    }

    /**
     * This method looks up if the Node of Weather Object of the specific city
     *
     * @param city the name of a city
     * @return the Weather Object of the specific city.
     */
    public Node lookupNode(String city) {
        return lookupNode(root, city);
    }

    /**
     * This private helper method is used to see if the specific Weather Information stored in the
     * WeatherTree should be updated by the pass of time, because all of data will be stored, the
     * time based updates are necessary to guarantee the effective update of weather information.
     *
     * @param hours the number of hours passed want to update
     * @param city  the Weather Information Object for a specific city
     * @return true if the number of hours have been passed compared with the time marked in this
     * Weather Object city, it means should be update; false, when it is not required to be update.
     */
    private boolean timeBasedUpdate(Weather city, int hours) {
        LocalDateTime markedTime = city.getMarkedTime();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime requiredTime = currentTime.minusHours(hours);
        int result = requiredTime.compareTo(markedTime);
        // the markedTime is later than the requiredTime, namely, should be not be updated
        // the markedTime is earlier than the requiredTime, namely, not yet exceed the update time
        return result != -1;
    }

    /**
     * This method gives out the detailed weather information for the specific city.
     *
     * @param city the name of a city
     * @return all of weather information for the city
     */
    public String getDetailInfo(String city) throws IOException, ParseException {
        Weather targetCity = this.read(city);
        return "\n" + "City: " + targetCity.getCity() + "\n" + "Today's Weather: " + targetCity
            .getWeatherDescription() + "\n" + "Current Temperature: " + targetCity.getTemperature()
            + "°C" + "\n" + "Highest Temperature: " + targetCity.getMaxTemperature() + "°C" + "\n"
            + "Lowest Temperature: " + targetCity.getMinTemperature() + "°C" + "\n"
            + "Apparent Temperature: " + targetCity.getApparentTemperature() + "°C" + "\n"
            + "Humidity: " + targetCity.getHumidity() + "%" + "\n" + "Visibility: " + targetCity
            .getVisibility() + " ft" + "\n" + "Wind Speed: " + targetCity.getWindSpeed() + " mph"
            + "\n" + "Longitude: " + targetCity.getLongitude() + "°" + "\n" + "Latitude: "
            + targetCity.getLatitude() + "°" + "\n" + "Current Date: " + LocalDateTime.now() + "\n"
            + "Update Date: " + targetCity.getMarkedTime() + "\n";
    }

    /**
     * This method gives out the important weather information for the specific city.
     *
     * @param city the name of a city
     * @return the important weather information for the specific city
     */
    public String getImportantInfo(String city) throws IOException, ParseException {
        Weather targetCity = this.read(city);
        return "\n" + "City: " + targetCity.getCity() + "\n" + "Today's Weather: " + targetCity
            .getWeatherDescription() + "\n" + "Current Temperature: " + targetCity.getTemperature()
            + "°C" + "\n" + "Highest Temperature: " + targetCity.getMaxTemperature() + "°C" + "\n"
            + "Lowest Temperature: " + targetCity.getMinTemperature() + "°C" + "\n"
            + "Current Date: " + LocalDateTime.now() + "\n" + "Update Date: " + targetCity
            .getMarkedTime() + "\n";
    }

    /**
     * This method read out the city that is being checked. In this method, lookup method will be
     * used to check if the city has been in the WeatherTree. If it does not exist in the WeatherTree
     * or its markedTime exceeds the update time, we will update this city's weather information again
     * in the WeatherTree.
     *
     * @param city the name of a city
     * @return the Weather Object for this specific being checked city
     */
    public Weather read(String city) throws IOException, ParseException {
        // Check if the WeatherTree contains the city
        if (this.lookup(city) == null) {
            this.update(city);
        } else {
            // Get the Weather Object for the read city
            Weather targetCity = this.lookup(city);
            // Check if the targetCity's markedTime exceeds the Update time
            if (this.timeBasedUpdate(targetCity, 1)) {
                targetCity.setMarkedTime(LocalDateTime.now());
                this.reset(this.lookupNode(city), targetCity);
            }
        }
        return lookup(city);
    }

    /**
     * This private helper method helps to reset the targetCity into the targetNode of the WeatherTree
     *
     * @param targetCity the Weather Object of a city which will be updated as a new Weather Object
     *                   and then being replaced with the original one.
     * @param targetNode the target city's node in the WeatherTree.
     */
    private void reset(Node targetNode, Weather targetCity) throws IOException, ParseException {
        targetNode.data = dataBase.update(targetCity.getCity());
    }

}
