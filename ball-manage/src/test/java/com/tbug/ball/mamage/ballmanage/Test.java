package com.tbug.ball.mamage.ballmanage;

import java.util.HashMap;
import java.util.List;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.fastjson.JSON;
import com.tbug.ball.service.TradeService;
import com.tbug.ball.service.Dto.user.TradeUserDto;

public class Test {

	static ApplicationConfig ac = null;
	static RegistryConfig rc = null;

	static {
		try {
			ac = new ApplicationConfig();
			ac.setName("Test");// 应用名字
			rc = new RegistryConfig();
			rc.setAddress("zookeeper://127.0.0.1:2181");// zookeeper注册中心地址
			rc.setProtocol("zookeeper");// 协议
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static <T> T getProvider(Class<T> c, String group) {
		ReferenceConfig<T> rcc = new ReferenceConfig<T>();
		rcc.setTimeout(50000);
		rcc.setRegistry(rc);
		rcc.setApplication(ac);
		rcc.setCheck(false);
		rcc.setInterface(c);
		rcc.setGroup(group);
		return rcc.get();
	}

	public static void test() {
		
		// 2根据接口的字节码和服务的group获得接口引用（此处是dubbo通过你传入的参数 使用动态代理 返回给你接口的引用）
		TradeService tradeService = getProvider(TradeService.class, "");
		
		List<TradeUserDto> list = tradeService.listTradeUserDto(new HashMap<String, Object>());
		
		for (TradeUserDto dto : list){
			
			System.out.println(JSON.toJSONString(dto));
		}
	}

	public static void main(String[] args) {
		test();// 执行测试
	}
}
