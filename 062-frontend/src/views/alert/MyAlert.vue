<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.alertNo" placeholder="预警号" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 140px" clearable>
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
      <el-table-column prop="riskScore" label="风险分" width="90" />
      <el-table-column prop="riskLevel" label="风险等级" width="110">
        <template #default="{ row }">
          <el-tag :type="levelType(row.riskLevel)">{{ levelText(row.riskLevel) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="110">
        <template #default="{ row }">
          <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="handleResult" label="处置结果" min-width="180" />
      <el-table-column prop="handleTime" label="处置时间" width="180" />
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button link @click="openDetail(row)">详情</el-button>
          <el-button link type="primary" :disabled="![2, 3].includes(row.status)" @click="openAppeal(row)">发起申诉</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="detailVisible" title="预警详情" width="760px">
    <el-descriptions :column="2" border>
      <el-descriptions-item label="预警号">{{ detail.alertNo }}</el-descriptions-item>
      <el-descriptions-item label="事件号">{{ detail.eventNo }}</el-descriptions-item>
      <el-descriptions-item label="风险分">{{ detail.riskScore }}</el-descriptions-item>
      <el-descriptions-item label="风险等级">{{ levelText(detail.riskLevel) }}</el-descriptions-item>
      <el-descriptions-item label="状态">{{ statusText(detail.status) }}</el-descriptions-item>
      <el-descriptions-item label="处理人">{{ detail.assigneeName || '-' }}</el-descriptions-item>
      <el-descriptions-item label="处置结果" :span="2">{{ detail.handleResult || '-' }}</el-descriptions-item>
      <el-descriptions-item label="处置备注" :span="2">{{ detail.handleRemark || '-' }}</el-descriptions-item>
      <el-descriptions-item label="处置时间">{{ detail.handleTime || '-' }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ detail.createTime || '-' }}</el-descriptions-item>
    </el-descriptions>
  </el-dialog>

  <el-dialog v-model="appealVisible" title="发起申诉" width="560px">
    <el-form ref="appealRef" :model="appealForm" :rules="appealRules" label-width="90px">
      <el-form-item label="关联预警">
        <el-input :model-value="appealForm.alertNo" disabled />
      </el-form-item>
      <el-form-item label="申诉内容" prop="content">
        <el-input v-model="appealForm.content" type="textarea" :rows="4" maxlength="1000" show-word-limit />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="appealVisible = false">取消</el-button>
      <el-button type="primary" @click="submitAppeal">提交</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addAppeal, getAlertById, getMyAlertPage } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const detailVisible = ref(false)
const appealVisible = ref(false)
const detail = ref({})
const appealRef = ref()

const query = reactive({ pageNum: 1, pageSize: 10, alertNo: '', status: null })
const appealForm = reactive({ alertId: null, alertNo: '', content: '' })

const appealRules = {
  content: [{ required: true, message: '请输入申诉内容', trigger: 'blur' }]
}

const levelText = (level) => ({ HIGH: '高风险', MEDIUM: '中风险', LOW: '低风险' }[level] || level)
const levelType = (level) => ({ HIGH: 'danger', MEDIUM: 'warning', LOW: 'success' }[level] || 'info')
const statusText = (status) => ({ 0: '待处理', 1: '处理中', 2: '确认欺诈', 3: '误报放行' }[status] || '未知')
const statusType = (status) => ({ 0: 'warning', 1: 'primary', 2: 'danger', 3: 'success' }[status] || 'info')

const loadData = async () => {
  loading.value = true
  try {
    const res = await getMyAlertPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openDetail = async (row) => {
  const res = await getAlertById(row.id)
  detail.value = res.data || {}
  detailVisible.value = true
}

const openAppeal = (row) => {
  Object.assign(appealForm, { alertId: row.id, alertNo: row.alertNo, content: '' })
  appealVisible.value = true
}

const submitAppeal = async () => {
  await appealRef.value.validate()
  await addAppeal({ alertId: appealForm.alertId, content: appealForm.content })
  ElMessage.success('申诉提交成功')
  appealVisible.value = false
  await loadData()
}

onMounted(loadData)
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}
</style>
