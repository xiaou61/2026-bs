<template>
  <div class="page-container">
    <el-card>
      <div class="toolbar">
        <div class="search">
          <el-date-picker v-model="query.date" type="date" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" style="width: 150px;" />
          <el-button type="primary" @click="loadData">查询</el-button>
        </div>
        <el-button type="primary" @click="handleAdd">预约会议</el-button>
      </div>
      <el-table :data="list" v-loading="loading" stripe>
        <el-table-column prop="title" label="会议主题" width="200" />
        <el-table-column prop="roomName" label="会议室" width="120" />
        <el-table-column prop="organizerName" label="组织者" width="100" />
        <el-table-column prop="startTime" label="开始时间" width="160" />
        <el-table-column prop="endTime" label="结束时间" width="160" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 0 ? 'info' : row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 0 ? '待开始' : row.status === 1 ? '进行中' : '已结束' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleView(row)">查看</el-button>
            <el-button link type="danger" @click="handleDelete(row)" v-if="row.status === 0">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px;" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="isView ? '会议详情' : '预约会议'" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px" :disabled="isView">
        <el-form-item label="会议主题" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="会议室" prop="roomId">
          <el-select v-model="form.roomId" placeholder="选择会议室" style="width: 100%;">
            <el-option v-for="r in roomList" :key="r.id" :label="`${r.name}（${r.capacity}人）`" :value="r.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="时间" prop="timeRange">
          <el-date-picker v-model="form.timeRange" type="datetimerange" start-placeholder="开始时间" end-placeholder="结束时间" format="YYYY-MM-DD HH:mm" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="参会人员">
          <el-select v-model="form.participantIds" multiple placeholder="选择参会人员" style="width: 100%;">
            <el-option v-for="u in userList" :key="u.id" :label="u.realName" :value="u.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="会议内容">
          <el-input v-model="form.content" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer v-if="!isView">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMeetingList, addMeeting, deleteMeeting, getAllMeetingRooms, getAllUsers } from '../../api'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const roomList = ref([])
const userList = ref([])
const dialogVisible = ref(false)
const isView = ref(false)
const formRef = ref()

const query = reactive({
  date: '',
  pageNum: 1,
  pageSize: 10
})

const form = reactive({
  id: null,
  title: '',
  roomId: null,
  timeRange: [],
  participantIds: [],
  content: ''
})

const rules = {
  title: [{ required: true, message: '请输入会议主题', trigger: 'blur' }],
  roomId: [{ required: true, message: '请选择会议室', trigger: 'change' }],
  timeRange: [{ required: true, message: '请选择时间', trigger: 'change' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getMeetingList(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const loadRooms = async () => {
  const res = await getAllMeetingRooms()
  roomList.value = res.data
}

const loadUsers = async () => {
  const res = await getAllUsers()
  userList.value = res.data
}

const resetForm = () => {
  Object.assign(form, { id: null, title: '', roomId: null, timeRange: [], participantIds: [], content: '' })
}

const handleAdd = () => {
  resetForm()
  isView.value = false
  dialogVisible.value = true
}

const handleView = (row) => {
  resetForm()
  Object.assign(form, row)
  form.timeRange = [row.startTime, row.endTime]
  form.participantIds = row.participants ? row.participants.split(',').map(Number) : []
  isView.value = true
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await addMeeting({
    title: form.title,
    roomId: form.roomId,
    startTime: form.timeRange[0],
    endTime: form.timeRange[1],
    participants: form.participantIds.join(','),
    content: form.content
  })
  ElMessage.success('预约成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定取消该会议吗？', '提示', { type: 'warning' }).then(async () => {
    await deleteMeeting(row.id)
    ElMessage.success('取消成功')
    loadData()
  })
}

onMounted(() => {
  loadData()
  loadRooms()
  loadUsers()
})
</script>

<style scoped>
.page-container {
  padding: 10px;
}
.toolbar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
}
.search {
  display: flex;
  gap: 10px;
}
</style>
