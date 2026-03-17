<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.shipmentStatus" placeholder="出货状态" clearable>
          <el-option label="SUCCESS" value="SUCCESS" />
          <el-option label="PENDING" value="PENDING" />
          <el-option label="FAILED" value="FAILED" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="orderNo" label="订单号" min-width="170" />
        <el-table-column prop="machineName" label="设备" min-width="160" />
        <el-table-column prop="slotNo" label="货道" min-width="100" />
        <el-table-column prop="productName" label="商品" min-width="140" />
        <el-table-column prop="quantity" label="数量" min-width="80" />
        <el-table-column prop="shipmentStatus" label="出货状态" min-width="100" />
        <el-table-column prop="resultMsg" label="结果信息" min-width="220" show-overflow-tooltip />
        <el-table-column prop="shipmentTime" label="出货时间" min-width="180" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getShipmentList } from '../../api'

const loading = ref(false)
const tableData = ref([])
const query = reactive({
  pageNum: 1,
  pageSize: 20,
  shipmentStatus: ''
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await getShipmentList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

onMounted(loadData)
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}
</style>
