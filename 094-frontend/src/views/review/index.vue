<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.reviewStatus" placeholder="评价状态" clearable>
          <el-option label="WAIT_REPLY" value="WAIT_REPLY" />
          <el-option label="REPLIED" value="REPLIED" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="isCustomer" type="success" @click="handleAdd">提交评价</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="orderNo" label="订单号" min-width="170" />
        <el-table-column prop="shopName" label="门店" min-width="160" />
        <el-table-column prop="rating" label="评分" min-width="80" />
        <el-table-column prop="content" label="评价内容" min-width="220" show-overflow-tooltip />
        <el-table-column prop="reviewStatus" label="状态" min-width="110" />
        <el-table-column prop="replyName" label="回复人" min-width="100" />
        <el-table-column prop="replyContent" label="回复内容" min-width="220" show-overflow-tooltip />
        <el-table-column label="操作" width="160">
          <template #default="{ row }">
            <el-button v-if="!isCustomer && row.reviewStatus !== 'REPLIED'" link type="primary" @click="handleReply(row)">回复</el-button>
            <el-popconfirm v-if="!isCustomer" title="确认删除吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isCustomer ? '提交评价' : '回复评价'" width="560px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="90px">
        <template v-if="isCustomer">
          <el-form-item label="订单ID" prop="orderId">
            <el-input v-model="form.orderId" />
          </el-form-item>
          <el-form-item label="评分" prop="rating">
            <el-input-number v-model="form.rating" :min="1" :max="5" style="width: 100%" />
          </el-form-item>
          <el-form-item label="评价内容" prop="content">
            <el-input v-model="form.content" type="textarea" />
          </el-form-item>
        </template>
        <template v-else>
          <el-form-item label="回复内容">
            <el-input v-model="replyForm.replyContent" type="textarea" />
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
import { addReview, deleteReview, getReviewList, replyReview } from '../../api'
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
  reviewStatus: ''
})
const form = reactive({
  orderId: '',
  rating: 5,
  content: ''
})
const replyForm = reactive({
  replyContent: ''
})
const rules = {
  orderId: [{ required: true, message: '请输入订单ID', trigger: 'blur' }],
  rating: [{ required: true, message: '请选择评分', trigger: 'change' }],
  content: [{ required: true, message: '请输入评价内容', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getReviewList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { orderId: '', rating: 5, content: '' })
  dialogVisible.value = true
}

const handleReply = row => {
  currentId.value = row.id
  Object.assign(replyForm, { replyContent: row.replyContent || '' })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (isCustomer.value) {
    await formRef.value.validate()
    await addReview({
      ...form,
      orderId: Number(form.orderId)
    })
    ElMessage.success('评价已提交')
  } else {
    await replyReview(currentId.value, replyForm)
    ElMessage.success('回复成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteReview(id)
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
