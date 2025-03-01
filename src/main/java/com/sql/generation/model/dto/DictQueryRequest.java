package com.sql.generation.model.dto;

import com.sql.generation.common.PageRequest;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询请求
 *
 * @author wangliang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictQueryRequest extends PageRequest implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 内容，支持模糊查询
     */
    private String content;

    /**
     * 状态（0-待审核, 1-通过, 2-拒绝）
     */
    private Integer reviewStatus;

    /**
     * 创建用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}