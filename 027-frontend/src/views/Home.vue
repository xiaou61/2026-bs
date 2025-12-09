<template>
  <div class="home-page">
    <!-- 顶部导航 -->
    <el-header class="header">
      <div class="header-content">
        <div class="logo">理发预约系统</div>
        <el-menu mode="horizontal" :default-active="activeMenu" router>
          <el-menu-item index="/home">首页</el-menu-item>
          <el-menu-item index="/store">门店</el-menu-item>
          <el-menu-item index="/hairdresser">理发师</el-menu-item>
          <el-menu-item index="/appointment/my" v-if="userStore.isLoggedIn">我的预约</el-menu-item>
        </el-menu>
        <div class="user-actions">
          <template v-if="userStore.isLoggedIn">
            <el-dropdown @command="handleCommand">
              <span class="user-info">
                <el-avatar :size="32" :src="userStore.userInfo?.avatar" />
                <span class="username">{{ userStore.userInfo?.nickname }}</span>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                  <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          <el-button v-else type="primary" @click="router.push('/login')">登录</el-button>
        </div>
      </div>
    </el-header>

    <!-- Banner -->
    <div class="banner">
      <div class="banner-content">
        <h1>专业理发  在线预约</h1>
        <p>省时省心，告别排队等待</p>
        <el-button type="primary" size="large" @click="router.push('/store')">
          立即预约
        </el-button>
      </div>
    </div>

    <!-- 服务优势 -->
    <div class="features">
      <h2>为什么选择我们</h2>
      <el-row :gutter="30">
        <el-col :span="6">
          <el-card shadow="hover">
            <div class="feature-item">
              <el-icon :size="50" color="#409EFF"><Calendar /></el-icon>
              <h3>在线预约</h3>
              <p>提前预约，告别排队</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover">
            <div class="feature-item">
              <el-icon :size="50" color="#67C23A"><UserFilled /></el-icon>
              <h3>精选理发师</h3>
              <p>资深理发师，手艺精湛</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover">
            <div class="feature-item">
              <el-icon :size="50" color="#E6A23C"><Star /></el-icon>
              <h3>会员优惠</h3>
              <p>积分兑换，专享折扣</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover">
            <div class="feature-item">
              <el-icon :size="50" color="#F56C6C"><Location /></el-icon>
              <h3>多店选择</h3>
              <p>覆盖全城，就近选择</p>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 热门门店 -->
    <div class="hot-stores">
      <h2>热门门店</h2>
      <el-row :gutter="20" v-loading="loading">
        <el-col :span="8" v-for="store in stores" :key="store.id">
          <el-card shadow="hover" class="store-card" @click="goToStore(store.id)">
            <div class="store-info">
              <h3>{{ store.name }}</h3>
              <div class="store-rating">
                <el-rate v-model="store.rating" disabled show-score text-color="#ff9900" />
              </div>
              <p><el-icon><Location /></el-icon> {{ store.address }}</p>
              <p><el-icon><Clock /></el-icon> {{ store.openTime }} - {{ store.closeTime }}</p>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { getStoreList } from '@/api/store'
import { Calendar, UserFilled, Star, Location, Clock } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

const activeMenu = ref('/home')
const loading = ref(false)
const stores = ref([])

// 获取热门门店
const fetchStores = async () => {
  loading.value = true
  try {
    const res = await getStoreList({ pageNum: 1, pageSize: 3, sortBy: 'rating' })
    stores.value = res.data.records
  } catch (error) {
    console.error('获取门店列表失败：', error)
  } finally {
    loading.value = false
  }
}

// 跳转门店详情
const goToStore = (id) => {
  router.push(`/store/${id}`)
}

// 用户操作
const handleCommand = (command) => {
  if (command === 'profile') {
    router.push('/profile')
  } else if (command === 'logout') {
    userStore.logout()
    router.push('/home')
  }
}

onMounted(() => {
  fetchStores()
})
</script>

<style scoped>
.home-page {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.header {
  background-color: #fff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.header-content {
  display: flex;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
  height: 60px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: #409EFF;
  margin-right: 50px;
}

.user-actions {
  margin-left: auto;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.username {
  margin-left: 10px;
}

.banner {
  height: 400px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.banner-content {
  text-align: center;
}

.banner h1 {
  font-size: 48px;
  margin-bottom: 20px;
}

.banner p {
  font-size: 24px;
  margin-bottom: 40px;
}

.features,
.hot-stores {
  max-width: 1200px;
  margin: 60px auto;
  padding: 0 20px;
}

.features h2,
.hot-stores h2 {
  text-align: center;
  font-size: 32px;
  margin-bottom: 40px;
  color: #333;
}

.feature-item {
  text-align: center;
  padding: 30px 20px;
}

.feature-item h3 {
  margin: 20px 0 10px;
  font-size: 20px;
  color: #333;
}

.feature-item p {
  color: #666;
}

.store-card {
  cursor: pointer;
  margin-bottom: 20px;
  transition: transform 0.3s;
}

.store-card:hover {
  transform: translateY(-5px);
}

.store-info h3 {
  font-size: 20px;
  margin-bottom: 15px;
  color: #333;
}

.store-rating {
  margin-bottom: 10px;
}

.store-info p {
  color: #666;
  margin: 8px 0;
  display: flex;
  align-items: center;
}

.store-info p .el-icon {
  margin-right: 5px;
}
</style>
