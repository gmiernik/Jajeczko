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
import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jajeczko.model.Task;
import org.miernik.jfxlib.presenter.AbstractPresenter;

/**
 *
 * @author Miernik
 */
public class TodayToDoPresenter extends AbstractPresenter<JajeczkoService> implements Initializable{

    @FXML
    private TableView<Task> taskTable;
    @FXML
    private Button addButton;
    private ObservableList<Task> tasks;

    public ObservableList<Task> getTasks() {
        if (tasks==null)
            tasks = getService().getTodayTasks();
        return tasks;
    }
        
    @Override
    public void show() {
        super.show();
        taskTable.setItems(getTasks());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                fireAction("NewTask");
            }
        });
        
        
//        taskTable.setEditable(true);
//        name2 = new TableColumn("name2");
//        name2.setEditable(true);
//        taskTable.getColumns().add(name2);
//        name2.setPrefWidth(200);
//        Callback<TableColumn, TableCell> callFactory = new Callback<TableColumn, TableCell>() {
//
//            @Override
//            public TableCell call(TableColumn p) {
//                return new EditingCell();
//            }
//        };
//        name2.setCellFactory(callFactory);
//        name2.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
    }

    public void addTask() {
        taskTable.setEditable(true);
        taskTable.requestFocus();
        Task newTask = new Task(0, "");
        taskTable.getItems().add(newTask);
        TableView.TableViewSelectionModel<Task> s = taskTable.getSelectionModel();
        s.select(newTask);
        taskTable.edit(s.getSelectedIndex(), taskTable.getColumns().get(0));
        taskTable.setEditable(true);
    }
    
}
