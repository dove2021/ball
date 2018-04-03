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

import com.tbug.ball.service.MemberService;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.Dto.account.MemberAccountDto;
import com.tbug.ball.service.Dto.flow.MemberAccountFlowDto;
import com.tbug.ball.service.Dto.user.MemberDto;
import com.tbug.ball.service.biz.account.MemberAccountBiz;
import com.tbug.ball.service.biz.flow.MemberAccountFlowBiz;
import com.tbug.ball.service.biz.user.MemberBiz;
import com.tbug.ball.service.common.DBContants.MemberConst;
import com.tbug.ball.service.common.security.PasswordHelper;
import com.tbug.ball.service.model.account.MemberAccount;
import com.tbug.ball.service.model.flow.MemberAccountFlow;
import com.tbug.ball.service.model.user.Member;

@Service
public class MemberServiceImpl implements MemberService {
	private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@Autowired MemberBiz memberBiz;
	
	@Autowired MemberAccountBiz memberAccountBiz;
	
	@Autowired MemberAccountFlowBiz memberAccountFlowBiz;
	
	@Override
	@Transactional(rollbackFor={ServiceException.class})
	public boolean createMember(String creater,String memberCode,String name,String linkman,String phoneNo,String loginName,String password,
			String content) throws ServiceException {
		
		try {
			// 会员信息
			Member member = memberBiz.createMember(memberCode, name, creater,linkman,phoneNo,loginName,password, content);
			
			// 会员账号
			MemberAccount memberAccount = new MemberAccount();
			memberAccount.setMemberCode(memberCode);
			memberAccount.setMemberId(member.getId());
			memberAccount.setMemberName(name);
			memberAccount.setAccountPassword("123456"); // 默认
			memberAccountBiz.createMemberAccount(memberAccount);
			
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		
		return false;
	}

	@Override
	public MemberDto getMemberDtoById(Integer id) {
		MemberDto memberDto = null;
		
		Member member = memberBiz.getMemberById(id);
		if (null != member){
			memberDto = new MemberDto();
			
			BeanUtils.copyProperties(member, memberDto);
		}
		
		return memberDto;
	}

	@Override
	public MemberDto getMemberDtoByCode(String code) {
		MemberDto memberDto = null;
		
		Member member = memberBiz.getMemberByCode(code);
		if (null != member){
			memberDto = new MemberDto();
			
			BeanUtils.copyProperties(member, memberDto);
		}
		
		return memberDto;
	}
	
	@Override
	public List<MemberDto> listMemberDtoByMap(Map<String, Object> params) {
		List<MemberDto> memberDtoList = new ArrayList<>();
		
		List<Member> memberList = memberBiz.getMemberByMap(params);
		if (!CollectionUtils.isEmpty(memberList)){
			
			for (Member member : memberList){
				MemberDto memberDto = new MemberDto();
				BeanUtils.copyProperties(member, memberDto);
				
				memberDtoList.add(memberDto);
			}
		}
		return memberDtoList;
	}

	@Override
	public boolean updMemberDto(MemberDto memberDto) throws ServiceException {
		
		Member member = new Member();
		BeanUtils.copyProperties(memberDto, member);
		
		if (memberBiz.updMember(member) != 1){
			throw new ServiceException("会员更新失败");
		}
		
		return true;
	}

	@Override
	public MemberDto memberLogin(String loginName, String password) throws ServiceException{
		MemberDto memberDto = null;
		try {
			Member member = memberBiz.getMemberByLoginName(loginName);
			if (member == null){
				member = memberBiz.getMemberByPhoneNo(loginName);
			}
			if (member == null){
				member = memberBiz.getMemberByCode(loginName);
			}
			if (member == null){
				throw new ServiceException("会员不存在");
			}
			if (!MemberConst.STATUS_NORMAL.equals(member.getStatus())){
				throw new ServiceException("会员状态异常");
			}
			
			String salt = member.getSalt();
			String encryptPassword = PasswordHelper.encryptPassword(salt, password);
			if (!encryptPassword.equals(member.getPassword())){
				throw new ServiceException("会员密码错误");
			}
			
			memberDto = new MemberDto();
			BeanUtils.copyProperties(member, memberDto);
		} catch (Exception e){
			logger.error("用户登陆异常,参数, 用户名:{},密码:{}, {}",loginName,password,e.getMessage(), e);
		}
		
		return memberDto;
	}

	@Override
	public MemberAccountDto getMemberAccountById(Integer id) {
		MemberAccountDto memberAccountDto = new MemberAccountDto();
		
		MemberAccount memberAccount = memberAccountBiz.getMemberAccountById(id);
		if (null != memberAccount){
			BeanUtils.copyProperties(memberAccount, memberAccountDto);
		}
		return memberAccountDto;
	}

	@Override
	public List<MemberAccountDto> listMemberAccountByMap(Map<String, Object> params) {
		List<MemberAccountDto> listDto = new ArrayList<>();
		
		List<MemberAccount> list = memberAccountBiz.listMemberAccountByMap(params);
		if (!CollectionUtils.isEmpty(list)){
			for (MemberAccount memberAccount : list){
				MemberAccountDto memberAccountDto = new MemberAccountDto();
				BeanUtils.copyProperties(memberAccount, memberAccountDto);
				
				listDto.add(memberAccountDto);
			}
		}
		return listDto;
	}

	@Override
	public boolean updMemberAccount(MemberAccountDto memberAccountDto) {
		MemberAccount memberAccount = new MemberAccount();
		BeanUtils.copyProperties(memberAccountDto, memberAccount);
		if (memberAccountBiz.updMemberAccount(memberAccount) != 1){
			return false;
		}
		return true;
	}

	@Override
	public boolean memberRePwd(Integer memberId, String password, String newPassword) throws ServiceException {
		Member member = memberBiz.getMemberById(memberId);
		if (null == member){
			throw new ServiceException("账户不存在");
		}
		String newSalt = PasswordHelper.generateSalt();
		String newEnpassword = PasswordHelper.encryptPassword(newSalt, newPassword);
		member.setSalt(newSalt);
		member.setPassword(newEnpassword);
		
		if (memberBiz.updMember(member) != 1){
			throw new ServiceException("更新密码失败");
		}
		return true;
	}

	@Override
	public List<MemberAccountFlowDto> listMemberAccountFlowByMap(Map<String, Object> params) {
		List<MemberAccountFlowDto> dtoList = new ArrayList<>();
		
		List<MemberAccountFlow> list = memberAccountFlowBiz.listByMap(params);
		if (!CollectionUtils.isEmpty(list)){
			for (MemberAccountFlow flow : list){
				MemberAccountFlowDto dto = new MemberAccountFlowDto();
				BeanUtils.copyProperties(flow, dto);
				
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	@Override
	public Integer countMemberAccountFlowByMap(Map<String, Object> params) {
		return memberAccountFlowBiz.countByMap(params);
	}

}
