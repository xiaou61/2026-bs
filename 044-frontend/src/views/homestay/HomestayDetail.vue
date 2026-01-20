<template>
  <div class="homestay-detail container" v-loading="loading">
    <template v-if="homestay">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ homestay.name }}</el-breadcrumb-item>
      </el-breadcrumb>

      <div class="main-content">
        <div class="left">
          <el-image :src="homestay.coverImage || 'https://via.placeholder.com/600x400'" fit="cover" class="cover" />
          <el-card class="info-card">
            <h1>{{ homestay.name }}</h1>
            <div class="meta">
              <span><el-icon><Location /></el-icon>{{ homestay.province }} {{ homestay.city }} {{ homestay.district }}</span>
              <span><el-icon><Star /></el-icon>{{ homestay.rating }} 分</span>
            </div>
            <el-divider />
            <h3>民宿介绍</h3>
            <p>{{ homestay.description }}</p>
            <el-divider />
            <h3>设施服务</h3>
            <div class="facilities">
              <el-tag v-for="f in facilities" :key="f.id">{{ f.name }}</el-tag>
            </div>
          </el-card>
        </div>

        <div class="right">
          <el-card class="booking-card">
            <h3>选择房型预订</h3>
            <div class="room-list">
              <div v-for="room in roomTypes" :key="room.id" class="room-item" :class="{ active: selectedRoom?.id === room.id }" @click="selectedRoom = room">
                <div class="room-info">
                  <h4>{{ room.name }}</h4>
                  <p>{{ room.area }}㎡ | {{ room.bedType }} | 最多{{ room.maxGuests }}人</p>
                </div>
                <div class="room-price">¥{{ room.price }}/晚</div>
              </div>
            </div>
            <el-divider />
            <el-form label-width="80px">
              <el-form-item label="入住日期">
                <el-date-picker v-model="checkInDate" type="date" placeholder="选择入住日期" :disabled-date="disablePastDate" />
              </el-form-item>
              <el-form-item label="离店日期">
                <el-date-picker v-model="checkOutDate" type="date" placeholder="选择离店日期" :disabled-date="disableCheckoutDate" />
              </el-form-item>
              <el-form-item label="入住人数">
                <el-input-number v-model="guests" :min="1" :max="selectedRoom?.maxGuests || 10" />
              </el-form-item>
              <el-form-item label="联系人">
                <el-input v-model="contactName" placeholder="请输入联系人姓名" />
              </el-form-item>
              <el-form-item label="联系电话">
                <el-input v-model="contactPhone" placeholder="请输入联系电话" />
              </el-form-item>
            </el-form>
            <div class="total" v-if="totalPrice > 0">
              <span>总价：</span>
              <span class="price">¥{{ totalPrice }}</span>
            </div>
            <el-button type="primary" size="large" style="width: 100%" @click="handleBook" :disabled="!canBook">立即预订</el-button>
          </el-card>

          <el-card class="fav-card">
            <el-button :type="isFavorite ? 'danger' : 'default'" @click="toggleFavorite">
              <el-icon><Star /></el-icon>
              {{ isFavorite ? '已收藏' : '收藏' }}
            </el-button>
          </el-card>
        </div>
      </div>

      <el-card class="reviews-card">
        <template #header><h3>用户评价</h3></template>
        <div v-if="reviews.length === 0" class="empty">暂无评价</div>
        <div v-for="review in reviews" :key="review.id" class="review-item">
          <div class="review-header">
            <el-rate v-model="review.rating" disabled />
            <span class="time">{{ review.createTime }}</span>
          </div>
          <p>{{ review.content }}</p>
          <div v-if="review.hostReply" class="reply">
            <strong>房东回复：</strong>{{ review.hostReply }}
          </div>
        </div>
      </el-card>
    </template>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getHomestayDetail } from '@/api/homestay'
import { createBooking } from '@/api/booking'
import { checkFavorite, addFavorite, removeFavorite } from '@/api/favorite'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const homestay = ref<any>(null)
const roomTypes = ref<any[]>([])
const facilities = ref<any[]>([])
const reviews = ref<any[]>([])
const selectedRoom = ref<any>(null)
const checkInDate = ref<Date | null>(null)
const checkOutDate = ref<Date | null>(null)
const guests = ref(1)
const contactName = ref('')
const contactPhone = ref('')
const isFavorite = ref(false)

