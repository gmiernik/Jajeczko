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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jajeczko.event.RunTimerEvent;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.presenter.AbstractPresenter;

/**
 * 
 * @author Miernik
 */
public class TodayToDoPresenter extends AbstractPresenter<JajeczkoService>
		implements Initializable {

	@FXML
	private TableView<Task> taskTable;
	@FXML
	private Button addButton;
	@FXML
	private Button startButton;
	private ObservableList<Task> tasks;
	private TableColumn name2;

	public ObservableList<Task> getTasks() {
		if (tasks == null)
			tasks = getService().getTodayTasks();
		return tasks;
	}

	@Override
	public void show() {
		taskTable.setItems(getTasks());
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				Task t = taskTable.getSelectionModel().getSelectedItem();
				getEventBus().fireEvent(new RunTimerEvent(t));
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
		taskTable.edit(s.getSelectedIndex(), name2);

	}

}
