package org.miernik.jajeczko.presenter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AcceptanceDialog {
	
	private final Stage stage;
	private DialogResultHandler handler; 
		
	protected Stage getStage() {
		return stage;
	}
	
	public AcceptanceDialog(String title, String message) {
		stage = new Stage();
		stage.setTitle(title);
		stage.setScene(new Scene(buildLayout(message)));
		stage.initModality(Modality.APPLICATION_MODAL);
	}
	
	private Parent buildLayout(String message) {
		VBox mainBox = new VBox(8);
		mainBox.setPadding(new Insets(8));
		mainBox.setMinWidth(200);
		Label messageLabel = new Label(message);
		HBox buttonBox = new HBox(8);
		buttonBox.setAlignment(Pos.CENTER_RIGHT);
		mainBox.getChildren().addAll(messageLabel, buttonBox);
		Button btnYes = new Button("Yes");
		btnYes.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				close(DialogResult.Yes);
			}
		});
		Button btnNo = new Button("No");
		btnNo.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				close(DialogResult.No);
			}
		});
		buttonBox.getChildren().addAll(btnYes, btnNo);
		return mainBox;
	}
	
	private void close(DialogResult result) {
		if (handler!=null)
			handler.handle(result);
		stage.close();
	}
	
	public void show() {
		stage.show();
	}

	public void setDialogResultHandler(DialogResultHandler handler) {
		this.handler = handler;
	}
}
