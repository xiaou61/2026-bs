<template>
  <div class="dashboard">
    <!-- 欢迎区域 -->
    <div class="welcome-section">
      <div class="welcome-content">
        <h1 class="welcome-title">欢迎回来，{{ userInfo?.username }}！</h1>
        <p class="welcome-subtitle">今天是 {{ currentDate }}，祝您工作愉快</p>
      </div>
      <div class="welcome-decoration">
        <div class="decoration-circle circle-1"></div>
        <div class="decoration-circle circle-2"></div>
      </div>
    </div>

    <!-- 数据统计卡片 -->
    <el-row :gutter="24" class="stats-row">
      <el-col :xs="12" :sm="6" v-for="(item, index) in statsData" :key="item.title">
        <el-card class="stat-card" :class="`stat-card-${index}`" shadow="hover">
          <div class="stat-content">
            <div class="stat-left">
              <div class="stat-title">{{ item.title }}</div>
              <div class="stat-value">{{ item.value }}</div>
              <div class="stat-trend">
                <el-icon :size="14"><Top /></el-icon>
                <span>较上周增长 12%</span>
              </div>
            </div>
            <div class="stat-icon" :style="{ background: item.color }">
              <el-icon :size="28"><component :is="item.icon" /></el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="24" class="content-row">
      <!-- 最新公告 -->
      <el-col :xs="24" :lg="12">
        <el-card class="content-card">
          <template #header>
            <div class="card-header">
              <div class="card-title">
                <el-icon><Bell /></el-icon>
                <span>最新公告</span>
              </div>
              <el-button type="primary" link @click="toNotice" class="view-more">
                查看更多 <el-icon><ArrowRight /></el-icon>
              </el-button>
            </div>
          </template>
          <div class="timeline-container">
            <el-timeline v-if="noticeList.length">
              <el-timeline-item
                v-for="notice in noticeList"
                :key="notice.id"
                :timestamp="notice.createTime"
                placement="top"
              >
                <el-card class="timeline-card" shadow="hover">
                  <el-link @click="toNoticeDetail(notice.id)" class="timeline-link">
                    {{ notice.title }}
                  </el-link>
                </el-card>
              </el-timeline-item>
            </el-timeline>
            <el-empty v-else description="暂无公告" :image-size="100" />
          </div>
        </el-card>
      </el-col>

      <!-- 最新活动 -->
      <el-col :xs="24" :lg="12">
        <el-card class="content-card">
          <template #header>
            <div class="card-header">
              <div class="card-title">
                <el-icon><Calendar /></el-icon>
                <span>最新活动</span>
              </div>
              <el-button type="primary" link @click="toActivity" class="view-more">
                查看更多 <el-icon><ArrowRight /></el-icon>
              </el-button>
            </div>
          </template>
          <div class="timeline-container">
            <el-timeline v-if="activityList.length">
              <el-timeline-item
                v-for="activity in activityList"
                :key="activity.id"
                :timestamp="activity.createTime"
                placement="top"
              >
                <el-card class="timeline-card" shadow="hover">
                  <el-link @click="toActivityDetail(activity.id)" class="timeline-link">
                    {{ activity.title }}
                  </el-link>
                </el-card>
              </el-timeline-item>
            </el-timeline>
            <el-empty v-else description="暂无活动" :image-size="100" />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 个人事务 -->
    <el-row :gutter="24" class="content-row">
      <el-col :span="24">
        <el-card class="content-card">
          <template #header>
            <div class="card-header">
              <div class="card-title">
                <el-icon><Document /></el-icon>
                <span>我的事务</span>
              </div>
            </div>
          </template>
          <el-tabs v-model="activeTab" class="custom-tabs">
            <el-tab-pane label="我的请假" name="leave">
              <el-table :data="leaveList" style="width: 100%" class="custom-table">
                <el-table-column prop="reason" label="请假理由" min-width="200" />
                <el-table-column prop="startTime" label="开始时间" width="180" />
                <el-table-column prop="endTime" label="结束时间" width="180" />
                <el-table-column prop="status" label="状态" width="120">
                  <template #default="{ row }">
                    <el-tag v-if="row.status === 0" type="warning" class="status-tag">待审批</el-tag>
                    <el-tag v-else-if="row.status === 1" type="success" class="status-tag">已通过</el-tag>
                    <el-tag v-else type="danger" class="status-tag">已驳回</el-tag>
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="我的报修" name="repair">
              <el-table :data="repairList" style="width: 100%" class="custom-table">
                <el-table-column prop="type" label="报修类型" width="120" />
                <el-table-column prop="location" label="位置" width="150" />
                <el-table-column prop="description" label="描述" min-width="200" />
                <el-table-column prop="status" label="状态" width="120">
                  <template #default="{ row }">
                    <el-tag v-if="row.status === 0" type="warning" class="status-tag">未处理</el-tag>
                    <el-tag v-else-if="row.status === 1" type="primary" class="status-tag">处理中</el-tag>
                    <el-tag v-else type="success" class="status-tag">已完成</el-tag>
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getNoticeList } from '@/api/notice'
import { getActivityList } from '@/api/activity'
import { getLeaveList } from '@/api/leave'
import { getRepairList } from '@/api/repair'

