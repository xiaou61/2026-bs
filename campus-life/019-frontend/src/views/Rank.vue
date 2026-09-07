<template>
  <div class="rank">
    <el-card>
      <template #header>
        <div class="header">
          <span>积分排行榜</span>
          <el-radio-group v-model="rankType">
            <el-radio-button label="day">日榜</el-radio-button>
            <el-radio-button label="week">周榜</el-radio-button>
            <el-radio-button label="month">月榜</el-radio-button>
          </el-radio-group>
        </div>
      </template>
      
      <div class="rank-podium">
        <div class="podium-item" v-for="(user, index) in topThree" :key="user.id" :class="`rank-${index + 1}`">
          <div class="rank-badge">
            <el-icon v-if="index === 0"><Medal /></el-icon>
            <el-icon v-else-if="index === 1"><Trophy /></el-icon>
            <el-icon v-else><Star /></el-icon>
            <span class="rank-num">{{ index + 1 }}</span>
          </div>
          <el-avatar :size="80" :src="user.avatar">
            {{ user.nickname?.[0] }}
          </el-avatar>
          <div class="user-name">{{ user.nickname }}</div>
          <div class="user-points">{{ user.points }}积分</div>
        </div>
      </div>
      
      <el-table :data="otherUsers" style="width: 100%; margin-top: 30px;">
        <el-table-column label="排名" width="100">
          <template #default="{ $index }">
            <div class="rank-number">{{ $index + 4 }}</div>
          </template>
        </el-table-column>
        
        <el-table-column label="用户" width="200">
          <template #default="{ row }">
            <div class="user-cell">
              <el-avatar :size="35" :src="row.avatar">
                {{ row.nickname?.[0] }}
              </el-avatar>
              <span>{{ row.nickname }}</span>
            </div>
          </template>
        </el-table-column>
        
        <el-table-column prop="points" label="积分" width="150">
          <template #default="{ row }">
            <el-tag type="success" size="large">{{ row.points }}</el-tag>
          </template>
        </el-table-column>
        
        <el-table-column prop="role" label="角色" width="120">
          <template #default="{ row }">
            <el-tag :type="getRoleType(row.role)">
              {{ getRoleName(row.role) }}
            </el-tag>
          </template>
        </el-table-column>
        
        <el-table-column label="趋势" width="100">
          <template>
            <el-icon color="#52c41a" :size="20"><TrendCharts /></el-icon>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { getPointsRank } from '@/api/rank'

const rankType = ref('day')
const rankList = ref([])

const topThree = computed(() => rankList.value.slice(0, 3))
const otherUsers = computed(() => rankList.value.slice(3))

const getRoleName = (role) => {
  const map = {
    admin: '管理员',
    coach: '教练',
    student: '学生'
  }
  return map[role] || role
}

const getRoleType = (role) => {
  const map = {
    admin: 'danger',
    coach: 'warning',
    student: ''
  }
  return map[role] || ''
}

const loadRank = async () => {
  try {
    const res = await getPointsRank({ limit: 50 })
    rankList.value = res.data || []
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadRank()
})
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.rank-podium {
  display: flex;
  justify-content: center;
  align-items: flex-end;
  gap: 40px;
  padding: 40px 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 10px;
}

.podium-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
  padding: 30px 20px;
  background: white;
  border-radius: 10px;
  position: relative;
}

.rank-1 {
  order: 2;
  transform: translateY(-20px);
}

.rank-2 {
  order: 1;
}

.rank-3 {
  order: 3;
}

.rank-badge {
  position: absolute;
  top: -15px;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: bold;
}

.rank-1 .rank-badge {
  background: linear-gradient(135deg, #ffd700, #ffed4e);
}

.rank-2 .rank-badge {
  background: linear-gradient(135deg, #c0c0c0, #e8e8e8);
}

.rank-3 .rank-badge {
  background: linear-gradient(135deg, #cd7f32, #dda15e);
}

.user-name {
  font-size: 16px;
  font-weight: bold;
  color: #333;
}

.user-points {
  font-size: 20px;
  font-weight: bold;
  color: #1890ff;
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
  margin: 0 auto;
}

.user-cell {
  display: flex;
  align-items: center;
  gap: 10px;
}
</style>

