<template>
  <div class="review-list">
    <el-card>
      <template #header>
        <div class="header">
          <span>游客评价</span>
          <el-button type="primary" @click="openReview">发表评价</el-button>
        </div>
      </template>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="spotName" label="景点名称" width="180" />
        <el-table-column prop="nickname" label="评价用户" width="120" />
        <el-table-column prop="rating" label="评分" width="180">
          <template #default="{ row }"><el-rate v-model="row.rating" disabled /></template>
        </el-table-column>
        <el-table-column prop="content" label="评价内容" show-overflow-tooltip />
        <el-table-column prop="createTime" label="评价时间" width="170" />
      </el-table>
      <el-pagination v-if="total > 0" background layout="prev, pager, next" :total="total" :page-size="query.pageSize" v-model:current-page="query.pageNum" @current-change="loadData" class="pagination" />
    </el-card>
    <el-dialog v-model="reviewDialog" title="发表评价" width="500px">
      <el-form :model="form" label-width="80px" :rules="rules" ref="formRef">
        <el-form-item label="选择景点" prop="spotId">
          <el-select v-model="form.spotId" placeholder="请选择景点" filterable>
            <el-option v-for="s in spotOptions" :key="s.id" :label="s.name" :value="s.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="评分" prop="rating">
          <el-rate v-model="form.rating" />
        </el-form-item>
        <el-form-item label="评价内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="4" placeholder="请输入评价内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="reviewDialog = false">取消</el-button>
        <el-button type="primary" @click="submitReview">提交评价</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getReviewList, createReview, getSpotList } from '../api'
import { ElMessage } from 'element-plus'

const query = ref({ pageNum: 1, pageSize: 10 })
const list = ref([])
const total = ref(0)
const loading = ref(false)
const reviewDialog = ref(false)
const formRef = ref()
const form = ref({ spotId: null, rating: 5, content: '' })
const spotOptions = ref([])

const rules = {
  spotId: [{ required: true, message: '请选择景点', trigger: 'change' }],
  content: [{ required: true, message: '请输入评价内容', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  const res = await getReviewList(query.value)
  list.value = res.data.records || []
  total.value = res.data.total || 0
  loading.value = false
}

const openReview = async () => {
  form.value = { spotId: null, rating: 5, content: '' }
  const res = await getSpotList({ pageNum: 1, pageSize: 100 })
  spotOptions.value = res.data.records || []
  reviewDialog.value = true
}

const submitReview = async () => {
  await formRef.value.validate()
  await createReview(form.value)
  ElMessage.success('评价成功')
  reviewDialog.value = false
  loadData()
}

onMounted(() => loadData())
</script>

<style scoped>
.header { display: flex; justify-content: space-between; align-items: center; }
.pagination { margin-top: 20px; justify-content: center; }
</style>
