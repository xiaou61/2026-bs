<template>
  <div class="bills-page">
    <h2>我的账单</h2>
    <div class="stats-row">
      <div class="stat-item"><span class="label">待支付</span><span class="value warning">¥{{ stats.unpaidAmount || 0 }}</span></div>
      <div class="stat-item"><span class="label">已支付</span><span class="value success">¥{{ stats.paidAmount || 0 }}</span></div>
      <div class="stat-item"><span class="label">账户余额</span><span class="value">¥{{ userStore.userInfo?.balance || 0 }}</span></div>
    </div>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="账单编号" width="180" prop="billNo" />
      <el-table-column label="房源" min-width="180">
        <template #default="{ row }">{{ row.house?.title }}</template>
      </el-table-column>
      <el-table-column label="账单月份" width="100" prop="billMonth" />
      <el-table-column label="金额" width="100">
        <template #default="{ row }">¥{{ row.amount }}</template>
      </el-table-column>
      <el-table-column label="到期日" width="120" prop="dueDate" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已支付' : '待支付' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button v-if="row.status !== 1" size="small" type="primary" @click="handlePay(row)">支付</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-if="total > 0" v-model:current-page="page" :total="total" :page-size="10" layout="total, prev, pager, next" style="margin-top: 20px; justify-content: center" @current-change="loadData" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserStore } from '@/store/user'
import { billApi } from '@/api'

const userStore = useUserStore()
const loading = ref(false)
const list = ref([])
const page = ref(1)
const total = ref(0)
const stats = ref({})

onMounted(async () => {
  loadData()
  const res = await billApi.getStatistics()
  stats.value = res.data || {}
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await billApi.getList({ page: page.value, size: 10 })
    list.value = res.data?.records || []
    total.value = res.data?.total || 0
  } finally { loading.value = false }
}

const handlePay = async (row) => {
  await ElMessageBox.confirm(`确认支付 ¥${row.amount}？将从账户余额中扣除`, '支付账单')
  await billApi.pay(row.id)
  ElMessage.success('支付成功')
  await userStore.getUserInfo()
  loadData()
}
</script>

<style scoped>
.bills-page h2 { margin-bottom: 20px; }
.stats-row { display: flex; gap: 30px; margin-bottom: 20px; background: #fff; padding: 20px; border-radius: 8px; }
.stat-item { display: flex; flex-direction: column; }
.stat-item .label { color: #999; font-size: 13px; }
.stat-item .value { font-size: 24px; font-weight: bold; }
.stat-item .value.success { color: #67c23a; }
.stat-item .value.warning { color: #e6a23c; }
</style>
