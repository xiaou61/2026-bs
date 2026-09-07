<template>
  <div>
    <el-card>
      <el-page-header @back="$router.back()" title="返回">
        <template #content>
          <h2>{{ activity.title }}</h2>
        </template>
      </el-page-header>

      <el-divider />

      <el-descriptions :column="2" border>
        <el-descriptions-item label="活动地点">{{ activity.location }}</el-descriptions-item>
        <el-descriptions-item label="发布者">{{ activity.publisherName }}</el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ activity.startTime }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ activity.endTime }}</el-descriptions-item>
        <el-descriptions-item label="活动积分">
          <span style="color: #409eff; font-weight: bold">{{ activity.points }} 积分</span>
        </el-descriptions-item>
        <el-descriptions-item label="志愿时长">{{ activity.hours }} 小时</el-descriptions-item>
        <el-descriptions-item label="参与人数">
          {{ activity.currentParticipants }} / {{ activity.maxParticipants }}
        </el-descriptions-item>
        <el-descriptions-item label="活动状态">
          <el-tag :type="getStatusType(activity.status)">
            {{ getStatusText(activity.status) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="活动描述" :span="2">
          {{ activity.description }}
        </el-descriptions-item>
      </el-descriptions>

      <div style="margin-top: 20px">
        <el-button v-if="!enrollment && activity.status === 1" type="primary" @click="handleEnroll">我要报名</el-button>
        <el-button v-if="enrollment && enrollment.status === 1 && activity.status === 1" type="danger" @click="handleCancel">取消报名</el-button>
        <el-tag v-if="enrollment && enrollment.status === 1" type="success">已报名</el-tag>
        <el-tag v-if="enrollment && enrollment.status === 0" type="warning">待审核</el-tag>
        <el-button v-if="enrollment && enrollment.status === 1 && activity.status === 2 && !attendance" type="success" @click="handleCheckIn">签到</el-button>
        <el-button v-if="attendance && attendance.status === 0" type="warning" @click="handleCheckOut">签退</el-button>
        <el-tag v-if="attendance && attendance.status === 1" type="success">已完成</el-tag>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getActivityById } from '@/api/activity'
import { enrollActivity, cancelEnrollment, getMyEnrollments } from '@/api/enrollment'
import { checkIn, checkOut, getMyAttendances } from '@/api/attendance'

const route = useRoute()
const activity = ref({})
const enrollment = ref(null)
const attendance = ref(null)

const getStatusType = (status) => {
  const types = { 0: 'info', 1: 'success', 2: 'warning', 3: '', 4: 'danger' }
  return types[status]
}

const getStatusText = (status) => {
  const texts = { 0: '草稿', 1: '报名中', 2: '进行中', 3: '已结束', 4: '已取消' }
  return texts[status]
}

const loadActivity = async () => {
  const res = await getActivityById(route.params.id)
  activity.value = res.data
}

const loadEnrollment = async () => {
  const res = await getMyEnrollments()
  enrollment.value = res.data.find(e => e.activityId == route.params.id)
}

const loadAttendance = async () => {
  const res = await getMyAttendances()
  attendance.value = res.data.find(a => a.activityId == route.params.id)
}

const handleEnroll = async () => {
  try {
    await ElMessageBox.confirm('确定要报名此活动吗?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消'
    })
    await enrollActivity(route.params.id)
    ElMessage.success('报名成功')
    loadEnrollment()
    loadActivity()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

const handleCancel = async () => {
  try {
    await ElMessageBox.confirm('确定要取消报名吗?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await cancelEnrollment(enrollment.value.id)
    ElMessage.success('取消成功')
    enrollment.value = null
    loadActivity()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

const handleCheckIn = async () => {
  try {
    await checkIn(route.params.id)
    ElMessage.success('签到成功')
    loadAttendance()
  } catch (error) {
    console.error(error)
  }
}

const handleCheckOut = async () => {
  try {
    await ElMessageBox.confirm('确定要签退吗? 签退后将根据实际参与时长计算积分', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消'
    })
    await checkOut(attendance.value.id)
    ElMessage.success('签退成功')
    loadAttendance()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

onMounted(() => {
  loadActivity()
  loadEnrollment()
  loadAttendance()
})
</script>

