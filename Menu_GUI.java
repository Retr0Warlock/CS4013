import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.collections.*;

import java.util.*;
import java.io.*;
import java.time.Year;

public class Menu_GUI extends Application {
    int windowSizeX = 700;
    int windowSizeY = 500;
    Stage window;
    Scene mainMenu, ownerMenu, adminMenu, regPropertyMenu,addPropertyMenu, myProperties, listPropMenu, overdueTaxMenu, generalStatsMenu, searchTaxMenu, getTaxDueMenu, namePromptScene;
    TextField Names, firstLine, secondLine, city, county, eircode, marketValue, country, amount, taxYear, searchOwner, searchAddress, overdueRouting, overdueYear, generalRouting;
    ChoiceBox<String> category, privateRes;
    Label searchTaxLabel, overdueTaxLabel, generalStatsLabel, addInfo, getTax, getTaxYear, generalTaxStats, totalOverdue, taxData;
    ArrayList<Property> propertyArrayList = new ArrayList<>();
    private final PropertyFiler filer = new PropertyFiler();

    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Initalizes the GUI and displays it.
     * Stores the display settings for each menu.
     */
    @Override
    public void start(Stage mainStage) {
        window = mainStage;
        //Main menu - Done
        mainStage.setTitle("Main Menu");
        BorderPane main = new BorderPane();
        Label header = new Label("Select user type");
        GridPane mainMenuButtons = new GridPane();
        mainMenu = new Scene(main, windowSizeX, windowSizeY);

        main.setCenter(header);
        main.setBottom(mainMenuButtons);

        Button option1 = new Button("Owner");
        option1.setOnAction(e -> mainStage.setScene(ownerMenu));
        Button option2 = new Button("Admin");
        option2.setOnAction(e -> mainStage.setScene(adminMenu));
        QuitHandler quitHandle = new QuitHandler();
        Button option3 = new Button("Quit");

        option3.setOnAction(quitHandle);

        mainMenuButtons.setHgap(10);
        mainMenuButtons.setVgap(10);
        mainMenuButtons.setPadding(new Insets(10, 10, 100, 10));
        mainMenuButtons.setAlignment(Pos.CENTER);
        mainMenuButtons.add(option1, 1, 1);
        mainMenuButtons.add(option2, 2, 1);
        mainMenuButtons.add(option3, 3, 1);
        option1.setPrefWidth(mainMenu.getWidth());
        option2.setPrefWidth(mainMenu.getWidth());
        option3.setPrefWidth(mainMenu.getWidth());
        header.setFont(new Font("", 35));


        //OwnerMenu - Done
        BorderPane ownerMenuParent = new BorderPane();
        ownerMenu = new Scene(ownerMenuParent, windowSizeX, windowSizeY);
        Button ownerButton1 = new Button("Register a Property");
        ownerButton1.setPrefWidth(ownerMenu.getWidth());
        ownerButton1.setOnAction(e -> mainStage.setScene(addPropertyMenu));
        Button ownerButton2 = new Button("My Properties");
        ownerButton2.setPrefWidth(ownerMenu.getWidth());
        ownerButton2.setOnAction(e -> {
            propertyArrayList = filer.search(new Owner(TextPromptWindow.display("Enter Name")));
            if (propertyArrayList.size() == 0) {
                ErrorWindow.display("No properties with that owner name");
                mainStage.setScene(ownerMenu);
            } else {
                getPropertyScene(propertyArrayList);
            }
        });
        Button ownerButton3 = new Button("Quit");
        ownerButton3.setPrefWidth(ownerMenu.getWidth());
        ownerButton3.setOnAction(e -> mainStage.setScene(mainMenu));
        GridPane ownerMenuButtons = new GridPane();
        ownerMenuButtons.add(ownerButton1, 1, 1);
        ownerMenuButtons.add(ownerButton2, 2, 1);
        ownerMenuButtons.add(ownerButton3, 3, 1);
        ownerMenuButtons.setHgap(10);
        ownerMenuButtons.setVgap(10);
        ownerMenuButtons.setPadding(new Insets(10, 10, 100, 10));
        ownerMenuButtons.setAlignment(Pos.CENTER);
        Label ownerMenuLabel = new Label("Choose Owner menu option");
        ownerMenuLabel.setFont(new Font("", 35));
        ownerMenuParent.setCenter(ownerMenuLabel);
        ownerMenuParent.setBottom(ownerMenuButtons);

        //New add property
        TextField addPropNames = new TextField();
        TextField addPropFirstLine = new TextField();
        TextField addPropSecondLine = new TextField();
        TextField addPropCity = new TextField();
        TextField addPropCounty = new TextField();
        TextField addPropCountry = new TextField();
        TextField addPropEircode = new TextField();
        ChoiceBox addPropCategory = new ChoiceBox(FXCollections.observableArrayList("City", "Large Town", "Small Town", "Village", "Countryside"));
        TextField addPropMarketValue = new TextField();
        ChoiceBox addPropPrivateRes = new ChoiceBox(FXCollections.observableArrayList("true", "false"));
        addPropPrivateRes.getSelectionModel().select(0);
        Button addPropSubmit = new Button("Submit");
        addPropSubmit.setOnAction(e -> {
            ArrayList<Owner> owners = new ArrayList<>();
            for (String owner : addPropNames.getText().split(","))
                owners.add(new Owner(owner));
            try {
                filer.add(new Property(owners, new Address(addPropFirstLine.getText(), addPropSecondLine.getText(), addPropCity.getText(), addPropCounty.getText(), addPropCountry.getText()),
                        addPropEircode.getText(), Double.parseDouble(addPropMarketValue.getText()), addPropCategory.getValue().toString(), Boolean.parseBoolean(addPropPrivateRes.getValue().toString())));
                mainStage.setScene(ownerMenu);
            } catch (Exception a) {
                ErrorWindow.display("Invalid property info");
            }
        });
        Button addPropQuit = new Button("Quit");
        addPropQuit.setOnAction(e->mainStage.setScene(mainMenu));
        VBox addPropertyFields = new VBox();
        addPropertyFields.getChildren().addAll(new Label("Names(comma separated)"), addPropNames, new Label("First Line"), addPropFirstLine, new Label("Second Line"), addPropSecondLine,
                new Label("City"), addPropCity, new Label("County"), addPropCounty, new Label("Country"), addPropCountry, new Label("Eircode"), addPropEircode, new Label("Market Value"),
                addPropMarketValue, new Label("Category"), addPropCategory, new Label("Private Residence"), addPropPrivateRes, addPropSubmit, addPropQuit);
        addPropertyFields.setSpacing(10);
        addPropertyFields.setPadding(new Insets(10, 10, 10, 10));
        VBox addPropertyParent = new VBox();
        addPropertyParent.getChildren().add(addPropertyFields);
        addPropertyMenu = new Scene(addPropertyParent, windowSizeX, windowSizeY * 1.5);

        //Register A Property - Done
        Label names = new Label("Owners (Comma seperated)");
        this.Names = new TextField();
        Label address = new Label("Address");
        Label firstline = new Label("First line");
        this.firstLine = new TextField();
        Label secondline = new Label("Second line");
        this.secondLine = new TextField();
        Label city = new Label("City");
        this.city = new TextField();
        Label county = new Label("County");
        this.county = new TextField();
        Label country = new Label("Country");
        this.country = new TextField();
        Label eircode = new Label("Eircode");
        this.eircode = new TextField();
        Label MarketVal = new Label("Market Value");
        this.marketValue = new TextField();
        Label catagory = new Label("Catagory");
        this.category = new ChoiceBox(FXCollections.observableArrayList("City", "Large Town", "Small Town", "Village", "Countryside"));
        category.getSelectionModel().select(0);
        Label privateRes = new Label("Private Residence");
        this.privateRes = new ChoiceBox(FXCollections.observableArrayList("Yes", "No"));
        this.privateRes.getSelectionModel().select(0);
        Button submitNew = new Button("Submit");
        submitNew.setOnAction(e -> {
            AddProp();
            mainStage.setScene(ownerMenu);
        });
        Button QuitNew = new Button("Quit");
        QuitNew.setOnAction(e -> mainStage.setScene(ownerMenu));

        HBox AddPropertyFields = new HBox();
        AddPropertyFields.getChildren().addAll(names, Names, firstline, firstLine, secondline, secondLine, city, this.city, county, this.county, country, this.country, eircode, this.eircode, MarketVal, marketValue, catagory, category, privateRes, this.privateRes, submitNew, QuitNew);
        VBox AddPropertyParent = new VBox();
        Label AddPropertyLabel = new Label("Fill All Fields");
        AddPropertyParent.getChildren().addAll(AddPropertyLabel, AddPropertyFields);
        regPropertyMenu = new Scene(AddPropertyParent, 1000, 1000);


        //Get Tax Due - Done
        this.taxYear = new TextField();
        Button taxGet = new Button("Get Tax Due");
        taxGet.setOnAction(e -> getTaxDue());
        this.getTaxYear = new Label("");
        Label enterYear = new Label("Enter a year to view total tax due for all owned properties that year: ");
        this.getTax = new Label("");
        Button taxQuit = new Button("Quit");
        taxQuit.setOnAction(e -> mainStage.setScene(ownerMenu));
        HBox getTaxFields = new HBox();
        getTaxFields.getChildren().addAll(enterYear, getTax, taxYear, taxGet, taxQuit, getTaxYear);
        VBox getTaxParent = new VBox();
        getTaxParent.getChildren().addAll(getTaxFields);
        getTaxDueMenu = new Scene(getTaxParent, 400, 400);


        //AdminMenu - Done
        BorderPane adminMenuParent = new BorderPane();
        adminMenu = new Scene(adminMenuParent, windowSizeX, windowSizeY);
        Button AdminButton1 = new Button("Search Tax Data");
        AdminButton1.setOnAction(e -> mainStage.setScene(searchTaxMenu));
        AdminButton1.setPrefWidth(adminMenu.getWidth());
        Button AdminButton2 = new Button("Overdue Tax");
        AdminButton2.setOnAction(e -> mainStage.setScene(overdueTaxMenu));
        AdminButton2.setPrefWidth(adminMenu.getWidth());
        Button AdminButton3 = new Button("General Tax Statistics");
        AdminButton3.setOnAction(e -> mainStage.setScene(generalStatsMenu));
        AdminButton3.setPrefWidth(adminMenu.getWidth());
        Button AdminButton4 = new Button("Quit");
        AdminButton4.setOnAction(e -> mainStage.setScene(mainMenu));
        AdminButton4.setPrefWidth(adminMenu.getWidth());
        HBox AdminMenuButtons = new HBox();
        AdminMenuButtons.getChildren().addAll(AdminButton1, AdminButton2, AdminButton3, AdminButton4);
        VBox AdminMenuParent = new VBox();
        Label AdminMenuLabel = new Label("Choose Admin menu option");
        AdminMenuParent.getChildren().addAll(AdminMenuLabel, AdminMenuButtons);
        
        GridPane adminMenuButtons = new GridPane();
        adminMenuButtons.add(AdminButton1, 1, 1);
        adminMenuButtons.add(AdminButton2, 2, 1);
        adminMenuButtons.add(AdminButton3, 3, 1);
        adminMenuButtons.add(AdminButton4, 4, 1);
        adminMenuButtons.setHgap(10);
        adminMenuButtons.setVgap(10);
        adminMenuButtons.setPadding(new Insets(10, 10, 100, 10));
        adminMenuButtons.setAlignment(Pos.CENTER);
        AdminMenuLabel.setFont(new Font("", 35));
        adminMenuParent.setCenter(AdminMenuLabel);
        adminMenuParent.setBottom(adminMenuButtons);
        
        
        //Search Tax Data - Done
        BorderPane searchTaxMenuParent = new BorderPane();
        searchTaxMenu = new Scene(searchTaxMenuParent, windowSizeX, windowSizeY);
        this.searchOwner = new TextField();
        Button searchForOwner = new Button("Search by Owner");
        searchForOwner.setOnAction(e -> searchTaxDataByName());
        firstline = new Label("First Line");
        this.firstLine = new TextField();
        secondline = new Label("Second line");
        this.secondLine = new TextField();
        city = new Label("City");
        this.city = new TextField();
        county = new Label("County");
        this.county = new TextField();
        country = new Label("Country");
        this.country = new TextField();
        Button searchForAddress = new Button("Search by Address");
        searchForAddress.setOnAction(e -> searchTaxDataByAddress());
        Button listAll = new Button("List All Properties");
        listAll.setOnAction(e -> searchTaxDataAllProp());
        this.taxData = new Label("");
        Button searchQuit = new Button("Quit");
        searchQuit.setOnAction(e -> mainStage.setScene(adminMenu));
        
        GridPane searchTaxFields = new GridPane();
        searchTaxFields.add(searchOwner, 1, 1);
        searchTaxFields.add(searchForOwner, 2, 1);
        searchTaxFields.add(firstline, 1, 2);
        searchTaxFields.add(firstLine, 2, 2);
        searchTaxFields.add(secondline, 1, 3);
        searchTaxFields.add(secondLine, 2, 3);
        searchTaxFields.add(city, 1, 4);
        searchTaxFields.add(this.city, 2, 4);
        searchTaxFields.add(county, 1, 5);
        searchTaxFields.add(this.county, 2, 5);
        searchTaxFields.add(country, 1, 6);
        searchTaxFields.add(this.country, 2, 6);
        searchTaxFields.add(searchForAddress, 3, 6);
        searchTaxFields.add(listAll, 1, 7);
        searchTaxFields.add(searchQuit, 1, 8);
        
        searchTaxFields.setHgap(10);
        searchTaxFields.setVgap(10);
        searchTaxFields.setPadding(new Insets(10, 10, 100, 10));
        searchTaxFields.setAlignment(Pos.CENTER);        
        searchTaxMenuParent.setLeft(searchTaxFields);
        
        
        
        
        
        //Overdue Tax - Done
        BorderPane overdueTaxMenuParent = new BorderPane();
        overdueTaxMenu = new Scene(overdueTaxMenuParent, windowSizeX, windowSizeY);
        Button searchOverdue = new Button("Search");
        searchOverdue.setOnAction(e -> OverDueTax());
        Label overduerouting = new Label("Routing Key (Leave Blank to Ignore)");
        this.overdueRouting = new TextField();
        Label overdueyear = new Label("Year (Leave Blank to Ignore)");
        this.overdueYear = new TextField();
        this.totalOverdue = new Label("");
        Button overdueQuit = new Button("Quit");
        overdueQuit.setOnAction(e -> mainStage.setScene(adminMenu));
        
        GridPane overdueTaxFields = new GridPane();
        overdueTaxFields.add(overduerouting, 1, 1);
        overdueTaxFields.add(overdueRouting, 2, 1);
        overdueTaxFields.add(overdueyear, 1, 2);
        overdueTaxFields.add(overdueYear, 2, 2);
        overdueTaxFields.add(searchOverdue, 1, 3);
        overdueTaxFields.add(totalOverdue, 2, 3);
        overdueTaxFields.add(overdueQuit, 1, 4);
                
        overdueTaxFields.setHgap(10);
        overdueTaxFields.setVgap(10);
        overdueTaxFields.setPadding(new Insets(10, 10, 100, 10));
        overdueTaxFields.setAlignment(Pos.CENTER);        
        overdueTaxMenuParent.setLeft(overdueTaxFields);

        //General Tax Statistics - Done
        BorderPane generalStatsMenuParent = new BorderPane();
        generalStatsMenu = new Scene(generalStatsMenuParent, windowSizeX, windowSizeY);
        Button showStats = new Button("Show Statistics");
        showStats.setOnAction(e -> generalTaxStats());
        this.generalRouting = new TextField();
        generalTaxStats = new Label("");
        Button generalQuit = new Button("Quit");
        generalQuit.setOnAction(e -> mainStage.setScene(adminMenu));
        this.generalStatsLabel = new Label("Routing Key (Leave Blank to ignore)");
        
        GridPane generalStatsFields = new GridPane();
        generalStatsFields.add(generalStatsLabel, 1, 1);
        generalStatsFields.add(generalRouting, 2, 1);
        generalStatsFields.add(showStats, 1, 2);
        generalStatsFields.add(generalTaxStats, 2, 2);
        generalStatsFields.add(generalQuit, 1, 3);
                
        generalStatsFields.setHgap(10);
        generalStatsFields.setVgap(10);
        generalStatsFields.setPadding(new Insets(10, 10, 100, 10));
        generalStatsFields.setAlignment(Pos.CENTER);        
        generalStatsMenuParent.setLeft(generalStatsFields);

        mainStage.setScene(mainMenu);
        mainStage.show();
    }

    
    /**
     * Method for - Search Tax Data
     * Gets List of Tax data for all properties
     */
    public void searchTaxDataAllProp() {
        ArrayList<Property> properties;
        properties = filer.read();
        chooseProperty(properties);
    }

