package com.sql.generation.core.builder;

import com.sql.generation.core.schema.TableSchema;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Python代码生成器
 */
@Component
@Slf4j
public class PythonCodeBuilder {

    @Resource
    private Configuration configuration;

    /**
     * 生成Python模型类
     *
     * @param tableSchema 表结构信息
     * @return 生成的代码
     */
    public String buildPythonModel(TableSchema tableSchema) {
        String template = "python/model.py.ftl";
        return buildByTemplate(template, tableSchema);
    }

    /**
     * 生成Python ORM操作类
     *
     * @param tableSchema 表结构信息
     * @return 生成的代码
     */
    public String buildPythonOrm(TableSchema tableSchema) {
        String template = "python/orm.py.ftl";
        return buildByTemplate(template, tableSchema);
    }

    /**
     * 生成FastAPI路由代码
     *
     * @param tableSchema 表结构信息
     * @return 生成的代码
     */
    public String buildFastAPIRouter(TableSchema tableSchema) {
        String template = "python/fastapi_router.py.ftl";
        return buildByTemplate(template, tableSchema);
    }

    /**
     * 根据模板生成代码
     *
     * @param templateName 模板名称
     * @param tableSchema  表结构信息
     * @return 生成的代码
     */
    private String buildByTemplate(String templateName, TableSchema tableSchema) {
        try {
            Template template = configuration.getTemplate(templateName);
            StringWriter stringWriter = new StringWriter();
            Map<String, Object> dataModel = new HashMap<>();

            // 转换字段类型为Python类型
            List<Map<String, String>> fields = new ArrayList<>();
            tableSchema.getFieldList().forEach(field -> {
                Map<String, String> fieldMap = new HashMap<>();
                fieldMap.put("name", field.getFieldName());
                fieldMap.put("type", convertToPythonType(field.getFieldType()));
                fieldMap.put("comment", field.getComment());
                fields.add(fieldMap);
            });

            dataModel.put("className", tableSchema.getTableName());
            dataModel.put("fields", fields);
            dataModel.put("tableName", tableSchema.getTableName());
            dataModel.put("tableComment", tableSchema.getTableComment());

            template.process(dataModel, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            log.error("生成Python代码失败", e);
            return "";
        }
    }

    /**
     * 将SQL类型转换为Python类型
     *
     * @param sqlType SQL类型
     * @return Python类型
     */
    private String convertToPythonType(String sqlType) {
        sqlType = sqlType.toLowerCase();
        if (sqlType.contains("int")) {
            return "int";
        } else if (sqlType.contains("float") || sqlType.contains("double") || sqlType.contains("decimal")) {
            return "float";
        } else if (sqlType.contains("boolean")) {
            return "bool";
        } else if (sqlType.contains("datetime") || sqlType.contains("timestamp")) {
            return "datetime.datetime";
        } else if (sqlType.contains("date")) {
            return "datetime.date";
        } else {
            return "str";
        }
    }
}