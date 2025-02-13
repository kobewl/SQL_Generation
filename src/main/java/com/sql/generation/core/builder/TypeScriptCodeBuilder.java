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
 * TypeScript代码生成器
 */
@Component
@Slf4j
public class TypeScriptCodeBuilder {

    @Resource
    private Configuration configuration;

    /**
     * 生成TypeScript接口定义
     *
     * @param tableSchema 表结构信息
     * @return 生成的代码
     */
    public String buildTypeScriptInterface(TableSchema tableSchema) {
        String template = "typescript/interface.ts.ftl";
        return buildByTemplate(template, tableSchema);
    }

    /**
     * 生成TypeScript类
     *
     * @param tableSchema 表结构信息
     * @return 生成的代码
     */
    public String buildTypeScriptClass(TableSchema tableSchema) {
        String template = "typescript/class.ts.ftl";
        return buildByTemplate(template, tableSchema);
    }

    /**
     * 生成TypeScript API服务
     *
     * @param tableSchema 表结构信息
     * @return 生成的代码
     */
    public String buildTypeScriptService(TableSchema tableSchema) {
        String template = "typescript/service.ts.ftl";
        return buildByTemplate(template, tableSchema);
    }

    /**
     * 生成Vue3组件
     *
     * @param tableSchema 表结构信息
     * @return 生成的代码
     */
    public String buildVue3Component(TableSchema tableSchema) {
        String template = "typescript/vue3_component.vue.ftl";
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

            // 转换字段类型为TypeScript类型
            List<Map<String, String>> fields = new ArrayList<>();
            tableSchema.getFieldList().forEach(field -> {
                Map<String, String> fieldMap = new HashMap<>();
                fieldMap.put("name", toCamelCase(field.getFieldName())); // 转换为驼峰命名
                fieldMap.put("type", convertToTypeScriptType(field.getFieldType()));
                fieldMap.put("comment", field.getComment());
                fieldMap.put("required", String.valueOf(field.isNotNull()));
                fields.add(fieldMap);
            });

            String interfaceName = toInterfaceName(tableSchema.getTableName());
            dataModel.put("interfaceName", interfaceName);
            dataModel.put("className", interfaceName.substring(1)); // 移除I前缀
            dataModel.put("fields", fields);
            dataModel.put("tableName", tableSchema.getTableName());
            dataModel.put("tableComment", tableSchema.getTableComment());

            template.process(dataModel, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            log.error("生成TypeScript代码失败", e);
            throw new RuntimeException("生成TypeScript代码失败: " + e.getMessage());
        }
    }

    /**
     * 将SQL类型转换为TypeScript类型
     *
     * @param sqlType SQL类型
     * @return TypeScript类型
     */
    private String convertToTypeScriptType(String sqlType) {
        if (sqlType == null) {
            return "any";
        }
        sqlType = sqlType.toLowerCase();
        if (sqlType.contains("int") || sqlType.contains("float") || sqlType.contains("double")
                || sqlType.contains("decimal")) {
            return "number";
        } else if (sqlType.contains("boolean")) {
            return "boolean";
        } else if (sqlType.contains("datetime") || sqlType.contains("timestamp") || sqlType.contains("date")) {
            return "Date";
        } else {
            return "string";
        }
    }

    /**
     * 转换为TypeScript接口名称（添加I前缀）
     */
    private String toInterfaceName(String tableName) {
        if (tableName == null || tableName.isEmpty()) {
            return "IModel";
        }
        StringBuilder result = new StringBuilder("I");
        String[] parts = tableName.split("_");
        for (String part : parts) {
            if (part.length() > 0) {
                result.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 转换为驼峰命名
     */
    private String toCamelCase(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = false;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == '_') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else if (i == 0) {
                    result.append(Character.toLowerCase(c));
                } else {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }
}