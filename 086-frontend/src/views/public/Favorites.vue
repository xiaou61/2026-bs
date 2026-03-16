<template>
  <div class="page-shell user-page">
    <div class="section-header">
      <div>
        <div class="section-eyebrow">我的收藏</div>
        <h1 class="display-title section-title">留下来的一定是你最喜欢的画面</h1>
      </div>
    </div>

    <div class="favorite-grid" v-if="list.length">
      <article class="favorite-card glass-card" v-for="item in list" :key="item.id">
        <img :src="item.coverUrl || item.imageUrl" :alt="item.title" @click="goDetail(item.id)">
        <div class="favorite-info">
          <div class="favorite-title">{{ item.title }}</div>
          <div class="favorite-meta">{{ item.resolution || '高清' }} · {{ item.downloadCount || 0 }} 下载</div>
          <div class="favorite-actions">
            <el-button type="primary" text @click="goDetail(item.id)">查看详情</el-button>
            <el-button type="danger" text @click="handleCancel(item.id)">取消收藏</el-button>
          </div>
        </div>
      </article>
    </div>

    <el-empty v-else description="还没有收藏任何壁纸" />
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { cancelFavorite, getFavoriteList } from '../../api'

const router = useRouter()
const list = ref([])

const goDetail = (id) => {
  router.push(`/detail/${id}`)
}

const loadData = async () => {
  const res = await getFavoriteList()
  list.value = res.data || []
}

const handleCancel = async (id) => {
  await cancelFavorite(id)
  ElMessage.success('已取消收藏')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.user-page {
  padding-top: 32px;
}

.section-title {
  margin: 10px 0 0;
  font-size: 42px;
}

.favorite-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 18px;
}

.favorite-card {
  overflow: hidden;
  border-radius: 24px;
}

.favorite-card img {
  width: 100%;
  height: 250px;
  object-fit: cover;
  display: block;
  cursor: pointer;
}

.favorite-info {
  padding: 18px;
}

.favorite-title {
  font-size: 18px;
  font-weight: 600;
}

.favorite-meta {
  margin-top: 8px;
  color: var(--text-sub);
}

.favorite-actions {
  margin-top: 14px;
  display: flex;
  justify-content: space-between;
}

@media (max-width: 900px) {
  .favorite-grid {
    grid-template-columns: 1fr;
  }
}
</style>
