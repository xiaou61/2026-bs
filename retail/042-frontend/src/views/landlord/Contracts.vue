<template>
  <div class="contracts-page">
    <h2>合同管理</h2>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="合同编号" width="180" prop="contractNo" />
      <el-table-column label="房源" min-width="200">
        <template #default="{ row }">{{ row.house?.title }}</template>
      </el-table-column>
      <el-table-column label="租客" width="100">
        <template #default="{ row }">{{ row.tenant?.realName }}</template>
      </el-table-column>
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
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button v-if="row.status === 1" size="small" type="danger" @click="handleTerminate(row)">终止</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-if="total > 0" v-model:current-page="page" :total="total" :page-size="10" layout="total, prev, pager, next" style="margin-top: 20px; justify-content: center" @current-change="loadData" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { contractApi } from '@/api'

const loading = ref(false)
const list = ref([])
const page = ref(1)
const total = ref(0)

const statusText = (s) => ({ 0: '待签署', 1: '生效中', 2: '已终止', 3: '已到期' }[s])
const statusType = (s) => ({ 0: 'warning', 1: 'success', 2: 'danger', 3: 'info' }[s])

onMounted(() => loadData())

const loadData = async () => {
  loading.value = true
  try {
    const res = await contractApi.getList({ page: page.value, size: 10 })
    list.value = res.data?.records || []
    total.value = res.data?.total || 0
  } finally { loading.value = false }
}

const handleTerminate = async (row) => {
  const { value } = await ElMessageBox.prompt('请输入终止原因', '终止合同')
  await contractApi.terminate(row.id, value)
  ElMessage.success('合同已终止')
  loadData()
}
</script>

<style scoped>
.contracts-page h2 { margin-bottom: 20px; }
</style>
