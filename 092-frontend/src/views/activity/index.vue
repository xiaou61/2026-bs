<template>
  <div class="page">
    <div class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item label="活动名称">
          <el-input v-model="filters.courseName" placeholder="请输入活动名称" clearable />
        </el-form-item>
        <el-form-item label="带班教师">
          <el-select v-model="filters.teacherId" placeholder="全部教师" clearable style="width: 160px">
            <el-option v-for="item in teacherOptions" :key="item.id" :label="item.realName" :value="item.id" />
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
        <el-button v-if="canEdit" type="success" @click="openDialog()">新增活动</el-button>
      </div>
    </div>

    <el-card shadow="never">
      <el-table :data="list" stripe>
        <el-table-column prop="courseName" label="活动名称" min-width="180" />
        <el-table-column prop="courseCode" label="活动编码" min-width="120" />
        <el-table-column prop="departmentName" label="所属园区" min-width="140" />
        <el-table-column prop="teacherName" label="带班教师" min-width="120" />
        <el-table-column prop="credit" label="学分" width="90" />
        <el-table-column prop="courseType" label="活动类型" min-width="100" />
        <el-table-column prop="courseHours" label="学时" width="90" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ getOptionLabel(statusOptions, row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="courseDesc" label="活动简介" min-width="220" show-overflow-tooltip />
        <el-table-column v-if="canEdit" label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openDialog(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑活动' : '新增活动'" width="640px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="92px">
        <el-form-item label="活动名称" prop="courseName">
          <el-input v-model="form.courseName" placeholder="请输入活动名称" />
        </el-form-item>
        <el-form-item label="活动编码" prop="courseCode">
          <el-input v-model="form.courseCode" placeholder="请输入活动编码" />
        </el-form-item>
        <el-form-item label="所属园区" prop="departmentId">
          <el-select v-model="form.departmentId" placeholder="请选择园区" style="width: 100%">
            <el-option v-for="item in departmentOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="带班教师" prop="teacherId">
          <el-select v-model="form.teacherId" placeholder="请选择教师" style="width: 100%">
            <el-option v-for="item in teacherOptions" :key="item.id" :label="item.realName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="学分">
          <el-input-number v-model="form.credit" :min="0" :max="20" :step="0.5" style="width: 100%" />
        </el-form-item>
        <el-form-item label="活动类型">
          <el-select v-model="form.courseType" placeholder="请选择活动类型" style="width: 100%">
            <el-option v-for="item in courseTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="活动学时">
          <el-input-number v-model="form.courseHours" :min="0" :max="200" style="width: 100%" />
        </el-form-item>
        <el-form-item label="活动简介">
          <el-input v-model="form.courseDesc" type="textarea" :rows="4" placeholder="请输入活动简介" />
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
import { addCourse, deleteCourse, getCourseList, getDepartmentEnabled, getUserOptions, updateCourse } from '../../api'
import { courseTypeOptions, getOptionLabel, resolvePage, statusOptions } from '../../utils'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const canEdit = computed(() => userStore.role === 'admin')
const departmentOptions = ref([])
const teacherOptions = ref([])
const filters = reactive({
  courseName: '',
  teacherId: undefined,
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
  courseName: '',
  courseCode: '',
  departmentId: null,
  teacherId: null,
  credit: 0,
  courseType: '必修',
  courseHours: 32,
  courseDesc: '',
  status: 1
})
const rules = {
  courseName: [{ required: true, message: '请输入活动名称', trigger: 'blur' }],
  courseCode: [{ required: true, message: '请输入活动编码', trigger: 'blur' }],
  departmentId: [{ required: true, message: '请选择所属园区', trigger: 'change' }],
  teacherId: [{ required: true, message: '请选择带班教师', trigger: 'change' }]
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    courseName: '',
    courseCode: '',
    departmentId: null,
    teacherId: null,
    credit: 0,
    courseType: '必修',
    courseHours: 32,
    courseDesc: '',
    status: 1
  })
}

const loadOptions = async () => {
  const [departmentRes, teacherRes] = await Promise.all([
    getDepartmentEnabled(),
    getUserOptions('teacher')
  ])
  departmentOptions.value = departmentRes.data || []
  teacherOptions.value = teacherRes.data || []
}

const loadData = async () => {
  const res = await getCourseList({
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    ...filters
  })
  const page = resolvePage(res.data)
  list.value = page.list
  total.value = page.total
}

const resetFilters = () => {
  filters.courseName = ''
  filters.teacherId = undefined
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
    await updateCourse(form)
    ElMessage.success('活动更新成功')
  } else {
    await addCourse(form)
    ElMessage.success('活动新增成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('删除后不可恢复，确定继续吗？', '提示', { type: 'warning' })
  await deleteCourse(id)
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

.pagination {
  margin-top: 16px;
}
</style>
