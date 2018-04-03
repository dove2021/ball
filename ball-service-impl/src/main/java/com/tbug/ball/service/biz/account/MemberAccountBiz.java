package com.tbug.ball.service.biz.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.common.DBContants.MemberAccountConst;
import com.tbug.ball.service.common.security.PasswordHelper;
import com.tbug.ball.service.dao.account.MemberAccountMapper;
import com.tbug.ball.service.model.account.MemberAccount;

@Service
public class MemberAccountBiz {

	@Autowired
	private MemberAccountMapper memberAccountMapper;
	
	public Integer createMemberAccount(MemberAccount memberAccount) throws BizException{
		
		if (null == memberAccount){
			throw new BizException("客户账户不存在");
		}
		
		memberAccount.setBalance(BigDecimal.ZERO);
		memberAccount.setCreateDate(new Date());
		memberAccount.setStatus(MemberAccountConst.STATUS_NORMAL);
		
		String password = memberAccount.getAccountPassword();
		String salt = PasswordHelper.generateSalt();
		String newPassword = PasswordHelper.encryptPassword(salt, password);
		
		memberAccount.setAccountPassword(newPassword);
		memberAccount.setSalt(salt);
		
		return memberAccountMapper.insert(memberAccount);
	}
	
	public MemberAccount getMemberAccountById(Integer id){
		return memberAccountMapper.selectByPrimaryKey(id);
	}
	
	public MemberAccount getMemberAccountByIdForUpdate(Integer id){
		return memberAccountMapper.selectByPrimaryKeyForUpdate(id);
	}
	
	public MemberAccount getMemberAccountByMemberId(Integer memberId){
		return memberAccountMapper.selectByMemberId(memberId);
	}

	public List<MemberAccount> listMemberAccountByMap(Map<String, Object> params){
		return memberAccountMapper.selectByMap(params);
	}
	
	public Integer CountMemberAccountByMap(Map<String, Object> params){
		return memberAccountMapper.countByMap(params);
	}
	
	public Integer updMemberAccount(MemberAccount memberAccount){
		return memberAccountMapper.updateByPrimaryKeySelective(memberAccount);
	}
}
