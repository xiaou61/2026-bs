<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.orderNo" placeholder="订单号" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 140px" clearable>
        <el-option label="待支付" :value="0" />
        <el-option label="已支付" :value="1" />
        <el-option label="已发货" :value="2" />
        <el-option label="已完成" :value="3" />
        <el-option label="已取消" :value="4" />
        <el-option label="申诉中" :value="5" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="orderNo" label="订单号" min-width="220" />
      <el-table-column prop="buyerName" label="买家" width="120" />
      <el-table-column prop="itemTitle" label="商品" min-width="180" />
      <el-table-column prop="quantity" label="数量" width="70" />
      <el-table-column prop="totalPrice" label="总价" width="100" />
      <el-table-column label="状态" width="110">
        <template #default="{ row }">
          <el-tag :type="tagType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button link @click="openDetail(row)">详情</el-button>
          <el-button link type="primary" @click="deliver(row)" :disabled="row.status !== 1">发货</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="detailVisible" title="售出订单详情" width="680px">
    <el-descriptions :column="2" border>
      <el-descriptions-item label="订单号">{{ detail.orderNo }}</el-descriptions-item>
      <el-descriptions-item label="订单状态">{{ statusText(detail.status) }}</el-descriptions-item>
      <el-descriptions-item label="买家">{{ detail.buyerName }}</el-descriptions-item>
      <el-descriptions-item label="卖家">{{ detail.sellerName }}</el-descriptions-item>
      <el-descriptions-item label="商品">{{ detail.itemTitle }}</el-descriptions-item>
      <el-descriptions-item label="数量">{{ detail.quantity }}</el-descriptions-item>
      <el-descriptions-item label="总价">¥{{ detail.totalPrice }}</el-descriptions-item>
      <el-descriptions-item label="支付时间">{{ detail.payTime || '-' }}</el-descriptions-item>
      <el-descriptions-item label="完成时间">{{ detail.finishTime || '-' }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ detail.createTime || '-' }}</el-descriptions-item>
      <el-descriptions-item label="备注" :span="2">{{ detail.remark || '-' }}</el-descriptions-item>
    </el-descriptions>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deliverOrder, getSaleOrders } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, status: null, orderNo: '' })
const detailVisible = ref(false)
const detail = ref({})

const statusText = (status) => ['待支付', '已支付', '已发货', '已完成', '已取消', '申诉中'][status] || '未知'
const tagType = (status) => ({ 0: 'warning', 1: 'primary', 2: 'success', 3: 'success', 4: 'info', 5: 'danger' }[status] || 'info')

const loadData = async () => {
  loading.value = true
  try {
    const res = await getSaleOrders(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const deliver = async (row) => {
  await deliverOrder(row.id)
  ElMessage.success('发货成功')
  loadData()
}

const openDetail = (row) => {
  detail.value = row
  detailVisible.value = true
}

onMounted(loadData)
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}
</style>
