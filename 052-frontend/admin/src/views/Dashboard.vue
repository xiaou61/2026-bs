<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <el-icon :size="40" color="#1890ff"><User /></el-icon>
            <div class="info">
              <div class="value">{{ stats.userCount }}</div>
              <div class="label">用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <el-icon :size="40" color="#52c41a"><VideoPlay /></el-icon>
            <div class="info">
              <div class="value">{{ stats.courseCount }}</div>
              <div class="label">课程总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <el-icon :size="40" color="#faad14"><Reading /></el-icon>
            <div class="info">
              <div class="value">{{ stats.learnCount }}</div>
              <div class="label">学习人次</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <el-icon :size="40" color="#eb2f96"><ChatDotRound /></el-icon>
            <div class="info">
              <div class="value">{{ stats.commentCount }}</div>
              <div class="label">评论总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../api'

const stats = ref({ userCount: 0, courseCount: 0, learnCount: 0, commentCount: 0 })

onMounted(async () => {
  stats.value = await api.getStats()
})
</script>

<style scoped>
.stat-card { display: flex; align-items: center; gap: 20px; }
.info .value { font-size: 28px; font-weight: bold; }
.info .label { color: #999; margin-top: 5px; }
</style>
