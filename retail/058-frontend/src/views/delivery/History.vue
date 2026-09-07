<template>
  <div class="page-container">
    <el-card>
      <template #header><span>配送历史</span></template>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="orderId" label="订单ID" width="80" />
        <el-table-column prop="routeId" label="路线ID" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '已完成' : '异常' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" show-overflow-tooltip />
        <el-table-column prop="deliveryTime" label="配送时间" width="160" />
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 15px;" @current-change="loadData" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getDeliveryHistory } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10 })

const loadData = async () => {
  loading.value = true
  try { const res = await getDeliveryHistory(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false }
}
onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
</style>
