import java.io.*;

public class CSV_Storing {
    private String file = "file.csv";

    public void add(Property prop) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(prop.toString() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties read() {
        String line = "";
        Properties prop = new Properties();
        try {
            BufferedReader Buffer = new BufferedReader(new FileReader(file));
            while ((line = Buffer.readLine()) != null) {
                String[] values = line.split(",");
                String[] names = values[0].split(" ");
                String[] address = values[1].split(" ");
                Owner o = new Owner(names[0]);
                Address a = new Address(address[0], address[1], address[2], address[3], address[4]);
                double MarketVal = Double.parseDouble(values[3]);
                boolean privateResidence = Boolean.parseBoolean(values[5]);
                Property p = new Property(o, a, values[2], MarketVal, values[4], privateResidence);
                prop.addProperty(p);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
