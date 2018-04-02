package com.tbug.ball.service.dao.flow;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.flow.MemberAccountFlow;

public interface MemberAccountFlowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberAccountFlow record);

    int insertSelective(MemberAccountFlow record);

    MemberAccountFlow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberAccountFlow record);

    int updateByPrimaryKey(MemberAccountFlow record);
    
    List<MemberAccountFlow> selectByMap(Map<String, Object> params);
    
    Integer countByMap(Map<String, Object> params);
}