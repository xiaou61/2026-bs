<template>
  <div>
    <el-card>
      <el-button type="primary" @click="showDialog = true">创建活动</el-button>
    </el-card>

    <el-card style="margin-top: 20px">
      <el-table :data="activities" border>
        <el-table-column prop="title" label="活动标题" />
        <el-table-column prop="location" label="地点" width="120" />
        <el-table-column prop="startTime" label="开始时间" width="180" />
        <el-table-column prop="points" label="积分" width="80" />
        <el-table-column prop="hours" label="时长" width="80" />
        <el-table-column prop="currentParticipants" label="参与人数" width="100">
          <template #default="{ row }">
            {{ row.currentParticipants }}/{{ row.maxParticipants }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="warning" @click="handleStatus(row)">状态</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" :title="form.id ? '编辑活动' : '创建活动'" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="活动标题">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="活动描述">
          <el-input v-model="form.description" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="活动地点">
          <el-input v-model="form.location" />
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker v-model="form.startTime" type="datetime" placeholder="选择开始时间" style="width: 100%" value-format="YYYY-MM-DD HH:mm:ss" />
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker v-model="form.endTime" type="datetime" placeholder="选择结束时间" style="width: 100%" value-format="YYYY-MM-DD HH:mm:ss" />
        </el-form-item>
        <el-form-item label="最大人数">
          <el-input-number v-model="form.maxParticipants" :min="1" />
        </el-form-item>
        <el-form-item label="活动积分">
          <el-input-number v-model="form.points" :min="0" />
        </el-form-item>
        <el-form-item label="志愿时长">
          <el-input-number v-model="form.hours" :min="0" :step="0.5" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="showStatusDialog" title="更改状态" width="400px">
      <el-select v-model="currentStatus" placeholder="选择状态" style="width: 100%">
        <el-option label="草稿" :value="0" />
        <el-option label="报名中" :value="1" />
        <el-option label="进行中" :value="2" />
        <el-option label="已结束" :value="3" />
        <el-option label="已取消" :value="4" />
      </el-select>
      <template #footer>
        <el-button @click="showStatusDialog = false">取消</el-button>
        <el-button type="primary" @click="confirmStatus">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getActivityList, createActivity, updateActivity, deleteActivity, updateActivityStatus } from '@/api/activity'

const activities = ref([])
const showDialog = ref(false)
const showStatusDialog = ref(false)
const currentId = ref(null)
const currentStatus = ref(null)

const form = reactive({
  id: null,
  title: '',
  description: '',
  location: '',
  startTime: '',
  endTime: '',
  maxParticipants: 10,
  points: 30,
  hours: 2
})

const getStatusType = (status) => {
  const types = { 0: 'info', 1: 'success', 2: 'warning', 3: '', 4: 'danger' }
  return types[status]
}

const getStatusText = (status) => {
  const texts = { 0: '草稿', 1: '报名中', 2: '进行中', 3: '已结束', 4: '已取消' }
  return texts[status]
}

const loadActivities = async () => {
  const res = await getActivityList({ page: 1, size: 100 })
  activities.value = res.data.records
}

const handleEdit = (row) => {
  Object.assign(form, row)
  showDialog.value = true
}

const handleSave = async () => {
  try {
    if (form.id) {
      await updateActivity(form.id, form)
      ElMessage.success('更新成功')
    } else {
      await createActivity(form)
      ElMessage.success('创建成功')
    }
    showDialog.value = false
    resetForm()
    loadActivities()
  } catch (error) {
    console.error(error)
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除吗?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteActivity(id)
    ElMessage.success('删除成功')
    loadActivities()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

const handleStatus = (row) => {
  currentId.value = row.id
  currentStatus.value = row.status
  showStatusDialog.value = true
}

const confirmStatus = async () => {
  try {
    await updateActivityStatus(currentId.value, currentStatus.value)
    ElMessage.success('状态更新成功')
    showStatusDialog.value = false
    loadActivities()
  } catch (error) {
    console.error(error)
  }
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    title: '',
    description: '',
    location: '',
    startTime: '',
    endTime: '',
    maxParticipants: 10,
    points: 30,
    hours: 2
  })
}

onMounted(() => {
  loadActivities()
})
</script>

