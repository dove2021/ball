package com.tbug.ball.service.biz.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.common.DBContants.MemberConst;
import com.tbug.ball.service.common.security.PasswordHelper;
import com.tbug.ball.service.dao.user.MemberMapper;
import com.tbug.ball.service.model.user.Member;

@Service
public class MemberBiz {

	@Autowired
	MemberMapper memberMapper;
	
	public Member createMember(String memberCode, String name, String creater, String linkman, String phoneNo,String loginName,String password, String content) throws BizException{
		
		if (StringUtils.isEmpty(memberCode)){
			throw new BizException("会员编号不能为空");
		}
		Member dbMember = memberMapper.selectByCode(memberCode);
		if (null != dbMember){
			throw new BizException("会员编号已存在");
		}
		if (StringUtils.isEmpty(name)){
			throw new BizException("会员名称不能为空");
		}
		if (StringUtils.isEmpty(creater)){
			throw new BizException("创建人不能为空");
		}
		if (StringUtils.isEmpty(phoneNo)){
			throw new BizException("会员联系人手机号不能为空");
		}
		if (StringUtils.isEmpty(password)){
			throw new BizException("会员联系人密码不能为空");
		}
		Member dbMember1 = memberMapper.selectByPhoneNo(phoneNo);
		if (null != dbMember1){
			throw new BizException("手机号码已存在");
		}
		Member dbMember2 = memberMapper.selectByLoginName(loginName);
		if (null != dbMember2){
			throw new BizException("登陆名已存在");
		}
		
		Member member = new Member();
		member.setMemberCode(memberCode);
		member.setName(name);
		member.setCreater(creater);
		member.setCreateTime(new Date());
		member.setLinkman(linkman);
		member.setLoginName(loginName);
		member.setPhoneNo(phoneNo);
		member.setContent(content);
		member.setStatus(MemberConst.STATUS_NORMAL);
		
		String salt = PasswordHelper.generateSalt();
		String newPassword = PasswordHelper.encryptPassword(salt, password);
		
		member.setPassword(newPassword);
		member.setSalt(salt);
		
		if (memberMapper.insert(member) != 1){
			throw new BizException("新建会员失败");
		}
		return member;
	}
	
	public Member getMemberById(Integer id){
		return memberMapper.selectByPrimaryKey(id);
	}
	
	public Member getMemberByCode(String code){
		return memberMapper.selectByCode(code);
	}
	
	public Member getMemberByPhoneNo(String phoneNo){
		return memberMapper.selectByPhoneNo(phoneNo);
	}
	
	public Member getMemberByLoginName(String loginName){
		return memberMapper.selectByLoginName(loginName);
	}
	
	public Integer updMember(Member member){
		return memberMapper.updateByPrimaryKeySelective(member);
	}
	
	public List<Member> getMemberByMap(Map<String, Object> params){
		return memberMapper.selectByMap(params);
	}
}
