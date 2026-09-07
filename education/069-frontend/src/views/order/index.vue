<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.taskName" placeholder="任务名称" clearable style="width: 180px" />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 140px">
          <el-option label="草稿" value="DRAFT" />
          <el-option label="进行中" value="OPEN" />
          <el-option label="已结束" value="CLOSED" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="isAdmin" type="success" @click="handleAdd">新增任务</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="taskName" label="任务名称" min-width="190" />
        <el-table-column prop="termName" label="学期" min-width="170" />
        <el-table-column prop="className" label="班级" min-width="120" />
        <el-table-column prop="teacherName" label="教师" min-width="120" />
        <el-table-column prop="startTime" label="开始时间" min-width="170" />
        <el-table-column prop="endTime" label="结束时间" min-width="170" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column v-if="isAdmin" label="操作" width="220">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-dropdown @command="val => handleStatusChange(row, val)">
              <el-button link>状态</el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="DRAFT">草稿</el-dropdown-item>
                  <el-dropdown-item command="OPEN">进行中</el-dropdown-item>
                  <el-dropdown-item command="CLOSED">已结束</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pager">
        <el-pagination
          v-model:current-page="query.pageNum"
          v-model:page-size="query.pageSize"
          :total="total"
          :page-sizes="[10, 20, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          @current-change="loadData"
          @size-change="loadData"
        />
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑任务' : '新增任务'" width="560px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="任务名称" prop="taskName"><el-input v-model="form.taskName" maxlength="100" /></el-form-item>
        <el-form-item label="学期" prop="termName"><el-input v-model="form.termName" maxlength="30" /></el-form-item>
        <el-form-item label="班级" prop="classId">
          <el-select v-model="form.classId" style="width: 100%">
            <el-option v-for="item in classOptions" :key="item.id" :label="`${item.gradeName}${item.className}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="教师" prop="teacherId">
          <el-select v-model="form.teacherId" style="width: 100%">
            <el-option v-for="item in teacherOptions" :key="item.id" :label="`${item.teacherName}-${item.teacherNo}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker v-model="form.startTime" type="datetime" value-format="YYYY-MM-DDTHH:mm:ss" style="width: 100%" />
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker v-model="form.endTime" type="datetime" value-format="YYYY-MM-DDTHH:mm:ss" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" style="width: 100%">
            <el-option label="草稿" value="DRAFT" />
            <el-option label="进行中" value="OPEN" />
            <el-option label="已结束" value="CLOSED" />
          </el-select>
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
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { addTask, deleteTask, getClassList, getMyTaskPage, getTaskPage, getTeacherList, updateTask, updateTaskStatus } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const classOptions = ref([])
const teacherOptions = ref([])

const query = reactive({ pageNum: 1, pageSize: 10, taskName: '', status: '' })
const form = reactive({ id: null, taskName: '', termName: '', classId: null, teacherId: null, startTime: '', endTime: '', status: 'DRAFT' })
const rules = {
  taskName: [{ required: true, message: '请输入任务名称', trigger: 'blur' }],
  termName: [{ required: true, message: '请输入学期', trigger: 'blur' }],
  classId: [{ required: true, message: '请选择班级', trigger: 'change' }],
  teacherId: [{ required: true, message: '请选择教师', trigger: 'change' }],
  startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
  endTime: [{ required: true, message: '请选择结束时间', trigger: 'change' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }]
}

const loadOptions = async () => {
  const [classRes, teacherRes] = await Promise.all([getClassList(), getTeacherList()])
  classOptions.value = classRes.data || []
  teacherOptions.value = teacherRes.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = isAdmin.value ? await getTaskPage(query) : await getMyTaskPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const statusText = (status) => {
  if (status === 'DRAFT') return '草稿'
  if (status === 'OPEN') return '进行中'
  if (status === 'CLOSED') return '已结束'
  return status
}

const statusType = (status) => {
  if (status === 'DRAFT') return 'info'
  if (status === 'OPEN') return 'success'
  if (status === 'CLOSED') return 'warning'
  return ''
}

const resetForm = () => {
  Object.assign(form, { id: null, taskName: '', termName: '', classId: null, teacherId: null, startTime: '', endTime: '', status: 'DRAFT' })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, {
    id: row.id,
    taskName: row.taskName,
    termName: row.termName,
    classId: row.classId,
    teacherId: row.teacherId,
    startTime: row.startTime,
    endTime: row.endTime,
    status: row.status
  })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateTask(form)
  } else {
    await addTask(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确定删除任务 ${row.taskName} 吗？`, '提示', { type: 'warning' })
  await deleteTask(row.id)
  ElMessage.success('删除成功')
  loadData()
}

const handleStatusChange = async (row, status) => {
  await updateTaskStatus({ id: row.id, status })
  row.status = status
  ElMessage.success('状态已更新')
}

onMounted(async () => {
  await loadOptions()
  await loadData()
})
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; flex-wrap: wrap; }
.pager { margin-top: 12px; display: flex; justify-content: flex-end; }
</style>
