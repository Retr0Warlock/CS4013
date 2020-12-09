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
public class Menu_GUI extends Application {
    Stage window;
    Scene mainMenu,ownerMenu, AdminMenu, AddPropertyMenu;
    TextField Names, Firstline, Secondline, City, County, Eircode, MarketValue, Country;
    ChoiceBox<String> Catagory, PrivateRes;
    private final PropertyFiler filer = new PropertyFiler();
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        window = stage;
        //Main menu
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


        //OwnerMenu
        Button ownerButton1 = new Button("Register a Property");
        ownerButton1.setOnAction(e-> window.setScene(AddPropertyMenu));
        Button ownerButton2 = new Button("My Properties");
        Button ownerButton3 = new Button("Quit");
        ownerButton3.setOnAction(e->window.setScene(mainMenu));
        HBox ownerMenuButtons = new HBox();
        ownerMenuButtons.getChildren().addAll(ownerButton1, ownerButton2, ownerButton3);
        VBox ownerMenuParent = new VBox();
        Label ownerMenuLabel = new Label("Choose Owner menu option");
        ownerMenuParent.getChildren().addAll(ownerMenuLabel, ownerMenuButtons);
        ownerMenu = new Scene(ownerMenuParent, 500, 500);
        
        
        //Register A Property
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
        
        HBox AddPropertyFields = new HBox();
        AddPropertyFields.getChildren().addAll(names, Names, firstline, Firstline, secondline, Secondline, city, City, county, County, country, Country, eircode, Eircode, MarketVal, MarketValue, catagory, Catagory, privateRes, PrivateRes, submitNew);
        VBox AddPropertyParent = new VBox();
        Label AddPropertyLabel = new Label("Fill All Fields");
        AddPropertyParent.getChildren().addAll(AddPropertyLabel, AddPropertyFields);
        AddPropertyMenu = new Scene(AddPropertyParent, 1000, 1000);
        
        
        
        //My Property
        
                
        
        
        
        //AdminMenu
        Button AdminButton1 = new Button("Search Tax Data");
        Button AdminButton2 = new Button("Overdue Tax");
        Button AdminButton3 = new Button("General Tax Statistics");
        Button AdminButton4 = new Button("Quit");
        AdminButton4.setOnAction(e->window.setScene(mainMenu));
        HBox AdminMenuButtons = new HBox();
        AdminMenuButtons.getChildren().addAll(AdminButton1, AdminButton2, AdminButton3, AdminButton4);
        VBox AdminMenuParent = new VBox();
        Label AdminMenuLabel = new Label("Choose Admin menu option");
        AdminMenuParent.getChildren().addAll(AdminMenuLabel, AdminMenuButtons);
        AdminMenu = new Scene(AdminMenuParent, 500, 500);
        
        
        
        
        stage.setScene(mainMenu);
        stage.show();
    }
    
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
        Property newProp = new Property(owners, address, eircode, marketVal, catagory, privateRes);
        filer.add(newProp);    
    }
}

class QuitHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent e) {
        System.exit(0);
    }
}
