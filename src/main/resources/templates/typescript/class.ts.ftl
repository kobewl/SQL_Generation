/**
 * ${tableComment}
 */
export class ${className} implements ${interfaceName} {
    <#list fields as field>
    /**
     * ${field.comment}
     */
    <#if field.required == 'true'>
    private _${field.name}: ${field.type};
    <#else>
    private _${field.name}?: ${field.type};
    </#if>
    </#list>

    constructor(data?: Partial<${interfaceName}>) {
        if (data) {
            <#list fields as field>
            this._${field.name} = data.${field.name};
            </#list>
        }
    }

    <#list fields as field>
    /**
     * 获取${field.comment}
     */
    get ${field.name}()<#if field.required != 'true'>?</#if>: ${field.type} {
        return this._${field.name};
    }

    /**
     * 设置${field.comment}
     */
    set ${field.name}(value<#if field.required != 'true'>?</#if>: ${field.type}) {
        this._${field.name} = value;
    }

    </#list>

    /**
     * 转换为普通对象
     */
    toPlainObject(): ${interfaceName} {
        return {
            <#list fields as field>
            ${field.name}: this._${field.name}<#if field_has_next>,</#if>
            </#list>
        };
    }

    /**
     * 从普通对象创建实例
     */
    static fromPlainObject(data: Partial<${interfaceName}>): ${className} {
        return new ${className}(data);
    }
} 