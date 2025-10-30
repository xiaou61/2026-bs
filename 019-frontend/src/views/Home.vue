<template>
  <div class="home">
    <el-row :gutter="20">
      <el-col :span="6" v-for="item in stats" :key="item.title">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" :style="{ backgroundColor: item.color }">
              <el-icon :size="30"><component :is="item.icon" /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ item.value }}</div>
              <div class="stat-title">{{ item.title }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>最近运动记录</span>
              <el-button text type="primary" @click="$router.push('/sport/record')">
                查看更多
              </el-button>
            </div>
          </template>
          <el-empty v-if="!recentRecords.length" description="暂无运动记录" />
          <div v-else class="record-list">
            <div v-for="record in recentRecords" :key="record.id" class="record-item">
              <div class="record-type">
                <el-tag>{{ getSportTypeName(record.sportType) }}</el-tag>
              </div>
              <div class="record-info">
                <div>距离: {{ record.distance }}km</div>
                <div>时长: {{ record.duration }}分钟</div>
                <div>积分: +{{ record.pointsEarned }}</div>
              </div>
              <div class="record-date">{{ record.sportDate }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>积分排行榜</span>
              <el-button text type="primary" @click="$router.push('/rank')">
                查看更多
              </el-button>
            </div>
          </template>
          <el-empty v-if="!rankList.length" description="暂无排行数据" />
          <div v-else class="rank-list">
            <div v-for="(user, index) in rankList" :key="user.id" class="rank-item">
              <div class="rank-number" :class="{ 'top-three': index < 3 }">
                {{ index + 1 }}
              </div>
              <el-avatar :size="35">{{ user.nickname?.[0] }}</el-avatar>
              <div class="rank-name">{{ user.nickname }}</div>
              <div class="rank-points">{{ user.points }}积分</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card>
          <template #header>
            <span>快捷操作</span>
          </template>
          <div class="quick-actions">
            <el-button type="primary" icon="Plus" @click="$router.push('/sport/create')">
              运动打卡
            </el-button>
            <el-button type="success" icon="Document" @click="$router.push('/plan/create')">
              创建计划
            </el-button>
            <el-button type="warning" icon="User" @click="$router.push('/activity/create')">
              发起活动
            </el-button>
            <el-button type="info" icon="School" @click="$router.push('/venue/list')">
              预约场馆
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getRecords, getStats } from '@/api/sport'
import { getPointsRank } from '@/api/rank'
import { Trophy, Timer, Position, Medal } from '@element-plus/icons-vue'

const stats = ref([
  { title: '总运动次数', value: 0, icon: Trophy, color: '#1890ff' },
  { title: '总时长(分钟)', value: 0, icon: Timer, color: '#52c41a' },
  { title: '总距离(公里)', value: 0, icon: Position, color: '#faad14' },
  { title: '总积分', value: 0, icon: Medal, color: '#f5222d' }
])

const recentRecords = ref([])
const rankList = ref([])

const getSportTypeName = (type) => {
  const map = {
    running: '跑步',
    gym: '健身房',
    basketball: '篮球',
    football: '足球',
    badminton: '羽毛球'
  }
  return map[type] || type
}

const loadStats = async () => {
  try {
    const res = await getStats()
    stats.value[0].value = res.data.totalRecords
    stats.value[1].value = res.data.totalDuration
    stats.value[2].value = res.data.totalDistance
    stats.value[3].value = res.data.totalSteps
  } catch (error) {
    console.error(error)
  }
}

const loadRecentRecords = async () => {
  try {
    const res = await getRecords({ page: 1, size: 5 })
    recentRecords.value = res.data.records || []
  } catch (error) {
    console.error(error)
  }
}

const loadRank = async () => {
  try {
    const res = await getPointsRank({ limit: 10 })
    rankList.value = res.data || []
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadStats()
  loadRecentRecords()
  loadRank()
})
</script>

<style scoped>
.home {
  padding: 20px;
}

.stat-card {
  cursor: pointer;
  transition: transform 0.3s;
}

.stat-card:hover {
  transform: translateY(-5px);
}

.stat-content {
  display: flex;
  align-items: center;
  gap: 20px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #333;
}

.stat-title {
  color: #999;
  font-size: 14px;
  margin-top: 5px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.record-list, .rank-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.record-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 15px;
  background-color: #f5f5f5;
  border-radius: 8px;
}

.record-info {
  display: flex;
  gap: 20px;
  font-size: 14px;
  color: #666;
}

.record-date {
  color: #999;
  font-size: 12px;
}

.rank-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 10px;
  border-bottom: 1px solid #f0f0f0;
}

.rank-number {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
}

.rank-number.top-three {
  background: linear-gradient(135deg, #ffd700, #ffed4e);
  color: white;
}

.rank-name {
  flex: 1;
  font-size: 14px;
}

.rank-points {
  color: #1890ff;
  font-weight: bold;
}

.quick-actions {
  display: flex;
  gap: 15px;
}
</style>

