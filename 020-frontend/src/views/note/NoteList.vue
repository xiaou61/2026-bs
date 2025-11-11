<template>
  <div class="note-container">
    <el-card>
      <template #header>
        <div class="header-actions">
          <h3>学习笔记</h3>
          <el-button type="primary" @click="goToCreate">
            <el-icon><EditPen /></el-icon>
            创建笔记
          </el-button>
        </div>
      </template>

      <el-tabs v-model="activeTab" @tab-change="handleTabChange">
        <el-tab-pane label="公开笔记" name="public" />
        <el-tab-pane label="我的笔记" name="my" />
      </el-tabs>

      <div class="note-list">
        <div v-for="item in noteList" :key="item.id" class="note-item" @click="goToDetail(item.id)">
          <div class="note-header">
            <h4>{{ item.title }}</h4>
            <div class="note-meta">
              <el-tag size="small">{{ item.category }}</el-tag>
              <span>{{ item.viewCount }} 浏览</span>
              <span>{{ item.likeCount }} 点赞</span>
            </div>
          </div>
          <div class="note-content">{{ item.content.substring(0, 150) }}...</div>
          <div class="note-footer">{{ item.createTime }}</div>
        </div>
      </div>

      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getNoteList, getMyNotes } from '@/api/note'

const router = useRouter()
const activeTab = ref('public')
const noteList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const loadData = async () => {
  try {
    if (activeTab.value === 'public') {
      const res = await getNoteList({
        page: currentPage.value,
        size: pageSize.value,
        isPublic: 1
      })
      noteList.value = res.data.records
      total.value = res.data.total
    } else {
      const res = await getMyNotes({
        page: currentPage.value,
        size: pageSize.value
      })
      noteList.value = res.data.records
      total.value = res.data.total
    }
  } catch (error) {
    console.error(error)
  }
}

const handleTabChange = () => {
  currentPage.value = 1
  loadData()
}

const goToDetail = (id) => {
  router.push(`/note/${id}`)
}

const goToCreate = () => {
  router.push('/note/edit')
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.note-container {
  max-width: 1400px;
  margin: 0 auto;
}

.header-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-actions h3 {
  margin: 0;
}

.note-list {
  margin-top: 20px;
}

.note-item {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 15px;
  cursor: pointer;
  transition: box-shadow 0.3s;
}

.note-item:hover {
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
}

.note-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.note-header h4 {
  margin: 0;
  color: #303133;
}

.note-meta {
  display: flex;
  gap: 15px;
  color: #909399;
  font-size: 12px;
}

.note-content {
  color: #606266;
  line-height: 1.6;
  margin: 10px 0;
}

.note-footer {
  color: #909399;
  font-size: 12px;
}
</style>

