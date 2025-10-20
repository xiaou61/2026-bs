<template>
  <div class="orders-container">
    <el-card>
      <el-form :inline="true" :model="filters" class="search-form">
        <el-form-item label="订单编号">
          <el-input v-model="filters.orderNo" placeholder="请输入订单编号" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="filters.status" placeholder="全部" clearable>
            <el-option label="待接单" :value="0" />
            <el-option label="待取件" :value="1" />
            <el-option label="配送中" :value="2" />
            <el-option label="待确认" :value="3" />
            <el-option label="已完成" :value="4" />
            <el-option label="已取消" :value="5" />
            <el-option label="已退款" :value="6" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadOrders">查询</el-button>
          <el-button @click="resetFilters">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="orders" border style="width: 100%">
        <el-table-column prop="orderNo" label="订单编号" width="180" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="publisherName" label="发单人" width="120" />
        <el-table-column prop="takerName" label="接单人" width="120">
          <template #default="{ row }">
            {{ row.takerName || '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="itemType" label="物品类型" width="100" />
        <el-table-column label="物品重量" width="100">
          <template #default="{ row }">{{ getWeightText(row.itemWeight) }}</template>
        </el-table-column>
        <el-table-column prop="pickupAddress" label="取件地址" min-width="200" show-overflow-tooltip />
        <el-table-column prop="deliveryAddress" label="送达地址" min-width="200" show-overflow-tooltip />
        <el-table-column label="跑腿费" width="100">
          <template #default="{ row }">¥{{ row.fee }}</template>
        </el-table-column>
        <el-table-column label="创建时间" width="160">
          <template #default="{ row }">{{ formatTime(row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="viewDetail(row)">详情</el-button>
            <el-button
              v-if="row.status !== 4 && row.status !== 5 && row.status !== 6"
              type="danger"
              size="small"
              @click="handleCancel(row)"
            >
              强制取消
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-if="total > 0"
        v-model:current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadOrders"
        class="pagination"
      />
    </el-card>

    <el-dialog v-model="showDetailDialog" title="订单详情" width="800px">
      <el-descriptions v-if="currentOrder" :column="2" border>
        <el-descriptions-item label="订单编号">{{ currentOrder.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getStatusType(currentOrder.status)">{{ getStatusText(currentOrder.status) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="发单人">{{ currentOrder.publisherName }}</el-descriptions-item>
        <el-descriptions-item label="接单人">{{ currentOrder.takerName || '待接单' }}</el-descriptions-item>
        <el-descriptions-item label="快递公司">{{ currentOrder.expressCompany }}</el-descriptions-item>
        <el-descriptions-item label="取件码">{{ currentOrder.pickupCode }}</el-descriptions-item>
        <el-descriptions-item label="物品类型">{{ currentOrder.itemType }}</el-descriptions-item>
        <el-descriptions-item label="物品重量">{{ getWeightText(currentOrder.itemWeight) }}</el-descriptions-item>
        <el-descriptions-item label="取件地址" :span="2">{{ currentOrder.pickupAddress }}</el-descriptions-item>
        <el-descriptions-item label="送达地址" :span="2">{{ currentOrder.deliveryAddress }}</el-descriptions-item>
        <el-descriptions-item label="跑腿费">¥{{ currentOrder.fee }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ currentOrder.contactPhone }}</el-descriptions-item>
        <el-descriptions-item label="备注说明" :span="2">{{ currentOrder.remark || '无' }}</el-descriptions-item>
        <el-descriptions-item label="创建时间" :span="2">{{ formatTime(currentOrder.createTime) }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getOrderList, handleOrder } from '../../api/admin'
import { ElMessage, ElMessageBox } from 'element-plus'

const orders = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const showDetailDialog = ref(false)
const currentOrder = ref(null)

const filters = reactive({
  orderNo: '',
  status: null
})

const loadOrders = async () => {
  try {
    const data = await getOrderList({
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      ...filters
    })
    orders.value = data.records
    total.value = data.total
  } catch (error) {
    console.error('加载订单列表失败', error)
  }
}

const resetFilters = () => {
  filters.orderNo = ''
  filters.status = null
  loadOrders()
}

const viewDetail = (order) => {
  currentOrder.value = order
  showDetailDialog.value = true
}

const handleCancel = async (order) => {
  try {
    await ElMessageBox.confirm('确认强制取消该订单吗？此操作将退款给发单人。', '提示')
    await handleOrder(order.id, 'CANCEL')
    ElMessage.success('操作成功')
    loadOrders()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('操作失败', error)
    }
  }
}

const getStatusType = (status) => {
  const types = { 0: '', 1: 'warning', 2: 'primary', 3: 'success', 4: 'success', 5: 'info', 6: 'info' }
  return types[status] || ''
}

const getStatusText = (status) => {
  const texts = { 0: '待接单', 1: '待取件', 2: '配送中', 3: '待确认', 4: '已完成', 5: '已取消', 6: '已退款' }
  return texts[status] || '未知'
}

const getWeightText = (weight) => {
  const texts = { SMALL: '小件', MEDIUM: '中件', LARGE: '大件' }
  return texts[weight] || weight
}

const formatTime = (time) => {
  if (!time) return ''
  return new Date(time).toLocaleString('zh-CN')
}

onMounted(() => {
  loadOrders()
})
</script>

<style scoped>
.orders-container {
  max-width: 100%;
}

.search-form {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>

