<template>
  <div class="home">
    <div class="banner">
      <div class="banner-content">
        <h1>小梦想，大力量</h1>
        <p>让每一份爱心都能点亮希望之光</p>
        <el-button type="primary" size="large" @click="$router.push('/projects')">
          浏览公益项目
        </el-button>
      </div>
    </div>

    <div class="container">
      <h2 class="section-title">最新公益项目</h2>
      <el-row :gutter="20">
        <el-col :span="8" v-for="project in projects" :key="project.id">
          <el-card class="project-card" @click="goToDetail(project.id)">
            <img :src="project.coverImage || '/cover-placeholder.svg'" class="project-cover">
            <div class="project-info">
              <h3>{{ project.title }}</h3>
              <p class="project-desc">{{ project.description }}</p>
              <div class="project-progress">
                <el-progress :percentage="calculateProgress(project)" />
                <div class="progress-info">
                  <span>已筹：¥{{ project.currentAmount }}</span>
                  <span>目标：¥{{ project.targetAmount }}</span>
                </div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getLatestProjects } from '@/api/project'

const router = useRouter()
const projects = ref([])

const loadProjects = async () => {
  try {
    const res = await getLatestProjects(6)
    projects.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const calculateProgress = (project) => {
  if (!project.targetAmount || project.targetAmount === 0) return 0
  return Math.round((project.currentAmount / project.targetAmount) * 100)
}

const goToDetail = (id) => {
  router.push(`/project/${id}`)
}

onMounted(() => {
  loadProjects()
})
</script>

<style scoped>
.home {
  width: 100%;
}

.banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 80px 20px;
  text-align: center;
}

.banner-content h1 {
  font-size: 48px;
  margin-bottom: 20px;
}

.banner-content p {
  font-size: 20px;
  margin-bottom: 30px;
}

.container {
  max-width: 1200px;
  margin: 40px auto;
  padding: 0 20px;
}

.section-title {
  font-size: 32px;
  margin-bottom: 30px;
  text-align: center;
}

.project-card {
  cursor: pointer;
  margin-bottom: 20px;
  transition: transform 0.3s;
}

.project-card:hover {
  transform: translateY(-5px);
}

.project-cover {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.project-info {
  padding: 15px 0;
}

.project-info h3 {
  font-size: 18px;
  margin-bottom: 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.project-desc {
  color: #666;
  font-size: 14px;
  margin-bottom: 15px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.project-progress {
  margin-top: 10px;
}

.progress-info {
  display: flex;
  justify-content: space-between;
  margin-top: 8px;
  font-size: 14px;
  color: #666;
}
</style>
