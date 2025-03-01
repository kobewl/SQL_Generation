package com.sql.generation.core.generator;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sql.generation.common.ErrorCode;
import com.sql.generation.core.schema.TableSchema.Field;
import com.sql.generation.exception.BusinessException;
import com.sql.generation.model.entity.Dict;
import com.sql.generation.service.DictService;
import com.sql.generation.utils.SpringContextUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomUtils;

/**
 * 词库数据生成器
 *
 * @author wangliang
 */
public class DictDataGenerator implements DataGenerator {

    private static final DictService dictService = SpringContextUtils.getBean(DictService.class);

    private final static Gson GSON = new Gson();


    @Override
    public List<String> doGenerate(Field field, int rowNum) {
        String mockParams = field.getMockParams();
        long id = Long.parseLong(mockParams);
        Dict dict = dictService.getById(id);
        if (dict == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "词库不存在");
        }
        List<String> wordList = GSON.fromJson(dict.getContent(),
                new TypeToken<List<String>>() {
                }.getType());
        List<String> list = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            String randomStr = wordList.get(RandomUtils.nextInt(0, wordList.size()));
            list.add(randomStr);
        }
        return list;
    }
}