    /**
     * Method for - Search Tax Data
     * Gets List of Tax data for all properties belonging to a certain owner.
     */
    public void searchTaxDataByName() {
        ArrayList<Property> properties;
        String name = searchOwner.getText();
        properties = filer.search(new Owner(name));
        chooseProperty(properties);
    }

    /**
     * Method for - Search Tax Data
     * Gets List of Tax data for all properties at a certain address.
     */
    public void searchTaxDataByAddress() {
        ArrayList<Property> properties;
        String firstline = this.firstLine.getText();
        String secondline = this.secondLine.getText();
        String city = this.city.getText();
        String county = this.county.getText();
        String country = this.country.getText();
        properties = filer.search(new Address(firstline, secondline, city, county, country));
        chooseProperty(properties);
    }


    /**
     * Method for - Overdue Tax
     * Gets List of Overdue Tax for properties
     */
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
    
    /**
     * Method for - Search Tax Data
     * Gets List of properties for user to choose from. 
     */
    public void chooseProperty(ArrayList<Property> propertyList) {
        ObservableList<Property> props = FXCollections.observableArrayList(propertyList);
        ChoiceBox<Property> propertyChoiceBox = new ChoiceBox<>(props);
        Button quit = new Button("Quit");
        quit.setOnAction(e -> window.setScene(ownerMenu));

        BorderPane propertyInfoWindow = new BorderPane();
        VBox choiceVBox = new VBox();
        choiceVBox.getChildren().addAll(propertyChoiceBox, quit);
        choiceVBox.setPrefWidth(300);
        choiceVBox.setPadding(new Insets(10, 100, 10, 50));
        choiceVBox.setSpacing(6);

        BorderPane propertyBox = new BorderPane();
        VBox propertyInfo = new VBox();
        VBox propertyTaxInfo = new VBox();
        propertyBox.setTop(propertyInfo);

        propertyChoiceBox.setOnAction(e -> {
            propertyBox.setTop(getAddressVBox(propertyChoiceBox.getValue()));
            propertyBox.setCenter(new VBox());
            String temp = "";
            for (PropertyTax tax : propertyChoiceBox.getValue().getPropertyTaxes()) //display tax data for the property
                temp = temp + (tax.getSummary() + "\n");
            ErrorWindow.display(temp);
            window.setScene(adminMenu);
        });
        propertyInfoWindow.setLeft(choiceVBox);
        propertyInfoWindow.setCenter(propertyBox);
        Scene TaxData = new Scene(propertyInfoWindow, windowSizeX, windowSizeY);
        window.setScene(TaxData);
    }

