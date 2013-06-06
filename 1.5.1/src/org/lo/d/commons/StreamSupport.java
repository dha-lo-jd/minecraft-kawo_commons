package org.lo.d.commons;

import java.io.Closeable;
import java.io.IOException;

public class StreamSupport {

	public static boolean quietClose(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
				return true;
			} catch (IOException e) {
			}
		}
		return false;
	}
}
