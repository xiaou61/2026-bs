<template>
  <div class="profile-page">
    <section class="panel profile-panel">
      <div class="section-head">
        <div>
          <span class="eyebrow">个人中心</span>
          <h2>{{ userStore.userInfo.realName || userStore.userInfo.username || '校园用户' }}</h2>
          <p>{{ creditMeta.label }} · 当前信用分 {{ userStore.userInfo.creditScore ?? 100 }}</p>
        </div>
        <el-avatar :size="72" :src="userStore.userInfo.avatar || ''">
          {{ (userStore.userInfo.realName || userStore.userInfo.username || '我').slice(0, 1) }}
        </el-avatar>
      </div>

      <el-tabs v-model="activeTab" @tab-change="handleTabChange">
        <el-tab-pane label="资料设置" name="profile">
          <el-form ref="formRef" :model="profileForm" :rules="rules" label-position="top">
            <div class="grid two-col">
              <el-form-item label="用户名" prop="username">
                <el-input v-model="profileForm.username" />
              </el-form-item>
              <el-form-item label="真实姓名" prop="realName">
                <el-input v-model="profileForm.realName" />
              </el-form-item>
            </div>

            <div class="grid two-col">
              <el-form-item label="学号" prop="studentId">
                <el-input v-model="profileForm.studentId" />
              </el-form-item>
              <el-form-item label="手机号" prop="phone">
                <el-input v-model="profileForm.phone" />
              </el-form-item>
            </div>

            <div class="grid two-col">
              <el-form-item label="学院" prop="college">
                <el-input v-model="profileForm.college" />
              </el-form-item>
              <el-form-item label="宿舍" prop="dorm">
                <el-input v-model="profileForm.dorm" />
              </el-form-item>
            </div>

            <el-form-item label="新密码">
              <el-input
                v-model="profileForm.password"
                type="password"
                show-password
                placeholder="不修改密码可留空"
              />
            </el-form-item>

            <div class="submit-row">
              <el-button type="primary" :loading="savingProfile" @click="saveProfile">保存资料</el-button>
            </div>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="我的买入" name="buy">
          <div class="order-list" v-loading="buyLoading">
            <article v-for="order in buyOrders" :key="order.id" class="order-card">
              <div class="order-head">
                <div>
                  <strong>{{ order.productTitle }}</strong>
                  <p>订单号：{{ order.orderNo }}</p>
                </div>
                <el-tag :type="order.status === 'pending' ? 'warning' : order.status === 'completed' ? 'success' : 'info'">
                  {{ getOrderStatusLabel(order.status) }}
                </el-tag>
              </div>

              <div class="order-meta">
                <span>卖家：{{ order.sellerName }}</span>
                <span>成交价：{{ formatPrice(order.price) }}</span>
                <span>{{ formatTime(order.createTime) }}</span>
              </div>

              <div class="order-actions">
                <el-button link type="primary" @click="router.push(`/product/${order.productId}`)">查看商品</el-button>
                <el-button link type="primary" @click="router.push(`/chat/${order.sellerId}/${order.productId}`)">联系卖家</el-button>
                <el-button link type="success" :disabled="order.status !== 'pending'" @click="completeOrder(order.id)">
                  确认收货
                </el-button>
                <el-button link type="warning" :disabled="order.status !== 'pending'" @click="cancelOrder(order.id)">
                  取消订单
                </el-button>
                <el-button
                  link
                  type="danger"
                  :disabled="order.status !== 'completed'"
                  @click="openRateDialog(order, 'buyer')"
                >
                  评价卖家
                </el-button>
              </div>
            </article>

            <el-empty v-if="!buyLoading && !buyOrders.length" description="暂时还没有买入订单" />
          </div>
        </el-tab-pane>

        <el-tab-pane label="我的卖出" name="sell">
          <div class="order-list" v-loading="sellLoading">
            <article v-for="order in sellOrders" :key="order.id" class="order-card">
              <div class="order-head">
                <div>
                  <strong>{{ order.productTitle }}</strong>
                  <p>订单号：{{ order.orderNo }}</p>
                </div>
                <el-tag :type="order.status === 'pending' ? 'warning' : order.status === 'completed' ? 'success' : 'info'">
                  {{ getOrderStatusLabel(order.status) }}
                </el-tag>
              </div>

              <div class="order-meta">
                <span>买家：{{ order.buyerName }}</span>
                <span>成交价：{{ formatPrice(order.price) }}</span>
                <span>{{ formatTime(order.createTime) }}</span>
              </div>

              <div class="order-actions">
                <el-button link type="primary" @click="router.push(`/product/${order.productId}`)">查看商品</el-button>
                <el-button link type="primary" @click="router.push(`/chat/${order.buyerId}/${order.productId}`)">联系买家</el-button>
                <el-button link type="warning" :disabled="order.status !== 'pending'" @click="cancelOrder(order.id)">
                  取消订单
                </el-button>
                <el-button
                  link
                  type="danger"
                  :disabled="order.status !== 'completed'"
                  @click="openRateDialog(order, 'seller')"
                >
                  评价买家
                </el-button>
              </div>
            </article>

            <el-empty v-if="!sellLoading && !sellOrders.length" description="暂时还没有卖出订单" />
          </div>
        </el-tab-pane>
      </el-tabs>
    </section>

    <el-dialog v-model="rateDialogVisible" title="订单评价" width="420px">
      <el-form label-position="top">
        <el-form-item label="评分">
          <el-rate v-model="rateForm.rating" />
        </el-form-item>
        <el-form-item label="评价内容">
          <el-input v-model="rateForm.comment" type="textarea" :rows="4" placeholder="说说这次交易体验" />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="rateDialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="rateSubmitting" @click="submitRate">提交评价</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { orderApi } from '@/api/order'
