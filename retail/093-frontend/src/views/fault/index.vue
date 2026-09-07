<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.handleStatus" placeholder="处理状态" clearable>
          <el-option label="PENDING" value="PENDING" />
          <el-option label="DONE" value="DONE" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="isCustomer" type="success" @click="handleAdd">提交反馈</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="orderNo" label="订单号" min-width="170" />
        <el-table-column prop="machineName" label="设备" min-width="160" />
        <el-table-column prop="reportType" label="反馈类型" min-width="120" />
        <el-table-column prop="content" label="反馈内容" min-width="240" show-overflow-tooltip />
        <el-table-column prop="handleStatus" label="处理状态" min-width="100" />
        <el-table-column prop="handlerName" label="处理人" min-width="120" />
        <el-table-column prop="handleResult" label="处理结果" min-width="220" show-overflow-tooltip />
        <el-table-column label="操作" width="160">
          <template #default="{ row }">
            <el-button v-if="!isCustomer && row.handleStatus !== 'DONE'" link type="primary" @click="handleDeal(row)">处理</el-button>
            <el-popconfirm v-if="!isCustomer" title="确认删除吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isCustomer ? '提交反馈' : '处理反馈'" width="560px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="90px">
        <template v-if="isCustomer">
          <el-form-item label="订单编号">
            <el-input v-model="form.orderId" placeholder="可选，填写订单ID" />
          </el-form-item>
          <el-form-item label="设备ID">
            <el-input v-model="form.machineId" placeholder="可选，填写设备ID" />
          </el-form-item>
          <el-form-item label="反馈类型" prop="reportType">
            <el-select v-model="form.reportType" style="width: 100%">
              <el-option label="未出货" value="未出货" />
              <el-option label="设备离线" value="设备离线" />
              <el-option label="其他问题" value="其他问题" />
            </el-select>
          </el-form-item>
          <el-form-item label="反馈内容" prop="content">
            <el-input v-model="form.content" type="textarea" />
          </el-form-item>
        </template>
        <template v-else>
          <el-form-item label="处理状态">
            <el-select v-model="dealForm.handleStatus" style="width: 100%">
              <el-option label="DONE" value="DONE" />
              <el-option label="PROCESSING" value="PROCESSING" />
            </el-select>
          </el-form-item>
          <el-form-item label="处理结果">
            <el-input v-model="dealForm.handleResult" type="textarea" />
          </el-form-item>
        </template>
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
import { ElMessage } from 'element-plus'
import { addFault, deleteFault, getFaultList, handleFault } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isCustomer = computed(() => (userStore.user?.role || '').toUpperCase() === 'CUSTOMER')

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const currentId = ref(null)
const query = reactive({
  pageNum: 1,
  pageSize: 20,
  handleStatus: ''
})
const form = reactive({
  orderId: '',
  machineId: '',
  reportType: '',
  content: ''
})
const dealForm = reactive({
  handleStatus: 'DONE',
  handleResult: ''
})
const rules = {
  reportType: [{ required: true, message: '请选择反馈类型', trigger: 'change' }],
  content: [{ required: true, message: '请输入反馈内容', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getFaultList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { orderId: '', machineId: '', reportType: '', content: '' })
  dialogVisible.value = true
}

const handleDeal = row => {
  currentId.value = row.id
  Object.assign(dealForm, { handleStatus: 'DONE', handleResult: row.handleResult || '' })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (isCustomer.value) {
    await formRef.value.validate()
    await addFault({
      ...form,
      orderId: form.orderId ? Number(form.orderId) : null,
      machineId: form.machineId ? Number(form.machineId) : null
    })
    ElMessage.success('反馈已提交')
  } else {
    await handleFault(currentId.value, dealForm)
    ElMessage.success('处理成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteFault(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}
</style>
