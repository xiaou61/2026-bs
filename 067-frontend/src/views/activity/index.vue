<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.title" placeholder="活动标题" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 130px" clearable>
        <el-option label="草稿" :value="0" />
        <el-option label="报名中" :value="1" />
        <el-option label="进行中" :value="2" />
        <el-option label="已结束" :value="3" />
      </el-select>
      <el-select v-if="isAdmin" v-model="query.organizerId" placeholder="组织者" style="width: 180px" clearable>
        <el-option v-for="item in teachers" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button v-if="isStaff" type="success" @click="openAdd">新增活动</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="title" label="活动标题" min-width="200" />
      <el-table-column prop="organizerName" label="组织者" width="120" />
      <el-table-column prop="location" label="地点" width="120" />
      <el-table-column label="人数" width="110">
        <template #default="{ row }">{{ row.participantCount || 0 }}/{{ row.maxParticipant || 0 }}</template>
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="startTime" label="开始时间" width="170" />
      <el-table-column prop="endTime" label="结束时间" width="170" />
      <el-table-column prop="content" label="活动说明" min-width="220" show-overflow-tooltip />
      <el-table-column label="操作" width="240">
        <template #default="{ row }">
          <el-button v-if="isStaff" link type="primary" @click="openEdit(row)">编辑</el-button>
          <el-popconfirm v-if="isStaff" title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
          <el-button v-if="isStudent && row.status === 1" link type="success" :disabled="(row.participantCount || 0) >= (row.maxParticipant || 0)" @click="handleSignup(row)">报名</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑活动' : '新增活动'" width="760px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="活动标题" prop="title"><el-input v-model="form.title" maxlength="120" /></el-form-item>
      <el-form-item label="组织者" prop="organizerId">
        <el-select v-model="form.organizerId" style="width: 100%" :disabled="isTeacher">
          <el-option v-for="item in teachers" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="地点"><el-input v-model="form.location" maxlength="120" /></el-form-item>
      <el-form-item label="开始时间"><el-date-picker v-model="form.startTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" /></el-form-item>
      <el-form-item label="结束时间"><el-date-picker v-model="form.endTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" /></el-form-item>
      <el-form-item label="上限人数" prop="maxParticipant"><el-input-number v-model="form.maxParticipant" :min="1" :max="5000" style="width: 100%" /></el-form-item>
      <el-form-item label="状态">
        <el-select v-model="form.status" style="width: 100%">
          <el-option label="草稿" :value="0" />
          <el-option label="报名中" :value="1" />
          <el-option label="进行中" :value="2" />
          <el-option label="已结束" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="活动说明"><el-input v-model="form.content" type="textarea" :rows="4" maxlength="1000" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addActivity, addSignup, deleteActivity, getActivityPage, getTeacherList, updateActivity } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isTeacher = computed(() => userStore.user?.role === 'TEACHER')
const isStudent = computed(() => userStore.user?.role === 'STUDENT')
const isStaff = computed(() => isAdmin.value || isTeacher.value)

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const teachers = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, title: '', status: null, organizerId: null })
const form = reactive({})

const rules = {
  title: [{ required: true, message: '请输入活动标题', trigger: 'blur' }],
  organizerId: [{ required: true, message: '请选择组织者', trigger: 'change' }],
  maxParticipant: [{ required: true, message: '请设置人数上限', trigger: 'change' }]
}

const statusText = (status) => {
  const v = Number(status)
  if (v === 0) return '草稿'
  if (v === 1) return '报名中'
  if (v === 2) return '进行中'
  if (v === 3) return '已结束'
  return '未知'
}

const statusType = (status) => {
  const v = Number(status)
  if (v === 1) return 'success'
  if (v === 2) return 'primary'
  if (v === 3) return 'info'
  return 'warning'
}

const loadBase = async () => {
  const res = await getTeacherList()
  teachers.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const params = { ...query }
    if (isStudent.value) {
      params.status = params.status == null ? 1 : params.status
    }
    const res = await getActivityPage(params)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = async () => {
  await loadBase()
  Object.assign(form, { id: null, title: '', organizerId: isTeacher.value ? userStore.user?.id : null, location: '', startTime: '', endTime: '', maxParticipant: 200, status: 1, content: '' })
  dialogVisible.value = true
}

const openEdit = async (row) => {
  await loadBase()
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateActivity(form)
  } else {
    await addActivity(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteActivity(id)
  ElMessage.success('删除成功')
  loadData()
}

const handleSignup = async (row) => {
  await addSignup({ activityId: row.id })
  ElMessage.success('报名成功')
  loadData()
}

onMounted(async () => {
  await loadBase()
  await loadData()
})
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
  flex-wrap: wrap;
}
</style>
