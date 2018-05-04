package uk.ac.tees.com2060.froyo;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * User data structure used for storing user data
 */
@IgnoreExtraProperties
public class User
{

    private String id, name, email;
    private boolean courier;

    /**
     * Constructor used to create a new user
     *
     * @param id    User ID
     * @param name  User name
     * @param email User email
     * @param courier   Boolean variable representing whether user is a courier or not
     *
     */
    public User(String id, String name, String email, boolean courier)
    {
        this.id = id;
        this.email = email;
        this.name = name;
        this.courier = courier;
    }

    /**
     * Extra constructor for creating a user without being given a boolean
     * Users signed up like this are not signed up as couriers
     *
     * @param id    User ID
     * @param name  User name
     * @param email User email
     */
    public User(String id, String name, String email){
        this.id = id;
        this.email = email;
        this.name = name;
        this.courier = false;
    }

    /**
     * Get User ID
     *
     * @return id
     */
    public String getId() { return this.id; }

    /**
     * Set User ID
     *
     * @param id User ID
     */
    public void setId(String id) { this.id = id; }

    /**
     * Get User name
     *
     * @return name
     */
    public String getName() { return this.name; }

    /**
     * Set User name
     *
     * @param name User name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Get User email
     *
     * @return email
     */
    public String getEmail() { return this.email; }

    /**
     * Set User email
     *
     * @param email User email
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Get courier value
     *
     * @return courier
     */
    public boolean getCourier() { return this.courier; }

    /**
     * Set courier value
     * @param courier Courier
     */
    public void setCourier(boolean courier) { this.courier = courier; }

}