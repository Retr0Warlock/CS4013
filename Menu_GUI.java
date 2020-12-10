import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.beans.value.*;
import java.util.*;
import java.io.*;
import java.time.Year;
public class Menu_GUI extends Application {
    Stage window;
    Scene mainMenu,ownerMenu, AdminMenu, AddPropertyMenu, myPropertiesMenu, ListPropMenu, overdueTaxMenu, generalStatsMenu, searchTaxMenu, getTaxDueMenu;
    TextField Names, Firstline, Secondline, City, County, Eircode, MarketValue, Country, Amount, taxYear, searchOwner, searchAddress, overdueRouting, overdueYear, generalRouting;
    ChoiceBox<String> Catagory, PrivateRes;
    ChoiceBox<Property> ChosenProp;
    Label searchTaxLabel, overdueTaxLabel, generalStatsLabel, addInfo, getTax, getTaxYear, generalTaxStats, totalOverdue;
    private final PropertyFiler filer = new PropertyFiler();
   
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        window = stage;
        //Main menu - Done
        stage.setTitle("Main Menu");

        Button options1 = new Button("Owner");
        options1.setOnAction(e-> window.setScene(ownerMenu));
        Button option2 = new Button("Admin");
        option2.setOnAction(e-> window.setScene(AdminMenu));
        Button option3 = new Button("Quit");
        QuitHandler quitHandle = new QuitHandler();
        option3.setOnAction(quitHandle);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(options1, option2, option3);

        VBox main = new VBox();

        Label header = new Label("Choose user type");
        header.setFont(new Font("Arial", 20));
        main.getChildren().addAll(header, buttons);
        main.setPrefSize(500, 500);
        mainMenu = new Scene(main, 500, 500);


        //OwnerMenu - Done
        Button ownerButton1 = new Button("Register a Property");
        ownerButton1.setOnAction(e-> window.setScene(AddPropertyMenu));
        Button ownerButton2 = new Button("My Properties");
        ownerButton2.setOnAction(e-> window.setScene(myPropertiesMenu));
        Button ownerButton3 = new Button("Quit");
        ownerButton3.setOnAction(e->window.setScene(mainMenu));
        HBox ownerMenuButtons = new HBox();
        ownerMenuButtons.getChildren().addAll(ownerButton1, ownerButton2, ownerButton3);
        VBox ownerMenuParent = new VBox();
        Label ownerMenuLabel = new Label("Choose Owner menu option");
        ownerMenuParent.getChildren().addAll(ownerMenuLabel, ownerMenuButtons);
        ownerMenu = new Scene(ownerMenuParent, 500, 500);
        
        
        //Register A Property - Done
        Label names = new Label("Owners (Comma seperated)"); 
        this.Names = new TextField();
        Label address = new Label("Address");
        Label firstline = new Label("First line");
        this.Firstline = new TextField();
        Label secondline = new Label("Second line");
        this.Secondline = new TextField();
        Label city = new Label("City");
        this.City = new TextField();
        Label county = new Label("County");
        this.County = new TextField();
        Label country = new Label("Country");
        this.Country = new TextField();
        Label eircode = new Label("Eircode");
        this.Eircode = new TextField();
        Label MarketVal = new Label("Market Value");
        this.MarketValue = new TextField();
        Label catagory = new Label("Catagory");
        this.Catagory = new ChoiceBox(FXCollections.observableArrayList("City", "Large Town", "Small Town", "Village", "Countryside"));
        Catagory.getSelectionModel().select(0);
        Label privateRes = new Label("Private Residence");
        this.PrivateRes = new ChoiceBox(FXCollections.observableArrayList("Yes", "No"));
        PrivateRes.getSelectionModel().select(0);
        Button submitNew = new Button("Submit");
        submitNew.setOnAction(e-> AddProp());
        Button QuitNew = new Button("Quit");
        QuitNew.setOnAction(e-> window.setScene(ownerMenu));
        
        HBox AddPropertyFields = new HBox();
        AddPropertyFields.getChildren().addAll(names, Names, firstline, Firstline, secondline, Secondline, city, City, county, County, country, Country, eircode, Eircode, MarketVal, MarketValue, catagory, Catagory, privateRes, PrivateRes, submitNew, QuitNew);
        VBox AddPropertyParent = new VBox();
        Label AddPropertyLabel = new Label("Fill All Fields");
        AddPropertyParent.getChildren().addAll(AddPropertyLabel, AddPropertyFields);
        AddPropertyMenu = new Scene(AddPropertyParent, 1000, 1000);
        
        
        
        //My Property - Done
        this.Names = new TextField();
        Button listProp = new Button("List Properties / Pay Tax");
        //listProp.setOnAction(e-> ListProperties_PayTax());
        Button taxDue = new Button("Get tax due");
        taxDue.setOnAction(e->window.setScene(getTaxDueMenu));
        Button myPropQuit = new Button("Quit");
        myPropQuit.setOnAction(e -> window.setScene(ownerMenu));
        HBox myPropertiesFields = new HBox(); 
        myPropertiesFields.getChildren().addAll(Names, listProp, taxDue, myPropQuit);
        VBox myPropertiesParent = new VBox();
        Label ownerName = new Label("Fill the name of the Owner and select one of the following options.");
        myPropertiesParent.getChildren().addAll(ownerName, myPropertiesFields);
        myPropertiesMenu = new Scene(myPropertiesParent, 500, 500);
        
