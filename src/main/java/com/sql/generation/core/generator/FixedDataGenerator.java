package com.sql.generation.core.generator;

import com.sql.generation.core.schema.TableSchema.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * 固定值数据生成器
 *
 * @author wangliang
 */
public class FixedDataGenerator implements DataGenerator {

    @Override
    public List<String> doGenerate(Field field, int rowNum) {
        String mockParams = field.getMockParams();
        if (StringUtils.isBlank(mockParams)) {
            mockParams = "6";
        }
        List<String> list = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            list.add(mockParams);
        }
        return list;
    }
}
