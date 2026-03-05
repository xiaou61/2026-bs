<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <el-button @click="$router.back()">返回</el-button>
      </template>
      <h2>{{ news.title }}</h2>
      <div class="meta">
        <span>{{ news.authorName }}</span>
        <span>{{ news.createTime }}</span>
        <span>浏览 {{ news.viewCount }}</span>
      </div>
      <div class="content" v-html="news.content"></div>
    </el-card>
    <el-card style="margin-top: 15px">
      <template #header>评论 ({{ comments.length }})</template>
      <el-input v-model="commentContent" type="textarea" :rows="3" placeholder="写下你的评论..." />
      <el-button type="primary" style="margin-top: 10px" @click="handleComment">发表评论</el-button>
      <div class="comment-list">
        <div class="comment-item" v-for="c in comments" :key="c.id">
          <el-avatar :size="36" :src="c.userAvatar" icon="UserFilled" />
          <div class="comment-content">
            <div class="comment-user">{{ c.userName }} <span class="comment-time">{{ c.createTime }}</span></div>
            <div>{{ c.content }}</div>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getNewsById, getNewsComments, addNewsComment } from '../../api'

const route = useRoute()
const news = ref({})
const comments = ref([])
const commentContent = ref('')

const loadData = async () => {
  const [newsRes, commentsRes] = await Promise.all([
    getNewsById(route.params.id),
    getNewsComments(route.params.id)
  ])
  news.value = newsRes.data
  comments.value = commentsRes.data
}

const handleComment = async () => {
  if (!commentContent.value.trim()) return
  await addNewsComment(route.params.id, { content: commentContent.value })
  ElMessage.success('评论成功')
  commentContent.value = ''
  const res = await getNewsComments(route.params.id)
  comments.value = res.data
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
h2 { margin-bottom: 10px; }
.meta { color: #999; margin-bottom: 20px; display: flex; gap: 20px; }
.content { line-height: 1.8; }
.comment-list { margin-top: 20px; }
.comment-item { display: flex; gap: 10px; padding: 15px 0; border-bottom: 1px solid #eee; }
.comment-user { font-weight: bold; margin-bottom: 5px; }
.comment-time { color: #999; font-weight: normal; margin-left: 10px; font-size: 12px; }
</style>