    /**
     * Method for - List Properties/Pay Tax
     * Gets List of properties for user to choose from and pay tax.
     */
    public void getPropertyScene(ArrayList<Property> properties) {
        ObservableList<Property> props = FXCollections.observableArrayList(properties);
        ChoiceBox<Property> propertyChoiceBox = new ChoiceBox<>(props);
        ComboBox<Year> propertyTaxChoiceBox = new ComboBox<Year>();
        TextField paymentInfo = new TextField();
        Button quit = new Button("Quit");

        BorderPane propertyInfoWindow = new BorderPane();
        VBox choiceVBox = new VBox();
        choiceVBox.getChildren().addAll(propertyChoiceBox, propertyTaxChoiceBox, paymentInfo, quit);
        choiceVBox.setPrefWidth(300);
        choiceVBox.setPadding(new Insets(10, 100, 10, 50));
        choiceVBox.setSpacing(6);

        BorderPane propertyBox = new BorderPane();
        VBox propertyInfo = new VBox();
        VBox propertyTaxInfo = new VBox();
        propertyBox.setTop(propertyInfo);
        propertyBox.setCenter(propertyTaxInfo);
        propertyChoiceBox.setOnAction(e -> {
            propertyBox.setTop(getAddressVBox(propertyChoiceBox.getValue()));
            propertyBox.setCenter(new VBox());
            propertyTaxChoiceBox.getItems().clear();
            for (PropertyTax tax : propertyChoiceBox.getValue().getPropertyTaxes())
                propertyTaxChoiceBox.getItems().add(tax.getYear());
        });
        propertyTaxChoiceBox.setOnAction(e -> {
            propertyBox.setCenter(getTaxInfoVBox(propertyChoiceBox.getValue().getPropertyTax(propertyTaxChoiceBox.getValue())));
        });

        paymentInfo.setPromptText("Payment amount");
        paymentInfo.setOnAction(e -> {
            try {
                filer.makeTaxPayment(propertyChoiceBox.getValue(), propertyChoiceBox.getValue().getPropertyTax(propertyTaxChoiceBox.getValue()), Double.parseDouble(paymentInfo.getText()));
                ErrorWindow.display("Payment of " + Double.parseDouble(paymentInfo.getText()) + "made");
                window.setScene(ownerMenu);
            } catch (Exception a) {
                ErrorWindow.display("Invalid payment amount");
            }
        });
        quit.setOnAction(e -> window.setScene(ownerMenu));
        propertyInfoWindow.setLeft(choiceVBox);
        propertyInfoWindow.setCenter(propertyBox);
        Scene test = new Scene(propertyInfoWindow, windowSizeX, windowSizeY);
        window.setScene(test);
    }
    
