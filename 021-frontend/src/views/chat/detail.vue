<template>
  <div class="chat-page">
    <section class="panel conversation-panel">
      <div class="conversation-head">
        <div>
          <span class="eyebrow">{{ product.title || '商品会话' }}</span>
          <h2>{{ targetName }}</h2>
          <p>围绕商品进行聊天与议价，系统会按 5 秒自动刷新消息。</p>
        </div>
        <div class="head-actions">
          <el-button @click="router.push(`/product/${route.params.productId}`)">查看商品</el-button>
          <el-button type="primary" @click="bargainDialogVisible = true">发起议价</el-button>
        </div>
      </div>

      <div ref="messageContainer" class="messages" v-loading="loading">
        <article
          v-for="message in messages"
          :key="message.id"
          class="message"
          :class="{ mine: message.senderId === userStore.userInfo.id }"
        >
          <div class="bubble">
            <div class="meta-line">
              <strong>{{ message.senderName }}</strong>
              <span>{{ formatTime(message.createTime) }}</span>
            </div>

            <p>{{ message.content }}</p>

            <div v-if="message.messageType === 'bargain'" class="bargain-box">
              <el-tag size="small" type="warning">
                议价 {{ formatPrice(message.bargainPrice) }} · {{ getBargainStatusLabel(message.bargainStatus) }}
              </el-tag>

              <div
                v-if="message.receiverId === userStore.userInfo.id && message.bargainStatus === 'pending'"
                class="bargain-actions"
              >
                <el-button size="small" type="success" @click="acceptBargain(message.id)">接受</el-button>
                <el-button size="small" @click="rejectBargain(message.id)">拒绝</el-button>
              </div>
            </div>
          </div>
        </article>

        <el-empty v-if="!loading && !messages.length" description="还没有消息，先打个招呼吧" />
      </div>

      <div class="composer">
        <el-input
          v-model="draft"
          type="textarea"
          :rows="3"
          placeholder="输入想聊的内容，按 Enter + Ctrl 也可以发送"
          @keydown.ctrl.enter.prevent="sendMessage"
        />
        <div class="composer-actions">
          <el-button @click="router.push('/chat')">返回列表</el-button>
          <el-button type="primary" :loading="sending" @click="sendMessage">发送消息</el-button>
        </div>
      </div>
    </section>

    <section class="panel side-panel">
      <img :src="productImage" :alt="product.title || '商品图片'" />
      <div>
        <h3>{{ product.title || '商品信息加载中' }}</h3>
        <p>{{ product.description || '这里会显示当前会话关联商品的描述信息。' }}</p>
      </div>
      <div class="side-meta">
        <span>价格：{{ formatPrice(product.price) }}</span>
        <span>成色：{{ product.condition || '--' }}</span>
        <span>状态：{{ getProductStatusLabel(product.status) }}</span>
      </div>
    </section>

    <el-dialog v-model="bargainDialogVisible" title="发起议价" width="420px">
      <el-form label-position="top">
        <el-form-item label="目标价格">
          <el-input-number
            v-model="bargainForm.bargainPrice"
            :min="0.01"
            :precision="2"
            :step="1"
            class="full-width"
          />
        </el-form-item>
        <el-form-item label="说明">
          <el-input v-model="bargainForm.content" type="textarea" :rows="4" placeholder="给对方留一句解释或补充" />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="bargainDialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="bargainSubmitting" @click="sendBargain">发送议价</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, nextTick, onBeforeUnmount, onMounted, ref, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { chatApi } from '@/api/chat'
import { productApi } from '@/api/product'
import { useUserStore } from '@/stores/user'
import {
  fallbackImage,
  formatPrice,
  formatTime,
  getBargainStatusLabel,
  getProductStatusLabel,
  normalizeImageList
} from '@/utils/market'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const sending = ref(false)
const bargainSubmitting = ref(false)
const messages = ref([])
const product = ref({})
const draft = ref('')
const bargainDialogVisible = ref(false)
const messageContainer = ref()
const bargainForm = ref({
  bargainPrice: 0,
  content: ''
})

let timer = null

const targetUserId = computed(() => Number(route.params.userId))
const productId = computed(() => Number(route.params.productId))

const targetName = computed(() => {
  const message = messages.value.find((item) => item.senderId === targetUserId.value || item.receiverId === targetUserId.value)
  if (!message) {
    return product.value.sellerId === targetUserId.value ? product.value.sellerName || '对方用户' : '对方用户'
  }
  return message.senderId === targetUserId.value ? message.senderName : message.receiverName
})

const productImage = computed(() => {
  const list = normalizeImageList(product.value.imageList || product.value.images)
  return list[0] || fallbackImage(product.value.title)
})

const scrollToBottom = async () => {
  await nextTick()
  const element = messageContainer.value
  if (element) {
    element.scrollTop = element.scrollHeight
  }
}

