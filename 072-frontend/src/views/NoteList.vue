<template>
  <div class="note-list">
    <el-card class="search-card">
      <el-form :inline="true" :model="query">
        <el-form-item label="游记标题"><el-input v-model="query.title" placeholder="请输入标题关键词" clearable /></el-form-item>
        <el-form-item><el-button type="primary" @click="loadData">搜索</el-button></el-form-item>
        <el-form-item><el-button type="success" @click="$router.push('/notes/edit')">发布游记</el-button></el-form-item>
      </el-form>
    </el-card>
    <el-row :gutter="20">
      <el-col :span="8" v-for="note in list" :key="note.id">
        <el-card class="note-card" shadow="hover" @click="$router.push(`/notes/${note.id}`)">
          <el-image :src="note.coverImage" class="note-img" fit="cover" />
          <div class="note-info">
            <h3>{{ note.title }}</h3>
            <p class="summary">{{ note.summary }}</p>
            <div class="meta">
              <span class="author"><el-icon><User /></el-icon>{{ note.nickname }}</span>
              <span class="time">{{ note.createTime?.substring(0, 10) }}</span>
            </div>
            <div class="stats">
              <span><el-icon><View /></el-icon>{{ note.viewCount }}</span>
              <span><el-icon><ChatDotRound /></el-icon>{{ note.commentCount || 0 }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination v-if="total > 0" background layout="prev, pager, next" :total="total" :page-size="query.pageSize" v-model:current-page="query.pageNum" @current-change="loadData" class="pagination" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getNoteList } from '../api'

const query = ref({ title: '', pageNum: 1, pageSize: 6 })
const list = ref([])
const total = ref(0)

const loadData = async () => {
  const res = await getNoteList(query.value)
  list.value = res.data.records || []
  total.value = res.data.total || 0
}

onMounted(() => loadData())
</script>

<style scoped>
.search-card { margin-bottom: 20px; }
.note-card { cursor: pointer; margin-bottom: 20px; }
.note-img { width: 100%; height: 200px; }
.note-info { padding: 15px 0; }
.note-info h3 { margin: 0 0 10px; font-size: 18px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.summary { color: #666; font-size: 14px; margin-bottom: 15px; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; }
.meta { display: flex; justify-content: space-between; color: #999; font-size: 13px; margin-bottom: 10px; }
.author { display: flex; align-items: center; gap: 4px; }
.stats { display: flex; gap: 15px; color: #999; font-size: 13px; }
.stats span { display: flex; align-items: center; gap: 4px; }
.pagination { margin-top: 20px; justify-content: center; }
</style>
