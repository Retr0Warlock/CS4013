public class Property {
    private Owners owners;
    private Address address;
    private String eircode;
    private double MarketVal;
    private String category;
    private boolean privateRes;
    private double propertyTax;

    /* Constructs a Product Object */
    public Property(Owners owners, Address address, String eircode, double MarketVal, String category, boolean privateRes) {
        this.owners = owners;
        this.address = address;
        this.eircode = eircode;
        this.MarketVal = MarketVal;
        this.category = category;
        this.privateRes = privateRes;
        calcPropertyTax();
    }

    /* Returns owners */
    public Owners getOwners() {
        return owners;
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
        return MarketVal;
    }

    /* Returns category */
    public String getCategory() {
        return category;
    }

    /* Returns privateResidence */
    public boolean isPrivateRes() {
        return privateRes;
    }

    /* Returns propertyTax */
    public double getPropertyTax() {
        return propertyTax;
    }

    /* Calculates the property tax */
    private void calcPropertyTax(){
        propertyTax = propertyTax + 100 + Rates() + Categories();
        if(!privateRes){
            propertyTax = propertyTax  + 100;
        }
    }

    /* Calculates rate for property tax */
    private double Rates(){
        double PropertyTax = 0;
        int[] Values = {150_000,400_000,650_000};
        double[] Rates = {0,0.01,0.02,0.04};
        for(int i = 0; i < Values.length;i++){
            if(MarketVal <= Values[i]){
                PropertyTax = PropertyTax + MarketVal*Rates[i]/100;
                return PropertyTax;
            }else if(MarketVal > 650000){
                PropertyTax = PropertyTax + MarketVal*0.04/100;
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
        return owners.toString() + " " + address.toString() + " " + eircode + " " + MarketVal + " " + category + " " + propertyTax;
    }
}
