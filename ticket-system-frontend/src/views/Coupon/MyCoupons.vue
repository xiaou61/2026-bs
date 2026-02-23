<template>
  <div class="my-coupons-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>我的优惠券</span>
        </div>
      </template>

      <el-tabs v-model="activeTab" @tab-click="handleTabChange">
        <el-tab-pane label="全部" name="all" />
        <el-tab-pane label="未使用" name="unused" />
        <el-tab-pane label="已使用" name="used" />
        <el-tab-pane label="已过期" name="expired" />
      </el-tabs>

      <el-row :gutter="20" class="coupon-grid">
        <el-col v-for="item in tableData" :key="item.id" :span="8">
          <div class="coupon-card" :class="getCouponClass(item.status)">
            <div class="coupon-left">
              <div class="coupon-value">
                <span v-if="item.type === 'full_reduction'" class="value-amount">¥{{ item.value }}</span>
                <span v-else class="value-discount">{{ item.value }}折</span>
              </div>
              <div class="coupon-condition">满{{ item.minAmount }}元可用</div>
            </div>
            <div class="coupon-right">
              <div class="coupon-name">{{ item.name }}</div>
              <div class="coupon-type">{{ item.type === 'full_reduction' ? '满减券' : '折扣券' }}</div>
              <div class="coupon-time">
                <div>生效：{{ item.startTime }}</div>
                <div>失效：{{ item.endTime }}</div>
              </div>
              <div class="coupon-status">
                <el-tag :type="getStatusType(item.status)" size="small">
                  {{ getStatusText(item.status) }}
                </el-tag>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>

      <el-empty v-if="tableData.length === 0" description="暂无优惠券" />

      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        :page-sizes="[9, 18, 27, 36]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { couponApi } from '@/api'

const activeTab = ref('all')

const tableData = ref([])

const pagination = reactive({
  current: 1,
  size: 9,
  total: 0
})

const loadData = async () => {
  try {
    const params = {
      current: pagination.current,
      size: pagination.size
    }
    if (activeTab.value !== 'all') {
      params.status = activeTab.value
    }
    const res = await couponApi.getMyCoupons(params)
    tableData.value = res.data.records
    pagination.total = res.data.total
  } catch (error) {
    ElMessage.error('加载数据失败')
  }
}

const handleTabChange = () => {
  pagination.current = 1
  loadData()
}

const getCouponClass = (status) => {
  return {
    'coupon-unused': status === 'unused',
    'coupon-used': status === 'used',
    'coupon-expired': status === 'expired'
  }
}

const getStatusType = (status) => {
  const types = {
    unused: 'success',
    used: 'info',
    expired: 'danger'
  }
  return types[status] || ''
}

const getStatusText = (status) => {
  const texts = {
    unused: '未使用',
    used: '已使用',
    expired: '已过期'
  }
  return texts[status] || status
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.my-coupons-container {
  padding: 20px;
}

.card-header {
  font-size: 18px;
  font-weight: bold;
}

.coupon-grid {
  margin: 20px 0;
  min-height: 400px;
}

.coupon-card {
  display: flex;
  height: 140px;
  margin-bottom: 20px;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s;
}

.coupon-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.coupon-unused {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.coupon-used {
  background: #f5f7fa;
  color: #909399;
}

.coupon-expired {
  background: #f5f7fa;
  color: #c0c4cc;
}

.coupon-left {
  flex: 0 0 120px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-right: 2px dashed rgba(255, 255, 255, 0.3);
}

.coupon-value {
  margin-bottom: 10px;
}

.value-amount,
.value-discount {
  font-size: 36px;
  font-weight: bold;
}

.coupon-condition {
  font-size: 12px;
  opacity: 0.9;
}

.coupon-right {
  flex: 1;
  padding: 15px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.coupon-name {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.coupon-type {
  font-size: 12px;
  opacity: 0.8;
  margin-bottom: 10px;
}

.coupon-time {
  font-size: 11px;
  opacity: 0.8;
}

.coupon-time > div {
  margin-bottom: 3px;
}

.coupon-status {
  text-align: right;
}

.coupon-used .coupon-left,
.coupon-expired .coupon-left {
  border-right-color: rgba(0, 0, 0, 0.1);
}

.el-pagination {
  margin-top: 20px;
  justify-content: flex-end;
}
</style>
