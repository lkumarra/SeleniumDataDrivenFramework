package com.w2a.DataDrivenFramework.rough;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestHostAdd {

	public static void main(String[] args) throws UnknownHostException {
		
		String messageBody ="http://"+InetAddress.getLocalHost().getHostAddress()+":8080/job/DataDrivenFramework/TestReports/";
		System.out.println(messageBody);
	}

}
