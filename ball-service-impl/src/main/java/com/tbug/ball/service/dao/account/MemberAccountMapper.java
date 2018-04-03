package com.tbug.ball.service.dao.account;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.account.MemberAccount;

public interface MemberAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberAccount record);

    int insertSelective(MemberAccount record);

    MemberAccount selectByPrimaryKey(Integer id);
    
    MemberAccount selectByPrimaryKeyForUpdate(Integer id);

    int updateByPrimaryKeySelective(MemberAccount record);

    int updateByPrimaryKey(MemberAccount record);
    
    List<MemberAccount> selectByMap(Map<String, Object> params);
    
    Integer countByMap(Map<String, Object> params);
    
    MemberAccount selectByMemberId(Integer memberId);
}