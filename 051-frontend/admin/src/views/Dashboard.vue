<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="4" v-for="(item, key) in statsConfig" :key="key">
        <el-card shadow="hover">
          <div class="stat-item">
            <div class="stat-value">{{ stats[key] || 0 }}</div>
            <div class="stat-label">{{ item.label }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../api'

const stats = ref({})
const statsConfig = {
  userCount: { label: '用户总数' },
  articleCount: { label: '文章总数' },
  questionCount: { label: '题目总数' },
  newsCount: { label: '资讯总数' },
  qaCount: { label: '问答总数' },
  todayAnswerCount: { label: '今日答题' }
}

onMounted(async () => {
  stats.value = await api.getStats()
})
</script>

<style scoped>
.stat-item { text-align: center; padding: 20px 0; }
.stat-value { font-size: 36px; font-weight: bold; color: #409EFF; }
.stat-label { color: #999; margin-top: 10px; }
</style>
