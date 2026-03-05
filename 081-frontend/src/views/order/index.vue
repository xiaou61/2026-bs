<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.orderNo" placeholder="工单号" style="width: 220px" />
        <el-select v-model="query.status" placeholder="状态" style="width: 140px" clearable>
          <el-option label="待受理" :value="0" />
          <el-option label="已派单" :value="1" />
          <el-option label="上门中" :value="2" />
          <el-option label="待支付" :value="3" />
          <el-option label="已完成" :value="4" />
          <el-option label="已取消" :value="5" />
        </el-select>
        <el-input v-model="query.contactPhone" placeholder="联系电话" style="width: 160px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增工单</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="orderNo" label="工单号" width="170" />
        <el-table-column prop="contactName" label="联系人" width="90" />
        <el-table-column prop="contactPhone" label="电话" width="130" />
        <el-table-column prop="brand" label="品牌" width="100" />
        <el-table-column prop="model" label="型号" width="110" />
        <el-table-column prop="faultDesc" label="故障描述" min-width="220" show-overflow-tooltip />
        <el-table-column label="故障图片" width="140">
          <template #default="{ row }">
            <el-image
              v-for="img in splitImages(row.images).slice(0, 2)"
              :key="img"
              :src="normalizeImage(img)"
              :preview-src-list="splitImages(row.images).map(normalizeImage)"
              style="width: 40px; height: 40px; margin-right: 6px"
              fit="cover"
            />
          </template>
        </el-table-column>
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="支付" width="90">
          <template #default="{ row }">
            <el-tag :type="row.payStatus === 1 ? 'success' : 'warning'">{{ row.payStatus === 1 ? '已支付' : '未支付' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="technicianId" label="技师ID" width="90" />
        <el-table-column prop="createTime" label="创建时间" width="170" />
        <el-table-column label="操作" width="320" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="success" @click="handleAssign(row)">派单</el-button>
            <el-button link type="warning" @click="handleStatus(row)">状态</el-button>
            <el-button v-if="row.status === 3 && row.payStatus !== 1" link type="success" @click="handlePay(row)">支付</el-button>
            <el-button link @click="viewProcess(row)">进度</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        @current-change="loadData"
        layout="total, prev, pager, next"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑工单' : '新增工单'" width="760px">
      <el-form :model="form" label-width="100px">
        <el-row :gutter="12">
          <el-col :span="12"><el-form-item label="用户ID"><el-input-number v-model="form.userId" :min="1" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="联系人"><el-input v-model="form.contactName" /></el-form-item></el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="12"><el-form-item label="联系电话"><el-input v-model="form.contactPhone" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="电器分类"><el-select v-model="form.categoryId" clearable><el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" /></el-select></el-form-item></el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="12"><el-form-item label="品牌"><el-input v-model="form.brand" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="型号"><el-input v-model="form.model" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="地址"><el-input v-model="form.address" /></el-form-item>
        <el-form-item label="故障描述"><el-input v-model="form.faultDesc" type="textarea" :rows="4" /></el-form-item>
        <el-form-item label="故障图片">
          <el-input v-model="form.images" type="textarea" :rows="2" placeholder="可填写多个图片URL，逗号分隔" />
          <div style="margin-top: 10px;">
            <el-upload
              action="/api/common/upload"
              :headers="uploadHeaders"
              :show-file-list="false"
              :on-success="handleUploadSuccess"
            >
              <el-button type="primary" plain>上传图片</el-button>
            </el-upload>
          </div>
        </el-form-item>
        <el-row :gutter="12">
          <el-col :span="8"><el-form-item label="紧急程度"><el-select v-model="form.urgency"><el-option label="普通" :value="0" /><el-option label="紧急" :value="1" /></el-select></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="工单状态"><el-select v-model="form.status"><el-option label="待受理" :value="0" /><el-option label="已派单" :value="1" /><el-option label="上门中" :value="2" /><el-option label="待支付" :value="3" /><el-option label="已完成" :value="4" /><el-option label="已取消" :value="5" /></el-select></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="支付状态"><el-select v-model="form.payStatus"><el-option label="未支付" :value="0" /><el-option label="已支付" :value="1" /></el-select></el-form-item></el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="assignVisible" title="工单派单" width="420px">
      <el-form label-width="90px">
        <el-form-item label="工单号"><el-input :model-value="assignForm.orderNo" disabled /></el-form-item>
        <el-form-item label="选择技师">
          <el-select v-model="assignForm.technicianId" style="width: 100%">
            <el-option v-for="item in technicianOptions" :key="item.id" :label="`${item.name}(${item.phone || ''})`" :value="item.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="assignVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAssign">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="statusVisible" title="更新状态" width="460px">
      <el-form label-width="90px">
        <el-form-item label="工单号"><el-input :model-value="statusForm.orderNo" disabled /></el-form-item>
        <el-form-item label="新状态">
          <el-select v-model="statusForm.status" style="width: 100%">
            <el-option label="待受理" :value="0" />
            <el-option label="已派单" :value="1" />
            <el-option label="上门中" :value="2" />
            <el-option label="待支付" :value="3" />
            <el-option label="已完成" :value="4" />
            <el-option label="已取消" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="说明"><el-input v-model="statusForm.content" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="statusVisible = false">取消</el-button>
        <el-button type="primary" @click="submitStatus">确定</el-button>
      </template>
    </el-dialog>

    <el-drawer v-model="processVisible" title="工单进度" size="40%">
      <el-timeline>
        <el-timeline-item v-for="item in processList" :key="item.id" :timestamp="item.createTime">
          <div><b>{{ item.nodeType }}</b> - {{ item.content }}</div>
          <div class="process-meta">操作人：{{ item.operatorRole || '-' }} #{{ item.operatorId || '-' }}</div>
        </el-timeline-item>
      </el-timeline>
    </el-drawer>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import {
  getOrderList,
  addOrder,
  updateOrder,
  deleteOrder,
  assignOrder,
  updateOrderStatus,
  userPayOrder,
  getProcessList,
  getCategoryPublicList,
  getTechnicianList
} from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const assignVisible = ref(false)
const statusVisible = ref(false)
const processVisible = ref(false)
const processList = ref([])
const categoryOptions = ref([])
const technicianOptions = ref([])
const uploadHeaders = computed(() => ({
  Authorization: localStorage.getItem('token') || ''
}))

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  orderNo: '',
  status: null,
  contactPhone: ''
})

