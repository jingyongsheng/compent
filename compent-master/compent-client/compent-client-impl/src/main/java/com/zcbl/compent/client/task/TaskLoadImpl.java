package com.zcbl.compent.client.task;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import com.zcbl.compent.config.factory.ConfigFactory;
import com.zcbl.compent.data.center.log.Log;
import com.zcbl.compent.data.center.log.factory.LogCompent;
import com.zcbl.compent.task.client.annation.TaskCompent;
import com.zcbl.compent.task.client.channel.api.Channel;
import com.zcbl.compent.task.client.task.api.Task;

@TaskCompent
public class TaskLoadImpl implements Task {
	static Log log = LogCompent.getInstance().getLog(TaskLoadImpl.class);

	public void excuteTask(Channel channel, String card) {
		if (channel == null)
			return;
		List<String> obj = channel.getData(card);
		InputStream input = null;
		if (obj != null && !obj.isEmpty())
			for (String o : obj) {
				try {
					if (o == null)
						return;
					input = new ByteArrayInputStream(StringToHtml(o).getBytes("UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				ConfigFactory.build(input);
			}
	}

	public static String StringToHtml(String s) {
		s = Replace(s, "&amp;", "&");
		s = Replace(s, "&lt;", "<");
		s = Replace(s, "&gt;", ">");
		s = Replace(s, "&single_qu;", "'");
		s = Replace(s, "&quot;", "\"");
		return s;
	}

	public static String Replace(String source, String oldString, String newString) {
		if (source == null) {
			return null;
		}
		StringBuffer output = new StringBuffer();
		int lengOfsource = source.length();
		int lengOfold = oldString.length();
		int posStart;
		int pos;
		for (posStart = 0; (pos = source.indexOf(oldString, posStart)) >= 0; posStart = pos + lengOfold) {
			output.append(source.substring(posStart, pos));
			output.append(newString);
		}
		if (posStart < lengOfsource) {
			output.append(source.substring(posStart));
		}
		return output.toString();
	}

}