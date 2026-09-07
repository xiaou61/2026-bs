<template>
  <el-card v-loading="loading">
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <h2>{{ experience.title }}</h2>
        <el-button type="primary" @click="handleLike">点赞 ({{ experience.likes }})</el-button>
      </div>
    </template>
    
    <el-descriptions :column="2" border>
      <el-descriptions-item label="类型">
        <el-tag>{{ experience.type === 'interview' ? '面经' : '笔试' }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="公司">{{ experience.companyName }}</el-descriptions-item>
      <el-descriptions-item label="岗位">{{ experience.jobTitle }}</el-descriptions-item>
      <el-descriptions-item label="浏览次数">{{ experience.views }}</el-descriptions-item>
    </el-descriptions>

    <el-divider />

    <div class="content">{{ experience.content }}</div>

    <el-divider />

    <div v-if="experience.tags">
      <el-tag v-for="tag in experience.tags.split(',')" :key="tag" style="margin-right: 10px">
        {{ tag }}
      </el-tag>
    </div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getExperienceById, likeExperience } from '@/api/experience'
import { ElMessage } from 'element-plus'

const route = useRoute()

const experience = ref({})
const loading = ref(false)

const loadExperience = async () => {
  loading.value = true
  try {
    const res = await getExperienceById(route.params.id)
    experience.value = res.data
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handleLike = async () => {
  try {
    await likeExperience(route.params.id)
    experience.value.likes++
    ElMessage.success('点赞成功')
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadExperience()
})
</script>

<style scoped>
.content {
  white-space: pre-wrap;
  line-height: 1.8;
  padding: 20px 0;
}
</style>

