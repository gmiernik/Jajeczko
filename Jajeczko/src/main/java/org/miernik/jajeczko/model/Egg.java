package org.miernik.jajeczko.model;

import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Miernik
 * @version 1.0 @created 22-cze-2012 16:49:08
 */
public class Egg {

    private SimpleIntegerProperty interruptionInside = new SimpleIntegerProperty();
    private SimpleIntegerProperty interruptionOutside = new SimpleIntegerProperty();
    private ObjectProperty<Date> startTime = new SimpleObjectProperty<>();

    public int getInterruptionInside() {
        return interruptionInside.get();
    }

    public void setInterruptionInside(int interruptionInside) {
        this.interruptionInside.set(interruptionInside);
    }

    public int getInterruptionOutside() {
        return interruptionOutside.get();
    }

    public void setInterruptionOutside(int interruptionOutside) {
        this.interruptionOutside.set(interruptionOutside);
    }

    public Date getStartTime() {
        return startTime.get();
    }

    public void setStartTime(Date startTime) {
        this.startTime.set(startTime);
    }
}