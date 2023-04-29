package io.toadlabs.lwjgllegacycursors;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

import org.lwjgl.LWJGLException;

final class Win32SystemCursors {

	private static final Long[] CACHE = new Long[SystemCursors.SIZE];
	private static MethodHandle getHwndMethod;
	private static boolean supported = true;

	static {
		if (Util.foundInputImplementationMethod()) {
			try {
				Class<?> windowsDisplay = Class.forName("org.lwjgl.opengl.WindowsDisplay");
				Method getHwnd = windowsDisplay.getDeclaredMethod("getHwnd");
				getHwnd.setAccessible(true);
				getHwndMethod = MethodHandles.lookup().unreflect(getHwnd);

				System.loadLibrary("lwjglLegacyCursorsWin32");
			} catch (Throwable error) {
				error.printStackTrace();
				supported = false;
			}
		} else
			supported = false;
	}

	public static void setCursor(byte cursor) throws LWJGLException {
		if (!supported)
			return;

		nSetCursor(getHwnd(), getDefaultCursorHandle(cursor));
	}

	private static long getDefaultCursorHandle(byte cursor) {
		if (CACHE[cursor] != null)
			return CACHE[cursor];

		return CACHE[cursor] = nGetDefaultCursorHandle(cursor);
	}

	private static long getHwnd() {
		try {
			return (long) getHwndMethod.invokeExact(Util.getDisplayImplementation());
		} catch (Throwable error) {
			throw Util.sneakyThrow(error);
		}
	}

	private static native long nGetDefaultCursorHandle(byte cursor);
	private static native void nSetCursor(long hwnd, long cursor);

}
