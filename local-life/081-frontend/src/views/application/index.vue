<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.applyStatus" placeholder="申请状态" style="width: 150px" clearable>
          <el-option label="待审核" :value="0" />
          <el-option label="已通过" :value="1" />
          <el-option label="已拒绝" :value="2" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增申请</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="childId" label="儿童ID" />
        <el-table-column prop="applyReason" label="申请理由" show-overflow-tooltip />
        <el-table-column prop="requiredAmount" label="所需金额" />
        <el-table-column prop="applyStatus" label="申请状态">
          <template #default="{ row }">
            <el-tag :type="row.applyStatus === 1 ? 'success' : row.applyStatus === 2 ? 'danger' : 'warning'">
              {{ row.applyStatus === 0 ? '待审核' : row.applyStatus === 1 ? '已通过' : '已拒绝' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="reviewComment" label="审核意见" show-overflow-tooltip />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleReview(row)" v-if="row.applyStatus === 0">审核</el-button>
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

    <el-dialog v-model="dialogVisible" title="新增申请" width="500px">
      <el-form :model="form" ref="formRef" label-width="100px">
        <el-form-item label="儿童ID" prop="childId">
          <el-input v-model.number="form.childId" />
        </el-form-item>
        <el-form-item label="申请理由" prop="applyReason">
          <el-input v-model="form.applyReason" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="所需金额" prop="requiredAmount">
          <el-input v-model.number="form.requiredAmount" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="reviewVisible" title="审核申请" width="500px">
      <el-form :model="reviewForm" label-width="100px">
        <el-form-item label="审核结果">
          <el-radio-group v-model="reviewForm.applyStatus">
            <el-radio :label="1">通过</el-radio>
            <el-radio :label="2">拒绝</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核意见">
          <el-input v-model="reviewForm.reviewComment" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="reviewVisible = false">取消</el-button>
        <el-button type="primary" @click="handleReviewSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getApplicationList, submitApplication, reviewApplication } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const reviewVisible = ref(false)
const formRef = ref()

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  applyStatus: null
})

const form = reactive({
  childId: null,
  applyReason: '',
  requiredAmount: null
})

const reviewForm = reactive({
  id: null,
  applyStatus: 1,
  reviewComment: ''
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await getApplicationList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, {
    childId: null,
    applyReason: '',
    requiredAmount: null
  })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await submitApplication(form)
  ElMessage.success('提交成功')
  dialogVisible.value = false
  loadData()
}

const handleReview = (row) => {
  reviewForm.id = row.id
  reviewForm.applyStatus = 1
  reviewForm.reviewComment = ''
  reviewVisible.value = true
}

const handleReviewSubmit = async () => {
  await reviewApplication(reviewForm)
  ElMessage.success('审核成功')
  reviewVisible.value = false
  loadData()
}

onMounted(loadData)
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
</style>
