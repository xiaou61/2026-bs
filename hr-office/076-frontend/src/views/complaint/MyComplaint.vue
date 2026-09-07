<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.orderNo" placeholder="处理单号" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="待处理" :value="0" />
        <el-option label="已回复" :value="1" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="openAdd">新增申诉</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="orderNo" label="处理单号" min-width="180" />
      <el-table-column prop="serviceTitle" label="企业信息" min-width="160" />
      <el-table-column prop="type" label="类型" width="100" />
      <el-table-column prop="content" label="内容" min-width="240" />
      <el-table-column label="图片" width="120">
        <template #default="{ row }">
          <el-image v-if="row.images" :src="row.images" style="width: 50px; height: 50px; border-radius: 4px" :preview-src-list="[row.images]" />
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已回复' : '待处理' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="reply" label="平台回复" min-width="220" />
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" title="新增申诉" width="520px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="关联处理" prop="orderId">
        <el-select v-model="form.orderId" style="width: 100%" filterable placeholder="请选择处理单">
          <el-option v-for="order in orderOptions" :key="order.id" :label="`${order.orderNo} / ${order.serviceTitle || '企业信息'}`" :value="order.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="类型" prop="type"><el-input v-model="form.type" maxlength="30" show-word-limit /></el-form-item>
      <el-form-item label="内容" prop="content"><el-input v-model="form.content" type="textarea" :rows="4" maxlength="1000" show-word-limit /></el-form-item>
      <el-form-item label="图片">
        <el-input v-model="form.images" />
        <el-upload style="margin-top: 8px" :show-file-list="false" :http-request="handleImageUpload" accept="image/png,image/jpeg,image/webp,image/gif">
          <el-button type="primary" plain>上传图片</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { nextTick, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addComplaint, getMyComplaint, getMyOrders, uploadFile } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const orderOptions = ref([])
const formRef = ref()

const query = reactive({ pageNum: 1, pageSize: 10, status: null, orderNo: '' })
const form = reactive({ orderId: null, type: 'ORDER', content: '', images: '' })
const rules = {
  orderId: [{ required: true, message: '请选择关联处理单', trigger: 'change' }],
  type: [
    { required: true, message: '请输入申诉类型', trigger: 'blur' },
    { max: 30, message: '申诉类型不能超过30字符', trigger: 'blur' }
  ],
  content: [
    { required: true, message: '请输入申诉内容', trigger: 'blur' },
    { max: 1000, message: '申诉内容不能超过1000字符', trigger: 'blur' }
  ]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getMyComplaint(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const openAdd = async () => {
  await loadOrders()
  if (!orderOptions.value.length) {
    ElMessage.warning('暂无可申诉处理单')
    return
  }
  Object.assign(form, { orderId: null, type: 'ORDER', content: '', images: '' })
  dialogVisible.value = true
  nextTick(() => formRef.value?.clearValidate())
}

const submitForm = async () => {
  await formRef.value.validate()
  await addComplaint(form)
  ElMessage.success('提交成功')
  dialogVisible.value = false
  await loadOrders()
  await loadData()
}

const loadOrders = async () => {
  const res = await getMyOrders({ pageNum: 1, pageSize: 200 })
  orderOptions.value = (res.data.records || []).filter(order => [1, 2, 3].includes(order.status))
}

const handleImageUpload = async (option) => {
  if (option.file.size > 5 * 1024 * 1024) {
    ElMessage.warning('图片大小不能超过5MB')
    option.onError(new Error('file too large'))
    return
  }
  try {
    const res = await uploadFile(option.file)
    form.images = res.data
    option.onSuccess(res, option.file)
    ElMessage.success('图片上传成功')
  } catch (e) {
    option.onError(e)
  }
}

onMounted(loadData)
onMounted(loadOrders)
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}
</style>



