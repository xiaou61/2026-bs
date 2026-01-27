<template>
  <div class="home">
    <el-carousel height="300px" v-if="banners.length">
      <el-carousel-item v-for="item in banners" :key="item.id">
        <div class="banner-item" :style="{ backgroundImage: `url(${item.image})` }">
          <h3>{{ item.title }}</h3>
        </div>
      </el-carousel-item>
    </el-carousel>

    <div class="section">
      <div class="section-header">
        <h2>课程分类</h2>
      </div>
      <div class="categories">
        <div class="category-item" v-for="cat in categories" :key="cat.id" @click="$router.push(`/courses?categoryId=${cat.id}`)">
          <el-icon :size="32"><Reading /></el-icon>
          <span>{{ cat.name }}</span>
        </div>
      </div>
    </div>

    <div class="section">
      <div class="section-header">
        <h2>推荐课程</h2>
        <el-button link type="primary" @click="$router.push('/courses?orderBy=score')">查看更多</el-button>
      </div>
      <div class="course-grid">
        <div class="course-card" v-for="course in recommend" :key="course.id" @click="$router.push(`/course/${course.id}`)">
          <div class="course-cover" :style="{ backgroundImage: `url(${course.cover})` }"></div>
          <div class="course-info">
            <h4>{{ course.title }}</h4>
            <p class="teacher">{{ course.teacherName || '讲师' }}</p>
            <div class="meta">
              <span><el-icon><User /></el-icon> {{ course.learnCount }}人学习</span>
              <span class="score">{{ course.score }}分</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="section">
      <div class="section-header">
        <h2>热门课程</h2>
        <el-button link type="primary" @click="$router.push('/courses?orderBy=hot')">查看更多</el-button>
      </div>
      <div class="course-grid">
        <div class="course-card" v-for="course in hot" :key="course.id" @click="$router.push(`/course/${course.id}`)">
          <div class="course-cover" :style="{ backgroundImage: `url(${course.cover})` }"></div>
          <div class="course-info">
            <h4>{{ course.title }}</h4>
            <p class="teacher">{{ course.teacherName || '讲师' }}</p>
            <div class="meta">
              <span><el-icon><User /></el-icon> {{ course.learnCount }}人学习</span>
              <span class="score">{{ course.score }}分</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { courseApi } from '../api'

const banners = ref([])
const categories = ref([])
const recommend = ref([])
const hot = ref([])

onMounted(async () => {
  banners.value = await courseApi.getBanners()
  categories.value = await courseApi.getCategories()
  recommend.value = await courseApi.getRecommend()
  hot.value = await courseApi.getHot()
})
</script>

<style scoped>
.banner-item { height: 100%; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); display: flex; align-items: center; justify-content: center; background-size: cover; }
.banner-item h3 { color: #fff; font-size: 32px; }
.section { margin-top: 30px; }
.section-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.section-header h2 { font-size: 20px; }
.categories { display: flex; gap: 20px; flex-wrap: wrap; }
.category-item { display: flex; flex-direction: column; align-items: center; gap: 8px; padding: 20px 30px; background: #fff; border-radius: 8px; cursor: pointer; transition: all 0.3s; }
.category-item:hover { transform: translateY(-4px); box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
.course-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 20px; }
.course-card { background: #fff; border-radius: 8px; overflow: hidden; cursor: pointer; transition: all 0.3s; }
.course-card:hover { transform: translateY(-4px); box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
.course-cover { height: 160px; background: #f5f5f5 center/cover no-repeat; }
.course-info { padding: 15px; }
.course-info h4 { margin-bottom: 8px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.teacher { color: #666; font-size: 14px; margin-bottom: 8px; }
.meta { display: flex; justify-content: space-between; font-size: 13px; color: #999; }
.score { color: #ff9800; }
</style>
