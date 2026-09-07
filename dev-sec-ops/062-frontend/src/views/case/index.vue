<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.caseNo" placeholder="案件号" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="待分配" :value="0" />
        <el-option label="处理中" :value="1" />
        <el-option label="已关闭" :value="2" />
      </el-select>
      <el-select v-model="query.priority" placeholder="优先级" style="width: 120px" clearable>
        <el-option label="低" :value="1" />
        <el-option label="中" :value="2" />
        <el-option label="高" :value="3" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="openAdd">新增案件</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="caseNo" label="案件号" min-width="180" />
      <el-table-column prop="alertNo" label="预警号" min-width="180" />
      <el-table-column prop="caseType" label="类型" width="120" />
      <el-table-column prop="priority" label="优先级" width="90" />
      <el-table-column prop="ownerName" label="负责人" width="120" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="summary" label="摘要" min-width="220" show-overflow-tooltip />
      <el-table-column label="操作" width="220" fixed="right">
        <template #default="{ row }">
          <el-button link @click="openEdit(row)">编辑</el-button>
          <el-button link type="primary" @click="close(row)" :disabled="row.status === 2">关闭</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑案件' : '新增案件'" width="620px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="预警" prop="alertId">
        <el-select v-model="form.alertId" :disabled="!!form.id" style="width: 100%" filterable>
          <el-option v-for="a in alerts" :key="a.id" :label="`${a.alertNo} / ${a.eventNo}`" :value="a.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="案件类型" prop="caseType"><el-input v-model="form.caseType" maxlength="40" /></el-form-item>
      <el-form-item label="优先级" prop="priority"><el-input-number v-model="form.priority" :min="1" :max="3" /></el-form-item>
      <el-form-item label="负责人">
        <el-select v-model="form.ownerId" style="width: 100%" filterable>
          <el-option v-for="u in users" :key="u.id" :label="`${u.nickname || u.username}`" :value="u.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态"><el-select v-model="form.status" style="width: 100%"><el-option label="待分配" :value="0" /><el-option label="处理中" :value="1" /><el-option label="已关闭" :value="2" /></el-select></el-form-item>
      <el-form-item label="摘要"><el-input v-model="form.summary" type="textarea" :rows="3" maxlength="500" show-word-limit /></el-form-item>
      <el-form-item label="结论"><el-input v-model="form.result" type="textarea" :rows="3" maxlength="500" show-word-limit /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { addCase, closeCase, getAlertPage, getCasePage, getRiskUserList, updateCase } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const users = ref([])
const alerts = ref([])

const query = reactive({ pageNum: 1, pageSize: 10, caseNo: '', status: null, priority: null })
const form = reactive({})

const rules = {
  alertId: [{ required: true, message: '请选择预警', trigger: 'change' }],
  caseType: [{ required: true, message: '请输入案件类型', trigger: 'blur' }],
  priority: [{ required: true, message: '请选择优先级', trigger: 'blur' }]
}

const statusText = (status) => ({ 0: '待分配', 1: '处理中', 2: '已关闭' }[status] || '未知')
const statusType = (status) => ({ 0: 'warning', 1: 'primary', 2: 'success' }[status] || 'info')

const loadData = async () => {
  loading.value = true
  try {
    const res = await getCasePage(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const loadUsers = async () => {
  const res = await getRiskUserList()
  users.value = res.data || []
}

const loadAlerts = async () => {
  const [alertRes, caseRes] = await Promise.all([
    getAlertPage({ pageNum: 1, pageSize: 500 }),
    getCasePage({ pageNum: 1, pageSize: 500 })
  ])
  const allAlerts = alertRes.data.records || []
  const usedAlertIds = new Set((caseRes.data.records || []).map(i => i.alertId))
  alerts.value = allAlerts.filter(i => !usedAlertIds.has(i.id))
}

const openAdd = async () => {
  await loadAlerts()
  if (!alerts.value.length) {
    ElMessage.warning('暂无可建案预警')
    return
  }
  Object.assign(form, { id: null, alertId: null, caseType: '', priority: 2, ownerId: null, status: 0, summary: '', result: '' })
  dialogVisible.value = true
}

const openEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateCase(form)
  } else {
    await addCase(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const close = async (row) => {
  const { value } = await ElMessageBox.prompt('请输入关闭结论', '关闭案件', { inputValue: row.result || '' })
  await closeCase(row.id, value)
  ElMessage.success('已关闭')
  loadData()
}

onMounted(async () => {
  await loadUsers()
  await loadAlerts()
  await loadData()
})
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}
</style>
