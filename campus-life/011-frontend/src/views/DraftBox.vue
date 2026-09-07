<template>
  <div class="draft-container">
    <div class="page-header">
      <div>
        <h2>草稿箱</h2>
        <p>继续编辑未发布的视频内容，随时回到发布页完善细节。</p>
      </div>
      <el-button type="primary" @click="createDraft">去发布页</el-button>
    </div>

    <div v-if="drafts.length > 0" class="draft-grid">
      <el-card v-for="draft in drafts" :key="draft.id" class="draft-card" shadow="hover">
        <div class="draft-cover">
          <img v-if="draft.coverUrl" :src="draft.coverUrl" alt="">
          <div v-else class="cover-placeholder">草稿</div>
        </div>
        <div class="draft-body">
          <div class="draft-title">{{ draft.title || '未命名草稿' }}</div>
          <div class="draft-desc">{{ draft.description || '还没有填写描述，点击继续编辑完善内容。' }}</div>
          <div class="draft-meta">
            <span>更新时间：{{ draft.updateTime || draft.createTime }}</span>
            <span v-if="draft.location">位置：{{ draft.location }}</span>
          </div>
          <div class="draft-actions">
            <el-button type="primary" @click="editDraft(draft.id)">继续编辑</el-button>
            <el-button @click="removeDraft(draft.id)">删除</el-button>
          </div>
        </div>
      </el-card>
    </div>

    <el-empty v-else description="还没有保存的草稿，去发布页创作第一条内容吧" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getDraftList, deleteDraft } from '@/api/draft'

const router = useRouter()
const drafts = ref([])

const fetchDrafts = async () => {
  try {
    const res = await getDraftList()
    drafts.value = res.data
  } catch (error) {
    ElMessage.error('获取草稿列表失败')
  }
}

const createDraft = () => {
  router.push('/publish')
}

const editDraft = (draftId) => {
  router.push(`/publish?draftId=${draftId}`)
}

const removeDraft = async (draftId) => {
  try {
    await ElMessageBox.confirm('删除后无法恢复，确定要删除这个草稿吗？', '提示', {
      type: 'warning'
    })
    await deleteDraft(draftId)
    ElMessage.success('草稿已删除')
    fetchDrafts()
  } catch (error) {
  }
}

onMounted(() => {
  fetchDrafts()
})
</script>

<style scoped>
.draft-container {
  padding: 24px;
  height: 100%;
  overflow-y: auto;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 16px;
  margin-bottom: 24px;
}

.page-header h2 {
  margin: 0 0 8px;
  font-size: 28px;
}

.page-header p {
  margin: 0;
  color: #6b7280;
}

.draft-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 20px;
}

.draft-card {
  border-radius: 16px;
}

.draft-cover {
  width: 100%;
  height: 200px;
  border-radius: 12px;
  overflow: hidden;
  background: linear-gradient(135deg, #eef2ff 0%, #fff7ed 100%);
  margin-bottom: 16px;
}

.draft-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.cover-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  font-weight: 700;
  color: #64748b;
}

.draft-title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 10px;
  color: #111827;
}

.draft-desc {
  color: #6b7280;
  line-height: 1.7;
  min-height: 48px;
  margin-bottom: 12px;
}

.draft-meta {
  display: flex;
  flex-direction: column;
  gap: 6px;
  font-size: 13px;
  color: #94a3b8;
  margin-bottom: 16px;
}

.draft-actions {
  display: flex;
  gap: 12px;
}
</style>
