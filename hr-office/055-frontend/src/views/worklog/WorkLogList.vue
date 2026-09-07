<template>
  <div class="page-container">
    <el-card>
      <div class="toolbar">
        <div class="search">
          <el-date-picker v-model="query.date" type="date" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" style="width: 150px;" />
          <el-input v-model="query.keyword" placeholder="员工姓名" clearable style="width: 150px;" v-if="!isEmployee" />
          <el-button type="primary" @click="loadData">查询</el-button>
        </div>
        <el-button type="primary" @click="handleAdd" v-if="isEmployee">写日志</el-button>
      </div>
      <el-table :data="list" v-loading="loading" stripe>
        <el-table-column prop="realName" label="员工" width="100" v-if="!isEmployee" />
        <el-table-column prop="logDate" label="日期" width="120" />
        <el-table-column prop="content" label="今日工作">
          <template #default="{ row }">
            <div class="log-content">{{ row.content }}</div>
          </template>
        </el-table-column>
        <el-table-column prop="plan" label="明日计划">
          <template #default="{ row }">
            <div class="log-content">{{ row.plan }}</div>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="提交时间" width="180" />
        <el-table-column label="操作" width="120" v-if="isEmployee">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px;" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑日志' : '写日志'" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="日期" prop="logDate">
          <el-date-picker v-model="form.logDate" type="date" format="YYYY-MM-DD" value-format="YYYY-MM-DD" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="今日工作" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="5" placeholder="请输入今日完成的工作内容" />
        </el-form-item>
        <el-form-item label="明日计划">
          <el-input v-model="form.plan" type="textarea" :rows="5" placeholder="请输入明日工作计划" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getWorkLogList, getMyWorkLogs, addWorkLog, updateWorkLog, deleteWorkLog } from '../../api'
import { useUserStore } from '../../store/user'
import dayjs from 'dayjs'

const userStore = useUserStore()
const isEmployee = computed(() => userStore.isEmployee)
const loading = ref(false)
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({
  date: '',
  keyword: '',
  pageNum: 1,
  pageSize: 10
})

const form = reactive({
  id: null,
  logDate: dayjs().format('YYYY-MM-DD'),
  content: '',
  plan: ''
})

const rules = {
  logDate: [{ required: true, message: '请选择日期', trigger: 'change' }],
  content: [{ required: true, message: '请输入今日工作内容', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const api = isEmployee.value ? getMyWorkLogs : getWorkLogList
    const res = await api(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(form, { id: null, logDate: dayjs().format('YYYY-MM-DD'), content: '', plan: '' })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  resetForm()
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateWorkLog(form)
    ElMessage.success('修改成功')
  } else {
    await addWorkLog(form)
    ElMessage.success('提交成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定删除该日志吗？', '提示', { type: 'warning' }).then(async () => {
    await deleteWorkLog(row.id)
    ElMessage.success('删除成功')
    loadData()
  })
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.page-container {
  padding: 10px;
}
.toolbar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
}
.search {
  display: flex;
  gap: 10px;
}
.log-content {
  white-space: pre-wrap;
  max-height: 100px;
  overflow: hidden;
}
</style>
