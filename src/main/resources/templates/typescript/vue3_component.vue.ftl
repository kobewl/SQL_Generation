<template>
  <div class="${tableName}-container">
    <!-- 搜索表单 -->
    <a-form
      :model="queryParams"
      layout="inline"
      @finish="handleSearch"
    >
      <#list fields as field>
      <a-form-item label="${field.comment}">
        <a-input
          v-model:value="queryParams.${field.name}"
          placeholder="请输入${field.comment}"
          allow-clear
        />
      </a-form-item>
      </#list>
      <a-form-item>
        <a-space>
          <a-button type="primary" html-type="submit">
            搜索
          </a-button>
          <a-button @click="handleReset">
            重置
          </a-button>
        </a-space>
      </a-form-item>
    </a-form>

    <!-- 操作按钮 -->
    <div class="table-operations">
      <a-button type="primary" @click="handleAdd">
        新增
      </a-button>
    </div>

    <!-- 数据表格 -->
    <a-table
      :columns="columns"
      :data-source="dataList"
      :loading="loading"
      :pagination="pagination"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <a-space>
            <a @click="handleEdit(record)">编辑</a>
            <a-popconfirm
              title="确定要删除吗？"
              @confirm="handleDelete(record)"
            >
              <a class="danger">删除</a>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <!-- 编辑表单弹窗 -->
    <a-modal
      v-model:visible="modalVisible"
      :title="modalTitle"
      @ok="handleModalOk"
    >
      <a-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
      >
        <#list fields as field>
        <a-form-item
          label="${field.comment}"
          name="${field.name}"
        >
          <a-input
            v-model:value="formData.${field.name}"
            placeholder="请输入${field.comment}"
          />
        </a-form-item>
        </#list>
      </a-form>
    </a-modal>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue';
import type { TablePaginationConfig } from 'ant-design-vue';
import { message } from 'ant-design-vue';
import type { ${interfaceName}, ${interfaceName}Query, ${interfaceName}CreateParams, ${interfaceName}UpdateParams } from './interface';
import { ${className}Service } from './service';

// 查询参数
const queryParams = reactive<${interfaceName}Query>({
  pageSize: 10,
  current: 1,
});

// 表格列定义
const columns = [
  <#list fields as field>
  {
    title: '${field.comment}',
    dataIndex: '${field.name}',
    key: '${field.name}',
  },
  </#list>
  {
    title: '操作',
    key: 'action',
    width: 150,
  },
];

// 表格数据
const dataList = ref<${interfaceName}[]>([]);
const loading = ref(false);
const pagination = reactive<TablePaginationConfig>({
  total: 0,
  current: 1,
  pageSize: 10,
});

// 弹窗相关
const modalVisible = ref(false);
const modalTitle = ref('新增${tableComment}');
const formRef = ref();
const formData = reactive<${interfaceName}CreateParams>({});
const formRules = {
  <#list fields as field>
  <#if field.required == 'true'>
  ${field.name}: [{ required: true, message: '请输入${field.comment}' }],
  </#if>
  </#list>
};

// 获取数据列表
const fetchList = async () => {
  loading.value = true;
  try {
    const res = await ${className}Service.list(queryParams);
    dataList.value = res.data;
    pagination.total = res.total;
    pagination.current = res.current;
    pagination.pageSize = res.pageSize;
  } finally {
    loading.value = false;
  }
};

// 搜索
const handleSearch = () => {
  queryParams.current = 1;
  fetchList();
};

// 重置
const handleReset = () => {
  queryParams.current = 1;
  <#list fields as field>
  queryParams.${field.name} = undefined;
  </#list>
  fetchList();
};

// 表格变化
const handleTableChange = (pag: TablePaginationConfig) => {
  queryParams.current = pag.current;
  queryParams.pageSize = pag.pageSize;
  fetchList();
};

// 新增
const handleAdd = () => {
  modalTitle.value = '新增${tableComment}';
  Object.assign(formData, {
    <#list fields as field>
    ${field.name}: undefined,
    </#list>
  });
  modalVisible.value = true;
};

// 编辑
const handleEdit = (record: ${interfaceName}) => {
  modalTitle.value = '编辑${tableComment}';
  Object.assign(formData, record);
  modalVisible.value = true;
};

// 删除
const handleDelete = async (record: ${interfaceName}) => {
  try {
    await ${className}Service.delete(record.id);
    message.success('删除成功');
    fetchList();
  } catch (error) {
    message.error('删除失败');
  }
};

// 提交表单
const handleModalOk = async () => {
  try {
    await formRef.value.validate();
    if (formData.id) {
      await ${className}Service.update(formData.id, formData);
      message.success('更新成功');
    } else {
      await ${className}Service.create(formData);
      message.success('创建成功');
    }
    modalVisible.value = false;
    fetchList();
  } catch (error) {
    message.error('操作失败');
  }
};

onMounted(() => {
  fetchList();
});
</script>

<style scoped>
.${tableName}-container {
  padding: 24px;
}

.table-operations {
  margin: 16px 0;
}

.danger {
  color: #ff4d4f;
}
</style> 