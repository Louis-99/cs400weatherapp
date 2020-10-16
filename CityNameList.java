import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class is used to load all of searchable names of cities in the list and
 * gives out several methods to search the desired cities based on their partial
 * starting names or their nations. And to be serialized, this class implements
 * the java.io.Serializable.
 *
 * @author Jiahe Jin
 */
public class CityNameList implements java.io.Serializable {
    private ArrayList city;
    private ArrayList nation;


    /**
     * The city-nation pair that will be loaded to the final ArrayList of
     * CityNameList to allow access of both city and nation.
     */
    public class pair implements Comparable, java.io.Serializable {
        public String city;
        public String nation;

        /**
         * The inner class constructor instantiates the pair of city and its
         * corresponded nation.
         *
         * @param city   the name of a city
         * @param nation the name of a nation
         */
        public pair(String city, String nation) {
            this.city = city;
            this.nation = nation;
        }

        /**
         * The override method used to help compare the city of this pair when the pair
         * is called in the Collection.sort() method.
         *
         * @param o the object of pair being compared with itself
         */
        @Override public int compareTo(Object o) {
            return city.compareTo(((pair) o).city);
        }

    }

    /**
     * The default constructor comes to instantiate the two blank ArrayList for both
     * city and its corresponded nation.
     */
    public CityNameList() {
        city = new ArrayList();
        nation = new ArrayList();
    }

    /**
     * This method comes to load all of searchable cities with their country names
     * into the two ArrayList in this class.
     *
     * @throws IOException    when the FileReader meets an error
     * @throws ParseException when the JSONParser meets an error
     */
    @SuppressWarnings("unchecked") public void load() throws IOException, ParseException {
        FileReader reader = new FileReader("city.list.json"); // read the file
        JSONParser jsonParser = new JSONParser(); // JSON parser object to parse read file
        Object object = jsonParser.parse(reader); // parse the reader into the json object
        JSONArray searchList = (JSONArray) object; // cast jason object into JSONArray

        for (int i = 0; i < searchList.size(); i++) {
            city.add(((JSONObject) searchList.get(i)).get("name").toString());
            nation.add(((JSONObject) searchList.get(i)).get("country").toString());
        }
    }

    /**
     * This method is used to see if the name of a city is contained in the
     * CityNameList.
     *
     * @param name the name of a city
     * @return true if the CiyNameList contains the name of this city, false if the
     * it does not contain the name of this city.
     */
    public boolean contains(String name) {
        return this.city.contains(name);
    }

    /**
     * This method intends to give out the iterator of ArrayList of cities in the
     * order of start with the entered city name.
     *
     * @param name the name of a city that could be incomplete
     * @return the Iterator of the ArrayList of cities after appropriate sort.
     */
    @SuppressWarnings("unchecked") public Iterator<String> search(String name) {
        name =
            name.substring(0, 1).toUpperCase() + name.substring(1); // Capitalize the first letter
        ArrayList sortedList = this.sortCityByStartWith(name);
        ArrayList finalList = new ArrayList();
        for (int i = 0; i < sortedList.size(); i++) {
            finalList.add(
                ((pair) sortedList.get(i)).city + "     --" + ((pair) sortedList.get(i)).nation);
        }
        Iterator<String> itCity = finalList.iterator();
        return itCity;
    }

    /**
     * This method intends to give out the iterator of ArrayList of cities in the
     * order of start with the entered city name and the corresponded country name.
     *
     * @param name    the name of a city that could be incomplete
     * @param country the name of a country that could be incomplete
     * @return the Iterator of the ArrayList of cities after appropriate sort.
     */
    @SuppressWarnings("unchecked") public Iterator<String> search(String name, String country) {
        name =
            name.substring(0, 1).toUpperCase() + name.substring(1); // Capitalize the first letter
        country = country.toUpperCase(); // Capitalize the first letter
        ArrayList sortedList = this.sortCityByNationAndStartWith(name, country);
        ArrayList finalList = new ArrayList();
        for (int i = 0; i < sortedList.size(); i++) {
            finalList.add(
                ((pair) sortedList.get(i)).city + "     --" + ((pair) sortedList.get(i)).nation);
        }
        Iterator<String> itCity = finalList.iterator();
        return itCity;
    }

    /**
     * The private helper method is used for search methods to help them to sort the
     * ArrayList of cities in the order of start with the entered city name
     *
     * @param name the name of a city that could be incomplete
     * @return the sorted list based on the name.
     */
    @SuppressWarnings("unchecked") private ArrayList sortCityByStartWith(String name) {
        ArrayList checkList = new ArrayList();
        ArrayList sortList = new ArrayList();
        for (int i = 0; i < city.size(); i++) {
            if (city.get(i).toString().startsWith(name) && !checkList
                .contains(city.get(i).toString())) {
                checkList.add(city.get(i));
                sortList.add(new pair(city.get(i).toString(), nation.get(i).toString()));
            }
        }
        Collections.sort(sortList);
        return sortList;
    }

    /**
     * The private helper method is used for search methods to help them to sort the
     * ArrayList of cities in the order of start with the entered city name
     *
     * @param name    the name of a city that could be incomplete
     * @param country the name of a country that could be incomplete
     * @return the sorted list based on the name.
     */
    @SuppressWarnings("unchecked") private ArrayList sortCityByNationAndStartWith(String name,
        String country) {
        ArrayList checkList = new ArrayList();
        ArrayList sortList = new ArrayList();
        for (int i = 0; i < city.size(); i++) {
            if (city.get(i).toString().startsWith(name) && !checkList
                .contains(city.get(i).toString()) && nation.get(i).toString().equals(country)) {
                checkList.add(city.get(i));
                sortList.add(new pair(city.get(i).toString(), nation.get(i).toString()));
            }
        }
        Collections.sort(sortList);
        return sortList;
    }

    /**
     * This method is the test method which will finally be deleted. You can try
     * other letters of cities in this method to search any cities as you want.
     */
    public void getCity() {
        Iterator<String> itCity = this.search("Wuh", "cn");
        while (itCity.hasNext()) {
            System.out.println(itCity.next());
        }
    }

    /**
     * This method is also the test method which will finally be deleted.
     */
    public static void main(String[] args) {
        CityNameList nameList = new CityNameList();
        try {
            nameList.load();
            nameList.getCity();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
