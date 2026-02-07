<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="路线名称" style="width: 200px;" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="name" label="路线名称" />
        <el-table-column prop="areaId" label="区域ID" width="80" />
        <el-table-column prop="deliveryUserId" label="配送员ID" width="100" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 15px;" @current-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑路线' : '新增路线'" width="450px">
      <el-form :model="form" ref="formRef" label-width="80px" :rules="rules">
        <el-form-item label="名称" prop="name"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="区域"><el-select v-model="form.areaId" style="width: 100%;"><el-option v-for="a in areas" :key="a.id" :label="a.name" :value="a.id" /></el-select></el-form-item>
        <el-form-item label="配送员"><el-input-number v-model="form.deliveryUserId" :min="1" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" /></el-form-item>
        <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getRoutePage, addRoute, updateRoute, deleteRoute, getAreaList } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const areas = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, name: '' })
const form = reactive({ id: null, name: '', areaId: null, deliveryUserId: null, description: '', status: 1 })
const rules = { name: [{ required: true, message: '请输入名称', trigger: 'blur' }] }

const loadData = async () => {
  loading.value = true
  try { const res = await getRoutePage(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false }
}
const handleAdd = () => { Object.assign(form, { id: null, name: '', areaId: null, deliveryUserId: null, description: '', status: 1 }); dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }
const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) { await updateRoute(form) } else { await addRoute(form) }
  ElMessage.success('操作成功'); dialogVisible.value = false; loadData()
}
const handleDelete = async (id) => { await deleteRoute(id); ElMessage.success('删除成功'); loadData() }
onMounted(async () => { loadData(); const res = await getAreaList(); areas.value = res.data })
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
