import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

/**
 * entrance of weather app
 *
 * @author Yunzhao Liu
 */
public class WeatherApp {
    static final String help_document = ""
            + "(no argument):                         \t\tshow the weather information of each city in the favorite list\n"
            + "{city_name city_name …}:               \tshow the weather information of cities\n"
            + "--help or –h:                          \tshow the this document\n"
            + "--version or –v:                       \tshow the version information\n"
            + "--clean-cache:                         \t\tclean the cache file of the application\n"
            + "--add or –a {city_name city_name …}:   \tadd the cities to the favorites list\n"
            + "--list or –l:                          \tlist all the item in the favorites list\n"
            + "--remove or –r {city_name city_name …}:\tremove cities from favorites list\n"
            + "--detail:                              \t\tshow the weather information in detail mode\n"
            + "--search or -s {string}:               \t\tsearch available city names begin with {string}\n"
            + "--search or -s {string} [{nation}]:    \t\tsearch available city names begin with {string} in country {nation}";

    static final String version_document = "This is Weather version 0.0.1";

    static final String favoriteListCache = "./.weather_favorite_list.weather_app_cache";
    static final String weatherDataCache = "./.weather_weather_data.weather_app_cache";
    static final String cityDataCache = "./.city_data_cache.weather_app_cache";

    /**
     * write an object to a file using serialization.
     *
     * @param obj      the object written
     * @param filename path of the file that object write to
     */
    static void writeObject(Serializable obj, String filename) {
        try {
            FileOutputStream fo = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fo);
            out.writeObject(obj);
            out.close();
            fo.close();
        } catch (IOException e) {
            System.out.println("error when write the cache: " + filename);
            e.printStackTrace();
        }
    }

    /**
     * read an object from a file
     *
     * @param filename path of the file read from
     * @return the object
     */
    static Object readObject(String filename) {
        Object ret = null;
        try {
            FileInputStream fi = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fi);
            ret = in.readObject();
            in.close();
            fi.close();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return ret;
    }

    /**
     * delete a file
     *
     * @param filename the file name
     */
    static void deleteFile(String filename) {
        File file = new File(filename);
        file.delete();
    }

    /**
     * load favorite list from cache
     *
     * @return the favorite list
     */
    @SuppressWarnings("unchecked")
    static ArrayList<String> loadFavoriteList() {
        ArrayList<String> list;
        list = (ArrayList<String>) readObject(favoriteListCache);
        if (list == null)
            list = new ArrayList<>();
        return list;
    }

    /**
     * load WeatherTree from cache
     *
     * @return the WeatherTree
     */
    static WeatherTree loadWeatherTree() {
        WeatherTree tree;
        tree = (WeatherTree) readObject(weatherDataCache);
        if (tree == null) {
            tree = new WeatherTree();
        }
        return tree;
    }

    /**
     * load city name list from cache
     *
     * @return the city name list
     */
    static CityNameList loadCityNameList() {
        CityNameList list;
        list = (CityNameList) readObject(cityDataCache);
        if (list == null) {
            list = new CityNameList();
            try {
                list.load();
            } catch (IOException | ParseException e) {
                System.out.println("error when load city name list");
                e.printStackTrace();
            }
            writeCityNameList(list);
        }
        return list;
    }

    /**
     * write favorite list to cache
     *
     * @param list the favorite list
     */
    static void writeFavoriteList(ArrayList<String> list) {
        writeObject(list, favoriteListCache);
    }

    /**
     * write WeatherTree to cache
     *
     * @param tree the WeatherTree
     */
    static void writeWeatherTree(WeatherTree tree) {
        writeObject(tree, weatherDataCache);
    }

    /**
     * write city name list to cache
     *
     * @param list the city name list list
     */
    static void writeCityNameList(CityNameList list) {
        writeObject(list, cityDataCache);
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> favoriteList = loadFavoriteList();
        WeatherTree tree = loadWeatherTree();
        CityNameList cityNameList = null;
        // when no argument, print weather information of favorite list
        if (args.length == 0) {
            if (favoriteList.isEmpty()) {
                System.out.println(
                        "favorite list is empty. \nuse --add to add city into the list. \nuse --help to see the help document.");
            } else {
                for (String city : favoriteList) {
                    System.out.println(tree.getImportantInfo(city));
                }
            }
        } else {
            ArgumentParser parser = new ArgumentParser(args);
            try {
                parser.parseArgument();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return;
            }
            // with --detail only, print weather information of favorite list in detail mode
            if (args.length == 1 && parser.isArgDetail()) {
                if (favoriteList.isEmpty()) {
                    System.out.println("favorite list is empty. \nuse --add to add city into the list.");
                } else {
                    for (String city : favoriteList) {
                        System.out.println(tree.getDetailInfo(city));
                    }
                }
            } else {
                // other commands
                if (parser.isArgHelp()) {
                    System.out.println(help_document);
                }
                if (parser.isArgVersion()) {
                    System.out.println(version_document);
                }
                if (parser.getAddCities() != null) {
                    if (cityNameList == null) {
                        cityNameList = loadCityNameList();
                    }
                    for (String city : parser.getAddCities()) {
                        if (!cityNameList.contains(city)) {
                            System.out.println("\"" + city + "\" does not in the dataset");
                            continue;
                        }
                        if (!favoriteList.contains(city)) {
                            favoriteList.add(city);
                        } else {
                            System.out.println("city \"" + city + "\" already in the favorite list");
                        }
                    }
                }
                if (parser.getRemoveCities() != null) {
                    for (String city : parser.getRemoveCities()) {
                        if (!favoriteList.remove(city)) {
                            System.out.println("city \"" + city + "\" does not in the favorite list");
                        }
                    }
                }
                if (parser.isArgList()) {
                    for (String city : favoriteList) {
                        System.out.println(city);
                    }
                }
                if (parser.getRequestCities() != null) {
                    if (cityNameList == null) {
                        cityNameList = loadCityNameList();
                    }
                    for (String city : parser.getRequestCities()) {
                        if (!cityNameList.contains(city)) {
                            System.out.println("\"" + city + "\" does not in the dataset");
                            continue;
                        }
                        if (parser.isArgDetail()) {
                            System.out.println(tree.getDetailInfo(city));
                        } else {
                            System.out.println(tree.getImportantInfo(city));
                        }
                    }
                }
                if (parser.getSearchCity() != null) {
                    Iterator<String> iter;
                    if (cityNameList == null) {
                        cityNameList = loadCityNameList();
                    }
                    if (parser.getSearchCityNation() != null) {
                        iter = cityNameList.search(parser.getSearchCity(), parser.getSearchCityNation());
                    } else {
                        iter = cityNameList.search(parser.getSearchCity());
                    }

                    loop: while (true) {
                        for (int i = 0; i < 10; i++) {
                            if (iter.hasNext()) {
                                System.out.println(iter.next());
                            } else {
                                break loop;
                            }
                        }
                        Scanner scanner = new Scanner(System.in);
                        if (!scanner.nextLine().trim().equals("")) {
                            break;
                        }
                    }
                }
                if (parser.isArgCleanCache()) {
                    deleteFile(weatherDataCache);
                    deleteFile(cityDataCache);
                    deleteFile(favoriteListCache);
                } else {
                    writeWeatherTree(tree);
                    if (parser.getRemoveCities() != null || parser.getAddCities() != null) {
                        writeFavoriteList(favoriteList);
                    }
                }
            }
        }
    }
}
