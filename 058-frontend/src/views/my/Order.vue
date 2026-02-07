<template>
  <div class="page-container">
    <el-card>
      <template #header><span>我的订单</span></template>
      <div class="search-bar">
        <el-select v-model="query.status" placeholder="订单状态" clearable @change="loadData">
          <el-option label="待配送" :value="0" />
          <el-option label="配送中" :value="1" />
          <el-option label="已完成" :value="2" />
          <el-option label="已取消" :value="3" />
        </el-select>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="orderNo" label="订单号" width="160" />
        <el-table-column prop="quantity" label="数量" width="60" />
        <el-table-column prop="totalPrice" label="金额" width="80" />
        <el-table-column prop="address" label="地址" show-overflow-tooltip />
        <el-table-column prop="deliveryDate" label="配送日期" width="110" />
        <el-table-column prop="status" label="状态" width="90">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusMap[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-popconfirm title="确认取消？" @confirm="handleCancel(row.id)" v-if="row.status === 0">
              <template #reference><el-button link type="danger">取消</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 15px;" @current-change="loadData" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMyOrders, cancelOrder } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, status: null })
const statusMap = { 0: '待配送', 1: '配送中', 2: '已完成', 3: '已取消' }
const statusType = { 0: 'warning', 1: 'primary', 2: 'success', 3: 'info' }

const loadData = async () => {
  loading.value = true
  try { const res = await getMyOrders(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false }
}

const handleCancel = async (id) => { await cancelOrder(id); ElMessage.success('已取消'); loadData() }
onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
