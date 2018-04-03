package com.tbug.ball.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.tbug.ball.service.model.user.Customer;

public class TestDemo {

	public static void main(String[] args) {
		
		Calendar ca = Calendar.getInstance();
		//ca.set(2001, 1, 29);
		
		System.out.println(new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(ca.getTime()));
		
		ca.add(Calendar.YEAR, 1);
		System.out.println(new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(ca.getTime()));
		
		
		Customer c = new Customer();
		System.out.println(c.getLevelNum());
		
	}

}
