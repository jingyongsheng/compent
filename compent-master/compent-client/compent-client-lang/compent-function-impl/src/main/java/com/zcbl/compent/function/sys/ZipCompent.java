package com.zcbl.compent.function.sys;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.SystemCompent;

public class ZipCompent extends SystemCompent
{
	/**
	 * 
	 * 使用gzip进行压缩
	 */
	public static String gzip(String primStr)
	{
		if (primStr == null || primStr.length() == 0)
		{
			return primStr;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		GZIPOutputStream gzip = null;
		try
		{
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes());
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (gzip != null)
			{
				try
				{
					gzip.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return new sun.misc.BASE64Encoder().encode(out.toByteArray());
	}

	/**
	 *
	 * <p>
	 * Description:使用gzip进行解压缩
	 * </p>
	 * 
	 * @param compressedStr
	 * @return
	 */
	public static String gunzip(String compressedStr)
	{
		if (compressedStr == null)
		{
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		String decompressed = null;
		try
		{
			compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);

			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1)
			{
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (ginzip != null)
			{
				try
				{
					ginzip.close();
				} catch (IOException e)
				{
				}
			}
			if (in != null)
			{
				try
				{
					in.close();
				} catch (IOException e)
				{
				}
			}
			if (out != null)
			{
				try
				{
					out.close();
				} catch (IOException e)
				{
				}
			}
		}

		return decompressed;
	}

	/**
	 * 使用zip进行压缩
	 * 
	 * @param str
	 *            压缩前的文本
	 * @return 返回压缩后的文本
	 */
	public static final String zip(String str)
	{
		if (str == null)
			return null;
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;
		String compressedStr = null;
		try
		{
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(str.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();
			compressedStr = new sun.misc.BASE64Encoder().encodeBuffer(compressed);
		} catch (IOException e)
		{
			compressed = null;
		} finally
		{
			if (zout != null)
			{
				try
				{
					zout.close();
				} catch (IOException e)
				{
				}
			}
			if (out != null)
			{
				try
				{
					out.close();
				} catch (IOException e)
				{
				}
			}
		}
		return compressedStr;
	}

	/**
	 * 使用zip进行解压缩
	 * 
	 * @param compressed
	 *            压缩后的文本
	 * @return 解压后的字符串
	 */
	public static final String unzip(String compressedStr)
	{
		if (compressedStr == null)
		{
			return null;
		}

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed = null;
		try
		{
			byte[] compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1)
			{
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e)
		{
			decompressed = null;
		} finally
		{
			if (zin != null)
			{
				try
				{
					zin.close();
				} catch (IOException e)
				{
				}
			}
			if (in != null)
			{
				try
				{
					in.close();
				} catch (IOException e)
				{
				}
			}
			if (out != null)
			{
				try
				{
					out.close();
				} catch (IOException e)
				{
				}
			}
		}
		return decompressed;
	}

	public static void main(String[] args)
	{
	}

	@Override
	public void execute(Compent compent, Input in, Output out) throws Exception
	{
		String type = (String) in.getSys().get("zip");
		if (type == null)
		{
			return;
		}
		String content = (String) in.getUser().get("content");
		if (type.equals("gzip"))
		{
			out.getUser().put("result", gzip(content));
		} else if (type.equals("ungzip"))
		{
			out.getUser().put("result", gunzip(content));

		} else if (type.equals("zip"))
		{
			out.getUser().put("result", zip(content));

		} else if (type.equals("unzip"))
		{
			out.getUser().put("result", unzip(content));
		}
	}

	@Override
	public void load()
	{
		CmdFactory.getInstance().addCmd(ZipCompent.class.getName(), this);
	}
}
