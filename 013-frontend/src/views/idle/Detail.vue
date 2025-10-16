<template>
  <div class="idle-detail">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <div class="item-images">
            <div class="main-image">
              <el-icon :size="200"><Camera /></el-icon>
            </div>
          </div>

          <el-divider />

          <div class="item-detail">
            <h2>{{ item?.title }}</h2>
            <div class="item-meta">
              <el-tag>{{ item?.category }}</el-tag>
              <span>{{ item?.viewCount }}次浏览</span>
              <el-rate v-if="item" v-model="item.rating" disabled show-score text-color="#ff9900" />
            </div>

            <el-divider />

            <el-descriptions :column="2" border>
              <el-descriptions-item label="新旧程度">{{ getConditionText(item?.conditionLevel) }}</el-descriptions-item>
              <el-descriptions-item label="原价">¥{{ item?.originalPrice }}</el-descriptions-item>
              <el-descriptions-item label="小时租金">{{ item?.hourlyPrice ? `¥${item.hourlyPrice}` : '-' }}</el-descriptions-item>
              <el-descriptions-item label="日租金">{{ item?.dailyPrice ? `¥${item.dailyPrice}` : '-' }}</el-descriptions-item>
              <el-descriptions-item label="周租金">{{ item?.weeklyPrice ? `¥${item.weeklyPrice}` : '-' }}</el-descriptions-item>
              <el-descriptions-item label="押金">¥{{ item?.depositAmount }}</el-descriptions-item>
              <el-descriptions-item label="取货方式" :span="2">
                {{ item?.pickupMethod === 0 ? '上门' : item?.pickupMethod === 1 ? '自取' : '均可' }}
              </el-descriptions-item>
              <el-descriptions-item label="取货地址" :span="2">{{ item?.pickupAddress }}</el-descriptions-item>
            </el-descriptions>

            <el-divider />

            <h3>物品描述</h3>
            <p class="description">{{ item?.description }}</p>
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card>
          <template #header>
            <span>出租者信息</span>
          </template>
          <div class="owner-info">
            <el-avatar :size="60">{{ owner?.nickname?.charAt(0) }}</el-avatar>
            <h4>{{ owner?.nickname }}</h4>
            <p>信用分：{{ owner?.creditScore }}</p>
          </div>
        </el-card>

        <el-card style="margin-top: 20px;">
          <template #header>
            <span>租借物品</span>
          </template>
          
          <div class="rent-section">
            <div class="price-options">
              <el-radio-group v-model="rentType">
                <el-radio label="daily" v-if="item?.dailyPrice">日租 ¥{{ item?.dailyPrice }}/天</el-radio>
                <el-radio label="weekly" v-if="item?.weeklyPrice">周租 ¥{{ item?.weeklyPrice }}/周</el-radio>
              </el-radio-group>
            </div>

            <el-divider />

            <div class="total-price">
              <span>押金：</span>
              <span class="price">¥{{ item?.depositAmount }}</span>
            </div>

            <el-button type="primary" size="large" @click="handleRent" style="width: 100%">
              立即租借
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getIdleDetail } from '@/api/idle'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()

const item = ref(null)
const owner = ref(null)
const rentType = ref('daily')

const getConditionText = (level) => {
  const map = {
    1: '全新',
    2: '九九新',
    3: '九五新',
    4: '九成新',
    5: '八成以下'
  }
  return map[level] || '-'
}

const loadDetail = async () => {
  try {
    const res = await getIdleDetail(route.params.id)
    item.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const handleRent = () => {
  ElMessage.info('请联系出租者协商租借事宜')
}

onMounted(() => {
  loadDetail()
})
</script>

<style scoped>
.item-images {
  display: flex;
  justify-content: center;
  padding: 40px 0;
}

.main-image {
  width: 400px;
  height: 300px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  border-radius: 8px;
  color: #909399;
}

.item-detail h2 {
  margin-bottom: 15px;
  color: #303133;
}

.item-meta {
  display: flex;
  align-items: center;
  gap: 20px;
  color: #909399;
  font-size: 14px;
}

.description {
  line-height: 1.8;
  color: #606266;
}

.owner-info {
  text-align: center;
  padding: 20px;
}

.owner-info h4 {
  margin: 15px 0 5px;
}

.owner-info p {
  color: #909399;
}

.rent-section {
  padding: 10px 0;
}

.price-options {
  margin-bottom: 20px;
}

.total-price {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  margin-bottom: 20px;
}

.total-price .price {
  color: #f56c6c;
  font-size: 24px;
  font-weight: bold;
}
</style>


