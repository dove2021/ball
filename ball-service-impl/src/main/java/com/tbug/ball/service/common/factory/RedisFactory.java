package com.tbug.ball.service.common.factory;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tbug.ball.service.utils.RedisUtil;

@Component
public class RedisFactory {

	@Resource
	private RedisUtil redisUtil;
	
}
