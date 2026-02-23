<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.orderNo" placeholder="报修单号" style="width: 200px" clearable />
      <el-select v-if="isStaffRole" v-model="query.ownerId" placeholder="业主" style="width: 160px" clearable>
        <el-option v-for="item in owners" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 130px" clearable>
        <el-option label="待受理" :value="0" />
        <el-option label="处理中" :value="1" />
        <el-option label="已完成" :value="2" />
        <el-option label="已取消" :value="3" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button v-if="isOwner" type="success" @click="openAdd">新增报修</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="orderNo" label="报修单号" min-width="190" />
      <el-table-column prop="houseName" label="房屋" min-width="180" />
      <el-table-column v-if="isStaffRole" prop="ownerName" label="业主" width="120" />
      <el-table-column prop="title" label="标题" min-width="160" />
      <el-table-column prop="content" label="描述" min-width="220" show-overflow-tooltip />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="assigneeName" label="处理人" width="120" />
      <el-table-column prop="reply" label="处理结果" min-width="180" show-overflow-tooltip />
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button v-if="isStaffRole && (row.status === 0 || row.status === 1)" link type="primary" @click="openHandle(row)">处理</el-button>
          <el-popconfirm v-if="isOwner && (row.status === 0 || row.status === 3)" title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" title="新增报修" width="600px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="房屋" prop="houseId">
        <el-select v-model="form.houseId" style="width: 100%">
          <el-option v-for="item in houses" :key="item.id" :label="houseText(item)" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="标题" prop="title"><el-input v-model="form.title" maxlength="120" /></el-form-item>
      <el-form-item label="描述" prop="content"><el-input v-model="form.content" type="textarea" :rows="4" maxlength="500" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="handleVisible" title="处理报修" width="520px">
    <el-form :model="handleForm" label-width="90px">
      <el-form-item label="状态">
        <el-select v-model="handleForm.status" style="width: 100%">
          <el-option v-if="handleFromStatus === 0" label="处理中" :value="1" />
          <el-option v-if="handleFromStatus === 1" label="已完成" :value="2" />
          <el-option v-if="handleFromStatus === 0 || handleFromStatus === 1" label="已取消" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="处理结果"><el-input v-model="handleForm.reply" type="textarea" :rows="4" maxlength="255" /></el-form-item>
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
import { addRepair, deleteRepair, getHouseList, getMyRepairPage, getOwnerList, getRepairPage, updateRepairStatus } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isStaff = computed(() => userStore.user?.role === 'STAFF')
const isOwner = computed(() => userStore.user?.role === 'OWNER')
const isStaffRole = computed(() => isAdmin.value || isStaff.value)
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const houses = ref([])
const owners = ref([])
const dialogVisible = ref(false)
const handleVisible = ref(false)
const handleFromStatus = ref(0)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, orderNo: '', ownerId: null, status: null })
const form = reactive({})
const handleForm = reactive({ id: null, status: 0, reply: '' })

const rules = {
  houseId: [{ required: true, message: '请选择房屋', trigger: 'change' }],
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入描述', trigger: 'blur' }]
}

const houseText = (h) => `${h.buildingName ? `${h.buildingName}-` : ''}${h.unitNo}-${h.roomNo}`

const statusText = (status) => {
  const v = Number(status)
  if (v === 0) return '待受理'
  if (v === 1) return '处理中'
  if (v === 2) return '已完成'
  if (v === 3) return '已取消'
  return '未知'
}

const statusType = (status) => {
  const v = Number(status)
  if (v === 0) return 'warning'
  if (v === 1) return 'primary'
  if (v === 2) return 'success'
  if (v === 3) return 'info'
  return ''
}

const loadBase = async () => {
  const tasks = [getHouseList()]
  if (isStaffRole.value) {
    tasks.push(getOwnerList())
  }
  const res = await Promise.all(tasks)
  houses.value = res[0].data || []
  if (isStaffRole.value) {
    owners.value = res[1].data || []
  }
}

const loadData = async () => {
  loading.value = true
  try {
    const res = isStaffRole.value ? await getRepairPage(query) : await getMyRepairPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = async () => {
  await loadBase()
  Object.assign(form, { houseId: null, title: '', content: '' })
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  await addRepair(form)
  ElMessage.success('报修提交成功')
  dialogVisible.value = false
  loadData()
}

const openHandle = (row) => {
  handleFromStatus.value = Number(row.status)
  Object.assign(handleForm, { id: row.id, status: row.status === 0 ? 1 : 2, reply: row.reply || '' })
  handleVisible.value = true
}

const submitHandle = async () => {
  if (handleForm.status === 2 && !String(handleForm.reply || '').trim()) {
    ElMessage.warning('工单完成时请填写处理结果')
    return
  }
  const payload = { ...handleForm }
  if (handleForm.status === 1 || handleForm.status === 2) {
    payload.assigneeId = userStore.user?.id
  }
  await updateRepairStatus(payload)
  ElMessage.success('处理成功')
  handleVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteRepair(id)
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
