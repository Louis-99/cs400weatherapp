// --== CS400 File Header Information ==--
// Name: Yunzhao Liu
// Email: liu995@wisc.edu
// Team: JB
// Role: Front End Developer
// TA: Harper
// Lecturer: Florian Heimerl
// Notes to Grader: N/A
import java.util.ArrayList;

/**
 * command line argument parser for weather app.
 *
 * @author Yunzhao Liu
 */
public class ArgumentParser {
    private ArrayList<String> requestCities = null;
    private String searchCity = null;
    private String searchCityNation = null;
    private ArrayList<String> addCities = null;
    private ArrayList<String> removeCities = null;
    private boolean argDetail = false;
    private boolean argList = false;
    private boolean argHelp = false;
    private boolean argVersion = false;
    private boolean argCleanCache = false;
    private int index = 0;
    private String[] args;

    public ArgumentParser(String[] args) {
        this.args = args;
    }

    /**
     * get next string which may be around by quotes from args
     *
     * @return next string from args
     * @throws IllegalArgumentException
     */
    String getNextArgumentString() throws IllegalArgumentException {
        String arg = args[index++];
        if (arg.startsWith("\"")) {
            if (arg.endsWith("\"")) {
                return arg.substring(1, arg.length() - 1);
            }
            StringBuilder sb = new StringBuilder(arg.substring(1));
            while (index < args.length) {
                arg = args[index++];
                if (arg.endsWith("\"")) {
                    sb.append(' ');
                    sb.append(arg.substring(0, arg.length()));
                    return sb.toString().trim();
                }
                sb.append(arg);
            }
            throw new IllegalArgumentException("unpaired double quote");
        } else {
            return arg;
        }
    }

    /**
     * parser the args
     *
     * @throws IllegalArgumentException
     */
    void parseArgument() throws IllegalArgumentException {
        index = 0;
        while (index < args.length && !args[index].startsWith("-") && !args[index]
            .startsWith("\"-")) {
			if(requestCities == null){
				requestCities = new ArrayList<>();
			}
            requestCities.add(getNextArgumentString());
        }
        while (index < args.length) {
            String arg = getNextArgumentString();
            if (arg.startsWith("-")) {
                if (arg.equals("--help") || arg.equals("-h")) {
                    argHelp = true;
                } else if (arg.equals("--version") || arg.equals("-v")) {
                    argVersion = true;
                } else if (arg.equals("--list") || arg.equals("-l")) {
                    argList = true;
                } else if (arg.equals("--detail")) {
                    argDetail = true;
                } else if (arg.equals("--clean-cache")) {
                    argCleanCache = true;
                } else if (arg.equals("--add") || arg.equals("-a")) {
                    if (addCities == null)
                        addCities = new ArrayList<>();
                    while (index < args.length && !args[index].startsWith("-") && !args[index]
                        .startsWith("\"-")) {
                        addCities.add(getNextArgumentString());
                    }
                } else if (arg.equals("--remove") || arg.equals("-r")) {
                    if (removeCities == null)
                        removeCities = new ArrayList<>();
                    while (index < args.length && !args[index].startsWith("-") && !args[index]
                        .startsWith("\"-")) {
                        removeCities.add(getNextArgumentString());
                    }
                } else if (arg.equals("--search") || arg.equals("-s")) {
                    searchCity = getNextArgumentString();
                    if (index < args.length) {
                        arg = getNextArgumentString();
                        if (arg.startsWith("[") && arg.endsWith("]")) {
                            searchCityNation = arg.substring(1, arg.length() - 1);
                        } else {
                            searchCityNation = null;
                        }
                    }
                } else {
                    throw new IllegalArgumentException("wrong command: " + arg
                        + "\nuse --help to see the usage of this application");
                }
            } else {
                throw new IllegalArgumentException("unexpected argument: " + arg);
            }
        }
    }

    /**
     * get requested cities
     *
     * @return requested cities
     */
    public ArrayList<String> getRequestCities() {
        return requestCities;
    }

    /**
     * get searched city
     *
     * @return searched city
     */
    public String getSearchCity() {
        return searchCity;
    }

    /**
     * get searched city nation
     *
     * @return searched city nation
     */
    public String getSearchCityNation() {
        return searchCityNation;
    }

    /**
     * get the cities add to favorite list
     *
     * @return cities add to favorite list
     */
    public ArrayList<String> getAddCities() {
        return addCities;
    }

    /**
     * get the cities remove from favorite list
     *
     * @return cities remove from favorite list
     */
    public ArrayList<String> getRemoveCities() {
        return removeCities;
    }

    /**
     * whether have argument --detail
     *
     * @return true if have --detail
     */
    public boolean isArgDetail() {
        return argDetail;
    }

    /**
     * whether have argument --list or -l
     *
     * @return true if have --list or -l
     */
    public boolean isArgList() {
        return argList;
    }

    /**
     * whether have argument --help or -h
     *
     * @return true if have --help or -h
     */
    public boolean isArgHelp() {
        return argHelp;
    }

    /**
     * whether have argument --version or -v
     *
     * @return true if have --version or -v
     */
    public boolean isArgVersion() {
        return argVersion;
    }

    /**
     * whether have argument --clean-cache
     *
     * @return true if have --clean-cache
     */
    public boolean isArgCleanCache() {
        return argCleanCache;
    }

}
