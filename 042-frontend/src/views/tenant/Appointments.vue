<template>
  <div class="appointments-page">
    <h2>我的预约</h2>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="房源" min-width="200">
        <template #default="{ row }">{{ row.house?.title }}</template>
      </el-table-column>
      <el-table-column label="房东" width="100">
        <template #default="{ row }">{{ row.landlord?.realName }}</template>
      </el-table-column>
      <el-table-column label="预约时间" width="170" prop="appointTime" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-popconfirm v-if="row.status === 0" title="确定取消？" @confirm="handleCancel(row)">
            <template #reference>
              <el-button size="small" type="danger">取消</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-if="total > 0" v-model:current-page="page" :total="total" :page-size="10" layout="total, prev, pager, next" style="margin-top: 20px; justify-content: center" @current-change="loadData" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { appointmentApi } from '@/api'

const loading = ref(false)
const list = ref([])
const page = ref(1)
const total = ref(0)

const statusText = (s) => ({ 0: '待确认', 1: '已确认', 2: '已拒绝', 3: '已完成', 4: '已取消' }[s])
const statusType = (s) => ({ 0: 'warning', 1: 'primary', 2: 'danger', 3: 'success', 4: 'info' }[s])

onMounted(() => loadData())

const loadData = async () => {
  loading.value = true
  try {
    const res = await appointmentApi.getList({ page: page.value, size: 10 })
    list.value = res.data?.records || []
    total.value = res.data?.total || 0
  } finally { loading.value = false }
}

const handleCancel = async (row) => {
  await appointmentApi.cancel(row.id)
  ElMessage.success('已取消')
  loadData()
}
</script>

<style scoped>
.appointments-page h2 { margin-bottom: 20px; }
</style>
