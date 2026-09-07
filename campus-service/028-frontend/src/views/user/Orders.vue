<template>
  <div class="orders-page">
    <el-card>
      <template #header>我的订单</template>
      <el-table :data="orders" v-loading="loading">
        <el-table-column prop="orderNo" label="订单号" width="200" />
        <el-table-column prop="startTime" label="开始时间" width="170" />
        <el-table-column prop="endTime" label="结束时间" width="170" />
        <el-table-column prop="duration" label="时长(分钟)" width="100" />
        <el-table-column prop="amount" label="费用" width="100">
          <template #default="{ row }">
            ¥{{ row.amount || '0.00' }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusText[row.status] }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.size"
          :total="pagination.total"
          :page-sizes="[10, 20, 50]"
          layout="total, sizes, prev, pager, next"
          @size-change="loadOrders"
          @current-change="loadOrders"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { orderApi } from '@/api'

const loading = ref(false)
const orders = ref([])
const pagination = reactive({ page: 1, size: 10, total: 0 })

const statusText = { 0: '进行中', 1: '已完成', 2: '异常', 3: '已取消' }
const statusType = { 0: 'warning', 1: 'success', 2: 'danger', 3: 'info' }

const loadOrders = async () => {
  loading.value = true
  try {
    const res = await orderApi.getList({
      page: pagination.page,
      size: pagination.size
    })
    orders.value = res.data.records
    pagination.total = res.data.total
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadOrders()
})
</script>

<style scoped lang="scss">
.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
