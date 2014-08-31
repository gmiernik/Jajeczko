package org.miernik.jajeczko;

import org.apache.log4j.Logger;


public class AppException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4369330336457015827L;
	
	final static Logger logger = Logger.getLogger(AppException.class);
	
	public AppException(String message) {
		super(message);
		logger.error(message);
	}

}
