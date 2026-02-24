<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-select v-model="query.status" placeholder="支付状态" clearable style="width: 160px">
          <el-option label="待支付" value="WAIT" />
          <el-option label="成功" value="SUCCESS" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="payNo" label="支付单号" width="220" />
        <el-table-column prop="orderId" label="订单ID" width="100" />
        <el-table-column prop="payType" label="支付方式" width="120" />
        <el-table-column prop="payAmount" label="金额" width="100" />
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column prop="payTime" label="支付时间" width="180" />
      </el-table>

      <el-pagination class="mt16" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" @current-change="loadData" />
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getPaymentList } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, status: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getPaymentList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

onMounted(loadData)
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
}

.mt16 {
  margin-top: 16px;
}
</style>
