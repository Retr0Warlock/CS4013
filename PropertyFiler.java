import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Used for reading, writing, searching and general processing of the csv files.
 */
public class PropertyFiler {
    private final String propertyFileName = "properties.csv";

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
            BufferedWriter writer = new BufferedWriter(new FileWriter(propertyFileName, true));
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
            Scanner fileRead = new Scanner(new File(propertyFileName));
            while (fileRead.hasNextLine()) {
                propertyList.add(new Property(fileRead.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return propertyList;
    }
}
