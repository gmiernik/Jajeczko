/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.miernik.jajeczko.presenter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.miernik.jajeczko.JajeczkoService;
import org.miernik.jfxlib.presenter.DialogPresenter;

/**
 *
 * @author Miernik
 */
public class NewTaskPresenter extends DialogPresenter<JajeczkoService> implements Initializable {

    public NewTaskPresenter() {
		super("New task", true);
	}

	@Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
