<template>
  <div class="category-page">
    <el-card v-if="category">
      <div class="category-header">
        <h2>{{ category.name }}</h2>
        <p>{{ category.description }}</p>
      </div>
    </el-card>

    <el-card style="margin-top: 20px;">
      <el-row :gutter="16">
        <el-col :span="6" v-for="song in songs" :key="song.id">
          <div class="song-card" @click="router.push(`/song/${song.id}`)">
            <img :src="song.coverImage || '/default-cover.png'" class="song-cover" />
            <div class="song-info">
              <div class="song-title">{{ song.title }}</div>
              <div class="song-meta">
                <span>{{ song.userName }}</span>
                <span><el-icon><View /></el-icon> {{ song.viewCount }}</span>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-empty v-if="songs.length === 0" description="该分类下暂无民歌" />
      <el-pagination
        v-if="total > 0"
        v-model:current-page="pageNum"
        :page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="fetchSongs"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { categoryApi, songApi } from '@/api'

const route = useRoute()
const router = useRouter()

const category = ref(null)
const songs = ref([])
const pageNum = ref(1)
const pageSize = ref(12)
const total = ref(0)

const fetchCategory = async () => {
  const res = await categoryApi.getById(route.params.id)
  category.value = res.data
}

const fetchSongs = async () => {
  const res = await songApi.getByCategory(route.params.id, { pageNum: pageNum.value, pageSize: pageSize.value })
  songs.value = res.data.records
  total.value = res.data.total
}

onMounted(() => {
  fetchCategory()
  fetchSongs()
})

watch(() => route.params.id, () => {
  pageNum.value = 1
  fetchCategory()
  fetchSongs()
})
</script>

<style scoped lang="scss">
.category-page {
  max-width: 1200px;
  margin: 0 auto;
}

.category-header {
  h2 {
    margin: 0 0 10px;
  }

  p {
    color: #666;
    margin: 0;
  }
}
</style>
