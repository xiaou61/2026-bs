<template>
  <div class="my-favorites-page">
    <el-card>
      <template #header><span class="card-title">我的收藏</span></template>
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
      <el-empty v-if="songs.length === 0" description="暂无收藏" />
      <el-pagination
        v-if="total > 0"
        v-model:current-page="pageNum"
        :page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="fetchData"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { userApi } from '@/api'

const router = useRouter()
const songs = ref([])
const pageNum = ref(1)
const pageSize = ref(12)
const total = ref(0)

const fetchData = async () => {
  const res = await userApi.getMyFavorites({ pageNum: pageNum.value, pageSize: pageSize.value })
  songs.value = res.data.records
  total.value = res.data.total
}

onMounted(fetchData)
</script>

<style scoped lang="scss">
.my-favorites-page {
  max-width: 1200px;
  margin: 0 auto;
}
</style>
