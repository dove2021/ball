package com.tbug.ball.service.biz.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.common.DBContants.TradeUserConst;
import com.tbug.ball.service.common.factory.CodeFactory;
import com.tbug.ball.service.common.security.PasswordHelper;
import com.tbug.ball.service.dao.user.TradeUserMapper;
import com.tbug.ball.service.model.user.TradeUser;

@Service
public class TradeUserBiz {

	@Autowired
	TradeUserMapper tradeUserMapper;
	@Autowired
	CodeFactory codeFactory;
	
	public TradeUser createTradeUser(String creater, String loginName, String password) throws BizException{
		
		if (StringUtils.isEmpty(creater)){
			throw new BizException("创建人不能为空");
		}
		if (StringUtils.isEmpty(loginName)){
			throw new BizException("用户名不能为空");
		}
		if (StringUtils.isEmpty(password)){
			throw new BizException("密码不能为空");
		}
		List<TradeUser> dbTradeUserList = tradeUserMapper.selectByLoginName(loginName);
		if (null !=  dbTradeUserList && dbTradeUserList.size() > 0){
			throw new BizException("用户名已存在");
		}
		
		TradeUser tradeUser = new TradeUser();
		tradeUser.setUserCode(codeFactory.getUserCode());
		tradeUser.setCreater(creater);
		tradeUser.setCreateTime(new Date());
		tradeUser.setLoginName(loginName);
		tradeUser.setStatus(TradeUserConst.STATUS_NORMAL);

		String salt = PasswordHelper.generateSalt();
		String newPassword = PasswordHelper.encryptPassword(salt, password);
		
		tradeUser.setPassword(newPassword);
		tradeUser.setSalt(salt);
		
		if (tradeUserMapper.insertSelective(tradeUser) != 1){
			throw new BizException("平台管理员添加失败");
		}
		return tradeUser;
	}
	
	public TradeUser login(String userName, String password) throws BizException{
		if (StringUtils.isEmpty(userName)){
			throw new BizException("用户名不能为空");
		}
		if (StringUtils.isEmpty(password)){
			throw new BizException("密码不能为空");
		}
		
		List<TradeUser> tradeUserList = tradeUserMapper.selectByLoginName(userName);
		if (null == tradeUserList || tradeUserList.size() > 1){
			throw new BizException("用户不存在");
		}
		
		TradeUser tradeUser = tradeUserList.get(0);
		if (!TradeUserConst.STATUS_NORMAL.equals(tradeUser.getStatus())){
			throw new BizException("用户状态异常");
		}
		
		String salt = tradeUser.getSalt();
		String encryptPassword = PasswordHelper.encryptPassword(salt, password);
		if (!encryptPassword.equals(tradeUser.getPassword())){
			throw new BizException("密码错误");
		}
		
		return tradeUser;
	}
	
	public TradeUser getTradeUserById(Integer id){
		return tradeUserMapper.selectByPrimaryKey(id);
	}
	
	public TradeUser getTradeUserByCode(String code){
		return tradeUserMapper.selectByCode(code);
	}
	public List<TradeUser> listByMap(Map<String, Object> params){
		return tradeUserMapper.listByMap(params);
	}
	
	public Integer countByMap(Map<String, Object> params){
		return tradeUserMapper.countByMap(params);
	}
	
	public Integer updTradeUser(TradeUser tradeUser){
		return tradeUserMapper.updateByPrimaryKeySelective(tradeUser);
	}
	
	public List<TradeUser> getTradeUserDtoByLoginName(String loginName){
		return tradeUserMapper.selectByLoginName(loginName);
	}
	
	public Integer updTradeUserPassword(Integer id, String password, String newPassword) throws BizException{
		
		TradeUser tradeUser = tradeUserMapper.selectByPrimaryKey(id);
		if (null == tradeUser){
			throw new BizException("用户不存在");
		}
		
		String salt = PasswordHelper.generateSalt();
		String encryptPassword = PasswordHelper.encryptPassword(salt, newPassword);
		tradeUser.setSalt(salt);
		tradeUser.setPassword(encryptPassword);
		
		return tradeUserMapper.updateByPrimaryKeySelective(tradeUser);
	}
	
}
