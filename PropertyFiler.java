import java.io.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Used for reading, writing, searching and general processing of the csv files.
 */
public class PropertyFiler {
    private final String file = "properties.csv";

    /**
     * Logs a String representation of a Property to file
     *
     * @param prop the property to be logged to file
     */
    public void add(Property prop) {
        for (Property prop2 : read()) //if duplicate property already in file
            if (prop.equals(prop2))
                return;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(prop.toString() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all properties on file
     *
     * @return ArrayList of properties on file
     */
    public ArrayList<Property> read() {
        ArrayList<Property> propertyList = new ArrayList<Property>();
        try {
            Scanner fileRead = new Scanner(new File(file));
            while (fileRead.hasNextLine()) {
                propertyList.add(new Property(fileRead.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return propertyList;
    }

    public ArrayList<Property> search(Owner owner) {
        ArrayList<Property> result = new ArrayList<Property>();
        for (Property prop : read()) {
            for (Owner owners : prop.getOwners())
                if (owners.equals(owner)) {
                    result.add(prop);
                    break;
                }
        }
        return result;
    }

    public void rewrite(ArrayList<Property> properties) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Property prop : properties)
                writer.write(prop.toString() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeTaxPayment(Property prop, PropertyTax taxYear, double payment) {
        ArrayList<Property> store = read();
        for (Property property : store)
            if (prop.equals(property))
                for (PropertyTax tax : property.getPropertyTaxes())
                    if (tax.equals(taxYear))
                        tax.makePayment(payment);
        rewrite(store);
    }

    public ArrayList<Property> search(Address address) {
        ArrayList<Property> result = new ArrayList<>();
        for (Property prop : read())
            if (prop.getAddress().equals(address))
                result.add(prop);
        return result;
    }

    public ArrayList<Property> search(String routingKey) {
        if (routingKey.equals(""))
            return read();
        ArrayList<Property> result = new ArrayList<>();
        for (Property prop : read())
            if (prop.getRoutingKey().equalsIgnoreCase(routingKey))
                result.add(prop);
        return result;
    }

}
