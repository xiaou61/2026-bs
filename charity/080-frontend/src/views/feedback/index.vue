<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.feedbackType" placeholder="反馈类型" style="width: 150px" clearable>
          <el-option label="感谢信" value="letter" />
          <el-option label="留言" value="message" />
          <el-option label="日记" value="diary" />
        </el-select>
        <el-input v-model.number="query.childId" placeholder="儿童ID" style="width: 150px" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="feedbackType" label="类型">
          <template #default="{ row }">
            {{ row.feedbackType === 'letter' ? '感谢信' : row.feedbackType === 'message' ? '留言' : '日记' }}
          </template>
        </el-table-column>
        <el-table-column prop="childId" label="儿童ID" />
        <el-table-column prop="donorId" label="捐赠人ID" />
        <el-table-column prop="content" label="内容" show-overflow-tooltip />
        <el-table-column prop="createTime" label="创建时间" />
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

    <el-dialog v-model="dialogVisible" title="新增反馈" width="500px">
      <el-form :model="form" ref="formRef" label-width="100px">
        <el-form-item label="反馈类型" prop="feedbackType">
          <el-select v-model="form.feedbackType">
            <el-option label="感谢信" value="letter" />
            <el-option label="留言" value="message" />
            <el-option label="日记" value="diary" />
          </el-select>
        </el-form-item>
        <el-form-item label="儿童ID" prop="childId">
          <el-input v-model.number="form.childId" />
        </el-form-item>
        <el-form-item label="捐赠人ID" prop="donorId">
          <el-input v-model.number="form.donorId" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="5" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getFeedbackList, addFeedback } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  feedbackType: '',
  childId: null
})

const form = reactive({
  feedbackType: 'letter',
  childId: null,
  donorId: null,
  content: ''
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await getFeedbackList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, {
    feedbackType: 'letter',
    childId: null,
    donorId: null,
    content: ''
  })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await addFeedback(form)
  ElMessage.success('添加成功')
  dialogVisible.value = false
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
