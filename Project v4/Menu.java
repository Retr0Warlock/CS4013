import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private Properties properties = new Properties();
    private Property_Tax Tax = new Property_Tax();
    Scanner input = new Scanner(System.in);
    public void start(){
        File_Read_Storage File_Read_Storage = new File_Read_Storage();
        properties.addProperties(File_Read_Storage.read());
        boolean quit = true;
        while (quit){
            System.out.println("1) My Properties / 2) Add Property / 3) Property Tax Info / 4) Available Properties / 5) Exit");
            int option = input.nextInt();
            switch (option){
                case 1:
                    byName();
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
                    break;
                case 2:
                    addProperty();
                    break;
                case 3:
                    PropertyTaxInfo();
                    break;
                case 4:
                    ArrayList<String> s = properties.getPropertiesByName();
                    for(String name : s) {
                        System.out.println(name);
                    }
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
                    break;
                case 5:
                    quit = false;
                    break;

            }
        }
    }
    
    private String category(){
        String[] categories = {"City","Large Town","Small Town","Village","Countryside"};
        int i = 0;
        for(String a : categories){
            i++;
            System.out.println(i+ ")" + a);
        }
        System.out.println("Choose a category: ");
        int choice = input.nextInt();
        return categories[choice-1];
    }
    
    private void byName(){
        input.nextLine();
        System.out.println("First name: ");
        String firstname = input.nextLine();
        System.out.println("Last name: ");
        String lastname = input.nextLine();
        ArrayList<Property> searchByName;
        searchByName = properties.searchForOwner(new Owner(firstname,lastname));
        if(searchByName.size() == 0){
            System.out.println("404 Name not found");
            return;
        }else{
            Property choice = choice(searchByName);
            ArrayList<Property_Tax_Data> tx = Tax.findByProperty(choice);
            for(Property_Tax_Data t:tx){
                System.out.println(t);
            }
        }
    }
    
    private void addProperty(){
        input.nextLine();
        System.out.println("Firstname: ");
        String first = input.nextLine();

        System.out.println("Lastname: ");
        String last = input.nextLine();

        System.out.println("Full Address: ");
        String address = input.nextLine();

        System.out.println("Eircode: ");
        String eircode = input.nextLine();

        System.out.println("Market Value: ");
        double MarketValue = input.nextDouble();
        input.nextLine();
        String category = category();
        input.nextLine();
        System.out.println("Private Residence: (Yes/No) ");
        String privateResidence = input.nextLine();

        boolean privateRes;
        if(privateResidence.compareToIgnoreCase("Yes") ==0){
            privateRes = true;
        }else{
            privateRes = false;
        }
        Property name = new Property(new Owner(first,last),address,
                eircode,MarketValue,category,privateRes);
        File_Read_Storage write = new File_Read_Storage();
        write.add(name);
        properties.addProperty(name);
        Tax.addPropertyTax(name);
    }

    private void PropertyTaxInfo(){
        boolean run = true;
        while(run){
            System.out.println("1) Property Tax by name / 2) All Property tax info / 3) Overdue Property tax / 4) Overdue Property tax (By routing key) / 5) Property tax statistics (By routing key) / 6) Quit");
            int choice = input.nextInt();
            switch (choice){
                case 1:
                    byName();
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
                    break;
                case 2:
                    Property picked = choice(properties.getProperties());
                    ArrayList<Property_Tax_Data> tx = Tax.findByProperty(picked);
                    for(Property_Tax_Data t:tx){
                        System.out.println(t);
                    }
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
                    break;
                case 3:
                    System.out.println("Enter year: ");
                    int year = input.nextInt();
                    ArrayList<Property_Tax_Data> overdue = Tax.overdue(year);
                    for(Property_Tax_Data t:overdue){
                        System.out.println(t.toStringMore() + "\n");
                    }
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
                    break;
                case 4:
                    System.out.println("Year: ");
                    int year2 = input.nextInt();
                    input.nextLine();
                    System.out.println("Routing key: ");
                    String routing = input.nextLine();
                    ArrayList<Property_Tax_Data> overdue2 = Tax.overdue(year2,routing);
                    for(Property_Tax_Data t : overdue2){
                        System.out.println(t.toStringMore() + "\n");
                    }
                    break;
                case 5:
                    PropertyTaxStats();
                    break;
                case 6:
                    run =false;
                    break;
            }
        }
    }

    private void PropertyTaxStats(){
        boolean run = true;
        while (run){
            System.out.println("1) Total tax paid / 2) Average tax paid / 3) Percentage of property taxes paid / 4) Quit");
            int choice = input.nextInt();
            switch (choice){
                case 1:
                    input.nextLine();
                    System.out.println("Routing code: ");
                    String routing = input.nextLine();
                    System.out.println(Tax.totalTax(routing));
                    break;
                case 2:
                    input.nextLine();
                    System.out.println("Routing code: ");
                    String routing2 = input.nextLine();
                    System.out.println(Tax.averageTax(routing2));
                    break;
                case 3:
                    input.nextLine();
                    System.out.println("Routing code: ");
                    String routing3 = input.nextLine();
                    System.out.println(Math.round(Tax.taxPercent(routing3)) + "%");
                    break;
                case 4:
                    run = false;
                    break;
            }
        }
    }

    private Property choice(ArrayList<Property> p){
        int count = 0;
        for(Property prop : p){
            count ++;
            System.out.println(count+") " + prop);
        }
        System.out.println("Number value to display Property Tax information: ");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        if(choice <= p.size()){
            return p.get(choice-1);
        }else{
            System.out.println("Invalid Number.");
            return p.get(0);
        }
    }

    
}
//volvunt ad inceptum
