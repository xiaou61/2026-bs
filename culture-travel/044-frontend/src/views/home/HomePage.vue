<template>
  <div class="home-page">
    <div class="banner">
      <h1>发现特色民宿</h1>
      <p>探索独特的住宿体验，感受不一样的旅行生活</p>
    </div>
    <div class="container">
      <div class="section">
        <h2>热门推荐</h2>
        <el-row :gutter="20" v-loading="loading">
          <el-col :span="6" v-for="item in homestayList" :key="item.id">
            <el-card class="homestay-card card-hover" @click="goDetail(item.id)">
              <el-image :src="item.coverImage || 'https://via.placeholder.com/300x200'" fit="cover" class="cover" />
              <div class="info">
                <h3>{{ item.name }}</h3>
                <p class="location"><el-icon><Location /></el-icon>{{ item.city }} {{ item.district }}</p>
                <div class="bottom">
                  <span class="price">¥{{ item.minPrice }}<small>/晚起</small></span>
                  <span class="rating"><el-icon><Star /></el-icon>{{ item.rating }}</span>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
        <div class="more" v-if="total > 8">
          <el-button type="primary" plain @click="$router.push('/search')">查看更多</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getHomestayList } from '@/api/homestay'

const router = useRouter()
const loading = ref(false)
const homestayList = ref<any[]>([])
const total = ref(0)

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getHomestayList({ current: 1, size: 8 })
    homestayList.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const goDetail = (id: number) => {
  router.push(`/homestay/${id}`)
}

onMounted(() => {
  loadData()
})
</script>

<style scoped lang="scss">
.banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 80px 20px;
  text-align: center;
  color: #fff;
  h1 { font-size: 42px; margin-bottom: 16px; }
  p { font-size: 18px; opacity: 0.9; }
}
.section {
  padding: 40px 0;
  h2 { margin-bottom: 24px; font-size: 24px; }
}
.homestay-card {
  cursor: pointer;
  margin-bottom: 20px;
  :deep(.el-card__body) { padding: 0; }
  .cover {
    width: 100%;
    height: 180px;
  }
  .info {
    padding: 12px;
    h3 {
      font-size: 16px;
      margin-bottom: 8px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .location {
      color: #999;
      font-size: 13px;
      display: flex;
      align-items: center;
      gap: 4px;
      margin-bottom: 8px;
    }
    .bottom {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    .price {
      color: #ff6600;
      font-size: 18px;
      font-weight: bold;
      small { font-size: 12px; font-weight: normal; }
    }
    .rating {
      color: #ff9900;
      display: flex;
      align-items: center;
      gap: 4px;
    }
  }
}
.more {
  text-align: center;
  margin-top: 20px;
}
</style>