    /**
     * Displays the address selected in the ChoiceBox. 
     */
    public VBox getAddressVBox(Property prop) {
        int fontSize = 15;
        VBox result = new VBox();
        Label owners = new Label(prop.getOwners().toString());
        owners.setFont(new Font("", fontSize));
        Label firstLine = new Label(prop.getAddress().getFirstLine());
        firstLine.setFont(new Font("", fontSize));
        Label secondLine = new Label(prop.getAddress().getSecondLine());
        secondLine.setFont(new Font("", fontSize));
        Label city = new Label(prop.getAddress().getCity());
        city.setFont(new Font("", fontSize));
        Label county = new Label(prop.getAddress().getCounty());
        county.setFont(new Font("", fontSize));
        Label country = new Label(prop.getAddress().getCountry());
        country.setFont(new Font("", fontSize));
        Label eircode = new Label(prop.getEircode());
        eircode.setFont(new Font("", fontSize));
        Label marketvalue = new Label(prop.getMarketVal() + "");
        marketvalue.setFont(new Font("", fontSize));
        Label category = new Label(prop.getCategory());
        category.setFont(new Font("", fontSize));
        Label isPrivateRes = new Label(prop.isPrivateRes() + "");
        isPrivateRes.setFont(new Font("", fontSize));
        result.getChildren().addAll(owners, firstLine, secondLine, city, county, country, eircode, marketvalue, category, isPrivateRes);
        result.setPadding(new Insets(20, 20, 20, 0));
        result.setSpacing(10);
        result.setAlignment(Pos.TOP_LEFT);
        return result;
    }

