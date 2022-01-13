package cs601.project4.MySQL;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * A utility class.
 */
public class Utilities {

    public static final String configFileName = "configDB.json";

    /**
     * Read in the configuration file.
     * @return
     */
    public static Config readConfig() {

        Config config = null;
        Gson gson = new Gson();
        try {
            config = gson.fromJson(new FileReader(configFileName), Config.class);
        } catch (FileNotFoundException e) {
            System.err.println("Config file configDB.json not found: " + e.getMessage());
        }
        return config;
    }
}