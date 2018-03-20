package com.zcbl.compent.function.sys;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.SystemCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class AutointegerCompent extends SystemCompent {
	static AtomicInteger no = new AtomicInteger();
	static {
		no.set(getRadomStart(3));
	}

	public void execute(Compent compent, Input input, Output output) {
		String length = (String) input.getSys().get("length");
		if (length == null) {
			output.getUser().put("result", no.incrementAndGet());
		} else {
			int l = Integer.parseInt(length);
			output.getUser().put("result", getNo(l));
		}
	}

	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static int getRadomStart(int length) {
		Random rm = new Random();
		double pross = (1 + rm.nextDouble()) * Math.pow(10, length);
		String fixLenthString = String.valueOf(pross);
		return Integer.parseInt(fixLenthString.substring(1, length + 1));
	}

	public void load() {
		CmdFactory.getInstance().addCmd(AutointegerCompent.class.getName(), this);
	}

	public static String getNo(int length) {
		StringBuffer sb = new StringBuffer("00000000000000000000000000000000000000");
		int k = no.incrementAndGet();
		if (k >= Math.pow(10, length)) {
			no.set(0);
			k = no.incrementAndGet();
		}
		sb.append(k);
		int l = sb.length();
		if (l > length) {
			return sb.substring(l - length, l);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		for (int k = 0; k < 100; k++) {
			System.out.println(getNo(6));
		}
		for (int k = 0; k < 100; k++) {
			System.out.println(getNo(6));
		}
	}
}
