package com.zcbl.compent.language.respertories.expression;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.zcbl.compent.language.respertories.Anysis;
import com.zcbl.compent.language.respertories.Command;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Function;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.LanguageFactory;
import com.zcbl.compent.language.respertories.CommandFactory.CommandStack;

public abstract class Lang implements Anysis {
	public abstract void excute(Command command, List<Function> function, Input input, CommandStack stack)
			throws Exception;

	public Queue<Command> anysisElement(Element element) throws Exception {
		Queue<Command> queue = new LinkedList<Command>();
		Map<String, Object> attrs = getAttrs(element);
		Compent c = new Compent();
		c.setAttrs(attrs);
		Command cmd = new Command(c, this);
		queue.add(cmd);
		anysisCommand(cmd, element);
		return queue;
	}

	protected Map<String, Object> getAttrs(Element ele) {
		Map<String, Object> att = new HashMap<String, Object>();
		int total = ele.attributeCount();
		for (int i = 0; i < total; i++) {
			Attribute attr = ele.attribute(i);
			attr.getValue();
			att.put(attr.getName(), attr.getValue());
		}
		return att;
	}

	public abstract void anysis(Command cmd, Element element) throws Exception;

	@SuppressWarnings("unchecked")
	protected void anysisCommand(Command cmd, Element element) {
		try {
			this.anysis(cmd, element);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Iterator<Element> iter = element.elementIterator(); iter.hasNext();) {
			Element childs = iter.next();
			String name = childs.getName();
			Lang lang = LanguageFactory.getInstance().getLang(name);
			if (lang != null) {
				Map<String, Object> attrs = getAttrs(childs);
				Compent c = new Compent();
				c.setAttrs(attrs);
				Command next = new Command(c, lang);
				cmd.addCommand(next);
				try {
					lang.anysisCommand(next, childs);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean validate(Input input) {
		boolean validate = false;
		if (input.getSys() != null && input.getSys().get("test") != null) {
			String value = (String) input.getSys().get("test");
			{
				String[] or = value.split("\\|");
				String[] and = value.split("\\+");
				if (or.length > 1) {
					for (int i = 0; i < or.length; i++) {
						String[] args = or[i].split("==");
						if (args.length == 2) {
							if (args[0].trim().equals(args[1].trim())) {
								validate = true;
								break;
							}
						} else {
							String[] not = or[i].split("!=");
							if (not.length == 2) {
								if (!not[0].trim().equals(not[1].trim())) {
									validate = true;
									break;
								}
							}
						}
					}
				} else if (and.length > 1) {
					int all = 0;
					for (int i = 0; i < and.length; i++) {
						String[] args = and[i].split("==");
						if (args.length == 2) {
							if (args[0].trim().equals(args[1].trim())) {
								all++;
							}
						} else {
							String[] not = and[i].split("!=");
							if (not.length == 2) {
								if (!not[0].trim().equals(not[1].trim())) {
									all++;
								}
							}
						}
					}
					if (all == and.length) {
						validate = true;
					}
				} else {
					String[] args = value.split("==");
					if (args.length == 2) {
						if (args[0].trim().equals(args[1].trim())) {
							validate = true;
						}
					} else {
						String[] not = value.split("!=");
						if (not.length == 2) {
							if (!not[0].trim().equals(not[1].trim())) {
								validate = true;
							}
						}
					}
				}
			}
		}
		return validate;
	}
}
