<template>
  <div class="my-booking container">
    <h2>我的订单</h2>
    <el-tabs v-model="activeTab" @tab-change="handleTabChange">
      <el-tab-pane label="全部" name="all" />
      <el-tab-pane label="待确认" name="1" />
      <el-tab-pane label="已确认" name="2" />
      <el-tab-pane label="已完成" name="4" />
      <el-tab-pane label="已取消" name="5" />
    </el-tabs>
    <div v-loading="loading">
      <el-empty v-if="!loading && bookingList.length === 0" description="暂无订单" />
      <el-card v-for="item in bookingList" :key="item.id" class="booking-card">
        <div class="booking-header">
          <span class="order-no">订单号：{{ item.orderNo }}</span>
          <el-tag :type="getStatusType(item.status)">{{ getStatusText(item.status) }}</el-tag>
        </div>
        <div class="booking-body">
          <div class="info">
            <p><strong>入住日期：</strong>{{ item.checkInDate }} 至 {{ item.checkOutDate }}</p>
            <p><strong>入住人数：</strong>{{ item.guests }}人 | {{ item.nights }}晚</p>
            <p><strong>联系人：</strong>{{ item.contactName }} {{ item.contactPhone }}</p>
          </div>
          <div class="price">
            <span>¥{{ item.totalPrice }}</span>
          </div>
        </div>
        <div class="booking-footer">
          <el-button v-if="item.status < 3" type="danger" text @click="handleCancel(item)">取消订单</el-button>
          <el-button v-if="item.status === 4" type="primary" text @click="handleReview(item)">去评价</el-button>
        </div>
      </el-card>
      <div class="pagination">
        <el-pagination v-model:current-page="current" :total="total" :page-size="10" layout="prev, pager, next" @change="loadData" />
      </div>
    </div>

    <el-dialog v-model="reviewVisible" title="评价订单" width="500px">
      <el-form label-width="80px">
        <el-form-item label="评分">
          <el-rate v-model="reviewForm.rating" />
        </el-form-item>
        <el-form-item label="评价内容">
          <el-input v-model="reviewForm.content" type="textarea" rows="4" placeholder="请输入您的评价" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="reviewVisible = false">取消</el-button>
        <el-button type="primary" @click="submitReview">提交评价</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMyBookings, cancelBooking } from '@/api/booking'
import { addReview } from '@/api/review'

const loading = ref(false)
const bookingList = ref<any[]>([])
const current = ref(1)
const total = ref(0)
const activeTab = ref('all')
const reviewVisible = ref(false)
const reviewForm = reactive({ bookingId: 0, rating: 5, content: '' })

const statusMap: Record<number, string> = {
  0: '待支付', 1: '待确认', 2: '已确认', 3: '进行中', 4: '已完成', 5: '已取消'
}
const statusTypeMap: Record<number, string> = {
  0: 'info', 1: 'warning', 2: 'primary', 3: 'primary', 4: 'success', 5: 'info'
}
const getStatusText = (status: number) => statusMap[status] || '未知'
const getStatusType = (status: number) => statusTypeMap[status] || 'info'

const loadData = async () => {
  loading.value = true
  try {
    const params: any = { current: current.value, size: 10 }
    if (activeTab.value !== 'all') params.status = Number(activeTab.value)
    const res: any = await getMyBookings(params)
    bookingList.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleTabChange = () => {
  current.value = 1
  loadData()
}

const handleCancel = async (item: any) => {
  try {
    await ElMessageBox.confirm('确定要取消此订单吗？', '提示')
    await cancelBooking(item.id)
    ElMessage.success('订单已取消')
    loadData()
  } catch (e) {}
}

const handleReview = (item: any) => {
  reviewForm.bookingId = item.id
  reviewForm.rating = 5
  reviewForm.content = ''
  reviewVisible.value = true
}

const submitReview = async () => {
  try {
    await addReview(reviewForm)
    ElMessage.success('评价成功')
    reviewVisible.value = false
    loadData()
  } catch (e) {}
}

onMounted(() => {
  loadData()
})
</script>

<style scoped lang="scss">
.my-booking {
  padding-top: 20px;
  h2 { margin-bottom: 20px; }
}
.booking-card {
  margin-bottom: 16px;
  .booking-header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 12px;
    .order-no { color: #666; }
  }
  .booking-body {
    display: flex;
    justify-content: space-between;
    .info p { margin: 4px 0; color: #333; }
    .price {
      font-size: 24px;
      color: #ff6600;
      font-weight: bold;
    }
  }
  .booking-footer {
    margin-top: 12px;
    text-align: right;
  }
}
.pagination { text-align: center; margin-top: 20px; }
</style>
