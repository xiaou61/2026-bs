<template>
  <div class="home">
    <el-row :gutter="20">
      <el-col :span="18">
        <el-card class="section-card">
          <template #header>
            <div class="card-header">
              <span class="card-title">热门民歌</span>
              <el-button text type="primary" @click="router.push('/search')">更多</el-button>
            </div>
          </template>
          <el-row :gutter="16">
            <el-col :span="6" v-for="song in hotSongs" :key="song.id">
              <div class="song-card" @click="router.push(`/song/${song.id}`)">
                <img :src="song.coverImage || '/default-cover.png'" class="song-cover" />
                <div class="song-info">
                  <div class="song-title">{{ song.title }}</div>
                  <div class="song-meta">
                    <span>{{ song.categoryName }}</span>
                    <span><el-icon><View /></el-icon> {{ song.viewCount }}</span>
                  </div>
                </div>
              </div>
            </el-col>
          </el-row>
        </el-card>

        <el-card class="section-card" style="margin-top: 20px;">
          <template #header>
            <div class="card-header">
              <span class="card-title">最新发布</span>
            </div>
          </template>
          <el-row :gutter="16">
            <el-col :span="6" v-for="song in latestSongs" :key="song.id">
              <div class="song-card" @click="router.push(`/song/${song.id}`)">
                <img :src="song.coverImage || '/default-cover.png'" class="song-cover" />
                <div class="song-info">
                  <div class="song-title">{{ song.title }}</div>
                  <div class="song-meta">
                    <span>{{ song.userName }}</span>
                    <span><el-icon><Star /></el-icon> {{ song.likeCount }}</span>
                  </div>
                </div>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card class="section-card">
          <template #header><span class="card-title">分类导航</span></template>
          <div class="category-list">
            <el-tag v-for="cat in categories" :key="cat.id" class="category-tag" @click="router.push(`/category/${cat.id}`)">
              {{ cat.name }}
            </el-tag>
          </div>
        </el-card>

        <el-card class="section-card" style="margin-top: 20px;">
          <template #header><span class="card-title">最新公告</span></template>
          <div class="announcement-list">
            <div v-for="ann in announcements" :key="ann.id" class="announcement-item">
              <el-icon><Bell /></el-icon>
              <span>{{ ann.title }}</span>
            </div>
            <el-empty v-if="announcements.length === 0" description="暂无公告" :image-size="60" />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { songApi, categoryApi, announcementApi } from '@/api'

const router = useRouter()
const hotSongs = ref([])
const latestSongs = ref([])
const categories = ref([])
const announcements = ref([])

onMounted(async () => {
  const [hotRes, latestRes, catRes, annRes] = await Promise.all([
    songApi.getHot(8),
    songApi.getLatest(8),
    categoryApi.getAll(),
    announcementApi.getLatest(5)
  ])
  hotSongs.value = hotRes.data
  latestSongs.value = latestRes.data
  categories.value = catRes.data
  announcements.value = annRes.data
})
</script>

<style scoped lang="scss">
.home {
  max-width: 1200px;
  margin: 0 auto;
}

.section-card {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}

.category-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;

  .category-tag {
    cursor: pointer;
    &:hover {
      background: #409eff;
      color: #fff;
    }
  }
}

.announcement-list {
  .announcement-item {
    display: flex;
    align-items: center;
    padding: 8px 0;
    border-bottom: 1px dashed #eee;
    cursor: pointer;
    
    .el-icon {
      color: #f56c6c;
      margin-right: 8px;
    }

    span {
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    &:hover span {
      color: #409eff;
    }
  }
}
</style>
