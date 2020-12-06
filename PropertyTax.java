/**
 * Used for calculating property tax of a given property.
 */
public class PropertyTax {
    private Property property;

    public PropertyTax(Property o) {
        this.property = o;
    }

    public double calculate() {
        double propertyTax = 0;
        propertyTax = propertyTax + 100 + rates() + categories();
        if (!property.isPrivateRes()) {
            propertyTax = propertyTax + 100;
        }
        return propertyTax;
    }

    /* Calculates rate for property tax */
    private double rates() {
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
    private double categories() {
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

}
