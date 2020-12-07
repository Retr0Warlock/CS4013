import java.io.InvalidObjectException;
import java.util.Arrays;

public class Property {
    private Owner owner;
    private Address address;
    private String eircode;
    private double marketVal;
    private String category;
    public static final String[] categoryList={"City","Large town","Small town","Village","Countryside"};
    private boolean isPrivateRes;
    private double propertyTax;
    private PropertyTax taxRule;

    /* Constructs a Product Object */
    public Property(Owner owner, Address address, String eircode, double marketVal, String category, boolean isPrivateRes) throws InvalidObjectException {
        this.owner = owner;
        this.address = address;
        this.eircode = eircode;
        this.marketVal = marketVal;
        this.category = category;
        this.isPrivateRes = isPrivateRes;
        this.taxRule = new PropertyTax(this);

        if (toString().split(",").length != 10 || toString().contains("\\"))
            throw new InvalidObjectException("Data fields must not contain commas or backslashes");
    }

    /**
     * Constructor for a String in the same format as the toString method.
     *
     * @param o String "owner,4 line address comma separated,eircode,marketvalue,catergory,is property a private residence(true/false)"
     */
    public Property(String o) {
        String[] oArr = o.split(",");
        this.owner = new Owner(oArr[0]);
        this.address = new Address(Arrays.copyOfRange(oArr, 1, 6));
        this.eircode = oArr[6];
        this.marketVal = Double.parseDouble(oArr[7]);
        this.category = oArr[8];
        this.isPrivateRes = Boolean.parseBoolean(oArr[9]);
    }

    /* Returns owner */
    public Owner getOwner() {
        return owner;
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

    /* Returns propertyTax */
    public double getPropertyTax() {
        return propertyTax;
    }

    /* Calculates the property tax */
    private void calcPropertyTax() {
        taxRule.calculate();
    }

    @Override
    public String toString() {
        return owner.toString() + "," + address.toString() + "," + eircode + "," + marketVal + "," + category + "," + isPrivateRes;
    }
}
