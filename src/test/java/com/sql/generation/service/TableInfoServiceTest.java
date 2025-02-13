package com.sql.generation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TableInfoServiceTest {
    
    @Autowired
    private TableInfoService tableInfoService;
    
    @Test
    void testAddTableInfo() {
        TableInfoAddRequest request = new TableInfoAddRequest();
        // 设置测试数据
        request.setTableName("test_table");
        Long id = tableInfoService.addTableInfo(request);
        assertNotNull(id);
    }
} 