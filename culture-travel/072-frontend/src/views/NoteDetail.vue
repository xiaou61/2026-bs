<template>
  <div class="note-detail" v-if="note">
    <el-card>
      <h1>{{ note.title }}</h1>
      <div class="meta">
        <span class="author"><el-icon><User /></el-icon>{{ note.nickname }}</span>
        <span class="time"><el-icon><Clock /></el-icon>{{ note.createTime }}</span>
        <span class="views"><el-icon><View /></el-icon>{{ note.viewCount }}次浏览</span>
      </div>
      <el-divider />
      <el-image v-if="note.coverImage" :src="note.coverImage" class="cover-img" fit="cover" />
      <div class="content" v-html="note.content"></div>
      <el-divider />
      <div class="actions">
        <el-button :type="isFavorite ? 'warning' : 'default'" @click="toggleFavorite">
          <el-icon><Star /></el-icon>{{ isFavorite ? '已收藏' : '收藏' }}
        </el-button>
      </div>
    </el-card>
    <el-card class="comment-card">
      <template #header><span>评论区</span></template>
      <div class="comment-form">
        <el-input v-model="commentContent" type="textarea" :rows="3" placeholder="写下你的评论..." />
        <el-button type="primary" @click="submitComment" style="margin-top:10px">发表评论</el-button>
      </div>
      <el-divider />
      <div v-if="comments.length">
        <div v-for="c in comments" :key="c.id" class="comment-item">
          <div class="comment-header">
            <span class="nickname">{{ c.nickname }}</span>
            <span class="time">{{ c.createTime }}</span>
          </div>
          <p class="comment-content">{{ c.content }}</p>
        </div>
      </div>
      <el-empty v-else description="暂无评论，快来抢沙发吧~" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getNoteDetail, getCommentsByNote, addComment, checkFavorite, addFavorite, removeFavorite } from '../api'
import { ElMessage } from 'element-plus'

const route = useRoute()
const note = ref(null)
const comments = ref([])
const isFavorite = ref(false)
const commentContent = ref('')

const loadData = async () => {
  const id = route.params.id
  const res = await getNoteDetail(id)
  note.value = res.data
  const commentRes = await getCommentsByNote(id)
  comments.value = commentRes.data || []
  const favRes = await checkFavorite(id, 'note')
  isFavorite.value = favRes.data
}

const submitComment = async () => {
  if (!commentContent.value.trim()) {
    ElMessage.warning('请输入评论内容')
    return
  }
  await addComment({ noteId: note.value.id, content: commentContent.value })
  ElMessage.success('评论成功')
  commentContent.value = ''
  const commentRes = await getCommentsByNote(note.value.id)
  comments.value = commentRes.data || []
}

const toggleFavorite = async () => {
  if (isFavorite.value) {
    await removeFavorite(note.value.id, 'note')
    isFavorite.value = false
    ElMessage.success('已取消收藏')
  } else {
    await addFavorite({ targetId: note.value.id, targetType: 'note' })
    isFavorite.value = true
    ElMessage.success('收藏成功')
  }
}

onMounted(() => loadData())
</script>

<style scoped>
h1 { margin-bottom: 15px; }
.meta { display: flex; gap: 20px; color: #999; font-size: 14px; }
.meta span { display: flex; align-items: center; gap: 4px; }
.cover-img { width: 100%; max-height: 500px; border-radius: 8px; margin-bottom: 20px; }
.content { line-height: 1.8; color: #333; white-space: pre-wrap; }
.actions { display: flex; gap: 15px; }
.comment-card { margin-top: 20px; }
.comment-form { margin-bottom: 20px; }
.comment-item { padding: 15px 0; border-bottom: 1px solid #eee; }
.comment-item:last-child { border-bottom: none; }
.comment-header { display: flex; justify-content: space-between; margin-bottom: 10px; }
.nickname { font-weight: bold; }
.time { color: #999; font-size: 12px; }
.comment-content { color: #666; }
</style>
