package com.zjlsdm.twodimensionalworld.util;

import java.util.Calendar;

public class TimerUtil {
	public static int getQuarter(){
		Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH) + 1;
        if(month == 1 || month ==2 || month ==3){
            return 0;
        }
        else if(month == 4 || month ==5 || month ==6){
            return 1;
        }
        else if(month == 7 || month ==8 || month ==9){
            return 2;
        }
        else if(month == 10 || month ==11 || month ==12){
            return 3;
        }else{
        	return -1;
        }
	}
}
