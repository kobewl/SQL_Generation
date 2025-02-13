package com.sql.generation.service;

import org.springframework.stereotype.Service;
import com.alibaba.excel.EasyExcel;
import com.sql.generation.core.builder.SqlBuilder;
import com.sql.generation.model.dto.TableSchema;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import java.util.Map;

@Service
public class ExportService {
    
    public void exportToExcel(TableSchema schema, List<Map<String, Object>> data, OutputStream out) {
        EasyExcel.write(out)
                .head(buildExcelHead(schema))
                .sheet("数据")
                .doWrite(data);
    }
    
    public void exportToSQL(TableSchema schema, List<Map<String, Object>> data, Writer writer) {
        SqlBuilder builder = new SqlBuilder();
        String sql = builder.buildBatchInsertSql(schema, data);
        writer.write(sql);
    }
} 