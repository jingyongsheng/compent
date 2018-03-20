package com.lpcode.modules.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lpcode.modules.test.entity.Test;
import com.zcbl.common.service.CrudService;
import com.lpcode.modules.test.dao.TestDao;

/**
 * 测试Service

 * @version 2013-10-17
 */
@Service
@Transactional(readOnly = true)
public class TestService extends CrudService<TestDao, Test> {

}
