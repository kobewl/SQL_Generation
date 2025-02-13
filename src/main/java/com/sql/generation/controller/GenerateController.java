package com.sql.generation.controller;

import com.sql.generation.common.BaseResponse;
import com.sql.generation.common.ResultUtils;
import com.sql.generation.core.builder.*;
import com.sql.generation.core.schema.TableSchema;
import com.sql.generation.core.schema.TableSchemaBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 代码生成接口
 */
@RestController
@RequestMapping("/api/generate")
@Api(tags = "代码生成接口")
@Slf4j
public class GenerateController {

    @Resource
    private JavaCodeBuilder javaCodeBuilder;

    @Resource
    private PythonCodeBuilder pythonCodeBuilder;

    @Resource
    private GoCodeBuilder goCodeBuilder;

    @Resource
    private TypeScriptCodeBuilder typeScriptCodeBuilder;

    @PostMapping("/python/model")
    @ApiOperation("生成Python模型代码")
    public BaseResponse<String> generatePythonModel(@RequestBody TableSchema tableSchema) {
        String code = pythonCodeBuilder.buildPythonModel(tableSchema);
        return ResultUtils.success(code);
    }

    @PostMapping("/python/orm")
    @ApiOperation("生成Python ORM代码")
    public BaseResponse<String> generatePythonOrm(@RequestBody TableSchema tableSchema) {
        String code = pythonCodeBuilder.buildPythonOrm(tableSchema);
        return ResultUtils.success(code);
    }

    @PostMapping("/python/fastapi")
    @ApiOperation("生成FastAPI路由代码")
    public BaseResponse<String> generateFastAPIRouter(@RequestBody TableSchema tableSchema) {
        String code = pythonCodeBuilder.buildFastAPIRouter(tableSchema);
        return ResultUtils.success(code);
    }

    @PostMapping("/go/struct")
    @ApiOperation("生成Go结构体代码")
    public BaseResponse<String> generateGoStruct(@RequestBody TableSchema tableSchema) {
        String code = goCodeBuilder.buildGoStruct(tableSchema);
        return ResultUtils.success(code);
    }

    @PostMapping("/go/gorm")
    @ApiOperation("生成GORM代码")
    public BaseResponse<String> generateGormCode(@RequestBody TableSchema tableSchema) {
        String code = goCodeBuilder.buildGormCode(tableSchema);
        return ResultUtils.success(code);
    }

    @PostMapping("/go/gin")
    @ApiOperation("生成Gin路由代码")
    public BaseResponse<String> generateGinRouter(@RequestBody TableSchema tableSchema) {
        String code = goCodeBuilder.buildGinRouter(tableSchema);
        return ResultUtils.success(code);
    }

    @PostMapping("/typescript/interface")
    @ApiOperation("生成TypeScript接口定义")
    public BaseResponse<String> generateTypeScriptInterface(@RequestBody TableSchema tableSchema) {
        String code = typeScriptCodeBuilder.buildTypeScriptInterface(tableSchema);
        return ResultUtils.success(code);
    }

    @PostMapping("/typescript/class")
    @ApiOperation("生成TypeScript类")
    public BaseResponse<String> generateTypeScriptClass(@RequestBody TableSchema tableSchema) {
        String code = typeScriptCodeBuilder.buildTypeScriptClass(tableSchema);
        return ResultUtils.success(code);
    }

    @PostMapping("/typescript/service")
    @ApiOperation("生成TypeScript服务代码")
    public BaseResponse<String> generateTypeScriptService(@RequestBody TableSchema tableSchema) {
        String code = typeScriptCodeBuilder.buildTypeScriptService(tableSchema);
        return ResultUtils.success(code);
    }

    @PostMapping("/typescript/vue3")
    @ApiOperation("生成Vue3组件代码")
    public BaseResponse<String> generateVue3Component(@RequestBody TableSchema tableSchema) {
        String code = typeScriptCodeBuilder.buildVue3Component(tableSchema);
        return ResultUtils.success(code);
    }
}