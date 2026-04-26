<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="分类名称" clearable />
        <el-select v-model="query.status" placeholder="状态" clearable>
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增分类</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="name" label="分类名称" min-width="160" />
        <el-table-column prop="sort" label="排序" min-width="100" />
        <el-table-column prop="status" label="状态" min-width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="180" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-wrap">
        <el-pagination
          v-model:current-page="query.pageNum"
          v-model:page-size="query.pageSize"
          :total="total"
          layout="total, prev, pager, next"
          @current-change="loadData"
        />
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑分类' : '新增分类'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deleteCategory, getCategoryList, saveCategory } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({
  pageNum: 1,
  pageSize: 10,
  name: '',
  status: null
})
const form = reactive({
  id: null,
  name: '',
  sort: 0,
  status: 1
})
const rules = {
  name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getCategoryList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    name: '',
    sort: 0,
    status: 1
  })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await saveCategory(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteCategory(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}

.pagination-wrap {
  display: flex;
  justify-content: flex-end;
  margin-top: 16px;
}
</style>
