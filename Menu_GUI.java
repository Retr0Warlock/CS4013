import com.sun.javafx.css.CalculatedValue;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.beans.value.*;

import java.nio.channels.ClosedSelectorException;
import java.util.*;
import java.io.*;
import java.time.Year;

public class Menu_GUI extends Application {
    Scene mainMenu, ownerMenu, AdminMenu, AddPropertyMenu, myProperties, ListPropMenu, overdueTaxMenu, generalStatsMenu, searchTaxMenu, getTaxDueMenu, namePromptScene;
    TextField Names, Firstline, Secondline, City, County, Eircode, MarketValue, Country, Amount, taxYear, searchOwner, searchAddress, overdueRouting, overdueYear, generalRouting;
    ChoiceBox<String> Catagory, PrivateRes;
    ChoiceBox<Property> ChosenProp;
    Label searchTaxLabel, overdueTaxLabel, generalStatsLabel, addInfo, getTax, getTaxYear, generalTaxStats, totalOverdue, taxData;
    ArrayList<Property> propertyArrayList = new ArrayList<>();
    private final PropertyFiler filer = new PropertyFiler();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        //Main menu - Done
        mainStage.setTitle("Main Menu");
        BorderPane main = new BorderPane();
        Label header = new Label("Select user type");
        GridPane mainMenuButtons = new GridPane();
        mainMenu = new Scene(main, 900, 500);

        main.setCenter(header);
        main.setBottom(mainMenuButtons);

        Button option1 = new Button("Owner");
        option1.setOnAction(e -> mainStage.setScene(ownerMenu));
        Button option2 = new Button("Admin");
        option2.setOnAction(e -> mainStage.setScene(AdminMenu));

        mainMenuButtons.setHgap(10);
        mainMenuButtons.setVgap(10);
        mainMenuButtons.setPadding(new Insets(10, 10, 100, 10));
        mainMenuButtons.setAlignment(Pos.CENTER);
        mainMenuButtons.add(option1, 1, 1);
        mainMenuButtons.add(option2, 2, 1);
        option1.setPrefWidth(mainMenu.getWidth());
        option2.setPrefWidth(mainMenu.getWidth());

        header.setFont(new Font("", 35));


        //OwnerMenu - Done
        Button ownerButton1 = new Button("Register a Property");
        ownerButton1.setOnAction(e -> mainStage.setScene(AddPropertyMenu));
        Button ownerButton2 = new Button("My Properties");
        ownerButton2.setOnAction(e -> {
            propertyArrayList = filer.search(new Owner(TextPromptWindow.display("Enter Name")));
            if (propertyArrayList.size() == 0) {
                ErrorWindow.display("No properties with that owner name");
                mainStage.setScene(ownerMenu);
            } else {
//                    mainStage.setScene(myProperties);
            }
        });
        Button ownerButton3 = new Button("Quit");
        ownerButton3.setOnAction(e -> mainStage.setScene(mainMenu));
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
        submitNew.setOnAction(e -> {
            AddProp();
            mainStage.setScene(ownerMenu);
        });
        Button QuitNew = new Button("Quit");
        QuitNew.setOnAction(e -> mainStage.setScene(ownerMenu));

        HBox AddPropertyFields = new HBox();
        AddPropertyFields.getChildren().addAll(names, Names, firstline, Firstline, secondline, Secondline, city, City, county, County, country, Country, eircode, Eircode, MarketVal, MarketValue, catagory, Catagory, privateRes, PrivateRes, submitNew, QuitNew);
        VBox AddPropertyParent = new VBox();
        Label AddPropertyLabel = new Label("Fill All Fields");
        AddPropertyParent.getChildren().addAll(AddPropertyLabel, AddPropertyFields);
        AddPropertyMenu = new Scene(AddPropertyParent, 1000, 1000);

        //ListProperties - WIP
        this.ChosenProp = new ChoiceBox(FXCollections.observableArrayList());
        this.Amount = new TextField();

        Button Pay = new Button("Pay");

