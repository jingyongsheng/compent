package com.zcbl.compent.language.respertories.expression;

import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.zcbl.compent.language.respertories.Command;
import com.zcbl.compent.language.respertories.CommandFactory.CommandStack;
import com.zcbl.compent.language.respertories.Function;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.LanguageFactory;
import com.zcbl.compent.task.client.annation.SysLoader;
import com.zcbl.compent.task.client.sys.Loader;

@SysLoader
public class For extends Lang implements Loader {

	@Override
	public void load() {
		LanguageFactory.getInstance().addLang("for", this);
	}

	@Override
	public void excute(Command command, List<Function> function, Input input, CommandStack stack) throws Exception {
		try {
			String end = (String) input.getSys().get("end");
			String start = (String) input.getSys().get("begin");
			String step = (String) input.getSys().get("step");
			String var = (String) input.getSys().get("var");
			Object data = input.getSys().get("list");
			if (data != null && (data instanceof List)) {
				List<Map<String, Object>> list = (List<Map<String, Object>>) data;
				if (list != null && list.size() > 0) {
					int s = Integer.parseInt(start == null || start.equals("") ? "0" : start);
					int e = Integer.parseInt(end == null || end.equals("") ? "0" : end);
					int _end = list.size();
					if (e < _end && e > 0) {
						_end = e;
					}
					int sep = Integer.parseInt(step == null || step.equals("") ? "1" : step);
					for (int i = s; i < _end; i = i + sep) {
						if (function != null && !function.isEmpty()) {
							for (Function f : function) {
								if (f != null)
									f.excute(input, stack);
							}
						} else {
							Map<String, Object> obj = (Map<String, Object>) list.get(i);
							stack.getMap().put(var, obj);
							if (!command.getCommmand().isEmpty()) {
								for (Command c : command.getCommmand()) {
									c.excute(stack);
								}
							}
						}
					}
				}
			} else {
				System.out.println("object cannot transfer List");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void anysis(Command cmd, Element element) throws Exception {
		// TODO Auto-generated method stub
	}

}
