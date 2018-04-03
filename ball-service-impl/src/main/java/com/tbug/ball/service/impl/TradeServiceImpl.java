package com.tbug.ball.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.TradeService;
import com.tbug.ball.service.Dto.account.TradeAccountDto;
import com.tbug.ball.service.Dto.flow.TradeAccountFlowDto;
import com.tbug.ball.service.Dto.user.TradeUserDto;
import com.tbug.ball.service.biz.account.TradeAccountBiz;
import com.tbug.ball.service.biz.flow.TradeAccountFlowBiz;
import com.tbug.ball.service.biz.system.SysUserRoleBiz;
import com.tbug.ball.service.biz.user.TradeUserBiz;
import com.tbug.ball.service.model.account.TradeAccount;
import com.tbug.ball.service.model.flow.TradeAccountFlow;
import com.tbug.ball.service.model.system.SysUserRole;
import com.tbug.ball.service.model.user.TradeUser;

@Service
public class TradeServiceImpl implements TradeService {
	private static final Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);
	
	@Autowired
	TradeUserBiz tradeUserBiz;
	
	@Autowired
	TradeAccountBiz tradeAccountBiz;
	
	@Autowired
	SysUserRoleBiz sysUserRoleBiz;
	
	@Autowired
	TradeAccountFlowBiz tradeAccountFlowBiz;
	
	@Override
	public TradeUserDto login(String userName, String password) throws ServiceException {
		
		TradeUserDto tradeUserDto = null;
		try {
			TradeUser tradeUser = tradeUserBiz.login(userName, password);
			if (null == tradeUser){
				tradeUserDto = new TradeUserDto();
				BeanUtils.copyProperties(tradeUser, tradeUserDto);
				
				// 敏感数据处理
				tradeUserDto.setPassword("");
				tradeUserDto.setSalt("");
			}
			
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		
		return tradeUserDto;
	}

	@Override
	public List<TradeUserDto> listTradeUserDto(Map<String, Object> params) {
		
		List<TradeUserDto> tradeUserDtoList = new ArrayList<>();
		
		List<TradeUser> tradeUserList = tradeUserBiz.listByMap(params);
		if (!CollectionUtils.isEmpty(tradeUserList)){
			
			for (TradeUser tradeUser : tradeUserList){
				TradeUserDto tradeUserDto = new TradeUserDto();
				BeanUtils.copyProperties(tradeUser, tradeUserDto);
				
				tradeUserDtoList.add(tradeUserDto);
			}
		}
		
		return tradeUserDtoList;
	}

	@Override
	public Integer countTradeUserDto(Map<String, Object> params) {
		return tradeUserBiz.countByMap(params);
	}

	@Override
	public TradeUserDto getTradeUserDtoById(Integer id) {
		TradeUserDto tradeUserDto = null;
		
		TradeUser tradeUser = tradeUserBiz.getTradeUserById(id);
		if (null != tradeUser){
			tradeUserDto = new TradeUserDto();
			BeanUtils.copyProperties(tradeUser, tradeUserDto);
		}
		
		return tradeUserDto;
	}

	@Override
	public TradeUserDto getTradeUserDtoByCode(String code) {
		TradeUserDto tradeUserDto = null;
		
		TradeUser tradeUser = tradeUserBiz.getTradeUserByCode(code);
		if (null != tradeUser){
			tradeUserDto = new TradeUserDto();
			BeanUtils.copyProperties(tradeUser, tradeUser);
		}
		
		return tradeUserDto;
	}

	@Override
	@Transactional(rollbackFor={ServiceException.class})
	public boolean saveTradeUserDto(String creater, String loginName, String password, String[] roles) throws ServiceException {
		try {
			TradeUser tradeUser = tradeUserBiz.createTradeUser(creater, loginName, password);
			
			for (String role : roles){
				SysUserRole userRole = new SysUserRole();
				userRole.setRoleId(Integer.valueOf(role));
				userRole.setUserId(tradeUser.getId());
				sysUserRoleBiz.save(userRole);
			}
		} catch (Exception e) {
			logger.error("创建平台管理账号失败:{}",e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		return true;
	}

	@Override
	public boolean updTradeUserDto(TradeUserDto tradeUserDto) throws ServiceException {
		
		if (null == tradeUserDto){
			throw new ServiceException("更新平台用户失败");
		}
		
		TradeUser tradeUser = new TradeUser();
		BeanUtils.copyProperties(tradeUserDto, tradeUser);
		
		if (tradeUserBiz.updTradeUser(tradeUser) != 1){
			throw new ServiceException("平台用户更新失败");
		}
		
		return false;
	}

	@Override
	public TradeUserDto getTradeUserDtoByLoginName(String loginName) throws ServiceException {
		
		TradeUserDto tradeUserDto = new TradeUserDto();
		
		List<TradeUser> tradeUserList = tradeUserBiz.getTradeUserDtoByLoginName(loginName);
		if (null == tradeUserList || tradeUserList.size() != 1){
			throw new ServiceException("用户异常");
		}
		
		TradeUser tradeUser = tradeUserList.get(0);
		BeanUtils.copyProperties(tradeUser, tradeUserDto);
		
		return tradeUserDto;
	}

	@Override
	public boolean updTradeUserPwd(Integer id, String password, String newPassword) throws ServiceException {
		try {
			if (null == id){
				throw new ServiceException("用户Id不能为空");
			}
			if (StringUtils.isEmpty(newPassword)){
				throw new ServiceException("新密码不能为空");
			}
			
			if (tradeUserBiz.updTradeUserPassword(id, password, newPassword) != 1){
				throw new ServiceException("密码修改失败");
			}
		} catch (Exception e ){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
		
		return true;
	}

	@Override
	public TradeAccountDto getTradeAccountById(Integer id) {
		TradeAccountDto tradeAccountDto = new TradeAccountDto();
		
		TradeAccount tradeAccount = tradeAccountBiz.getTradeAccountById(id);
		if (null != tradeAccount){
			BeanUtils.copyProperties(tradeAccount, tradeAccountDto);
		}
		return tradeAccountDto;
	}

	@Override
	public List<TradeAccountDto> listTradeAccountByMap() {
		List<TradeAccountDto> list = new ArrayList<>();
		
		List<TradeAccount> tradeAccountList = tradeAccountBiz.AllTradeAccount();
		if (!CollectionUtils.isEmpty(tradeAccountList)){
			for (TradeAccount tradeAccount : tradeAccountList){
				TradeAccountDto tradeAccountDto = new TradeAccountDto();
				BeanUtils.copyProperties(tradeAccount, tradeAccountDto);
				
				list.add(tradeAccountDto);
			}
		}
		return list;
	}

	@Override
	public boolean updTradeAccount(TradeAccountDto tradeAccountDto) {
		
		TradeAccount tradeAccount = new TradeAccount();
		BeanUtils.copyProperties(tradeAccountDto, tradeAccount);
		if (tradeAccountBiz.updTradeAccount(tradeAccount) != 1){
			return false;
		}
		return true;
	}

	@Override
	public List<TradeAccountFlowDto> listTradeAccountFlowByMap(Map<String, Object> params) {
		List<TradeAccountFlowDto> dtoList = new ArrayList<>();
		
		List<TradeAccountFlow> list = tradeAccountFlowBiz.listByMap(params);
		if (!CollectionUtils.isEmpty(list)){
			for (TradeAccountFlow flow : list){
				TradeAccountFlowDto dto = new TradeAccountFlowDto();
				BeanUtils.copyProperties(flow, dto);
				
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	@Override
	public Integer countTradeAccountFlowByMap(Map<String, Object> params) {
		return tradeAccountFlowBiz.countByMap(params);
	}

}
