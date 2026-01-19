<template>
  <div v-loading="loading" class="house-detail-page">
    <template v-if="house">
      <!-- 图片轮播 -->
      <div class="gallery-section">
        <el-carousel height="400px" v-if="images.length">
          <el-carousel-item v-for="(img, idx) in images" :key="idx">
            <img :src="img" class="carousel-image" />
          </el-carousel-item>
        </el-carousel>
        <div v-else class="no-image">暂无图片</div>
      </div>

      <div class="content-section">
        <div class="main-info">
          <!-- 基本信息 -->
          <div class="info-card">
            <div class="title-row">
              <h1>{{ house.title }}</h1>
              <el-button v-if="userStore.isTenant" :type="isFavorited ? 'warning' : 'default'" @click="handleFavorite">
                <el-icon><Star /></el-icon>
                {{ isFavorited ? '已收藏' : '收藏' }}
              </el-button>
            </div>
            <p class="address"><el-icon><Location /></el-icon>{{ house.address }}</p>
            <div class="price-row">
              <span class="price">¥{{ house.price }}</span>
              <span class="unit">/月</span>
              <el-tag v-if="house.status === 1" type="success">可租</el-tag>
              <el-tag v-else type="info">已租</el-tag>
            </div>
            <el-divider />
            <div class="specs">
              <div class="spec-item">
                <span class="label">面积</span>
                <span class="value">{{ house.area }}㎡</span>
              </div>
              <div class="spec-item">
                <span class="label">户型</span>
                <span class="value">{{ house.roomCount }}室{{ house.hallCount }}厅{{ house.bathCount }}卫</span>
              </div>
              <div class="spec-item">
                <span class="label">楼层</span>
                <span class="value">{{ house.floor }}/{{ house.totalFloor }}层</span>
              </div>
              <div class="spec-item">
                <span class="label">朝向</span>
                <span class="value">{{ house.orientation }}</span>
              </div>
              <div class="spec-item">
                <span class="label">租赁方式</span>
                <span class="value">{{ house.rentType }}</span>
              </div>
              <div class="spec-item">
                <span class="label">装修</span>
                <span class="value">{{ house.decoration }}</span>
              </div>
            </div>
          </div>

          <!-- 配套设施 -->
          <div class="info-card">
            <h3>配套设施</h3>
            <div class="facilities">
              <el-tag v-for="f in facilities" :key="f" type="info">{{ f }}</el-tag>
            </div>
          </div>

          <!-- 房源描述 -->
          <div class="info-card">
            <h3>房源描述</h3>
            <p class="description">{{ house.description || '暂无描述' }}</p>
          </div>

          <!-- 评价 -->
          <div class="info-card">
            <h3>租客评价</h3>
            <div v-if="reviews.length" class="reviews">
              <div v-for="r in reviews" :key="r.id" class="review-item">
                <div class="review-header">
                  <el-rate v-model="r.rating" disabled />
                  <span class="time">{{ r.createTime }}</span>
                </div>
                <p>{{ r.content }}</p>
              </div>
            </div>
            <el-empty v-else description="暂无评价" :image-size="60" />
          </div>
        </div>

        <!-- 侧边栏 -->
        <div class="sidebar">
          <!-- 房东信息 -->
          <div class="sidebar-card">
            <h3>房东信息</h3>
            <div class="landlord-info">
              <el-avatar :size="60">{{ house.landlordName?.[0] }}</el-avatar>
              <div class="landlord-detail">
                <p class="name">{{ house.landlordName }}</p>
                <p class="phone"><el-icon><Phone /></el-icon>{{ house.landlordPhone }}</p>
              </div>
            </div>
          </div>

          <!-- 预约看房 -->
          <div v-if="house.status === 1 && userStore.isTenant" class="sidebar-card">
            <h3>预约看房</h3>
            <el-form :model="appointForm" label-position="top">
              <el-form-item label="预约日期">
                <el-date-picker v-model="appointForm.appointTime" type="datetime" placeholder="选择日期时间" style="width: 100%" :disabled-date="disabledDate" />
              </el-form-item>
              <el-form-item label="备注">
                <el-input v-model="appointForm.remark" type="textarea" rows="2" placeholder="留言给房东" />
              </el-form-item>
              <el-button type="primary" style="width: 100%" @click="handleAppoint">提交预约</el-button>
            </el-form>
          </div>

          <div v-if="!userStore.isLoggedIn" class="sidebar-card">
            <p style="text-align:center;color:#999">登录后可预约看房</p>
            <el-button type="primary" style="width: 100%;margin-top:12px" @click="$router.push('/login')">立即登录</el-button>
          </div>
        </div>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { houseApi, appointmentApi, reviewApi } from '@/api'