const loadProduct = async () => {
  const response = await productApi.getProductDetail(productId.value)
  product.value = response.data || {}
  bargainForm.value.bargainPrice = Number(product.value.price || 0)
}

const loadMessages = async (scroll = false) => {
  loading.value = true
  try {
    const response = await chatApi.getMessages({
      targetUserId: targetUserId.value,
      productId: productId.value
    })
    messages.value = response.data || []
    await chatApi.markRead({
      targetUserId: targetUserId.value,
      productId: productId.value
    })
    if (scroll) {
      scrollToBottom()
    }
  } finally {
    loading.value = false
  }
}

const startPolling = () => {
  timer = window.setInterval(() => {
    loadMessages()
  }, 5000)
}

const stopPolling = () => {
  if (timer) {
    window.clearInterval(timer)
    timer = null
  }
}

const sendMessage = async () => {
  if (!draft.value.trim()) {
    ElMessage.warning('请输入消息内容')
    return
  }

  sending.value = true
  try {
    await chatApi.sendMessage({
      productId: productId.value,
      receiverId: targetUserId.value,
      content: draft.value.trim()
    })
    draft.value = ''
    await loadMessages(true)
  } finally {
    sending.value = false
  }
}

const sendBargain = async () => {
  bargainSubmitting.value = true
  try {
    await chatApi.bargain({
      productId: productId.value,
      receiverId: targetUserId.value,
      bargainPrice: bargainForm.value.bargainPrice,
      content: bargainForm.value.content
    })
    bargainDialogVisible.value = false
    bargainForm.value.content = ''
    await loadMessages(true)
  } finally {
    bargainSubmitting.value = false
  }
}

const acceptBargain = async (id) => {
  await chatApi.acceptBargain(id)
  ElMessage.success('已接受议价，系统已生成订单')
  await Promise.all([loadProduct(), loadMessages(true)])
}

const rejectBargain = async (id) => {
  await chatApi.rejectBargain(id)
  ElMessage.success('已拒绝议价')
  await loadMessages(true)
}

watch(
  () => [route.params.userId, route.params.productId],
  async () => {
    stopPolling()
    await Promise.all([loadProduct(), loadMessages(true)])
    startPolling()
  }
)

onMounted(async () => {
  await Promise.all([loadProduct(), loadMessages(true)])
  startPolling()
})

onBeforeUnmount(() => {
  stopPolling()
})
</script>

<style scoped>
.chat-page {
  display: grid;
  grid-template-columns: minmax(0, 1.2fr) 320px;
  gap: 20px;
}

.panel {
  background: rgba(255, 255, 255, 0.92);
  border-radius: 28px;
  border: 1px solid rgba(148, 163, 184, 0.16);
  padding: 24px;
}

.conversation-panel {
  display: flex;
  flex-direction: column;
  gap: 18px;
}

.conversation-head {
  display: flex;
  justify-content: space-between;
  gap: 16px;
}

.eyebrow {
  display: inline-flex;
  padding: 6px 12px;
  border-radius: 999px;
  background: rgba(249, 115, 22, 0.12);
  color: #c2410c;
  margin-bottom: 12px;
}

.conversation-head h2 {
  font-size: 30px;
  margin-bottom: 8px;
}

.conversation-head p {
  color: #64748b;
}

.head-actions {
  display: flex;
  gap: 10px;
  align-items: flex-start;
}

.messages {
  min-height: 420px;
  max-height: 620px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 14px;
  padding-right: 6px;
}

.message {
  display: flex;
}

.message.mine {
  justify-content: flex-end;
}

.bubble {
  max-width: min(74%, 560px);
  background: #f8fafc;
  border-radius: 22px;
  padding: 14px 16px;
  border: 1px solid rgba(148, 163, 184, 0.14);
}

.message.mine .bubble {
  background: rgba(255, 247, 237, 0.95);
  border-color: rgba(251, 146, 60, 0.18);
}

.meta-line {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  margin-bottom: 10px;
}

.meta-line span {
  color: #64748b;
  font-size: 12px;
}

.bubble p {
  white-space: pre-wrap;
  line-height: 1.8;
  color: #334155;
}

.bargain-box {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 12px;
}

.bargain-actions {
  display: flex;
  gap: 10px;
}

.composer {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.composer-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.side-panel {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.side-panel img {
  width: 100%;
  height: 220px;
  object-fit: cover;
  border-radius: 20px;
}

.side-panel h3 {
  font-size: 24px;
  margin-bottom: 8px;
}

.side-panel p,
.side-meta span {
  color: #64748b;
  line-height: 1.8;
}

.side-meta {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.full-width {
  width: 100%;
}

@media (max-width: 1080px) {
  .chat-page {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 720px) {
  .conversation-head {
    flex-direction: column;
  }

  .head-actions {
    width: 100%;
    flex-wrap: wrap;
  }

  .bubble {
    max-width: 100%;
  }
}
</style>
