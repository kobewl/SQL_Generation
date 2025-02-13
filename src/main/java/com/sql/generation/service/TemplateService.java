package com.sql.generation.service;

import com.alibaba.fastjson.JSON;
import com.sql.generation.model.dto.TableSchema;
import com.sql.generation.model.entity.Template;
import com.sql.generation.mapper.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {
    
    @Autowired
    private TemplateMapper templateMapper;
    
    public void saveTemplate(String name, TableSchema schema) {
        Template template = new Template();
        template.setName(name);
        template.setContent(JSON.toJSONString(schema));
        templateMapper.insert(template);
    }
    
    public List<Template> listTemplates() {
        return templateMapper.selectList(null);
    }
} 