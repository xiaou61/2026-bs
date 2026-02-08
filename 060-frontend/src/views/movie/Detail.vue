<template>
  <div v-if="movie">
    <el-card style="margin-bottom:20px">
      <el-row :gutter="20">
        <el-col :span="16">
          <h2>{{ movie.title }}</h2>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="导演">{{ movie.director }}</el-descriptions-item>
            <el-descriptions-item label="演员">{{ movie.actors }}</el-descriptions-item>
            <el-descriptions-item label="分类">{{ movie.categoryName }}</el-descriptions-item>
            <el-descriptions-item label="时长">{{ movie.duration }}分钟</el-descriptions-item>
            <el-descriptions-item label="评分"><el-rate :model-value="movie.score / 2" disabled allow-half /> {{ movie.score }}分</el-descriptions-item>
            <el-descriptions-item label="上映日期">{{ movie.releaseDate }}</el-descriptions-item>
          </el-descriptions>
          <p style="margin-top:15px;color:#666">{{ movie.description }}</p>
          <el-button :type="isFav ? 'warning' : 'default'" @click="handleFav" style="margin-top:10px">{{ isFav ? '已收藏' : '收藏' }}</el-button>
        </el-col>
      </el-row>
    </el-card>
    <el-card style="margin-bottom:20px">
      <h3>场次信息</h3>
      <el-table :data="showtimes" stripe>
        <el-table-column prop="cinemaName" label="影院" />
        <el-table-column prop="hallName" label="影厅" />
        <el-table-column prop="showDate" label="日期" width="110" />
        <el-table-column label="时间" width="130"><template #default="{ row }">{{ row.startTime }} - {{ row.endTime }}</template></el-table-column>
        <el-table-column prop="price" label="票价" width="80" />
        <el-table-column prop="availableSeats" label="余座" width="70" />
        <el-table-column label="操作" width="100"><template #default="{ row }"><el-button type="primary" size="small" @click="handleBuy(row)">购票</el-button></template></el-table-column>
      </el-table>
    </el-card>
    <el-card>
      <h3>影评</h3>
      <div style="margin-bottom:15px">
        <el-rate v-model="reviewForm.rating" :max="10" />
        <el-input v-model="reviewForm.content" type="textarea" :rows="2" placeholder="写下你的评论..." style="margin-top:10px" />
        <el-button type="primary" style="margin-top:10px" @click="handleReview">发表评论</el-button>
      </div>
      <el-divider />
      <div v-for="r in reviews" :key="r.id" style="margin-bottom:15px;padding-bottom:15px;border-bottom:1px solid #eee">
        <div style="display:flex;justify-content:space-between"><strong>{{ r.username }}</strong><span style="color:#999">{{ r.createTime }}</span></div>
        <el-rate :model-value="r.rating / 2" disabled allow-half style="margin:5px 0" />
        <p>{{ r.content }}</p>
      </div>
    </el-card>
    <el-dialog v-model="buyDialog" title="选座购票" width="400px">
      <el-form :model="orderForm" label-width="80px">
        <el-form-item label="场次">{{ currentShowtime?.cinemaName }} {{ currentShowtime?.hallName }} {{ currentShowtime?.startTime }}</el-form-item>
        <el-form-item label="座位"><el-input v-model="orderForm.seats" placeholder="例如: 3排5座,3排6座" /></el-form-item>
        <el-form-item label="座位数"><el-input-number v-model="orderForm.seatCount" :min="1" :max="6" /></el-form-item>
        <el-form-item label="总价">¥{{ (currentShowtime?.price * orderForm.seatCount).toFixed(2) }}</el-form-item>
      </el-form>
      <template #footer><el-button @click="buyDialog = false">取消</el-button><el-button type="primary" @click="handleOrder">确认下单</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getMovieById, getShowtimeByMovie, getReviewsByMovie, addReview, checkFavorite, toggleFavorite, createOrder } from '../../api'

const route = useRoute()
const movie = ref(null)
const showtimes = ref([])
const reviews = ref([])
const isFav = ref(false)
const buyDialog = ref(false)
const currentShowtime = ref(null)
const reviewForm = reactive({ rating: 8, content: '' })
const orderForm = reactive({ seats: '', seatCount: 1 })

const loadData = async () => {
  const id = route.params.id
  const m = await getMovieById(id); movie.value = m.data
  const s = await getShowtimeByMovie(id); showtimes.value = s.data
  const r = await getReviewsByMovie(id); reviews.value = r.data
  const f = await checkFavorite(id); isFav.value = f.data
}

const handleFav = async () => { await toggleFavorite(route.params.id); isFav.value = !isFav.value; ElMessage.success(isFav.value ? '已收藏' : '已取消收藏') }
const handleReview = async () => {
  if (!reviewForm.content) { ElMessage.warning('请输入评论内容'); return }
  await addReview({ movieId: route.params.id, rating: reviewForm.rating, content: reviewForm.content })
  ElMessage.success('评论成功'); reviewForm.content = ''; loadData()
}
const handleBuy = (row) => { currentShowtime.value = row; orderForm.seats = ''; orderForm.seatCount = 1; buyDialog.value = true }
const handleOrder = async () => {
  if (!orderForm.seats) { ElMessage.warning('请输入座位信息'); return }
  await createOrder({ showtimeId: currentShowtime.value.id, seats: orderForm.seats, seatCount: orderForm.seatCount })
  ElMessage.success('下单成功'); buyDialog.value = false; loadData()
}

onMounted(loadData)
</script>
