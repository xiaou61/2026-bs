<template>
  <div class="activity-detail-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <div class="page-title">
        <el-icon><Calendar /></el-icon>
        <span>活动详情</span>
      </div>
      <el-button @click="goBack" class="back-btn">
        <el-icon><ArrowLeft /></el-icon>
        返回列表
      </el-button>
    </div>

    <el-card class="detail-card" v-loading="loading">
      <div v-if="activity" class="activity-content">
        <!-- 标题区域 -->
        <div class="activity-header">
          <h1 class="activity-title">{{ activity.title }}</h1>
          <div class="activity-meta">
            <el-tag :type="getStatusType(activity.status)" class="status-tag">
              {{ activity.status }}
            </el-tag>
            <div class="meta-list">
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
          </div>
        </div>

        <el-divider class="custom-divider" />

        <!-- 活动信息 -->
        <div class="activity-info">
          <el-descriptions :column="2" border class="custom-descriptions">
            <el-descriptions-item label="活动地点">
              <el-icon><Location /></el-icon>
              {{ activity.location }}
            </el-descriptions-item>
            <el-descriptions-item label="活动状态">
              <el-tag :type="getStatusType(activity.status)" class="status-tag">
                {{ activity.status }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="开始时间">
              <el-icon><Calendar /></el-icon>
              {{ activity.startTime }}
            </el-descriptions-item>
            <el-descriptions-item label="结束时间">
              <el-icon><Calendar /></el-icon>
              {{ activity.endTime }}
            </el-descriptions-item>
            <el-descriptions-item label="限制人数">
              <el-icon><User /></el-icon>
              {{ activity.maxParticipants }}
            </el-descriptions-item>
            <el-descriptions-item label="已报名人数">
              <el-icon><UserFilled /></el-icon>
              <el-text type="primary" class="participant-count">{{ activity.currentParticipants || 0 }}</el-text>
            </el-descriptions-item>
          </el-descriptions>
        </div>

        <!-- 活动描述 -->
        <div class="activity-description">
          <h3 class="section-title">
            <el-icon><Document /></el-icon>
            活动描述
          </h3>
          <div class="description-content">
            <p>{{ activity.description }}</p>
          </div>
        </div>

        <!-- 报名操作 -->
        <div class="activity-actions" v-if="userInfo?.role === 'student'">
          <el-button
            v-if="!activity.isSignedUp"
            type="primary"
            size="large"
            :disabled="activity.currentParticipants >= activity.maxParticipants || activity.status === '已结束'"
            @click="handleSignup"
            :loading="submitting"
            class="signup-btn"
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
            class="cancel-btn"
          >
            <el-icon><Close /></el-icon>
            取消报名
          </el-button>
        </div>

        <!-- 报名列表（教师和管理员可查看） -->
        <div v-if="userInfo?.role !== 'student'" class="signup-list">
          <el-divider class="custom-divider" />
          <h3 class="section-title">
            <el-icon><List /></el-icon>
            报名列表
          </h3>
          <el-table :data="signupList" class="custom-table" stripe>
            <el-table-column prop="studentName" label="学生姓名" width="120" align="center" />
            <el-table-column prop="phone" label="联系电话" width="150" align="center" />
            <el-table-column prop="email" label="邮箱" min-width="200" align="center" />
            <el-table-column prop="signupTime" label="报名时间" width="180" align="center" />
          </el-table>
        </div>
      </div>

      <el-empty v-else description="活动不存在" :image-size="200" />
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

/* 页面标题 */
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  padding: 0 4px;
}

.page-title {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 24px;
  font-weight: 700;
  color: #2C3E50;
  font-family: var(--font-heading);
}

.page-title .el-icon {
  color: #FF6F00;
  font-size: 28px;
}

.back-btn {
  height: 44px;
  padding: 0 24px;
  font-size: 14px;
  font-weight: 600;
  border-radius: 10px;
  background: linear-gradient(135deg, #FF6F00 0%, #FFA726 100%) !important;
  border: none !important;
  box-shadow: 0 4px 12px rgba(255, 111, 0, 0.3);
  transition: all 0.3s ease;
  color: white !important;
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(255, 111, 0, 0.4);
}

/* 详情卡片 */
.detail-card {
  border-radius: 16px !important;
  border: none !important;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08) !important;
}

.detail-card :deep(.el-card__body) {
  padding: 32px;
}

