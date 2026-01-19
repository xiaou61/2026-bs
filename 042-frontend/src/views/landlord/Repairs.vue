<template>
  <div class="repairs-page">
    <h2>报修管理</h2>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="房源" min-width="180">
        <template #default="{ row }">{{ row.house?.title }}</template>
      </el-table-column>
      <el-table-column label="报修类型" width="100" prop="type" />
      <el-table-column label="问题描述" min-width="200" prop="description" />
      <el-table-column label="租客" width="100">
        <template #default="{ row }">{{ row.tenant?.realName }}</template>
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="提交时间" width="170" prop="createTime" />
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button v-if="row.status === 0" size="small" type="primary" @click="handleProcess(row)">开始处理</el-button>
          <el-button v-if="row.status === 1" size="small" type="success" @click="handleComplete(row)">处理完成</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-if="total > 0" v-model:current-page="page" :total="total" :page-size="10" layout="total, prev, pager, next" style="margin-top: 20px; justify-content: center" @current-change="loadData" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { repairApi } from '@/api'

const loading = ref(false)
const list = ref([])
const page = ref(1)
const total = ref(0)

const statusText = (s) => ({ 0: '待处理', 1: '处理中', 2: '已完成' }[s])
const statusType = (s) => ({ 0: 'warning', 1: 'primary', 2: 'success' }[s])

onMounted(() => loadData())

const loadData = async () => {
  loading.value = true
  try {
    const res = await repairApi.getList({ page: page.value, size: 10 })
    list.value = res.data?.records || []
    total.value = res.data?.total || 0
  } finally { loading.value = false }
}

const handleProcess = async (row) => {
  await repairApi.process(row.id)
  ElMessage.success('已开始处理')
  loadData()
}

const handleComplete = async (row) => {
  const { value } = await ElMessageBox.prompt('请输入处理结果', '完成报修')
  await repairApi.complete(row.id, value)
  ElMessage.success('已完成')
  loadData()
}
</script>

<style scoped>
.repairs-page h2 { margin-bottom: 20px; }
</style>
