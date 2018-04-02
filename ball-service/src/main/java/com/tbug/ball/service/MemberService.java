package com.tbug.ball.service;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.Dto.account.MemberAccountDto;
import com.tbug.ball.service.Dto.flow.MemberAccountFlowDto;
import com.tbug.ball.service.Dto.user.MemberDto;

public interface MemberService {

	public boolean createMember(String creater,String memberCode, String name,String linkman, String phoneNo, String loginName,String password, String content) throws ServiceException;
	
	public MemberDto getMemberDtoById(Integer id);
	
	public MemberDto getMemberDtoByCode(String code);
	
	List<MemberDto> listMemberDtoByMap(Map<String, Object> params);
	
	boolean updMemberDto(MemberDto memberDto) throws ServiceException;
	
	MemberDto memberLogin(String loginName, String password) throws ServiceException;
	
	MemberAccountDto getMemberAccountById(Integer id);
	
	List<MemberAccountDto> listMemberAccountByMap(Map<String, Object> params);
	
	boolean updMemberAccount(MemberAccountDto memberAccountDto);
	
	boolean memberRePwd(Integer memberId, String password, String newPassword) throws ServiceException;
	
	List<MemberAccountFlowDto> listMemberAccountFlowByMap(Map<String, Object> params);
	
	Integer countMemberAccountFlowByMap(Map<String, Object> params);
	
}