    /**
     * Displays the Tax info of the chosen address and year.
     */
    public VBox getTaxInfoVBox(PropertyTax tax) {
        VBox result = new VBox();
        Label yearInfo = new Label("Year: " + tax.getYear());
        yearInfo.setFont(new Font("", 25));
        Label paymentData = new Label("Payments made: " + tax.getPaymentsString());
        paymentData.setFont(new Font("", 25));
        Label totalPaid = new Label("Total Paid:" + tax.getPaymentTotal());
        totalPaid.setFont(new Font("", 25));
        Label totalDue = new Label("Total Due: " + (tax.getTax() - tax.getPaymentTotal()));
        totalDue.setFont(new Font("", 25));
        result.getChildren().addAll(yearInfo, paymentData, totalPaid, totalDue);
        return result;
    }

    
    /**
     * Returns general statistics of all properties/ properties of a certain routing code.
     */
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

    
    /**
     * Returns the tax due on a chosen property.
     */
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

    
    /**
     * Adds a property to the properties.csv file.
     */
    public void AddProp() {
        ArrayList<Owner> owners = new ArrayList<Owner>();
        String names = this.Names.getText();
        for (String str : names.split(",")) {
            owners.add(new Owner(str));
        }
        String firstline = this.firstLine.getText();
        String secondline = this.secondLine.getText();
        String city = this.city.getText();
        String county = this.county.getText();
        String country = this.country.getText();
        Address address = new Address(firstline, secondline, city, county, country);
        String eircode = this.eircode.getText();
        double marketVal = Double.parseDouble(this.marketValue.getText());
        String catagory = category.getValue();
        boolean privateRes = this.privateRes.getValue().equalsIgnoreCase("yes");
        Property newProp = null;
        try {
            newProp = new Property(owners, address, eircode, marketVal, catagory, privateRes);
        } catch (InvalidObjectException e) {
            e.printStackTrace();
        }
        filer.add(newProp);
    }
}

