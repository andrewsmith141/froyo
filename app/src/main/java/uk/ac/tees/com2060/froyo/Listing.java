package uk.ac.tees.com2060.froyo;

/**
 * Created by s6090581 on 02/05/18.
 */

public class Listing {

    /* Things that make up a listing
    /   User ID             -> String
    /   Item name           -> String
    /   Item size           -> Enumarable (Small, Medium, Large)
    /   Item Weight         -> Double
    /   Pickup Location     -> TBD
    /   General Location    -> String
    /   Dropoff Location    -> TBD
    /   Distance            -> Double
    /   Image of item       -> TBD
    */

    private String id;
    private String itemName;
    private String fromLocation;
    private String toLocation;
    private double weight;

    public Listing(){}

    public Listing(String id, String itemName, String fromLocation, String toLocation,
                   double weight){
        this.id = id;
        this.itemName = itemName;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.weight = weight;
    }

    public String getId(){return this.id;}
    public String getName() {return this.itemName;}
    public double getWeight() {return this.weight;}


}
