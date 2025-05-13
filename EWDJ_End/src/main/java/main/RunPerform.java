package main;

import com.springBoot.EWDJ_End.rest.perform.PerformRestEvent;
import com.springBoot.EWDJ_End.rest.perform.PerformRestRoom;

public class RunPerform {
    public static void main(String... args) {
    	try {
			new PerformRestRoom();
			new PerformRestEvent();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
