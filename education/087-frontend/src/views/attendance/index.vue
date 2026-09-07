<template>
  <div class="page">
    <div class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item v-if="!isStudent" label="排课">
          <el-select v-model="filters.scheduleId" placeholder="全部排课" clearable style="width: 260px" @change="handleFilterScheduleChange">
            <el-option v-for="item in scheduleOptions" :key="item.id" :label="`${item.courseName} / ${item.weekDay}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="!isStudent" label="学生">
          <el-select v-model="filters.studentId" placeholder="全部学生" clearable style="width: 180px">
            <el-option v-for="item in selectionOptions" :key="item.studentId" :label="item.studentName" :value="item.studentId" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="toolbar-actions">
        <el-button @click="resetFilters">重置</el-button>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="canEdit" type="success" @click="openDialog()">新增考勤</el-button>
      </div>
    </div>

    <el-card shadow="never">
      <el-table :data="list" stripe>
        <el-table-column prop="courseName" label="课程名称" min-width="160" />
        <el-table-column prop="studentName" label="学生姓名" min-width="120" />
        <el-table-column prop="attendanceDate" label="考勤日期" min-width="120" />
        <el-table-column label="考勤状态" width="100">
          <template #default="{ row }">
            {{ getOptionLabel(attendanceStatusOptions, row.attendanceStatus) }}
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" min-width="200" show-overflow-tooltip />
        <el-table-column v-if="canEdit" label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openDialog(row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑考勤' : '新增考勤'" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="92px">
        <el-form-item label="排课" prop="scheduleId">
          <el-select v-model="form.scheduleId" placeholder="请选择排课" style="width: 100%" @change="handleFormScheduleChange">
            <el-option v-for="item in scheduleOptions" :key="item.id" :label="`${item.courseName} / ${item.weekDay}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="学生" prop="studentId">
          <el-select v-model="form.studentId" placeholder="请选择学生" style="width: 100%">
            <el-option v-for="item in selectionOptions" :key="item.studentId" :label="item.studentName" :value="item.studentId" />
          </el-select>
        </el-form-item>
        <el-form-item label="考勤日期">
          <el-date-picker v-model="form.attendanceDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="考勤状态">
          <el-select v-model="form.attendanceStatus" placeholder="请选择状态" style="width: 100%">
            <el-option v-for="item in attendanceStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="4" placeholder="请输入备注" />
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
import { ElMessage } from 'element-plus'
import { addAttendance, getAttendanceList, getScheduleList, getSelectionList, getTeacherSchedule, updateAttendance } from '../../api'
import { attendanceStatusOptions, getOptionLabel, resolvePage } from '../../utils'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isStudent = computed(() => userStore.role === 'student')
const canEdit = computed(() => userStore.role === 'teacher' || userStore.role === 'admin')

const filters = reactive({
  scheduleId: undefined,
  studentId: undefined
})
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const list = ref([])
const scheduleOptions = ref([])
const selectionOptions = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({
  id: null,
  scheduleId: null,
  courseId: null,
  studentId: null,
  attendanceDate: '',
  attendanceStatus: 'present',
  remark: ''
})
const rules = {
  scheduleId: [{ required: true, message: '请选择排课', trigger: 'change' }],
  studentId: [{ required: true, message: '请选择学生', trigger: 'change' }]
}

const loadScheduleOptions = async () => {
  if (userStore.role === 'admin') {
    const res = await getScheduleList({ pageNum: 1, pageSize: 200, status: 1 })
    scheduleOptions.value = res.data?.list || []
    return
  }
  const res = await getTeacherSchedule()
  scheduleOptions.value = res.data || []
}

const loadSelectionOptions = async (scheduleId) => {
  if (!scheduleId) {
    selectionOptions.value = []
    return
  }
  const res = await getSelectionList({ pageNum: 1, pageSize: 200, scheduleId, selectStatus: 1 })
  selectionOptions.value = res.data?.list || []
}

const loadData = async () => {
  const res = await getAttendanceList({
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    ...filters
  })
  const page = resolvePage(res.data)
  list.value = page.list
  total.value = page.total
}

const handleFilterScheduleChange = async (scheduleId) => {
  filters.studentId = undefined
  await loadSelectionOptions(scheduleId)
}

const handleFormScheduleChange = async (scheduleId) => {
  form.studentId = null
  const current = scheduleOptions.value.find((item) => item.id === scheduleId)
  form.courseId = current?.courseId || null
  await loadSelectionOptions(scheduleId)
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    scheduleId: null,
    courseId: null,
    studentId: null,
    attendanceDate: '',
    attendanceStatus: 'present',
    remark: ''
  })
}

const openDialog = async (row) => {
  resetForm()
  if (row) {
    Object.assign(form, { ...row })
    await loadSelectionOptions(row.scheduleId)
  }
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateAttendance(form)
    ElMessage.success('考勤更新成功')
  } else {
    await addAttendance(form)
    ElMessage.success('考勤新增成功')
  }
  dialogVisible.value = false
  loadData()
}

const resetFilters = async () => {
  filters.scheduleId = undefined
  filters.studentId = undefined
  pageNum.value = 1
  selectionOptions.value = []
  await loadData()
}

onMounted(async () => {
  if (!isStudent.value) {
    await loadScheduleOptions()
  }
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

.pagination {
  margin-top: 16px;
}
</style>
