package cs601.project4.MySQL;

/**
 * tunnel
 * ssh -L 3300:sql.cs.usfca.edu:3306 bnovakova@stargate.cs.usfca.edu
 *
 * on local
 * mysql -h 127.0.0.1 -u user007 -p
 */



/**
 * A class to store the properties of the JSON configuration file.
 */
public class Config {

    private String database;
    private String username;
    private String password;

    /**
     * Config class constructor.
     * @param database
     * @param username
     * @param password
     */
    public Config(String database, String username, String password) {
        this.database = database;
        this.username = username;
        this.password = password;
    }

    /**
     * Return the database property.
     * @return
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Return the username property.
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Return the password property.
     * @return
     */
    public String getPassword() {
        return password;
    }
}
