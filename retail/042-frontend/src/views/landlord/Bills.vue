<template>
  <div class="bills-page">
    <h2>账单管理</h2>
    <div class="stats-row">
      <div class="stat-item"><span class="label">累计收入</span><span class="value success">¥{{ stats.paidAmount || 0 }}</span></div>
      <div class="stat-item"><span class="label">待收款</span><span class="value warning">¥{{ stats.unpaidAmount || 0 }}</span></div>
      <div class="stat-item"><span class="label">待收账单</span><span class="value">{{ stats.unpaidCount || 0 }}笔</span></div>
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
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已支付' : '待支付' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="支付时间" width="170" prop="paidTime" />
    </el-table>
    <el-pagination v-if="total > 0" v-model:current-page="page" :total="total" :page-size="10" layout="total, prev, pager, next" style="margin-top: 20px; justify-content: center" @current-change="loadData" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { billApi } from '@/api'

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