const form = reactive({
  id: null,
  userId: 3,
  contactName: '',
  contactPhone: '',
  province: '四川省',
  city: '成都市',
  district: '高新区',
  address: '',
  categoryId: null,
  brand: '',
  model: '',
  faultDesc: '',
  images: '',
  urgency: 0,
  status: 0,
  payStatus: 0
})

const assignForm = reactive({
  orderId: null,
  orderNo: '',
  technicianId: null
})

const statusForm = reactive({
  orderId: null,
  orderNo: '',
  status: 0,
  content: '',
  operatorRole: 'admin'
})

const statusText = (status) => ({
  0: '待受理',
  1: '已派单',
  2: '上门中',
  3: '待支付',
  4: '已完成',
  5: '已取消'
}[status] || '未知')

const statusType = (status) => ({
  0: 'info',
  1: 'warning',
  2: 'primary',
  3: 'warning',
  4: 'success',
  5: 'danger'
}[status] || 'info')

const loadData = async () => {
  loading.value = true
  try {
    const res = await getOrderList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const loadOptions = async () => {
  const [categoryRes, techRes] = await Promise.all([
    getCategoryPublicList(),
    getTechnicianList({ pageNum: 1, pageSize: 100 })
  ])
  categoryOptions.value = categoryRes.data || []
  technicianOptions.value = techRes.data.records || []
}

const handleAdd = () => {
  Object.assign(form, {
    id: null,
    userId: 3,
    contactName: '',
    contactPhone: '',
    province: '四川省',
    city: '成都市',
    district: '高新区',
    address: '',
    categoryId: null,
    brand: '',
    model: '',
    faultDesc: '',
    images: '',
    urgency: 0,
    status: 0,
    payStatus: 0
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (form.id) {
    await updateOrder(form)
  } else {
    await addOrder(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleUploadSuccess = (res) => {
  if (res.code === 200 && res.data?.url) {
    form.images = form.images ? `${form.images},${res.data.url}` : res.data.url
    ElMessage.success('图片上传成功')
  }
}

const handleDelete = async (id) => {
  await deleteOrder(id)
  ElMessage.success('删除成功')
  loadData()
}

const handleAssign = (row) => {
  assignForm.orderId = row.id
  assignForm.orderNo = row.orderNo
  assignForm.technicianId = row.technicianId
  assignVisible.value = true
}

const submitAssign = async () => {
  await assignOrder({ orderId: assignForm.orderId, technicianId: assignForm.technicianId })
  ElMessage.success('派单成功')
  assignVisible.value = false
  loadData()
}

const handleStatus = (row) => {
  statusForm.orderId = row.id
  statusForm.orderNo = row.orderNo
  statusForm.status = row.status
  statusForm.content = ''
  statusVisible.value = true
}

const submitStatus = async () => {
  await updateOrderStatus(statusForm)
  ElMessage.success('状态更新成功')
  statusVisible.value = false
  loadData()
}

const handlePay = async (row) => {
  await userPayOrder(row.id, { content: '后台发起支付确认' })
  ElMessage.success('支付完成')
  loadData()
}

const viewProcess = async (row) => {
  const res = await getProcessList(row.id)
  processList.value = res.data || []
  processVisible.value = true
}

const splitImages = (images) => {
  if (!images) return []
  return images.split(',').filter(Boolean)
}

const normalizeImage = (url) => {
  if (!url) return ''
  if (url.startsWith('http://') || url.startsWith('https://')) return url
  return `http://localhost:8080${url}`
}

onMounted(async () => {
  await Promise.all([loadData(), loadOptions()])
})
</script>

<style scoped>
.page-container {
  padding: 10px;
}

.search-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}

.process-meta {
  color: #909399;
  margin-top: 4px;
  font-size: 12px;
}
</style>
