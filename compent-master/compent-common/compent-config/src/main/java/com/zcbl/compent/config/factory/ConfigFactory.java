package com.zcbl.compent.config.factory;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.zcbl.compent.config.impl.GlobalConfig;

/**
 * @author jys 2016年11月8日
 */
public class ConfigFactory {
	public static List<ConfigInter> list = new ArrayList<ConfigInter>();

	public static void anysis() {
		list.add(GlobalConfig.getInstance());
		analysis("/");
	}

	public static void addConfigInter(ConfigInter iter) {
		list.add(iter);
		analysis("/");
	}

	public static void analysis(String path) {
		try {
			URL url = ConfigFactory.class.getResource(path);
			File file = new File(url.toURI().getPath());
			File[] files = file.listFiles(new PatternFilenameUtils());
			if (files != null && files.length > 0) {
				for (File f : files) {
					build("/" + f.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void build(String path) {
		SAXReader reader = new SAXReader();
		InputStream inputStream = ConfigFactory.class.getResourceAsStream(path);
		try {
			Document d = reader.read(inputStream);
			if (inputStream != null) {
				for (ConfigInter inter : list) {
					inter.anaylsis(d);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
				reader = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void build(InputStream inputStream) {
		SAXReader reader = new SAXReader();
		if (inputStream != null) {
			try {
				Document d = reader.read(inputStream);
				for (ConfigInter inter : list) {
					inter.anaylsis(d);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
					reader = null;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}
}
