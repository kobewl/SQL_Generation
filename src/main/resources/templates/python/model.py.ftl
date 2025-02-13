from sqlalchemy import Column, Integer, String, Float, Boolean, DateTime, Date
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class ${className}(Base):
    """
    ${tableComment}
    """
    __tablename__ = '${tableName}'

    <#list fields as field>
    ${field.name} = Column(${field.type}<#if field.comment>, comment='${field.comment}'</#if>)
    </#list>

    def __repr__(self):
        return f"<${className}(<#list fields as field>${field.name}={self.${field.name}}<#if field_has_next>, </#if></#list>)>"

    def to_dict(self):
        return {
            <#list fields as field>
            '${field.name}': self.${field.name}<#if field_has_next>,</#if>
            </#list>
        } 