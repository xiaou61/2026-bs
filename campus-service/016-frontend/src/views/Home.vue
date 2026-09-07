<template>
  <div class="home-container">
    <el-card class="filter-card">
      <el-form :inline="true" :model="filters">
        <el-form-item label="物品类型">
          <el-select v-model="filters.itemType" placeholder="全部" clearable>
            <el-option label="文件" value="文件" />
            <el-option label="服饰" value="服饰" />
            <el-option label="食品" value="食品" />
            <el-option label="数码" value="数码" />
            <el-option label="日用品" value="日用品" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="物品重量">
          <el-select v-model="filters.itemWeight" placeholder="全部" clearable>
            <el-option label="小件" value="SMALL" />
            <el-option label="中件" value="MEDIUM" />
            <el-option label="大件" value="LARGE" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadOrders">查询</el-button>
          <el-button @click="resetFilters">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <div class="order-list">
      <el-empty v-if="orders.length === 0" description="暂无订单" />
      <el-card v-for="order in orders" :key="order.id" class="order-card" @click="viewDetail(order.id)">
        <div class="order-header">
          <el-tag :type="getStatusType(order.status)">{{ getStatusText(order.status) }}</el-tag>
          <span class="order-no">订单号：{{ order.orderNo }}</span>
        </div>
        <div class="order-content">
          <div class="order-info">
            <div class="info-row">
              <el-icon><Box /></el-icon>
              <span>{{ order.itemType }} · {{ getWeightText(order.itemWeight) }}</span>
            </div>
            <div class="info-row">
              <el-icon><Location /></el-icon>
              <span>{{ order.pickupAddress }}</span>
            </div>
            <div class="info-row">
              <el-icon><Location /></el-icon>
              <span>{{ order.deliveryAddress }}</span>
            </div>
            <div class="info-row">
              <el-icon><Clock /></el-icon>
              <span>{{ formatTime(order.expectTime) }}</span>
            </div>
          </div>
          <div class="order-fee">
            <div class="fee-amount">¥{{ order.fee }}</div>
            <el-button type="primary" size="small" @click.stop="handleAccept(order.id)">
              接单
            </el-button>
          </div>
        </div>
      </el-card>
    </div>

    <el-pagination
      v-if="total > 0"
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :total="total"
      layout="prev, pager, next"
      @current-change="loadOrders"
      class="pagination"
    />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getOrderSquare, acceptOrder } from '../api/order'
import { Box, Location, Clock } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const orders = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const filters = reactive({
  itemType: '',
  itemWeight: ''
})

const loadOrders = async () => {
  try {
    const data = await getOrderSquare({
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      ...filters
    })
    orders.value = data.records
    total.value = data.total
  } catch (error) {
    console.error('加载订单失败', error)
  }
}

const resetFilters = () => {
  filters.itemType = ''
  filters.itemWeight = ''
  loadOrders()
}

const viewDetail = (id) => {
  router.push(`/order-detail/${id}`)
}

const handleAccept = async (id) => {
  try {
    await ElMessageBox.confirm('确认接单吗？', '提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await acceptOrder(id)
    ElMessage.success('接单成功')
    loadOrders()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('接单失败', error)
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
.home-container {
  max-width: 1200px;
  margin: 0 auto;
}

.filter-card {
  margin-bottom: 20px;
}

.order-list {
  display: grid;
  gap: 16px;
}

.order-card {
  cursor: pointer;
  transition: all 0.3s;
}

.order-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  transform: translateY(-2px);
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid #ebeef5;
}

.order-no {
  font-size: 14px;
  color: #909399;
}

.order-content {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}

.order-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.info-row {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  color: #606266;
}

.order-fee {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 12px;
}

.fee-amount {
  font-size: 24px;
  font-weight: bold;
  color: #f56c6c;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>