        //ListProperties - WIP
        this.ChosenProp = new ChoiceBox(FXCollections.observableArrayList());
        this.Amount = new TextField();
        
        Button Pay = new Button("Pay");
        
        Button listQuit = new Button("Quit");
        listQuit.setOnAction(e->window.setScene(ownerMenu));
        HBox ListPropFields = new HBox();
        ListPropFields.getChildren().addAll(ChosenProp, Firstline, Amount, Pay, listQuit);
        VBox ListPropParent = new VBox();
        addInfo = new Label("");
        ListPropParent.getChildren().addAll(ListPropFields, addInfo);
        ListPropMenu = new Scene(ListPropParent, 600, 600);
        
        //Get Tax Due - Done
        this.taxYear = new TextField();
        Button taxGet = new Button("Get Tax Due");
        taxGet.setOnAction(e->getTaxDue());
        this.getTaxYear = new Label ("");
        Label enterYear = new Label ("Enter a year to view total tax due for all owned properties that year: ");
        this.getTax = new Label ("");
        Button taxQuit = new Button("Quit");
        taxQuit.setOnAction(e->window.setScene(ownerMenu));
        HBox getTaxFields = new HBox();
        getTaxFields.getChildren().addAll(enterYear, getTax, taxYear, taxGet, taxQuit, getTaxYear);
        VBox getTaxParent = new VBox();
        getTaxParent.getChildren().addAll(getTaxFields);
        getTaxDueMenu = new Scene(getTaxParent, 400, 400);
        
        
        //AdminMenu - Done
        Button AdminButton1 = new Button("Search Tax Data");
        AdminButton1.setOnAction(e->window.setScene(searchTaxMenu));
        Button AdminButton2 = new Button("Overdue Tax");
        AdminButton2.setOnAction(e->window.setScene(overdueTaxMenu));
        Button AdminButton3 = new Button("General Tax Statistics");
        AdminButton3.setOnAction(e->window.setScene(generalStatsMenu));
        Button AdminButton4 = new Button("Quit");
        AdminButton4.setOnAction(e->window.setScene(mainMenu));
        HBox AdminMenuButtons = new HBox();
        AdminMenuButtons.getChildren().addAll(AdminButton1, AdminButton2, AdminButton3, AdminButton4);
        VBox AdminMenuParent = new VBox();
        Label AdminMenuLabel = new Label("Choose Admin menu option");
        AdminMenuParent.getChildren().addAll(AdminMenuLabel, AdminMenuButtons);
        AdminMenu = new Scene(AdminMenuParent, 500, 500);
        
        //Search Tax Data - WIP
        this.searchOwner= new TextField();
        Button searchForOwner = new Button("Search by Owner");
        
        this.searchAddress = new TextField();
        Button searchForAddress = new Button("Search by Address");
        
        Button listAll = new Button("List All Properties");
        
        Button searchQuit = new Button("Quit");
        searchQuit.setOnAction(e->window.setScene(AdminMenu));
        HBox searchTaxFields = new HBox();
        searchTaxFields.getChildren().addAll(searchOwner, searchForOwner, searchAddress, searchForAddress, listAll, searchQuit);
        VBox searchTaxParent = new VBox();
        this.searchTaxLabel = new Label("");
        searchTaxParent.getChildren().addAll(searchTaxLabel, searchTaxFields);
        searchTaxMenu = new Scene(searchTaxParent, 500, 500);
        
        //Overdue Tax - Done
        Button searchOverdue = new Button("Search");
        searchOverdue.setOnAction(e->OverDueTax());
        Label overduerouting= new Label("Routing Key (Leave Blank to Ignore)");
        this.overdueRouting = new TextField();
        Label overdueyear = new Label("Year (Leave Blank to Ignore)");
        this.overdueYear = new TextField();
        this.totalOverdue = new Label("");
        Button overdueQuit = new Button("Quit");
        overdueQuit.setOnAction(e->window.setScene(AdminMenu));
        HBox overdueTaxFields = new HBox();
        overdueTaxFields.getChildren().addAll(overduerouting, overdueRouting, overdueyear, overdueYear, searchOverdue, totalOverdue, overdueQuit);
        VBox overdueTaxParent = new VBox();
        this.overdueTaxLabel = new Label("");
        overdueTaxParent.getChildren().addAll(overdueTaxLabel, overdueTaxFields);
        overdueTaxMenu = new Scene(overdueTaxParent, 500, 500);
        
