import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu_CLI {
    private final PropertyFiler filer = new PropertyFiler();

    public static void main(String[] args) {
        Menu_CLI a = new Menu_CLI();
        a.run();
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            System.out.println("Main menu");
            System.out.println("Select user type: 1.Owner 2.Admin 3.Quit");
            String choice = in.nextLine();
            try {
                switch (Integer.parseInt(choice)) {
                    case 1 -> ownerMenu();
                    case 2 -> adminMenu();
                    case 3 -> quit = true;
                    default -> throw new IllegalStateException("Unexpected value: " + Integer.parseInt(choice));
                }
            } catch (Exception e) {
                System.out.println("Please enter valid input");
            }
        }
    }

    public void adminMenu() {
        Scanner in = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            System.out.println("Admin Tax Menu");
            System.out.println("1.Search Tax Data 2.Overdue Tax 3.General Tax Statistics 4.Quit");
            String choice = in.nextLine();
            try {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        //Search by:  filer.search(SearchType)    Address/Owner, if searching by eircode: for(Property prob:filer.read()) if(prop.getAddress.equals(SearchAddress)
                        break;
                    case 2:
                        //
                        break;
                    case 3:
                        //taxStatistics();  total tax paid: result+=filer.getTax.getPaymentTotal()
                        break;
                    case 4:
                        quit = true;
                        break;
                }
            } catch (Exception e) {
                System.out.println("Please enter valid input");
            }
        }
    }

    public void ownerMenu() {
        Scanner in = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            System.out.println("\tOwner Menu");
            System.out.println("1. Register property 2. My Properties 3. Quit");
            String choice = in.nextLine();
            try {
                switch (Integer.parseInt(choice)) {
                    case 1 -> filer.add(createProperty());
                    case 2 -> myPropertiesMenu();
                    case 3 -> quit = true;
                }
            } catch (Exception a) {
                a.printStackTrace();
            }
        }
    }

    public void myPropertiesMenu() {
        Scanner in = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            System.out.println("Enter your name: ");
            String choice = in.nextLine();
            try {
                //Displays all properties belonging to choice, throws Exception if not (Call filer.search(new Owner(ownerName)))
                //Then allows property to be selected to view more info/pay tax(another method propbably)
                //and another options for viewing all owed property tax(a year or all time)
            } catch (Exception a) {
                System.out.println("No Properties found in your name");
            }
        }
    }

    public Property createProperty() throws InvalidObjectException {
        Scanner in = new Scanner(System.in);
        try {
            System.out.print("Owners(comma separated): ");
            ArrayList<Owner> owners = new ArrayList<Owner>();
            for (String str : in.nextLine().split(","))
                owners.add(new Owner(str));

            System.out.print("\t\t\tAddress \nFirstLine: ");
            String firstLine = in.nextLine();
            System.out.print("SecondLine: ");
            String secondLine = in.nextLine();
            System.out.print("City: ");
            String city = in.nextLine();
            System.out.print("County: ");
            String county = in.nextLine();
            System.out.print("Country: ");
            String country = in.nextLine();
            Address address = new Address(firstLine, secondLine, city, county, country);

            System.out.print("Eircode: ");
            String eircode = in.nextLine();

            System.out.print("Market Value: ");
            double MarketValue = in.nextDouble();
            in.nextLine();

            for (int i = 0; i < Property.categoryList.length; i++)
                System.out.print("(" + (i + 1) + ")" + Property.categoryList[i] + " ");

            System.out.print("\nCategory: ");
            String category = Property.categoryList[in.nextInt() - 1];
            in.nextLine();

            System.out.print("Private Residence(Y/N): ");
            String privateResidence = in.nextLine();
            boolean privateRes = privateResidence.equalsIgnoreCase("Y");

            return new Property(owners, address, eircode, MarketValue, category, privateRes);
        } catch (ArrayIndexOutOfBoundsException a) {
            throw new InvalidObjectException("Invalid category choice");
        } catch (InputMismatchException a) {
            throw new InvalidObjectException("Market value must be a number");
        } catch (Exception a) {
            throw new InvalidObjectException("Invalid property");
        }
    }
}

