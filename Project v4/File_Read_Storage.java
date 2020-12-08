import java.io.*;
public class File_Read_Storage {
    private String file = "CSV/CSV.txt";
     private final String csv = "CSV/properties.csv";
    /* Adds property data to CSV.txt file */
    public void add(Property p){
        try {
            FileWriter filewrite = new FileWriter(file,true);
            BufferedWriter buffwrite = new BufferedWriter(filewrite);
            PrintWriter printwrite = new PrintWriter(buffwrite);
            printwrite.println(p.getOwner() + "," + p.getAddress() + "," +
                    p.getEircode() + "," + p.getMarketVal() + "," +
                    p.getCategory() + "," + p.isPrivateRes());
            printwrite.flush();
            printwrite.close();

        }catch (Exception E){
            System.out.println("Error writing to csv");
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(csv, true));
            writer.write(p.toString() + "\n\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Reads all data in the CSV.txt file and returns it as an object of type properties*/
    public Properties read(){
        String string = "";
        Properties prop = new Properties();
        try{
            BufferedReader buffread = new BufferedReader(new FileReader(file));
            while ((string = buffread.readLine()) != null){
                String[] section = string.split(",");
                String[] Name = section[0].split(" ");
                Owner o = new Owner(Name[0],Name[1]);
                double MarketVal = Double.parseDouble(section[3]);
                boolean privateRes = Boolean.parseBoolean(section[5]);
                Property p = new Property(o,section[1],section[2],MarketVal,section[4],privateRes);
                prop.addProperty(p);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return prop;
    }
}
