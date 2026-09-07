<template>
  <div class="page">
    <section class="panel">
      <div class="header">
        <div>
          <h2>消息中心</h2>
          <p>按商品维度组织会话，普通聊天和议价消息都在这里统一查看。</p>
        </div>
      </div>

      <div class="session-list" v-loading="loading">
        <article
          v-for="session in sessions"
          :key="`${session.productId}-${session.targetUserId}`"
          class="session-card"
          @click="openSession(session)"
        >
          <img :src="resolveImage(session)" :alt="session.productTitle" />
          <div class="content">
            <div class="title-row">
              <div>
                <strong>{{ session.productTitle }}</strong>
                <p>{{ session.targetUserName }}</p>
              </div>
              <div class="right-meta">
                <span>{{ formatTime(session.lastTime) }}</span>
                <el-badge v-if="session.unreadCount" :value="session.unreadCount" />
              </div>
            </div>

            <div class="message-row">
              <span>{{ session.lastMessage }}</span>
              <el-tag v-if="session.lastMessageType === 'bargain'" size="small" type="warning">
                议价 {{ formatPrice(session.bargainPrice) }} · {{ getBargainStatusLabel(session.bargainStatus) }}
              </el-tag>
            </div>
          </div>
        </article>

        <el-empty v-if="!loading && !sessions.length" description="暂时还没有聊天记录" />
      </div>
    </section>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { chatApi } from '@/api/chat'
import { fallbackImage, formatPrice, formatTime, getBargainStatusLabel, normalizeImageList } from '@/utils/market'

const router = useRouter()
const loading = ref(false)
const sessions = ref([])

const loadSessions = async () => {
  loading.value = true
  try {
    const response = await chatApi.getChatList()
    sessions.value = response.data || []
  } finally {
    loading.value = false
  }
}

const openSession = (session) => {
  router.push(`/chat/${session.targetUserId}/${session.productId}`)
}

const resolveImage = (session) => {
  const list = normalizeImageList(session.imageList || session.productImages)
  return list[0] || fallbackImage(session.productTitle)
}

onMounted(() => {
  loadSessions()
})
</script>

<style scoped>
.panel {
  background: rgba(255, 255, 255, 0.92);
  border-radius: 28px;
  border: 1px solid rgba(148, 163, 184, 0.16);
  padding: 24px;
}

.header {
  margin-bottom: 20px;
}

.header h2 {
  font-size: 28px;
  margin-bottom: 8px;
}

.header p {
  color: #64748b;
}

.session-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.session-card {
  display: grid;
  grid-template-columns: 120px minmax(0, 1fr);
  gap: 18px;
  padding: 16px;
  border-radius: 24px;
  background: #fff;
  border: 1px solid rgba(148, 163, 184, 0.12);
  cursor: pointer;
}

.session-card img {
  width: 100%;
  height: 100px;
  object-fit: cover;
  border-radius: 18px;
}

.content {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.title-row,
.message-row {
  display: flex;
  justify-content: space-between;
  gap: 12px;
}

.title-row strong {
  display: block;
  font-size: 18px;
  margin-bottom: 6px;
}

.title-row p,
.message-row span,
.right-meta span {
  color: #64748b;
}

.right-meta {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 10px;
}

@media (max-width: 720px) {
  .session-card {
    grid-template-columns: 1fr;
  }

  .title-row,
  .message-row {
    flex-direction: column;
  }

  .right-meta {
    align-items: flex-start;
  }
}
</style>