import { useUserStore } from '@/store/user'

const route = useRoute()
const userStore = useUserStore()

const loading = ref(true)
const house = ref(null)
const reviews = ref([])
const isFavorited = ref(false)

const images = computed(() => house.value?.images?.split(',').filter(Boolean) || [])
const facilities = computed(() => house.value?.facilities?.split(',').filter(Boolean) || [])

const appointForm = reactive({
  appointTime: '',
  remark: ''
})

onMounted(async () => {
  const id = route.params.id
  try {
    const res = await houseApi.getDetail(id)
    house.value = res.data
    isFavorited.value = res.data?.isFavorited || false
    
    const reviewRes = await reviewApi.getByHouse(id, { page: 1, size: 5 })
    reviews.value = reviewRes.data?.records || []
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
})

const disabledDate = (date) => {
  return date.getTime() < Date.now() - 24 * 60 * 60 * 1000
}

const handleFavorite = async () => {
  try {
    await houseApi.toggleFavorite(house.value.id)
    isFavorited.value = !isFavorited.value
    ElMessage.success(isFavorited.value ? '收藏成功' : '取消收藏')
  } catch (e) {}
}

const handleAppoint = async () => {
  if (!appointForm.appointTime) {
    return ElMessage.warning('请选择预约时间')
  }
  try {
    await appointmentApi.create({
      houseId: house.value.id,
      appointTime: appointForm.appointTime,
      remark: appointForm.remark
    })
    ElMessage.success('预约成功，请等待房东确认')
    appointForm.appointTime = ''
    appointForm.remark = ''
  } catch (e) {}
}
</script>

<style scoped>
.house-detail-page {
  max-width: 1200px;
  margin: 0 auto;
}

.gallery-section {
  border-radius: 12px;
  overflow: hidden;
  margin-bottom: 24px;
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.no-image {
  height: 400px;
  background: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
}

.content-section {
  display: flex;
  gap: 24px;
}

.main-info {
  flex: 1;
}

.sidebar {
  width: 320px;
}

.info-card, .sidebar-card {
  background: #fff;
  border-radius: 12px;
  padding: 24px;
  margin-bottom: 20px;
}

.title-row {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.title-row h1 {
  font-size: 24px;
  margin-bottom: 8px;
}

.address {
  color: #999;
  display: flex;
  align-items: center;
  gap: 4px;
  margin-bottom: 16px;
}

.price-row {
  display: flex;
  align-items: baseline;
  gap: 8px;
}

.price {
  font-size: 32px;
  font-weight: bold;
  color: #f56c6c;
}

.unit {
  color: #999;
}

.specs {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.spec-item {
  display: flex;
  flex-direction: column;
}

.spec-item .label {
  color: #999;
  font-size: 13px;
  margin-bottom: 4px;
}

.spec-item .value {
  font-size: 15px;
  font-weight: 500;
}

.info-card h3 {
  font-size: 18px;
  margin-bottom: 16px;
}

.facilities {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.description {
  color: #666;
  line-height: 1.8;
  white-space: pre-wrap;
}

.sidebar-card h3 {
  font-size: 16px;
  margin-bottom: 16px;
}

.landlord-info {
  display: flex;
  gap: 16px;
  align-items: center;
}

.landlord-detail .name {
  font-weight: bold;
  margin-bottom: 4px;
}

.landlord-detail .phone {
  color: #666;
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 4px;
}

.reviews .review-item {
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.review-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.review-header .time {
  color: #999;
  font-size: 12px;
}
</style>
