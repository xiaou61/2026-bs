<template>
  <el-card>
    <div class="bar">
      <el-select v-model="query.activityId" placeholder="活动" style="width: 220px" clearable>
        <el-option v-for="item in activities" :key="item.id" :label="item.title" :value="item.id" />
      </el-select>
      <el-select v-if="isStaff" v-model="query.studentId" placeholder="学生" style="width: 180px" clearable>
        <el-option v-for="item in students" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 130px" clearable>
        <el-option label="已报名" :value="1" />
        <el-option label="已取消" :value="2" />
        <el-option label="已签到" :value="3" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="activityTitle" label="活动" min-width="220" />
      <el-table-column prop="studentName" label="学生" width="120" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="checkinTime" label="签到时间" width="170" />
      <el-table-column prop="remark" label="备注" min-width="220" show-overflow-tooltip />
      <el-table-column label="操作" width="250">
        <template #default="{ row }">
          <el-button v-if="isStaff" link type="primary" @click="openHandle(row)">处理</el-button>
          <el-button v-if="!isStaff && (row.status === 1 || row.status === 3)" link type="warning" @click="cancelSignup(row)">取消</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="handleVisible" title="处理报名" width="520px">
    <el-form :model="handleForm" label-width="80px">
      <el-form-item label="状态">
        <el-select v-model="handleForm.status" style="width: 100%">
          <el-option label="已报名" :value="1" />
          <el-option label="已取消" :value="2" />
          <el-option label="已签到" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="备注"><el-input v-model="handleForm.remark" type="textarea" :rows="3" maxlength="255" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="handleVisible = false">取消</el-button>
      <el-button type="primary" @click="submitHandle">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deleteSignup, getActivityList, getMySignupPage, getSignupPage, getStudentList, updateSignupStatus } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isTeacher = computed(() => userStore.user?.role === 'TEACHER')
const isStaff = computed(() => isAdmin.value || isTeacher.value)

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const activities = ref([])
const students = ref([])
const handleVisible = ref(false)

const query = reactive({ pageNum: 1, pageSize: 10, activityId: null, studentId: null, status: null })
const handleForm = reactive({ id: null, status: 1, remark: '' })

const statusText = (status) => {
  const v = Number(status)
  if (v === 1) return '已报名'
  if (v === 2) return '已取消'
  if (v === 3) return '已签到'
  return '未知'
}

const statusType = (status) => {
  const v = Number(status)
  if (v === 1) return 'success'
  if (v === 2) return 'info'
  if (v === 3) return 'primary'
  return ''
}

const loadBase = async () => {
  const tasks = [getActivityList({ status: null })]
  if (isStaff.value) {
    tasks.push(getStudentList())
  }
  const res = await Promise.all(tasks)
  activities.value = res[0].data || []
  if (isStaff.value) {
    students.value = res[1].data || []
  }
}

const loadData = async () => {
  loading.value = true
  try {
    const res = isStaff.value ? await getSignupPage(query) : await getMySignupPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openHandle = (row) => {
  Object.assign(handleForm, { id: row.id, status: row.status, remark: row.remark || '' })
  handleVisible.value = true
}

const submitHandle = async () => {
  await updateSignupStatus(handleForm)
  ElMessage.success('处理成功')
  handleVisible.value = false
  loadData()
}

const cancelSignup = async (row) => {
  await updateSignupStatus({ id: row.id, status: 2, remark: '学生取消报名' })
  ElMessage.success('已取消报名')
  loadData()
}

const handleDelete = async (id) => {
  await deleteSignup(id)
  ElMessage.success('删除成功')
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
