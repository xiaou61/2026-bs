<template>
  <div class="contracts-page">
    <h2>合同管理</h2>
    <div class="filter-bar">
      <el-select v-model="filters.status" placeholder="状态" clearable style="width:120px" @change="loadData">
        <el-option label="待签署" :value="0" />
        <el-option label="生效中" :value="1" />
        <el-option label="已终止" :value="2" />
        <el-option label="已到期" :value="3" />
      </el-select>
      <el-button type="primary" @click="loadData">搜索</el-button>
    </div>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="合同编号" width="180" prop="contractNo" />
      <el-table-column label="房源ID" width="80" prop="houseId" />
      <el-table-column label="房东ID" width="80" prop="landlordId" />
      <el-table-column label="租客ID" width="80" prop="tenantId" />
      <el-table-column label="月租金" width="100">
        <template #default="{ row }">¥{{ row.monthlyRent }}</template>
      </el-table-column>
      <el-table-column label="租期" width="200">
        <template #default="{ row }">{{ row.startDate }} ~ {{ row.endDate }}</template>
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" width="170" prop="createTime" />
    </el-table>
    <el-pagination v-if="total > 0" v-model:current-page="page" :total="total" :page-size="10" layout="total, prev, pager, next" style="margin-top: 20px; justify-content: center" @current-change="loadData" />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { adminApi } from '@/api'

const loading = ref(false)
const list = ref([])
const page = ref(1)
const total = ref(0)
const filters = reactive({ status: '' })

const statusText = (s) => ({ 0: '待签署', 1: '生效中', 2: '已终止', 3: '已到期' }[s])
const statusType = (s) => ({ 0: 'warning', 1: 'success', 2: 'danger', 3: 'info' }[s])

onMounted(() => loadData())

const loadData = async () => {
  loading.value = true
  try {
    const params = { page: page.value, size: 10 }
    if (filters.status !== '') params.status = filters.status
    const res = await adminApi.getContracts(params)
    list.value = res.data?.records || []
    total.value = res.data?.total || 0
  } finally { loading.value = false }
}
</script>

<style scoped>
.contracts-page h2 { margin-bottom: 20px; }
.filter-bar { display: flex; gap: 12px; margin-bottom: 20px; }
</style>
