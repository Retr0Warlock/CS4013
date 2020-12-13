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

    /**
     * Searches the file for a given property owner and returns all properties owned
     * @param owner the owner to be searched
     * @return the ArrayList of properties owned by owner
     */
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

    /**
     * rewrites the file with new list of properties
     * @param properties the list of new properties that will overwrite the file
     */
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

    /**
     * Allows a tax payment to be made for a given year on a given property.
     * @param prop the property that is taxed
     * @param taxYear the tax year to be paid
     * @param payment the payment amount
     * @throws IllegalArgumentException if payment amount is invalid
     */
    public void makeTaxPayment(Property prop, PropertyTax taxYear, double payment) throws IllegalArgumentException{
        ArrayList<Property> store = read();
        for (Property property : store)
            if (prop.toString().equals(property.toString()))
                for (PropertyTax tax : property.getPropertyTaxes())
                    if (tax.equals(taxYear))
                        tax.makePayment(payment);
        rewrite(store);
    }

    /**
     * searches file for properties under a given address
     * @param address the given address
     * @return List of properties under an address
     */
    public ArrayList<Property> search(Address address) {
        ArrayList<Property> result = new ArrayList<>();
        for (Property prop : read())
            if (prop.getAddress().equals(address))
                result.add(prop);
        return result;
    }

    /**
     * searches all properties on file that have a given routing key
     * @param routingKey the routing key to be searched
     * @return list of properties on file that have the same routing key
     */
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
