package com.zcbl.compent.language.respertories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zcbl.compent.language.respertories.CommandFactory.CommandStack;
import com.zcbl.compent.language.respertories.Compent.Args;
import com.zcbl.compent.language.respertories.expression.Lang;

public class Command {
	List<Function> function = new ArrayList<Function>();
	Queue<Command> commmand = new LinkedList<Command>();
	Lang lang;
	Compent compent;

	public Queue<Command> getCommmand() {
		return commmand;
	}

	public void setCommmand(Queue<Command> commmand) {
		this.commmand = commmand;
	}

	public Lang getLang() {
		return lang;
	}

	public void setLang(Lang lang) {
		this.lang = lang;
	}

	public Compent getCompent() {
		return compent;
	}

	public void setCompent(Compent compent) {
		this.compent = compent;
	}

	public void setFunction(List<Function> function) {
		this.function = function;
	}

	public void addCommand(Command command) {
		this.commmand.add(command);
	}

	public Command(Compent compent, Lang l) {
		this.compent = compent;
		this.lang = l;
	}

	public List<Function> getFunction() {
		return function;
	}

	public void excute(CommandStack stack) throws Exception {
		if (lang != null) {
			lang.excute(this, function, excuteParamter(compent, stack), stack);
		}
	}

	private Input excuteParamter(Compent compent, CommandStack stack) {
		Input input = new Input();
		Map<String, Object> par = new HashMap<String, Object>();
		if (compent.getArgs().size() > 0) {
			for (Args args : compent.getArgs()) {
				Object obj = replace(args.getKey(), stack);
				if (obj instanceof String) {
					String key = (String) obj;
					par.put(key.substring(0, key.indexOf("=")), key.substring(key.indexOf("=") + 1, key.length()));
				} else if (obj instanceof Map) {
					String[] arg = args.getKey().split("=");
					if (arg.length == 2) {
						par.put(arg[0], obj.toString());
					} else {
						par.putAll((Map<String, Object>) obj);
					}
				}
			}
		}

		if (compent.getAttrs().size() > 0) {
			List<String> list = new ArrayList<String>(compent.getAttrs().keySet());
			for (String key : list) {
				if (key.trim().equals("id")) {
					continue;
				}
				String value = (String) compent.getAttrs().get(key);
				Object obj = replace(value, stack);
				input.getSys().put(key, obj);
			}
		}
		input.setUser(par);
		return input;
	}

	private Object replace(String content, CommandStack stack) {
		if (content == null)
			return null;
		Pattern p = Pattern.compile("\\$\\{.*?\\}");
		Matcher m = p.matcher(content);
		while (m.find()) {
			String key = m.group();
			String[] kv = key.replaceAll("\\$\\{", "").replaceAll("\\}", "").split("\\.");
			if (kv.length == 2) {
				Map<String, Object> map = stack.getPointer(kv[0]);
				if (map == null) {
					Cmd cmd = CmdFactory.getInstance().getSysCmd().get(kv[0]);
					if (cmd != null) {
						try {
							Output out = new Output();
							cmd.execute(null, null, out);
							map = out.getUser();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						map = CompentFactory.getInstance().getCompent().get(kv[0]);
					}
				}
				if (map != null) {
					Object value = map.get(kv[1]);
					if (value == null) {
						content = content.replace(key, "");
					} else {
						 if (value instanceof List) {
							return value;
						} else if (value instanceof Map) {
							return value;
						} else {
							content = content.replace(key, value.toString());
						}
					}
				} else {
					content = content.replace(key, "");
				}
			} else if (kv.length == 1) {
				Map<String, Object> map = stack.getPointer(kv[0]);
				if (map == null) {
					Cmd cmd = CmdFactory.getInstance().getSysCmd().get(kv[0]);
					if (cmd != null) {
						try {
							Output out = new Output();
							cmd.execute(null, null, out);
							map = out.getUser();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						map = CompentFactory.getInstance().getCompent().get(kv[0]);
					}
				}
				return map;
			}
		}
		return content;
	}
}
