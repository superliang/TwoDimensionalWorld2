package com.zjlsdm.twodimensionalworld.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	@org.junit.Test
	public void test() {
		String txt = "em acgdb-timestamp=\"1396344600000\"";
		String regex = "^em acgdb-timestamp.*";
		
		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(txt);

		if (m.find()) {
			System.out.println(true);
		}else
			System.out.println(false);

	}

}