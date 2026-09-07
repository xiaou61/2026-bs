<template>
  <section class="page-shell">
    <div class="page-head">
      <div>
        <p class="eyebrow">My Discussions</p>
        <h1>我发起的话题</h1>
      </div>
      <el-button type="primary" @click="router.push({ name: 'create-topic' })">继续发起话题</el-button>
    </div>

    <div v-loading="loading" class="card-list">
      <el-card v-for="topic in topics" :key="topic.id" class="item-card" shadow="never">
        <div class="item-head">
          <div>
            <el-tag size="small" effect="plain">{{ topic.category || '综合交流' }}</el-tag>
            <h3>{{ topic.title }}</h3>
            <p>{{ topic.content }}</p>
          </div>
          <div class="item-stats">
            <span>{{ topic.views || 0 }} 浏览</span>
            <span>{{ topic.replies || 0 }} 回复</span>
          </div>
        </div>
        <div class="actions">
          <el-button @click="router.push({ name: 'topic-detail', params: { id: topic.id } })">查看详情</el-button>
          <el-button text type="danger" @click="removeTopic(topic.id)">删除</el-button>
        </div>
      </el-card>
      <el-empty v-if="!loading && !topics.length" description="你还没有发起话题" />
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMyTopics } from '../../api/user'
import { deleteTopic } from '../../api/topic'

const router = useRouter()
const loading = ref(false)
const topics = ref([])

const loadTopics = async () => {
  loading.value = true
  try {
    const response = await getMyTopics()
    topics.value = response.data || []
  } catch (error) {
    ElMessage.error(error?.message || '加载我的话题失败')
  } finally {
    loading.value = false
  }
}

const removeTopic = async (id) => {
  try {
    await ElMessageBox.confirm('确定删除这个话题吗？', '删除话题', { type: 'warning' })
    await deleteTopic(id)
    ElMessage.success('已删除')
    await loadTopics()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error?.message || '删除失败')
    }
  }
}

onMounted(loadTopics)
</script>

<style scoped>
.page-shell {
  max-width: 980px;
  margin: 0 auto;
}

.page-head {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: flex-end;
  padding: 28px 30px;
  margin-bottom: 20px;
  border-radius: 24px;
  background: linear-gradient(135deg, #f4ebe0 0%, #ead7bb 100%);
}

.eyebrow {
  color: #7b806f;
  font-size: 12px;
  letter-spacing: 0.16em;
  text-transform: uppercase;
  margin-bottom: 8px;
}

.card-list {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.item-card {
  border-radius: 24px;
}

.item-head {
  display: flex;
  justify-content: space-between;
  gap: 16px;
}

.item-head h3 {
  margin: 10px 0 8px;
  color: #183c2f;
}

.item-head p {
  color: #55655c;
  line-height: 1.7;
}

.item-stats {
  display: flex;
  flex-direction: column;
  gap: 8px;
  color: #7b806f;
  white-space: nowrap;
}

.actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 16px;
}

@media (max-width: 768px) {
  .page-head,
  .item-head,
  .actions {
    flex-direction: column;
  }
}
</style>
