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
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jajeczko.event.FinishWorkEvent;
import org.miernik.jfxlib.event.EventListener;
import org.miernik.jfxlib.presenter.AbstractMainPresenter;
import org.miernik.jfxlib.presenter.Presenter;

/**
 * 
 * @author Miernik
 */
public class MainPresenter extends AbstractMainPresenter<JajeczkoService>
		implements Initializable {

	@FXML
	private MenuItem menuClose;
	@FXML
	private MenuItem menuAbout;
	@FXML
	private MenuItem menuTodayToDo;
	@FXML
	private MenuItem menuProjects;
	@FXML
	private AnchorPane mainContent;
	private Stage stage;
	private Presenter todayToDo;
	private Presenter projects;
	private boolean initiated;

	public Presenter getProjectsView() {
		return projects;
	}

	public void setProjects(Presenter projects) {
		this.projects = projects;
	}

	public Presenter getTodayToDo() {
		return todayToDo;
	}

	public void setTodayToDo(Presenter todayToDo) {
		this.todayToDo = todayToDo;
	}

	public void setMainContent(Presenter p) {
		ObservableList<Node> list = this.mainContent.getChildren();
		list.clear();
		list.add(p.getView());
		p.show();
	}

	@Override
	public void setMainView(Stage stage) {
		super.setMainView(stage);
		this.stage = stage;
		stage.getScene().getStylesheets().add("styles/stylesheet.css");
	}

	protected Stage getStage() {
		return stage;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		menuClose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				getStage().close();
			}
		});
		menuAbout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				fireAction("About");
			}
		});
		menuProjects.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				setMainContent(getProjectsView());
			}
		});
		menuTodayToDo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				setMainContent(getTodayToDo());
			}
		});
	}

	@Override
	public void show() {
		if (!initiated) {
			setMainContent(getTodayToDo());
			getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent arg0) {
					getService().getTimer().dispose();
				}
			});
			getEventBus().addListener(new EventListener<FinishWorkEvent>() {

				@Override
				public void performed(FinishWorkEvent event) {
					getStage().show();
				}
			});
			initiated = true;
		}
	}

}
