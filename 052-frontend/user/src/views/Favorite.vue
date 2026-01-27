<template>
  <div class="favorite">
    <h2>我的收藏</h2>
    <div class="course-grid" v-if="favorites.length">
      <div class="course-card" v-for="fav in favorites" :key="fav.id" @click="$router.push(`/course/${fav.course.id}`)">
        <div class="course-cover" :style="{ backgroundImage: `url(${fav.course?.cover})` }"></div>
        <div class="course-info">
          <h4>{{ fav.course?.title }}</h4>
          <div class="meta">
            <span>{{ fav.course?.learnCount }}人学习</span>
            <span class="score">{{ fav.course?.score }}分</span>
          </div>
        </div>
      </div>
    </div>
    <el-empty v-else description="暂无收藏" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { favoriteApi } from '../api'

const favorites = ref([])

onMounted(async () => {
  const res = await favoriteApi.getList({ pageNum: 1, pageSize: 50 })
  favorites.value = res.records
})
</script>

<style scoped>
.favorite { background: #fff; padding: 20px; border-radius: 8px; }
h2 { margin-bottom: 20px; }
.course-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 20px; }
.course-card { background: #f5f7fa; border-radius: 8px; overflow: hidden; cursor: pointer; transition: all 0.3s; }
.course-card:hover { transform: translateY(-4px); box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
.course-cover { height: 160px; background: #e0e0e0 center/cover no-repeat; }
.course-info { padding: 15px; }
.course-info h4 { margin-bottom: 8px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.meta { display: flex; justify-content: space-between; font-size: 13px; color: #999; }
.score { color: #ff9800; }
</style>
