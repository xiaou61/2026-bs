<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.taskName" placeholder="任务名称" clearable style="width: 220px" />
        <el-select v-model="query.courseId" placeholder="课程" clearable style="width: 220px">
          <el-option v-for="item in courseOptions" :key="item.id" :label="item.courseName" :value="item.id" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 140px">
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="taskName" label="任务名称" min-width="220" />
        <el-table-column label="课程" min-width="160">
          <template #default="{ row }">{{ courseName(row.courseId) }}</template>
        </el-table-column>
        <el-table-column prop="term" label="学期" width="130" />
        <el-table-column label="时间区间" min-width="280">
          <template #default="{ row }">{{ row.startTime }} 至 {{ row.endTime }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="90">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="260">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="warning" @click="toggleStatus(row)">{{ row.status === 1 ? '禁用' : '启用' }}</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadData"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑任务' : '新增任务'" width="700px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="95px">
        <el-form-item label="任务名称" prop="taskName"><el-input v-model="form.taskName" /></el-form-item>
        <el-form-item label="课程" prop="courseId">
          <el-select v-model="form.courseId" style="width: 100%">
            <el-option v-for="item in courseOptions" :key="item.id" :label="item.courseName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="学期" prop="term"><el-input v-model="form.term" placeholder="如 2025-2026-2" /></el-form-item>
        <el-form-item label="时间区间" prop="dateRange">
          <el-date-picker
            v-model="form.dateRange"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addTask, deleteTask, getCourseEnabled, getTaskList, updateTask, updateTaskStatus } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const courseOptions = ref([])

const query = reactive({ pageNum: 1, pageSize: 10, taskName: '', courseId: null, status: null, term: '' })
const form = reactive({ id: null, taskName: '', courseId: null, term: '2025-2026-2', dateRange: [], status: 1 })

const rules = {
  taskName: [{ required: true, message: '请输入任务名称', trigger: 'blur' }],
  courseId: [{ required: true, message: '请选择课程', trigger: 'change' }],
  dateRange: [{ required: true, message: '请选择时间区间', trigger: 'change' }]
}

const courseName = (id) => {
  const target = courseOptions.value.find(item => item.id === id)
  return target ? target.courseName : '-'
}

const resetForm = () => {
  Object.assign(form, { id: null, taskName: '', courseId: null, term: '2025-2026-2', dateRange: [], status: 1 })
}

const loadCourse = async () => {
  const res = await getCourseEnabled()
  courseOptions.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getTaskList(query)
    tableData.value = res.data.list || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  form.dateRange = [row.startTime, row.endTime]
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  const payload = {
    ...form,
    startTime: form.dateRange?.[0],
    endTime: form.dateRange?.[1]
  }
  delete payload.dateRange

  if (payload.id) await updateTask(payload)
  else await addTask(payload)
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const toggleStatus = async (row) => {
  await updateTaskStatus(row.id, row.status === 1 ? 0 : 1)
  ElMessage.success('状态已更新')
  loadData()
}

const handleDelete = async (id) => {
  await deleteTask(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadCourse()
  await loadData()
})
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; flex-wrap: wrap; }
</style>
