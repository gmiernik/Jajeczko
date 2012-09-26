package org.miernik.jajeczko;

import org.miernik.jajeczko.model.JajeczkoTimer;

public abstract class JajeczkoServiceBase implements JajeczkoService {
    private JajeczkoTimer timer = new JajeczkoTimer();

	@Override
	public JajeczkoTimer getTimer() {
		return timer;
	}
	
	@Override
	public void dispose() {
		timer.dispose();
	}
}