/* 活动内容 */
.activity-content {
  max-width: 800px;
  margin: 0 auto;
}

/* 标题区域 */
.activity-header {
  text-align: center;
  margin-bottom: 32px;
}

.activity-title {
  font-size: 32px;
  font-weight: 700;
  color: #2C3E50;
  margin-bottom: 20px;
  line-height: 1.4;
  font-family: var(--font-heading);
}

.activity-meta {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
}

.status-tag {
  border-radius: 20px !important;
  padding: 0 20px !important;
  font-weight: 600;
  font-size: 14px;
}

.meta-list {
  display: flex;
  align-items: center;
  gap: 24px;
  color: #909399;
  font-size: 14px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 6px;
}

.meta-item .el-icon {
  color: #FF6F00;
  font-size: 16px;
}

/* 分割线 */
.custom-divider {
  margin: 32px 0;
  border-color: #f0f0f0;
}

/* 活动信息 */
.activity-info {
  margin-bottom: 32px;
}

.custom-descriptions {
  border-radius: 12px !important;
  overflow: hidden;
}

.custom-descriptions :deep(.el-descriptions__label) {
  font-weight: 500;
  color: #606266;
  background: #f8f9fa;
}

.custom-descriptions :deep(.el-descriptions__content) {
  color: #2C3E50;
}

.custom-descriptions :deep(.el-descriptions-item) {
  padding: 16px;
}

.custom-descriptions :deep(.el-descriptions-item .el-icon) {
  color: #FF6F00;
  margin-right: 8px;
}

.participant-count {
  font-weight: 600;
  font-size: 16px;
}

/* 活动描述 */
.activity-description {
  margin-bottom: 32px;
}

.section-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 18px;
  font-weight: 600;
  color: #2C3E50;
  margin-bottom: 16px;
}

.section-title .el-icon {
  color: #FF6F00;
}

.description-content {
  background: #f8f9fa;
  border-radius: 12px;
  padding: 24px;
}

.description-content p {
  font-size: 16px;
  line-height: 1.8;
  color: #606266;
  text-align: justify;
  margin: 0;
}

/* 报名操作 */
.activity-actions {
  text-align: center;
  margin: 32px 0;
}

.signup-btn {
  height: 48px;
  padding: 0 32px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 12px;
  background: linear-gradient(135deg, #FF6F00 0%, #FFA726 100%) !important;
  border: none !important;
  box-shadow: 0 8px 20px rgba(255, 111, 0, 0.3);
  transition: all 0.3s ease;
}

.signup-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 25px rgba(255, 111, 0, 0.4);
}

.signup-btn:disabled {
  background: #C0C4CC !important;
  box-shadow: none;
}

.cancel-btn {
  height: 48px;
  padding: 0 32px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 12px;
  background: linear-gradient(135deg, #F56C6C 0%, #f78989 100%) !important;
  border: none !important;
  box-shadow: 0 8px 20px rgba(245, 108, 108, 0.3);
  transition: all 0.3s ease;
}

.cancel-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 25px rgba(245, 108, 108, 0.4);
}

/* 报名列表 */
.signup-list {
  margin-top: 32px;
}

.signup-list h3 {
  font-size: 18px;
  margin-bottom: 16px;
  color: #2C3E50;
}

/* 自定义表格 */
.custom-table {
  border-radius: 12px !important;
  overflow: hidden;
}

.custom-table :deep(.el-table__header) {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
}

.custom-table :deep(.el-table__header th) {
  color: #2C3E50;
  font-weight: 600;
  border-bottom: 2px solid #dee2e6;
  padding: 16px 12px;
}

.custom-table :deep(.el-table__row) {
  transition: all 0.3s ease;
}

.custom-table :deep(.el-table__row:hover) {
  background: #f8f9fa;
}

.custom-table :deep(.el-table__row--striped) {
  background: #fafbfc;
}

.custom-table :deep(.el-table__row--striped:hover) {
  background: #f8f9fa;
}

/* 响应式 */
@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    gap: 16px;
    align-items: flex-start;
  }
  
  .back-btn {
    width: 100%;
  }
  
  .detail-card :deep(.el-card__body) {
    padding: 20px;
  }
  
  .activity-title {
    font-size: 24px;
  }
  
  .meta-list {
    flex-direction: column;
    gap: 8px;
  }
  
  .signup-btn,
  .cancel-btn {
    width: 100%;
  }
}
</style>

