/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jajeczko.presenter;

import java.net.URL;
import java.util.ResourceBundle;

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
import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jajeczko.event.FinishWorkEvent;
import org.miernik.jajeczko.event.StartWorkEvent;
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

	@FXML
	private Pane todayToDo;
	@FXML
	private TableView<Task> taskTable;
	@FXML
	private Button addButton;
	@FXML
	private Button startButton;
	private ObservableList<Task> tasks;
	private boolean initiated;

	public ObservableList<Task> getTasks() {
		if (tasks == null)
			tasks = getService().getTodayTasks();
		return tasks;
	}

	@Override
	public void show() {
		if (!initiated) {
			taskTable.setItems(getTasks());
			getEventBus().addListener(new EventListener<FinishWorkEvent>() {

				@Override
				public void performed(FinishWorkEvent event) {
					// TODO: improve refresh method
					System.out.println("refreshed ");
				}
			});
			initiated = true;
		}
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
					getEventBus().fireEvent(new StartWorkEvent(t));
					todayToDo.getScene().getWindow().hide();
				}
			}
		});
		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				fireAction("NewTask");
			}
		});

		// taskTable.setEditable(true);
		// name2 = new TableColumn<Task, String>("name2");
		// name2.setEditable(true);
		// taskTable.getColumns().add(name2);
		// name2.setPrefWidth(200);
		// Callback<TableColumn, TableCell> callFactory = new
		// Callback<TableColumn, TableCell>() {
		//
		// @Override
		// public TableCell call(TableColumn p) {
		// return new EditingCell();
		// }
		// };
		// name2.setCellFactory(callFactory);
		// name2.setCellValueFactory(new PropertyValueFactory("name"));
	}

	public void addTask() {
		taskTable.requestFocus();
		Task newTask = new Task("nowe zadanie");
		taskTable.getItems().add(newTask);
		TableView.TableViewSelectionModel<Task> s = taskTable
				.getSelectionModel();
		s.select(newTask);
	}

}
