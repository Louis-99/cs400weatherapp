import java.time.LocalDateTime;

/**
 * This class is used to store all the useful information that will be included
 * in the node of the Weathercast Tree
 *
 * @author Tianwei Bao
 */
public class Weather implements Comparable, java.io.Serializable {

    private String city;
    private double longitude;
    private double latitude;
    private String weatherDescription;
    private double temperature; // in Celsius degree
    private double apparentTemperature; // Celsius degree
    private double minTemperature; // Celsius degree
    private double maxTemperature;// Celsius degree
    private double pressure;
    private double humidity;
    private double windSpeed;
    private double visibility;
    private LocalDateTime markedTime; // the last being called marked time

    /**
     * This is the default constructor that stores the weather information of a city
     *
     * @param longitude           the longitude of the city
     * @param latitude            the latitude of the city
     * @param weatherDescription  the weather condition
     * @param temperature         the temperature of the city
     * @param apparentTemperature the "feels-like" temperature of the city
     * @param minTemperature      the minimum temperature of the city
     * @param maxTemperature      the maximum temperature of the city
     * @param pressure            the sea level pressure of the city
     * @param humidity            the humidity of the city
     * @param windSpeed           the wind speed of the city
     * @param visibility          the visibility of the city
     */
    public Weather(String city, double longitude, double latitude, String weatherDescription,
        double temperature, double apparentTemperature, double minTemperature,
        double maxTemperature, double pressure, double humidity, double windSpeed,
        double visibility) {

        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.weatherDescription = weatherDescription;
        this.temperature = temperature;
        this.apparentTemperature = apparentTemperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.visibility = visibility;

        // setting marked time for each weather object
        if (markedTime == null) {
            this.markedTime = LocalDateTime.now();
        }
    }

    /**
     * The accessor of markedTime
     *
     * @return the name of city
     */
    public LocalDateTime getMarkedTime() {
        return markedTime;
    }

    /**
     * The mutator of markedTime
     *
     * @param newMarkedTime the new marked time for this weather object
     */
    public void setMarkedTime(LocalDateTime newMarkedTime) {
        this.markedTime = newMarkedTime;
    }

    /**
     * The accessor of city
     *
     * @return the name of city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * The accessor of longitude
     *
     * @return the value of longitude
     */
    public double getLongitude() {
        return this.longitude;
    }

    /**
     * The mutator of longitude
     *
     * @param longitude the value of longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * The accessor of latitude
     *
     * @return the value of latitude
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * The mutator of latitude
     *
     * @param latitude the value of latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * The accessor of weatherDescription
     *
     * @return the value of weatherDescription
     */
    public String getWeatherDescription() {
        return this.weatherDescription;
    }

    /**
     * The mutator of waetherDescription
     *
     * @param weatherDescription the value of weatherDescrption
     */
    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    /**
     * The accessor of temperature
     *
     * @return the value of temperature
     */
    public double getTemperature() {
        return this.temperature;
    }

    /**
     * The mutator of temperature
     *
     * @param temperature the value of temperature
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * The accessor of apparentTemperature
     *
     * @return the value of apparentTemperature
     */
    public double getApparentTemperature() {
        return this.apparentTemperature;
    }

    /**
     * The mutator of apparentTemperature
     *
     * @param apparentTemperature the value of apparentTemperature
     */
    public void setApparentTemperature(double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    /**
     * The accessor of minTemperature
     *
     * @return the value of minTemperature
     */
    public double getMinTemperature() {
        return this.minTemperature;
    }

    /**
     * The mutator of minTemperature
     *
     * @param minTemperature the value of minTemperature
     */
    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    /**
     * The accessor of maxTemperature
     *
     * @return the value of maxTemperature
     */
    public double getMaxTemperature() {
        return this.maxTemperature;
    }

    /**
     * The mutator of maxTemperature
     *
     * @param maxTemperature the value of maxTemperature
     */
    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    /**
     * The accessor of pressure
     *
     * @return the value of pressure
     */
    public double getPressure() {
        return this.pressure;
    }

    /**
     * The mutator of pressure
     *
     * @param pressure the value of pressure
     */
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    /**
     * The accessor of humidity
     *
     * @return the value of humidity
     */
    public double getHumidity() {
        return this.humidity;
    }

    /**
     * The mutator of humidity
     *
     * @param humidity the value of humidity
     */
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    /**
     * The accessor of windSpeed
     *
     * @return the value of windSpeed
     */
    public double getWindSpeed() {
        return this.windSpeed;
    }

    /**
     * The mutator of windSpeed
     *
     * @param windSpeed the value of windSpeed
     */
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * The accessor of visibility
     *
     * @return the value of visibility
     */
    public double getVisibility() {
        return this.visibility;
    }

    /**
     * The mutator of visibility
     *
     * @param visibility the value of visibility
     */
    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    @Override public int compareTo(Object o) {
        Weather weather = (Weather) o;
        return this.city.compareTo(((Weather) o).city);
    }
}
