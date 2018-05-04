package uk.ac.tees.com2060.froyo;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User
{

    private String id, name, email;
    private boolean courier;

    public User(String id, String name, String email, boolean courier)
    {
        this.id = id;
        this.email = email;
        this.name = name;
        this.courier = courier;
    }

    public User(String id, String name, String email){
        this.id = id;
        this.email = email;
        this.name = name;
        this.courier = false;
    }

    public String getId() { return this.id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public boolean getCourier() { return this.courier; }
    public void setCourier(boolean courier) { this.courier = courier; }

}