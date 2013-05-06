package cn._2dland.common.httpclient;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileInputStreamWrapper extends FilterInputStream {

	private IInputStreamReadHandler handler = null;

	public FileInputStreamWrapper(InputStream in, IInputStreamReadHandler handler) {
		super(in);
		this.handler = handler;
	}

	@Override
	public int read() throws IOException {
		int b = super.read();
		if(handler != null && b >= 0) 
			handler.onRead(1);
		return b;
	}

	@Override
	public int read(byte[] b) throws IOException {
		int len = super.read(b);
		if(handler != null && len >= 0) 
			handler.onRead(len);
		return len;
	}

}
