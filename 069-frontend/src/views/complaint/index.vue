<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 150px">
          <el-option label="待处理" value="WAITING" />
          <el-option label="已通过" value="PASSED" />
          <el-option label="已驳回" value="REJECTED" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="isTeacher" type="success" @click="handleAdd">发起申诉</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="taskName" label="任务" min-width="170" />
        <el-table-column prop="teacherName" label="教师" min-width="110" />
        <el-table-column prop="evaluatorName" label="评教人" min-width="110" />
        <el-table-column prop="totalScore" label="分数" width="90" />
        <el-table-column prop="reasonText" label="申诉原因" min-width="220" show-overflow-tooltip />
        <el-table-column prop="replyText" label="处理回复" min-width="220" show-overflow-tooltip />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="handleName" label="处理人" min-width="100" />
        <el-table-column prop="handleTime" label="处理时间" min-width="160" />
        <el-table-column v-if="isAdmin" label="操作" width="100">
          <template #default="{ row }">
            <el-button v-if="row.status === 'WAITING'" link type="primary" @click="openHandle(row)">处理</el-button>
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

    <el-dialog v-model="dialogVisible" title="发起申诉" width="560px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="评教记录" prop="recordId">
          <el-select v-model="form.recordId" filterable style="width: 100%">
            <el-option v-for="item in recordOptions" :key="item.id" :label="`${item.taskName} | 分数${item.totalScore}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="申诉原因" prop="reasonText">
          <el-input v-model="form.reasonText" type="textarea" :rows="4" maxlength="800" show-word-limit />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAppeal">提交</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="handleVisible" title="处理申诉" width="520px">
      <el-form ref="handleRef" :model="handleForm" :rules="handleRules" label-width="90px">
        <el-form-item label="处理结果" prop="status">
          <el-radio-group v-model="handleForm.status">
            <el-radio label="PASSED">通过</el-radio>
            <el-radio label="REJECTED">驳回</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="处理回复" prop="replyText">
          <el-input v-model="handleForm.replyText" type="textarea" :rows="4" maxlength="800" show-word-limit />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="handleVisible = false">取消</el-button>
        <el-button type="primary" @click="submitHandle">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addAppeal, getAppealPage, getMyAppealPage, getRecordPage, handleAppeal } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isTeacher = computed(() => userStore.user?.role === 'TEACHER')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const handleVisible = ref(false)
const handleRef = ref()
const recordOptions = ref([])

const query = reactive({ pageNum: 1, pageSize: 10, status: '' })
const form = reactive({ recordId: null, reasonText: '' })
const handleForm = reactive({ id: null, status: 'PASSED', replyText: '' })

const rules = {
  recordId: [{ required: true, message: '请选择评教记录', trigger: 'change' }],
  reasonText: [{ required: true, message: '请输入申诉原因', trigger: 'blur' }]
}

const handleRules = {
  status: [{ required: true, message: '请选择处理结果', trigger: 'change' }],
  replyText: [{ required: true, message: '请输入处理回复', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = isAdmin.value ? await getAppealPage(query) : await getMyAppealPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const loadRecordOptions = async () => {
  if (!isTeacher.value) {
    return
  }
  const res = await getRecordPage({ pageNum: 1, pageSize: 300 })
  recordOptions.value = res.data.records || []
}

const handleAdd = async () => {
  await loadRecordOptions()
  form.recordId = null
  form.reasonText = ''
  dialogVisible.value = true
}

const submitAppeal = async () => {
  await formRef.value.validate()
  await addAppeal(form)
  ElMessage.success('申诉提交成功')
  dialogVisible.value = false
  loadData()
}

const openHandle = (row) => {
  handleForm.id = row.id
  handleForm.status = 'PASSED'
  handleForm.replyText = ''
  handleVisible.value = true
}

const submitHandle = async () => {
  await handleRef.value.validate()
  await handleAppeal(handleForm)
  ElMessage.success('处理成功')
  handleVisible.value = false
  loadData()
}

const statusText = (status) => {
  if (status === 'WAITING') return '待处理'
  if (status === 'PASSED') return '已通过'
  if (status === 'REJECTED') return '已驳回'
  return status
}

const statusType = (status) => {
  if (status === 'WAITING') return 'warning'
  if (status === 'PASSED') return 'success'
  if (status === 'REJECTED') return 'danger'
  return ''
}

onMounted(async () => {
  await loadData()
  await loadRecordOptions()
})
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; flex-wrap: wrap; }
.pager { margin-top: 12px; display: flex; justify-content: flex-end; }
</style>
