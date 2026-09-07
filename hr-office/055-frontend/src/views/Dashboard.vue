<template>
  <div class="dashboard">
    <el-row :gutter="20" class="stat-row">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #409EFF;">
              <el-icon :size="30"><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.userCount }}</div>
              <div class="stat-label">员工总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #67C23A;">
              <el-icon :size="30"><OfficeBuilding /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.deptCount }}</div>
              <div class="stat-label">部门数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #E6A23C;">
              <el-icon :size="30"><Clock /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.todayAttendance }}</div>
              <div class="stat-label">今日打卡</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background: #F56C6C;">
              <el-icon :size="30"><Tickets /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.pendingLeave }}</div>
              <div class="stat-label">待审请假</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="content-row">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>打卡状态</span>
          </template>
          <div class="clock-section">
            <div class="clock-info">
              <p>当前时间: {{ currentTime }}</p>
              <p v-if="todayAttendance.clockInTime">签到时间: {{ todayAttendance.clockInTime }}</p>
              <p v-if="todayAttendance.clockOutTime">签退时间: {{ todayAttendance.clockOutTime }}</p>
            </div>
            <div class="clock-buttons">
              <el-button type="primary" :disabled="!!todayAttendance.clockInTime" @click="handleClockIn">签到</el-button>
              <el-button type="success" :disabled="!todayAttendance.clockInTime || !!todayAttendance.clockOutTime" @click="handleClockOut">签退</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>最新公告</span>
          </template>
          <div class="notice-list">
            <div v-for="item in notices" :key="item.id" class="notice-item">
              <el-tag v-if="item.isTop" type="danger" size="small">置顶</el-tag>
              <span class="notice-title">{{ item.title }}</span>
              <span class="notice-time">{{ item.publishTime }}</span>
            </div>
            <el-empty v-if="!notices.length" description="暂无公告" />
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="content-row">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>今日日程</span>
          </template>
          <div class="schedule-list">
            <div v-for="item in schedules" :key="item.id" class="schedule-item">
              <div class="schedule-time">{{ item.startTime?.substring(11, 16) }}</div>
              <div class="schedule-content">{{ item.title }}</div>
            </div>
            <el-empty v-if="!schedules.length" description="暂无日程" />
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>今日会议</span>
          </template>
          <div class="meeting-list">
            <div v-for="item in meetings" :key="item.id" class="meeting-item">
              <div class="meeting-time">{{ item.startTime?.substring(11, 16) }}-{{ item.endTime?.substring(11, 16) }}</div>
              <div class="meeting-content">{{ item.title }}</div>
            </div>
            <el-empty v-if="!meetings.length" description="暂无会议" />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getStatistics, getTodayAttendance, clockIn, clockOut, getPublishedNotices, getScheduleList, getMyMeetings } from '../api'
import dayjs from 'dayjs'

const stats = ref({ userCount: 0, deptCount: 0, todayAttendance: 0, pendingLeave: 0 })
const todayAttendance = ref({})
const notices = ref([])
const schedules = ref([])
const meetings = ref([])
const currentTime = ref('')
let timer = null

const loadData = async () => {
  try {
    const [statsRes, attendanceRes, noticeRes, scheduleRes, meetingRes] = await Promise.all([
      getStatistics(),
      getTodayAttendance(),
      getPublishedNotices({ pageNum: 1, pageSize: 5 }),
      getScheduleList({ date: dayjs().format('YYYY-MM-DD') }),
      getMyMeetings({ date: dayjs().format('YYYY-MM-DD') })
    ])
    stats.value = statsRes.data || {}
    todayAttendance.value = attendanceRes.data || {}
    notices.value = noticeRes.data?.records || []
    schedules.value = scheduleRes.data || []
    meetings.value = meetingRes.data || []
  } catch (e) {}
}

const handleClockIn = async () => {
  await clockIn()
  ElMessage.success('签到成功')
  loadData()
}

const handleClockOut = async () => {
  await clockOut()
  ElMessage.success('签退成功')
  loadData()
}

const updateTime = () => {
  currentTime.value = dayjs().format('YYYY-MM-DD HH:mm:ss')
}

onMounted(() => {
  loadData()
  updateTime()
  timer = setInterval(updateTime, 1000)
})

onUnmounted(() => {
  if (timer) clearInterval(timer)
})
</script>

<style scoped>
.dashboard {
  padding: 10px;
}
.stat-row {
  margin-bottom: 20px;
}
.stat-card {
  height: 100px;
}
.stat-content {
  display: flex;
  align-items: center;
}
.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #fff;
}
.stat-info {
  margin-left: 20px;
}
.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #333;
}
.stat-label {
  color: #999;
  margin-top: 5px;
}
.content-row {
  margin-bottom: 20px;
}
.clock-section {
  text-align: center;
}
.clock-info {
  margin-bottom: 20px;
}
.clock-info p {
  margin: 10px 0;
  color: #666;
}
.clock-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
}
.notice-list, .schedule-list, .meeting-list {
  min-height: 150px;
}
.notice-item, .schedule-item, .meeting-item {
  padding: 10px 0;
  border-bottom: 1px solid #eee;
  display: flex;
  align-items: center;
  gap: 10px;
}
.notice-title, .schedule-content, .meeting-content {
  flex: 1;
}
.notice-time {
  color: #999;
  font-size: 12px;
}
.schedule-time, .meeting-time {
  width: 100px;
  color: #409EFF;
}
</style>
