package com.tbug.ball.service.dao.user;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.user.Member;

public interface MemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);
    
    Member selectByCode(String memberCode);
    
    Member selectByPhoneNo(String phoneNo);
    
    Member selectByLoginName(String loginName);
    
    List<Member> selectByMap(Map<String, Object> params);
}