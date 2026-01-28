<template>
  <div class="page-container">
    <el-card>
      <div class="toolbar">
        <div class="search">
          <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px;">
            <el-option label="待审批" :value="0" />
            <el-option label="已通过" :value="1" />
            <el-option label="已拒绝" :value="2" />
          </el-select>
          <el-button type="primary" @click="loadData">查询</el-button>
        </div>
        <el-button type="primary" @click="handleAdd" v-if="isEmployee">申请请假</el-button>
      </div>
      <el-table :data="list" v-loading="loading" stripe>
        <el-table-column prop="realName" label="申请人" width="100" v-if="!isEmployee" />
        <el-table-column prop="leaveType" label="请假类型" width="100">
          <template #default="{ row }">{{ getLeaveType(row.leaveType) }}</template>
        </el-table-column>
        <el-table-column prop="startTime" label="开始时间" width="160" />
        <el-table-column prop="endTime" label="结束时间" width="160" />
        <el-table-column prop="days" label="天数" width="80" />
        <el-table-column prop="reason" label="请假原因" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 0 ? 'warning' : row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 0 ? '待审批' : row.status === 1 ? '已通过' : '已拒绝' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <template v-if="!isEmployee && row.status === 0">
              <el-button link type="success" @click="handleApprove(row, 1)">通过</el-button>
              <el-button link type="danger" @click="handleApprove(row, 2)">拒绝</el-button>
            </template>
            <el-button link type="danger" @click="handleDelete(row)" v-if="isEmployee && row.status === 0">撤回</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px;" />
    </el-card>
    <el-dialog v-model="dialogVisible" title="申请请假" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="请假类型" prop="leaveType">
          <el-select v-model="form.leaveType" style="width: 100%;">
            <el-option label="事假" :value="1" />
            <el-option label="病假" :value="2" />
            <el-option label="年假" :value="3" />
            <el-option label="婚假" :value="4" />
            <el-option label="产假" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="时间范围" prop="timeRange">
          <el-date-picker v-model="form.timeRange" type="datetimerange" start-placeholder="开始时间" end-placeholder="结束时间" format="YYYY-MM-DD HH:mm" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="请假天数" prop="days">
          <el-input-number v-model="form.days" :min="0.5" :step="0.5" />
        </el-form-item>
        <el-form-item label="请假原因" prop="reason">
          <el-input v-model="form.reason" type="textarea" :rows="3" />
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
import { getLeaveList, getMyLeaves, addLeave, approveLeave, deleteLeave } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isEmployee = computed(() => userStore.isEmployee)
const loading = ref(false)
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({
  status: null,
  pageNum: 1,
  pageSize: 10
})

const form = reactive({
  leaveType: 1,
  timeRange: [],
  days: 1,
  reason: ''
})

const rules = {
  leaveType: [{ required: true, message: '请选择请假类型', trigger: 'change' }],
  timeRange: [{ required: true, message: '请选择时间范围', trigger: 'change' }],
  days: [{ required: true, message: '请输入请假天数', trigger: 'blur' }],
  reason: [{ required: true, message: '请输入请假原因', trigger: 'blur' }]
}

const getLeaveType = (type) => {
  const map = { 1: '事假', 2: '病假', 3: '年假', 4: '婚假', 5: '产假' }
  return map[type] || '未知'
}

const loadData = async () => {
  loading.value = true
  try {
    const api = isEmployee.value ? getMyLeaves : getLeaveList
    const res = await api(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  form.leaveType = 1
  form.timeRange = []
  form.days = 1
  form.reason = ''
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await addLeave({
    leaveType: form.leaveType,
    startTime: form.timeRange[0],
    endTime: form.timeRange[1],
    days: form.days,
    reason: form.reason
  })
  ElMessage.success('提交成功')
  dialogVisible.value = false
  loadData()
}

const handleApprove = (row, status) => {
  const action = status === 1 ? '通过' : '拒绝'
  ElMessageBox.confirm(`确定${action}该请假申请吗？`, '提示', { type: 'warning' }).then(async () => {
    await approveLeave({ id: row.id, status })
    ElMessage.success('操作成功')
    loadData()
  })
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定撤回该请假申请吗？', '提示', { type: 'warning' }).then(async () => {
    await deleteLeave(row.id)
    ElMessage.success('撤回成功')
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
</style>
