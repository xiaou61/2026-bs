<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="6" v-for="artist in artists" :key="artist.id">
        <el-card :body-style="{ padding: '0px' }" class="artist-card" @click="viewDetail(artist.id)">
          <img :src="artist.avatar || 'https://via.placeholder.com/300'" class="artist-image">
          <div style="padding: 14px;">
            <span class="artist-name">{{ artist.nickname }}</span>
            <div class="artist-info">
              <p>风格: {{ artist.style }}</p>
              <p>价格: ¥{{ artist.priceMin }}-{{ artist.priceMax }}</p>
              <p>评分: {{ artist.rating }}⭐</p>
              <p>订单: {{ artist.orderCount }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import request from '../../utils/request'

const router = useRouter()
const artists = ref([])

const loadArtists = async () => {
  try {
    artists.value = await request.get('/artist/approved')
  } catch (error) {
    console.error(error)
  }
}

const viewDetail = (id) => {
  router.push(`/artist-detail/${id}`)
}

onMounted(() => {
  loadArtists()
})
</script>

<style scoped>
.artist-card {
  cursor: pointer;
  margin-bottom: 20px;
}

.artist-card:hover {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);
}

.artist-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.artist-name {
  font-weight: bold;
  font-size: 16px;
}

.artist-info {
  margin-top: 10px;
  font-size: 14px;
  color: #666;
}

.artist-info p {
  margin: 5px 0;
}
</style>
