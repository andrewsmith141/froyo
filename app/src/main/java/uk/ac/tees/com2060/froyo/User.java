package uk.ac.tees.com2060.froyo;

public class User
{

    private String id, name;
    private boolean courier;

    public User(String id, String name, boolean courier)
    {
        this.id = id;
        this.name = name;
        this.courier = courier;
    }

    public String getId() { return this.id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public boolean getCourier() { return this.courier; }
    public void setCourier(boolean courier) { this.courier = courier; }

}