import java.io.InvalidObjectException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;

public class Property {
    private ArrayList<Owner> owners=new ArrayList<Owner>();
    private Address address;
    private String eircode;
    private double marketVal;
    private String category;
    public static final String[] categoryList = {"City", "Large town", "Small town", "Village", "Countryside"};
    private boolean isPrivateRes;
    private ArrayList<PropertyTax> propertyTaxes = new ArrayList<PropertyTax>();

    public Property(ArrayList<Owner> owners, Address address, String eircode, double marketVal, String category, boolean isPrivateRes) throws InvalidObjectException {
        this.owners=owners;
        this.address = address;
        this.eircode = eircode;
        this.marketVal = marketVal;
        this.category = category;
        this.isPrivateRes = isPrivateRes;
        if (toString().split(",").length != 10+owners.size() || toString().contains("\\"))
            throw new InvalidObjectException("Data fields must not contain commas or backslashes");
        propertyTaxes.add(new PropertyTax(Year.now(), calcPropertyTax()));
    }

    /**
     * Constructor for a String in the same format as the toString method.
     *
     * @param o String "no.ofowners,owners...,address...,eircode,marketvalue,category,is property a private residence(true/false,PropertyTax.toString())"
     */
    public Property(String o) {
        String[] oArr = o.split(",");
        for(String owner:Arrays.copyOfRange(oArr,1,Integer.parseInt(oArr[0])+1))
            owners.add(new Owner(owner));
        this.address = new Address(Arrays.copyOfRange(oArr, 1+owners.size(), 6+owners.size()));
        this.eircode = oArr[6+owners.size()];
        this.marketVal = Double.parseDouble(oArr[7+owners.size()]);
        this.category = oArr[8+owners.size()];
        this.isPrivateRes = Boolean.parseBoolean(oArr[9+owners.size()]);
        String[] temp;
        for (int i = 10+owners.size(); i < oArr.length; ) {
            temp = Arrays.copyOfRange(oArr, i + 2, Integer.parseInt(oArr[i + 1]) + i + 2);
            propertyTaxes.add(new PropertyTax(Year.parse(oArr[i]), temp, Double.parseDouble(oArr[i + temp.length + 2])));
            i += temp.length + 3;
        }
    }

    /* Returns address */
    public Address getAddress() {
        return address;
    }

    /* Returns eircode */
    public String getEircode() {
        return eircode;
    }

    /* Returns MarketVal */
    public double getMarketVal() {
        return marketVal;
    }

    /* Returns category */
    public String getCategory() {
        return category;
    }

    /* Returns privateResidence */
    public boolean isPrivateRes() {
        return isPrivateRes;
    }

    /* Calculates the property tax */
    private double calcPropertyTax() {
        return PropertyTax.calculate(this);
    }

    @Override
    public boolean equals(Object prop) {
        return toString().equals(prop.toString());
    }

    @Override
    public String toString() {
        String result=owners.size()+",";
        for(Owner owner:owners)
            result+=owner.toString()+",";
        result += address.toString() + "," + eircode + "," + marketVal + "," + category + "," + isPrivateRes;
        for (PropertyTax propertyTax : propertyTaxes)
            result += "," + propertyTax.toString();
        return result;
    }
}
