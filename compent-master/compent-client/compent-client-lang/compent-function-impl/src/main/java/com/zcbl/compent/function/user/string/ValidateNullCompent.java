package com.zcbl.compent.function.user.string;

import java.util.Iterator;
import java.util.Set;

import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.UserCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class ValidateNullCompent extends UserCompent {

	public void execute(Compent compent, Input input, Output output) {
		if (input.getUser() != null && !input.getUser().isEmpty()) {
			Set<String> set = input.getUser().keySet();
			Iterator<String> ite = set.iterator();
			while (ite.hasNext()) {
				String k = (String) ite.next();
				String value = (String) input.getUser().get(k);
				output.getUser().put(k, String.valueOf(validate(value)));
			}
		}
	}

	public boolean validate(String value) {
		if (value == null || value.equals("")) {
			return false;
		} else {
			return true;
		}
	}

	public void load() {
		CmdFactory.getInstance().addCmd(ValidateNullCompent.class.getName(), this);
	}
}
