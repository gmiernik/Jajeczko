/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jajeczko.presenter;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jajeczko.event.CloseTimerEvent;
import org.miernik.jfxlib.event.EventListener;
import org.miernik.jfxlib.presenter.MainWindowPresenter;
import org.miernik.jfxlib.presenter.Presenter;

/**
 * 
 * @author Miernik
 */
public class MainPresenter extends MainWindowPresenter<JajeczkoService> {

	final static Logger logger = Logger.getLogger(MainPresenter.class);
	
	public MainPresenter(Stage s) {
		super(s);
	}

	@FXML
	private MenuItem menuClose;
	@FXML
	private MenuItem menuAbout;
	@FXML
	private MenuItem menuInboxList;
	@FXML
	private MenuItem menuTodayToDo;
	@FXML
	private MenuItem menuProjects;
	@FXML
	private AnchorPane mainContent;
	private Presenter inboxList;
	private Presenter todayToDo;
	private Presenter projects;
	private double width;
	private double height;

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

	@Override
	public void onInit() {
		getScene().getStylesheets().add("styles/stylesheet.css");
		menuClose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				hide();
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
		menuInboxList.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setMainContent(inboxList);
			}
		});
		menuTodayToDo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				setMainContent(getTodayToDo());
			}
		});
		getEventBus().addListener(new EventListener<CloseTimerEvent>() {

			@Override
			public void performed(CloseTimerEvent arg) {
				show();
			}
		});
	}
	
	@Override
	protected void onShow() {
		logger.debug("run onShow method");
		if (mainContent.getChildren().isEmpty())
			setMainContent(getInboxList());
		if (width!=0 && height!=0) {
			logger.debug("restore window size");
			getWindow().setHeight(height);
			getWindow().setWidth(width);
		}
	}
	
	@Override
	protected void onHide() {
		logger.debug("run onHide method");
		width = getWindow().getWidth();
		height = getWindow().getHeight();
	}

	public void setMainContent(Presenter p) {
		ObservableList<Node> list = this.mainContent.getChildren();
		list.clear();
		list.add(p.getView());
	}

	public Presenter getInboxList() {
		return inboxList;
	}

	public void setInboxList(Presenter inboxList) {
		this.inboxList = inboxList;
	}
}