const router = useRouter()
const userStore = useUserStore()

const activeTab = ref('leave')
const userInfo = computed(() => userStore.userInfo)

// 获取当前日期
const currentDate = computed(() => {
  const now = new Date()
  const year = now.getFullYear()
  const month = now.getMonth() + 1
  const day = now.getDate()
  const weekDays = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']
  const weekDay = weekDays[now.getDay()]
  return `${year}年${month}月${day}日 ${weekDay}`
})

const statsData = ref([
  { title: '我的请假', value: 0, icon: 'Calendar', color: 'linear-gradient(135deg, #2E7D32 0%, #4CAF50 100%)' },
  { title: '我的报修', value: 0, icon: 'Tools', color: 'linear-gradient(135deg, #1565C0 0%, #42A5F5 100%)' },
  { title: '我的活动', value: 0, icon: 'Basketball', color: 'linear-gradient(135deg, #FF6F00 0%, #FFA726 100%)' },
  { title: '最新公告', value: 0, icon: 'Bell', color: 'linear-gradient(135deg, #7B1FA2 0%, #AB47BC 100%)' }
])

const noticeList = ref([])
const activityList = ref([])
const leaveList = ref([])
const repairList = ref([])

const fetchDashboardData = async () => {
  try {
    // 获取最新公告
    const noticeRes = await getNoticeList({ current: 1, size: 5 })
    if (noticeRes.code === 200) {
      noticeList.value = noticeRes.data.records || []
      statsData.value[3].value = noticeRes.data.total || 0
    }

    // 获取最新活动
    const activityRes = await getActivityList({ current: 1, size: 5 })
    if (activityRes.code === 200) {
      activityList.value = activityRes.data.records || []
      statsData.value[2].value = activityRes.data.total || 0
    }

    // 获取我的请假
    const leaveRes = await getLeaveList({ current: 1, size: 5 })
    if (leaveRes.code === 200) {
      leaveList.value = leaveRes.data.records || []
      statsData.value[0].value = leaveRes.data.total || 0
    }

    // 获取我的报修
    const repairRes = await getRepairList({ current: 1, size: 5 })
    if (repairRes.code === 200) {
      repairList.value = repairRes.data.records || []
      statsData.value[1].value = repairRes.data.total || 0
    }
  } catch (error) {
    console.error('获取数据失败:', error)
  }
}

const toNotice = () => {
  router.push('/notice')
}

const toNoticeDetail = (id) => {
  router.push(`/notice/${id}`)
}

const toActivity = () => {
  router.push('/activity')
}

const toActivityDetail = (id) => {
  router.push(`/activity/${id}`)
}

onMounted(() => {
  fetchDashboardData()
})
</script>

<style scoped>
.dashboard {
  padding: 0;
}

