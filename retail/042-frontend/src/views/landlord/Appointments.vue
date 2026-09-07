<template>
  <div class="appointments-page">
    <h2>预约管理</h2>
    <el-tabs v-model="activeTab" @tab-change="loadData">
      <el-tab-pane label="待处理" name="0" />
      <el-tab-pane label="已确认" name="1" />
      <el-tab-pane label="已完成" name="3" />
      <el-tab-pane label="全部" name="" />
    </el-tabs>

    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="房源" min-width="200">
        <template #default="{ row }">{{ row.house?.title }}</template>
      </el-table-column>
      <el-table-column label="租客" width="120">
        <template #default="{ row }">{{ row.tenant?.realName }}</template>
      </el-table-column>
      <el-table-column label="联系电话" width="130">
        <template #default="{ row }">{{ row.tenant?.phone }}</template>
      </el-table-column>
      <el-table-column label="预约时间" width="170" prop="appointTime" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <template v-if="row.status === 0">
            <el-button size="small" type="primary" @click="handleConfirm(row)">确认</el-button>
            <el-button size="small" type="danger" @click="handleReject(row)">拒绝</el-button>
          </template>
          <template v-if="row.status === 1">
            <el-button size="small" type="success" @click="handleComplete(row)">完成看房</el-button>
            <el-button size="small" @click="openContract(row)">签订合同</el-button>
          </template>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-if="total > 0"
      v-model:current-page="page"
      :total="total"
      :page-size="10"
      layout="total, prev, pager, next"
      style="margin-top: 20px; justify-content: center"
      @current-change="loadData"
    />

    <!-- 签订合同对话框 -->
    <el-dialog v-model="contractDialogVisible" title="签订租赁合同" width="500px">
      <el-form :model="contractForm" label-width="100px">
        <el-form-item label="租客">{{ currentAppoint?.tenant?.realName }}</el-form-item>
        <el-form-item label="房源">{{ currentAppoint?.house?.title }}</el-form-item>
        <el-form-item label="月租金">¥{{ currentAppoint?.house?.price }}</el-form-item>
        <el-form-item label="租期">
          <el-date-picker v-model="contractForm.dateRange" type="daterange" start-placeholder="开始日期" end-placeholder="结束日期" style="width:100%" />
        </el-form-item>
        <el-form-item label="押金">
          <el-input-number v-model="contractForm.deposit" :min="0" style="width:150px" />
        </el-form-item>
        <el-form-item label="缴租日">
          每月 <el-input-number v-model="contractForm.paymentDay" :min="1" :max="28" style="width:80px" /> 日
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="contractDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitContract">创建合同</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { appointmentApi, contractApi } from '@/api'

const loading = ref(false)
const list = ref([])
const page = ref(1)
const total = ref(0)
const activeTab = ref('0')

const contractDialogVisible = ref(false)
const currentAppoint = ref(null)
const contractForm = reactive({
  dateRange: [],
  deposit: 0,
  paymentDay: 1
})

const statusText = (s) => ({ 0: '待确认', 1: '已确认', 2: '已拒绝', 3: '已完成', 4: '已取消' }[s])
const statusType = (s) => ({ 0: 'warning', 1: 'primary', 2: 'danger', 3: 'success', 4: 'info' }[s])

onMounted(() => loadData())

const loadData = async () => {
  loading.value = true
  try {
    const params = { page: page.value, size: 10 }
    if (activeTab.value) params.status = parseInt(activeTab.value)
    const res = await appointmentApi.getList(params)
    list.value = res.data?.records || []
    total.value = res.data?.total || 0
  } finally {
    loading.value = false
  }
}

const handleConfirm = async (row) => {
  await appointmentApi.confirm(row.id)
  ElMessage.success('已确认')
  loadData()
}

const handleReject = async (row) => {
  const { value } = await ElMessageBox.prompt('请输入拒绝原因', '拒绝预约')
  await appointmentApi.reject(row.id, value)
  ElMessage.success('已拒绝')
  loadData()
}

const handleComplete = async (row) => {
  await appointmentApi.complete(row.id)
  ElMessage.success('已完成')
  loadData()
}

const openContract = (row) => {
  currentAppoint.value = row
  contractForm.deposit = row.house?.price || 0
  contractForm.paymentDay = 1
  contractForm.dateRange = []
  contractDialogVisible.value = true
}

const submitContract = async () => {
  if (!contractForm.dateRange?.length) {
    return ElMessage.warning('请选择租期')
  }
  await contractApi.create({
    houseId: currentAppoint.value.houseId,
    tenantId: currentAppoint.value.tenantId,
    startDate: contractForm.dateRange[0],
    endDate: contractForm.dateRange[1],
    monthlyRent: currentAppoint.value.house?.price,
    deposit: contractForm.deposit,
    paymentDay: contractForm.paymentDay
  })
  ElMessage.success('合同创建成功，等待租客签署')
  contractDialogVisible.value = false
  loadData()
}
</script>

<style scoped>
.appointments-page h2 {
  margin-bottom: 20px;
}
</style>
