<template>
  <div class="question-container">
    <el-card>
      <template #header>
        <div class="header-actions">
          <h3>题库</h3>
          <div>
            <el-button type="primary" @click="goToPractice">开始刷题</el-button>
            <el-button type="warning" @click="goToWrong">错题本</el-button>
          </div>
        </div>
      </template>

      <el-form :inline="true" :model="searchForm">
        <el-form-item label="学科">
          <el-select v-model="searchForm.subject" placeholder="全部学科" clearable>
            <el-option label="数学" value="数学" />
            <el-option label="编程" value="编程" />
            <el-option label="算法" value="算法" />
          </el-select>
        </el-form-item>
        <el-form-item label="难度">
          <el-select v-model="searchForm.difficulty" placeholder="全部难度" clearable>
            <el-option label="简单" value="简单" />
            <el-option label="中等" value="中等" />
            <el-option label="困难" value="困难" />
          </el-select>
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="searchForm.type" placeholder="全部类型" clearable>
            <el-option label="选择题" value="选择题" />
            <el-option label="编程题" value="编程题" />
            <el-option label="算法题" value="算法题" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="questionList">
        <el-table-column prop="subject" label="学科" width="100">
          <template #default="{ row }">
            <el-tag>{{ row.subject }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="题目内容" min-width="300" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">
            <el-tag type="info">{{ row.type }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="difficulty" label="难度" width="100">
          <template #default="{ row }">
            <el-tag :type="getDifficultyType(row.difficulty)">{{ row.difficulty }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button link type="primary" @click="viewQuestion(row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>

    <el-dialog v-model="showDialog" title="题目详情" width="800px">
      <div v-if="currentQuestion">
        <p><strong>学科：</strong>{{ currentQuestion.subject }}</p>
        <p><strong>类型：</strong>{{ currentQuestion.type }}</p>
        <p><strong>难度：</strong>{{ currentQuestion.difficulty }}</p>
        <p><strong>题目：</strong>{{ currentQuestion.content }}</p>
        <p v-if="currentQuestion.options"><strong>选项：</strong></p>
        <div v-if="currentQuestion.options">
          <p v-for="(option, index) in currentQuestion.options.split('|')" :key="index">{{ option }}</p>
        </div>
        <p><strong>答案：</strong>{{ currentQuestion.answer }}</p>
        <p v-if="currentQuestion.analysis"><strong>解析：</strong>{{ currentQuestion.analysis }}</p>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getQuestionList } from '@/api/question'

const router = useRouter()
const searchForm = ref({
  subject: '',
  difficulty: '',
  type: ''
})

const questionList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const showDialog = ref(false)
const currentQuestion = ref(null)

const getDifficultyType = (difficulty) => {
  const map = { '简单': 'success', '中等': 'warning', '困难': 'danger' }
  return map[difficulty] || ''
}

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

const viewQuestion = (question) => {
  currentQuestion.value = question
  showDialog.value = true
}

const goToPractice = () => router.push('/question/practice')
const goToWrong = () => router.push('/question/wrong')

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.question-container {
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
</style>

