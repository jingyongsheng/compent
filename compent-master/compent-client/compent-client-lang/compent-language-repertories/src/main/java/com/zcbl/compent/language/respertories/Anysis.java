package com.zcbl.compent.language.respertories;

import java.util.Queue;

import org.dom4j.Element;

public interface Anysis
{

	public Queue<Command> anysisElement(Element element) throws Exception;
}
