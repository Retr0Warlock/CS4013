import java.time.Year;
import java.util.ArrayList;

/**
 * Holds property tax info for a given year. Static methods for calculating how much tax is owed on a property.
 */
public class PropertyTax {
    private double tax;
    private Year year;
    private ArrayList<Double> payments;

    public PropertyTax(Year year, double tax) {
        this.tax = tax;
        this.year = year;
        payments = new ArrayList<Double>();
    }

    public double getTax() {
        return tax;
    }

    public Year getYear() {
        return year;
    }

    /**
     * compound tax owed by seven percent
     */
    public void compoundTax(){
        if(tax-getPaymentTotal()!=0)
            tax=tax*1.07;
    }

    /**
     * @param tax the tax to be compared
     * @return if a propery tax info matches the same year, tax payments made and total tax owed returns true
     */
    public boolean equals(PropertyTax tax){
        return toString().equals(tax.toString());
    }

    public PropertyTax(Year year, String[] subArr, double tax) {
        this.year = year;
        this.payments = new ArrayList<Double>();
        for (String payment : subArr)
            payments.add(Double.parseDouble(payment));
        this.tax = tax;
    }

    /**
     * calculates the tax owed for a given property. Used rates() and categories().
     * @param property the property tax to be taxed
     * @return the amount of tax owed on that property
     */
    public static double calculate(Property property) {
        double propertyTax = 0;
        propertyTax = propertyTax + 100 + rates(property) + categories(property);
        if (!property.isPrivateRes()) {
            propertyTax = propertyTax + 100;
        }
        return propertyTax;
    }

    /* Calculates rate for property tax */
    private static double rates(Property property) {
        double PropertyTax = 0;
        int[] values = {150_000, 400_000, 650_000};
        double[] rates = {0, 0.01, 0.02, 0.04};
        for (int i = 0; i < values.length; i++) {
            if (property.getMarketVal() <= values[i]) {
                PropertyTax = PropertyTax + property.getMarketVal() * rates[i] / 100;
                return PropertyTax;
            } else if (property.getMarketVal() > 650000) {
                PropertyTax = PropertyTax + property.getMarketVal() * 0.04 / 100;
                return PropertyTax;
            }
        }
        return PropertyTax;
    }

    /* Calculates property tax based off of category */
    private static double categories(Property property) {
        double PropertyTax = 0;
        String[] categories = {"City", "Large Town", "Small Town", "Village", "Countryside"};
        int[] charge = {100, 80, 60, 50, 25};
        for (int i = 0; i < categories.length; i++) {
            if (property.getCategory().equalsIgnoreCase(categories[i])) {
                PropertyTax = PropertyTax + charge[i];
            }
        }
        return PropertyTax;
    }

    public String getSummary(){
        return year+": "+(tax-getPaymentTotal())+" due of "+tax+" total.";
    }

    public void makePayment(double payment) throws IllegalArgumentException {
        if (payment > tax - getPaymentTotal())
            throw new IllegalArgumentException("Payment is over total due");
        if (payment < 0)
            throw new IllegalArgumentException("Payment must be more than 0");
        payments.add(payment);
    }

    public String getPaymentsString(){
        String result="";
        for(Double payment:payments)
            result+=payment+",";
        return result;
    }

    public double getPaymentTotal() {
        double result = 0;
        for (double payment : payments)
            result += payment;
        return result;
    }

    @Override
    public String toString() {
        String result = year.toString() + "," + payments.size() + ",";
        for (double payment : payments)
            result += payment + ",";
        result += tax;
        return result;
    }
}
