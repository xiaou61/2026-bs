<template>
  <view class="container">
    <view class="filter-row">
      <picker mode="selector" :range="statusNames" @change="onStatusChange">
        <view class="picker">{{ currentStatusLabel }}</view>
      </picker>
      <button size="mini" @click="loadData">刷新</button>
      <button size="mini" type="primary" @click="goCreate">报修</button>
    </view>

    <view v-for="item in list" :key="item.id" class="card order-card">
      <view class="top-row">
        <text class="order-no">{{ item.orderNo }}</text>
        <text :class="['status', statusClass(item.status)]">{{ statusText(item.status) }}</text>
      </view>
      <view class="line">联系人：{{ item.contactName }} {{ item.contactPhone }}</view>
      <view class="line">地址：{{ item.address }}</view>
      <view class="line">故障：{{ item.faultDesc }}</view>
      <view class="line">支付状态：{{ item.payStatus === 1 ? '已支付' : '未支付' }}</view>
      <view class="line">创建时间：{{ item.createTime }}</view>
      <view class="action-row">
        <button size="mini" @click="goDetail(item.id)">详情</button>
        <button v-if="item.status === 3 && item.payStatus !== 1" size="mini" type="primary" @click="handlePay(item.id)">支付</button>
        <button v-if="canCancel(item.status)" size="mini" type="warn" @click="handleCancel(item.id)">取消</button>
      </view>
    </view>

    <view v-if="!list.length" class="empty">暂无工单</view>
  </view>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { getMyOrders, cancelOrder, payOrder } from '../../api'

const list = ref([])
const statusOptions = [
  { label: '全部状态', value: null },
  { label: '待受理', value: 0 },
  { label: '已派单', value: 1 },
  { label: '上门中', value: 2 },
  { label: '待支付', value: 3 },
  { label: '已完成', value: 4 },
  { label: '已取消', value: 5 }
]
const statusIndex = ref(0)

const statusNames = statusOptions.map(item => item.label)
const currentStatusLabel = computed(() => statusOptions[statusIndex.value].label)

const loadData = async () => {
  try {
    const status = statusOptions[statusIndex.value].value
    const params = { pageNum: 1, pageSize: 50 }
    if (status !== null) params.status = status
    const res = await getMyOrders(params)
    list.value = res.data.records || []
  } catch (e) {}
}

const onStatusChange = (e) => {
  statusIndex.value = Number(e.detail.value)
  loadData()
}

const statusText = (status) => ({ 0: '待受理', 1: '已派单', 2: '上门中', 3: '待支付', 4: '已完成', 5: '已取消' }[status] || '未知')

const statusClass = (status) => ({
  0: 'status-gray',
  1: 'status-orange',
  2: 'status-blue',
  3: 'status-yellow',
  4: 'status-green',
  5: 'status-red'
}[status] || 'status-gray')

const canCancel = (status) => status !== 4 && status !== 5

const goDetail = (id) => uni.navigateTo({ url: `/pages/order/detail?id=${id}` })
const goCreate = () => uni.navigateTo({ url: '/pages/order/create' })

const handleCancel = (id) => {
  uni.showModal({
    title: '提示',
    content: '确定取消该工单吗？',
    success: async (res) => {
      if (!res.confirm) return
      try {
        await cancelOrder(id)
        uni.showToast({ title: '已取消', icon: 'success' })
        loadData()
      } catch (e) {}
    }
  })
}

const handlePay = async (id) => {
  try {
    await payOrder(id, { content: '小程序用户支付完成' })
    uni.showToast({ title: '支付成功', icon: 'success' })
    loadData()
  } catch (e) {}
}

onMounted(loadData)
</script>

<style scoped>
.filter-row { display: flex; gap: 12rpx; margin-bottom: 16rpx; }
.picker { background: #fff; border-radius: 10rpx; padding: 16rpx 20rpx; flex: 1; }
.order-card { margin-bottom: 16rpx; }
.top-row { display: flex; justify-content: space-between; margin-bottom: 10rpx; }
.order-no { font-size: 26rpx; color: #2d5baf; }
.status { font-size: 24rpx; }
.line { color: #666; font-size: 25rpx; margin-top: 6rpx; }
.action-row { display: flex; gap: 12rpx; margin-top: 14rpx; }
.status-gray { color: #909399; }
.status-orange { color: #e6a23c; }
.status-blue { color: #409eff; }
.status-yellow { color: #e6a23c; }
.status-green { color: #67c23a; }
.status-red { color: #f56c6c; }
.empty { text-align: center; color: #999; margin-top: 120rpx; }
</style>
