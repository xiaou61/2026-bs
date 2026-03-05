<template>
  <div class="home-container">
    <el-card class="notice-card" v-if="notices.length">
      <el-carousel height="40px" direction="vertical" :autoplay="true" indicator-position="none">
        <el-carousel-item v-for="notice in notices" :key="notice.id">
          <div class="notice-item">📢 {{ notice.title }}</div>
        </el-carousel-item>
      </el-carousel>
    </el-card>
    <el-card class="category-card">
      <div class="category-list">
        <div class="category-item" :class="{ active: !selectedCategory }" @click="selectedCategory = null">全部</div>
        <div v-for="cat in categories" :key="cat.id" class="category-item" :class="{ active: selectedCategory === cat.id }" @click="selectedCategory = cat.id">{{ cat.name }}</div>
      </div>
    </el-card>
    <div class="section-title">🔥 热门团购</div>
    <div class="activity-list">
      <el-card v-for="item in activities" :key="item.id" class="activity-card" @click="goDetail(item.id)">
        <el-image :src="item.product?.cover" style="width: 100%; height: 200px" fit="cover" />
        <div class="activity-info">
          <div class="product-name">{{ item.product?.name }}</div>
          <div class="price-row">
            <span class="group-price">¥{{ item.groupPrice }}</span>
            <span class="original-price">¥{{ item.product?.originalPrice }}</span>
          </div>
          <div class="meta-row">
            <span>{{ item.minCount }}人团</span>
            <span>已售{{ item.soldCount || 0 }}</span>
          </div>
          <el-button type="danger" size="small" style="width: 100%; margin-top: 10px">立即参团</el-button>
        </div>
      </el-card>
    </div>
    <el-empty v-if="!activities.length" description="暂无团购活动" />
    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadActivities" style="margin-top: 20px; justify-content: center" />
  </div>
</template>

<script setup>
import { ref, reactive, watch, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getCategoryTree, getActivityPage, getNoticeList } from '../../api'

const router = useRouter()
const categories = ref([])
const activities = ref([])
const notices = ref([])
const total = ref(0)
const selectedCategory = ref(null)
const query = reactive({ pageNum: 1, pageSize: 12, status: 1 })

const loadCategories = async () => {
  const res = await getCategoryTree()
  categories.value = res.data.flatMap(cat => [cat, ...(cat.children || [])])
}

const loadActivities = async () => {
  const params = { ...query }
  if (selectedCategory.value) params.categoryId = selectedCategory.value
  const res = await getActivityPage(params)
  activities.value = res.data.records
  total.value = res.data.total
}

const loadNotices = async () => {
  const res = await getNoticeList()
  notices.value = res.data
}

const goDetail = (id) => {
  router.push(`/activity/${id}`)
}

watch(selectedCategory, () => {
  query.pageNum = 1
  loadActivities()
})

onMounted(() => {
  loadCategories()
  loadActivities()
  loadNotices()
})
</script>

<style scoped>
.home-container { padding: 20px; }
.notice-card { margin-bottom: 20px; }
.notice-item { line-height: 40px; color: #e6a23c; }
.category-card { margin-bottom: 20px; }
.category-list { display: flex; flex-wrap: wrap; gap: 10px; }
.category-item { padding: 8px 16px; border-radius: 20px; background: #f5f5f5; cursor: pointer; transition: all 0.3s; }
.category-item:hover, .category-item.active { background: #409eff; color: white; }
.section-title { font-size: 18px; font-weight: bold; margin-bottom: 15px; }
.activity-list { display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 20px; }
.activity-card { cursor: pointer; transition: transform 0.3s; }
.activity-card:hover { transform: translateY(-5px); }
.activity-info { padding: 10px; }
.product-name { font-weight: bold; margin-bottom: 8px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.price-row { margin-bottom: 5px; }
.group-price { font-size: 20px; color: #f56c6c; font-weight: bold; }
.original-price { font-size: 12px; color: #999; text-decoration: line-through; margin-left: 8px; }
.meta-row { display: flex; justify-content: space-between; color: #999; font-size: 12px; }
</style>
