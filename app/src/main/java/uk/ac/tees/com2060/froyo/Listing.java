package uk.ac.tees.com2060.froyo;

/**
 * Listing data structure used to store listing data
 */
public class Listing {

    //  Attributes which are assigned to each of the values which are entered into the database
    private String id;
    private String itemName;
    private String fromLocation;
    private String toLocation;
    private double weight;

    public Listing(){}

    /**
     * Constructor for Listing which is used to create a new Listing
     *
     * @param id    User ID
     * @param itemName  Item name
     * @param fromLocation  Location item will be taken from
     * @param toLocation    Location item will be taken to
     * @param weight    Weight of item
     */
    public Listing(String id, String itemName, String fromLocation, String toLocation,
                   double weight){

        this.id = id;
        this.itemName = itemName;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.weight = weight;
    }

    /**
     * Get user ID
     *
     * @return id
     */
    public String getId(){return this.id;}

    /**
     * Get item name
     *
     * @return itemName
     */
    public String getName() {return this.itemName;}

    /**
     * Get item weight
     *
     * @return weight
     */
    public double getWeight() {return this.weight;}

    /**
     * Get pickup location
     *
     * @return toLocation
     */
    public String getToLocation() {return this.toLocation;}

    /**
     * Get dropoff location
     *
     * @return fromLocation
     */
    public String getFromLocation() {return this.fromLocation;}


}
