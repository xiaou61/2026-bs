<template>
  <div class="home-page">
    <section class="hero page-shell">
      <div class="hero-copy glass-card">
        <div class="hero-tag">高清视觉内容社区</div>
        <h1 class="display-title hero-title">让桌面像电影海报一样被点亮</h1>
        <p class="hero-text">
          聚合桌面与手机双端高清壁纸，支持分类筛选、收藏下载、用户投稿与后台审核运营。
        </p>
        <div class="hero-actions">
          <router-link to="/discover"><el-button type="primary" size="large" round>去发现壁纸</el-button></router-link>
          <router-link to="/my-uploads"><el-button size="large" round plain>我要投稿</el-button></router-link>
        </div>
      </div>
      <div class="hero-visual glass-card">
        <el-carousel height="420px" indicator-position="outside">
          <el-carousel-item v-for="item in banners" :key="item.id">
            <div class="banner-card" :style="{ backgroundImage: `linear-gradient(180deg, rgba(4,10,20,0.1), rgba(4,10,20,0.75)), url(${item.imageUrl})` }">
              <div class="banner-content">
                <div class="banner-title display-title">{{ item.title }}</div>
                <router-link :to="item.linkUrl || '/discover'">
                  <el-button type="warning" round>进入专题</el-button>
                </router-link>
              </div>
            </div>
          </el-carousel-item>
        </el-carousel>
      </div>
    </section>

    <section class="page-shell section-grid">
      <div class="section-main">
        <div class="section-header">
          <div>
            <div class="section-eyebrow">精选推荐</div>
            <h2 class="display-title section-title">本周高热壁纸</h2>
          </div>
          <router-link to="/discover">查看更多</router-link>
        </div>
        <div class="wallpaper-grid">
          <article class="wallpaper-card glass-card" v-for="item in recommendList" :key="item.id" @click="goDetail(item.id)">
            <img :src="item.coverUrl || item.imageUrl" :alt="item.title">
            <div class="wallpaper-meta">
              <div class="wallpaper-title">{{ item.title }}</div>
              <div class="wallpaper-sub">{{ item.resolution || item.wallpaperType }}</div>
            </div>
          </article>
        </div>
      </div>
      <aside class="section-side">
        <div class="notice-card glass-card">
          <div class="section-eyebrow">公告</div>
          <h3 class="display-title side-title">站内动态</h3>
          <div class="notice-list">
            <div class="notice-item" v-for="item in notices" :key="item.id">
              <div class="notice-title">{{ item.title }}</div>
              <div class="notice-content">{{ item.content }}</div>
            </div>
          </div>
        </div>
      </aside>
    </section>

    <section class="page-shell latest-section">
      <div class="section-header">
        <div>
          <div class="section-eyebrow">最新上新</div>
          <h2 class="display-title section-title">刚刚通过审核的作品</h2>
        </div>
      </div>
      <div class="latest-list">
        <div class="latest-item glass-card" v-for="item in latestList" :key="item.id" @click="goDetail(item.id)">
          <img :src="item.coverUrl || item.imageUrl" :alt="item.title">
          <div class="latest-info">
            <div class="latest-name">{{ item.title }}</div>
            <div class="latest-desc">{{ item.subtitle || item.description || '高清壁纸资源' }}</div>
            <div class="latest-tags">
              <span>{{ item.wallpaperType === 'mobile' ? '手机' : '桌面' }}</span>
              <span>{{ item.resolution || '高清' }}</span>
              <span>{{ item.downloadCount || 0 }} 下载</span>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { getBannerEnabled, getPublicNoticeList, getPublicWallpaperList, getRecommendWallpapers } from '../../api'

const router = useRouter()
const banners = ref([])
const notices = ref([])
const recommendList = ref([])
const latestList = ref([])

const goDetail = (id) => {
  router.push(`/detail/${id}`)
}

const loadData = async () => {
  const [bannerRes, noticeRes, recommendRes, latestRes] = await Promise.all([
    getBannerEnabled(),
    getPublicNoticeList(),
    getRecommendWallpapers({ size: 6 }),
    getPublicWallpaperList({ pageNum: 1, pageSize: 6, sortBy: 'latest' })
  ])
  banners.value = bannerRes.data || []
  notices.value = noticeRes.data || []
  recommendList.value = recommendRes.data || []
  latestList.value = latestRes.data.records || []
}

onMounted(loadData)
</script>

<style scoped>
.home-page {
  padding-top: 32px;
}

.hero {
  display: grid;
  grid-template-columns: 1.1fr 1fr;
  gap: 24px;
  align-items: stretch;
}

.hero-copy,
.hero-visual,
.notice-card,
.wallpaper-card,
.latest-item {
  border-radius: 28px;
}

.hero-copy {
  padding: 52px;
}

.hero-tag,
.section-eyebrow {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  color: var(--accent);
  font-size: 13px;
  letter-spacing: 0.18em;
  text-transform: uppercase;
}

.hero-title {
  margin: 20px 0 16px;
  font-size: 56px;
  line-height: 1.05;
}

.hero-text {
  margin: 0;
  max-width: 520px;
  color: var(--text-sub);
  font-size: 18px;
  line-height: 1.8;
}

.hero-actions {
  display: flex;
  gap: 14px;
  margin-top: 32px;
}

.banner-card {
  height: 420px;
  padding: 36px;
  border-radius: 28px;
  background-size: cover;
  background-position: center;
  display: flex;
  align-items: flex-end;
}

.banner-title {
  margin-bottom: 16px;
  font-size: 32px;
}

.section-grid {
  display: grid;
  grid-template-columns: 1.2fr 0.8fr;
  gap: 24px;
  margin-top: 36px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: end;
  margin-bottom: 20px;
}

.section-title,
.side-title {
  margin: 8px 0 0;
  font-size: 32px;
}

.wallpaper-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 18px;
}

.wallpaper-card {
  overflow: hidden;
  cursor: pointer;
}

.wallpaper-card img {
  width: 100%;
  height: 220px;
  object-fit: cover;
  display: block;
}

.wallpaper-meta {
  padding: 18px;
}

.wallpaper-title,
.notice-title,
.latest-name {
  font-size: 18px;
  font-weight: 600;
}

.wallpaper-sub,
.notice-content,
.latest-desc,
.latest-tags {
  margin-top: 8px;
  color: var(--text-sub);
  font-size: 14px;
  line-height: 1.6;
}

.notice-card {
  padding: 26px;
}

.notice-list {
  margin-top: 18px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.notice-item {
  padding-bottom: 16px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
}

.latest-section {
  margin-top: 42px;
}

.latest-list {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 20px;
}

.latest-item {
  display: grid;
  grid-template-columns: 280px 1fr;
  overflow: hidden;
  cursor: pointer;
}

.latest-item img {
  width: 100%;
  height: 100%;
  min-height: 220px;
  object-fit: cover;
}

.latest-info {
  padding: 24px;
}

.latest-tags span {
  margin-right: 14px;
}

@media (max-width: 900px) {
  .hero,
  .section-grid,
  .latest-list {
    grid-template-columns: 1fr;
  }

  .hero-copy {
    padding: 28px;
  }

  .hero-title {
    font-size: 38px;
  }

  .wallpaper-grid {
    grid-template-columns: 1fr;
  }

  .latest-item {
    grid-template-columns: 1fr;
  }
}
</style>
