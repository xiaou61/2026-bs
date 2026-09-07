<template>
  <div class="page-container">
    <el-row :gutter="20">
      <el-col :span="14">
        <el-card v-if="work">
          <template #header>{{ work.title }}</template>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="所属竞赛">{{ work.competitionTitle }}</el-descriptions-item>
            <el-descriptions-item label="作者">{{ work.authorName }}</el-descriptions-item>
            <el-descriptions-item label="字数">{{ work.wordCount }}</el-descriptions-item>
            <el-descriptions-item label="提交时间">{{ work.submitTime }}</el-descriptions-item>
          </el-descriptions>
          <div class="work-content" v-html="work.content?.replace(/\n/g, '<br>')"></div>
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card>
          <template #header>评分</template>
          <el-form ref="formRef" label-width="100px">
            <div v-for="standard in standards" :key="standard.id" class="score-item">
              <div class="score-header">
                <span>{{ standard.name }}</span>
                <span class="max-score">满分: {{ standard.maxScore }}</span>
              </div>
              <el-slider v-model="scoreMap[standard.id]" :max="standard.maxScore" show-input />
            </div>
            <el-form-item label="综合评语">
              <el-input v-model="comment" type="textarea" :rows="4" placeholder="请输入评语" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSubmit">提交评分</el-button>
              <el-button @click="$router.back()">返回</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getWork, getScoreStandards, getMyScore, submitScore } from '../../api'

const route = useRoute()
const router = useRouter()
const work = ref(null)
const standards = ref([])
const scoreMap = reactive({})
const comment = ref('')

const handleSubmit = async () => {
  const scores = standards.value.map(s => ({
    standardId: s.id,
    score: scoreMap[s.id] || 0
  }))
  await submitScore({ workId: route.params.id, scores, comment: comment.value })
  ElMessage.success('评分成功')
  router.back()
}

onMounted(async () => {
  const workRes = await getWork(route.params.id)
  work.value = workRes.data
  const standardsRes = await getScoreStandards(work.value.competitionId)
  standards.value = standardsRes.data
  standards.value.forEach(s => { scoreMap[s.id] = 0 })
  try {
    const myScoreRes = await getMyScore(route.params.id)
    if (myScoreRes.data.scores?.length) {
      myScoreRes.data.scores.forEach(s => { scoreMap[s.standardId] = s.score })
    }
    if (myScoreRes.data.record) {
      comment.value = myScoreRes.data.record.comment || ''
    }
  } catch (e) {}
})
</script>

<style scoped>
.page-container { padding: 10px; }
.work-content { margin-top: 20px; padding: 20px; background: #f5f7fa; border-radius: 4px; line-height: 1.8; max-height: 500px; overflow-y: auto; }
.score-item { margin-bottom: 20px; }
.score-header { display: flex; justify-content: space-between; margin-bottom: 10px; }
.max-score { color: #999; font-size: 12px; }
</style>
