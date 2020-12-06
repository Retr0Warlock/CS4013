import java.io.InvalidObjectException;
import java.util.Scanner;
public class StoreProperty {
    public void addPropertyValues(){
        Scanner scanner = new Scanner(System.in);
        
        //Full Name
        System.out.println("Fullname: ");
        String Fullname = scanner.nextLine();
        
        //Full Address
        System.out.println("Address \n FirstLine: ");
        String firstLine = scanner.nextLine();
        System.out.println("SecondLine: ");
        String secondLine = scanner.nextLine();
        System.out.println("City ");
        String city = scanner.nextLine();
        System.out.println("County: ");
        String county = scanner.nextLine();
        System.out.println("Country: ");
        String country = scanner.nextLine();
        
        //Eircode
        System.out.println("Eircode: ");
        String eircode = scanner.nextLine();

        //Market Value
        System.out.println("Market Value: ");
        double MarketValue = scanner.nextDouble();
        scanner.nextLine();
        
        //Catagory (City, Large Town, Small Town, Village, Countryside)
        System.out.println("Category: ");
        String category = scanner.nextLine();

        //Private Residence
        System.out.println("Private Residence: (Y/N) ");
        String privateResidence = scanner.nextLine();

        boolean privateRes;
        if(privateResidence.equalsIgnoreCase("Y")){
            privateRes = true;
        }else{
            privateRes = false;
        }
        
        //Creating a Property and adding it to the file.
        Property newProperty = null;
        try {
            newProperty = new Property(new Owner(Fullname),new Address(firstLine,secondLine,city,county,country),eircode,MarketValue,category,privateRes);
        } catch (InvalidObjectException e) {
            e.printStackTrace();
        }
        CSV_Storing Write = new CSV_Storing();
        Write.add(newProperty);
        Properties TempStorage = new Properties();
        TempStorage.addProperty(newProperty);
    }
}
