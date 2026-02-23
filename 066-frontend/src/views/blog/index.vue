<template>
  <el-row :gutter="12">
    <el-col :span="17">
      <el-card>
        <div class="bar">
          <el-input v-model="query.keyword" placeholder="搜索标题/摘要" style="width: 260px" clearable />
          <el-select v-model="query.categoryId" placeholder="分类" style="width: 160px" clearable>
            <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
          <el-button type="primary" @click="loadData">搜索</el-button>
        </div>

        <el-empty v-if="!tableData.length" description="暂无文章" />

        <div v-for="item in tableData" :key="item.id" class="post-item">
          <div class="head">
            <h3 @click="goDetail(item.id)">{{ item.title }}</h3>
            <el-tag v-if="item.isTop === 1" type="warning">置顶</el-tag>
          </div>
          <div class="meta">
            <span>{{ item.authorName || '未知作者' }}</span>
            <span>{{ item.categoryName || '未分类' }}</span>
            <span>{{ item.createTime }}</span>
            <span>浏览 {{ item.viewCount || 0 }}</span>
            <span>评论 {{ item.commentCount || 0 }}</span>
          </div>
          <p class="summary">{{ item.summary }}</p>
          <div class="tags">
            <el-tag v-for="tag in item.tagNames || []" :key="tag" size="small" effect="plain">{{ tag }}</el-tag>
          </div>
          <div class="actions">
            <el-button type="primary" link @click="goDetail(item.id)">阅读全文</el-button>
          </div>
        </div>

        <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
      </el-card>
    </el-col>

    <el-col :span="7">
      <el-card>
        <template #header>系统公告</template>
        <div v-if="!notices.length" class="empty-text">暂无公告</div>
        <div v-for="item in notices" :key="item.id" class="notice-item">
          <div class="notice-title">{{ item.title }}</div>
          <div class="notice-content">{{ item.content }}</div>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { getCategoryList, getNoticeList, getPublicPostPage } from '../../api'

const router = useRouter()
const tableData = ref([])
const total = ref(0)
const categories = ref([])
const notices = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, keyword: '', categoryId: null })

const loadBase = async () => {
  const [cRes, nRes] = await Promise.all([getCategoryList(), getNoticeList()])
  categories.value = cRes.data || []
  notices.value = (nRes.data || []).slice(0, 6)
}

const loadData = async () => {
  const res = await getPublicPostPage(query)
  tableData.value = res.data.records || []
  total.value = res.data.total || 0
}

const goDetail = (id) => {
  router.push(`/blog-detail/${id}`)
}

onMounted(async () => {
  await loadBase()
  await loadData()
})
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}

.post-item {
  border-bottom: 1px solid #e2e8f0;
  padding: 14px 0;
}

.post-item:last-child {
  border-bottom: none;
}

.head {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.head h3 {
  margin: 0;
  font-size: 20px;
  color: #0f172a;
  cursor: pointer;
}

.meta {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  color: #64748b;
  font-size: 13px;
  margin-top: 8px;
}

.summary {
  color: #334155;
  line-height: 1.8;
  margin: 10px 0;
}

.tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.actions {
  margin-top: 10px;
}

.notice-item {
  padding: 10px 0;
  border-bottom: 1px dashed #e2e8f0;
}

.notice-item:last-child {
  border-bottom: none;
}

.notice-title {
  font-weight: 600;
  color: #0f172a;
}

.notice-content {
  margin-top: 6px;
  color: #475569;
  line-height: 1.6;
  white-space: pre-wrap;
}

.empty-text {
  color: #94a3b8;
}
</style>
