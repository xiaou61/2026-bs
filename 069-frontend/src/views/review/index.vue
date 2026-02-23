<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.score" placeholder="评分" clearable style="width: 120px">
          <el-option v-for="n in 5" :key="n" :label="`${n}分`" :value="n" />
        </el-select>
        <el-select v-model="query.status" placeholder="审核状态" clearable style="width: 140px">
          <el-option label="待审核" :value="0" />
          <el-option label="已通过" :value="1" />
          <el-option label="已驳回" :value="2" />
        </el-select>
        <el-input v-if="isAdmin" v-model="query.spotName" placeholder="景点名称" clearable style="width: 200px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="isUser" type="success" @click="openAddDialog">新增评价</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="orderNo" label="订单号" min-width="150" />
        <el-table-column v-if="isAdmin" prop="userName" label="用户" min-width="110" />
        <el-table-column prop="spotName" label="景点" min-width="130" />
        <el-table-column prop="score" label="评分" width="100">
          <template #default="{ row }">
            <el-rate v-model="row.score" disabled />
          </template>
        </el-table-column>
        <el-table-column label="审核状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="评价内容" min-width="220" show-overflow-tooltip />
        <el-table-column prop="replyContent" label="回复" min-width="200" show-overflow-tooltip />
        <el-table-column prop="createTime" label="评价时间" min-width="170" />
        <el-table-column v-if="isAdmin" label="操作" width="220">
          <template #default="{ row }">
            <el-button v-if="row.status === 0" link type="success" @click="handleAudit(row, 1)">通过</el-button>
            <el-button v-if="row.status === 0" link type="danger" @click="handleAudit(row, 2)">驳回</el-button>
            <el-button v-if="row.status === 1" link type="primary" @click="openReplyDialog(row)">回复</el-button>
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

    <el-dialog v-model="addDialogVisible" title="新增评价" width="560px">
      <el-form ref="addFormRef" :model="addForm" :rules="addRules" label-width="90px">
        <el-form-item label="订单" prop="orderId">
          <el-select v-model="addForm.orderId" filterable style="width: 100%">
            <el-option v-for="item in finishOrders" :key="item.id" :label="`${item.orderNo} - ${item.spotName}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="评分" prop="score">
          <el-rate v-model="addForm.score" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="addForm.content" type="textarea" :rows="4" maxlength="1000" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAdd">提交评价</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="replyDialogVisible" title="回复评价" width="520px">
      <el-form ref="replyFormRef" :model="replyForm" :rules="replyRules" label-width="90px">
        <el-form-item label="回复内容" prop="replyContent">
          <el-input v-model="replyForm.replyContent" type="textarea" :rows="4" maxlength="1000" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="replyDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleReply">保存回复</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addReview, getMyOrderPage, getMyReviewPage, getReviewPage, replyReview, updateReviewStatus } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isUser = computed(() => userStore.user?.role === 'USER')

const loading = ref(false)
const total = ref(0)
const tableData = ref([])
const addDialogVisible = ref(false)
const replyDialogVisible = ref(false)
const addFormRef = ref()
const replyFormRef = ref()
const finishOrders = ref([])

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  score: null,
  status: null,
  spotName: ''
})

const addForm = reactive({
  orderId: null,
  score: 5,
  content: ''
})

const replyForm = reactive({
  id: null,
  replyContent: ''
})

const addRules = {
  orderId: [{ required: true, message: '请选择订单', trigger: 'change' }],
  score: [{ required: true, message: '请选择评分', trigger: 'change' }],
  content: [{ required: true, message: '请输入评价内容', trigger: 'blur' }]
}

const replyRules = {
  replyContent: [{ required: true, message: '请输入回复内容', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = isAdmin.value ? await getReviewPage(query) : await getMyReviewPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const loadFinishOrders = async () => {
  const res = await getMyOrderPage({ pageNum: 1, pageSize: 200, status: 'FINISHED' })
  finishOrders.value = res.data.records || []
}

const openAddDialog = async () => {
  await loadFinishOrders()
  if (!finishOrders.value.length) {
    ElMessage.warning('暂无可评价的已完成订单')
    return
  }
  Object.assign(addForm, { orderId: null, score: 5, content: '' })
  addDialogVisible.value = true
}

const handleAdd = async () => {
  await addFormRef.value.validate()
  const res = await addReview(addForm)
  ElMessage.success(res.data?.status === 0 ? '评价已提交，待审核' : '评价成功')
  addDialogVisible.value = false
  loadData()
}

const openReplyDialog = (row) => {
  Object.assign(replyForm, { id: row.id, replyContent: row.replyContent || '' })
  replyDialogVisible.value = true
}

const handleReply = async () => {
  await replyFormRef.value.validate()
  await replyReview(replyForm)
  ElMessage.success('回复成功')
  replyDialogVisible.value = false
  loadData()
}

const handleAudit = async (row, status) => {
  await updateReviewStatus({ id: row.id, status })
  ElMessage.success(status === 1 ? '审核通过' : '已驳回')
  loadData()
}

const statusText = (status) => {
  if (status === 0) return '待审核'
  if (status === 1) return '已通过'
  if (status === 2) return '已驳回'
  return status
}

const statusType = (status) => {
  if (status === 0) return 'warning'
  if (status === 1) return 'success'
  if (status === 2) return 'danger'
  return ''
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
</style>
