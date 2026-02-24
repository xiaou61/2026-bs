<template>
  <div class="spot-detail" v-if="spot">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <el-image :src="spot.coverImage" class="cover-img" fit="cover" />
          <h1>{{ spot.name }}</h1>
          <div class="tags">
            <el-tag>{{ spot.category }}</el-tag>
            <el-tag type="info">{{ spot.openTime }}</el-tag>
          </div>
          <p class="address"><el-icon><Location /></el-icon>{{ spot.address }}</p>
          <el-divider />
          <h3>景点介绍</h3>
          <p class="desc">{{ spot.description }}</p>
          <el-divider />
          <h3>游玩攻略</h3>
          <p class="tips">{{ spot.tips || '暂无攻略' }}</p>
        </el-card>
        <el-card class="review-card">
          <template #header><span>游客评价</span></template>
          <div v-if="reviews.length">
            <div v-for="r in reviews" :key="r.id" class="review-item">
              <div class="review-header">
                <span class="nickname">{{ r.nickname }}</span>
                <el-rate v-model="r.rating" disabled />
                <span class="time">{{ r.createTime }}</span>
              </div>
              <p class="content">{{ r.content }}</p>
            </div>
          </div>
          <el-empty v-else description="暂无评价" />
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="ticket-card">
          <template #header><span>门票预订</span></template>
          <div v-if="tickets.length">
            <div v-for="t in tickets" :key="t.id" class="ticket-item">
              <div class="ticket-info">
                <span class="name">{{ t.name }}</span>
                <span class="price">¥{{ t.price }}</span>
              </div>
              <p class="desc">{{ t.description }}</p>
              <el-button type="primary" size="small" @click="buyTicket(t)">立即预订</el-button>
            </div>
          </div>
          <el-empty v-else description="暂无可售票种" />
        </el-card>
        <el-card class="action-card">
          <el-button :type="isFavorite ? 'warning' : 'default'" @click="toggleFavorite" style="width:100%">
            <el-icon><Star /></el-icon>{{ isFavorite ? '已收藏' : '收藏景点' }}
          </el-button>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog v-model="orderDialog" title="门票预订" width="400px">
      <el-form :model="orderForm" label-width="80px">
        <el-form-item label="票种">{{ currentTicket?.name }}</el-form-item>
        <el-form-item label="单价">¥{{ currentTicket?.price }}</el-form-item>
        <el-form-item label="数量"><el-input-number v-model="orderForm.quantity" :min="1" :max="10" /></el-form-item>
        <el-form-item label="游玩日期"><el-date-picker v-model="orderForm.visitDate" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="总价"><span style="color:#f56c6c;font-size:20px;font-weight:bold">¥{{ (currentTicket?.price || 0) * orderForm.quantity }}</span></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="orderDialog = false">取消</el-button>
        <el-button type="primary" @click="submitOrder">确认预订</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getSpotDetail, getTicketsBySpot, getReviewsBySpot, createTicketOrder, checkFavorite, addFavorite, removeFavorite } from '../api'
import { ElMessage } from 'element-plus'

const route = useRoute()
const spot = ref(null)
const tickets = ref([])
const reviews = ref([])
const isFavorite = ref(false)
const orderDialog = ref(false)
const currentTicket = ref(null)
const orderForm = ref({ quantity: 1, visitDate: '' })

const loadData = async () => {
  const id = route.params.id
  const res = await getSpotDetail(id)
  spot.value = res.data
  const ticketRes = await getTicketsBySpot(id)
  tickets.value = ticketRes.data || []
  const reviewRes = await getReviewsBySpot(id)
  reviews.value = reviewRes.data || []
  const favRes = await checkFavorite(id, 'spot')
  isFavorite.value = favRes.data
}

const buyTicket = (t) => {
  currentTicket.value = t
  orderForm.value = { quantity: 1, visitDate: '' }
  orderDialog.value = true
}

const submitOrder = async () => {
  if (!orderForm.value.visitDate) {
    ElMessage.warning('请选择游玩日期')
    return
  }
  await createTicketOrder({ ticketTypeId: currentTicket.value.id, quantity: orderForm.value.quantity, visitDate: orderForm.value.visitDate })
  ElMessage.success('预订成功')
  orderDialog.value = false
}

const toggleFavorite = async () => {
  if (isFavorite.value) {
    await removeFavorite(spot.value.id, 'spot')
    isFavorite.value = false
    ElMessage.success('已取消收藏')
  } else {
    await addFavorite({ targetId: spot.value.id, targetType: 'spot' })
    isFavorite.value = true
    ElMessage.success('收藏成功')
  }
}

onMounted(() => loadData())
</script>

<style scoped>
.cover-img { width: 100%; height: 400px; border-radius: 8px; }
h1 { margin: 20px 0 10px; }
.tags { display: flex; gap: 10px; margin-bottom: 10px; }
.address { color: #666; display: flex; align-items: center; gap: 4px; }
.desc, .tips { color: #666; line-height: 1.8; white-space: pre-wrap; }
.review-card { margin-top: 20px; }
.review-item { padding: 15px 0; border-bottom: 1px solid #eee; }
.review-item:last-child { border-bottom: none; }
.review-header { display: flex; align-items: center; gap: 10px; margin-bottom: 10px; }
.nickname { font-weight: bold; }
.time { color: #999; font-size: 12px; margin-left: auto; }
.content { color: #666; }
.ticket-card { margin-bottom: 20px; }
.ticket-item { padding: 15px 0; border-bottom: 1px solid #eee; }
.ticket-item:last-child { border-bottom: none; }
.ticket-info { display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px; }
.ticket-info .name { font-weight: bold; }
.ticket-info .price { color: #f56c6c; font-size: 18px; }
.ticket-item .desc { color: #999; font-size: 13px; margin-bottom: 10px; }
.action-card { margin-bottom: 20px; }
</style>
