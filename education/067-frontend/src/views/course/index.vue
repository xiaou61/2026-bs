<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.name" placeholder="课程名称" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 130px" clearable>
        <el-option label="停开" :value="0" />
        <el-option label="进行中" :value="1" />
        <el-option label="结课" :value="2" />
      </el-select>
      <el-select v-if="isAdmin" v-model="query.teacherId" placeholder="授课教师" style="width: 180px" clearable>
        <el-option v-for="item in teachers" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button v-if="isStaff" type="success" @click="openAdd">新增课程</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="name" label="课程名称" min-width="180" />
      <el-table-column prop="teacherName" label="授课教师" width="120" />
      <el-table-column prop="credit" label="学分" width="80" />
      <el-table-column prop="location" label="地点" width="120" />
      <el-table-column label="人数" width="110">
        <template #default="{ row }">{{ row.selectedCount || 0 }}/{{ row.maxStudent || 0 }}</template>
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="startDate" label="开始日期" width="120" />
      <el-table-column prop="endDate" label="结束日期" width="120" />
      <el-table-column prop="description" label="简介" min-width="220" show-overflow-tooltip />
      <el-table-column label="操作" width="240">
        <template #default="{ row }">
          <el-button v-if="isStaff" link type="primary" @click="openEdit(row)">编辑</el-button>
          <el-popconfirm v-if="isStaff" title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
          <el-button v-if="isStudent && row.status === 1" link type="success" :disabled="(row.selectedCount || 0) >= (row.maxStudent || 0)" @click="handleEnroll(row)">选课</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑课程' : '新增课程'" width="700px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="课程名称" prop="name"><el-input v-model="form.name" maxlength="120" /></el-form-item>
      <el-form-item label="授课教师" prop="teacherId">
        <el-select v-model="form.teacherId" style="width: 100%" :disabled="isTeacher">
          <el-option v-for="item in teachers" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="学分" prop="credit"><el-input-number v-model="form.credit" :min="0.5" :max="10" :step="0.5" style="width: 100%" /></el-form-item>
      <el-form-item label="地点"><el-input v-model="form.location" maxlength="120" /></el-form-item>
      <el-form-item label="上限人数" prop="maxStudent"><el-input-number v-model="form.maxStudent" :min="1" :max="1000" style="width: 100%" /></el-form-item>
      <el-form-item label="开始日期"><el-date-picker v-model="form.startDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item>
      <el-form-item label="结束日期"><el-date-picker v-model="form.endDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item>
      <el-form-item label="状态">
        <el-select v-model="form.status" style="width: 100%">
          <el-option label="停开" :value="0" />
          <el-option label="进行中" :value="1" />
          <el-option label="结课" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="简介"><el-input v-model="form.description" type="textarea" :rows="4" maxlength="1000" /></el-form-item>
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
import { addCourse, addEnroll, deleteCourse, getCoursePage, getTeacherList, updateCourse } from '../../api'
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
const query = reactive({ pageNum: 1, pageSize: 10, name: '', status: null, teacherId: null })
const form = reactive({})

const rules = {
  name: [{ required: true, message: '请输入课程名称', trigger: 'blur' }],
  teacherId: [{ required: true, message: '请选择授课教师', trigger: 'change' }],
  credit: [{ required: true, message: '请设置学分', trigger: 'change' }],
  maxStudent: [{ required: true, message: '请设置上限人数', trigger: 'change' }]
}

const statusText = (status) => {
  const v = Number(status)
  if (v === 0) return '停开'
  if (v === 1) return '进行中'
  if (v === 2) return '结课'
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
    const res = await getCoursePage(params)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = async () => {
  await loadBase()
  Object.assign(form, { id: null, name: '', teacherId: isTeacher.value ? userStore.user?.id : null, credit: 2, location: '', maxStudent: 60, startDate: '', endDate: '', status: 1, description: '' })
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
    await updateCourse(form)
  } else {
    await addCourse(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteCourse(id)
  ElMessage.success('删除成功')
  loadData()
}

const handleEnroll = async (row) => {
  await addEnroll({ courseId: row.id })
  ElMessage.success('选课成功')
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
