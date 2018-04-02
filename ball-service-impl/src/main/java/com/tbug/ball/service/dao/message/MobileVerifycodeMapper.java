package com.tbug.ball.service.dao.message;

import com.tbug.ball.service.model.message.MobileVerifycode;

public interface MobileVerifycodeMapper {
    int deleteByPrimaryKey(Integer verifycodeId);

    int insert(MobileVerifycode record);

    int insertSelective(MobileVerifycode record);

    MobileVerifycode selectByPrimaryKey(Integer verifycodeId);

    int updateByPrimaryKeySelective(MobileVerifycode record);

    int updateByPrimaryKey(MobileVerifycode record);
}