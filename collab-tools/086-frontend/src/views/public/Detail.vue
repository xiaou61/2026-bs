<template>
  <div class="detail-page page-shell" v-loading="loading">
    <template v-if="detail.wallpaper">
      <section class="detail-hero">
        <div class="preview-panel glass-card">
          <img :src="detail.wallpaper.imageUrl" :alt="detail.wallpaper.title">
        </div>
        <div class="info-panel glass-card">
          <div class="section-eyebrow">作品详情</div>
          <h1 class="display-title detail-title">{{ detail.wallpaper.title }}</h1>
          <p class="detail-sub">{{ detail.wallpaper.subtitle || detail.wallpaper.description }}</p>
          <div class="detail-meta">
            <span>{{ detail.wallpaper.wallpaperType === 'mobile' ? '手机壁纸' : '桌面壁纸' }}</span>
            <span>{{ detail.wallpaper.resolution || '高清' }}</span>
            <span>{{ detail.wallpaper.downloadCount || 0 }} 下载</span>
            <span>{{ detail.wallpaper.favoriteCount || 0 }} 收藏</span>
          </div>
          <div class="tag-row">
            <span class="tag-chip" v-for="tag in detail.tags || []" :key="tag.id">{{ tag.name }}</span>
          </div>
          <div class="action-row">
            <el-button type="primary" size="large" round @click="handleDownload">下载原图</el-button>
            <el-button size="large" round @click="handleFavorite">
              {{ detail.hasFavorite ? '取消收藏' : '加入收藏' }}
            </el-button>
          </div>
          <div class="meta-block">
            <div>分类：{{ detail.category?.name || '-' }}</div>
            <div>作者：{{ detail.wallpaper.authorName || '-' }}</div>
            <div>主色：{{ detail.wallpaper.colorHex || '-' }}</div>
          </div>
        </div>
      </section>
    </template>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { addFavorite, cancelFavorite, downloadWallpaper, getWallpaperDetail } from '../../api'
import { useUserStore } from '../../store/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const detail = reactive({
  wallpaper: null,
  category: null,
  tags: [],
  hasFavorite: false
})

const loadDetail = async () => {
  loading.value = true
  try {
    const res = await getWallpaperDetail(route.params.id)
    Object.assign(detail, res.data || {})
  } finally {
    loading.value = false
  }
}

const handleFavorite = async () => {
  if (!userStore.token) {
    router.push('/login')
    return
  }
  if (detail.hasFavorite) {
    await cancelFavorite(detail.wallpaper.id)
    detail.hasFavorite = false
    ElMessage.success('已取消收藏')
  } else {
    await addFavorite(detail.wallpaper.id)
    detail.hasFavorite = true
    ElMessage.success('收藏成功')
  }
}

const handleDownload = async () => {
  await downloadWallpaper(detail.wallpaper.id)
  window.open(detail.wallpaper.imageUrl, '_blank')
}

onMounted(loadDetail)
</script>

<style scoped>
.detail-page {
  padding-top: 32px;
}

.detail-hero {
  display: grid;
  grid-template-columns: 1.3fr 0.9fr;
  gap: 24px;
}

.preview-panel,
.info-panel {
  border-radius: 28px;
  overflow: hidden;
}

.preview-panel img {
  width: 100%;
  display: block;
  min-height: 680px;
  object-fit: cover;
}

.info-panel {
  padding: 34px;
}

.detail-title {
  margin: 14px 0 16px;
  font-size: 44px;
}

.detail-sub,
.meta-block {
  color: var(--text-sub);
  line-height: 1.8;
}

.detail-meta {
  margin: 22px 0;
  display: flex;
  flex-wrap: wrap;
  gap: 14px;
  color: var(--text-sub);
}

.tag-row {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.tag-chip {
  padding: 8px 14px;
  border-radius: 999px;
  background: rgba(125, 211, 252, 0.12);
  border: 1px solid rgba(125, 211, 252, 0.3);
  color: var(--accent);
}

.action-row {
  display: flex;
  gap: 12px;
  margin: 28px 0;
}

.meta-block {
  display: grid;
  gap: 10px;
}

@media (max-width: 900px) {
  .detail-hero {
    grid-template-columns: 1fr;
  }

  .preview-panel img {
    min-height: 360px;
  }

  .detail-title {
    font-size: 34px;
  }

  .action-row {
    flex-wrap: wrap;
  }
}
</style>
