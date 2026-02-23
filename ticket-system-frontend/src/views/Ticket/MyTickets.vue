<template>
  <div class="my-tickets-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>我的票券</span>
        </div>
      </template>

      <el-tabs v-model="activeTab" @tab-click="handleTabChange">
        <el-tab-pane label="全部" name="all" />
        <el-tab-pane label="未使用" name="unused" />
        <el-tab-pane label="已使用" name="used" />
        <el-tab-pane label="已过期" name="expired" />
      </el-tabs>

      <el-table :data="tableData" style="width: 100%" stripe>
        <el-table-column prop="ticketNo" label="票券号" width="180" />
        <el-table-column prop="movieTitle" label="电影" width="200" />
        <el-table-column prop="cinemaName" label="影院" width="180" />
        <el-table-column prop="hallName" label="影厅" width="120" />
        <el-table-column prop="showDate" label="日期" width="110" />
        <el-table-column prop="showTime" label="时间" width="90" />
        <el-table-column prop="seatInfo" label="座位" width="100" />
        <el-table-column prop="price" label="金额" width="100">
          <template #default="{ row }">
            ¥{{ row.price }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="150">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleDetail(row)">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { ticketApi } from '@/api'

const router = useRouter()

const activeTab = ref('all')

const tableData = ref([])

const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

const loadData = async () => {
  try {
    const params = {
      current: pagination.current,
      size: pagination.size
    }
    if (activeTab.value !== 'all') {
      params.status = activeTab.value
    }
    const res = await ticketApi.getMyTickets(params)
    tableData.value = res.data.records
    pagination.total = res.data.total
  } catch (error) {
    ElMessage.error('加载数据失败')
  }
}

const handleTabChange = () => {
  pagination.current = 1
  loadData()
}

const handleDetail = (row) => {
  router.push(`/ticket/detail/${row.id}`)
}

const getStatusType = (status) => {
  const types = {
    unused: 'success',
    used: 'info',
    expired: 'danger'
  }
  return types[status] || ''
}

const getStatusText = (status) => {
  const texts = {
    unused: '未使用',
    used: '已使用',
    expired: '已过期'
  }
  return texts[status] || status
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.my-tickets-container {
  padding: 20px;
}

.card-header {
  font-size: 18px;
  font-weight: bold;
}

.el-pagination {
  margin-top: 20px;
  justify-content: flex-end;
}
</style>
