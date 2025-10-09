<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <!-- 数据统计卡片 -->
      <el-col :span="6" v-for="item in statsData" :key="item.title">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-left">
              <div class="stat-title">{{ item.title }}</div>
              <div class="stat-value">{{ item.value }}</div>
            </div>
            <div class="stat-icon" :style="{ background: item.color }">
              <el-icon :size="30"><component :is="item.icon" /></el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <!-- 最新公告 -->
      <el-col :span="12">
        <el-card class="content-card">
          <template #header>
            <div class="card-header">
              <span>最新公告</span>
              <el-button type="primary" link @click="toNotice">查看更多</el-button>
            </div>
          </template>
          <el-timeline v-if="noticeList.length">
            <el-timeline-item
              v-for="notice in noticeList"
              :key="notice.id"
              :timestamp="notice.createTime"
            >
              <el-link @click="toNoticeDetail(notice.id)">{{ notice.title }}</el-link>
            </el-timeline-item>
          </el-timeline>
          <el-empty v-else description="暂无公告" :image-size="100" />
        </el-card>
      </el-col>

      <!-- 最新活动 -->
      <el-col :span="12">
        <el-card class="content-card">
          <template #header>
            <div class="card-header">
              <span>最新活动</span>
              <el-button type="primary" link @click="toActivity">查看更多</el-button>
            </div>
          </template>
          <el-timeline v-if="activityList.length">
            <el-timeline-item
              v-for="activity in activityList"
              :key="activity.id"
              :timestamp="activity.createTime"
            >
              <el-link @click="toActivityDetail(activity.id)">{{ activity.title }}</el-link>
            </el-timeline-item>
          </el-timeline>
          <el-empty v-else description="暂无活动" :image-size="100" />
        </el-card>
      </el-col>
    </el-row>

    <!-- 个人事务 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card class="content-card">
          <template #header>
            <div class="card-header">
              <span>我的事务</span>
            </div>
          </template>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="我的请假" name="leave">
              <el-table :data="leaveList" style="width: 100%">
                <el-table-column prop="reason" label="请假理由" />
                <el-table-column prop="startTime" label="开始时间" width="180" />
                <el-table-column prop="endTime" label="结束时间" width="180" />
                <el-table-column prop="status" label="状态" width="100">
                  <template #default="{ row }">
                    <el-tag v-if="row.status === 0" type="warning">待审批</el-tag>
                    <el-tag v-else-if="row.status === 1" type="success">已通过</el-tag>
                    <el-tag v-else type="danger">已驳回</el-tag>
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="我的报修" name="repair">
              <el-table :data="repairList" style="width: 100%">
                <el-table-column prop="type" label="报修类型" width="120" />
                <el-table-column prop="location" label="位置" width="150" />
                <el-table-column prop="description" label="描述" />
                <el-table-column prop="status" label="状态" width="100">
                  <template #default="{ row }">
                    <el-tag v-if="row.status === 0" type="warning">未处理</el-tag>
                    <el-tag v-else-if="row.status === 1" type="primary">处理中</el-tag>
                    <el-tag v-else type="success">已完成</el-tag>
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
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getNoticeList } from '@/api/notice'
import { getActivityList } from '@/api/activity'
import { getLeaveList } from '@/api/leave'
import { getRepairList } from '@/api/repair'

const router = useRouter()
const userStore = useUserStore()

const activeTab = ref('leave')
const statsData = ref([
  { title: '我的请假', value: 0, icon: 'Calendar', color: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)' },
  { title: '我的报修', value: 0, icon: 'Tools', color: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)' },
  { title: '我的活动', value: 0, icon: 'Basketball', color: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)' },
  { title: '最新公告', value: 0, icon: 'Bell', color: 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)' }
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

.stat-card {
  margin-bottom: 20px;
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
  margin-bottom: 10px;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.content-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>

