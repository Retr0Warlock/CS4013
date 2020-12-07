import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu_CLI {

    public static void main(String[] args) {
        Menu_CLI a = new Menu_CLI();
        a.run();
    }

    public void run() {

        Properties properties = new Properties();
        Scanner in = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            System.out.println("Main menu");
            System.out.println("Select user type: 1.Owner  2.Admin 3.Quit");
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
            System.out.println("1.Get data for a property 2. Get data for an owner 3.Check tax overdue 4.Get general tax statistics 5.Quit");
            String choice = in.nextLine();
            try {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        Property a = createProperty();
                        System.out.println(a.toString());
                        break;
                    case 2: //
                        break;
                    case 3:
                        break;
                    case 4: //taxStats()
                        break;
                    case 5:
                        quit = true;
                        break;
                }
            } catch (
                    Exception e) {
                System.out.println("Please enter valid input");
            }
        }
    }

    public void ownerMenu() {
        Scanner in = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            System.out.println("\tOwner Menu");
            System.out.println("1. Add Property 2. Search 3. Quit");
            String choice = in.nextLine();
            try {
                switch (Integer.parseInt(choice)) {
                    case 1 -> CSV_Storing.add(createProperty());
                    case 2 -> ownerSearch();
                    case 3 -> quit = true;
                }
            } catch (Exception a) {
                a.printStackTrace();
            }
        }
    }

    public void ownerSearch() {
        System.out.println("Search By : A) Name B) Address C) Eircode D) Market Value " + "E) Category F) Private Residence");
        Scanner in = new Scanner(System.in);
        String searchChoice = in.nextLine();
        in.nextLine();
        if (searchChoice.equalsIgnoreCase("A")) {
            System.out.println("FullName: ");
            String Fullname = in.nextLine();
            ArrayList<Property> name = new ArrayList<>();
//                name = properties.searchByName(new Owner(Fullname));
            for (Property prop : name) {
                System.out.println(prop);
            }
        } else if (searchChoice.equalsIgnoreCase("B")) {
            System.out.println("Address \n FirstLine: ");
            String firstLine = in.nextLine();
            System.out.println("SecondLine: ");
            String secondLine = in.nextLine();
            System.out.println("City ");
            String city = in.nextLine();
            System.out.println("County: ");
            String county = in.nextLine();
            System.out.println("Country: ");
            String country = in.nextLine();

        } else if (searchChoice.equalsIgnoreCase("C")) {
            System.out.println("Eircode: ");
            String Eircode = in.nextLine();

        } else if (searchChoice.equalsIgnoreCase("D")) {
            System.out.println("Market Value: ");
            double MarketValue = in.nextDouble();

        } else if (searchChoice.equalsIgnoreCase("E")) {
            System.out.println("Catagory: ");
            String Catagory = in.nextLine();

        } else if (searchChoice.equalsIgnoreCase("F")) {
            System.out.println("Private Residence: (Y/N)");
            String privateResidence = in.nextLine();
        }
    }


    public Property createProperty() throws InvalidObjectException {
        Scanner in = new Scanner(System.in);

        System.out.print("Fullname: ");
        String fullName = in.nextLine();

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

        try {
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

            return new Property(new Owner(fullName), address, eircode, MarketValue, category, privateRes);
        } catch (ArrayIndexOutOfBoundsException a) {
            throw new InvalidObjectException("Invalid category choice");
        } catch (InputMismatchException a) {
            throw new InvalidObjectException("Market value must be a number");
        }
    }
}

