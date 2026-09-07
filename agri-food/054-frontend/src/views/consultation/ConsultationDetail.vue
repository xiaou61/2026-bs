<template>
  <div class="page-container">
    <el-card v-loading="loading">
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center">
          <h2>{{ detail.title }}</h2>
          <el-button @click="$router.back()">返回</el-button>
        </div>
      </template>
      <div>{{ detail.content }}</div>
      <el-divider />
      <h3>回答列表</h3>
      <div v-for="answer in answers" :key="answer.id" style="margin-bottom: 15px; padding: 10px; background: #f5f5f5; border-radius: 4px">
        <div>{{ answer.content }}</div>
        <div style="margin-top: 10px; color: #999; font-size: 12px">回答时间: {{ answer.createTime }}</div>
        <el-button link type="success" v-if="!answer.adopted" @click="adoptAnswer(answer.id)">采纳</el-button>
        <el-tag v-else type="success">已采纳</el-tag>
      </div>
      <el-divider />
      <h3>回答问题</h3>
      <el-input v-model="answerContent" type="textarea" :rows="3" />
      <el-button type="primary" style="margin-top: 10px" @click="submitAnswer">提交回答</el-button>
    </el-card>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getConsultationById, getAnswers, addAnswer, adoptAnswer as adoptAnswerApi } from '@/api/consultation'
import { ElMessage } from 'element-plus'
const route = useRoute()
const loading = ref(false), detail = ref({}), answers = ref([]), answerContent = ref('')
const loadDetail = async () => { loading.value = true; try { const res = await getConsultationById(route.params.id); detail.value = res.data; const answerRes = await getAnswers(route.params.id); answers.value = answerRes.data } finally { loading.value = false } }
const submitAnswer = async () => { await addAnswer(route.params.id, { content: answerContent.value }); ElMessage.success('回答成功'); answerContent.value = ''; loadDetail() }
const adoptAnswer = async (id) => { await adoptAnswerApi(id); ElMessage.success('采纳成功'); loadDetail() }
onMounted(() => loadDetail())
</script>