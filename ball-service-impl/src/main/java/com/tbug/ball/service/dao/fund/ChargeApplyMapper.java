package com.tbug.ball.service.dao.fund;

import com.tbug.ball.service.model.fund.ChargeApply;

public interface ChargeApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeApply record);

    int insertSelective(ChargeApply record);

    ChargeApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargeApply record);

    int updateByPrimaryKey(ChargeApply record);
}