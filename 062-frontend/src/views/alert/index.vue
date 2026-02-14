<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.alertNo" placeholder="预警号" style="width: 220px" clearable />
      <el-input v-model="query.eventNo" placeholder="事件号" style="width: 220px" clearable />
      <el-select v-model="query.riskLevel" placeholder="风险等级" style="width: 140px" clearable>
        <el-option label="高风险" value="HIGH" />
        <el-option label="中风险" value="MEDIUM" />
        <el-option label="低风险" value="LOW" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="待处理" :value="0" />
        <el-option label="处理中" :value="1" />
        <el-option label="确认欺诈" :value="2" />
        <el-option label="误报放行" :value="3" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="alertNo" label="预警号" min-width="180" />
      <el-table-column prop="eventNo" label="事件号" min-width="180" />
      <el-table-column prop="userName" label="用户" width="120" />
      <el-table-column prop="riskScore" label="风险分" width="90" />
      <el-table-column prop="riskLevel" label="风险等级" width="110">
        <template #default="{ row }"><el-tag :type="levelType(row.riskLevel)">{{ levelText(row.riskLevel) }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="assigneeName" label="处理人" width="120" />
      <el-table-column label="状态" width="110">
        <template #default="{ row }"><el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="handleResult" label="处置结果" min-width="180" />
      <el-table-column label="操作" width="220" fixed="right">
        <template #default="{ row }">
          <el-button link :disabled="[2, 3].includes(row.status)" @click="openAssign(row)">指派</el-button>
          <el-button link type="primary" @click="openHandle(row)">处置</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="assignVisible" title="指派处理人" width="420px">
    <el-form :model="assignForm" label-width="80px">
      <el-form-item label="处理人">
        <el-select v-model="assignForm.assigneeId" style="width: 100%" placeholder="请选择处理人">
          <el-option v-for="u in users" :key="u.id" :label="`${u.nickname || u.username}(${u.role})`" :value="u.id" />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="assignVisible = false">取消</el-button>
      <el-button type="primary" @click="submitAssign">保存</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="handleVisible" title="预警处置" width="520px">
    <el-form :model="handleForm" label-width="90px">
      <el-form-item label="处置状态">
        <el-select v-model="handleForm.status" style="width: 100%">
          <el-option label="处理中" :value="1" />
          <el-option label="确认欺诈" :value="2" />
          <el-option label="误报放行" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="处置结果"><el-input v-model="handleForm.handleResult" maxlength="100" /></el-form-item>
      <el-form-item label="处置备注"><el-input v-model="handleForm.handleRemark" type="textarea" :rows="4" maxlength="500" show-word-limit /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="handleVisible = false">取消</el-button>
      <el-button type="primary" @click="submitHandle">提交</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { assignAlert, getAlertPage, getRiskUserList, handleAlert } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const users = ref([])

const assignVisible = ref(false)
const handleVisible = ref(false)

const query = reactive({ pageNum: 1, pageSize: 10, alertNo: '', eventNo: '', riskLevel: '', status: null })
const assignForm = reactive({ id: null, assigneeId: null })
const handleForm = reactive({ id: null, status: 1, handleResult: '', handleRemark: '' })

const levelText = (level) => ({ HIGH: '高风险', MEDIUM: '中风险', LOW: '低风险' }[level] || level)
const levelType = (level) => ({ HIGH: 'danger', MEDIUM: 'warning', LOW: 'success' }[level] || 'info')
const statusText = (status) => ({ 0: '待处理', 1: '处理中', 2: '确认欺诈', 3: '误报放行' }[status] || '未知')
const statusType = (status) => ({ 0: 'warning', 1: 'primary', 2: 'danger', 3: 'success' }[status] || 'info')

const loadData = async () => {
  loading.value = true
  try {
    const res = await getAlertPage(query)
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

const openAssign = (row) => {
  Object.assign(assignForm, { id: row.id, assigneeId: row.assigneeId })
  assignVisible.value = true
}

const submitAssign = async () => {
  if (!assignForm.assigneeId) {
    ElMessage.warning('请选择处理人')
    return
  }
  await assignAlert(assignForm)
  ElMessage.success('指派成功')
  assignVisible.value = false
  loadData()
}

const openHandle = (row) => {
  Object.assign(handleForm, { id: row.id, status: row.status === 0 ? 1 : row.status, handleResult: row.handleResult || '', handleRemark: row.handleRemark || '' })
  handleVisible.value = true
}

const submitHandle = async () => {
  await handleAlert(handleForm)
  ElMessage.success('处置成功')
  handleVisible.value = false
  loadData()
}

onMounted(async () => {
  await loadUsers()
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
