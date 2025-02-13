package ${packageName}

import (
    "time"
    "github.com/shopspring/decimal"
    "gorm.io/gorm"
)

// ${structName} ${tableComment}
type ${structName} struct {
    <#list fields as field>
    ${field.name} ${field.type} ${field.tag} // ${field.comment}
    </#list>
}

// TableName 设置表名
func (${structName}) TableName() string {
    return "${tableName}"
}

// ToMap 转换为map
func (m *${structName}) ToMap() map[string]interface{} {
    return map[string]interface{}{
        <#list fields as field>
        "${field.name}": m.${field.name}<#if field_has_next>,</#if>
        </#list>
    }
}

// FromMap 从map转换
func (m *${structName}) FromMap(data map[string]interface{}) *${structName} {
    <#list fields as field>
    if v, ok := data["${field.name}"]; ok {
        m.${field.name} = v.(${field.type})
    }
    </#list>
    return m
} 