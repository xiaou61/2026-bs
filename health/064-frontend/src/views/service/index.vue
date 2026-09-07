<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.orderNo" placeholder="服务单号" style="width: 210px" clearable />
      <el-select v-if="isAdmin" v-model="query.userId" placeholder="用户" style="width: 150px" clearable>
        <el-option v-for="item in users" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 130px" clearable>
        <el-option label="待处理" :value="0" />
        <el-option label="已确认" :value="1" />
        <el-option label="已完成" :value="2" />
        <el-option label="已取消" :value="3" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button v-if="isUser" type="success" @click="openAdd">新增预约</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="orderNo" label="服务单号" min-width="190" />
      <el-table-column v-if="isAdmin" prop="userName" label="用户" width="120" />
      <el-table-column prop="planName" label="方案" width="160" />
      <el-table-column prop="contactName" label="联系人" width="110" />
      <el-table-column prop="contactPhone" label="联系电话" width="130" />
      <el-table-column prop="appointmentDate" label="预约日期" width="120" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="160" show-overflow-tooltip />
      <el-table-column prop="adminReply" label="平台回复" min-width="180" show-overflow-tooltip />
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button v-if="isAdmin" link type="primary" @click="openHandle(row)">处理</el-button>
          <el-popconfirm v-if="isUser && (row.status === 0 || row.status === 3)" title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" title="新增预约" width="600px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="95px">
      <el-form-item label="调理方案" prop="planId">
        <el-select v-model="form.planId" style="width: 100%">
          <el-option v-for="item in plans" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="联系人" prop="contactName"><el-input v-model="form.contactName" maxlength="50" /></el-form-item>
      <el-form-item label="联系电话" prop="contactPhone"><el-input v-model="form.contactPhone" maxlength="20" /></el-form-item>
      <el-form-item label="预约日期" prop="appointmentDate"><el-date-picker v-model="form.appointmentDate" type="date" style="width: 100%" value-format="YYYY-MM-DD" /></el-form-item>
      <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" :rows="3" maxlength="255" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="handleVisible" title="处理服务单" width="500px">
    <el-form :model="handleForm" label-width="90px">
      <el-form-item label="状态">
        <el-select v-model="handleForm.status" style="width: 100%">
          <el-option label="待处理" :value="0" />
          <el-option label="已确认" :value="1" />
          <el-option label="已完成" :value="2" />
          <el-option label="已取消" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="回复">
        <el-input v-model="handleForm.adminReply" type="textarea" :rows="3" maxlength="255" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="handleVisible = false">取消</el-button>
      <el-button type="primary" @click="submitHandle">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addServiceOrder, deleteServiceOrder, getMyServicePage, getPlanList, getServicePage, getUserPage, updateServiceStatus } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isUser = computed(() => userStore.user?.role === 'USER')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const users = ref([])
const plans = ref([])
const dialogVisible = ref(false)
const handleVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, orderNo: '', userId: null, status: null })
const form = reactive({})
const handleForm = reactive({ id: null, status: 0, adminReply: '' })

const rules = {
  planId: [{ required: true, message: '请选择方案', trigger: 'change' }],
  contactName: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
  contactPhone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
  appointmentDate: [{ required: true, message: '请选择预约日期', trigger: 'change' }]
}

const statusText = (status) => {
  if (status === 0) return '待处理'
  if (status === 1) return '已确认'
  if (status === 2) return '已完成'
  if (status === 3) return '已取消'
  return '未知'
}

const statusType = (status) => {
  if (status === 0) return 'warning'
  if (status === 1) return 'primary'
  if (status === 2) return 'success'
  if (status === 3) return 'info'
  return ''
}

const loadBase = async () => {
  const tasks = [getPlanList()]
  if (isAdmin.value) {
    tasks.push(getUserPage({ pageNum: 1, pageSize: 999 }))
  }
  const res = await Promise.all(tasks)
  plans.value = res[0].data || []
  if (isAdmin.value) {
    users.value = res[1].data.records || []
  }
}

const loadData = async () => {
  loading.value = true
  try {
    const res = isAdmin.value ? await getServicePage(query) : await getMyServicePage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = async () => {
  await loadBase()
  Object.assign(form, { planId: null, contactName: '', contactPhone: '', appointmentDate: '', remark: '' })
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  await addServiceOrder(form)
  ElMessage.success('预约提交成功')
  dialogVisible.value = false
  loadData()
}

const openHandle = (row) => {
  Object.assign(handleForm, { id: row.id, status: row.status, adminReply: row.adminReply || '' })
  handleVisible.value = true
}

const submitHandle = async () => {
  await updateServiceStatus(handleForm)
  ElMessage.success('处理成功')
  handleVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteServiceOrder(id)
  ElMessage.success('删除成功')
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
}
</style>
