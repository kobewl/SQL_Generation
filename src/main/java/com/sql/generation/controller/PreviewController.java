package com.sql.generation.controller;

import com.sql.generation.core.builder.DataBuilder;
import com.sql.generation.model.dto.BaseResponse;
import com.sql.generation.model.dto.PreviewVO;
import com.sql.generation.model.dto.TableSchema;
import com.sql.generation.util.ResultUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/preview")
public class PreviewController {
    
    private final DataBuilder dataBuilder;

    public PreviewController(DataBuilder dataBuilder) {
        this.dataBuilder = dataBuilder;
    }

    @PostMapping("/generate")
    public BaseResponse<PreviewVO> previewGenerate(@RequestBody TableSchema schema) {
        // 生成少量预览数据
        List<Map<String, Object>> previewData = dataBuilder.generateData(schema, 5);
        return ResultUtils.success(new PreviewVO(schema, previewData));
    }
} 