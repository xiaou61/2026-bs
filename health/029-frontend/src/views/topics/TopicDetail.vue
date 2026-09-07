<template>
  <section class="page-shell" v-loading="loading">
    <el-card v-if="topic" class="detail-card" shadow="never">
      <p class="eyebrow">{{ topic.category || '综合交流' }}</p>
      <h1>{{ topic.title }}</h1>
      <div class="meta">
        <span>{{ topic.views || 0 }} 浏览</span>
        <span>{{ topic.replies || 0 }} 回复</span>
        <span>{{ topic.createTime?.replace('T', ' ') }}</span>
      </div>
      <div class="tag-row" v-if="topic.tags">
        <el-tag v-for="tag in parseTags(topic.tags)" :key="tag" effect="light">{{ tag }}</el-tag>
      </div>
      <p class="content">{{ topic.content }}</p>
    </el-card>

    <el-card class="detail-card" shadow="never">
      <h2>参与讨论</h2>
      <div v-if="userStore.isLoggedIn" class="comment-editor">
        <el-input
          v-model="commentContent"
          type="textarea"
          :rows="4"
          placeholder="写下你的观点、经验或补充建议"
        />
        <div class="editor-actions">
          <el-button type="primary" @click="submitComment">发布评论</el-button>
        </div>
      </div>
      <el-alert
        v-else
        title="登录后可以参与评论"
        type="info"
        :closable="false"
        show-icon
      />

      <div class="comment-list">
        <el-empty v-if="!comments.length" description="还没有评论，欢迎参与交流" />
        <el-card v-for="comment in comments" :key="comment.id" class="comment-card" shadow="never">
          <div class="comment-top">
            <strong>用户 {{ comment.userId }}</strong>
            <span>{{ comment.createTime?.replace('T', ' ') }}</span>
          </div>
          <p>{{ comment.content }}</p>
        </el-card>
      </div>
    </el-card>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../../stores/user'
import { getTopicComments, getTopicDetail } from '../../api/topic'
import request from '../../api/request'

const route = useRoute()
const userStore = useUserStore()
const loading = ref(false)
const topic = ref(null)
const comments = ref([])
const commentContent = ref('')

const parseTags = (value = '') => value.split(/[,\s，]+/).filter(Boolean)

const loadTopic = async () => {
  loading.value = true
  try {
    const topicId = Number(route.params.id)
    const [detail, commentResp] = await Promise.all([
      getTopicDetail(topicId),
      getTopicComments(topicId)
    ])
    topic.value = detail.data
    comments.value = commentResp.data || []
  } catch (error) {
    ElMessage.error(error?.message || '加载话题失败')
  } finally {
    loading.value = false
  }
}

const submitComment = async () => {
  if (!commentContent.value.trim()) {
    ElMessage.warning('请先填写评论内容')
    return
  }

  try {
    await request.post('/comment/create', {
      targetType: 1,
      targetId: Number(route.params.id),
      content: commentContent.value.trim()
    })
    commentContent.value = ''
    ElMessage.success('评论成功')
    await loadTopic()
  } catch (error) {
    ElMessage.error(error?.message || '评论失败')
  }
}

onMounted(loadTopic)
</script>

<style scoped>
.page-shell {
  max-width: 980px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.detail-card {
  border-radius: 24px;
}

.eyebrow {
  color: #7b806f;
  letter-spacing: 0.16em;
  text-transform: uppercase;
  font-size: 12px;
  margin-bottom: 10px;
}

h1,
h2 {
  color: #183c2f;
}

.meta {
  display: flex;
  flex-wrap: wrap;
  gap: 14px;
  color: #7b806f;
  margin: 12px 0;
}

.tag-row {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 16px;
}

.content {
  color: #55655c;
  line-height: 1.9;
  white-space: pre-wrap;
}

.comment-editor {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin: 16px 0;
}

.editor-actions {
  display: flex;
  justify-content: flex-end;
}

.comment-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-top: 16px;
}

.comment-card {
  border-radius: 18px;
  background: #fbfaf6;
}

.comment-top {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  margin-bottom: 8px;
  color: #7b806f;
}
</style>
