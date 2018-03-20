package com.zcbl.compent.restful.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author jys 2016年11月10日
 */
public class ResponseCompent extends HttpServletResponseWrapper
{
	private ResponsePrintWriter writer;
	private ByteArrayOutputStream buffer;
	private ServletOutputStream out = null;

	private class ResponsePrintWriter extends PrintWriter
	{
		OutputStreamWriter output;

		public ResponsePrintWriter(OutputStreamWriter output)
		{
			super(output);
			this.output = output;
		}

		public OutputStreamWriter getOutputStreamWriter()
		{
			return output;
		}
	}

	private class WapperedOutputStream extends ServletOutputStream
	{
		private ByteArrayOutputStream bos = null;

		public WapperedOutputStream(ByteArrayOutputStream stream) throws IOException
		{
			bos = stream;
		}

		@Override
		public void write(int b) throws IOException
		{
			bos.write(b);
		}

		@Override
		public void write(byte[] b) throws IOException
		{
			bos.write(b, 0, b.length);
		}
	}

	public ResponseCompent(HttpServletResponse response) throws IOException
	{
		super(response);
		buffer = new ByteArrayOutputStream();
		writer = new ResponsePrintWriter(new OutputStreamWriter(buffer, this.getCharacterEncoding()));
		out = new WapperedOutputStream(buffer);
	}

	@Override
	public PrintWriter getWriter() throws UnsupportedEncodingException
	{
		return writer;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException
	{
		return out;
	}

	@Override
	public void flushBuffer() throws IOException
	{
		if (out != null)
		{
			out.flush();
		}
		if (writer != null)
		{
			writer.flush();
		}
	}

	@Override
	public void reset()
	{
		buffer.reset();
	}

	/**
	 * 
	 * @return
	 */
	public byte[] getReuslt() throws IOException
	{
		flushBuffer();
		return buffer.toByteArray();
	}
}
