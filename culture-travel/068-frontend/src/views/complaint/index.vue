<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 140px">
          <el-option label="待处理" value="WAITING" />
          <el-option label="已处理" value="HANDLED" />
        </el-select>
        <el-input v-if="isAdmin" v-model="query.username" placeholder="用户名/昵称" clearable style="width: 220px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="isUser" type="danger" @click="openAddDialog">提交投诉</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="orderNo" label="订单号" min-width="150" />
        <el-table-column v-if="isAdmin" prop="userName" label="用户" min-width="110" />
        <el-table-column prop="type" label="类型" width="120" />
        <el-table-column prop="content" label="投诉内容" min-width="220" show-overflow-tooltip />
        <el-table-column label="附件" width="110">
          <template #default="{ row }">
            <el-button link type="primary" :disabled="attachmentCount(row) === 0" @click="openPreview(row)">
              查看{{ attachmentCount(row) > 0 ? `(${attachmentCount(row)})` : '' }}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 'WAITING' ? 'warning' : 'success'">{{ row.status === 'WAITING' ? '待处理' : '已处理' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="handleResult" label="处理结果" min-width="220" show-overflow-tooltip />
        <el-table-column prop="createTime" label="提交时间" min-width="170" />
        <el-table-column v-if="isAdmin" label="操作" width="120">
          <template #default="{ row }">
            <el-button link type="primary" :disabled="row.status === 'HANDLED'" @click="openHandleDialog(row)">处理</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pager">
        <el-pagination
          v-model:current-page="query.pageNum"
          v-model:page-size="query.pageSize"
          :total="total"
          :page-sizes="[10, 20, 30]"
          layout="total, sizes, prev, pager, next, jumper"
          @current-change="loadData"
          @size-change="loadData"
        />
      </div>
    </el-card>

    <el-dialog v-model="addDialogVisible" title="提交投诉" width="560px">
      <el-form ref="addFormRef" :model="addForm" :rules="addRules" label-width="90px">
        <el-form-item label="订单" prop="orderId">
          <el-select v-model="addForm.orderId" filterable style="width: 100%">
            <el-option v-for="item in orderList" :key="item.id" :label="`${item.orderNo} - ${item.spotName}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="addForm.type" style="width: 100%">
            <el-option label="行程变更" value="行程变更" />
            <el-option label="服务体验" value="服务体验" />
            <el-option label="费用问题" value="费用问题" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="addForm.content" type="textarea" :rows="4" maxlength="1000" />
        </el-form-item>
        <el-form-item label="附件图片">
          <input ref="fileRef" type="file" accept="image/*" multiple @change="handleSelectFiles" />
          <div class="preview-wrap">
            <div v-for="(item, idx) in addForm.attachmentUrls" :key="idx" class="preview-item">
              <el-image :src="item" fit="cover" class="thumb" :preview-src-list="addForm.attachmentUrls" />
              <el-button link type="danger" @click="removeAttachment(idx)">移除</el-button>
            </div>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAdd">提交</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="handleDialogVisible" title="处理投诉" width="520px">
      <el-form ref="handleFormRef" :model="handleForm" :rules="handleRules" label-width="90px">
        <el-form-item label="处理结果" prop="handleResult">
          <el-input v-model="handleForm.handleResult" type="textarea" :rows="4" maxlength="1000" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="handleDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确认处理</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="previewVisible" title="投诉附件" width="760px">
      <div class="preview-dialog">
        <el-image v-for="(url, idx) in previewUrls" :key="idx" :src="url" fit="cover" class="dialog-image" :preview-src-list="previewUrls" />
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addComplaint, getComplaintPage, getMyComplaintPage, getMyOrderPage, handleComplaint } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isUser = computed(() => userStore.user?.role === 'USER')

const loading = ref(false)
const total = ref(0)
const tableData = ref([])
const addDialogVisible = ref(false)
const handleDialogVisible = ref(false)
const addFormRef = ref()
const handleFormRef = ref()
const orderList = ref([])
const fileRef = ref()
const previewVisible = ref(false)
const previewUrls = ref([])

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  status: '',
  username: ''
})

const addForm = reactive({
  orderId: null,
  type: '服务体验',
  content: '',
  attachmentUrls: []
})

const handleForm = reactive({
  id: null,
  handleResult: ''
})

const addRules = {
  orderId: [{ required: true, message: '请选择订单', trigger: 'change' }],
  type: [{ required: true, message: '请选择投诉类型', trigger: 'change' }],
  content: [{ required: true, message: '请输入投诉内容', trigger: 'blur' }]
}

const handleRules = {
  handleResult: [{ required: true, message: '请输入处理结果', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = isAdmin.value ? await getComplaintPage(query) : await getMyComplaintPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const loadOrders = async () => {
  const res = await getMyOrderPage({ pageNum: 1, pageSize: 200 })
  orderList.value = res.data.records || []
}

const openAddDialog = async () => {
  await loadOrders()
  if (!orderList.value.length) {
    ElMessage.warning('暂无可投诉订单')
    return
  }
  Object.assign(addForm, { orderId: null, type: '服务体验', content: '', attachmentUrls: [] })
  if (fileRef.value) {
    fileRef.value.value = ''
  }
  addDialogVisible.value = true
}

const handleAdd = async () => {
  await addFormRef.value.validate()
  await addComplaint({
    orderId: addForm.orderId,
    type: addForm.type,
    content: addForm.content,
    attachmentUrls: JSON.stringify(addForm.attachmentUrls)
  })
  ElMessage.success('投诉已提交')
  addDialogVisible.value = false
  loadData()
}

const openHandleDialog = (row) => {
  Object.assign(handleForm, { id: row.id, handleResult: row.handleResult || '' })
  handleDialogVisible.value = true
}

const handleSubmit = async () => {
  await handleFormRef.value.validate()
  await handleComplaint(handleForm)
  ElMessage.success('处理完成')
  handleDialogVisible.value = false
  loadData()
}

const handleSelectFiles = async (event) => {
  const files = Array.from(event.target.files || [])
  if (!files.length) {
    return
  }
  for (const file of files) {
    if (addForm.attachmentUrls.length >= 5) {
      ElMessage.warning('最多上传5张附件图片')
      break
    }
    const base64 = await fileToBase64(file)
    addForm.attachmentUrls.push(base64)
  }
  if (fileRef.value) {
    fileRef.value.value = ''
  }
}

const fileToBase64 = (file) => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}

const removeAttachment = (index) => {
  addForm.attachmentUrls.splice(index, 1)
}

const parseAttachments = (row) => {
  if (!row || !row.attachmentUrls) {
    return []
  }
  try {
    const list = JSON.parse(row.attachmentUrls)
    return Array.isArray(list) ? list : []
  } catch (e) {
    return []
  }
}

const attachmentCount = (row) => parseAttachments(row).length

const openPreview = (row) => {
  previewUrls.value = parseAttachments(row)
  previewVisible.value = true
}

onMounted(loadData)
</script>

<style scoped>
.search-bar {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 14px;
}

.pager {
  margin-top: 14px;
  display: flex;
  justify-content: flex-end;
}

.preview-wrap {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
  margin-top: 8px;
}

.preview-item {
  width: 110px;
}

.thumb {
  width: 100px;
  height: 100px;
  border-radius: 8px;
  display: block;
}

.preview-dialog {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}

.dialog-image {
  width: 210px;
  height: 140px;
  border-radius: 8px;
}
</style>
