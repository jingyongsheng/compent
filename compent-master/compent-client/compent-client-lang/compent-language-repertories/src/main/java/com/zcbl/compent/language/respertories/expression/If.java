package com.zcbl.compent.language.respertories.expression;

import java.util.List;

import org.dom4j.Element;

import com.zcbl.compent.language.respertories.Command;
import com.zcbl.compent.language.respertories.Function;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.LanguageFactory;
import com.zcbl.compent.language.respertories.CommandFactory.CommandStack;
import com.zcbl.compent.task.client.annation.SysLoader;
import com.zcbl.compent.task.client.sys.Loader;

@SysLoader
public class If extends Lang implements Loader {
	@Override
	public void load() {
		LanguageFactory.getInstance().addLang("if", this);
	}

	@Override
	public void excute(Command command, List<Function> function, Input input, CommandStack stack) throws Exception {
		try {
			boolean validate = validate(input);
			if (validate) {
				if (function != null && !function.isEmpty()) {
					for (Function f : function) {
						if (f != null)
							f.excute(input, stack);
					}
				} else {
					if (!command.getCommmand().isEmpty()) {
						for (Command c : command.getCommmand()) {
							c.excute(stack);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void anysis(Command cmd, Element element) {
	}

}
