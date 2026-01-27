<template>
  <div class="course-detail" v-if="course">
    <div class="course-header">
      <div class="cover" :style="{ backgroundImage: `url(${course.cover})` }"></div>
      <div class="info">
        <h1>{{ course.title }}</h1>
        <p class="desc">{{ course.description }}</p>
        <div class="meta">
          <span>讲师：{{ course.teacherName || '讲师' }}</span>
          <span>{{ course.learnCount }}人学习</span>
          <span>{{ course.chapterCount }}个章节</span>
          <span class="score"><el-icon><Star /></el-icon> {{ course.score }}分</span>
        </div>
        <div class="actions">
          <el-button type="primary" size="large" @click="startLearn">开始学习</el-button>
          <el-button size="large" @click="toggleFavorite">
            <el-icon><StarFilled v-if="isFavorite" /><Star v-else /></el-icon>
            {{ isFavorite ? '已收藏' : '收藏' }}
          </el-button>
        </div>
      </div>
    </div>

    <el-tabs v-model="activeTab">
      <el-tab-pane label="课程目录" name="chapters">
        <div class="chapters">
          <div class="chapter" v-for="chapter in chapters" :key="chapter.id">
            <div class="chapter-title">{{ chapter.title }}</div>
            <div class="videos">
              <div class="video-item" v-for="video in chapter.videos" :key="video.id" @click="playVideo(video)">
                <el-icon><VideoPlay /></el-icon>
                <span>{{ video.title }}</span>
                <span class="duration">{{ formatDuration(video.duration) }}</span>
                <el-tag v-if="video.isFree" size="small" type="success">免费</el-tag>
              </div>
            </div>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="课程评价" name="comments">
        <div class="comment-form" v-if="token">
          <el-rate v-model="commentForm.score" />
          <el-input v-model="commentForm.content" type="textarea" rows="3" placeholder="写下你的评价..." />
          <el-button type="primary" @click="submitComment">提交评价</el-button>
        </div>
        <div class="comments">
          <div class="comment-item" v-for="comment in comments" :key="comment.id">
            <el-avatar :size="40">{{ comment.username?.charAt(0) }}</el-avatar>
            <div class="comment-content">
              <div class="comment-header">
                <span class="name">{{ comment.username }}</span>
                <el-rate v-model="comment.score" disabled size="small" />
              </div>
              <p>{{ comment.content }}</p>
              <span class="time">{{ comment.createTime }}</span>
            </div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { courseApi, learnApi, favoriteApi, commentApi } from '../api'

const route = useRoute()
const router = useRouter()
const course = ref(null)
const chapters = ref([])
const comments = ref([])
const isFavorite = ref(false)
const activeTab = ref('chapters')
const token = computed(() => localStorage.getItem('token'))
const commentForm = reactive({ score: 5, content: '' })

onMounted(async () => {
  const id = route.params.id
  course.value = await courseApi.getDetail(id)
  chapters.value = await courseApi.getChapters(id)
  const commentRes = await commentApi.getList({ courseId: id, pageNum: 1, pageSize: 20 })
  comments.value = commentRes.records
  if (token.value) {
    isFavorite.value = await favoriteApi.check(id)
  }
})

const startLearn = async () => {
  if (!token.value) {
    router.push('/login')
    return
  }
  await learnApi.startLearn(course.value.id)
  const firstVideo = chapters.value[0]?.videos[0]
  if (firstVideo) {
    router.push(`/video/${firstVideo.id}`)
  }
}

const playVideo = video => {
  if (!token.value && !video.isFree) {
    router.push('/login')
    return
  }
  router.push(`/video/${video.id}`)
}

const toggleFavorite = async () => {
  if (!token.value) {
    router.push('/login')
    return
  }
  if (isFavorite.value) {
    await favoriteApi.remove(course.value.id)
    isFavorite.value = false
    ElMessage.success('已取消收藏')
  } else {
    await favoriteApi.add(course.value.id)
    isFavorite.value = true
    ElMessage.success('收藏成功')
  }
}

const submitComment = async () => {
  if (!commentForm.content.trim()) {
    ElMessage.warning('请输入评价内容')
    return
  }
  await commentApi.add({ courseId: course.value.id, ...commentForm })
  ElMessage.success('评价成功')
  commentForm.content = ''
  const res = await commentApi.getList({ courseId: course.value.id, pageNum: 1, pageSize: 20 })
  comments.value = res.records
}

const formatDuration = seconds => {
  const m = Math.floor(seconds / 60)
  const s = seconds % 60
  return `${m}:${s.toString().padStart(2, '0')}`
}
</script>

<style scoped>
.course-header { display: flex; gap: 30px; background: #fff; padding: 30px; border-radius: 8px; margin-bottom: 20px; }
.cover { width: 400px; height: 240px; background: #f5f5f5 center/cover no-repeat; border-radius: 8px; flex-shrink: 0; }
.info { flex: 1; }
.info h1 { margin-bottom: 15px; }
.desc { color: #666; margin-bottom: 15px; line-height: 1.6; }
.meta { display: flex; gap: 20px; color: #999; margin-bottom: 20px; }
.meta .score { color: #ff9800; }
.actions { display: flex; gap: 15px; }
.chapters { background: #fff; padding: 20px; border-radius: 8px; }
.chapter { margin-bottom: 20px; }
.chapter-title { font-weight: bold; padding: 10px 0; border-bottom: 1px solid #eee; }
.video-item { display: flex; align-items: center; gap: 10px; padding: 12px 15px; cursor: pointer; transition: background 0.3s; }
.video-item:hover { background: #f5f7fa; }
.video-item .duration { margin-left: auto; color: #999; }
.comment-form { background: #fff; padding: 20px; border-radius: 8px; margin-bottom: 20px; display: flex; flex-direction: column; gap: 15px; }
.comments { background: #fff; padding: 20px; border-radius: 8px; }
.comment-item { display: flex; gap: 15px; padding: 15px 0; border-bottom: 1px solid #f5f5f5; }
.comment-content { flex: 1; }
.comment-header { display: flex; align-items: center; gap: 10px; margin-bottom: 8px; }
.comment-header .name { font-weight: bold; }
.comment-item p { color: #666; margin-bottom: 8px; }
.comment-item .time { color: #999; font-size: 13px; }
</style>
