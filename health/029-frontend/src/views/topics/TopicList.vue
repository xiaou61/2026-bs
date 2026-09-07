<template>
  <section class="page-shell">
    <div class="page-head">
      <div>
        <p class="eyebrow">Community</p>
        <h1>食疗交流社区</h1>
        <p>从节气调养到创作经验，和平台用户一起交流真实可落地的健康饮食实践。</p>
      </div>
      <el-button type="primary" size="large" @click="router.push({ name: 'create-topic' })">
        发起话题
      </el-button>
    </div>

    <el-card class="toolbar-card" shadow="never">
      <div class="toolbar">
        <el-input
          v-model="keyword"
          placeholder="搜索话题标题或内容"
          clearable
          @keyup.enter="loadTopics"
          @clear="loadTopics"
        />
        <el-button type="primary" @click="loadTopics">搜索</el-button>
      </div>
    </el-card>

    <div v-loading="loading" class="topic-list">
      <el-card v-for="topic in topics" :key="topic.id" class="topic-card" shadow="hover" @click="openDetail(topic.id)">
        <div class="topic-card-head">
          <div>
            <el-tag size="small" effect="plain">{{ topic.category || '综合交流' }}</el-tag>
            <h3>{{ topic.title }}</h3>
          </div>
          <div class="topic-meta">
            <span>{{ topic.views || 0 }} 浏览</span>
            <span>{{ topic.replies || 0 }} 回复</span>
          </div>
        </div>
        <p>{{ topic.content }}</p>
        <div class="topic-tags" v-if="topic.tags">
          <el-tag v-for="tag in parseTags(topic.tags)" :key="tag" size="small" type="success" effect="light">
            {{ tag }}
          </el-tag>
        </div>
      </el-card>
      <el-empty v-if="!loading && !topics.length" description="暂时还没有匹配的话题" />
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getTopicList, searchTopics } from '../../api/topic'

const router = useRouter()
const loading = ref(false)
const keyword = ref('')
const topics = ref([])

const parseTags = (value = '') => value.split(/[,\s，]+/).filter(Boolean)

const loadTopics = async () => {
  loading.value = true
  try {
    const response = keyword.value.trim()
      ? await searchTopics(keyword.value.trim())
      : await getTopicList()
    topics.value = response.data || []
  } catch (error) {
    ElMessage.error(error?.message || '加载话题失败')
  } finally {
    loading.value = false
  }
}

const openDetail = (id) => {
  router.push({ name: 'topic-detail', params: { id } })
}

onMounted(loadTopics)
</script>

<style scoped>
.page-shell {
  max-width: 1080px;
  margin: 0 auto;
}

.page-head {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  align-items: flex-end;
  padding: 30px 32px;
  border-radius: 28px;
  background: linear-gradient(135deg, #f4ebe0 0%, #e8d8bf 48%, #d4c3a6 100%);
  color: #183c2f;
  margin-bottom: 20px;
}

.eyebrow {
  color: #7b806f;
  font-size: 12px;
  letter-spacing: 0.16em;
  text-transform: uppercase;
  margin-bottom: 8px;
}

.toolbar-card {
  border-radius: 20px;
  margin-bottom: 20px;
}

.toolbar {
  display: flex;
  gap: 12px;
}

.topic-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.topic-card {
  border-radius: 24px;
  cursor: pointer;
}

.topic-card-head {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 12px;
}

.topic-card-head h3 {
  margin-top: 10px;
  color: #183c2f;
}

.topic-card p {
  line-height: 1.8;
  color: #55655c;
}

.topic-meta {
  display: flex;
  gap: 12px;
  color: #7b806f;
  white-space: nowrap;
}

.topic-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 14px;
}

@media (max-width: 768px) {
  .page-head,
  .topic-card-head,
  .toolbar {
    flex-direction: column;
  }
}
</style>
