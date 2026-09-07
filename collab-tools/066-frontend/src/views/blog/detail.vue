<template>
  <el-card>
    <template #header>
      <div class="head">
        <el-button text @click="$router.push('/blog')">返回列表</el-button>
        <span>文章详情</span>
      </div>
    </template>

    <div class="title">{{ detail.title }}</div>
    <div class="meta">
      <span>{{ detail.authorName || '未知作者' }}</span>
      <span>{{ detail.categoryName || '未分类' }}</span>
      <span>{{ detail.createTime }}</span>
      <span>浏览 {{ detail.viewCount || 0 }}</span>
      <span>评论 {{ detail.commentCount || 0 }}</span>
    </div>
    <div class="tags">
      <el-tag v-for="tag in detail.tagNames || []" :key="tag" effect="plain">{{ tag }}</el-tag>
    </div>
    <div class="content">{{ detail.content }}</div>

    <el-divider />

    <h3>发表评论</h3>
    <el-form ref="formRef" :model="commentForm" :rules="rules" label-width="0">
      <el-form-item prop="content"><el-input v-model="commentForm.content" type="textarea" :rows="4" maxlength="500" placeholder="请输入评论内容" /></el-form-item>
      <el-button type="primary" @click="submitComment">提交评论</el-button>
    </el-form>

    <el-divider />

    <h3>评论列表</h3>
    <el-empty v-if="!comments.length" description="暂无评论" />
    <div v-for="item in comments" :key="item.id" class="comment-item">
      <div class="comment-head">
        <span class="name">{{ item.userName || '匿名用户' }}</span>
        <span class="time">{{ item.createTime }}</span>
      </div>
      <div class="comment-content">{{ item.content }}</div>
      <div v-if="item.replyContent" class="reply">管理员回复：{{ item.replyContent }}</div>
    </div>

    <el-pagination v-model:current-page="commentQuery.pageNum" v-model:page-size="commentQuery.pageSize" :total="commentTotal" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadComments" />
  </el-card>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { addComment, getPostCommentPage, getPostDetail } from '../../api'

const route = useRoute()
const detail = ref({})
const comments = ref([])
const commentTotal = ref(0)
const formRef = ref()
const postId = Number(route.params.id)

const commentForm = reactive({ content: '' })
const commentQuery = reactive({ postId, pageNum: 1, pageSize: 10 })

const rules = {
  content: [{ required: true, message: '请输入评论内容', trigger: 'blur' }]
}

const loadDetail = async () => {
  const res = await getPostDetail(postId)
  detail.value = res.data || {}
}

const loadComments = async () => {
  const res = await getPostCommentPage(commentQuery)
  comments.value = res.data.records || []
  commentTotal.value = res.data.total || 0
}

const submitComment = async () => {
  await formRef.value.validate()
  await addComment({ postId, content: commentForm.content })
  ElMessage.success('评论已提交，审核通过后展示')
  commentForm.content = ''
}

onMounted(async () => {
  await loadDetail()
  await loadComments()
})
</script>

<style scoped>
.head {
  display: flex;
  align-items: center;
  gap: 12px;
}

.title {
  font-size: 28px;
  font-weight: 700;
  color: #0f172a;
  line-height: 1.4;
}

.meta {
  margin-top: 10px;
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
  color: #64748b;
  font-size: 13px;
}

.tags {
  margin-top: 12px;
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.content {
  margin-top: 18px;
  white-space: pre-wrap;
  line-height: 1.9;
  color: #1e293b;
}

.comment-item {
  border-bottom: 1px solid #e2e8f0;
  padding: 12px 0;
}

.comment-item:last-child {
  border-bottom: none;
}

.comment-head {
  display: flex;
  justify-content: space-between;
}

.name {
  color: #0f172a;
  font-weight: 600;
}

.time {
  color: #94a3b8;
  font-size: 12px;
}

.comment-content {
  margin-top: 8px;
  color: #334155;
  line-height: 1.7;
}

.reply {
  margin-top: 8px;
  color: #0f766e;
  background: #f0fdfa;
  padding: 8px 10px;
  border-radius: 6px;
}
</style>
