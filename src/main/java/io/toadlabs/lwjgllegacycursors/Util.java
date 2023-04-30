package io.toadlabs.lwjgllegacycursors;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.InputImplementation;

final class Util {

	private static MethodHandle getInputImplementationMethod;
	private static MethodHandle getDisplayImplementationMethod;

	static {
		try {
			Method getInputImplementation = Mouse.class.getDeclaredMethod("getImplementation");
			getInputImplementation.setAccessible(true);
			getInputImplementationMethod = MethodHandles.lookup().unreflect(getInputImplementation);

			Method getDisplayImplementation = Display.class.getDeclaredMethod("getImplementation");
			getDisplayImplementation.setAccessible(true);
			getDisplayImplementationMethod = MethodHandles.lookup().unreflect(getDisplayImplementation);
		} catch (Throwable error) {
			error.printStackTrace();
		}
	}

	public static RuntimeException sneakyThrow(Throwable error) {
		if (error instanceof Error)
			throw (Error) error;
		if (error instanceof RuntimeException)
			throw (RuntimeException) error;
		if (error instanceof IOException)
			throw new UncheckedIOException((IOException) error);

		throw new IllegalStateException(error);
	}

	public static boolean foundInputImplementationMethod() {
		return getInputImplementationMethod != null;
	}

	public static InputImplementation getInputImplementation() {
		try {
			return (InputImplementation) getInputImplementationMethod.invokeExact();
		} catch (Throwable error) {
			throw sneakyThrow(error);
		}
	}

	public static Object getDisplayImplementation() {
		try {
			return getDisplayImplementationMethod.invoke();
		} catch (Throwable error) {
			throw sneakyThrow(error);
		}
	}

}
