package com.sql.generation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sql.generation.model.entity.Dict;

/**
 * @author wangliangli
 * @description 针对表【dict(词条)】的数据库操作Service
 */
public interface DictService extends IService<Dict> {

    /**
     * 校验并处理
     *
     * @param dict
     * @param add 是否为创建校验
     */
    void validAndHandleDict(Dict dict, boolean add);
}