/* 欢迎区域 */
.welcome-section {
  background: linear-gradient(135deg, #2E7D32 0%, #1565C0 100%);
  border-radius: 16px;
  padding: 32px;
  margin-bottom: 24px;
  position: relative;
  overflow: hidden;
  color: white;
}

.welcome-content {
  position: relative;
  z-index: 2;
}

.welcome-title {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 8px;
  font-family: var(--font-heading);
}

.welcome-subtitle {
  font-size: 14px;
  opacity: 0.9;
}

.welcome-decoration {
  position: absolute;
  top: 0;
  right: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

.decoration-circle {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
}

.circle-1 {
  width: 200px;
  height: 200px;
  top: -50px;
  right: -50px;
}

.circle-2 {
  width: 150px;
  height: 150px;
  bottom: -30px;
  right: 100px;
}

/* 统计卡片 */
.stats-row {
  margin-bottom: 24px;
}

.stat-card {
  border-radius: 16px !important;
  border: none !important;
  transition: all 0.3s ease !important;
  position: relative;
  overflow: hidden;
}

.stat-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 4px;
  background: linear-gradient(90deg, #2E7D32, #1565C0);
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15) !important;
}

.stat-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.stat-left {
  flex: 1;
}

.stat-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 12px;
}

.stat-value {
  font-size: 32px;
  font-weight: 700;
  color: #2C3E50;
  margin-bottom: 8px;
}

.stat-trend {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: #67C23A;
}

.stat-icon {
  width: 64px;
  height: 64px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

/* 内容卡片 */
.content-row {
  margin-bottom: 24px;
}

.content-card {
  border-radius: 16px !important;
  border: none !important;
}

.content-card :deep(.el-card__header) {
  border-bottom: 1px solid #f0f0f0;
  padding: 20px 24px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
  font-weight: 600;
  color: #2C3E50;
}

.card-title .el-icon {
  color: #2E7D32;
}

.view-more {
  font-size: 13px;
  display: flex;
  align-items: center;
  gap: 4px;
}

/* 时间线容器 */
.timeline-container {
  max-height: 400px;
  overflow-y: auto;
  padding: 8px 0;
}

.timeline-card {
  border-radius: 8px !important;
  margin-bottom: 0;
}

.timeline-link {
  font-size: 14px;
  color: #2C3E50 !important;
  transition: all 0.3s ease;
}

.timeline-link:hover {
  color: #2E7D32 !important;
  transform: translateX(4px);
}

/* 自定义标签页 */
.custom-tabs :deep(.el-tabs__header) {
  margin-bottom: 20px;
}

.custom-tabs :deep(.el-tabs__nav-wrap::after) {
  height: 1px;
  background: #f0f0f0;
}

.custom-tabs :deep(.el-tabs__active-bar) {
  background: linear-gradient(90deg, #2E7D32, #1565C0);
  height: 3px;
  border-radius: 2px;
}

.custom-tabs :deep(.el-tabs__item) {
  font-weight: 500;
  color: #606266;
}

.custom-tabs :deep(.el-tabs__item.is-active) {
  color: #2E7D32;
  font-weight: 600;
}

/* 自定义表格 */
.custom-table {
  border-radius: 12px !important;
  overflow: hidden;
}

.custom-table :deep(.el-table__header) {
  background: #f8f9fa;
}

.custom-table :deep(.el-table__header th) {
  color: #2C3E50;
  font-weight: 600;
  border-bottom: 2px solid #e9ecef;
}

.custom-table :deep(.el-table__row) {
  transition: all 0.3s ease;
}

.custom-table :deep(.el-table__row:hover) {
  background: #f8f9fa;
}

.status-tag {
  border-radius: 20px !important;
  padding: 0 12px !important;
}

/* 响应式 */
@media (max-width: 768px) {
  .welcome-section {
    padding: 24px;
  }
  
  .welcome-title {
    font-size: 22px;
  }
  
  .stat-value {
    font-size: 24px;
  }
  
  .stat-icon {
    width: 48px;
    height: 48px;
  }
}
</style>

