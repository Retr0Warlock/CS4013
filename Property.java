import java.io.InvalidObjectException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A property contains an address,eircode,marketValue,category,boolean if its a private residence and property tax info
 * on a yearly basis.
 */
public class Property {
    private ArrayList<Owner> owners = new ArrayList<Owner>();
    private Address address;
    private String eircode;
    private double marketVal;
    private String category;
    public static final String[] categoryList = {"City", "Large town", "Small town", "Village", "Countryside"};
    private boolean isPrivateRes;
    private ArrayList<PropertyTax> propertyTaxes = new ArrayList<PropertyTax>();

    public Property(ArrayList<Owner> owners, Address address, String eircode, double marketVal, String category, boolean isPrivateRes) throws InvalidObjectException {
        this.owners = owners;
        this.address = address;
        this.eircode = eircode.trim().toUpperCase();
        this.marketVal = marketVal;
        this.category = category;
        this.isPrivateRes = isPrivateRes;
        if (toString().split(",").length != 10 + owners.size() || toString().contains("\\"))
            throw new InvalidObjectException("Data fields must not contain commas or backslashes");
        propertyTaxes.add(new PropertyTax(Year.now(), calcPropertyTax()));
    }

    /**
     * Constructor for a String in the same format as the toString method.
     *
     * @param o String "no.of owners,owners comma separated,address comma separated(4 lines),eircode,marketvalue,category,is property a private residence(true/false),PropertyTax info...)"
     */
    public Property(String o) {
        String[] oArr = o.split(",");
        for (String owner : Arrays.copyOfRange(oArr, 1, Integer.parseInt(oArr[0]) + 1))
            owners.add(new Owner(owner));
        this.address = new Address(Arrays.copyOfRange(oArr, 1 + owners.size(), 6 + owners.size()));
        this.eircode = oArr[6 + owners.size()];
        this.marketVal = Double.parseDouble(oArr[7 + owners.size()]);
        this.category = oArr[8 + owners.size()];
        this.isPrivateRes = Boolean.parseBoolean(oArr[9 + owners.size()]);
        String[] temp;
        for (int i = 10 + owners.size(); i < oArr.length; ) {
            temp = Arrays.copyOfRange(oArr, i + 2, Integer.parseInt(oArr[i + 1]) + i + 2);
            propertyTaxes.add(new PropertyTax(Year.parse(oArr[i]), temp, Double.parseDouble(oArr[i + temp.length + 2])));
            i += temp.length + 3;
        }
    }

    public ArrayList<Owner> getOwners() {
        return owners;
    }

    /**
     * @return ArrayList of PropertyTax for every year property was registered.
     */
    public ArrayList<PropertyTax> getPropertyTaxes() {
        return propertyTaxes;
    }

    /**
     * returns the propertyTax of the year supplied
     * @param year the year of the property tax
     * @return the property tax of the given year
     */
    public PropertyTax getPropertyTax(Year year) {
        for (PropertyTax tax : propertyTaxes)
            if (tax.getYear().equals(year))
                return tax;
        return null;
    }

    /**
     * updates the tax, to be called on January 1st every year
     */
    public void updateTax(){
        if(!getPropertyTax(Year.now()).equals(null))
            propertyTaxes.add(new PropertyTax(Year.now(),calcPropertyTax()));
        for(PropertyTax propertyTax:propertyTaxes)
            if(propertyTax.getTax()-propertyTax.getPaymentTotal()!=0)
                propertyTax.compoundTax();
    }

    /**
     * @return the 3 character routing key associated with the property
     */
    public String getRoutingKey() {
        return eircode.substring(0, 3);
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

    /**
     * Returns total tax due for this property
     */
    public double getTaxDue() {
        double result = 0;
        for (PropertyTax taxes : propertyTaxes)
            result += taxes.getTax() - taxes.getPaymentTotal();
        return result;
    }

    /**
     * Returns a string formatted in more human readable format but with less info that toString()
     * @return human readable string containing property info
     */
    public String generalString() {
        return address.toString().replaceAll(",", "\n") + "\n" + eircode + "\n" +
                "MarketValue: " + marketVal + "\n" + "Private Residence: " + isPrivateRes + "\n" + "Category: " + category + "\nTax due: " + getTaxDue();
    }

    /**
     * Returns comma separated info relating to the property
     * @return "how many owners,ownername1,ownername2...,addressline1,...,addressline4,eircode,marketVal,category,isPrivateResidece(true/false),tax year,ammount of payments,total tax for that year,tax year2...
     */
    @Override
    public String toString() {
        String result = owners.size() + ",";
        for (Owner owner : owners)
            result += owner.toString() + ",";
        result += address.toString() + "," + eircode + "," + marketVal + "," + category + "," + isPrivateRes;
        for (PropertyTax propertyTax : propertyTaxes)
            result += "," + propertyTax.toString();
        return result;
    }
}
