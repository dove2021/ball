package com.tbug.ball.mamage.ballmanage;

import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class Test1 {

	public static void main(String[] args) {

		
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		
		
		schedulerFactoryBean.setBeanName("sss");
		
	}

}
