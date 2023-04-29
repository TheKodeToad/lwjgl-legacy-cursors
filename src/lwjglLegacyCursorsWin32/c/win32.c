#include <winuser.h>

JNIEXPORT jlong JNICALL Java_io_toadlabs_lwjgllegacycursors_Win32SystemCursors_nGetDefaultCursorHandle(
		JNIEnv *env, jclass unused, jbyte cursor) {
	switch (cursor) {
		case 0:
		default:
			return LoadCursor(NULL, IDC_ARROW);
		case 1:
			return LoadCursor(NULL, IDC_IBEAM);
		case 2:
			return LoadCursor(NULL, IDC_CROSSHAIR);
		case 3:
			return LoadCursor(NULL, IDC_HAND);
		case 4:
			return LoadCursor(NULL, IDC_SIZEEW);
		case 5:
			return LoadCursor(NULL, IDC_SIZENS);
		case 6:
			return LoadCursor(NULL, IDC_SIZENWSE);
		case 7:
			return LoadCursor(NULL, IDC_SIZENESW);
		case 8:
			return LoadCursor(NULL, IDC_SIZEALL);
		case 9:
			return LoadCursor(NULL, IDC_NO);
	}
}

JNIEXPORT void JNICALL Java_io_toadlabs_lwjgllegacycursors_Win32SystemCursors_nSetCursor(
		JNIEnv *env, jclass unused, jlong hwnd, jlong cursor) {
	SetClassLongPtr((HWND) hwnd, (HCURSOR) GCLP_HCURSOR, NULL);
	SetCursor(cursor);
}
