package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;

public class ErrorMessagePopup extends Popup /*PopupWindow*/ {
	
	private Stage stage;
	private Label errorLabel;
	private BorderPane bp;
	
	public ErrorMessagePopup(Stage stage) {
		super();
		this.stage = stage;
		this.setAutoHide(true);
//		Popup errorPopup = new Popup();
		
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		errorLabel = new Label("NO MESSAGE SET YET");
		//CHANGE LABEL FONT HERE IF NECESSARY
		Button exitButton = new Button("Close");
		
		exitButton.setOnAction(e -> {
			this.hide();
		});
		
		vbox.getChildren().addAll(errorLabel, exitButton);
		vbox.setStyle("-fx-background-color: rgb(244, 244, 244);"
					+ "-fx-background-radius: 10px;" //Radius of background around corners
					+ "-fx-border-radius: 10px;\r\n" //Radius of border around corners - make same as ^
					+ "-fx-border-width: 5px;\r\n" //Thickness of border
					+ "-fx-border-color: black;" //Makes round border black
					+ "-fx-padding: 10px;"); //Extra distance between stuff and borders 
		this.getContent().add(vbox);
//		bp = new BorderPane();
//		bp.setCenter(vbox);
//		vbox.set
//		bp.setBackground(new Background(new BackgroundFill(Color.rgb(244, 244, 24), new CornerRadii(5), Insets.EMPTY)).get);
//		this.getContent().add(bp);
//		this.setScene(new Scene(bp,30,30));
	}
	
	public void show(String message) {
		//Positioning
//		System.out.println(this.getWidth() + " - " + this.getHeight());
		this.setAnchorX(stage.getX() /*- this.getWidth() / 2*/ + stage.getWidth() / 3 /*- 10*/);
		this.setAnchorY(stage.getY() /*- this.getHeight() / 2*/ + stage.getHeight() / 3);
		
		errorLabel.setText(message);
		super.show(stage);
//		Stage testStage = new Stage();
//		stage.setScene(new Scene(bp, 100, 100));
//		super.show(testStage, 0, 0);
	}
	
	//Same thing as the previous show, but has a custom input X and Y value
	public void show(String message, double x, double y) {
		//Positioning
		this.setAnchorX(x);
		this.setAnchorY(y);
		errorLabel.setText(message);
		super.show(stage);
	}
	
}
