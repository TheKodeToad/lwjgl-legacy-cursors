package io.toadlabs.lwjgllegacycursors;

final class Util {

	public static void sneakyThrow(Throwable error) {
		if (error instanceof Error)
			throw (Error) error;
		if (error instanceof RuntimeException)
			throw (RuntimeException) error;

		throw new IllegalStateException(error);
	}

}
