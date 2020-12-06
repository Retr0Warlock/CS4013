import java.io.InvalidObjectException;
import java.util.Arrays;

public class Property {
    private Owner owner;
    private Address address;
    private String eircode;
    private double marketVal;
    private String category;
    private boolean isPrivateRes;
    private double propertyTax;

    /* Constructs a Product Object */
    public Property(Owner owner, Address address, String eircode, double marketVal, String category, boolean isPrivateRes) throws InvalidObjectException {
        this.owner = owner;
        this.address = address;
        this.eircode = eircode;
        this.marketVal = marketVal;
        this.category = category;
        this.isPrivateRes = isPrivateRes;

        if(toString().split(",").length!=10||toString().contains("\\"))
            throw new InvalidObjectException("Data fields must not contain commas or backslashes");
    }

    /**
     * Constructor for a String in the same format as the toString method.
     * @param o String "owner,4 line address comma separated,eircode,marketvalue,catergory,is property a private residence(true/false)"
     */
    public Property(String o){
        String[] oArr = o.split(",");
        this.owner = new Owner(oArr[0]);
        this.address = new Address(Arrays.copyOfRange(oArr,1,6));
        this.eircode = oArr[7];
        this.marketVal = Double.parseDouble(oArr[8]);
        this.category = oArr[9];
        this.isPrivateRes = Boolean.parseBoolean(oArr[10]);
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
    private void calcPropertyTax(){
        propertyTax = propertyTax + 100 + Rates() + Categories();
        if(!isPrivateRes){
            propertyTax = propertyTax  + 100;
        }
    }

    /* Calculates rate for property tax */
    private double Rates(){
        double PropertyTax = 0;
        int[] Values = {150_000,400_000,650_000};
        double[] Rates = {0,0.01,0.02,0.04};
        for(int i = 0; i < Values.length;i++){
            if(marketVal <= Values[i]){
                PropertyTax = PropertyTax + marketVal *Rates[i]/100;
                return PropertyTax;
            }else if(marketVal > 650000){
                PropertyTax = PropertyTax + marketVal *0.04/100;
                return PropertyTax;
            }
        }
        return PropertyTax;
    }

    /* Calculates property tax based off of category */
    private double Categories(){
        double PropertyTax = 0;
        String[] categories = {"City","Large Town","Small Town","Village","Countryside"};
        int[] charge = {100,80,60,50,25};
        for(int i = 0; i< categories.length;i++){
            if(category.equalsIgnoreCase(categories[i])){
                PropertyTax = PropertyTax + charge[i];
            }
        }
        return PropertyTax;
    }

    @Override
    public String toString() {
        return owner.toString() + "," + address.toString() + "," + eircode + "," + marketVal + "," + category + "," + isPrivateRes;
    }
}
