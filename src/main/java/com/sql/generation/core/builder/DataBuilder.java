package com.sql.generation.core.builder;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.sql.generation.core.generator.DataGenerator;
import com.sql.generation.core.generator.DataGeneratorFactory;
import com.sql.generation.core.model.enums.MockTypeEnum;
import com.sql.generation.core.schema.TableSchema;
import com.sql.generation.core.schema.TableSchema.Field;

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
public class DataBuilder {

    /**
     * 生成数据
     *
     * @param tableSchema
     * @param rowNum
     * @return
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

    // 使用StringBuilder替代String拼接
    private StringBuilder buildInsertSql(TableSchema schema) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ")
           .append(schema.getTableName())
           .append(" (");
        // ...
        return sql;
    }
    
    // 批量生成数据时使用批处理
    public void batchGenerateData(TableSchema schema, int count) {
        List<String> batchSql = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            if(batchSql.size() >= BATCH_SIZE) {
                executeBatch(batchSql);
                batchSql.clear();
            }
            batchSql.add(generateSingleData(schema));
        }
    }
}
