<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.orderNo" placeholder="订单号" style="width: 200px;" />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 150px;">
          <el-option label="待配送" :value="0" />
          <el-option label="配送中" :value="1" />
          <el-option label="已完成" :value="2" />
          <el-option label="已取消" :value="3" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="orderNo" label="订单号" width="160" />
        <el-table-column prop="userId" label="用户ID" width="80" />
        <el-table-column prop="productId" label="产品ID" width="80" />
        <el-table-column prop="quantity" label="数量" width="60" />
        <el-table-column prop="totalPrice" label="金额" width="80" />
        <el-table-column prop="contactName" label="联系人" width="80" />
        <el-table-column prop="deliveryDate" label="配送日期" width="110" />
        <el-table-column prop="status" label="状态" width="90">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusMap[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" />
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 15px;" @current-change="loadData" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getOrderPage } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, orderNo: '', status: null })
const statusMap = { 0: '待配送', 1: '配送中', 2: '已完成', 3: '已取消' }
const statusType = { 0: 'warning', 1: 'primary', 2: 'success', 3: 'info' }

const loadData = async () => {
  loading.value = true
  try { const res = await getOrderPage(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false }
}
onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
