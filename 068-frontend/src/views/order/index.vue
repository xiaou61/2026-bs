<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.orderNo" placeholder="订单号" clearable style="width: 220px" />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 140px">
          <el-option label="待支付" value="WAIT_PAY" />
          <el-option label="已支付" value="PAID" />
          <el-option label="已取消" value="CANCELED" />
          <el-option label="已完成" value="FINISHED" />
        </el-select>
        <el-input v-if="isAdmin" v-model.number="query.userId" placeholder="用户ID" clearable style="width: 120px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="info" @click="handleExport">导出CSV</el-button>
        <el-button v-if="isUser" type="success" @click="openAddDialog">新建订单</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="orderNo" label="订单号" min-width="160" />
        <el-table-column v-if="isAdmin" prop="userName" label="用户" min-width="110" />
        <el-table-column prop="spotName" label="景点" min-width="140" />
        <el-table-column prop="travelerName" label="出行人" min-width="110" />
        <el-table-column prop="travelDate" label="出行日期" width="120" />
        <el-table-column prop="quantity" label="人数" width="80" />
        <el-table-column prop="totalAmount" label="金额" width="100" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="170" />
        <el-table-column label="操作" width="210" fixed="right">
          <template #default="{ row }">
            <el-button v-if="row.status === 'WAIT_PAY'" link type="warning" @click="handleCancel(row)">取消</el-button>
            <el-button v-if="isUser && row.status === 'WAIT_PAY'" link type="primary" @click="handlePay(row)">支付</el-button>
            <el-button v-if="isUser && row.status === 'PAID'" link type="success" @click="handleFinish(row)">完成</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pager">
        <el-pagination
          v-model:current-page="query.pageNum"
          v-model:page-size="query.pageSize"
          :total="total"
          :page-sizes="[10, 20, 30]"
          layout="total, sizes, prev, pager, next, jumper"
          @current-change="loadData"
          @size-change="loadData"
        />
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" title="新建订单" width="560px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="景点" prop="spotId">
          <el-select v-model="form.spotId" filterable style="width: 100%">
            <el-option v-for="item in spots" :key="item.id" :label="`${item.name}（¥${item.price}）`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="出行人" prop="travelerId">
          <el-select v-model="form.travelerId" filterable style="width: 100%">
            <el-option v-for="item in travelers" :key="item.id" :label="`${item.name} ${item.phone}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="出行日期" prop="travelDate">
          <el-date-picker v-model="form.travelDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="人数" prop="quantity">
          <el-input-number v-model="form.quantity" :min="1" :max="10" />
        </el-form-item>
        <el-form-item label="联系人" prop="contactName"><el-input v-model="form.contactName" maxlength="50" /></el-form-item>
        <el-form-item label="联系电话" prop="contactPhone"><el-input v-model="form.contactPhone" maxlength="20" /></el-form-item>
        <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" :rows="3" maxlength="500" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">提交订单</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addOrder, cancelOrder, exportOrder, finishOrder, getMyOrderPage, getOrderPage, getSpotList, getTravelerList, payOrder } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isUser = computed(() => userStore.user?.role === 'USER')

const loading = ref(false)
const total = ref(0)
const tableData = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const spots = ref([])
const travelers = ref([])

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  orderNo: '',
  status: '',
  userId: null
})

const form = reactive({
  spotId: null,
  travelerId: null,
  travelDate: '',
  quantity: 1,
  contactName: '',
  contactPhone: '',
  remark: ''
})

const rules = {
  spotId: [{ required: true, message: '请选择景点', trigger: 'change' }],
  travelerId: [{ required: true, message: '请选择出行人', trigger: 'change' }],
  travelDate: [{ required: true, message: '请选择日期', trigger: 'change' }],
  quantity: [{ required: true, message: '请输入人数', trigger: 'change' }],
  contactName: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
  contactPhone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = isAdmin.value ? await getOrderPage(query) : await getMyOrderPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const loadOptions = async () => {
  const [spotRes, travelerRes] = await Promise.all([getSpotList({}), getTravelerList()])
  spots.value = spotRes.data || []
  travelers.value = travelerRes.data || []
}

const resetForm = () => {
  Object.assign(form, { spotId: null, travelerId: null, travelDate: '', quantity: 1, contactName: '', contactPhone: '', remark: '' })
}

const openAddDialog = async () => {
  await loadOptions()
  if (!travelers.value.length) {
    ElMessage.warning('请先在常用出行人模块添加出行人')
    return
  }
  resetForm()
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await addOrder(form)
  ElMessage.success('创建成功')
  dialogVisible.value = false
  loadData()
}

const handleCancel = async (row) => {
  await cancelOrder({ id: row.id })
  ElMessage.success('订单已取消')
  loadData()
}

const handlePay = async (row) => {
  await payOrder({ id: row.id })
  ElMessage.success('支付成功')
  loadData()
}

const handleFinish = async (row) => {
  await finishOrder({ id: row.id })
  ElMessage.success('订单已完成')
  loadData()
}

const handleExport = async () => {
  const params = { ...query }
  if (!isAdmin.value) {
    delete params.userId
  }
  const res = await exportOrder(params)
  const content = res.data || ''
  const blob = new Blob(['\uFEFF' + content], { type: 'text/csv;charset=utf-8;' })
  const url = window.URL.createObjectURL(blob)
  const link = document.createElement('a')
  link.href = url
  link.download = `orders_${Date.now()}.csv`
  link.click()
  window.URL.revokeObjectURL(url)
  ElMessage.success('导出成功')
}

const statusText = (status) => {
  if (status === 'WAIT_PAY') return '待支付'
  if (status === 'PAID') return '已支付'
  if (status === 'CANCELED') return '已取消'
  if (status === 'FINISHED') return '已完成'
  return status
}

const statusType = (status) => {
  if (status === 'WAIT_PAY') return 'warning'
  if (status === 'PAID') return 'primary'
  if (status === 'CANCELED') return 'info'
  if (status === 'FINISHED') return 'success'
  return ''
}

onMounted(loadData)
</script>

<style scoped>
.search-bar {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 14px;
}

.pager {
  margin-top: 14px;
  display: flex;
  justify-content: flex-end;
}
</style>
