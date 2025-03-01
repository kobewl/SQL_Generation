package com.sql.generation.model.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * 根据 SQL 生成请求体
 *
 * @author wangliang
 */
@Data
public class GenerateBySqlRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String sql;
}
