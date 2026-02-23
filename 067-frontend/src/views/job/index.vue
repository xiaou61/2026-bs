<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.keyword" placeholder="岗位/企业" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 130px" clearable>
        <el-option label="草稿" :value="0" />
        <el-option label="招聘中" :value="1" />
        <el-option label="关闭" :value="2" />
      </el-select>
      <el-select v-if="isAdmin" v-model="query.publisherId" placeholder="发布人" style="width: 180px" clearable>
        <el-option v-for="item in teachers" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button v-if="isStaff" type="success" @click="openAdd">新增岗位</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="title" label="岗位" min-width="160" />
      <el-table-column prop="company" label="企业" min-width="150" />
      <el-table-column prop="city" label="城市" width="100" />
      <el-table-column prop="salary" label="薪资" width="110" />
      <el-table-column prop="deadline" label="截止日期" width="120" />
      <el-table-column prop="publisherName" label="发布人" width="120" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="220" show-overflow-tooltip />
      <el-table-column label="操作" width="240">
        <template #default="{ row }">
          <el-button v-if="isStaff" link type="primary" @click="openEdit(row)">编辑</el-button>
          <el-popconfirm v-if="isStaff" title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
          <el-button v-if="isStudent && row.status === 1" link type="success" @click="openApply(row)">投递</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑岗位' : '新增岗位'" width="700px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="岗位" prop="title"><el-input v-model="form.title" maxlength="120" /></el-form-item>
      <el-form-item label="企业" prop="company"><el-input v-model="form.company" maxlength="120" /></el-form-item>
      <el-form-item label="城市"><el-input v-model="form.city" maxlength="80" /></el-form-item>
      <el-form-item label="薪资"><el-input v-model="form.salary" maxlength="80" /></el-form-item>
      <el-form-item label="截止日期"><el-date-picker v-model="form.deadline" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item>
      <el-form-item label="发布人" prop="publisherId">
        <el-select v-model="form.publisherId" style="width: 100%" :disabled="isTeacher">
          <el-option v-for="item in teachers" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="form.status" style="width: 100%">
          <el-option label="草稿" :value="0" />
          <el-option label="招聘中" :value="1" />
          <el-option label="关闭" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="描述"><el-input v-model="form.description" type="textarea" :rows="4" maxlength="1000" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">保存</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="applyVisible" title="岗位投递" width="520px">
    <el-form :model="applyForm" label-width="90px">
      <el-form-item label="简历链接"><el-input v-model="applyForm.resumeUrl" maxlength="255" placeholder="可填网盘/附件地址" /></el-form-item>
      <el-form-item label="投递说明"><el-input v-model="applyForm.applyNote" type="textarea" :rows="4" maxlength="255" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="applyVisible = false">取消</el-button>
      <el-button type="primary" @click="submitApply">提交</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addApply, addJob, deleteJob, getJobPage, getTeacherList, updateJob } from '../../api'
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
const applyVisible = ref(false)
const formRef = ref()

const query = reactive({ pageNum: 1, pageSize: 10, keyword: '', status: null, publisherId: null })
const form = reactive({})
const applyForm = reactive({ jobId: null, resumeUrl: '', applyNote: '' })

const rules = {
  title: [{ required: true, message: '请输入岗位名称', trigger: 'blur' }],
  company: [{ required: true, message: '请输入企业名称', trigger: 'blur' }],
  publisherId: [{ required: true, message: '请选择发布人', trigger: 'change' }]
}

const statusText = (status) => {
  const v = Number(status)
  if (v === 0) return '草稿'
  if (v === 1) return '招聘中'
  if (v === 2) return '关闭'
  return '未知'
}

const statusType = (status) => {
  const v = Number(status)
  if (v === 1) return 'success'
  if (v === 2) return 'info'
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
    const res = await getJobPage(params)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = async () => {
  await loadBase()
  Object.assign(form, { id: null, title: '', company: '', city: '', salary: '', deadline: '', publisherId: isTeacher.value ? userStore.user?.id : null, status: 1, description: '' })
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
    await updateJob(form)
  } else {
    await addJob(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteJob(id)
  ElMessage.success('删除成功')
  loadData()
}

const openApply = (row) => {
  Object.assign(applyForm, { jobId: row.id, resumeUrl: '', applyNote: '' })
  applyVisible.value = true
}

const submitApply = async () => {
  await addApply(applyForm)
  ElMessage.success('投递成功')
  applyVisible.value = false
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
