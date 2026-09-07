<template>
  <div class="home">
    <el-alert v-if="userStore.userInfo && userStore.userInfo.authStatus !== 2" 
              title="请先完成实名认证，才能使用全部功能" 
              type="warning" 
              :closable="false"
              show-icon>
      <template #default>
        <el-button type="primary" link @click="$router.push('/auth')">去认证</el-button>
      </template>
    </el-alert>

    <div class="banner">
      <el-carousel height="300px">
        <el-carousel-item>
          <div class="banner-item" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
            <h2>校园共享 让生活更便捷</h2>
            <p>共享单车、充电宝、雨伞，随时随地</p>
          </div>
        </el-carousel-item>
        <el-carousel-item>
          <div class="banner-item" style="background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);">
            <h2>闲置物品 物尽其用</h2>
            <p>相机、投影仪、乐器，短租更划算</p>
          </div>
        </el-carousel-item>
        <el-carousel-item>
          <div class="banner-item" style="background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);">
            <h2>技能共享 互帮互助</h2>
            <p>辅导、设计、摄影，一键预约</p>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="quick-entry">
      <h3>快速入口</h3>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-card shadow="hover" @click="$router.push('/map')">
            <div class="entry-item">
              <el-icon :size="40" color="#409eff"><Bicycle /></el-icon>
              <h4>共享单车</h4>
              <p>扫码租借，随停随还</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover" @click="$router.push('/map')">
            <div class="entry-item">
              <el-icon :size="40" color="#67c23a"><CharginPile /></el-icon>
              <h4>共享充电宝</h4>
              <p>紧急充电，应急必备</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover" @click="$router.push('/idle')">
            <div class="entry-item">
              <el-icon :size="40" color="#e6a23c"><Camera /></el-icon>
              <h4>闲置物品</h4>
              <p>短租省钱，物尽其用</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover" @click="$router.push('/skill')">
            <div class="entry-item">
              <el-icon :size="40" color="#f56c6c"><User /></el-icon>
              <h4>技能服务</h4>
              <p>找人帮忙，一键预约</p>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <div class="hot-items">
      <h3>热门闲置物品</h3>
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in hotIdleItems" :key="item.id">
          <el-card shadow="hover" @click="$router.push(`/idle/${item.id}`)">
            <div class="item-card">
              <div class="item-image">
                <el-icon :size="60"><Camera /></el-icon>
              </div>
              <h4>{{ item.title }}</h4>
              <div class="item-price">
                <span class="price">¥{{ item.dailyPrice }}</span>
                <span class="unit">/天</span>
              </div>
              <div class="item-info">
                <el-tag size="small">{{ getCategoryName(item.category) }}</el-tag>
                <span class="views">{{ item.viewCount }}次浏览</span>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <div class="hot-skills">
      <h3>热门技能服务</h3>
      <el-row :gutter="20">
        <el-col :span="6" v-for="skill in hotSkills" :key="skill.id">
          <el-card shadow="hover" @click="$router.push(`/skill/${skill.id}`)">
            <div class="item-card">
              <div class="item-image">
                <el-icon :size="60"><User /></el-icon>
              </div>
              <h4>{{ skill.title }}</h4>
              <div class="item-price">
                <span class="price">¥{{ skill.hourlyPrice }}</span>
                <span class="unit">/小时</span>
              </div>
              <div class="item-info">
                <el-rate v-if="skill && skill.rating" v-model="skill.rating" disabled show-score text-color="#ff9900" />
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { getIdleList } from '@/api/idle'
import { getSkillList } from '@/api/skill'

const userStore = useUserStore()

const hotIdleItems = ref([])
const hotSkills = ref([])

const getCategoryName = (category) => {
  const map = {
    '数码设备': '数码',
    '乐器': '乐器',
    '投影设备': '投影',
    '运动器材': '运动',
    '服装道具': '服装',
    '图书教材': '图书'
  }
  return map[category] || category
}

const loadHotItems = async () => {
  try {
    const res = await getIdleList({ page: 1, size: 4 })
    hotIdleItems.value = res.data.records
  } catch (error) {
    console.error(error)
  }
}

const loadHotSkills = async () => {
  try {
    const res = await getSkillList({ page: 1, size: 4 })
    hotSkills.value = res.data.records
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadHotItems()
  loadHotSkills()
})
</script>

<style scoped>
.home {
  max-width: 1200px;
  margin: 0 auto;
}

.banner {
  margin-bottom: 30px;
  border-radius: 8px;
  overflow: hidden;
}

.banner-item {
  height: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.banner-item h2 {
  font-size: 32px;
  margin-bottom: 10px;
}

.banner-item p {
  font-size: 18px;
}

.quick-entry,
.hot-items,
.hot-skills {
  margin-bottom: 40px;
}

.quick-entry h3,
.hot-items h3,
.hot-skills h3 {
  font-size: 24px;
  margin-bottom: 20px;
  color: #303133;
}

.entry-item {
  text-align: center;
  padding: 20px;
  cursor: pointer;
  transition: transform 0.3s;
}

.entry-item:hover {
  transform: translateY(-5px);
}

.entry-item h4 {
  margin: 15px 0 8px;
  color: #303133;
}

.entry-item p {
  color: #909399;
  font-size: 14px;
}

.item-card {
  text-align: center;
  cursor: pointer;
}

.item-image {
  height: 150px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  border-radius: 4px;
  margin-bottom: 15px;
  color: #909399;
}

.item-card h4 {
  margin-bottom: 10px;
  color: #303133;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.item-price {
  margin-bottom: 10px;
}

.price {
  color: #f56c6c;
  font-size: 20px;
  font-weight: bold;
}

.unit {
  color: #909399;
  font-size: 14px;
}

.item-info {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 12px;
  color: #909399;
}
</style>

