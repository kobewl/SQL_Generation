package com.sql.generation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sql.generation.model.entity.FieldInfo;

/**
 * @author wangliangli
 * @description 针对表【field_info】的数据库操作Service
 */
public interface FieldInfoService extends IService<FieldInfo> {

    /**
     * 校验并处理
     *
     * @param fieldInfo
     * @param add 是否为创建校验
     */
    void validAndHandleFieldInfo(FieldInfo fieldInfo, boolean add);
}
