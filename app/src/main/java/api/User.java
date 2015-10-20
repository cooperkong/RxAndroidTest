package api;

/**
 * Created by kongw1 on 5/10/15.
 */
public class User {
    public String login;
    public double id;

    public final String firstName;
    public final String lastName;
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
