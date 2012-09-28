/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jajeczko.presenter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;
import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jajeczko.event.AddTaskEvent;
import org.miernik.jajeczko.model.Status;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.dialogs.MessageBox;
import org.miernik.jfxlib.event.EventListener;
import org.miernik.jfxlib.presenter.AbstractPresenter;

/**
 * 
 * @author Miernik
 */
public class TodayToDoPresenter extends AbstractPresenter<JajeczkoService>
		implements Initializable {

	final static Logger logger = Logger.getLogger(TodayToDoPresenter.class);

	@FXML
	private Pane todayToDo;
	@FXML
	private TableView<Task> taskTable;
	@FXML
	private Button addButton;
	@FXML
	private Button startButton;
	@FXML
	private Button doneButton;
	private boolean initiated;

	@Override
	public void show() {
		logger.debug("run show() method");
		ObservableList<Task> tasks = FXCollections.observableList(getService()
				.getTodayTasks());
		taskTable.setItems(tasks);
		if (!initiated) {
			getEventBus().addListener(new EventListener<AddTaskEvent>() {

				@Override
				public void performed(AddTaskEvent arg0) {
					taskTable.getItems().add(arg0.getTask());
				}
			});
			initiated = true;
		}
	}

	private Task getSelectedTask() {
		return taskTable.getSelectionModel().getSelectedItem();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		todayToDo.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				if (arg0.getCode() == KeyCode.INSERT) {
					fireAction("NewTask");
				}
			}
		});
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Task t = taskTable.getSelectionModel().getSelectedItem();
				if (t == null)
					// TODO: prepare method to read text from Resource Bundle
					(new MessageBox("Start zegara",
							"Musisz wybrać zadanie do realizacji.")).show();
				else {
					getService().getTimer().startWork(t);
					getView().getScene().getWindow().hide();
				}
			}
		});
		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				fireAction("NewTask");
			}
		});
		doneButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				final Task t = getSelectedTask();
				if (t.getStatus() == Status.IN_PROGRESS) {
					AcceptanceDialog d = new AcceptanceDialog("Complete task",
							"Czy jesteś pewien, że ukończyłeś to zadanie? \n"
									+ "Opis zadania: " + t.getName());
					d.setDialogResultHandler(new DialogResultHandler() {

						@Override
						public void handle(DialogResult result) {
							if (result == DialogResult.Yes)
								getService().completeTask(t);
						}
					});
					d.show();
				} else {
					new MessageBox(
							"Complete task",
							"Nie można zakończyć zadania bez jego realizacji! \n"
									+ "Usuń zadanie lub rozpocznij nad nim prace.")
							.show();
				}
			}
		});
	}

}
