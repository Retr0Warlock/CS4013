public class Property {
    private Owner owners;
    private String address;
    private String eircode;
    private double MarketValue;
    private String category;
    private boolean privResidence;
    private double propertyTax;
    
    public Property(Owner owners, String address, String eircode, double MarketVal, String category, boolean privateRes) {
        //Constructs a Product Object
        this.owners = owners;
        this.address = address;
        this.eircode = eircode;
        this.MarketValue = MarketVal;
        this.category = category;
        this.privResidence = privateRes;
        calcPropertyTax();
    }

    public Owner getOwner() {
        //Returns owners
        return owners;
    }

    public String getAddress() {
        //Returns address
        return address;
    }

    public String getEircode() {
        //Returns eircode
        return eircode;
    }

    public double getMarketVal() {
        //Returns MarketVal
        return MarketValue;
    }

    public String getCategory() {
        //Returns category
        return category;
    }

    public boolean isPrivateRes() {
        //Returns privateRes
        return privResidence;
    }

    public double getPropertyTax() {
        //Returns propertyTax
        return propertyTax;
    }

    private void calcPropertyTax(){
        //Calculates the property tax
        propertyTax = propertyTax + 100 + Rates() + Categories();
        if(!privResidence){
            propertyTax = propertyTax  + 100;
        }
    }

    private double Rates(){
        //Calculates rate for property tax
        double PropertyTax = 0;
        int[] Values = {150_000,400_000,650_000};
        double[] Rates = {0,.01,.02,.04};
        for(int i = 0; i < Values.length;i++){
            if(MarketValue <= Values[i]){
                PropertyTax = PropertyTax + MarketValue*Rates[i]/100;
                return PropertyTax;
            }else if(MarketValue > 650000){
                PropertyTax = PropertyTax + MarketValue*.04/100;
                return PropertyTax;
            }
        }
        return PropertyTax;
    }

    private double Categories(){
        //Calculates property tax based off of category
        double PropertyTax = 0;
        String[] categories = {"City","Large Town","Small Town","Village","Countryside"};
        int[] charge = {100,80,60,50,25};
        for(int i = 0; i< categories.length;i++){
            if(category.compareToIgnoreCase(categories[i]) == 0){
                PropertyTax = PropertyTax + charge[i];
            }
        }
        return PropertyTax;
    }

    @Override
    public String toString() {
        return "Name : "+ owners.toString() + "\n" +"Address: "+ address + "\n"
                +"Eircode: "+ eircode + "\n"+ "Market Value: " + MarketValue + "\n"
                +"Category: "+ category + "\n" +
                "Property Tax: " + propertyTax + "\n";
    }
}
//volvunt ad inceptum
