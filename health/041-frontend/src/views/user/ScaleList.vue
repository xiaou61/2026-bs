<template>
  <div class="page-container">
    <el-page-header @back="$router.back()" title="返回">
      <template #content>
        <span class="page-title">心理测评量表</span>
      </template>
    </el-page-header>

    <el-row :gutter="20" style="margin-top: 20px" v-loading="loading">
      <el-col :span="8" v-for="scale in scales" :key="scale.id">
        <el-card shadow="hover" class="scale-card">
          <h3>{{ scale.name }}</h3>
          <p class="category">{{ scale.category }}</p>
          <p class="desc">{{ scale.description }}</p>
          <div class="info">
            <span>题目数：{{ scale.questionCount }}</span>
            <span>时长：{{ scale.duration }}分钟</span>
          </div>
          <el-button type="primary" @click="startAssessment(scale.id)" style="width: 100%">
            开始测评
          </el-button>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getScales } from '@/api/assessment'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loading = ref(false)
const scales = ref([])

const loadScales = async () => {
  loading.value = true
  try {
    const res = await getScales()
    scales.value = res.data
  } catch (error) {
    ElMessage.error('加载量表失败')
  } finally {
    loading.value = false
  }
}

const startAssessment = (scaleId) => {
  router.push(`/assessment/${scaleId}`)
}

onMounted(() => {
  loadScales()
})
</script>

<style scoped>
.page-container {
  padding: 20px;
  min-height: 100vh;
  background: #f0f2f5;
}

.page-title {
  font-size: 20px;
  font-weight: bold;
}

.scale-card {
  margin-bottom: 20px;
}

.scale-card h3 {
  margin-bottom: 10px;
  color: #333;
}

.category {
  color: #409eff;
  font-size: 14px;
  margin-bottom: 10px;
}

.desc {
  color: #666;
  font-size: 14px;
  margin-bottom: 15px;
  min-height: 40px;
}

.info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
  font-size: 14px;
  color: #999;
}
</style>
