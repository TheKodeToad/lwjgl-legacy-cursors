package io.toadlabs.lwjgllegacycursors;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.InputImplementation;

final class LinuxSystemCursors {

	private static final Long[] CACHE = new Long[SystemCursors.SIZE];
	private static MethodHandle getDisplayMethod;
	private static MethodHandle getInputImplementationMethod;
	private static boolean supported = true;

	static {
		try {
			Class<?> linuxDisplay = Class.forName("org.lwjgl.opengl.LinuxDisplay");
			Method getDisplay = linuxDisplay.getDeclaredMethod("getDisplay");
			getDisplay.setAccessible(true);
			getDisplayMethod = MethodHandles.lookup().unreflect(getDisplay);

			Method getImplementation = Mouse.class.getDeclaredMethod("getImplementation");
			getImplementation.setAccessible(supported);
			getInputImplementationMethod = MethodHandles.lookup().unreflect(getImplementation);

			System.loadLibrary("lwjglLegacyCursorsX11");
		} catch (Throwable error) {
			System.err.println("Failed to load Linux natives");
			error.printStackTrace();
			supported = false;
		}
	}

	public static void setCursor(byte cursor) throws LWJGLException {
		if (!supported)
			return;

		getInputImplementation().setNativeCursor(getDefaultCursorHandle(cursor));
	}

	private static long getDefaultCursorHandle(byte cursor) {
		if (CACHE[cursor] != null)
			return CACHE[cursor];

		return CACHE[cursor] = nGetDefaultCursorHandle(getDisplay(), cursor);
	}

	private static long getDisplay() {
		try {
			return (long) getDisplayMethod.invokeExact();
		} catch (Throwable error) {
			Util.sneakyThrow(error);
			throw null;
		}
	}

	public static InputImplementation getInputImplementation() {
		try {
			return (InputImplementation) getInputImplementationMethod.invokeExact();
		} catch (Throwable error) {
			Util.sneakyThrow(error);
			throw null;
		}
	}

	private static native long nGetDefaultCursorHandle(long display, byte cursor);

}
