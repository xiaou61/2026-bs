<template>
  <div class="qa-container">
    <el-card>
      <template #header>
        <div class="header-actions">
          <h3>问答广场</h3>
          <el-button type="primary" @click="goToAsk">
            <el-icon><Edit /></el-icon>
            我要提问
          </el-button>
        </div>
      </template>

      <el-form :inline="true" :model="searchForm">
        <el-form-item label="分类">
          <el-select v-model="searchForm.category" placeholder="全部分类" clearable>
            <el-option label="编程" value="编程" />
            <el-option label="数学" value="数学" />
            <el-option label="算法" value="算法" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="全部状态" clearable>
            <el-option label="待解决" value="pending" />
            <el-option label="已解决" value="resolved" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
        </el-form-item>
      </el-form>

      <div class="question-list">
        <div v-for="item in questionList" :key="item.id" class="question-item" @click="goToDetail(item.id)">
          <div class="question-header">
            <h4>{{ item.title }}</h4>
            <div class="tags">
              <el-tag v-if="item.bounty > 0" type="warning">悬赏 {{ item.bounty }}</el-tag>
              <el-tag :type="item.status === 'resolved' ? 'success' : 'info'">
                {{ item.status === 'resolved' ? '已解决' : '待解决' }}
              </el-tag>
              <el-tag>{{ item.category }}</el-tag>
            </div>
          </div>
          <div class="question-content">{{ item.content.substring(0, 100) }}...</div>
          <div class="question-meta">
            <span>{{ item.createTime }}</span>
          </div>
        </div>
      </div>

      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getQuestionList } from '@/api/qa'

const router = useRouter()
const searchForm = ref({
  category: '',
  status: ''
})

const questionList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const loadData = async () => {
  try {
    const res = await getQuestionList({
      page: currentPage.value,
      size: pageSize.value,
      ...searchForm.value
    })
    questionList.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error(error)
  }
}

const handleSearch = () => {
  currentPage.value = 1
  loadData()
}

const goToDetail = (id) => {
  router.push(`/qa/${id}`)
}

const goToAsk = () => {
  router.push('/qa/ask')
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.qa-container {
  max-width: 1400px;
  margin: 0 auto;
}

.header-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-actions h3 {
  margin: 0;
}

.question-list {
  margin-top: 20px;
}

.question-item {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 15px;
  cursor: pointer;
  transition: box-shadow 0.3s;
}

.question-item:hover {
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
}

.question-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.question-header h4 {
  margin: 0;
  color: #303133;
}

.tags {
  display: flex;
  gap: 8px;
}

.question-content {
  color: #606266;
  margin: 10px 0;
}

.question-meta {
  color: #909399;
  font-size: 12px;
}
</style>

