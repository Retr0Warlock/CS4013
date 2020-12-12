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
    Scene mainMenu, ownerMenu, adminMenu, addPropertyMenu, myProperties, listPropMenu, overdueTaxMenu, generalStatsMenu, searchTaxMenu, getTaxDueMenu, namePromptScene;
    TextField Names, firstLine, secondLine, city, county, eircode, marketValue, country, amount, taxYear, searchOwner, searchAddress, overdueRouting, overdueYear, generalRouting;
    ChoiceBox<String> category, privateRes;
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
        mainMenu = new Scene(main, 700, 500);

        main.setCenter(header);
        main.setBottom(mainMenuButtons);

        Button option1 = new Button("Owner");
        option1.setOnAction(e -> mainStage.setScene(ownerMenu));
        Button option2 = new Button("Admin");
        option2.setOnAction(e -> mainStage.setScene(adminMenu));

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
        ownerButton1.setOnAction(e -> mainStage.setScene(addPropertyMenu));
        Button ownerButton2 = new Button("My Properties");
        ownerButton2.setOnAction(e -> {
            propertyArrayList = filer.search(new Owner(TextPromptWindow.display("Enter Name")));
            if (propertyArrayList.size() == 0) {
                ErrorWindow.display("No properties with that owner name");
                mainStage.setScene(ownerMenu);
            } else {
                    mainStage.setScene(getPropetyScene(propertyArrayList));
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
        addPropertyMenu = new Scene(AddPropertyParent, 1000, 1000);

        //ListProperties - WIP
        this.ChosenProp = new ChoiceBox(FXCollections.observableArrayList());
        this.amount = new TextField();

        Button Pay = new Button("Pay");

        Button listQuit = new Button("Quit");
        listQuit.setOnAction(e -> mainStage.setScene(ownerMenu));
        HBox ListPropFields = new HBox();
        ListPropFields.getChildren().addAll(ChosenProp, firstLine, amount, Pay, listQuit);
        VBox ListPropParent = new VBox();
        addInfo = new Label("");
        ListPropParent.getChildren().addAll(ListPropFields, addInfo);
        listPropMenu = new Scene(ListPropParent, 600, 600);

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
        adminMenu = new Scene(AdminMenuParent, 500, 500);

        //Search Tax Data - WIP - Requires ChooseProperty method to work.
        this.searchOwner = new TextField();
        Button searchForOwner = new Button("Search by Owner");

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

        Button listAll = new Button("List All Properties");
        this.taxData = new Label("");
        Button searchQuit = new Button("Quit");
        searchQuit.setOnAction(e -> mainStage.setScene(adminMenu));
        HBox searchTaxFields = new HBox();
        searchTaxFields.getChildren().addAll(searchOwner, searchForOwner, firstline, firstLine, secondline, secondLine, city, this.city, county, this.county, country, this.country, searchForAddress, listAll, taxData, searchQuit);
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
        overdueQuit.setOnAction(e -> mainStage.setScene(adminMenu));
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
        generalQuit.setOnAction(e -> mainStage.setScene(adminMenu));
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
        String firstline = this.firstLine.getText();
        String secondline = this.secondLine.getText();
        String city = this.city.getText();
        String county = this.county.getText();
        String country = this.country.getText();
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

    public Scene getPropetyScene(ArrayList<Property> properties) {
        ChoiceBox<Property> propertyChoiceBox =new ChoiceBox<Property>();
        for(Property prop:properties)
            propertyChoiceBox.getItems().add(prop);
        ChoiceBox<Year> propertyTaxChoiceBox = new ChoiceBox();
        TextField paymentInfo = new TextField();
        Button quit = new Button("Quit");

        BorderPane propertyInfoWindow = new BorderPane();
        VBox choiceVBox = new VBox();
        choiceVBox.getChildren().addAll(propertyChoiceBox, propertyTaxChoiceBox, paymentInfo, quit);
        choiceVBox.setPrefWidth(300);
        choiceVBox.setPadding(new Insets(10,100,10,50));
        choiceVBox.setSpacing(6);

        BorderPane propertyBox = new BorderPane();
        VBox propertyInfo = new VBox();
        VBox propertyTaxInfo = new VBox();
        propertyBox.setTop(propertyInfo);
        propertyBox.setBottom(propertyTaxInfo);
        propertyChoiceBox.setOnAction(e->{
            propertyBox.setTop(getAddressVBox(propertyChoiceBox.getValue()));
            propertyTaxChoiceBox.getItems().clear();
            for(PropertyTax tax:propertyChoiceBox.getValue().getPropertyTaxes())
            propertyTaxChoiceBox.getItems().add(tax.getYear());
        });
        propertyInfoWindow.setLeft(choiceVBox);
        propertyInfoWindow.setCenter(propertyBox);
        return new Scene(propertyInfoWindow, 900, 500);
    }

    public VBox getAddressVBox(Property prop) {
        int fontSize=35;
        VBox result = new VBox();
        Label owners = new Label(prop.getOwners().toString());
        owners.setFont(new Font("",fontSize));
        Label firstLine = new Label(prop.getAddress().getFirstLine());
        firstLine.setFont(new Font("",fontSize));
        Label secondLine = new Label(prop.getAddress().getSecondLine());
        secondLine.setFont(new Font("",fontSize));
        Label city = new Label(prop.getAddress().getCity());
        city.setFont(new Font("",fontSize));
        Label county = new Label(prop.getAddress().getCounty());
        county.setFont(new Font("",fontSize));
        Label country = new Label(prop.getAddress().getCountry());
        country.setFont(new Font("",fontSize));
        Label eircode = new Label(prop.getEircode());
        eircode.setFont(new Font("",fontSize));
        Label marketvalue = new Label(prop.getMarketVal() + "");
        marketvalue.setFont(new Font("",fontSize));
        Label category = new Label(prop.getCategory());
        category.setFont(new Font("",fontSize));
        Label isPrivateRes = new Label(prop.isPrivateRes() + "");
        isPrivateRes.setFont(new Font("",fontSize));
        result.getChildren().addAll(owners, firstLine, secondLine, city, county, country, eircode, marketvalue, category, isPrivateRes);
        return result;
    }

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
    public void handle(ActionEvent e) {
        System.exit(0);
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
