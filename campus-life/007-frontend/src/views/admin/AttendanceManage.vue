<template>
  <div>
    <el-card>
      <el-button type="primary" @click="showDialog = true">管理员补签</el-button>
    </el-card>

    <el-card style="margin-top: 20px">
      <el-table :data="attendances" border>
        <el-table-column prop="activityTitle" label="活动标题" />
        <el-table-column prop="userName" label="志愿者" width="120" />
        <el-table-column prop="checkInTime" label="签到时间" width="180" />
        <el-table-column prop="checkOutTime" label="签退时间" width="180" />
        <el-table-column prop="actualHours" label="实际时长" width="100">
          <template #default="{ row }">
            {{ row.actualHours ? row.actualHours.toFixed(1) : '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="points" label="获得积分" width="100">
          <template #default="{ row }">
            <span style="color: #67c23a; font-weight: bold">{{ row.points || '-' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'warning'">
              {{ row.status === 1 ? '已完成' : '仅签到' }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="管理员补签" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="活动">
          <el-select v-model="form.activityId" placeholder="选择活动" style="width: 100%" @change="handleActivityChange">
            <el-option v-for="act in activities" :key="act.id" :label="act.title" :value="act.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="志愿者">
          <el-select v-model="form.userId" placeholder="选择志愿者" style="width: 100%">
            <el-option v-for="user in users" :key="user.id" :label="user.name" :value="user.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="签到时间">
          <el-date-picker v-model="form.checkInTime" type="datetime" placeholder="选择签到时间" style="width: 100%" value-format="YYYY-MM-DD HH:mm:ss" />
        </el-form-item>
        <el-form-item label="签退时间">
          <el-date-picker v-model="form.checkOutTime" type="datetime" placeholder="选择签退时间" style="width: 100%" value-format="YYYY-MM-DD HH:mm:ss" />
        </el-form-item>
        <el-form-item label="实际时长">
          <el-input-number v-model="form.actualHours" :min="0" :step="0.5" />
        </el-form-item>
        <el-form-item label="获得积分">
          <el-input-number v-model="form.points" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getAttendanceList, manualCheckIn } from '@/api/attendance'
import { getActivityList } from '@/api/activity'
import { getUserList } from '@/api/user'

const attendances = ref([])
const activities = ref([])
const users = ref([])
const showDialog = ref(false)

const form = reactive({
  activityId: null,
  userId: null,
  checkInTime: '',
  checkOutTime: '',
  actualHours: 2,
  points: 30
})

const loadAttendances = async () => {
  const res = await getAttendanceList()
  attendances.value = res.data
}

const loadActivities = async () => {
  const res = await getActivityList({ page: 1, size: 100 })
  activities.value = res.data.records
}

const loadUsers = async () => {
  const res = await getUserList({ page: 1, size: 100, role: 'VOLUNTEER' })
  users.value = res.data.records
}

const handleActivityChange = (activityId) => {
  const activity = activities.value.find(a => a.id === activityId)
  if (activity) {
    form.points = activity.points
    form.actualHours = activity.hours
  }
}

const handleSave = async () => {
  try {
    await manualCheckIn(form)
    ElMessage.success('补签成功')
    showDialog.value = false
    Object.assign(form, {
      activityId: null,
      userId: null,
      checkInTime: '',
      checkOutTime: '',
      actualHours: 2,
      points: 30
    })
    loadAttendances()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadAttendances()
  loadActivities()
  loadUsers()
})
</script>

