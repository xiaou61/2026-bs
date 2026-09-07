<template>
  <div class="page">
    <div v-if="canEdit" class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item label="课程名称">
          <el-input v-model="filters.courseName" placeholder="请输入课程名称" clearable />
        </el-form-item>
        <el-form-item label="授课教师">
          <el-select v-model="filters.teacherId" placeholder="全部教师" clearable style="width: 160px">
            <el-option v-for="item in teacherOptions" :key="item.id" :label="item.realName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="学期">
          <el-select v-model="filters.termId" placeholder="全部学期" clearable style="width: 180px">
            <el-option v-for="item in termOptions" :key="item.id" :label="item.termName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="班级">
          <el-select v-model="filters.classId" placeholder="全部班级" clearable style="width: 180px">
            <el-option v-for="item in classOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="filters.status" placeholder="全部状态" clearable style="width: 140px">
            <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="toolbar-actions">
        <el-button @click="resetFilters">重置</el-button>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="openDialog()">新增排课</el-button>
      </div>
    </div>

    <el-card v-else shadow="never" class="summary-card">
      <div class="summary-title">{{ pageTitle }}</div>
      <div class="summary-desc">{{ pageDesc }}</div>
    </el-card>

    <el-card shadow="never">
      <el-table :data="list" stripe>
        <el-table-column prop="courseName" label="课程名称" min-width="160" />
        <el-table-column prop="teacherName" label="授课教师" min-width="110" />
        <el-table-column prop="termName" label="学期" min-width="180" />
        <el-table-column prop="className" label="班级" min-width="130" />
        <el-table-column label="上课时间" min-width="160">
          <template #default="{ row }">
            {{ row.weekDay }} {{ row.startSection }}-{{ row.endSection }}节
          </template>
        </el-table-column>
        <el-table-column prop="classroom" label="教室" min-width="100" />
        <el-table-column label="人数" width="120">
          <template #default="{ row }">
            {{ row.selectedCount || 0 }}/{{ row.maxStudentCount || 0 }}
          </template>
        </el-table-column>
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ getOptionLabel(statusOptions, row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column v-if="canEdit" label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openDialog(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div v-if="canEdit" class="pagination">
        <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50]"
          :total="total"
          layout="total, sizes, prev, pager, next"
          @current-change="loadData"
          @size-change="loadData"
        />
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑排课' : '新增排课'" width="640px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="92px">
        <el-form-item label="课程" prop="courseId">
          <el-select v-model="form.courseId" placeholder="请选择课程" style="width: 100%">
            <el-option v-for="item in courseOptions" :key="item.id" :label="item.courseName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="学期" prop="termId">
          <el-select v-model="form.termId" placeholder="请选择学期" style="width: 100%">
            <el-option v-for="item in termOptions" :key="item.id" :label="item.termName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="教师" prop="teacherId">
          <el-select v-model="form.teacherId" placeholder="请选择教师" style="width: 100%">
            <el-option v-for="item in teacherOptions" :key="item.id" :label="item.realName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="班级">
          <el-select v-model="form.classId" placeholder="请选择班级" style="width: 100%">
            <el-option v-for="item in classOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="上课教室">
          <el-input v-model="form.classroom" placeholder="请输入教室" />
        </el-form-item>
        <el-form-item label="星期">
          <el-select v-model="form.weekDay" placeholder="请选择上课日" style="width: 100%">
            <el-option v-for="item in weekDayOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="开始节次">
          <el-input-number v-model="form.startSection" :min="1" :max="12" style="width: 100%" />
        </el-form-item>
        <el-form-item label="结束节次">
          <el-input-number v-model="form.endSection" :min="1" :max="12" style="width: 100%" />
        </el-form-item>
        <el-form-item label="人数上限">
          <el-input-number v-model="form.maxStudentCount" :min="1" :max="500" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { addSchedule, deleteSchedule, getClassEnabled, getCourseEnabled, getScheduleList, getStudentSchedule, getTeacherSchedule, getTermEnabled, getUserOptions, updateSchedule } from '../../api'
import { getOptionLabel, resolvePage, statusOptions, weekDayOptions } from '../../utils'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const canEdit = computed(() => userStore.role === 'admin')
const pageTitle = computed(() => userStore.role === 'teacher' ? '我的授课安排' : '我的个人课表')
const pageDesc = computed(() => userStore.role === 'teacher' ? '这里展示当前教师的全部授课排期。' : '这里展示学生已选课程形成的个人课表。')

const courseOptions = ref([])
const termOptions = ref([])
const classOptions = ref([])
const teacherOptions = ref([])
const filters = reactive({
  courseName: '',
  teacherId: undefined,
  termId: undefined,
  classId: undefined,
  status: undefined
})
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const list = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({
  id: null,
  courseId: null,
  termId: null,
  teacherId: null,
  classId: null,
  classroom: '',
  weekDay: '周一',
  startSection: 1,
  endSection: 2,
  maxStudentCount: 60,
  status: 1
})
const rules = {
  courseId: [{ required: true, message: '请选择课程', trigger: 'change' }],
  termId: [{ required: true, message: '请选择学期', trigger: 'change' }],
  teacherId: [{ required: true, message: '请选择教师', trigger: 'change' }]
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    courseId: null,
    termId: null,
    teacherId: null,
    classId: null,
    classroom: '',
    weekDay: '周一',
    startSection: 1,
    endSection: 2,
    maxStudentCount: 60,
    status: 1
  })
}

const loadOptions = async () => {
  const [courseRes, termRes, classRes, teacherRes] = await Promise.all([
    getCourseEnabled(),
    getTermEnabled(),
    getClassEnabled(),
    getUserOptions('teacher')
  ])
  courseOptions.value = courseRes.data || []
  termOptions.value = termRes.data || []
  classOptions.value = classRes.data || []
  teacherOptions.value = teacherRes.data || []
}

const loadData = async () => {
  if (canEdit.value) {
    const res = await getScheduleList({
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      ...filters
    })
    const page = resolvePage(res.data)
    list.value = page.list
    total.value = page.total
    return
  }
  const res = userStore.role === 'teacher' ? await getTeacherSchedule() : await getStudentSchedule()
  list.value = res.data || []
}

const resetFilters = () => {
  filters.courseName = ''
  filters.teacherId = undefined
  filters.termId = undefined
  filters.classId = undefined
  filters.status = undefined
  pageNum.value = 1
  loadData()
}

const openDialog = (row) => {
  resetForm()
  if (row) {
    Object.assign(form, { ...row })
  }
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateSchedule(form)
    ElMessage.success('排课更新成功')
  } else {
    await addSchedule(form)
    ElMessage.success('排课新增成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('删除后不可恢复，确定继续吗？', '提示', { type: 'warning' })
  await deleteSchedule(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadOptions()
  await loadData()
})
</script>

<style scoped>
.page {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  flex-wrap: wrap;
}

.toolbar-actions,
.pagination {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.summary-card {
  border-radius: 24px;
}

.summary-title {
  font-size: 22px;
  color: #3f2f1f;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.summary-desc {
  margin-top: 10px;
  color: var(--subtle);
}

.pagination {
  margin-top: 16px;
}
</style>
