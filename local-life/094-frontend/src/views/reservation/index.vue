<template>
  <div class="page-container">
    <el-card v-if="isCustomer">
      <template #header>
        <span>快速预约</span>
      </template>
      <el-form :model="form" :rules="rules" ref="formRef" inline>
        <el-form-item label="门店" prop="shopId">
          <el-select v-model="form.shopId" placeholder="请选择门店" style="width: 220px" @change="handleShopChange">
            <el-option v-for="item in shopOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="座位" prop="seatId">
          <el-select v-model="form.seatId" placeholder="请选择座位" style="width: 220px">
            <el-option v-for="item in seatOptions" :key="item.id" :label="`${item.seatNo} / ${item.zoneName || '大厅'} / ${item.capacity}人`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="日期" prop="visitDate">
          <el-date-picker v-model="form.visitDate" type="date" value-format="YYYY-MM-DD" style="width: 180px" />
        </el-form-item>
        <el-form-item label="时段" prop="visitTime">
          <el-input v-model="form.visitTime" placeholder="如 14:00-15:30" style="width: 180px" />
        </el-form-item>
        <el-form-item label="人数" prop="peopleCount">
          <el-input-number v-model="form.peopleCount" :min="1" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleCreate">提交预约</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card :class="isCustomer ? 'mt16' : ''">
      <div class="search-bar">
        <el-select v-if="!isCustomer" v-model="query.shopId" placeholder="门店" clearable>
          <el-option v-for="item in shopOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.status" placeholder="预约状态" clearable>
          <el-option label="CONFIRMED" value="CONFIRMED" />
          <el-option label="COMPLETED" value="COMPLETED" />
          <el-option label="CANCELED" value="CANCELED" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="reservationNo" label="预约单号" min-width="170" />
        <el-table-column prop="shopName" label="门店" min-width="160" />
        <el-table-column prop="seatNo" label="座位" min-width="100" />
        <el-table-column prop="customerName" label="预约人" min-width="100" />
        <el-table-column prop="visitDate" label="到店日期" min-width="120" />
        <el-table-column prop="visitTime" label="时段" min-width="120" />
        <el-table-column prop="peopleCount" label="人数" min-width="80" />
        <el-table-column prop="petCompanion" label="携宠说明" min-width="120" />
        <el-table-column prop="status" label="状态" min-width="100" />
        <el-table-column prop="managerRemark" label="店长备注" min-width="180" show-overflow-tooltip />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button v-if="!isCustomer && row.status === 'CONFIRMED'" link type="primary" @click="handleStatus(row.id, 'COMPLETED')">完成</el-button>
            <el-button v-if="!isCustomer && row.status === 'CONFIRMED'" link type="warning" @click="handleStatus(row.id, 'CANCELED')">取消</el-button>
            <el-button v-if="isCustomer && row.status === 'CONFIRMED'" link type="danger" @click="handleCancel(row.id)">取消预约</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { cancelReservation, createReservation, getReservationList, getShopOptions, getShopSeatList, updateReservationStatus } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isCustomer = computed(() => (userStore.user?.role || '').toUpperCase() === 'CUSTOMER')

const loading = ref(false)
const tableData = ref([])
const shopOptions = ref([])
const seatOptions = ref([])
const formRef = ref()
const query = reactive({
  pageNum: 1,
  pageSize: 20,
  shopId: null,
  status: ''
})
const form = reactive({
  shopId: null,
  seatId: null,
  visitDate: '',
  visitTime: '14:00-15:30',
  peopleCount: 2,
  customerName: '',
  customerPhone: '',
  petCompanion: '无',
  remark: ''
})
const rules = {
  shopId: [{ required: true, message: '请选择门店', trigger: 'change' }],
  seatId: [{ required: true, message: '请选择座位', trigger: 'change' }],
  visitDate: [{ required: true, message: '请选择日期', trigger: 'change' }],
  visitTime: [{ required: true, message: '请输入时段', trigger: 'blur' }]
}

const loadOptions = async () => {
  const res = await getShopOptions()
  shopOptions.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getReservationList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const handleShopChange = async shopId => {
  form.seatId = null
  if (!shopId) {
    seatOptions.value = []
    return
  }
  const res = await getShopSeatList(shopId)
  seatOptions.value = (res.data || []).filter(item => item.reservationStatus === 'AVAILABLE' && item.seatStatus !== 'DISABLED')
}

const handleCreate = async () => {
  await formRef.value.validate()
  await createReservation(form)
  ElMessage.success('预约成功')
  Object.assign(form, { shopId: null, seatId: null, visitDate: '', visitTime: '14:00-15:30', peopleCount: 2, customerName: '', customerPhone: '', petCompanion: '无', remark: '' })
  seatOptions.value = []
  loadData()
}

const handleStatus = async (id, status) => {
  await updateReservationStatus(id, { status, managerRemark: status === 'COMPLETED' ? '已完成接待' : '店长已取消预约' })
  ElMessage.success('状态已更新')
  loadData()
}

const handleCancel = async id => {
  await cancelReservation(id)
  ElMessage.success('预约已取消')
  loadData()
}

onMounted(async () => {
  await loadOptions()
  loadData()
})
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

.mt16 {
  margin-top: 16px;
}
</style>
