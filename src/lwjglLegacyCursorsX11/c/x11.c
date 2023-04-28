#include <Xcursor/Xcursor.h>
#include <jni.h>

const char *LOOKUP[] = {
	"default",
	"text",
	"crosshair",
	"pointer",
	"ew-resize",
	"ns-resize",
	"nwse-resize",
	"nesw-resize",
	"all-scroll",
	"not-allowed"
};

JNIEXPORT jlong JNICALL Java_io_toadlabs_lwjgllegacycursors_LinuxSystemCursors_nGetDefaultCursorHandle(
		JNIEnv *env, jclass unused, jlong displayPtr, jbyte cursor) {
	if (cursor < 0 || cursor >= sizeof(LOOKUP) / sizeof(*LOOKUP))
		cursor = 0;

	return XcursorLibraryLoadCursor((Display *) displayPtr, LOOKUP[cursor]);
}
