<template>
  <div class="data-page">
    <el-card class="main-card">
      <div class="toolbar">
        <div class="toolbar-left">
          <h3>{{ title }}</h3>
          <p>{{ description }}</p>
        </div>
        <el-button v-if="canManage" type="primary" class="add-btn" @click="openDialog()">
          <span>+</span> 新增
        </el-button>
      </div>
      <div class="search-bar">
        <el-input v-model="query.keyword" placeholder="请输入关键词搜索" clearable prefix-icon="Search" style="width:260px" />
        <el-select v-model="query.status" placeholder="选择状态" clearable style="width:160px">
          <el-option v-for="item in normalizedStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button @click="reset">重置</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe class="data-table">
        <el-table-column v-for="col in columns" :key="col.prop" :prop="col.prop" :label="col.label" :min-width="col.width || 120" show-overflow-tooltip />
        <el-table-column label="状态" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)" size="small" effect="plain">{{ getStatusLabel(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="240" align="center">
          <template #default="{ row }">
            <el-button v-if="canManage" link type="primary" @click="openDialog(row)">编辑</el-button>
            <el-button v-if="canManage" link type="warning" @click="handleProcess(row.id)">处理</el-button>
            <el-button v-if="canManage" link type="success" @click="handleFinish(row.id)">完成</el-button>
            <el-popconfirm v-if="canDelete" title="确认删除该记录？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next, sizes" class="pagination" @current-change="loadData" @size-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑记录' : '新增记录'" width="680px" class="form-dialog">
      <el-form :model="form" label-width="110px">
        <el-form-item v-for="field in formFields" :key="field.prop" :label="field.label">
          <el-select v-if="field.type === 'select'" v-model="form[field.prop]" clearable style="width:100%">
            <el-option v-for="item in field.options || normalizedStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
          <el-input v-else-if="field.type === 'textarea'" v-model="form[field.prop]" type="textarea" :rows="3" />
          <el-input v-else v-model="form[field.prop]" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="submit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>
<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'
const props = defineProps({ title: String, description: String, api: Object, columns: Array, formFields: Array, manageRoles: Array, deleteRoles: Array, statusOptions: Array, defaultStatus: { type: [String, Number], default: 'REGISTERED' } })
const userStore = useUserStore()
const normalizedStatusOptions = computed(() => props.statusOptions || [
  { label: '已建档', value: 'REGISTERED' },
  { label: '评估中', value: 'EVALUATING' },
  { label: '风险提醒', value: 'WARNING' },
  { label: '制定中', value: 'PLANNING' },
  { label: '训练中', value: 'TRAINING' },
  { label: '反馈中', value: 'FEEDBACK' },
  { label: '复评中', value: 'REVIEWING' },
  { label: '已闭环', value: 'CLOSED' }
])
const canManage = computed(() => (props.manageRoles || []).includes(userStore.user?.role))
const canDelete = computed(() => (props.deleteRoles || ['ADMIN']).includes(userStore.user?.role))
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, keyword: '', status: '' })
const form = reactive({})
const getStatusType = (status) => {
  const map = { REGISTERED: 'info', EVALUATING: 'warning', WARNING: 'danger', PLANNING: '', TRAINING: 'success', FEEDBACK: 'warning', REVIEWING: '', CLOSED: 'danger' }
  return map[status] || 'info'
}
const getStatusLabel = (status) => {
  const opt = normalizedStatusOptions.value.find(i => i.value === status)
  return opt ? opt.label : status
}
const loadData = async () => {
  loading.value = true
  try {
    const res = await props.api.page(query)
    tableData.value = res.data.records || res.data.list || []
    total.value = res.data.total || 0
  } finally { loading.value = false }
}
const reset = () => { query.pageNum = 1; query.keyword = ''; query.status = ''; loadData() }
const openDialog = row => {
  Object.keys(form).forEach(key => delete form[key])
  Object.assign(form, { status: props.defaultStatus }, row || {})
  dialogVisible.value = true
}
const submit = async () => {
  if (form.id) await props.api.update(form)
  else await props.api.add(form)
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}
const handleDelete = async id => { await props.api.delete(id); ElMessage.success('删除成功'); loadData() }
const handleProcess = async id => { await props.api.process(id); ElMessage.success('操作成功'); loadData() }
const handleFinish = async id => { await props.api.finish(id); ElMessage.success('操作成功'); loadData() }
onMounted(loadData)
</script>
<style scoped>
.data-page { padding: 0; }
.main-card { border-radius: 12px; box-shadow: 0 2px 12px rgba(46, 125, 50, 0.06); }
.main-card :deep(.el-card__body) { padding: 20px 24px; }
.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 20px;
  padding-bottom: 16px;
  border-bottom: 2px solid #E8F5E9;
}
.toolbar-left h3 {
  margin: 0 0 4px 0;
  color: #1B5E20;
  font-size: 18px;
  letter-spacing: 1px;
}
.toolbar-left p {
  margin: 0;
  color: #4CAF50;
  font-size: 13px;
}
.add-btn {
  background: linear-gradient(135deg, #2E7D32, #43A047) !important;
  border: none !important;
  border-radius: 8px !important;
  padding: 10px 20px !important;
  font-weight: 600;
}
.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
  flex-wrap: wrap;
}
.data-table {
  border-radius: 8px;
  overflow: hidden;
}
.data-table :deep(.el-table__header th) {
  background: #F1F8E9;
  color: #1B5E20;
  font-weight: 600;
}
.data-table :deep(.el-table__row:hover > td) {
  background: #F1F8E9 !important;
}
.pagination {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
}
.form-dialog :deep(.el-dialog__header) {
  background: linear-gradient(135deg, #E8F5E9, #F1F8E9);
  border-bottom: 2px solid #C8E6C9;
  padding: 16px 20px;
}
.form-dialog :deep(.el-dialog__title) {
  color: #1B5E20;
  font-weight: 600;
}
</style>
