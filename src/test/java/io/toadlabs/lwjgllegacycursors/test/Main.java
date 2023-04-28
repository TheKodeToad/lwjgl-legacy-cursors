package io.toadlabs.lwjgllegacycursors.test;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import io.toadlabs.lwjgllegacycursors.SystemCursors;

public class Main {

	public static void main(String[] args) throws LWJGLException {
		Display.setResizable(true);
		Display.setDisplayMode(new DisplayMode(800, 600));
		Display.create();
		while (!Display.isCloseRequested()) {
			SystemCursors.setCursor((byte) (9));
			Display.sync(60);
			Display.update();
		}
		Display.destroy();
	}

}
