<template>
  <div class="course-list">
    <div class="filter-bar">
      <div class="categories">
        <span :class="{ active: !query.categoryId }" @click="selectCategory(null)">全部</span>
        <span v-for="cat in categories" :key="cat.id" :class="{ active: query.categoryId == cat.id }" @click="selectCategory(cat.id)">{{ cat.name }}</span>
      </div>
      <div class="sort">
        <span :class="{ active: query.orderBy === 'latest' }" @click="selectOrder('latest')">最新</span>
        <span :class="{ active: query.orderBy === 'hot' }" @click="selectOrder('hot')">最热</span>
        <span :class="{ active: query.orderBy === 'score' }" @click="selectOrder('score')">评分</span>
      </div>
      <el-input v-model="query.keyword" placeholder="搜索课程" style="width: 200px" @keyup.enter="search" clearable @clear="search">
        <template #append><el-button @click="search"><el-icon><Search /></el-icon></el-button></template>
      </el-input>
    </div>

    <div class="course-grid">
      <div class="course-card" v-for="course in courses" :key="course.id" @click="$router.push(`/course/${course.id}`)">
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

    <div class="pagination">
      <el-pagination v-model:current-page="query.pageNum" :page-size="12" :total="total" layout="prev, pager, next" @current-change="loadData" />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { courseApi } from '../api'

const route = useRoute()
const categories = ref([])
const courses = ref([])
const total = ref(0)
const query = reactive({
  pageNum: 1,
  pageSize: 12,
  categoryId: route.query.categoryId || null,
  keyword: '',
  orderBy: route.query.orderBy || 'latest'
})

onMounted(async () => {
  categories.value = await courseApi.getCategories()
  loadData()
})

const loadData = async () => {
  const res = await courseApi.getList(query)
  courses.value = res.records
  total.value = res.total
}

const selectCategory = id => {
  query.categoryId = id
  query.pageNum = 1
  loadData()
}

const selectOrder = order => {
  query.orderBy = order
  query.pageNum = 1
  loadData()
}

const search = () => {
  query.pageNum = 1
  loadData()
}
</script>

<style scoped>
.filter-bar { background: #fff; padding: 20px; border-radius: 8px; margin-bottom: 20px; display: flex; flex-wrap: wrap; gap: 15px; align-items: center; }
.categories, .sort { display: flex; gap: 15px; }
.categories span, .sort span { padding: 6px 16px; cursor: pointer; border-radius: 4px; transition: all 0.3s; }
.categories span:hover, .sort span:hover { color: #409eff; }
.categories span.active, .sort span.active { background: #409eff; color: #fff; }
.sort { margin-left: auto; }
.course-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 20px; }
.course-card { background: #fff; border-radius: 8px; overflow: hidden; cursor: pointer; transition: all 0.3s; }
.course-card:hover { transform: translateY(-4px); box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
.course-cover { height: 160px; background: #f5f5f5 center/cover no-repeat; }
.course-info { padding: 15px; }
.course-info h4 { margin-bottom: 8px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.teacher { color: #666; font-size: 14px; margin-bottom: 8px; }
.meta { display: flex; justify-content: space-between; font-size: 13px; color: #999; }
.score { color: #ff9800; }
.pagination { margin-top: 20px; display: flex; justify-content: center; }
</style>
