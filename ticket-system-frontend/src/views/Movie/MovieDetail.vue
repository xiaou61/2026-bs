<template>
  <div class="movie-detail-container">
    <el-card class="movie-info">
      <div class="movie-content">
        <el-image :src="movie.poster" fit="cover" class="movie-poster" />
        <div class="movie-info-content">
          <h1>{{ movie.title }}</h1>
          <div class="info-item">
            <span class="label">类型：</span>
            <span>{{ movie.genre }}</span>
          </div>
          <div class="info-item">
            <span class="label">时长：</span>
            <span>{{ movie.duration }}分钟</span>
          </div>
          <div class="info-item">
            <span class="label">导演：</span>
            <span>{{ movie.director }}</span>
          </div>
          <div class="info-item">
            <span class="label">主演：</span>
            <span>{{ movie.actors }}</span>
          </div>
          <div class="info-item">
            <span class="label">上映时间：</span>
            <span>{{ movie.releaseDate }}</span>
          </div>
          <div class="info-item">
            <span class="label">评分：</span>
            <el-rate v-model="movie.rating" disabled show-score text-color="#ff9900" />
          </div>
          <div class="info-item">
            <span class="label">状态：</span>
            <el-tag :type="movie.status === 'showing' ? 'success' : movie.status === 'upcoming' ? 'warning' : 'info'">
              {{ movie.status === 'showing' ? '正在热映' : movie.status === 'upcoming' ? '即将上映' : '已下映' }}
            </el-tag>
          </div>
          <div class="info-item description">
            <span class="label">简介：</span>
            <p>{{ movie.description }}</p>
          </div>
          <el-button type="primary" size="large" @click="handleBuyTicket">立即购票</el-button>
        </div>
      </div>
    </el-card>

    <el-card class="showtime-list" style="margin-top: 20px">
      <template #header>
        <span>排期场次</span>
      </template>
      <el-table :data="showtimeList" style="width: 100%">
        <el-table-column prop="cinemaName" label="影院" width="200" />
        <el-table-column prop="hallName" label="影厅" width="150" />
        <el-table-column prop="showDate" label="放映日期" width="150" />
        <el-table-column prop="showTime" label="放映时间" width="120" />
        <el-table-column prop="price" label="票价" width="120">
          <template #default="{ row }">
            ¥{{ row.price }}
          </template>
        </el-table-column>
        <el-table-column prop="availableSeats" label="余票" width="100" />
        <el-table-column label="操作" fixed="right" width="120">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleSelectSeat(row)">选座购票</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card class="comment-section" style="margin-top: 20px">
      <template #header>
        <div class="comment-header">
          <span>用户评论</span>
          <el-button type="primary" @click="showCommentDialog = true">发表评论</el-button>
        </div>
      </template>
      <div v-for="comment in commentList" :key="comment.id" class="comment-item">
        <div class="comment-user">
          <span class="username">{{ comment.username }}</span>
          <el-rate v-model="comment.rating" disabled size="small" />
        </div>
        <p class="comment-content">{{ comment.content }}</p>
        <span class="comment-time">{{ comment.createTime }}</span>
      </div>
      <el-empty v-if="commentList.length === 0" description="暂无评论" />
    </el-card>

    <el-dialog v-model="showCommentDialog" title="发表评论" width="500px">
      <el-form :model="commentForm" label-width="80px">
        <el-form-item label="评分">
          <el-rate v-model="commentForm.rating" />
        </el-form-item>
        <el-form-item label="评论内容">
          <el-input v-model="commentForm.content" type="textarea" :rows="5" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCommentDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSubmitComment">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { movieApi, showtimeApi, commentApi } from '@/api'

const route = useRoute()
const router = useRouter()

const movie = ref({
  id: null,
  title: '',
  poster: '',
  genre: '',
  duration: 0,
  director: '',
  actors: '',
  releaseDate: '',
  rating: 0,
  status: '',
  description: ''
})

const showtimeList = ref([])
const commentList = ref([])

const showCommentDialog = ref(false)
const commentForm = ref({
  rating: 5,
  content: ''
})

const loadMovieDetail = async () => {
  try {
    const res = await movieApi.getMovieDetail(route.params.id)
    movie.value = res.data
  } catch (error) {
    ElMessage.error('获取电影信息失败')
  }
}

const loadShowtimes = async () => {
  try {
    const res = await showtimeApi.getShowtimeList({ movieId: route.params.id })
    showtimeList.value = res.data
  } catch (error) {
    ElMessage.error('获取场次信息失败')
  }
}

const loadComments = async () => {
  try {
    const res = await commentApi.getCommentsByMovie(route.params.id)
    commentList.value = res.data
  } catch (error) {
    ElMessage.error('获取评论失败')
  }
}

const handleBuyTicket = () => {
  if (showtimeList.value.length > 0) {
    handleSelectSeat(showtimeList.value[0])
  } else {
    ElMessage.warning('暂无可选场次')
  }
}

const handleSelectSeat = (showtime) => {
  router.push(`/showtime/seat-selection/${showtime.id}`)
}

const handleSubmitComment = async () => {
  try {
    await commentApi.addComment({
      movieId: route.params.id,
      ...commentForm.value
    })
    ElMessage.success('评论提交成功，待审核')
    showCommentDialog.value = false
    commentForm.value = { rating: 5, content: '' }
    loadComments()
  } catch (error) {
    ElMessage.error('评论提交失败')
  }
}

onMounted(() => {
  loadMovieDetail()
  loadShowtimes()
  loadComments()
})
</script>

<style scoped>
.movie-detail-container {
  padding: 20px;
}

.movie-content {
  display: flex;
  gap: 30px;
}

.movie-poster {
  width: 300px;
  height: 400px;
  border-radius: 8px;
}

.movie-info-content {
  flex: 1;
}

.movie-info-content h1 {
  margin: 0 0 20px 0;
  font-size: 32px;
}

.info-item {
  margin-bottom: 15px;
  font-size: 16px;
}

.info-item .label {
  font-weight: bold;
  color: #666;
}

.info-item.description p {
  margin-top: 10px;
  line-height: 1.8;
  color: #333;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.comment-item {
  padding: 15px 0;
  border-bottom: 1px solid #eee;
}

.comment-item:last-child {
  border-bottom: none;
}

.comment-user {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 10px;
}

.username {
  font-weight: bold;
  color: #409eff;
}

.comment-content {
  margin: 0 0 10px 0;
  line-height: 1.6;
}

.comment-time {
  font-size: 12px;
  color: #999;
}
</style>
