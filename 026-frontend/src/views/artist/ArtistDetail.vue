<template>
  <div v-if="artist">
    <el-card>
      <h2>{{ artist.nickname }}</h2>
      <el-descriptions :column="2">
        <el-descriptions-item label="擅长风格">{{ artist.style }}</el-descriptions-item>
        <el-descriptions-item label="价格区间">¥{{ artist.priceMin }}-{{ artist.priceMax }}</el-descriptions-item>
        <el-descriptions-item label="交稿周期">{{ artist.deliveryDays }}天</el-descriptions-item>
        <el-descriptions-item label="评分">{{ artist.rating }}⭐</el-descriptions-item>
        <el-descriptions-item label="完成订单">{{ artist.orderCount }}</el-descriptions-item>
        <el-descriptions-item label="接受类型">{{ artist.acceptTypes }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card style="margin-top: 20px">
      <template #header>
        <span>作品集</span>
      </template>
      <el-row :gutter="20">
        <el-col :span="6" v-for="work in portfolios" :key="work.id">
          <el-card :body-style="{ padding: '0px' }">
            <img :src="work.imageUrl || 'https://via.placeholder.com/300'" style="width: 100%; height: 200px; object-fit: cover;">
            <div style="padding: 10px;">
              <span>{{ work.title }}</span>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import request from '../../utils/request'

const route = useRoute()
const artist = ref(null)
const portfolios = ref([])

const loadArtist = async () => {
  try {
    artist.value = await request.get(`/artist/${route.params.id}`)
  } catch (error) {
    console.error(error)
  }
}

const loadPortfolios = async () => {
  try {
    portfolios.value = await request.get(`/portfolio/artist/${route.params.id}`)
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadArtist()
  loadPortfolios()
})
</script>
