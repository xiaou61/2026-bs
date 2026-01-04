&lt;template&gt;
  &lt;div class="home"&gt;
    &lt;div class="banner"&gt;
      &lt;div class="banner-content"&gt;
        &lt;h1&gt;小梦想，大力量&lt;/h1&gt;
        &lt;p&gt;让每一份爱心都能点亮希望之光&lt;/p&gt;
        &lt;el-button type="primary" size="large" @click="$router.push('/projects')"&gt;
          浏览公益项目
        &lt;/el-button&gt;
      &lt;/div&gt;
    &lt;/div&gt;

    &lt;div class="container"&gt;
      &lt;h2 class="section-title"&gt;最新公益项目&lt;/h2&gt;
      &lt;el-row :gutter="20"&gt;
        &lt;el-col :span="8" v-for="project in projects" :key="project.id"&gt;
          &lt;el-card class="project-card" @click="goToDetail(project.id)"&gt;
            &lt;img :src="project.coverImage || 'https://via.placeholder.com/300x200'" class="project-cover"&gt;
            &lt;div class="project-info"&gt;
              &lt;h3&gt;{{ project.title }}&lt;/h3&gt;
              &lt;p class="project-desc"&gt;{{ project.description }}&lt;/p&gt;
              &lt;div class="project-progress"&gt;
                &lt;el-progress :percentage="calculateProgress(project)" /&gt;
                &lt;div class="progress-info"&gt;
                  &lt;span&gt;已筹：¥{{ project.currentAmount }}&lt;/span&gt;
                  &lt;span&gt;目标：¥{{ project.targetAmount }}&lt;/span&gt;
                &lt;/div&gt;
              &lt;/div&gt;
            &lt;/div&gt;
          &lt;/el-card&gt;
        &lt;/el-col&gt;
      &lt;/el-row&gt;
    &lt;/div&gt;
  &lt;/div&gt;
&lt;/template&gt;

&lt;script setup&gt;
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getLatestProjects } from '@/api/project'

const router = useRouter()
const projects = ref([])

const loadProjects = async () =&gt; {
  try {
    const res = await getLatestProjects(6)
    projects.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const calculateProgress = (project) =&gt; {
  if (!project.targetAmount || project.targetAmount === 0) return 0
  return Math.round((project.currentAmount / project.targetAmount) * 100)
}

const goToDetail = (id) =&gt; {
  router.push(`/project/${id}`)
}

onMounted(() =&gt; {
  loadProjects()
})
&lt;/script&gt;

&lt;style scoped&gt;
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
&lt;/style&gt;
