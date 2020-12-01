import java.util.ArrayList;
import java.util.Scanner;
public class CommandLine_Interface {
    public static void main(String[] args) {
        Properties properties = new Properties();
        Scanner Line = new Scanner(System.in);
        boolean quit = true;
        while(quit){
            System.out.println("A) Add Property B) Search C) Quit");
            String choice = Line.nextLine();
            if(choice.equalsIgnoreCase("A")){
                StoreProperty add = new StoreProperty();
                add.addPropertyValues();
            }else if(choice.equalsIgnoreCase("B")){
                System.out.println("Search By : A) Name B) Address C) Eircode D) Market Value " + "E) Category F) Private Residence");
                String searchChoice = Line.nextLine();
                Line.nextLine();
                if(searchChoice.equalsIgnoreCase("A")){
                    System.out.println("FullName: ");
                    String Fullname = Line.nextLine();
                    ArrayList<Property> name = new ArrayList<>();
                    name = properties.searchByName(new Owners(Fullname));
                    for(Property prop : name){
                        System.out.println(prop);
                    }
                }else if(searchChoice.equalsIgnoreCase("B")){
                    System.out.println("Address \n FirstLine: ");
                    String firstLine = Line.nextLine();
                    System.out.println("SecondLine: ");
                    String secondLine = Line.nextLine();
                    System.out.println("City ");
                    String city = Line.nextLine();
                    System.out.println("County: ");
                    String county = Line.nextLine();
                    System.out.println("Country: ");
                    String country = Line.nextLine();

                }else if(searchChoice.equalsIgnoreCase("C")){
                    System.out.println("Eircode: ");
                    String Eircode = Line.nextLine();
                    
                }else if(searchChoice.equalsIgnoreCase("D")){
                    System.out.println("Market Value: ");
                    double MarketValue = Line.nextDouble();
                    
                }else if(searchChoice.equalsIgnoreCase("E")){
                    System.out.println("Catagory: ");
                    String Catagory = Line.nextLine();
                    
                }else if(searchChoice.equalsIgnoreCase("F")){
                    System.out.println("Private Residence: (Y/N)");
                    String privateResidence = Line.nextLine();
                    
                }
            }else if(choice.equalsIgnoreCase("C")){
                quit = false;
            }
        }
    }
}
