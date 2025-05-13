package main;

import com.example.EWDJ_End.rest.perform.PerformRestEvent;
import com.example.EWDJ_End.rest.perform.PerformRestRoom;

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
