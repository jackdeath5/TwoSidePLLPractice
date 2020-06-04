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

/**
 * This class is used a simple, reusable error message for the purpose of displaying short errors. It is currently 
 * utilized for displaying the errors that arise from FileIO. The .show() method is used to display the message on 
 * the same Stage with a new customized message each time.
 * @author Jack Lanois
 */
public class ErrorMessagePopup extends Popup /*PopupWindow*/ {
	
	/** The Stage the pop-up is a part of.**/
	private Stage stage;
	/** The label used as the message given for the pop-up.**/
	private Label errorLabel;
	/** The borderpane used as the base object to put all of the elements onto.**/
	private BorderPane bp;
	
	/**
	 * Constructs an error message for the particular Stage/window. By setting it here, it 
	 * does not need to be changed later on and the pop-up can be reused.
	 * @param stage The Stage for the window that the pop-up will be a part of.
	 */
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
	
	/**
	 * This method is used to show the pop-up with a new message. The pop-up stays part of the same stage, so it is 
	 * part of the same window, and by using this method, you can reuse the pop-up because the message it has can 
	 * be set differently.
	 * @param message String for the message to be displayed on the pop-up.
	 */
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
	/**
	 * This method is used to show the pop-up with a new message. The pop-up stays part of the same stage, so it is 
	 * part of the same window, and by using this method, you can reuse the pop-up because the message it has can 
	 * be set differently. Difference with this method is that you can specify where the method appears.
	 * @param message String for the message to be displayed on the pop-up.
	 * @param x Double for the X coordinate of pop-up on the window.
	 * @param y Double for the y coordinate of pop-up on the window.
	 */
	public void show(String message, double x, double y) {
		//Positioning
		this.setAnchorX(stage.getX() + x);
		this.setAnchorY(stage.getY() + y);
		errorLabel.setText(message);
		super.show(stage);
	}
	
}
