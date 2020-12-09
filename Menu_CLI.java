import java.io.InvalidObjectException;
import java.text.ParseException;
import java.time.Year;
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
                        boolean quit2=false;
                        while(!quit2){
                            System.out.println("1.Search by Owner 2.Search by address 3.List all properties 4.Quit");
                            ArrayList<Property> properties;
                            switch (Integer.parseInt(in.nextLine())){
                                case 1:
                                    System.out.println("Enter Owner name");
                                    properties=filer.search(new Owner(in.nextLine()));
                                    break;
                                case 2:
                                    System.out.println("Enter address");
                                    properties=filer.search(new Address(in.nextLine(),in.nextLine(),in.nextLine(),in.nextLine()));
                                case 3:
                                    properties= filer.read();
                                    break;
                                case 4:
                                    quit2=true;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + Integer.parseInt(in.nextLine()));
                            }
                            Property propChoice = chooseProperty(properties);
                            for (PropertyTax tax : propChoice.getPropertyTaxes()) //display tax data for the property
                                System.out.println(tax.getSummary() + "\n");
                            quit2=true;
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
                        double totalTaxPaid=0;
                        ArrayList<Property> properties=filer.search(in.nextLine());
                        for(Property prop: properties)
                            for(PropertyTax tax:prop.getPropertyTaxes())
                                totalTaxPaid+=tax.getPaymentTotal();
                            double averageTaxPaid=totalTaxPaid/properties.size();
                            int noPropTaxPaid=0;
                            for(Property prop:properties)
                                if(prop.getTaxDue() == 0)
                                    noPropTaxPaid++;
                        System.out.println("Total tax paid: "+totalTaxPaid+
                                "\nAverage tax paid: "+averageTaxPaid+
                                "\nNumber and percentage of property taxes fully paid: "+noPropTaxPaid+noPropTaxPaid/properties.size()+"%");
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
                }
            } catch (Exception a) {
                a.printStackTrace();
            }
        }
    }

    public void myPropertiesMenu() throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String choice = in.nextLine();
        ArrayList<Property> ownedProperties = filer.search(new Owner(choice));
        if (ownedProperties.size() == 0)
            throw new Exception("No property owner with that name");
        boolean quit = false;
        while (!quit) {
            try {
                ownedProperties = filer.search(new Owner(choice));
                System.out.println("1.List properties/Pay tax 2.Get tax due 3.Quit");
                switch (Integer.parseInt(in.nextLine())) {
                    case 1: {
                        Property propChoice = chooseProperty(ownedProperties);
                        for (PropertyTax tax : propChoice.getPropertyTaxes()) //display tax data for the property
                            System.out.println(tax.getSummary() + "\n");
                        if (propChoice.getTaxDue() > 0) {
                            System.out.println("Enter payment amount: ");
                            double payment = Double.parseDouble(in.nextLine());
                            filer.makeTaxPayment(propChoice, chooseTax(propChoice.getPropertyTaxes()), payment);
                            System.out.println("Payment of " + payment + "made");
                        }
                    }
                    case 2: {
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
                    }
                    case 3: quit=true;
                }
            } catch (Exception a) {
                System.out.println(a.toString());
            }
        }

    }
    

    public PropertyTax chooseTax(ArrayList<PropertyTax> taxes) {
        Scanner in = new Scanner(System.in);
        System.out.println("Choose which year");
        int index = 1;
        for (PropertyTax tax : taxes)
            System.out.println("(" + (index++) + ")" + tax.getSummary());
        return taxes.get(Integer.parseInt(in.nextLine()) - 1);
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

