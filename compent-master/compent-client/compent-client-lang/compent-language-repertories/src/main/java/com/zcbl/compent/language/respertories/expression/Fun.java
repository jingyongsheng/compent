package com.zcbl.compent.language.respertories.expression;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

import com.zcbl.compent.language.respertories.Command;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Compent.Args;
import com.zcbl.compent.language.respertories.Function;
import com.zcbl.compent.language.respertories.FunctionFactory;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.LanguageFactory;
import com.zcbl.compent.language.respertories.CommandFactory.CommandStack;
import com.zcbl.compent.task.client.annation.SysLoader;
import com.zcbl.compent.task.client.sys.Loader;

@SysLoader
public class Fun extends Lang implements Loader {
	@Override
	public void load() {
		LanguageFactory.getInstance().addLang("fun", this);
	}

	@Override
	public void excute(Command command, List<Function> function, Input input, CommandStack stack) throws Exception {
		try {
			if (function != null && function.size() > 0) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void anysis(Command cmd, Element element) {
		String function = element.getStringValue();
		Compent compent = cmd.getCompent();
		Pattern p = Pattern.compile("\\$.*?\\(.*?\\)");
		Matcher m = p.matcher(function);
		while (m.find()) {
			function = m.group();
			String funName = function.substring(function.indexOf("$") + 1, function.indexOf("("));
			String paramter = function.substring(function.indexOf("(") + 1, function.indexOf(")"));
			compent.setFunName(funName);
			String pointer = (String) compent.getAttrs().get("id");
			compent.setPointer(pointer);
			Function f = FunctionFactory.getInstance().buildAndReturnFuntion(compent, funName);
			cmd.getFunction().add(f);
			if (paramter != null && !paramter.equals("")) {
				String[] pp = paramter.split(",");
				for (String cc : pp) {
					Args a = compent.buildArgs();
					compent.getArgs().add(a);
					a.setKey(cc);
				}
			}
		}
	}
}
