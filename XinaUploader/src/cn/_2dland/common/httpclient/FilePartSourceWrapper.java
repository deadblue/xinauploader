package cn._2dland.common.httpclient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.methods.multipart.FilePartSource;

public class FilePartSourceWrapper extends FilePartSource {

	/** 读取处理器 */
	private IInputStreamReadHandler handler = null;

	public FilePartSourceWrapper(File file, IInputStreamReadHandler handler) throws FileNotFoundException {
		super(file);
		this.handler = handler;
	}

	public FilePartSourceWrapper(String fileName, File file, IInputStreamReadHandler handler)
			throws FileNotFoundException {
		super(fileName, file);
		this.handler = handler;
	}

	@Override
	public InputStream createInputStream() throws IOException {
		return new FileInputStreamWrapper(super.createInputStream(), handler);
	}

}
