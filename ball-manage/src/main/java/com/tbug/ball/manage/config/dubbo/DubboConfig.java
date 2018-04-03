package com.tbug.ball.manage.config.dubbo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;

@Configuration
public class DubboConfig {

	@Value("${dubbo.application.name}")
	String applicationName;
	@Value("${dubbo.register.protocol}")
	String protocol;
	@Value("${dubbo.register.address}")
	String address;
	@Value("${dubbo.provider.timeout}")
	Integer timeout;
	@Value("${dubbo.provider.retries}")
	Integer retries;

	@Bean
	public RegistryConfig registry() {
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setProtocol(protocol);
		registryConfig.setAddress(address);
		return registryConfig;
	}

	@Bean
	public ApplicationConfig application() {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName(applicationName);
		return applicationConfig;
	}
	
    @Bean
    public ProtocolConfig protocol() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20880);
        return protocolConfig;
    }
	
}
