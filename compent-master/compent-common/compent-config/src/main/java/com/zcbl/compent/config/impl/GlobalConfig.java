package com.zcbl.compent.config.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.dom4j.Node;

import com.zcbl.compent.config.factory.ConfigInter;

public class GlobalConfig implements ConfigInter {

	private static GlobalConfig config = new GlobalConfig();
	private Map<String, Map<String, Object>> global = new ConcurrentHashMap<String, Map<String, Object>>();
	private Map<String, List<Map<String, Object>>> type = new ConcurrentHashMap<String, List<Map<String, Object>>>();
	private byte[] b = new byte[0];

	private GlobalConfig() {
	}

	public Map<String, Map<String, Object>> getGlobal() {
		return global;
	}

	public List<Map<String, Object>> getGlobalByType(String key) {
		return type.get(key);
	}

	public static GlobalConfig getInstance() {
		return config;
	}

	@Override
	public void anaylsis(Document doc) {
		synchronized (b) {
			try {
				global.clear();
				type.clear();
				List<Node> nodeList = doc.selectNodes("//zc-compent/*");
				for (Node n : nodeList) {
					@SuppressWarnings("unchecked")
					List<Node> child = n.selectNodes("*");
					Map<String, Object> map = new HashMap<String, Object>();
					for (Node node : child) {
						if (node.getName().toLowerCase().equals("unique")) {
							if (global.get(node.getStringValue()) != null) {
								global.put(node.getStringValue(), map);
							} else {
								global.put(node.getStringValue(), map);
							}
							map.put(node.getName(), node.getStringValue());
						} else if (node.getName().toLowerCase().equals("compent_type")) {
							List<Map<String, Object>> list = type.get(node.getStringValue());
							if (list == null) {
								list = new ArrayList<Map<String, Object>>();
								list.add(map);
								type.put(node.getStringValue(), list);
							} else {
								list.add(map);
							}
						} else {
							map.put(node.getName(), node.getStringValue());
						}
					}
				}
			} catch (

			Exception e) {
				e.printStackTrace();
			}
		}
	}
}
