<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #409EFF"><el-icon><User /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.employee?.total || 0 }}</div>
            <div class="stat-label">员工总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #67C23A"><el-icon><Check /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.attendance?.total || 0 }}</div>
            <div class="stat-label">今日打卡</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #E6A23C"><el-icon><Document /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.pendingLeave || 0 }}</div>
            <div class="stat-label">待审批请假</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #F56C6C"><el-icon><Briefcase /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.openRecruitment || 0 }}</div>
            <div class="stat-label">招聘中职位</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="16">
        <el-card>
          <template #header>员工状态分布</template>
          <div ref="chartRef" style="height: 300px"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>最新公告</template>
          <div class="announcement-list">
            <div v-for="item in announcements" :key="item.id" class="announcement-item">
              <el-tag v-if="item.isTop" type="danger" size="small">置顶</el-tag>
              <span class="title">{{ item.title }}</span>
              <span class="time">{{ item.createTime?.substring(0, 10) }}</span>
            </div>
            <el-empty v-if="!announcements.length" description="暂无公告" />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'
import { employeeApi, attendanceApi, leaveApi, recruitmentApi, announcementApi } from '../api'

const stats = ref({})
const announcements = ref([])
const chartRef = ref()

const loadData = async () => {
  const [empStats, attStats, pendingLeave, openRecruitment, annList] = await Promise.all([
    employeeApi.getStatistics(),
    attendanceApi.getStatistics(),
    leaveApi.getPendingCount(),
    recruitmentApi.getOpenCount(),
    announcementApi.getTop(5)
  ])
  stats.value = {
    employee: empStats,
    attendance: attStats,
    pendingLeave,
    openRecruitment
  }
  announcements.value = annList
  initChart(empStats)
}

const initChart = (data) => {
  const chart = echarts.init(chartRef.value)
  chart.setOption({
    tooltip: { trigger: 'item' },
    legend: { bottom: 0 },
    series: [{
      type: 'pie',
      radius: ['40%', '70%'],
      data: [
        { value: data.trial || 0, name: '试用期' },
        { value: data.regular || 0, name: '正式' },
        { value: data.resigned || 0, name: '已离职' }
      ]
    }]
  })
}

onMounted(loadData)
</script>

<style scoped>
.stat-card {
  display: flex;
  align-items: center;
  padding: 20px;
}
.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: white;
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
  font-size: 14px;
}
.announcement-list {
  min-height: 250px;
}
.announcement-item {
  padding: 12px 0;
  border-bottom: 1px solid #eee;
  display: flex;
  align-items: center;
  gap: 10px;
}
.announcement-item .title {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.announcement-item .time {
  color: #999;
  font-size: 12px;
}
</style>