        //General Tax Statistics - Done
        Button showStats = new Button("Show Statistics");
        showStats.setOnAction(e->generalTaxStats());
        this.generalRouting = new TextField();
        generalTaxStats = new Label("");
        Button generalQuit = new Button("Quit");
        generalQuit.setOnAction(e->window.setScene(AdminMenu));
        HBox generalStatsFields = new HBox();
        generalStatsFields.getChildren().addAll(generalRouting, showStats, generalTaxStats, generalQuit);
        VBox generalStatsParent = new VBox();
        this.generalStatsLabel = new Label("Routing Key (Leave Blank to ignore)");
        generalStatsParent.getChildren().addAll(generalStatsLabel, generalStatsFields);
        generalStatsMenu = new Scene(generalStatsParent, 500, 500);
        
        
        stage.setScene(mainMenu);
        stage.show();
    }
    
    public void OverDueTax() {
        String searchCode = overdueRouting.getText();
        String yearSearch = overdueYear.getText();
        double overDueTax = 0;
        if (yearSearch.equals(""))
            for (Property prop : filer.search(searchCode))
                overDueTax += prop.getTaxDue();
        else
            for (Property prop : filer.search(searchCode))
                for (PropertyTax tax : prop.getPropertyTaxes())
                    if (tax.getYear().equals(Year.parse(yearSearch)))
                        overDueTax += tax.getTax() - tax.getPaymentTotal();
        totalOverdue.setText("Total tax overdue: " + overDueTax);
    }
    
    /*public void ListProperties_PayTax() {
        String choice = Names.getText();
        ArrayList<Property> ownedProperties = filer.search(new Owner(choice));
        try {
                ownedProperties = filer.search(new Owner(choice));
                Property propChoice = chooseProperty(ownedProperties);
                String temp = "";
                for (PropertyTax tax : propChoice.getPropertyTaxes()) //display tax data for the property
                    temp = temp + (tax.getSummary() + "\n");
                addInfo.setText(temp);
                if (propChoice.getTaxDue() > 0) {
                    double payment = Double.parseDouble(Amount.getText());
                    filer.makeTaxPayment(propChoice, chooseTax(propChoice.getPropertyTaxes()), payment);
                }
            } catch (Exception a) {
                System.out.println(a.toString());
            }
        window.setScene(ListPropMenu);
    }
    public Property chooseProperty(ArrayList<Property> propertyList) {
        ChosenProp = new ChoiceBox(FXCollections.observableArrayList(propertyList));
        ChosenProp.getSelectionModel().select(0);
        return ChosenProp.getValue();
    }
    public PropertyTax chooseTax(ArrayList<PropertyTax> taxes) {
        
    }
    */
    
    
    //generalTaxStats
    public void generalTaxStats() {
        double totalTaxPaid = 0;
        ArrayList<Property> properties = filer.search(generalRouting.getText());
        for (Property prop : properties)
            for (PropertyTax tax : prop.getPropertyTaxes())
                totalTaxPaid += tax.getPaymentTotal();
        double averageTaxPaid = totalTaxPaid / properties.size();
        int noPropTaxPaid = 0;
        for (Property prop : properties)
            if (prop.getTaxDue() == 0)
                noPropTaxPaid++;
        generalTaxStats.setText("Total tax paid: " + totalTaxPaid + "\nAverage tax paid: " + averageTaxPaid + "\nNumber and percentage of property taxes fully paid: " + noPropTaxPaid + noPropTaxPaid / properties.size() + "%");
    }
    
    //Get Tax Due
     public void getTaxDue() {
        String choice = this.Names.getText();
        ArrayList<Property> ownedProperties = filer.search(new Owner(choice));
        double totalTaxDue = 0;
        ownedProperties = filer.search(new Owner(choice));
        for (Property prop : ownedProperties) {
            totalTaxDue += prop.getTaxDue();
        }
        getTax.setText("Total tax due: " + totalTaxDue);
        String yearChoice = this.taxYear.getText();
        double annualTotalDue = 0;
        for (Property prop : ownedProperties) {
            for (PropertyTax tax : prop.getPropertyTaxes()) {
                if (tax.getYear().equals(yearChoice)) {
                    annualTotalDue += tax.getTax() - tax.getPaymentTotal();
                }
            }
        }
        getTaxYear.setText("Total due for " + yearChoice + ": " + annualTotalDue);
    }
    
    //Register A Property
    public void AddProp(){
        ArrayList<Owner> owners = new ArrayList<Owner>();
        String names = this.Names.getText();
        for (String str : names.split(",")) {
            owners.add(new Owner(str));
        }
        String firstline = this.Firstline.getText();
        String secondline = this.Secondline.getText();
        String city = this.City.getText();
        String county = this.County.getText();
        String country = this.Country.getText();
        Address address = new Address(firstline, secondline, city, county, country);
        String eircode = this.Eircode.getText();
        double marketVal = Double.parseDouble(this.MarketValue.getText());
        String catagory = Catagory.getValue();
        boolean privateRes = PrivateRes.getValue().equalsIgnoreCase("yes");
        Property newProp = null;
        try {
            newProp = new Property(owners, address, eircode, marketVal, catagory, privateRes);
        } catch (InvalidObjectException e) {
            e.printStackTrace();
        }
        filer.add(newProp);
        window.setScene(ownerMenu);
    }
}

class QuitHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent e) {
        System.exit(0);
    }
}
