<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.orderNo" placeholder="预约单号" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 140px" clearable>
        <el-option label="待支付" :value="0" />
        <el-option label="已支付" :value="1" />
        <el-option label="已确认" :value="2" />
        <el-option label="已完成" :value="3" />
        <el-option label="已取消" :value="4" />
        <el-option label="申诉中" :value="5" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="orderNo" label="预约单号" min-width="220" />
      <el-table-column prop="serviceTitle" label="维修项目" min-width="180" />
      <el-table-column prop="sellerName" label="服务方" width="120" />
      <el-table-column prop="quantity" label="预约数量" width="90" />
      <el-table-column prop="totalPrice" label="总价" width="100" />
      <el-table-column label="状态" width="110">
        <template #default="{ row }"><el-tag :type="tagType(row.status)">{{ statusText(row.status) }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="360" fixed="right">
        <template #default="{ row }">
          <el-button link @click="openDetail(row)">详情</el-button>
          <el-button link type="success" @click="pay(row)" :disabled="row.status !== 0">支付</el-button>
          <el-button link type="primary" @click="complete(row)" :disabled="row.status !== 2">确认完成</el-button>
          <el-button link type="danger" @click="cancel(row)" :disabled="![0,1].includes(row.status)">取消</el-button>
          <el-button link @click="openComplaint(row)" :disabled="![1,2,3].includes(row.status)">申诉</el-button>
          <el-button link @click="openReview(row)" :disabled="row.status !== 3">评价</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="detailVisible" title="预约详情" width="760px">
    <el-descriptions :column="2" border>
      <el-descriptions-item label="预约单号">{{ detail.orderNo }}</el-descriptions-item>
      <el-descriptions-item label="预约状态">{{ statusText(detail.status) }}</el-descriptions-item>
      <el-descriptions-item label="买家">{{ detail.buyerName }}</el-descriptions-item>
      <el-descriptions-item label="服务方">{{ detail.sellerName }}</el-descriptions-item>
      <el-descriptions-item label="维修项目">{{ detail.serviceTitle }}</el-descriptions-item>
      <el-descriptions-item label="预约数量">{{ detail.quantity }}</el-descriptions-item>
      <el-descriptions-item label="预约日期">{{ detail.appointmentDate || '-' }}</el-descriptions-item>
      <el-descriptions-item label="预约时间">{{ detail.appointmentTime || '-' }}</el-descriptions-item>
      <el-descriptions-item label="车牌号">{{ detail.plateNo || '-' }}</el-descriptions-item>
      <el-descriptions-item label="车型">{{ detail.vehicleModel || '-' }}</el-descriptions-item>
      <el-descriptions-item label="故障描述" :span="2">{{ detail.faultDesc || '-' }}</el-descriptions-item>
      <el-descriptions-item label="总价">¥{{ detail.totalPrice }}</el-descriptions-item>
      <el-descriptions-item label="支付时间">{{ detail.payTime || '-' }}</el-descriptions-item>
      <el-descriptions-item label="完成时间">{{ detail.finishTime || '-' }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ detail.createTime || '-' }}</el-descriptions-item>
      <el-descriptions-item label="备注" :span="2">{{ detail.remark || '-' }}</el-descriptions-item>
    </el-descriptions>
  </el-dialog>

  <el-dialog v-model="complaintVisible" title="发起申诉" width="520px">
    <el-form ref="complaintRef" :model="complaintForm" :rules="complaintRules" label-width="70px">
      <el-form-item label="类型" prop="type"><el-input v-model="complaintForm.type" maxlength="30" show-word-limit /></el-form-item>
      <el-form-item label="内容" prop="content"><el-input v-model="complaintForm.content" type="textarea" :rows="4" maxlength="1000" show-word-limit /></el-form-item>
      <el-form-item label="图片">
        <el-input v-model="complaintForm.images" />
        <el-upload style="margin-top: 8px" :show-file-list="false" :http-request="handleComplaintImageUpload" accept="image/png,image/jpeg,image/webp,image/gif">
          <el-button type="primary" plain>上传图片</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="complaintVisible = false">取消</el-button>
      <el-button type="primary" @click="submitComplaint">提交</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="reviewVisible" title="预约评价" width="520px">
    <el-form ref="reviewRef" :model="reviewForm" :rules="reviewRules" label-width="70px">
      <el-form-item label="评分" prop="rating">
        <el-rate v-model="reviewForm.rating" :max="5" />
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <el-input v-model="reviewForm.content" type="textarea" :rows="4" maxlength="1000" show-word-limit />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="reviewVisible = false">取消</el-button>
      <el-button type="primary" @click="submitReview">提交</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { nextTick, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addComplaint, addReview, cancelOrder, completeOrder, getMyOrders, payOrder, uploadFile } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, status: null, orderNo: '' })

