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
 * Go代码生成器
 */
@Component
@Slf4j
public class GoCodeBuilder {

    @Resource
    private Configuration configuration;

    /**
     * 生成Go结构体
     *
     * @param tableSchema 表结构信息
     * @return 生成的代码
     */
    public String buildGoStruct(TableSchema tableSchema) {
        String template = "go/model.go.ftl";
        return buildByTemplate(template, tableSchema);
    }

    /**
     * 生成GORM操作代码
     *
     * @param tableSchema 表结构信息
     * @return 生成的代码
     */
    public String buildGormCode(TableSchema tableSchema) {
        String template = "go/gorm.go.ftl";
        return buildByTemplate(template, tableSchema);
    }

    /**
     * 生成Gin路由代码
     *
     * @param tableSchema 表结构信息
     * @return 生成的代码
     */
    public String buildGinRouter(TableSchema tableSchema) {
        String template = "go/gin_router.go.ftl";
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

            // 转换字段类型为Go类型
            List<Map<String, String>> fields = new ArrayList<>();
            tableSchema.getFieldList().forEach(field -> {
                Map<String, String> fieldMap = new HashMap<>();
                fieldMap.put("name", toGoFieldName(field.getFieldName()));
                fieldMap.put("type", convertToGoType(field.getFieldType()));
                fieldMap.put("tag", buildGoTag(field));
                fieldMap.put("comment", field.getComment());
                fields.add(fieldMap);
            });

            dataModel.put("packageName", "models");
            dataModel.put("structName", toGoStructName(tableSchema.getTableName()));
            dataModel.put("fields", fields);
            dataModel.put("tableName", tableSchema.getTableName());
            dataModel.put("tableComment", tableSchema.getTableComment());

            template.process(dataModel, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            log.error("生成Go代码失败", e);
            return "";
        }
    }

    /**
     * 构建Go的结构体标签
     *
     * @param field 字段信息
     * @return Go标签字符串
     */
    private String buildGoTag(TableSchema.Field field) {
        StringBuilder tag = new StringBuilder();
        tag.append("`gorm:\"column:").append(field.getFieldName());

        if (field.isPrimaryKey()) {
            tag.append(";primaryKey");
        }
        if (field.isAutoIncrement()) {
            tag.append(";autoIncrement");
        }
        if (field.isNotNull()) {
            tag.append(";not null");
        }

        tag.append("\" json:\"").append(field.getFieldName()).append("\"`");
        return tag.toString();
    }

    /**
     * 将SQL类型转换为Go类型
     *
     * @param sqlType SQL类型
     * @return Go类型
     */
    private String convertToGoType(String sqlType) {
        sqlType = sqlType.toLowerCase();
        if (sqlType.contains("int")) {
            return "int64";
        } else if (sqlType.contains("float") || sqlType.contains("double")) {
            return "float64";
        } else if (sqlType.contains("decimal")) {
            return "decimal.Decimal";
        } else if (sqlType.contains("boolean")) {
            return "bool";
        } else if (sqlType.contains("datetime") || sqlType.contains("timestamp")) {
            return "time.Time";
        } else if (sqlType.contains("date")) {
            return "time.Time";
        } else {
            return "string";
        }
    }

    /**
     * 转换为Go的字段名（驼峰命名）
     */
    private String toGoFieldName(String name) {
        String[] parts = name.split("_");
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (part.length() > 0) {
                result.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 转换为Go的结构体名称
     */
    private String toGoStructName(String tableName) {
        return toGoFieldName(tableName);
    }
}