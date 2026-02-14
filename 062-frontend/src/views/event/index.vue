<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.eventNo" placeholder="事件号" style="width: 220px" clearable />
      <el-select v-model="query.riskLevel" placeholder="风险等级" style="width: 140px" clearable>
        <el-option label="高风险" value="HIGH" />
        <el-option label="中风险" value="MEDIUM" />
        <el-option label="低风险" value="LOW" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="放行" :value="1" />
        <el-option label="拦截" :value="2" />
        <el-option label="复核中" :value="3" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button v-if="!isRiskRole" type="success" @click="openReport">上报事件</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="eventNo" label="事件号" min-width="190" />
      <el-table-column prop="userName" label="用户" width="120" />
      <el-table-column prop="eventType" label="类型" width="110" />
      <el-table-column prop="channel" label="渠道" width="100" />
      <el-table-column prop="amount" label="金额" width="110" />
      <el-table-column prop="riskScore" label="风险分" width="90" />
      <el-table-column prop="riskLevel" label="风险等级" width="110">
        <template #default="{ row }">
          <el-tag :type="levelType(row.riskLevel)">{{ levelText(row.riskLevel) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="上报时间" width="170" />
      <el-table-column label="操作" width="220" fixed="right">
        <template #default="{ row }">
          <el-button link @click="openDetail(row)">详情</el-button>
          <el-button v-if="isRiskRole" link @click="changeStatus(row, 1)">放行</el-button>
          <el-button v-if="isRiskRole" link type="danger" @click="changeStatus(row, 2)">拦截</el-button>
          <el-button v-if="isRiskRole" link type="warning" @click="changeStatus(row, 3)">复核</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="detailVisible" title="事件详情" width="720px">
    <el-descriptions :column="2" border>
      <el-descriptions-item label="事件号">{{ detail.eventNo }}</el-descriptions-item>
      <el-descriptions-item label="用户">{{ detail.userName }}</el-descriptions-item>
      <el-descriptions-item label="账号">{{ detail.accountNo }}</el-descriptions-item>
      <el-descriptions-item label="设备ID">{{ detail.deviceId || '-' }}</el-descriptions-item>
      <el-descriptions-item label="IP">{{ detail.ip || '-' }}</el-descriptions-item>
      <el-descriptions-item label="金额">{{ detail.amount }}</el-descriptions-item>
      <el-descriptions-item label="风险分">{{ detail.riskScore }}</el-descriptions-item>
      <el-descriptions-item label="风险等级">{{ levelText(detail.riskLevel) }}</el-descriptions-item>
      <el-descriptions-item label="命中规则" :span="2">{{ detail.hitRules || '-' }}</el-descriptions-item>
      <el-descriptions-item label="备注" :span="2">{{ detail.remark || '-' }}</el-descriptions-item>
    </el-descriptions>
  </el-dialog>

  <el-dialog v-model="reportVisible" title="上报风险事件" width="560px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="账号" prop="accountNo"><el-input v-model="form.accountNo" maxlength="80" /></el-form-item>
      <el-form-item label="设备ID"><el-input v-model="form.deviceId" maxlength="80" /></el-form-item>
      <el-form-item label="IP"><el-input v-model="form.ip" maxlength="50" /></el-form-item>
      <el-form-item label="金额" prop="amount"><el-input-number v-model="form.amount" :min="0.01" :precision="2" style="width: 100%" /></el-form-item>
      <el-form-item label="事件类型" prop="eventType"><el-input v-model="form.eventType" maxlength="40" /></el-form-item>
      <el-form-item label="渠道" prop="channel"><el-input v-model="form.channel" maxlength="40" /></el-form-item>
      <el-form-item label="备注"><el-input v-model="form.remark" maxlength="255" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="reportVisible = false">取消</el-button>
      <el-button type="primary" @click="submitReport">提交</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getEventById, getEventPage, getMyEventPage, reportEvent, updateEventStatus } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isRiskRole = computed(() => ['ADMIN', 'ANALYST'].includes(userStore.user?.role))

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const detailVisible = ref(false)
const reportVisible = ref(false)
const detail = ref({})
const formRef = ref()

const query = reactive({ pageNum: 1, pageSize: 10, eventNo: '', riskLevel: '', status: null })
const form = reactive({ accountNo: '', deviceId: '', ip: '', amount: 1, eventType: '', channel: '', remark: '' })

const rules = {
  accountNo: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  amount: [{ required: true, message: '请输入金额', trigger: 'blur' }],
  eventType: [{ required: true, message: '请输入事件类型', trigger: 'blur' }],
  channel: [{ required: true, message: '请输入渠道', trigger: 'blur' }]
}

const levelText = (level) => ({ HIGH: '高风险', MEDIUM: '中风险', LOW: '低风险' }[level] || level)
const levelType = (level) => ({ HIGH: 'danger', MEDIUM: 'warning', LOW: 'success' }[level] || 'info')
const statusText = (status) => ({ 1: '放行', 2: '拦截', 3: '复核中' }[status] || '未知')
const statusType = (status) => ({ 1: 'success', 2: 'danger', 3: 'warning' }[status] || 'info')

const loadData = async () => {
  loading.value = true
  try {
    const api = isRiskRole.value ? getEventPage : getMyEventPage
    const res = await api(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const openDetail = async (row) => {
  const res = await getEventById(row.id)
  detail.value = res.data
  detailVisible.value = true
}

const changeStatus = async (row, status) => {
  await updateEventStatus({ id: row.id, status })
  ElMessage.success('状态已更新')
  loadData()
}

const openReport = () => {
  Object.assign(form, { accountNo: '', deviceId: '', ip: '', amount: 1, eventType: '', channel: '', remark: '' })
  reportVisible.value = true
}

const submitReport = async () => {
  await formRef.value.validate()
  await reportEvent(form)
  ElMessage.success('上报成功')
  reportVisible.value = false
  loadData()
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
