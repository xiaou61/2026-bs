<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <el-button @click="$router.back()">返回</el-button>
        <span style="margin-left: 15px">{{ post.categoryName }}</span>
      </template>
      <h2>{{ post.title }}</h2>
      <div class="meta">
        <el-avatar :size="32" :src="post.userAvatar" icon="UserFilled" />
        <span>{{ post.userName }}</span>
        <span>{{ post.createTime }}</span>
        <span>浏览 {{ post.viewCount }}</span>
        <el-button link type="primary" @click="handleLike">
          <el-icon><StarFilled v-if="post.liked" /><Star v-else /></el-icon>
          {{ post.likeCount || 0 }}
        </el-button>
      </div>
      <div class="content">{{ post.content }}</div>
    </el-card>
    <el-card style="margin-top: 15px">
      <template #header>回复 ({{ replies.length }})</template>
      <el-input v-model="replyContent" type="textarea" :rows="3" placeholder="写下你的回复..." />
      <el-button type="primary" style="margin-top: 10px" @click="handleReply">发表回复</el-button>
      <div class="reply-list">
        <div class="reply-item" v-for="r in replies" :key="r.id">
          <el-avatar :size="36" :src="r.userAvatar" icon="UserFilled" />
          <div class="reply-content">
            <div class="reply-user">
              {{ r.userName }}
              <span class="reply-time">{{ r.createTime }}</span>
              <span v-if="r.replyToUserName" class="reply-to">回复 @{{ r.replyToUserName }}</span>
            </div>
            <div>{{ r.content }}</div>
            <el-button link size="small" @click="replyTo(r)">回复</el-button>
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
import { Star, StarFilled } from '@element-plus/icons-vue'
import { getForumPostById, getForumReplies, addForumReply, likeForumPost } from '../../api'

const route = useRoute()
const post = ref({})
const replies = ref([])
const replyContent = ref('')
const replyToId = ref(null)

const loadData = async () => {
  const [postRes, replyRes] = await Promise.all([
    getForumPostById(route.params.id),
    getForumReplies(route.params.id)
  ])
  post.value = postRes.data
  replies.value = replyRes.data
}

const handleReply = async () => {
  if (!replyContent.value.trim()) return
  await addForumReply(route.params.id, { content: replyContent.value, replyToId: replyToId.value })
  ElMessage.success('回复成功')
  replyContent.value = ''
  replyToId.value = null
  const res = await getForumReplies(route.params.id)
  replies.value = res.data
}

const replyTo = (r) => {
  replyToId.value = r.id
  replyContent.value = `@${r.userName} `
}

const handleLike = async () => {
  await likeForumPost(route.params.id)
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
h2 { margin-bottom: 15px; }
.meta { display: flex; align-items: center; gap: 15px; color: #999; margin-bottom: 20px; }
.content { line-height: 1.8; white-space: pre-wrap; }
.reply-list { margin-top: 20px; }
.reply-item { display: flex; gap: 10px; padding: 15px 0; border-bottom: 1px solid #eee; }
.reply-user { font-weight: bold; margin-bottom: 5px; }
.reply-time { color: #999; font-weight: normal; margin-left: 10px; font-size: 12px; }
.reply-to { color: #409eff; margin-left: 10px; font-weight: normal; }
</style>
