package com.jverkamp.chesslike;

import javax.swing.*;

public class Applet extends JApplet {
	private static final long serialVersionUID = 1477865442062549385L;
	
	/**
	 * Initialize the applet.
	 */
	@Override
	public void init() {
		super.init();
		setSize(720, 384);
		add(new MainPanel());
	}
}
