<template>
  <el-card shadow="hover">
    <h3>后端健康检查</h3>
    <el-button type="primary" :loading="loading" @click="check">调用 /api/health</el-button>
    <div class="result" v-if="result">返回：{{ result }}</div>
  </el-card>
</template>

<script setup>
import request from '../utils/request'
import { ref } from 'vue'

const loading = ref(false)
const result = ref('')

const check = async () => {
  loading.value = true
  try {
    const { data } = await request.get('/health')
    result.value = JSON.stringify(data)
  } catch (e) {
    result.value = e?.message || '请求失败'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.result { margin-top: 12px; color: #1f2d3d; word-break: break-all; }
</style>
