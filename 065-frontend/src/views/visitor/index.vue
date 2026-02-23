<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.orderNo" placeholder="登记单号" style="width: 200px" clearable />
      <el-select v-if="isStaffRole" v-model="query.ownerId" placeholder="业主" style="width: 160px" clearable>
        <el-option v-for="item in owners" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 130px" clearable>
        <el-option label="待审批" :value="0" />
        <el-option label="已通过" :value="1" />
        <el-option label="已驳回" :value="2" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button v-if="isOwner" type="success" @click="openAdd">新增登记</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="orderNo" label="登记单号" min-width="190" />
      <el-table-column v-if="isStaffRole" prop="ownerName" label="业主" width="120" />
      <el-table-column prop="visitorName" label="访客姓名" width="120" />
      <el-table-column prop="visitorPhone" label="访客电话" width="140" />
      <el-table-column prop="visitTime" label="来访时间" width="180" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="180" show-overflow-tooltip />
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button v-if="isStaffRole && row.status === 0" link type="primary" @click="openHandle(row)">审批</el-button>
          <el-popconfirm v-if="isOwner && row.status === 0" title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" title="新增访客登记" width="580px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="访客姓名" prop="visitorName"><el-input v-model="form.visitorName" maxlength="50" /></el-form-item>
      <el-form-item label="访客电话" prop="visitorPhone"><el-input v-model="form.visitorPhone" maxlength="20" /></el-form-item>
      <el-form-item label="来访时间" prop="visitTime"><el-date-picker v-model="form.visitTime" type="datetime" style="width: 100%" value-format="YYYY-MM-DDTHH:mm:ss" /></el-form-item>
      <el-form-item label="备注"><el-input v-model="form.remark" maxlength="255" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="handleVisible" title="审批访客登记" width="520px">
    <el-form :model="handleForm" label-width="90px">
      <el-form-item label="审批状态">
        <el-select v-model="handleForm.status" style="width: 100%">
          <el-option label="已通过" :value="1" />
          <el-option label="已驳回" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="备注"><el-input v-model="handleForm.remark" type="textarea" :rows="3" maxlength="255" /></el-form-item>
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
import { addVisitor, deleteVisitor, getMyVisitorPage, getOwnerList, getVisitorPage, updateVisitorStatus } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isStaff = computed(() => userStore.user?.role === 'STAFF')
const isOwner = computed(() => userStore.user?.role === 'OWNER')
const isStaffRole = computed(() => isAdmin.value || isStaff.value)
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const owners = ref([])
const dialogVisible = ref(false)
const handleVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, orderNo: '', ownerId: null, status: null })
const form = reactive({})
const handleForm = reactive({ id: null, status: 1, remark: '' })

const rules = {
  visitorName: [{ required: true, message: '请输入访客姓名', trigger: 'blur' }],
  visitorPhone: [{ required: true, message: '请输入访客电话', trigger: 'blur' }],
  visitTime: [{ required: true, message: '请选择来访时间', trigger: 'change' }]
}

const statusText = (status) => {
  const v = Number(status)
  if (v === 0) return '待审批'
  if (v === 1) return '已通过'
  if (v === 2) return '已驳回'
  return '未知'
}

const statusType = (status) => {
  const v = Number(status)
  if (v === 0) return 'warning'
  if (v === 1) return 'success'
  if (v === 2) return 'info'
  return ''
}

const loadBase = async () => {
  if (!isStaffRole.value) {
    return
  }
  const res = await getOwnerList()
  owners.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = isStaffRole.value ? await getVisitorPage(query) : await getMyVisitorPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = () => {
  Object.assign(form, { visitorName: '', visitorPhone: '', visitTime: '', remark: '' })
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  await addVisitor(form)
  ElMessage.success('提交成功')
  dialogVisible.value = false
  loadData()
}

const openHandle = (row) => {
  Object.assign(handleForm, { id: row.id, status: 1, remark: row.remark || '' })
  handleVisible.value = true
}

const submitHandle = async () => {
  await updateVisitorStatus(handleForm)
  ElMessage.success('审批成功')
  handleVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteVisitor(id)
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
  flex-wrap: wrap;
}
</style>
