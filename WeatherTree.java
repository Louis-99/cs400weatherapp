import java.io.IOException;
import java.time.*;

/**
 * This class will store the Weather Information Objects in the RedBlackTree.
 * Some methods included in it will generate the specific Weather Information.
 * And to be serialized, this class implements the java.io.Serializable.
 *
 * @author Jiahe Jin
 */
public class WeatherTree extends RedBlackTree implements java.io.Serializable {
    final Data dataBase = new Data();

    /**
     * The default constructor builds the fundamental WeatherTree which only
     * includes the sample city Madison's weather information. It will also set up
     * the markedTime as the baseline for each time the app being opened and closed.
     */
    @SuppressWarnings("unchecked")
    public WeatherTree() {
        try {
            this.insert(dataBase.update("Madison"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failing to set up the WeatherApp.");
        }
    }

    /**
     * This method updates the Weather information for the specific city.
     *
     * @param city the name of a city
     */
    @SuppressWarnings("unchecked")
    private void update(String city) {
        try {
            Weather weatherInfo = dataBase.update(city);
            this.insert(weatherInfo);
        } catch (IOException e) {
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
     * This method looks up if the Node of Weather Object of the specific city
     *
     * @param city the name of a city
     * @return the Weather Object of the specific city.
     */
    public Node lookupNode(String city) {
        return lookupNode(root, city);
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
     * This private helper method is used to see if the specific Weather Information
     * stored in the WeatherTree should be updated by the pass of time, because all
     * of data will be stored, the time based updates are necessary to guarantee the
     * effective update of weather information.
     *
     * @param hours the number of hours passed want to update
     * @param city  the Weather Information Object for a specific city
     * @return true if the number of hours have been passed compared with the time
     *         marked in this Weather Object city, it means should be update; false,
     *         when it is not required to be update.
     */
    private boolean timeBasedUpdate(Weather city, int hours) {
        LocalDateTime markedTime = city.getMarkedTime();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime requiredTime = currentTime.minusHours(hours);
        int result = requiredTime.compareTo(markedTime);
        // the markedTime is later than the requiredTime, namely, should be not be
        // updated
        if (result < 0) {
            return false;
        } else { // the markedTime is earlier than the requiredTime, namely, not yet exceed the
                 // update time
            return true;
        }
    }

    /**
     * This method gives out the detailed weather information for the specific city.
     *
     * @param city the name of a city
     * @return all of weather information for the city
     */
    public String getDetailInfo(String city) throws IOException {
        Weather targetCity = this.read(city);
        return "\n" + "City: " + targetCity.getCity() + "\n" + "Today's Weather: " + targetCity.getWeatherDescription()
                + "\n" + "Current Temperature: " + targetCity.getTemperature() + "°C" + "\n" + "Highest Temperature: "
                + targetCity.getMaxTemperature() + "°C" + "\n" + "Lowest Temperature: " + targetCity.getMinTemperature()
                + "°C" + "\n" + "Apparent Temperature: " + targetCity.getApparentTemperature() + "°C" + "\n"
                + "Humidity: " + targetCity.getHumidity() + "%" + "\n" + "Visibility: " + targetCity.getVisibility()
                + " ft" + "\n" + "Wind Speed: " + targetCity.getWindSpeed() + " mph" + "\n" + "Longitude: "
                + targetCity.getLongitude() + "°" + "\n" + "Latitude: " + targetCity.getLatitude() + "°" + "\n"
                + "Current Date: " + LocalDateTime.now() + "\n" + "Update Date: " + targetCity.getMarkedTime() + "\n";
    }

    /**
     * This method gives out the important weather information for the specific
     * city.
     *
     * @param city the name of a city
     * @return the important weather information for the specific city
     */
    public String getImportantInfo(String city) throws IOException {
        Weather targetCity = this.read(city);
        return "\n" + "City: " + targetCity.getCity() + "\n" + "Today's Weather: " + targetCity.getWeatherDescription()
                + "\n" + "Current Temperature: " + targetCity.getTemperature() + "°C" + "\n" + "Highest Temperature: "
                + targetCity.getMaxTemperature() + "°C" + "\n" + "Lowest Temperature: " + targetCity.getMinTemperature()
                + "°C" + "\n" + "Current Date: " + LocalDateTime.now() + "\n" + "Update Date: "
                + targetCity.getMarkedTime() + "\n";
    }

    /**
     * This method read out the city that is being checked. In this method, lookup
     * method will be used to check if the city has been in the WeatherTree. If it
     * does not exist in the WeatherTree or its markedTime exceeds the update time,
     * we will update this city's weather information again in the WeatherTree.
     *
     * @param city the name of a city
     * @return the Weather Object for this specific being checked city
     */
    public Weather read(String city) throws IOException {
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
     * This private helper method helps to reset the targetCity into the targetNode
     * of the WeatherTree
     *
     * @param targetCity the Weather Object of a city which will be updated as a new
     *                   Weather Object and then being replaced with the original
     *                   one.
     * @param targetNode the target city's node in the WeatherTree.
     */
    private void reset(Node targetNode, Weather targetCity) throws IOException {
        targetNode.data = dataBase.update(targetCity.getCity());
    }

    /**
     * It is used to test the functionability of WeatherTree.class. The city String
     * could be replaced by any other cities searched in the CityNameList main
     * method. If any error occurs, it might be caused by the Data class. Finally,
     * it ignores the upper or lower case of letters. You can try more cities as you
     * want. And this method will finally be deleted.
     */
    public static void main(String[] args) throws IOException {
        WeatherTree myTree = new WeatherTree();
        myTree.getImportantInfo("Shanghai");
        myTree.getImportantInfo("Shanghai");

    }
}
