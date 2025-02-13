package com.sql.generation.model.dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 创建请求
 *
 * @author wangliang
 */
@Data
public class TableInfoAddRequest implements Serializable {

    /**
     * 名称
     */
    @NotBlank(message = "表名不能为空")
    private String tableName;

    /**
     * 内容
     */
    @NotEmpty(message = "字段列表不能为空")
    private List<Field> fieldList;

    @Size(max = 100, message = "表注释最大长度为100")
    private String tableComment;

    private static final long serialVersionUID = 1L;
}