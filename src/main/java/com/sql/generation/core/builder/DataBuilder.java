package com.sql.generation.core.builder;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.sql.generation.core.generator.DataGenerator;
import com.sql.generation.core.generator.DataGeneratorFactory;
import com.sql.generation.core.model.enums.MockTypeEnum;
import com.sql.generation.core.schema.TableSchema;
import com.sql.generation.core.schema.TableSchema.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 数据生成器
 *
 * @author wangliang
 */
@Slf4j
@Component
public class DataBuilder {

    /**
     * 批处理大小
     */
    private static final int BATCH_SIZE = 1000;

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 生成数据
     *
     * @param tableSchema 表结构
     * @param rowNum      行数
     * @return 生成的数据
     */
    public static List<Map<String, Object>> generateData(TableSchema tableSchema, int rowNum) {
        List<Field> fieldList = tableSchema.getFieldList();
        // 初始化结果数据
        List<Map<String, Object>> resultList = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            resultList.add(new HashMap<>());
        }
        // 依次生成每一列
        for (Field field : fieldList) {
            MockTypeEnum mockTypeEnum = Optional.ofNullable(MockTypeEnum.getEnumByValue(field.getMockType()))
                    .orElse(MockTypeEnum.NONE);
            DataGenerator dataGenerator = DataGeneratorFactory.getGenerator(mockTypeEnum);
            List<String> mockDataList = dataGenerator.doGenerate(field, rowNum);
            String fieldName = field.getFieldName();
            // 填充结果列表
            if (CollectionUtils.isNotEmpty(mockDataList)) {
                for (int i = 0; i < rowNum; i++) {
                    resultList.get(i).put(fieldName, mockDataList.get(i));
                }
            }
        }
        return resultList;
    }

    /**
     * 生成单条数据的SQL
     *
     * @param schema 表结构
     * @return SQL语句
     */
    private String generateSingleData(TableSchema schema) {
        List<Map<String, Object>> dataList = generateData(schema, 1);
        if (CollectionUtils.isEmpty(dataList)) {
            return null;
        }
        return buildInsertSql(schema, dataList.get(0));
    }

    /**
     * 构建插入SQL
     *
     * @param schema 表结构
     * @param data   数据
     * @return SQL语句
     */
    private String buildInsertSql(TableSchema schema, Map<String, Object> data) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ")
                .append(schema.getTableName())
                .append(" (");

        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();

        data.forEach((column, value) -> {
            columns.add(column);
            values.add(formatValue(value));
        });

        sql.append(String.join(", ", columns))
                .append(") VALUES (")
                .append(String.join(", ", values))
                .append(")");

        return sql.toString();
    }

    /**
     * 格式化SQL值
     *
     * @param value 值
     * @return 格式化后的值
     */
    private String formatValue(Object value) {
        if (value == null) {
            return "NULL";
        }
        if (value instanceof Number) {
            return value.toString();
        }
        return "'" + value.toString().replace("'", "''") + "'";
    }

    /**
     * 批量生成数据
     *
     * @param schema 表结构
     * @param count  数量
     */
    public void batchGenerateData(TableSchema schema, int count) {
        List<String> batchSql = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String sql = generateSingleData(schema);
            if (sql != null) {
                batchSql.add(sql);
                if (batchSql.size() >= BATCH_SIZE) {
                    executeBatch(batchSql);
                    batchSql.clear();
                }
            }
        }
        // 处理剩余的SQL
        if (!batchSql.isEmpty()) {
            executeBatch(batchSql);
        }
    }

    /**
     * 执行批量SQL
     *
     * @param sqlList SQL语句列表
     */
    private void executeBatch(List<String> sqlList) {
        try {
            jdbcTemplate.batchUpdate(sqlList.toArray(new String[0]));
        } catch (Exception e) {
            log.error("执行批量SQL失败", e);
            throw new RuntimeException("执行批量SQL失败: " + e.getMessage());
        }
    }
}
