<template>
  <div class="my-orders-container">
    <el-card>
      <el-tabs v-model="activeTab" @tab-change="handleTabChange">
        <el-tab-pane label="我发布的" name="published">
          <el-select v-model="publishedStatus" placeholder="全部状态" clearable @change="loadPublishedOrders" style="margin-bottom: 20px">
            <el-option label="待接单" :value="0" />
            <el-option label="待取件" :value="1" />
            <el-option label="配送中" :value="2" />
            <el-option label="待确认" :value="3" />
            <el-option label="已完成" :value="4" />
            <el-option label="已取消" :value="5" />
          </el-select>

          <el-empty v-if="publishedOrders.length === 0" description="暂无订单" />
          <div v-else class="order-list">
            <el-card v-for="order in publishedOrders" :key="order.id" class="order-card" @click="viewDetail(order.id)">
              <div class="order-header">
                <el-tag :type="getStatusType(order.status)">{{ getStatusText(order.status) }}</el-tag>
                <span class="order-no">{{ order.orderNo }}</span>
              </div>
              <div class="order-content">
                <div class="order-info">
                  <div class="info-item">
                    <span class="label">物品信息：</span>
                    <span>{{ order.itemType }} · {{ getWeightText(order.itemWeight) }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">取件地址：</span>
                    <span>{{ order.pickupAddress }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">送达地址：</span>
                    <span>{{ order.deliveryAddress }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">接单人：</span>
                    <span>{{ order.takerName || '待接单' }}</span>
                  </div>
                </div>
                <div class="order-fee">¥{{ order.fee }}</div>
              </div>
            </el-card>
          </div>
          <el-pagination
            v-if="publishedTotal > 0"
            v-model:current-page="publishedPage"
            :page-size="pageSize"
            :total="publishedTotal"
            layout="prev, pager, next"
            @current-change="loadPublishedOrders"
            class="pagination"
          />
        </el-tab-pane>

        <el-tab-pane label="我接的" name="accepted">
          <el-select v-model="acceptedStatus" placeholder="全部状态" clearable @change="loadAcceptedOrders" style="margin-bottom: 20px">
            <el-option label="待取件" :value="1" />
            <el-option label="配送中" :value="2" />
            <el-option label="待确认" :value="3" />
            <el-option label="已完成" :value="4" />
            <el-option label="已取消" :value="5" />
          </el-select>

          <el-empty v-if="acceptedOrders.length === 0" description="暂无订单" />
          <div v-else class="order-list">
            <el-card v-for="order in acceptedOrders" :key="order.id" class="order-card" @click="viewDetail(order.id)">
              <div class="order-header">
                <el-tag :type="getStatusType(order.status)">{{ getStatusText(order.status) }}</el-tag>
                <span class="order-no">{{ order.orderNo }}</span>
              </div>
              <div class="order-content">
                <div class="order-info">
                  <div class="info-item">
                    <span class="label">物品信息：</span>
                    <span>{{ order.itemType }} · {{ getWeightText(order.itemWeight) }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">取件地址：</span>
                    <span>{{ order.pickupAddress }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">送达地址：</span>
                    <span>{{ order.deliveryAddress }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">发单人：</span>
                    <span>{{ order.publisherName }}</span>
                  </div>
                </div>
                <div class="order-fee">¥{{ order.fee }}</div>
              </div>
            </el-card>
          </div>
          <el-pagination
            v-if="acceptedTotal > 0"
            v-model:current-page="acceptedPage"
            :page-size="pageSize"
            :total="acceptedTotal"
            layout="prev, pager, next"
            @current-change="loadAcceptedOrders"
            class="pagination"
          />
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getMyPublishedOrders, getMyAcceptedOrders } from '../api/order'

const router = useRouter()
const activeTab = ref('published')
const publishedOrders = ref([])
const acceptedOrders = ref([])
const publishedPage = ref(1)
const acceptedPage = ref(1)
const pageSize = ref(10)
const publishedTotal = ref(0)
const acceptedTotal = ref(0)
const publishedStatus = ref(null)
const acceptedStatus = ref(null)

const loadPublishedOrders = async () => {
  try {
    const data = await getMyPublishedOrders({
      pageNum: publishedPage.value,
      pageSize: pageSize.value,
      status: publishedStatus.value
    })
    publishedOrders.value = data.records
    publishedTotal.value = data.total
  } catch (error) {
    console.error('加载订单失败', error)
  }
}

const loadAcceptedOrders = async () => {
  try {
    const data = await getMyAcceptedOrders({
      pageNum: acceptedPage.value,
      pageSize: pageSize.value,
      status: acceptedStatus.value
    })
    acceptedOrders.value = data.records
    acceptedTotal.value = data.total
  } catch (error) {
    console.error('加载订单失败', error)
  }
}

const handleTabChange = (tab) => {
  if (tab === 'published') {
    loadPublishedOrders()
  } else {
    loadAcceptedOrders()
  }
}

const viewDetail = (id) => {
  router.push(`/order-detail/${id}`)
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

onMounted(() => {
  loadPublishedOrders()
})
</script>

<style scoped>
.my-orders-container {
  max-width: 1200px;
  margin: 0 auto;
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
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
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
  align-items: center;
}

.order-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.info-item {
  font-size: 14px;
  color: #606266;
}

.label {
  color: #909399;
}

.order-fee {
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

