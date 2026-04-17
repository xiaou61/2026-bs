<template>
  <section class="page-shell" v-loading="loading">
    <el-card v-if="recipe" class="detail-card" shadow="never">
      <div class="hero-grid">
        <div class="detail-cover">
          <img :src="recipe.coverImage || fallbackImage" :alt="recipe.title" />
        </div>
        <div class="detail-content">
          <p class="eyebrow">Recipe Detail</p>
          <h1>{{ recipe.title }}</h1>
          <p class="lead">{{ recipe.description }}</p>
          <div class="stats">
            <el-tag>{{ recipe.seasons || '四季可用' }}</el-tag>
            <span>难度 {{ recipe.difficulty ?? '-' }}</span>
            <span>烹饪 {{ recipe.cookTime || 0 }} 分钟</span>
            <span>{{ recipe.views || 0 }} 次浏览</span>
            <span>{{ recipe.collects || 0 }} 次收藏</span>
          </div>
          <div class="info-card">
            <h3>调养重点</h3>
            <p>{{ recipe.efficacy || '暂无功效说明' }}</p>
          </div>
          <div class="info-card">
            <h3>营养亮点</h3>
            <p>{{ recipe.nutrition || '暂无营养说明' }}</p>
          </div>
          <div class="actions">
            <el-button type="primary" @click="handleCollect">收藏食谱</el-button>
            <el-button @click="router.push({ name: 'recipes' })">返回列表</el-button>
          </div>
        </div>
      </div>
    </el-card>

    <el-card v-if="recipe" class="detail-card" shadow="never">
      <h2>适用人群</h2>
      <p class="paragraph">{{ recipe.applicablePeople || '未设置' }}</p>
      <h2>留言交流</h2>
      <div class="comment-editor" v-if="userStore.isLoggedIn">
        <el-input
          v-model="commentForm.content"
          type="textarea"
          :rows="4"
          placeholder="分享你的食用体验、调理反馈或改良做法"
        />
        <div class="comment-actions">
          <el-rate v-model="commentForm.rating" :max="5" />
          <el-button type="primary" @click="submitComment">提交评论</el-button>
        </div>
      </div>
      <el-alert
        v-else
        title="登录后可以发表食疗体验和评分"
        type="info"
        :closable="false"
        show-icon
      />

      <div class="comment-list">
        <el-empty v-if="!comments.length" description="还没有评论，欢迎成为第一个分享经验的人" />
        <el-card v-for="comment in comments" :key="comment.id" class="comment-card" shadow="never">
          <div class="comment-top">
            <strong>用户 {{ comment.userId }}</strong>
            <span>{{ comment.createTime?.replace('T', ' ') }}</span>
          </div>
          <el-rate v-if="comment.rating" :model-value="comment.rating" disabled />
          <p>{{ comment.content }}</p>
        </el-card>
      </div>
    </el-card>
  </section>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../../stores/user'
import { collectRecipe, getRecipeDetail } from '../../api/recipe'
import request from '../../api/request'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const recipe = ref(null)
const comments = ref([])
const fallbackImage = 'https://via.placeholder.com/900x560?text=Herbal+Recipe'
const commentForm = reactive({
  content: '',
  rating: 5
})

const recipeId = Number(route.params.id)

const loadRecipe = async () => {
  loading.value = true
  try {
    const [detail, commentResp] = await Promise.all([
      getRecipeDetail(recipeId),
      request.get(`/comment/list/0/${recipeId}`)
    ])
    recipe.value = detail.data
    comments.value = commentResp.data || []
  } catch (error) {
    ElMessage.error(error?.message || '加载食谱详情失败')
  } finally {
    loading.value = false
  }
}

const handleCollect = async () => {
  if (!userStore.isLoggedIn) {
    ElMessage.warning('请先登录后再收藏')
    router.push({ name: 'login', query: { redirect: route.fullPath } })
    return
  }

  try {
    await collectRecipe(recipeId)
    ElMessage.success('已加入收藏')
    await loadRecipe()
  } catch (error) {
    ElMessage.error(error?.message || '收藏失败')
  }
}

const submitComment = async () => {
  if (!commentForm.content.trim()) {
    ElMessage.warning('请先填写评论内容')
    return
  }

  try {
    await request.post('/comment/create', {
      targetType: 0,
      targetId: recipeId,
      content: commentForm.content.trim(),
      rating: commentForm.rating
    })
    commentForm.content = ''
    commentForm.rating = 5
    ElMessage.success('评论已发布')
    await loadRecipe()
  } catch (error) {
    ElMessage.error(error?.message || '发表评论失败')
  }
}

onMounted(loadRecipe)
</script>

<style scoped>
.page-shell {
  max-width: 1120px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.detail-card {
  border-radius: 28px;
}

.hero-grid {
  display: grid;
  grid-template-columns: 1.1fr 1fr;
  gap: 28px;
}

.detail-cover {
  border-radius: 24px;
  overflow: hidden;
  min-height: 320px;
}

.detail-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.detail-content {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.eyebrow {
  letter-spacing: 0.18em;
  font-size: 12px;
  text-transform: uppercase;
  color: #7c8a62;
}

.detail-content h1 {
  font-size: 34px;
  color: #173d2e;
}

.lead,
.paragraph,
.info-card p {
  line-height: 1.8;
  color: #50645a;
}

.stats {
  display: flex;
  flex-wrap: wrap;
  gap: 10px 14px;
  color: #7b806f;
}

.info-card {
  padding: 18px;
  border-radius: 20px;
  background: #f6f1e8;
}

.info-card h3,
.detail-card h2 {
  margin-bottom: 10px;
  color: #183c2f;
}

.actions,
.comment-actions {
  display: flex;
  gap: 12px;
  align-items: center;
  flex-wrap: wrap;
}

.comment-editor {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin: 16px 0 20px;
}

.comment-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-top: 20px;
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

@media (max-width: 900px) {
  .hero-grid {
    grid-template-columns: 1fr;
  }
}
</style>
