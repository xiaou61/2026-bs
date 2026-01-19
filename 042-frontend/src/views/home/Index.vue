<template>
  <div class="home-page">
    <!-- Hero Section -->
    <div class="hero">
      <div class="hero-content">
        <h1>找到您的理想住所</h1>
        <p>安全、便捷、专业的房屋租赁平台</p>
        <div class="search-box">
          <el-input v-model="keyword" placeholder="搜索房源地址、小区名称..." size="large" @keyup.enter="handleSearch">
            <template #append>
              <el-button type="primary" @click="handleSearch">
                <el-icon><Search /></el-icon>
                搜索
              </el-button>
            </template>
          </el-input>
        </div>
      </div>
    </div>

    <!-- Stats -->
    <div class="stats-section">
      <div class="stat-item">
        <div class="stat-num">{{ stats.houseCount || 0 }}+</div>
        <div class="stat-label">优质房源</div>
      </div>
      <div class="stat-item">
        <div class="stat-num">{{ stats.landlordCount || 0 }}+</div>
        <div class="stat-label">认证房东</div>
      </div>
      <div class="stat-item">
        <div class="stat-num">{{ stats.tenantCount || 0 }}+</div>
        <div class="stat-label">成功租户</div>
      </div>
    </div>

    <!-- Featured Houses -->
    <div class="section">
      <h2 class="section-title">热门房源</h2>
      <div class="house-grid">
        <div v-for="house in houses" :key="house.id" class="house-card" @click="$router.push(`/house/${house.id}`)">
          <div class="house-image">
            <img :src="house.images?.split(',')[0] || '/placeholder.jpg'" :alt="house.title" />
            <el-tag v-if="house.status === 1" class="status-tag" type="success">可租</el-tag>
            <el-tag v-else class="status-tag" type="info">已租</el-tag>
          </div>
          <div class="house-info">
            <h3>{{ house.title }}</h3>
            <p class="address"><el-icon><Location /></el-icon>{{ house.address }}</p>
            <div class="tags">
              <el-tag size="small">{{ house.area }}㎡</el-tag>
              <el-tag size="small" type="info">{{ house.roomCount }}室{{ house.hallCount }}厅</el-tag>
            </div>
            <div class="price">
              <span class="amount">¥{{ house.price }}</span>
              <span class="unit">/月</span>
            </div>
          </div>
        </div>
      </div>
      <div class="view-more">
        <el-button type="primary" plain @click="$router.push('/house')">查看更多房源</el-button>
      </div>
    </div>

    <!-- Features -->
    <div class="features-section">
      <h2 class="section-title">为什么选择我们</h2>
      <div class="features-grid">
        <div class="feature-item">
          <el-icon class="feature-icon"><Checked /></el-icon>
          <h3>房源真实</h3>
          <p>所有房源均经过实地验证，确保信息真实可靠</p>
        </div>
        <div class="feature-item">
          <el-icon class="feature-icon"><Shield /></el-icon>
          <h3>交易安全</h3>
          <p>合同电子签约，资金托管，全程保障您的权益</p>
        </div>
        <div class="feature-item">
          <el-icon class="feature-icon"><Service /></el-icon>
          <h3>贴心服务</h3>
          <p>专业客服团队，报修响应及时，让您住得安心</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { houseApi } from '@/api'

const router = useRouter()
const keyword = ref('')
const houses = ref([])
const stats = ref({})

onMounted(async () => {
  try {
    const res = await houseApi.getList({ page: 1, size: 6, status: 1 })
    houses.value = res.data?.records || []
    stats.value = {
      houseCount: res.data?.total || 0,
      landlordCount: Math.floor((res.data?.total || 0) * 0.3) + 10,
      tenantCount: Math.floor((res.data?.total || 0) * 2) + 50
    }
  } catch (e) {}
})

const handleSearch = () => {
  router.push({ path: '/house', query: { keyword: keyword.value } })
}
</script>

<style scoped>
.home-page {
  max-width: 1200px;
  margin: 0 auto;
}

.hero {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 16px;
  padding: 60px 40px;
  text-align: center;
  color: #fff;
  margin-bottom: 40px;
}

.hero h1 {
  font-size: 36px;
  margin-bottom: 12px;
}

.hero p {
  font-size: 18px;
  opacity: 0.9;
  margin-bottom: 30px;
}

.search-box {
  max-width: 600px;
  margin: 0 auto;
}

.stats-section {
  display: flex;
  justify-content: center;
  gap: 80px;
  margin-bottom: 50px;
}

.stat-item {
  text-align: center;
}

.stat-num {
  font-size: 36px;
  font-weight: bold;
  color: #409eff;
}

.stat-label {
  color: #666;
}

.section {
  margin-bottom: 50px;
}

.section-title {
  text-align: center;
  font-size: 28px;
  margin-bottom: 30px;
  color: #333;
}

.house-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
}

.house-card {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0,0,0,0.08);
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
}

.house-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0,0,0,0.12);
}

.house-image {
  position: relative;
  height: 180px;
}

.house-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.status-tag {
  position: absolute;
  top: 12px;
  right: 12px;
}

.house-info {
  padding: 16px;
}

.house-info h3 {
  font-size: 16px;
  margin-bottom: 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.address {
  font-size: 13px;
  color: #999;
  margin-bottom: 8px;
  display: flex;
  align-items: center;
  gap: 4px;
}

.tags {
  display: flex;
  gap: 8px;
  margin-bottom: 12px;
}

.price {
  color: #f56c6c;
}

.price .amount {
  font-size: 22px;
  font-weight: bold;
}

.price .unit {
  font-size: 14px;
}

.view-more {
  text-align: center;
  margin-top: 30px;
}

.features-section {
  background: #fff;
  border-radius: 16px;
  padding: 50px;
  margin-bottom: 40px;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 40px;
}

.feature-item {
  text-align: center;
}

.feature-icon {
  font-size: 48px;
  color: #409eff;
  margin-bottom: 16px;
}

.feature-item h3 {
  font-size: 18px;
  margin-bottom: 8px;
}

.feature-item p {
  color: #666;
  font-size: 14px;
}
</style>
