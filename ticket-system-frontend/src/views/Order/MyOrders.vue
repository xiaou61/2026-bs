<template>
  <div class="my-orders-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>我的订单</span>
        </div>
      </template>

      <el-tabs v-model="activeTab" @tab-click="handleTabChange">
        <el-tab-pane label="全部" name="all" />
        <el-tab-pane label="待支付" name="unpaid" />
        <el-tab-pane label="已支付" name="paid" />
        <el-tab-pane label="已完成" name="completed" />
        <el-tab-pane label="已取消" name="canceled" />
      </el-tabs>

      <el-table :data="tableData" style="width: 100%" stripe>
        <el-table-column prop="orderNo" label="订单号" width="180" />
        <el-table-column prop="movieTitle" label="电影" width="200" />
        <el-table-column prop="cinemaName" label="影院" width="180" />
        <el-table-column prop="showDate" label="日期" width="110" />
        <el-table-column prop="showTime" label="时间" width="90" />
        <el-table-column prop="seatInfo" label="座位" width="120" />
        <el-table-column prop="totalAmount" label="金额" width="100">
          <template #default="{ row }">
            ¥{{ row.totalAmount }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="250">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleDetail(row)">详情</el-button>
            <el-button v-if="row.status === 'unpaid'" type="success" size="small" @click="handlePay(row)">支付</el-button>
            <el-button v-if="row.status === 'unpaid'" type="danger" size="small" @click="handleCancel(row)">取消</el-button>
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
import { ElMessage, ElMessageBox } from 'element-plus'
import { orderApi } from '@/api'

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
    const res = await orderApi.getMyOrders(params)
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
  router.push(`/order/detail/${row.id}`)
}

const handlePay = (row) => {
  ElMessageBox.confirm('确定要支付该订单吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await orderApi.payOrder(row.id)
      ElMessage.success('支付成功')
      loadData()
    } catch (error) {
      ElMessage.error('支付失败')
    }
  })
}

const handleCancel = (row) => {
  ElMessageBox.confirm('确定要取消该订单吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await orderApi.cancelOrder(row.id)
      ElMessage.success('取消成功')
      loadData()
    } catch (error) {
      ElMessage.error('取消失败')
    }
  })
}

const getStatusType = (status) => {
  const types = {
    unpaid: 'warning',
    paid: 'success',
    completed: 'info',
    canceled: 'danger'
  }
  return types[status] || ''
}

const getStatusText = (status) => {
  const texts = {
    unpaid: '未支付',
    paid: '已支付',
    completed: '已完成',
    canceled: '已取消'
  }
  return texts[status] || status
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.my-orders-container {
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
