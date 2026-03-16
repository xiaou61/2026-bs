<template>
  <div>
    <el-card class="mb16" v-if="userStore.isUser">
      <template #header>提交退改签申请</template>
      <el-form :model="form" label-width="100px" inline>
        <el-form-item label="订单">
          <el-select v-model="form.orderId" placeholder="选择订单" style="width: 220px">
            <el-option v-for="item in myOrders" :key="item.id" :label="`${item.orderNo} ${item.trainCode}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="服务类型">
          <el-select v-model="form.serviceType" style="width: 140px">
            <el-option label="退票" value="REFUND" />
            <el-option label="改签" value="CHANGE" />
          </el-select>
        </el-form-item>
        <el-form-item label="目标班次" v-if="form.serviceType === 'CHANGE'">
          <el-select v-model="form.targetScheduleId" placeholder="选择班次" style="width: 220px">
            <el-option v-for="item in schedules" :key="item.id" :label="`${item.id} ${item.departureTime}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="原因">
          <el-input v-model="form.reason" placeholder="请输入原因" style="width: 260px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleApply">提交申请</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card>
      <div class="toolbar">
        <el-select v-model="query.serviceType" placeholder="服务类型" clearable style="width: 140px">
          <el-option label="退票" value="REFUND" />
          <el-option label="改签" value="CHANGE" />
        </el-select>
        <el-select v-model="query.reviewStatus" placeholder="审核状态" clearable style="width: 140px">
          <el-option label="待审核" value="PENDING" />
          <el-option label="已通过" value="APPROVED" />
          <el-option label="已驳回" value="REJECTED" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="orderId" label="订单ID" width="90" />
        <el-table-column prop="serviceType" label="类型" width="100" />
        <el-table-column prop="targetScheduleInfo" label="目标班次" min-width="180" show-overflow-tooltip />
        <el-table-column prop="reason" label="申请原因" min-width="180" show-overflow-tooltip />
        <el-table-column prop="reviewStatus" label="审核状态" width="100" />
        <el-table-column prop="reviewRemark" label="审核备注" min-width="160" show-overflow-tooltip />
        <el-table-column label="操作" width="180" v-if="!userStore.isUser">
          <template #default="{ row }">
            <el-button link type="success" @click="handleReview(row.id, 'APPROVED')" v-if="row.reviewStatus === 'PENDING'">通过</el-button>
            <el-button link type="danger" @click="handleReview(row.id, 'REJECTED')" v-if="row.reviewStatus === 'PENDING'">驳回</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination class="mt16" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" @current-change="loadData" />
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { applyAfterSale, getAfterSaleList, getMyOrderList, getScheduleEnabledList, reviewAfterSale } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const myOrders = ref([])
const schedules = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, serviceType: '', reviewStatus: '' })
const form = reactive({ orderId: null, serviceType: 'REFUND', targetScheduleId: null, reason: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getAfterSaleList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const loadOptions = async () => {
  if (userStore.isUser) {
    const [orderRes, scheduleRes] = await Promise.all([
      getMyOrderList({ pageNum: 1, pageSize: 100, status: 'PAID' }),
      getScheduleEnabledList()
    ])
    myOrders.value = orderRes.data.records || []
    schedules.value = scheduleRes.data || []
  }
}

const handleApply = async () => {
  await applyAfterSale(form)
  ElMessage.success('申请已提交')
  form.reason = ''
  loadData()
}

const handleReview = async (id, reviewStatus) => {
  const reviewRemark = await ElMessageBox.prompt('请输入审核备注', reviewStatus === 'APPROVED' ? '通过申请' : '驳回申请', {
    confirmButtonText: '确定',
    cancelButtonText: '取消'
  }).then(({ value }) => value).catch(() => '')
  if (reviewRemark === '') return
  await reviewAfterSale(id, { reviewStatus, reviewRemark })
  ElMessage.success('处理完成')
  loadData()
}

onMounted(async () => {
  await Promise.all([loadData(), loadOptions()])
})
</script>

<style scoped>
.mb16 {
  margin-bottom: 16px;
}

.toolbar {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
}

.mt16 {
  margin-top: 16px;
}
</style>
