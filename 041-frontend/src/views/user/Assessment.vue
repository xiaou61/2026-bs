<template>
  <div class="page-container">
    <el-card v-loading="loading">
      <h2>{{ scale.name }}</h2>
      <p class="desc">{{ scale.description }}</p>
      
      <div v-if="questions.length > 0" class="questions">
        <div v-for="(question, index) in questions" :key="question.id" class="question-item">
          <h4>{{ index + 1 }}. {{ question.content }}</h4>
          <el-radio-group v-model="answers[question.id]">
            <el-radio v-for="(option, idx) in parseOptions(question.options)" :key="idx" :value="idx + 1">
              {{ option }}
            </el-radio>
          </el-radio-group>
        </div>
      </div>

      <el-button type="primary" @click="handleSubmit" :loading="submitting" size="large" style="margin-top: 20px">
        提交测评
      </el-button>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { getScaleById, getQuestions, submitAssessment } from '@/api/assessment'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const loading = ref(false)
const submitting = ref(false)
const scale = ref({})
const questions = ref([])
const answers = ref({})

const loadData = async () => {
  loading.value = true
  try {
    const scaleRes = await getScaleById(route.params.id)
    scale.value = scaleRes.data
    
    const questionsRes = await getQuestions(route.params.id)
    questions.value = questionsRes.data
  } catch (error) {
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

const parseOptions = (optionsStr) => {
  if (!optionsStr) return []
  return optionsStr.split(';').map(opt => opt.split('=')[1])
}

const handleSubmit = async () => {
  const answersStr = JSON.stringify(answers.value)
  submitting.value = true
  try {
    await submitAssessment({ scaleId: route.params.id, answers: answersStr })
    ElMessage.success('提交成功')
    router.push('/assessments/my')
  } catch (error) {
    ElMessage.error('提交失败')
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.page-container {
  padding: 20px;
  min-height: 100vh;
  background: #f0f2f5;
}

.el-card {
  max-width: 800px;
  margin: 0 auto;
}

h2 {
  margin-bottom: 10px;
}

.desc {
  color: #666;
  margin-bottom: 30px;
}

.question-item {
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #eee;
}

.question-item h4 {
  margin-bottom: 15px;
  color: #333;
}

.el-radio-group {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
</style>
