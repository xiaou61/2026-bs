<template>
  <div class="search-page">
    <el-card>
      <div class="search-header">
        <el-input v-model="keyword" placeholder="搜索民歌" size="large" @keyup.enter="handleSearch" clearable>
          <template #append>
            <el-button @click="handleSearch"><el-icon><Search /></el-icon></el-button>
          </template>
        </el-input>
      </div>

      <div class="search-results" v-if="keyword">
        <h3>搜索结果：{{ keyword }}</h3>
        <el-row :gutter="16">
          <el-col :span="6" v-for="song in songs" :key="song.id">
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
        <el-empty v-if="songs.length === 0 && searched" description="未找到相关民歌" />
        <el-pagination
          v-if="total > 0"
          v-model:current-page="pageNum"
          :page-size="pageSize"
          :total="total"
          layout="total, prev, pager, next"
          @current-change="handleSearch"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { songApi } from '@/api'

const route = useRoute()
const router = useRouter()

const keyword = ref('')
const songs = ref([])
const pageNum = ref(1)
const pageSize = ref(12)
const total = ref(0)
const searched = ref(false)

onMounted(() => {
  if (route.query.keyword) {
    keyword.value = route.query.keyword
    handleSearch()
  }
})

const handleSearch = async () => {
  if (!keyword.value.trim()) return
  searched.value = true
  const res = await songApi.search({ keyword: keyword.value, pageNum: pageNum.value, pageSize: pageSize.value })
  songs.value = res.data.records
  total.value = res.data.total
}
</script>

<style scoped lang="scss">
.search-page {
  max-width: 1200px;
  margin: 0 auto;
}

.search-header {
  max-width: 600px;
  margin: 0 auto 30px;
}

.search-results {
  h3 {
    margin-bottom: 20px;
    color: #666;
  }
}
</style>
