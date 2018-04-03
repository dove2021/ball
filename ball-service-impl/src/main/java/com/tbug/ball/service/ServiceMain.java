package com.tbug.ball.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ServiceMain {
	private ClassPathXmlApplicationContext ctx=null;
	private boolean running=false;
	private ShutdownHook shutdownHook=new ShutdownHook();
	
	private class ShutdownHook extends Thread {
		public void run() {
			stopService();
		}
	}
	
	public static void main(String[] args) {
		ServiceMain serviceMain=new ServiceMain();
		
		serviceMain.startService();
		
		Runtime.getRuntime().addShutdownHook(serviceMain.shutdownHook);
		
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		while (serviceMain.running) {
			cal.setTimeInMillis(System.currentTimeMillis());
			System.out.println("["+df.format(cal.getTime())+"] "+"Service Running...");
			
			try {
				Thread.sleep(60*1000L);//sleep 60 seconds
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void startService() {
		if (running) return;
		
		System.out.println("Service Starting...");
		
		try {
			ctx=new ClassPathXmlApplicationContext("spring-context.xml","spring-dubbo-provider.xml");
			ctx.start();
			running=true;
		}
		catch(RuntimeException e) {
			e.printStackTrace();
		}
		
		if (running) {
			System.out.println("Service Started");
		}
	}
	
	private void stopService() {
		running=false;

		if (ctx!=null) {
			System.out.println("Service Stopping...");
			
			try {
				ctx.close();
			}
			catch(RuntimeException e) {
				e.printStackTrace();
			}
			finally {
				ctx=null;
			}
			
			System.out.println("Service Stopped");
		}
		else {
			System.out.println("Service not Running");
		}
	}
}
