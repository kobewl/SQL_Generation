package com.sql.generation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sql.generation.model.entity.Report;

/**
 * @author wangliangli
 * @description 针对表【report(举报)】的数据库操作Service
 */
public interface ReportService extends IService<Report> {

    /**
     * 校验
     *
     * @param report
     * @param add
     */
    void validReport(Report report, boolean add);
}