        Button listQuit = new Button("Quit");
        listQuit.setOnAction(e -> mainStage.setScene(ownerMenu));
        HBox ListPropFields = new HBox();
        ListPropFields.getChildren().addAll(ChosenProp, Firstline, Amount, Pay, listQuit);
        VBox ListPropParent = new VBox();
        addInfo = new Label("");
        ListPropParent.getChildren().addAll(ListPropFields, addInfo);
        ListPropMenu = new Scene(ListPropParent, 600, 600);

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
        Button AdminButton1 = new Button("Search Tax Data");
        AdminButton1.setOnAction(e -> mainStage.setScene(searchTaxMenu));
        Button AdminButton2 = new Button("Overdue Tax");
        AdminButton2.setOnAction(e -> mainStage.setScene(overdueTaxMenu));
        Button AdminButton3 = new Button("General Tax Statistics");
        AdminButton3.setOnAction(e -> mainStage.setScene(generalStatsMenu));
        Button AdminButton4 = new Button("Quit");
        AdminButton4.setOnAction(e -> mainStage.setScene(mainMenu));
        HBox AdminMenuButtons = new HBox();
        AdminMenuButtons.getChildren().addAll(AdminButton1, AdminButton2, AdminButton3, AdminButton4);
        VBox AdminMenuParent = new VBox();
        Label AdminMenuLabel = new Label("Choose Admin menu option");
        AdminMenuParent.getChildren().addAll(AdminMenuLabel, AdminMenuButtons);
        AdminMenu = new Scene(AdminMenuParent, 500, 500);

        //Search Tax Data - WIP - Requires ChooseProperty method to work.
        this.searchOwner = new TextField();
        Button searchForOwner = new Button("Search by Owner");

        firstline = new Label("First Line");
        this.Firstline = new TextField();
        secondline = new Label("Second line");
        this.Secondline = new TextField();
        city = new Label("City");
        this.City = new TextField();
        county = new Label("County");
        this.County = new TextField();
        country = new Label("Country");
        this.Country = new TextField();
        Button searchForAddress = new Button("Search by Address");

        Button listAll = new Button("List All Properties");
        this.taxData = new Label("");
        Button searchQuit = new Button("Quit");
        searchQuit.setOnAction(e -> mainStage.setScene(AdminMenu));
        HBox searchTaxFields = new HBox();
        searchTaxFields.getChildren().addAll(searchOwner, searchForOwner, firstline, Firstline, secondline, Secondline, city, City, county, County, country, Country, searchForAddress, listAll, taxData, searchQuit);
        VBox searchTaxParent = new VBox();
        this.searchTaxLabel = new Label("");
        searchTaxParent.getChildren().addAll(searchTaxLabel, searchTaxFields);
        searchTaxMenu = new Scene(searchTaxParent, 500, 500);

        //Overdue Tax - Done
        Button searchOverdue = new Button("Search");
        searchOverdue.setOnAction(e -> OverDueTax());
        Label overduerouting = new Label("Routing Key (Leave Blank to Ignore)");
        this.overdueRouting = new TextField();
        Label overdueyear = new Label("Year (Leave Blank to Ignore)");
        this.overdueYear = new TextField();
        this.totalOverdue = new Label("");
        Button overdueQuit = new Button("Quit");
        overdueQuit.setOnAction(e -> mainStage.setScene(AdminMenu));
        HBox overdueTaxFields = new HBox();
        overdueTaxFields.getChildren().addAll(overduerouting, overdueRouting, overdueyear, overdueYear, searchOverdue, totalOverdue, overdueQuit);
        VBox overdueTaxParent = new VBox();
        this.overdueTaxLabel = new Label("");
        overdueTaxParent.getChildren().addAll(overdueTaxLabel, overdueTaxFields);
        overdueTaxMenu = new Scene(overdueTaxParent, 500, 500);

        //General Tax Statistics - Done
        Button showStats = new Button("Show Statistics");
        showStats.setOnAction(e -> generalTaxStats());
        this.generalRouting = new TextField();
        generalTaxStats = new Label("");
        Button generalQuit = new Button("Quit");
        generalQuit.setOnAction(e -> mainStage.setScene(AdminMenu));
        HBox generalStatsFields = new HBox();
        generalStatsFields.getChildren().addAll(generalRouting, showStats, generalTaxStats, generalQuit);
        VBox generalStatsParent = new VBox();
        this.generalStatsLabel = new Label("Routing Key (Leave Blank to ignore)");
        generalStatsParent.getChildren().addAll(generalStatsLabel, generalStatsFields);
        generalStatsMenu = new Scene(generalStatsParent, 500, 500);


