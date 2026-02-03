<template>
  <div class="page-container">
    <el-card v-if="work">
      <template #header>
        <div class="card-header">
          <span>{{ work.title }}</span>
          <el-button @click="$router.back()">返回</el-button>
        </div>
      </template>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="所属竞赛">{{ work.competitionTitle }}</el-descriptions-item>
        <el-descriptions-item label="作者">{{ work.authorName }}</el-descriptions-item>
        <el-descriptions-item label="字数">{{ work.wordCount }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="statusType[work.status]">{{ statusText[work.status] }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="最终得分">{{ work.finalScore || '-' }}</el-descriptions-item>
        <el-descriptions-item label="提交时间">{{ work.submitTime }}</el-descriptions-item>
      </el-descriptions>
      <div class="content-section">
        <h4>作品内容</h4>
        <div class="work-content" v-html="work.content?.replace(/\n/g, '<br>')"></div>
      </div>
      <div class="score-section" v-if="scoreDetail.records?.length">
        <h4>评分详情</h4>
        <el-table :data="scoreDetail.records" style="width: 100%">
          <el-table-column prop="judgeName" label="评委" width="150" />
          <el-table-column prop="totalScore" label="总分" width="100" />
          <el-table-column prop="comment" label="评语" />
          <el-table-column prop="createTime" label="评分时间" width="180" />
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getWork, getScoreDetail } from '../../api'

const route = useRoute()
const work = ref(null)
const scoreDetail = ref({})
const statusText = { 0: '待审核', 1: '已通过', 2: '已退回', 3: '已撤回' }
const statusType = { 0: 'warning', 1: 'success', 2: 'danger', 3: 'info' }

onMounted(async () => {
  const [workRes, scoreRes] = await Promise.all([
    getWork(route.params.id),
    getScoreDetail(route.params.id)
  ])
  work.value = workRes.data
  scoreDetail.value = scoreRes.data
})
</script>

<style scoped>
.page-container { padding: 10px; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.content-section, .score-section { margin-top: 20px; }
.content-section h4, .score-section h4 { margin-bottom: 15px; color: #333; }
.work-content { padding: 20px; background: #f5f7fa; border-radius: 4px; line-height: 1.8; }
</style>
