<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.taskId" placeholder="任务ID" clearable style="width: 140px" />
        <el-input v-if="isAdmin" v-model="query.teacherId" placeholder="教师档案ID" clearable style="width: 150px" />
        <el-input v-if="isAdmin" v-model="query.evaluatorId" placeholder="评教人ID" clearable style="width: 150px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="isStudent" type="success" @click="handleAdd">提交评教</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="taskName" label="任务" min-width="180" />
        <el-table-column prop="evaluatorName" label="评教人" min-width="110" />
        <el-table-column prop="teacherName" label="教师" min-width="110" />
        <el-table-column prop="className" label="班级" min-width="100" />
        <el-table-column prop="totalScore" label="总分" width="90" />
        <el-table-column prop="commentText" label="评语" min-width="220" show-overflow-tooltip />
        <el-table-column prop="createTime" label="提交时间" min-width="170" />
        <el-table-column v-if="isAdmin" label="操作" width="90">
          <template #default="{ row }">
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

    <el-dialog v-model="dialogVisible" title="提交评教" width="560px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="考评任务" prop="taskId">
          <el-select v-model="form.taskId" style="width: 100%" filterable>
            <el-option v-for="item in taskOptions" :key="item.id" :label="`${item.taskName}（${item.teacherName}）`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="教学态度" prop="attitudeScore"><el-input-number v-model="form.attitudeScore" :min="0" :max="100" style="width: 100%" /></el-form-item>
        <el-form-item label="教学内容" prop="contentScore"><el-input-number v-model="form.contentScore" :min="0" :max="100" style="width: 100%" /></el-form-item>
        <el-form-item label="教学方法" prop="methodScore"><el-input-number v-model="form.methodScore" :min="0" :max="100" style="width: 100%" /></el-form-item>
        <el-form-item label="课堂管理" prop="manageScore"><el-input-number v-model="form.manageScore" :min="0" :max="100" style="width: 100%" /></el-form-item>
        <el-form-item label="作业反馈" prop="homeworkScore"><el-input-number v-model="form.homeworkScore" :min="0" :max="100" style="width: 100%" /></el-form-item>
        <el-form-item label="评语"><el-input v-model="form.commentText" type="textarea" :rows="3" maxlength="500" show-word-limit /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { addRecord, deleteRecord, getMyTaskPage, getRecordPage } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isStudent = computed(() => userStore.user?.role === 'STUDENT')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const taskOptions = ref([])

const query = reactive({ pageNum: 1, pageSize: 10, taskId: '', teacherId: '', evaluatorId: '' })
const form = reactive({ taskId: null, attitudeScore: 90, contentScore: 90, methodScore: 90, manageScore: 90, homeworkScore: 90, commentText: '' })
const rules = {
  taskId: [{ required: true, message: '请选择考评任务', trigger: 'change' }],
  attitudeScore: [{ required: true, message: '请输入教学态度分', trigger: 'blur' }],
  contentScore: [{ required: true, message: '请输入教学内容分', trigger: 'blur' }],
  methodScore: [{ required: true, message: '请输入教学方法分', trigger: 'blur' }],
  manageScore: [{ required: true, message: '请输入课堂管理分', trigger: 'blur' }],
  homeworkScore: [{ required: true, message: '请输入作业反馈分', trigger: 'blur' }]
}

const loadTaskOptions = async () => {
  if (!isStudent.value) {
    return
  }
  const res = await getMyTaskPage({ pageNum: 1, pageSize: 300, status: 'OPEN' })
  taskOptions.value = res.data.records || []
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      pageNum: query.pageNum,
      pageSize: query.pageSize,
      taskId: query.taskId || undefined,
      teacherId: query.teacherId || undefined,
      evaluatorId: query.evaluatorId || undefined
    }
    const res = await getRecordPage(params)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(form, { taskId: null, attitudeScore: 90, contentScore: 90, methodScore: 90, manageScore: 90, homeworkScore: 90, commentText: '' })
}

const handleAdd = async () => {
  await loadTaskOptions()
  resetForm()
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await addRecord(form)
  ElMessage.success('提交成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确定删除记录 #${row.id} 吗？`, '提示', { type: 'warning' })
  await deleteRecord(row.id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadData()
  await loadTaskOptions()
})
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; flex-wrap: wrap; }
.pager { margin-top: 12px; display: flex; justify-content: flex-end; }
</style>