        mainStage.setScene(mainMenu);
        mainStage.show();
    }

    //Search Tax Data
    public void searchTaxDataAllProp() {
        ArrayList<Property> properties;
        properties = filer.read();
        Property propChoice = chooseProperty(properties);
        String temp = "";
        for (PropertyTax tax : propChoice.getPropertyTaxes()) //display tax data for the property
            temp = temp + (tax.getSummary() + "\n");
        taxData.setText(temp);
    }

    public void searchTaxDataByName() {
        ArrayList<Property> properties;
        String name = searchOwner.getText();
        properties = filer.search(new Owner(name));
        Property propChoice = chooseProperty(properties);
        String temp = "";
        for (PropertyTax tax : propChoice.getPropertyTaxes()) //display tax data for the property
            temp = temp + (tax.getSummary() + "\n");
        taxData.setText(temp);
    }

    public void searchTaxDataByAddress() {
        ArrayList<Property> properties;
        String firstline = this.Firstline.getText();
        String secondline = this.Secondline.getText();
        String city = this.City.getText();
        String county = this.County.getText();
        String country = this.Country.getText();
        properties = filer.search(new Address(firstline, secondline, city, county, country));
        Property propChoice = chooseProperty(properties);
        String temp = "";
        for (PropertyTax tax : propChoice.getPropertyTaxes()) //display tax data for the property
            temp = temp + (tax.getSummary() + "\n");
        taxData.setText(temp);
    }


    //Overdue Tax
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

    public void ListProperties_PayTax() {
        /*String choice = Names.getText();
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
        window.setScene(ListPropMenu); */
    }

    public Property chooseProperty(ArrayList<Property> propertyList) {
        ChosenProp = new ChoiceBox(FXCollections.observableArrayList(propertyList));
        ChosenProp.getSelectionModel().select(0);
        return ChosenProp.getValue();
    }
    
    /*
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
    public void AddProp() {
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
    }
}

class QuitHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent e) {
        System.exit(0);
    }
}

class PropertyInfoWindow {
    public static Scene getPane(ArrayList<Property> properties) {
        ChoiceBox<Property> propertyChoiceBox = new ChoiceBox((ObservableList) properties);
        ChoiceBox<Year> propertyTaxChoiceBox = new ChoiceBox();
        TextField paymentInfo = new TextField();
        Button quit = new Button("Quit");

        BorderPane propertyInfoWindow = new BorderPane();
        VBox choiceVBox = new VBox();
        choiceVBox.getChildren().addAll(propertyChoiceBox, propertyTaxChoiceBox, paymentInfo, quit);

        VBox propertyBox = new VBox();
        VBox propertyInfo = new VBox();
        VBox propertyTaxInfo = new VBox();
        propertyBox.getChildren().addAll(propertyInfo, propertyTaxInfo);

        propertyInfoWindow.setLeft(choiceVBox);
        propertyInfoWindow.setCenter(propertyBox);
        return new Scene(propertyInfoWindow, 500, 500);
    }
}

class PropertyInfo {
    public static VBox getInfo(Property prop) {
        VBox result = new VBox();
        Label owners = new Label(prop.getOwners().toString());
        Label firstLine = new Label(prop.getAddress().getFirstLine());
        Label secondLine = new Label(prop.getAddress().getSecondLine());
        Label city = new Label(prop.getAddress().getCity());
        Label county = new Label(prop.getAddress().getCounty());
        Label country = new Label(prop.getAddress().getCountry());
        Label eircode = new Label(prop.getEircode());
        Label marketvalue = new Label(prop.getMarketVal() + "");
        Label category = new Label(prop.getCategory());
        Label isPrivateRes = new Label(prop.isPrivateRes() + "");
        result.getChildren().addAll(owners, firstLine, secondLine, city, county, country, eircode, marketvalue, category, isPrivateRes);
    }
}

class TextPromptWindow {
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
