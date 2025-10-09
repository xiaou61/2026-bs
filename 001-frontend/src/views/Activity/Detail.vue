<template>
  <div class="activity-detail-container">
    <el-card v-loading="loading">
      <template #header>
        <div class="card-header">
          <el-button @click="goBack">
            <el-icon><ArrowLeft /></el-icon>
            返回
          </el-button>
        </div>
      </template>

      <div v-if="activity" class="activity-content">
        <h1 class="activity-title">{{ activity.title }}</h1>
        
        <div class="activity-meta">
          <el-tag :type="getStatusType(activity.status)">{{ activity.status }}</el-tag>
          <span class="meta-item">
            <el-icon><Location /></el-icon>
            {{ activity.location }}
          </span>
          <span class="meta-item">
            <el-icon><User /></el-icon>
            {{ activity.organizerName }}
          </span>
          <span class="meta-item">
            <el-icon><UserFilled /></el-icon>
            {{ activity.currentParticipants || 0 }} / {{ activity.maxParticipants }}
          </span>
        </div>

        <el-divider />

        <div class="activity-info">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="活动地点">
              {{ activity.location }}
            </el-descriptions-item>
            <el-descriptions-item label="活动状态">
              <el-tag :type="getStatusType(activity.status)">{{ activity.status }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="开始时间">
              {{ activity.startTime }}
            </el-descriptions-item>
            <el-descriptions-item label="结束时间">
              {{ activity.endTime }}
            </el-descriptions-item>
            <el-descriptions-item label="限制人数">
              {{ activity.maxParticipants }}
            </el-descriptions-item>
            <el-descriptions-item label="已报名人数">
              <el-text type="primary">{{ activity.currentParticipants || 0 }}</el-text>
            </el-descriptions-item>
          </el-descriptions>
        </div>

        <div class="activity-description">
          <h3>活动描述</h3>
          <p>{{ activity.description }}</p>
        </div>

        <div class="activity-actions" v-if="userInfo?.role === 'student'">
          <el-button
            v-if="!activity.isSignedUp"
            type="primary"
            size="large"
            :disabled="activity.currentParticipants >= activity.maxParticipants || activity.status === '已结束'"
            @click="handleSignup"
            :loading="submitting"
          >
            <el-icon><Check /></el-icon>
            报名参加
          </el-button>
          <el-button
            v-else
            type="danger"
            size="large"
            @click="handleCancelSignup"
            :loading="submitting"
          >
            <el-icon><Close /></el-icon>
            取消报名
          </el-button>
        </div>

        <!-- 报名列表（教师和管理员可查看） -->
        <div v-if="userInfo?.role !== 'student'" class="signup-list">
          <el-divider />
          <h3>报名列表</h3>
          <el-table :data="signupList" border style="margin-top: 10px;">
            <el-table-column prop="studentName" label="学生姓名" />
            <el-table-column prop="phone" label="联系电话" />
            <el-table-column prop="email" label="邮箱" />
            <el-table-column prop="signupTime" label="报名时间" />
          </el-table>
        </div>
      </div>

      <el-empty v-else description="活动不存在" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getActivityDetail, signupActivity, cancelSignup, getActivitySignups } from '@/api/activity'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const userInfo = computed(() => userStore.userInfo)

const loading = ref(false)
const submitting = ref(false)
const activity = ref(null)
const signupList = ref([])

// 获取活动详情
const fetchActivityDetail = async () => {
  const id = route.params.id
  if (!id) return
  
  loading.value = true
  try {
    const res = await getActivityDetail(id)
    if (res.code === 200) {
      activity.value = res.data
      
      // 如果是教师或管理员，获取报名列表
      if (userInfo.value?.role !== 'student') {
        fetchSignupList(id)
      }
    }
  } catch (error) {
    console.error('获取活动详情失败:', error)
  } finally {
    loading.value = false
  }
}

// 获取报名列表
const fetchSignupList = async (activityId) => {
  try {
    const res = await getActivitySignups(activityId)
    if (res.code === 200) {
      signupList.value = res.data || []
    }
  } catch (error) {
    console.error('获取报名列表失败:', error)
  }
}

// 获取状态类型
const getStatusType = (status) => {
  if (status === '未开始') return 'info'
  if (status === '进行中') return 'success'
  return 'warning'
}

// 报名活动
const handleSignup = async () => {
  ElMessageBox.confirm('确定报名参加该活动吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'info'
  }).then(async () => {
    submitting.value = true
    try {
      const res = await signupActivity({ activityId: activity.value.id })
      if (res.code === 200) {
        ElMessage.success('报名成功')
        fetchActivityDetail()
      }
    } catch (error) {
      console.error('报名失败:', error)
    } finally {
      submitting.value = false
    }
  }).catch(() => {})
}

// 取消报名
const handleCancelSignup = async () => {
  ElMessageBox.confirm('确定取消报名吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    submitting.value = true
    try {
      const res = await cancelSignup({ activityId: activity.value.id })
      if (res.code === 200) {
        ElMessage.success('取消报名成功')
        fetchActivityDetail()
      }
    } catch (error) {
      console.error('取消报名失败:', error)
    } finally {
      submitting.value = false
    }
  }).catch(() => {})
}

// 返回
const goBack = () => {
  router.back()
}

onMounted(() => {
  fetchActivityDetail()
})
</script>

<style scoped>
.activity-detail-container {
  padding: 0;
}

.card-header {
  display: flex;
  align-items: center;
}

.activity-content {
  padding: 20px;
}

.activity-title {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 20px;
  text-align: center;
}

.activity-meta {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
  color: #909399;
  font-size: 14px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 4px;
}

.activity-info {
  margin: 20px 0;
}

.activity-description {
  margin: 30px 0;
}

.activity-description h3 {
  font-size: 18px;
  margin-bottom: 15px;
  color: #303133;
}

.activity-description p {
  font-size: 16px;
  line-height: 1.8;
  color: #606266;
  text-align: justify;
}

.activity-actions {
  text-align: center;
  margin: 30px 0;
}

.signup-list h3 {
  font-size: 18px;
  margin-bottom: 15px;
  color: #303133;
}
</style>