const totalPrice = computed(() => {
  if (!selectedRoom.value || !checkInDate.value || !checkOutDate.value) return 0
  const nights = Math.ceil((checkOutDate.value.getTime() - checkInDate.value.getTime()) / (1000 * 60 * 60 * 24))
  return nights > 0 ? selectedRoom.value.price * nights : 0
})

const canBook = computed(() => {
  return selectedRoom.value && checkInDate.value && checkOutDate.value && totalPrice.value > 0 && contactName.value && contactPhone.value
})

const disablePastDate = (date: Date) => date < new Date(new Date().setHours(0, 0, 0, 0))
const disableCheckoutDate = (date: Date) => !checkInDate.value || date <= checkInDate.value

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getHomestayDetail(Number(route.params.id))
    homestay.value = res.data.homestay
    roomTypes.value = res.data.roomTypes || []
    facilities.value = res.data.facilities || []
    reviews.value = res.data.reviews?.records || []
    if (roomTypes.value.length > 0) {
      selectedRoom.value = roomTypes.value[0]
    }
    if (userStore.isLogin) {
      const favRes: any = await checkFavorite(Number(route.params.id))
      isFavorite.value = favRes.data
    }
  } finally {
    loading.value = false
  }
}

const handleBook = async () => {
  if (!userStore.isLogin) {
    router.push({ name: 'login', query: { redirect: route.fullPath } })
    return
  }
  try {
    await createBooking({
      homestayId: homestay.value.id,
      roomTypeId: selectedRoom.value.id,
      checkInDate: checkInDate.value!.toISOString().split('T')[0],
      checkOutDate: checkOutDate.value!.toISOString().split('T')[0],
      guests: guests.value,
      contactName: contactName.value,
      contactPhone: contactPhone.value
    })
    ElMessage.success('预订成功')
    router.push('/booking')
  } catch (e) {}
}

const toggleFavorite = async () => {
  if (!userStore.isLogin) {
    router.push({ name: 'login', query: { redirect: route.fullPath } })
    return
  }
  try {
    if (isFavorite.value) {
      await removeFavorite(homestay.value.id)
      isFavorite.value = false
      ElMessage.success('已取消收藏')
    } else {
      await addFavorite(homestay.value.id)
      isFavorite.value = true
      ElMessage.success('收藏成功')
    }
  } catch (e) {}
}

onMounted(() => {
  loadData()
})

watch(() => route.params.id, () => {
  loadData()
})
</script>

<style scoped lang="scss">
.homestay-detail { padding-top: 20px; }
.el-breadcrumb { margin-bottom: 20px; }
.main-content {
  display: flex;
  gap: 20px;
}
.left {
  flex: 1;
  .cover {
    width: 100%;
    height: 400px;
    border-radius: 8px;
    margin-bottom: 20px;
  }
  .info-card {
    h1 { margin-bottom: 12px; }
    .meta {
      display: flex;
      gap: 20px;
      color: #666;
      span { display: flex; align-items: center; gap: 4px; }
    }
    h3 { font-size: 16px; margin-bottom: 12px; }
    .facilities { display: flex; gap: 8px; flex-wrap: wrap; }
  }
}
.right {
  width: 360px;
  .booking-card {
    h3 { margin-bottom: 16px; }
    .room-list {
      .room-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 12px;
        border: 1px solid #eee;
        border-radius: 8px;
        margin-bottom: 8px;
        cursor: pointer;
        &:hover, &.active { border-color: #409eff; background: #f0f9ff; }
        h4 { margin: 0 0 4px; font-size: 14px; }
        p { margin: 0; font-size: 12px; color: #999; }
        .room-price { color: #ff6600; font-weight: bold; }
      }
    }
    .total {
      display: flex;
      justify-content: space-between;
      margin-bottom: 16px;
      font-size: 16px;
      .price { color: #ff6600; font-size: 24px; font-weight: bold; }
    }
  }
  .fav-card { margin-top: 16px; text-align: center; }
}
.reviews-card {
  margin-top: 20px;
  h3 { margin: 0; }
  .empty { color: #999; text-align: center; padding: 40px; }
  .review-item {
    padding: 16px 0;
    border-bottom: 1px solid #eee;
    &:last-child { border-bottom: none; }
    .review-header { display: flex; align-items: center; gap: 12px; margin-bottom: 8px; }
    .time { color: #999; font-size: 12px; }
    p { margin: 8px 0; }
    .reply { background: #f5f5f5; padding: 12px; border-radius: 4px; margin-top: 8px; }
  }
}
</style>
