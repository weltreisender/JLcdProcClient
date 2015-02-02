package org.awi.jlcdproc.impl;

import org.awi.jlcdproc.io.Connection;

public interface LcdProcInternal extends EventListenerProvider {

	public Connection getConnection();
}
