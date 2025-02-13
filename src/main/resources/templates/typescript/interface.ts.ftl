/**
* ${tableComment}
*/
export interface ${interfaceName} {
<#list fields as field>
    /**
    * ${field.comment}
    */
    ${field.name}<#if field.required != 'true'>?</#if>: ${field.type};
</#list>
}

/**
* ${tableComment} 查询参数
*/
export interface ${interfaceName}Query {
pageSize?: number;
current?: number;
<#list fields as field>
    ${field.name}?: ${field.type};
</#list>
}

/**
* ${tableComment} 创建参数
*/
export type ${interfaceName}CreateParams = Omit<${interfaceName}, 'id'>;

/**
* ${tableComment} 更新参数
*/
export type ${interfaceName}UpdateParams = Partial
<${interfaceName}CreateParams>;



