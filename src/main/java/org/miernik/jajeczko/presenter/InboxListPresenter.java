package org.miernik.jajeczko.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

import org.apache.log4j.Logger;
import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jajeczko.event.AddTaskEvent;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.event.EventListener;
import org.miernik.jfxlib.presenter.BasePresenter;

public class InboxListPresenter extends BasePresenter<JajeczkoService> {

	final static Logger logger = Logger.getLogger(InboxListPresenter.class);

	@FXML
	private Pane inboxList;
	@FXML
	private TableView<Task> taskTable;
	@FXML
	private Button addButton;

	@Override
	public void onShow() {
		logger.debug("run onShow() method");
		ObservableList<Task> tasks = FXCollections.observableList(getService()
				.getOpenedTasks());
		taskTable.setItems(tasks);
	}

	@Override
	public void onInit() {
		getEventBus().addListener(new EventListener<AddTaskEvent>() {

			@Override
			public void performed(AddTaskEvent arg0) {
				taskTable.getItems().add(arg0.getTask());
			}
		});
		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				fireAction("NewTask");
			}
		});
	}
}
