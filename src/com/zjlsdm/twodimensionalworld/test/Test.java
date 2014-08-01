package com.zjlsdm.twodimensionalworld.test;

import java.util.Calendar;
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
	@org.junit.Test
	public void test2() throws Exception {
		 
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH) + 1;
        if(month == 1 || month ==2 || month ==3){
            System.out.println("一季度");
        }
        else if(month == 4 || month ==5 || month ==6){
            System.out.println("二季度");
        }
        else if(month == 7 || month ==8 || month ==9){
            System.out.println("三季度");
        }
        else if(month == 10 || month ==11 || month ==12){
            System.out.println("四季度");
        }
    }

}