import java.io.InvalidObjectException;
import java.time.Year;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A command line interface that allows a property owner to register, view and pay taxes on properties and allows
 * admins of the system to view all overdue tax and general tax info per property or owner.
 */
public class Menu_CLI {
    private final PropertyFiler filer = new PropertyFiler();

    public static void main(String[] args) {
        Menu_CLI a = new Menu_CLI();
        a.run();
    }

    /**
     * Method called to start the program
     */
    public void run() {
        Scanner in = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            System.out.println("Main menu");
            System.out.println("Select user type: 1.Owner 2.Admin 3.Quit");
            String choice = in.nextLine();
            try {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        ownerMenu();
                        break;
                    case 2:
                        adminMenu();
                        break;
                    case 3:
                        quit = true;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + Integer.parseInt(choice));
                }
            } catch (Exception e) {
                System.out.println("Please enter valid input");
            }
        }
    }

    /**
     * adminMenu of the command line interface
     */
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
                        boolean quit2 = false;
                        while (!quit2) {
                            System.out.println("1.Search by Owner 2.Search by address 3.Search by routing key 4.List all properties 5.Quit");
                            ArrayList<Property> properties = null;
                            switch (Integer.parseInt(in.nextLine())) {
                                case 1:
                                    System.out.print("Enter Owner name: ");
                                    properties = filer.search(new Owner(in.nextLine()));
                                    break;
                                case 2:
                                    properties = filer.search(createAddress());
                                    if (properties.size() == 0)
                                        throw new Exception("No property under that address");
                                    break;
                                case 3:
                                    System.out.print("Enter routing key: ");
                                    properties = filer.search(in.nextLine());
                                    break;
                                case 4:
                                    properties = filer.read();
                                    break;
                                case 5:
                                    quit2 = true;
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + Integer.parseInt(in.nextLine()));
                            }
                            Property propChoice = chooseProperty(properties);
                            for (PropertyTax tax : propChoice.getPropertyTaxes()) //display tax data for the property
                                System.out.println(tax.getSummary() + "\n");
                            quit2 = true;
                        }
                        break;
                    case 2:
                        System.out.print("Filter by routing key(Blank to ignore): ");
                        String searchCode = in.nextLine();
                        System.out.print("Filter by year(Blank to ignore):");
                        String yearSearch = in.nextLine();
                        double overDueTax = 0;
                        if (yearSearch.equals(""))
                            for (Property prop : filer.search(searchCode))
                                overDueTax += prop.getTaxDue();
                        else
                            for (Property prop : filer.search(searchCode))
                                for (PropertyTax tax : prop.getPropertyTaxes())
                                    if (tax.getYear().equals(Year.parse(yearSearch)))
                                        overDueTax += tax.getTax() - tax.getPaymentTotal();
                        System.out.println("Total tax overdue: " + overDueTax);
                        break;
                    case 3:
                        System.out.println("Enter routing key(Blank to ignore): ");
                        double totalTaxPaid = 0;
                        ArrayList<Property> properties = filer.search(in.nextLine());
                        for (Property prop : properties)
                            for (PropertyTax tax : prop.getPropertyTaxes())
                                totalTaxPaid += tax.getPaymentTotal();
                        double averageTaxPaid = totalTaxPaid / properties.size();
                        int noPropTaxPaid = 0;
                        for (Property prop : properties)
                            if (prop.getTaxDue() == 0)
                                noPropTaxPaid++;
                        System.out.println("Total tax paid: " + totalTaxPaid +
                                "\nAverage tax paid: " + averageTaxPaid +
                                "\nNumber and percentage of property taxes fully paid: " + noPropTaxPaid + noPropTaxPaid / properties.size() + "%");
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

    /**
     * owner menu of the command line interface
     */
    public void ownerMenu() {
        Scanner in = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            System.out.println("\tOwner Menu");
            System.out.println("1. Register a property 2. My Properties 3. Quit");
            String choice = in.nextLine();
            try {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        filer.add(createProperty());
                        break;
                    case 2:
                        myPropertiesMenu();
                        break;
                    case 3:
                        quit = true;
                        break;
                    default:
                        throw new IllegalArgumentException("Enter number 1-3");
                }
            } catch (InvalidObjectException | IllegalArgumentException a) {
                System.out.println(a.getMessage());
            } catch (Exception a) {
                System.out.println("Invalid input");
            }
        }
    }

    /**
     * Displays list of properties when supplied a valid property owner name
     *
     * @throws IllegalArgumentException when when invalid input is supplied
     */
    public void myPropertiesMenu() throws IllegalArgumentException {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String choice = in.nextLine();
        ArrayList<Property> ownedProperties = filer.search(new Owner(choice));
        if (ownedProperties.size() == 0)
            throw new IllegalArgumentException("No property owner with that name");
        boolean quit = false;
        while (!quit) {
            try {
                ownedProperties = filer.search(new Owner(choice));
                System.out.println("1.List properties/Pay tax 2.Get tax due 3.Quit");
                switch (Integer.parseInt(in.nextLine())) {
                    case 1:
                        Property propChoice = chooseProperty(ownedProperties);
                        for (PropertyTax tax : propChoice.getPropertyTaxes()) //display tax data for the property
                            System.out.println(tax.getSummary() + "\n");
                        if (propChoice.getTaxDue() > 0) {
                            System.out.println("Enter payment amount: ");
                            double payment = Double.parseDouble(in.nextLine());
                            filer.makeTaxPayment(propChoice, chooseTax(propChoice.getPropertyTaxes()), payment);
                            System.out.println("Payment of " + payment + " made");
                        }
                        break;
                    case 2:
                        double totalTaxDue = 0;
                        for (Property prop : ownedProperties)
                            totalTaxDue += prop.getTaxDue();
                        System.out.println("Total tax due: " + totalTaxDue);
                        System.out.print("Enter a year to view total tax due for all owned properties that year: ");
                        Year yearChoice = Year.parse(in.nextLine());
                        double annualTotalDue = 0;
                        for (Property prop : ownedProperties)
                            for (PropertyTax tax : prop.getPropertyTaxes())
                                if (tax.getYear().equals(yearChoice))
                                    annualTotalDue += tax.getTax() - tax.getPaymentTotal();
                        System.out.println("Total due for " + yearChoice + ": " + annualTotalDue);
                        break;
                    case 3:
                        quit = true;
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception a) {
                System.out.println("Invalid input");
            }
        }
    }


    /**
     * displays list of taxed years on a property and allows the user to select one
     *
     * @param taxes ArrayList containing all taxable years on a property
     * @return the chosen tax info of a taxable year
     */
    public PropertyTax chooseTax(ArrayList<PropertyTax> taxes) {
        Scanner in = new Scanner(System.in);
        System.out.println("Choose which year");
        int index = 1;
        for (PropertyTax tax : taxes)
            System.out.println("(" + (index++) + ")" + tax.getSummary());
        return taxes.get(Integer.parseInt(in.nextLine()) - 1);
    }

    /**
     * command line interface allowing the registration of properties
     *
     * @return the newly created property
     * @throws InvalidObjectException if invalid input is entered
     */
    public Property createProperty() throws InvalidObjectException {
        Scanner in = new Scanner(System.in);
        try {
            System.out.print("Owners(comma separated): ");
            ArrayList<Owner> owners = new ArrayList<Owner>();
            for (String str : in.nextLine().split(","))
                owners.add(new Owner(str));

            Address address = createAddress();

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

    /**
     * allows the creation of an address
     *
     * @return the created address
     */
    public Address createAddress() {
        Scanner in = new Scanner(System.in);
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
        return new Address(firstLine, secondLine, city, county, country);
    }

    /**
     * prints a list of properties and allows the user to select one via the command line
     *
     * @param propertyList the list of properties to be chosen from
     * @return the chosen property
     */
    public Property chooseProperty(ArrayList<Property> propertyList) {
        System.out.println();
        int index = 1;
        for (Property prop : propertyList)
            System.out.println("(" + index++ + ")" + "\n" + prop.generalString() + "\n");
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("Select property: ");
            try {
                return propertyList.get(Integer.parseInt(in.nextLine()) - 1);
            } catch (Exception a) {
                System.out.println("Enter number 1-" + propertyList.size());
            }
        }
    }
}

