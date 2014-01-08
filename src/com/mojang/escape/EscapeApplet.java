package com.mojang.escape;

import java.applet.Applet;
import java.awt.BorderLayout;

public class EscapeApplet extends Applet {
	private static final long serialVersionUID = 1L;

	private EscapeComponent escapeComponent = new EscapeComponent();

	@Override
	public void init() {
		setLayout(new BorderLayout());
		add(escapeComponent, BorderLayout.CENTER);
	}

	@Override
	public void start() {
		escapeComponent.start();
	}

	@Override
	public void stop() {
		escapeComponent.stop();
	}

}