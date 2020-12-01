import java.io.*;
public class CSV_Storing {
    private String file = "file.csv";
    public void add(Property prop){
        try {
            FileWriter File = new FileWriter(file,true);
            BufferedWriter Buffer = new BufferedWriter(File);
            PrintWriter Print = new PrintWriter(Buffer);
            Print.println(prop.getOwners() + "," + prop.getAddress() + "," + prop.getEircode() + "," + prop.getMarketVal() + "," + prop.getCategory() + "," + prop.isPrivateRes() + "," + prop.getPropertyTax());
            Print.flush();
            Print.close();
        }catch (Exception E){
            System.out.println("Error writing to csv");
        }
    }

    public Properties read(){
        String line = "";
        Properties prop = new Properties();
        try{
            BufferedReader Buffer = new BufferedReader(new FileReader(file));
            while ((line = Buffer.readLine()) != null){
                String[] values = line.split(",");
                String[] names = values[0].split(" ");
                String[] address = values[1].split(" ");
                Owners o = new Owners(names[0]);
                Address a = new Address(address[0],address[1],address[2],address[3],address[4]);
                double MarketVal = Double.parseDouble(values[3]);
                boolean privateResidence = Boolean.parseBoolean(values[5]);
                Property p = new Property(o,a,values[2],MarketVal,values[4],privateResidence);
                prop.addProperty(p);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return prop;
    }
}
