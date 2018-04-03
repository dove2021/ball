package com.tbug.ball.manage.config.dubbo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.tbug.ball.service.AccountService;
import com.tbug.ball.service.BrokerService;
import com.tbug.ball.service.CustomerService;
import com.tbug.ball.service.DeptService;
import com.tbug.ball.service.LogService;
import com.tbug.ball.service.MemberService;
import com.tbug.ball.service.MenuService;
import com.tbug.ball.service.OrderService;
import com.tbug.ball.service.PayService;
import com.tbug.ball.service.RoleService;
import com.tbug.ball.service.ScheduleService;
import com.tbug.ball.service.TaskService;
import com.tbug.ball.service.TradeService;

@Configuration
public class ReferenceConfig extends DubboConfig {

    @Bean
    public ReferenceBean<CustomerService> customerService() {
    	ReferenceBean<CustomerService> ref = new ReferenceBean<>();
    	ref.setInterface(CustomerService.class);
    	ref.setTimeout(timeout);
    	ref.setRetries(retries);
    	ref.setCheck(true);
    	return ref;
    }
    
    @Bean
    public ReferenceBean<ScheduleService> scheduleService() {
    	ReferenceBean<ScheduleService> ref = new ReferenceBean<>();
    	ref.setInterface(ScheduleService.class);
    	ref.setTimeout(timeout);
    	ref.setRetries(retries);
    	ref.setCheck(true);
    	return ref;
    }
    
    @Bean
    public ReferenceBean<BrokerService> brokerService() {
    	ReferenceBean<BrokerService> ref = new ReferenceBean<>();
    	ref.setInterface(BrokerService.class);
    	ref.setTimeout(timeout);
    	ref.setRetries(retries);
    	ref.setCheck(true);
    	return ref;
    }
    
    @Bean
    public ReferenceBean<MemberService> memberService() {
    	ReferenceBean<MemberService> ref = new ReferenceBean<>();
    	ref.setInterface(MemberService.class);
    	ref.setTimeout(timeout);
    	ref.setRetries(retries);
    	ref.setCheck(true);
    	return ref;
    }
    
    @Bean
    public ReferenceBean<TradeService> tradeService() {
        ReferenceBean<TradeService> ref = new ReferenceBean<>();
        ref.setInterface(TradeService.class);
        ref.setTimeout(timeout);
        ref.setRetries(retries);
        ref.setCheck(true);
        return ref;
    }
    
    @Bean
    public ReferenceBean<OrderService> orderService() {
    	ReferenceBean<OrderService> ref = new ReferenceBean<>();
    	ref.setInterface(OrderService.class);
    	ref.setTimeout(timeout);
    	ref.setRetries(retries);
    	ref.setCheck(true);
    	return ref;
    }
    
    @Bean
    public ReferenceBean<MenuService> menuService() {
    	ReferenceBean<MenuService> ref = new ReferenceBean<>();
    	ref.setInterface(MenuService.class);
    	ref.setTimeout(timeout);
    	ref.setRetries(retries);
    	ref.setCheck(true);
    	return ref;
    }
    
    @Bean
    public ReferenceBean<RoleService> roleService() {
    	ReferenceBean<RoleService> ref = new ReferenceBean<>();
    	ref.setInterface(RoleService.class);
    	ref.setTimeout(timeout);
    	ref.setRetries(retries);
    	ref.setCheck(true);
    	return ref;
    }
    
    @Bean
    public ReferenceBean<DeptService> deptService() {
    	ReferenceBean<DeptService> ref = new ReferenceBean<>();
    	ref.setInterface(DeptService.class);
    	ref.setTimeout(timeout);
    	ref.setRetries(retries);
    	ref.setCheck(true);
    	return ref;
    }
    
    @Bean
    public ReferenceBean<LogService> logService() {
    	ReferenceBean<LogService> ref = new ReferenceBean<>();
    	ref.setInterface(LogService.class);
    	ref.setTimeout(timeout);
    	ref.setRetries(retries);
    	ref.setCheck(true);
    	return ref;
    }
    
    @Bean
    public ReferenceBean<AccountService> accountService() {
    	ReferenceBean<AccountService> ref = new ReferenceBean<>();
    	ref.setInterface(AccountService.class);
    	ref.setTimeout(timeout);
    	ref.setRetries(retries);
    	ref.setCheck(true);
    	return ref;
    }
    
    @Bean
    public ReferenceBean<PayService> payService() {
    	ReferenceBean<PayService> ref = new ReferenceBean<>();
    	ref.setInterface(PayService.class);
    	ref.setTimeout(timeout);
    	ref.setRetries(retries);
    	ref.setCheck(true);
    	return ref;
    }
    
    @Bean
    public ReferenceBean<TaskService> taskService() {
    	ReferenceBean<TaskService> ref = new ReferenceBean<>();
    	ref.setInterface(TaskService.class);
    	ref.setTimeout(timeout);
    	ref.setRetries(retries);
    	ref.setCheck(true);
    	return ref;
    }
    
}
