<template>
  <div class="activity-list">
    <el-card>
      <template #header>
        <div class="header">
          <span>约球活动</span>
          <div class="header-right">
            <el-select v-model="sportType" placeholder="运动类型" clearable @change="loadActivities" style="width: 150px; margin-right: 10px;">
              <el-option label="篮球" value="basketball" />
              <el-option label="足球" value="football" />
              <el-option label="羽毛球" value="badminton" />
              <el-option label="网球" value="tennis" />
            </el-select>
            <el-button type="primary" icon="Plus" @click="$router.push('/activity/create')">
              发起活动
            </el-button>
          </div>
        </div>
      </template>
      
      <el-row :gutter="20">
        <el-col :span="8" v-for="activity in activities" :key="activity.id">
          <el-card class="activity-card">
            <div class="activity-header">
              <h3>{{ activity.activityName }}</h3>
              <el-tag :type="getStatusType(activity.status)">
                {{ getStatusName(activity.status) }}
              </el-tag>
            </div>
            
            <div class="activity-info">
              <div class="info-item">
                <el-icon><Trophy /></el-icon>
                <span>{{ getSportTypeName(activity.sportType) }}</span>
              </div>
              <div class="info-item">
                <el-icon><Clock /></el-icon>
                <span>{{ activity.activityTime }}</span>
              </div>
              <div class="info-item">
                <el-icon><User /></el-icon>
                <span>{{ activity.currentParticipants }} / {{ activity.maxParticipants }} 人</span>
              </div>
              <div class="info-item">
                <el-icon><Medal /></el-icon>
                <span>{{ getLevelName(activity.levelRequirement) }}</span>
              </div>
            </div>
            
            <div class="activity-desc">
              {{ activity.description }}
            </div>
            
            <div class="activity-actions">
              <el-button
                size="small"
                type="primary"
                @click="handleJoin(activity.id)"
                :disabled="activity.status !== 'recruiting'"
              >
                参加活动
              </el-button>
              <el-button
                size="small"
                type="warning"
                @click="handleCancel(activity.id)"
              >
                取消参加
              </el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
      
      <el-empty v-if="!activities.length" description="暂无活动" />
      
      <div class="pagination" v-if="total > 0">
        <el-pagination
          v-model:current-page="page"
          v-model:page-size="size"
          :total="total"
          layout="total, prev, pager, next"
          @current-change="loadActivities"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getActivityList, joinActivity, cancelActivity } from '@/api/activity'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const activities = ref([])
const page = ref(1)
const size = ref(9)
const total = ref(0)
const sportType = ref('')

const getSportTypeName = (type) => {
  const map = {
    basketball: '篮球',
    football: '足球',
    badminton: '羽毛球',
    tennis: '网球',
    pingpong: '乒乓球'
  }
  return map[type] || type
}

const getStatusName = (status) => {
  const map = {
    recruiting: '招募中',
    full: '已满员',
    ongoing: '进行中',
    completed: '已结束',
    cancelled: '已取消'
  }
  return map[status] || status
}

const getStatusType = (status) => {
  const map = {
    recruiting: 'success',
    full: 'warning',
    ongoing: '',
    completed: 'info',
    cancelled: 'danger'
  }
  return map[status] || ''
}

const getLevelName = (level) => {
  const map = {
    beginner: '新手',
    intermediate: '中级',
    advanced: '高级'
  }
  return map[level] || level
}

const loadActivities = async () => {
  loading.value = true
  try {
    const params = { page: page.value, size: size.value }
    if (sportType.value) {
      params.sportType = sportType.value
    }
    const res = await getActivityList(params)
    activities.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handleJoin = async (id) => {
  try {
    await joinActivity(id)
    ElMessage.success('参加成功')
    loadActivities()
  } catch (error) {
    console.error(error)
  }
}

const handleCancel = async (id) => {
  try {
    await cancelActivity(id)
    ElMessage.success('取消成功')
    loadActivities()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadActivities()
})
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-right {
  display: flex;
  align-items: center;
}

.activity-card {
  margin-bottom: 20px;
}

.activity-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.activity-header h3 {
  margin: 0;
  font-size: 18px;
}

.activity-info {
  margin-bottom: 15px;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
  font-size: 14px;
  color: #666;
}

.activity-desc {
  margin-bottom: 15px;
  font-size: 14px;
  color: #999;
  line-height: 1.5;
}

.activity-actions {
  display: flex;
  gap: 10px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>

