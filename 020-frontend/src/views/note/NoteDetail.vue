<template>
  <div class="note-detail-container">
    <el-card v-if="note">
      <template #header>
        <div class="header">
          <h2>{{ note.title }}</h2>
          <div>
            <el-button @click="handleLike" :disabled="liked">
              <el-icon><Star /></el-icon>
              点赞 ({{ note.likeCount }})
            </el-button>
            <el-button v-if="note.userId === userStore.userInfo?.id" type="primary" @click="goToEdit">
              编辑
            </el-button>
          </div>
        </div>
      </template>

      <div class="note-info">
        <el-tag>{{ note.category }}</el-tag>
        <span>浏览：{{ note.viewCount }}</span>
        <span>点赞：{{ note.likeCount }}</span>
        <span>{{ note.createTime }}</span>
      </div>

      <el-divider />

      <div class="note-content">
        {{ note.content }}
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getNoteDetail, likeNote } from '@/api/note'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const note = ref(null)
const liked = ref(false)

const loadData = async () => {
  try {
    const res = await getNoteDetail(route.params.id)
    note.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const handleLike = async () => {
  try {
    await likeNote(route.params.id)
    ElMessage.success('点赞成功')
    liked.value = true
    loadData()
  } catch (error) {
    console.error(error)
  }
}

const goToEdit = () => {
  router.push({ path: '/note/edit', query: { id: route.params.id } })
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.note-detail-container {
  max-width: 1200px;
  margin: 0 auto;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header h2 {
  margin: 0;
}

.note-info {
  display: flex;
  gap: 20px;
  align-items: center;
  color: #909399;
  font-size: 14px;
}

.note-content {
  padding: 20px 0;
  color: #303133;
  line-height: 1.8;
  white-space: pre-wrap;
}
</style>

