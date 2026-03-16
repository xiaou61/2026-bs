<template>
  <div class="discover-page page-shell">
    <div class="discover-header">
      <div>
        <div class="section-eyebrow">发现内容</div>
        <h1 class="display-title discover-title">按风格筛选你下一张壁纸</h1>
      </div>
      <el-input v-model="query.title" placeholder="搜索标题" class="search-input" clearable @keyup.enter="loadData" />
    </div>

    <div class="filter-bar glass-card">
      <el-select v-model="query.categoryId" placeholder="分类" clearable>
        <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-select v-model="query.tagId" placeholder="标签" clearable>
        <el-option v-for="item in tagOptions" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-select v-model="query.wallpaperType" placeholder="类型" clearable>
        <el-option label="桌面壁纸" value="pc" />
        <el-option label="手机壁纸" value="mobile" />
      </el-select>
      <el-select v-model="query.sortBy" placeholder="排序">
        <el-option label="最新" value="latest" />
        <el-option label="热门" value="hot" />
      </el-select>
      <el-button type="primary" @click="loadData">筛选</el-button>
    </div>

    <div class="discover-grid">
      <article class="discover-card glass-card" v-for="item in list" :key="item.id" @click="goDetail(item.id)">
        <img :src="item.coverUrl || item.imageUrl" :alt="item.title">
        <div class="discover-info">
          <div class="discover-name">{{ item.title }}</div>
          <div class="discover-sub">{{ item.subtitle || '高清视觉作品' }}</div>
          <div class="discover-meta">
            <span>{{ item.wallpaperType === 'mobile' ? '手机' : '桌面' }}</span>
            <span>{{ item.resolution || '高清' }}</span>
            <span>{{ item.downloadCount || 0 }} 下载</span>
          </div>
        </div>
      </article>
    </div>

    <div class="pager">
      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        layout="total, prev, pager, next"
        :total="total"
        @current-change="loadData"
      />
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { getCategoryEnabled, getPublicWallpaperList, getTagEnabled } from '../../api'

const router = useRouter()
const list = ref([])
const total = ref(0)
const categoryOptions = ref([])
const tagOptions = ref([])

const query = reactive({
  pageNum: 1,
  pageSize: 12,
  title: '',
  categoryId: null,
  tagId: null,
  wallpaperType: '',
  sortBy: 'latest'
})

const goDetail = (id) => {
  router.push(`/detail/${id}`)
}

const loadData = async () => {
  const res = await getPublicWallpaperList(query)
  list.value = res.data.records || []
  total.value = res.data.total || 0
}

const loadOptions = async () => {
  const [categoryRes, tagRes] = await Promise.all([getCategoryEnabled(), getTagEnabled()])
  categoryOptions.value = categoryRes.data || []
  tagOptions.value = tagRes.data || []
}

onMounted(async () => {
  await loadOptions()
  await loadData()
})
</script>

<style scoped>
.discover-page {
  padding-top: 32px;
}

.discover-header {
  display: flex;
  justify-content: space-between;
  align-items: end;
  margin-bottom: 24px;
}

.discover-title {
  margin: 10px 0 0;
  font-size: 42px;
}

.search-input {
  width: 320px;
}

.filter-bar {
  display: flex;
  gap: 14px;
  padding: 18px;
  border-radius: 22px;
  margin-bottom: 26px;
}

.discover-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 18px;
}

.discover-card {
  overflow: hidden;
  border-radius: 24px;
  cursor: pointer;
}

.discover-card img {
  width: 100%;
  height: 250px;
  object-fit: cover;
  display: block;
}

.discover-info {
  padding: 18px;
}

.discover-name {
  font-size: 18px;
  font-weight: 600;
}

.discover-sub,
.discover-meta {
  margin-top: 8px;
  color: var(--text-sub);
}

.discover-meta {
  display: flex;
  justify-content: space-between;
  font-size: 13px;
}

.pager {
  display: flex;
  justify-content: center;
  margin: 32px 0 12px;
}

@media (max-width: 900px) {
  .discover-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }

  .search-input {
    width: 100%;
  }

  .filter-bar {
    flex-wrap: wrap;
  }

  .discover-grid {
    grid-template-columns: 1fr;
  }
}
</style>
