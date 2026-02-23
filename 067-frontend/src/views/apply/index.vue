<template>
  <el-card>
    <div class="bar">
      <el-select v-model="query.jobId" placeholder="岗位" style="width: 220px" clearable>
        <el-option v-for="item in jobs" :key="item.id" :label="jobLabel(item)" :value="item.id" />
      </el-select>
      <el-select v-if="isStaff" v-model="query.studentId" placeholder="学生" style="width: 180px" clearable>
        <el-option v-for="item in students" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 130px" clearable>
        <el-option label="待审核" :value="0" />
        <el-option label="通过" :value="1" />
        <el-option label="拒绝" :value="2" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="jobTitle" label="岗位" min-width="150" />
      <el-table-column prop="company" label="企业" width="140" />
      <el-table-column prop="studentName" label="学生" width="120" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="resumeUrl" label="简历链接" min-width="180" show-overflow-tooltip />
      <el-table-column prop="applyNote" label="投递说明" min-width="180" show-overflow-tooltip />
      <el-table-column prop="reviewNote" label="审核说明" min-width="180" show-overflow-tooltip />
      <el-table-column prop="reviewerName" label="审核人" width="120" />
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button v-if="isStaff" link type="primary" @click="openReview(row)">审核</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="reviewVisible" title="审核投递" width="520px">
    <el-form :model="reviewForm" label-width="90px">
      <el-form-item label="审核结果">
        <el-select v-model="reviewForm.status" style="width: 100%">
          <el-option label="通过" :value="1" />
          <el-option label="拒绝" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="审核说明"><el-input v-model="reviewForm.reviewNote" type="textarea" :rows="4" maxlength="255" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="reviewVisible = false">取消</el-button>
      <el-button type="primary" @click="submitReview">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deleteApply, getApplyPage, getJobList, getMyApplyPage, getStudentList, reviewApply } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isTeacher = computed(() => userStore.user?.role === 'TEACHER')
const isStaff = computed(() => isAdmin.value || isTeacher.value)

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const jobs = ref([])
const students = ref([])
const reviewVisible = ref(false)

const query = reactive({ pageNum: 1, pageSize: 10, jobId: null, studentId: null, status: null })
const reviewForm = reactive({ id: null, status: 1, reviewNote: '' })

const statusText = (status) => {
  const v = Number(status)
  if (v === 0) return '待审核'
  if (v === 1) return '通过'
  if (v === 2) return '拒绝'
  return '未知'
}

const statusType = (status) => {
  const v = Number(status)
  if (v === 1) return 'success'
  if (v === 2) return 'danger'
  return 'warning'
}

const jobLabel = (row) => `${row.title}${row.company ? ` - ${row.company}` : ''}`

const loadBase = async () => {
  const tasks = [getJobList({ status: null })]
  if (isStaff.value) {
    tasks.push(getStudentList())
  }
  const res = await Promise.all(tasks)
  jobs.value = res[0].data || []
  if (isStaff.value) {
    students.value = res[1].data || []
  }
}

const loadData = async () => {
  loading.value = true
  try {
    const res = isStaff.value ? await getApplyPage(query) : await getMyApplyPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openReview = (row) => {
  Object.assign(reviewForm, { id: row.id, status: row.status === 0 ? 1 : row.status, reviewNote: row.reviewNote || '' })
  reviewVisible.value = true
}

const submitReview = async () => {
  await reviewApply(reviewForm)
  ElMessage.success('审核成功')
  reviewVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteApply(id)
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