const complaintVisible = ref(false)
const reviewVisible = ref(false)
const currentOrder = ref(null)
const detailVisible = ref(false)
const detail = ref({})
const complaintRef = ref()
const reviewRef = ref()
const complaintForm = reactive({ type: 'ORDER', content: '', images: '' })
const reviewForm = reactive({ rating: 5, content: '' })
const complaintRules = {
  type: [
    { required: true, message: '请输入申诉类型', trigger: 'blur' },
    { max: 30, message: '申诉类型不能超过30字符', trigger: 'blur' }
  ],
  content: [
    { required: true, message: '请输入申诉内容', trigger: 'blur' },
    { max: 1000, message: '申诉内容不能超过1000字符', trigger: 'blur' }
  ]
}
const reviewRules = {
  rating: [{ required: true, message: '请选择评分', trigger: 'change' }],
  content: [
    { required: true, message: '请输入评价内容', trigger: 'blur' },
    { max: 1000, message: '评价内容不能超过1000字符', trigger: 'blur' }
  ]
}

const statusText = (status) => ['待支付', '已支付', '已确认', '已完成', '已取消', '申诉中'][status] || '未知'
const tagType = (status) => ({ 0: 'warning', 1: 'primary', 2: 'success', 3: 'success', 4: 'info', 5: 'danger' }[status] || 'info')

const loadData = async () => {
  loading.value = true
  try {
    const res = await getMyOrders(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const pay = async (row) => {
  await payOrder(row.id)
  ElMessage.success('支付成功')
  loadData()
}

const complete = async (row) => {
  await completeOrder(row.id)
  ElMessage.success('预约已完成')
  loadData()
}

const cancel = async (row) => {
  await cancelOrder(row.id)
  ElMessage.success('预约已取消')
  loadData()
}

const openComplaint = (row) => {
  currentOrder.value = row
  Object.assign(complaintForm, { type: 'ORDER', content: '', images: '' })
  complaintVisible.value = true
  nextTick(() => complaintRef.value?.clearValidate())
}

const submitComplaint = async () => {
  if (!currentOrder.value) {
    ElMessage.warning('未找到申诉预约单')
    return
  }
  await complaintRef.value.validate()
  await addComplaint({
    orderId: currentOrder.value.id,
    type: complaintForm.type,
    content: complaintForm.content,
    images: complaintForm.images
  })
  ElMessage.success('申诉已提交')
  complaintVisible.value = false
  loadData()
}

const openReview = (row) => {
  currentOrder.value = row
  Object.assign(reviewForm, { rating: 5, content: '' })
  reviewVisible.value = true
  nextTick(() => reviewRef.value?.clearValidate())
}

const openDetail = (row) => {
  detail.value = row
  detailVisible.value = true
}

const submitReview = async () => {
  if (!currentOrder.value) {
    ElMessage.warning('未找到评价预约单')
    return
  }
  await reviewRef.value.validate()
  await addReview({
    orderId: currentOrder.value.id,
    rating: reviewForm.rating,
    content: reviewForm.content
  })
  ElMessage.success('评价成功')
  reviewVisible.value = false
  loadData()
}

const handleComplaintImageUpload = async (option) => {
  if (option.file.size > 5 * 1024 * 1024) {
    ElMessage.warning('图片大小不能超过5MB')
    option.onError(new Error('file too large'))
    return
  }
  try {
    const res = await uploadFile(option.file)
    complaintForm.images = res.data
    option.onSuccess(res, option.file)
    ElMessage.success('图片上传成功')
  } catch (e) {
    option.onError(e)
  }
}

onMounted(loadData)
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}
</style>

