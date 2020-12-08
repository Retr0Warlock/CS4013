public class Property_Tax_Data {
    private String eircode;
    private int year;
    private double propertyTax;
    private boolean paidDue;

    public Property_Tax_Data(String eircode, int year, double propertyTax, boolean paid) {
        this.eircode = eircode;
        this.year = year;
        this.propertyTax = propertyTax;
        this.paidDue = paid;
    }

    public Property_Tax_Data(String eircode,int year,double propertyTax, String paid){
        if(paid.compareToIgnoreCase("Not Paid") == 0){
            this.eircode = eircode;
            this.year = year;
            this.propertyTax = propertyTax;
            this.paidDue = false;
        }else{
            this.eircode = eircode;
            this.year = year;
            this.propertyTax = propertyTax;
            this.paidDue = true;
        }
    }

    public boolean equals(String eircode){
        return eircode.compareToIgnoreCase(this.eircode) == 0;
    }

    public boolean equalRoute(String route){
        return route.compareToIgnoreCase(getRouting()) == 0;
    }
    
    public int getYear() {
        return year;
    }
    
    private String getRouting(){
        return eircode.substring(0,3);
    }

    public String toStringMore(){
        return eircode + " " + propertyTax + " was not paid.";
    }

    public double getPropertyTax() {
        return propertyTax;
    }
    
    public boolean overdue(String routing){
        if(equalRoute(routing)){
            return !paidDue;
        }
        return false;
    }
        
    public boolean overdue(int year){
        if(year == this.year){
            return !paidDue;
        }
        return false;
    }
    
    @Override
    public String toString() {
        if(paidDue){
            return year + ",  " + propertyTax + " was paid";
        }else{
            return year + ",  " + propertyTax + " was not paid";
        }
    }
}
//volvunt ad inceptum