import { useUserStore } from '@/stores/user'
import { formatPrice, formatTime, getCreditMeta, getOrderStatusLabel } from '@/utils/market'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const formRef = ref()
const savingProfile = ref(false)
const buyLoading = ref(false)
const sellLoading = ref(false)
const rateDialogVisible = ref(false)
const rateSubmitting = ref(false)
const rateTargetOrderId = ref(null)

const activeTab = ref(route.query.tab || 'profile')
const buyOrders = ref([])
const sellOrders = ref([])

const profileForm = reactive({
  username: '',
  password: '',
  studentId: '',
  realName: '',
  college: '',
  dorm: '',
  phone: ''
})

const rateForm = reactive({
  rating: 5,
  comment: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  studentId: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  realName: [{ required: true, message: '请输入真实姓名', trigger: 'blur' }],
  college: [{ required: true, message: '请输入学院', trigger: 'blur' }],
  dorm: [{ required: true, message: '请输入宿舍号', trigger: 'blur' }],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ]
}

const creditMeta = computed(() => getCreditMeta(userStore.userInfo.creditScore ?? 100))

const syncProfileForm = () => {
  profileForm.username = userStore.userInfo.username || ''
  profileForm.password = ''
  profileForm.studentId = userStore.userInfo.studentId || ''
  profileForm.realName = userStore.userInfo.realName || ''
  profileForm.college = userStore.userInfo.college || ''
  profileForm.dorm = userStore.userInfo.dorm || ''
  profileForm.phone = userStore.userInfo.phone || ''
}

const loadProfile = async () => {
  await userStore.getUserInfo()
  syncProfileForm()
}

const loadBuyOrders = async () => {
  buyLoading.value = true
  try {
    const response = await orderApi.getMyBuyOrders({ current: 1, size: 20 })
    buyOrders.value = response.data?.records || []
  } finally {
    buyLoading.value = false
  }
}

const loadSellOrders = async () => {
  sellLoading.value = true
  try {
    const response = await orderApi.getMySellOrders({ current: 1, size: 20 })
    sellOrders.value = response.data?.records || []
  } finally {
    sellLoading.value = false
  }
}

const loadOrders = async () => {
  await Promise.all([loadBuyOrders(), loadSellOrders()])
}

const handleTabChange = (tab) => {
  router.replace({ path: '/profile', query: tab === 'profile' ? {} : { tab } })
}

const saveProfile = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) {
    return
  }

  savingProfile.value = true
  try {
    await userStore.updateUserInfo(profileForm)
    syncProfileForm()
    ElMessage.success('资料已更新')
  } finally {
    savingProfile.value = false
  }
}

const completeOrder = async (orderId) => {
  await ElMessageBox.confirm('确认线下交易已完成并收货吗？', '确认收货', {
    type: 'warning'
  })
  await orderApi.completeOrder(orderId)
  ElMessage.success('订单已完成')
  loadOrders()
}

const cancelOrder = async (orderId) => {
  await ElMessageBox.confirm('取消后商品会恢复为在售状态，确认继续吗？', '取消订单', {
    type: 'warning'
  })
  await orderApi.cancelOrder(orderId)
  ElMessage.success('订单已取消')
  loadOrders()
}

const openRateDialog = (order) => {
  rateTargetOrderId.value = order.id
  rateForm.rating = 5
  rateForm.comment = ''
  rateDialogVisible.value = true
}

const submitRate = async () => {
  rateSubmitting.value = true
  try {
    await orderApi.rateOrder(rateTargetOrderId.value, {
      rating: rateForm.rating,
      comment: rateForm.comment
    })
    ElMessage.success('评价成功')
    rateDialogVisible.value = false
    loadOrders()
    await loadProfile()
  } finally {
    rateSubmitting.value = false
  }
}

watch(
  () => route.query.tab,
  (value) => {
    activeTab.value = value || 'profile'
  }
)

onMounted(async () => {
  await loadProfile()
  await loadOrders()
})
</script>

<style scoped>
.profile-panel {
  background: rgba(255, 255, 255, 0.92);
  border-radius: 28px;
  border: 1px solid rgba(148, 163, 184, 0.16);
  padding: 24px;
}

.section-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 22px;
}

.eyebrow {
  display: inline-flex;
  padding: 6px 12px;
  border-radius: 999px;
  background: rgba(249, 115, 22, 0.12);
  color: #c2410c;
  margin-bottom: 12px;
}

.section-head h2 {
  font-size: 32px;
  margin-bottom: 8px;
}

.section-head p {
  color: #64748b;
}

.grid {
  display: grid;
  gap: 18px;
}

.two-col {
  grid-template-columns: repeat(2, minmax(0, 1fr));
}

.submit-row {
  display: flex;
  justify-content: flex-end;
}

.order-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.order-card {
  border-radius: 22px;
  border: 1px solid rgba(148, 163, 184, 0.16);
  background: #fff;
  padding: 18px;
}

.order-head,
.order-meta,
.order-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.order-head {
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.order-head strong {
  display: block;
  font-size: 18px;
  margin-bottom: 6px;
}

.order-head p,
.order-meta span {
  color: #64748b;
}

.order-actions {
  margin-top: 14px;
}

@media (max-width: 720px) {
  .section-head,
  .order-head {
    flex-direction: column;
    align-items: flex-start;
  }

  .two-col {
    grid-template-columns: 1fr;
  }
}
</style>
