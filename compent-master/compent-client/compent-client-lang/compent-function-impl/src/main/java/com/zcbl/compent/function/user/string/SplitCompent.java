package com.zcbl.compent.function.user.string;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.UserCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class SplitCompent extends UserCompent {

	public void execute(Compent compent, Input input, Output output) {
		if (input.getUser() != null && !input.getUser().isEmpty()) {
			try {
				if (input.getUser() == null || input.getUser().size() <= 0) {
					return;
				}
				String split = (String) compent.getAttrs().get("split");
				Set<String> set = input.getUser().keySet();
				Iterator<String> ite = set.iterator();
				while (ite.hasNext()) {
					String k = (String) ite.next();
					String value = (String) input.getUser().get(k);
					output.getUser().putAll(split(split, k, value));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Map<String, String> split(String split, String name, String value) {
		Map<String, String> map = new HashMap<String, String>();
		String[] str = value.split("\\" + split);
		if (str.length > 0) {
			for (int i = 0; i < str.length; i++) {
				map.put(name + "_" + i, str[i]);
			}
		}
		return map;
	}

	public void load() {
		CmdFactory.getInstance().addCmd(SplitCompent.class.getName(), this);
	}

	public static void main(String[] args) {
		SplitCompent c = new SplitCompent();
		System.out.println(c.split("^", "a", "12121^0.99^success"));
	}
}
