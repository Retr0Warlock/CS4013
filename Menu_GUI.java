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

public class Menu_GUI extends Application {
    Stage window;
    Scene mainMenu,ownerMenu;

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
        Button option3 = new Button("Quit");
        HBox buttons = new HBox();
        buttons.getChildren().addAll(options1, option2, option3);

        VBox main = new VBox();

        Label header = new Label("Choose user type");
        header.setFont(new Font("Arial", 20));
        main.getChildren().addAll(header, buttons);
        main.setPrefSize(500, 500);
        mainMenu = new Scene(main, 500, 500);


        //Ownermenu
        Button ownerButton1 = new Button("option1");
        Button ownerButton2 = new Button("option2");
        Button ownerButton3 = new Button("option3");
        HBox ownerMenuButtons = new HBox();
        ownerMenuButtons.getChildren().addAll(ownerButton1, ownerButton2, ownerButton3);
        VBox ownerMenuParent = new VBox();
        Label ownerMenuLabel = new Label("Choose owner menu option");
        ownerMenuParent.getChildren().addAll(ownerMenuLabel, ownerMenuButtons);
        ownerMenu = new Scene(ownerMenuParent, 500, 500);

        stage.setScene(mainMenu);
        stage.show();
    }
}
