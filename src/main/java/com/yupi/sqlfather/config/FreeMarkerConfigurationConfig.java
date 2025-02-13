package com.yupi.sqlfather.config;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.IOException;
import org.springframework.context.annotation.Bean;

/**
 * FreeMarker 模板配置
 *
 * @author wangliang
 */
@org.springframework.context.annotation.Configuration
public class FreeMarkerConfigurationConfig {

    /**
     * 创建并配置一个Freemarker模板的Configuration对象。
     * 这个方法配置了模板加载目录、默认编码、异常处理策略等核心设置。
     *
     * @return Configuration 配置好的Freemarker Configuration对象，可用于模板的解析和处理。
     * @throws IOException 如果在设置模板加载目录时发生IO异常。
     */
    @Bean
    public Configuration configuration() throws IOException {
        // 初始化Configuration对象并设置版本号
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);

        // 设置模板加载的目录
        cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));

        // 设置默认编码为UTF-8
        cfg.setDefaultEncoding("UTF-8");

        // 设置模板异常处理策略为抛出异常
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // 设置是否记录模板执行过程中的异常
        cfg.setLogTemplateExceptions(false);

        // 设置是否允许未检查的异常被包装
        cfg.setWrapUncheckedExceptions(true);

        // 设置是否允许空的循环变量
        cfg.setFallbackOnNullLoopVariable(false);

        return cfg;
    }

}
