<template>
  <div class="my-course">
    <h2>我的课程</h2>
    <div class="course-list" v-if="records.length">
      <div class="course-item" v-for="record in records" :key="record.id" @click="$router.push(`/course/${record.course.id}`)">
        <div class="cover" :style="{ backgroundImage: `url(${record.course?.cover})` }"></div>
        <div class="info">
          <h4>{{ record.course?.title }}</h4>
          <p>最近学习：{{ record.lastLearnTime }}</p>
        </div>
        <el-button type="primary" @click.stop="continueLean(record)">继续学习</el-button>
      </div>
    </div>
    <el-empty v-else description="暂无学习记录" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { learnApi } from '../api'

const router = useRouter()
const records = ref([])

onMounted(async () => {
  const res = await learnApi.getMyCourses({ pageNum: 1, pageSize: 50 })
  records.value = res.records
})

const continueLean = record => {
  if (record.videoId) {
    router.push(`/video/${record.videoId}`)
  } else {
    router.push(`/course/${record.course.id}`)
  }
}
</script>

<style scoped>
.my-course { background: #fff; padding: 20px; border-radius: 8px; }
h2 { margin-bottom: 20px; }
.course-item { display: flex; align-items: center; gap: 20px; padding: 15px; border-bottom: 1px solid #f5f5f5; cursor: pointer; }
.course-item:hover { background: #f5f7fa; }
.cover { width: 160px; height: 90px; background: #f5f5f5 center/cover no-repeat; border-radius: 4px; flex-shrink: 0; }
.info { flex: 1; }
.info h4 { margin-bottom: 8px; }
.info p { color: #999; font-size: 14px; }
</style>
