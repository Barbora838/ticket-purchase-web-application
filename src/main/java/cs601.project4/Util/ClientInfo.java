package cs601.project4.Util;


/**
 * A class to maintain info about each client.
 */
public class ClientInfo {

    private String firstName;
    private String lastName;
    private String email;

    /**
     * Constructor
     * @param firstname
     * @param lastname
     */
    public ClientInfo(String firstname, String lastname, String email) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
    }

    public ClientInfo(String email) {
        this.firstName = "";
        this.lastName = "";
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * return name
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * return email
     * @return
     */
    public String getEmail() {
        return email;
    }
}
