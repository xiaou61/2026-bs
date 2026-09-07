<template>
  <div class="practice-container">
    <el-card>
      <template #header>
        <h3>开始刷题</h3>
      </template>

      <el-form :inline="true" :model="form">
        <el-form-item label="学科">
          <el-select v-model="form.subject" placeholder="请选择学科">
            <el-option label="数学" value="数学" />
            <el-option label="编程" value="编程" />
            <el-option label="算法" value="算法" />
          </el-select>
        </el-form-item>
        <el-form-item label="难度">
          <el-select v-model="form.difficulty" placeholder="请选择难度">
            <el-option label="简单" value="简单" />
            <el-option label="中等" value="中等" />
            <el-option label="困难" value="困难" />
          </el-select>
        </el-form-item>
        <el-form-item label="题目数量">
          <el-input-number v-model="form.count" :min="1" :max="50" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="startPractice">开始</el-button>
        </el-form-item>
      </el-form>

      <div v-if="questions.length" style="margin-top: 30px">
        <div class="question-card">
          <div class="question-header">
            <span>题目 {{ currentIndex + 1 }} / {{ questions.length }}</span>
            <el-tag :type="getDifficultyType(currentQuestion.difficulty)">
              {{ currentQuestion.difficulty }}
            </el-tag>
          </div>
          <div class="question-content">
            <p><strong>题目：</strong>{{ currentQuestion.content }}</p>
            <div v-if="currentQuestion.options">
              <p v-for="(option, index) in currentQuestion.options.split('|')" :key="index">
                {{ option }}
              </p>
            </div>
            <el-input
              v-model="userAnswer"
              placeholder="请输入答案"
              style="margin-top: 20px"
            />
          </div>
          <div class="question-actions">
            <el-button @click="prevQuestion" :disabled="currentIndex === 0">上一题</el-button>
            <el-button type="primary" @click="submitAnswer">提交答案</el-button>
            <el-button @click="nextQuestion" :disabled="currentIndex === questions.length - 1">下一题</el-button>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { getRandomQuestions, addWrongQuestion } from '@/api/question'
import { ElMessage } from 'element-plus'

const form = ref({
  subject: '',
  difficulty: '',
  count: 10
})

const questions = ref([])
const currentIndex = ref(0)
const userAnswer = ref('')

const currentQuestion = ref({})

const getDifficultyType = (difficulty) => {
  const map = { '简单': 'success', '中等': 'warning', '困难': 'danger' }
  return map[difficulty] || ''
}

const startPractice = async () => {
  try {
    const res = await getRandomQuestions(form.value)
    questions.value = res.data
    if (questions.value.length > 0) {
      currentIndex.value = 0
      currentQuestion.value = questions.value[0]
      userAnswer.value = ''
    } else {
      ElMessage.warning('没有符合条件的题目')
    }
  } catch (error) {
    console.error(error)
  }
}

const submitAnswer = async () => {
  if (!userAnswer.value) {
    ElMessage.warning('请输入答案')
    return
  }

  const isCorrect = userAnswer.value.trim().toUpperCase() === currentQuestion.value.answer.trim().toUpperCase()
  
  if (isCorrect) {
    ElMessage.success('回答正确！')
  } else {
    ElMessage.error(`回答错误！正确答案是：${currentQuestion.value.answer}`)
    try {
      await addWrongQuestion({
        questionId: currentQuestion.value.id,
        userAnswer: userAnswer.value
      })
    } catch (error) {
      console.error(error)
    }
  }
}

const prevQuestion = () => {
  if (currentIndex.value > 0) {
    currentIndex.value--
    currentQuestion.value = questions.value[currentIndex.value]
    userAnswer.value = ''
  }
}

const nextQuestion = () => {
  if (currentIndex.value < questions.value.length - 1) {
    currentIndex.value++
    currentQuestion.value = questions.value[currentIndex.value]
    userAnswer.value = ''
  }
}
</script>

<style scoped>
.practice-container {
  max-width: 1000px;
  margin: 0 auto;
}

.question-card {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
}

.question-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  font-size: 16px;
  font-weight: bold;
}

.question-content {
  margin: 20px 0;
}

.question-actions {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 20px;
}
</style>