class QuitHandler implements EventHandler<ActionEvent> {
    /**
     * Closes the GUI from the quit button on the user selection menu.
     */
    public void handle(ActionEvent e) {
        System.exit(0);
    }
}

class TextPromptWindow {
    /**
     * Displays a window as a text propmt.
     */
    public static String display(String prompt) {
        Stage textPromptWindow = new Stage();
        textPromptWindow.initModality(Modality.APPLICATION_MODAL);
        textPromptWindow.setTitle(prompt);

        Label myProperyLabel = new Label(prompt);
        myProperyLabel.setFont(new Font("", 25));

        TextField userInput = new TextField();
        Button ownerEnter = new Button("Enter");
        ownerEnter.setOnAction(e -> {
            textPromptWindow.close();
        });
        Button quit = new Button("Quit");
        quit.setOnAction(e -> textPromptWindow.close());

        userInput.setPromptText(prompt);
        BorderPane namePromptPane = new BorderPane();

        HBox namePromptBottom = new HBox();
        namePromptBottom.setAlignment(Pos.CENTER);
        namePromptBottom.setSpacing(5);
        namePromptBottom.getChildren().addAll(userInput, ownerEnter, quit);

        namePromptPane.setCenter(myProperyLabel);
        namePromptPane.setBottom(namePromptBottom);
        namePromptPane.setPadding(new Insets(10, 10, 50, 10));

        Scene scene = new Scene(namePromptPane, 400, 200);
        textPromptWindow.setScene(scene);
        textPromptWindow.showAndWait();

        return userInput.getText();
    }
}

class ErrorWindow {
    /**
     * Displays a window with error message when an error occurs.
     */
    public static void display(String errorMessage) {
        Stage errorWindow = new Stage();
        errorWindow.initModality(Modality.APPLICATION_MODAL);
        errorWindow.setTitle("Error");

        Label errorLabel = new Label(errorMessage);

        StackPane main = new StackPane();
        main.getChildren().add(errorLabel);
        Scene scene = new Scene(main, 500, 100);
        errorWindow.setScene(scene);
        errorWindow.show();
    }
}
