<template>
  <div class="qa-detail-container">
    <el-card v-if="question">
      <template #header>
        <div class="header">
          <div>
            <h2>{{ question.title }}</h2>
            <div class="tags">
              <el-tag v-if="question.bounty > 0" type="warning">悬赏 {{ question.bounty }}</el-tag>
              <el-tag :type="question.status === 'resolved' ? 'success' : 'info'">
                {{ question.status === 'resolved' ? '已解决' : '待解决' }}
              </el-tag>
              <el-tag>{{ question.category }}</el-tag>
            </div>
          </div>
        </div>
      </template>

      <div class="question-content">
        <p>{{ question.content }}</p>
        <div class="meta">
          <span>提问时间：{{ question.createTime }}</span>
        </div>
      </div>
    </el-card>

    <el-card style="margin-top: 20px">
      <template #header>
        <h3>回答列表 ({{ answers.length }})</h3>
      </template>

      <div class="answer-form">
        <el-input
          v-model="answerContent"
          type="textarea"
          :rows="4"
          placeholder="写下你的回答..."
        />
        <el-button type="primary" @click="handleAnswer" style="margin-top: 10px">提交回答</el-button>
      </div>

      <el-divider />

      <div v-if="answers.length">
        <div v-for="item in answers" :key="item.id" class="answer-item">
          <div class="answer-content">{{ item.content }}</div>
          <div class="answer-footer">
            <span class="answer-time">{{ item.createTime }}</span>
            <div class="answer-actions">
              <el-button link @click="handleLike(item.id)">
                <el-icon><Promotion /></el-icon>
                点赞 ({{ item.likeCount }})
              </el-button>
              <el-button
                v-if="!item.isAccepted && question.askerId === userStore.userInfo?.id"
                link
                type="success"
                @click="handleAccept(item.id)"
              >
                采纳
              </el-button>
              <el-tag v-if="item.isAccepted" type="success">已采纳</el-tag>
            </div>
          </div>
        </div>
      </div>
      <el-empty v-else description="暂无回答" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getQuestionDetail, getAnswers, addAnswer, acceptAnswer, likeAnswer } from '@/api/qa'
import { ElMessage } from 'element-plus'

const route = useRoute()
const userStore = useUserStore()
const question = ref(null)
const answers = ref([])
const answerContent = ref('')

const loadData = async () => {
  try {
    const res = await getQuestionDetail(route.params.id)
    question.value = res.data

    const answerRes = await getAnswers(route.params.id)
    answers.value = answerRes.data
  } catch (error) {
    console.error(error)
  }
}

const handleAnswer = async () => {
  if (!answerContent.value) {
    ElMessage.warning('请输入回答内容')
    return
  }

  try {
    await addAnswer({
      questionAnswerId: route.params.id,
      content: answerContent.value
    })
    ElMessage.success('回答成功')
    answerContent.value = ''
    loadData()
  } catch (error) {
    console.error(error)
  }
}

const handleAccept = async (answerId) => {
  try {
    await acceptAnswer({ answerId })
    ElMessage.success('采纳成功')
    loadData()
  } catch (error) {
    console.error(error)
  }
}

const handleLike = async (answerId) => {
  try {
    await likeAnswer(answerId)
    ElMessage.success('点赞成功')
    loadData()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.qa-detail-container {
  max-width: 1200px;
  margin: 0 auto;
}

.header h2 {
  margin: 0 0 10px 0;
}

.tags {
  display: flex;
  gap: 8px;
}

.question-content {
  padding: 20px 0;
}

.question-content p {
  color: #303133;
  font-size: 16px;
  line-height: 1.8;
}

.meta {
  color: #909399;
  font-size: 12px;
  margin-top: 20px;
}

.answer-form {
  margin-bottom: 20px;
}

.answer-item {
  padding: 20px 0;
  border-bottom: 1px solid #f0f0f0;
}

.answer-item:last-child {
  border-bottom: none;
}

.answer-content {
  color: #303133;
  line-height: 1.8;
  margin-bottom: 10px;
}

.answer-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.answer-time {
  color: #909399;
  font-size: 12px;
}

.answer-actions {
  display: flex;
  gap: 10px;
  align-items: center;
}
</style>

