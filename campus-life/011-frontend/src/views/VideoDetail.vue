<template>
  <div class="detail-container" v-if="video">
    <div class="video-section">
      <video
        :src="video.videoUrl"
        :poster="video.coverUrl"
        class="video-player"
        controls
        autoplay
      ></video>
    </div>
    
    <div class="info-section">
      <div class="video-header">
        <div class="author-info" @click="goToUser(video.user.id)">
          <el-avatar :src="video.user.avatar" :size="50" />
          <div>
            <div class="nickname">{{ video.user.nickname }}</div>
            <div class="time">{{ video.publishTime }}</div>
          </div>
        </div>
        
        <el-button
          v-if="!video.user.isFollow && video.user.id !== userStore.userId"
          type="primary"
          @click="handleFollow"
        >
          关注
        </el-button>
      </div>
      
      <div class="video-info">
        <h2>{{ video.title }}</h2>
        <p class="description">{{ video.description }}</p>
        
        <div class="topics">
          <el-tag
            v-for="topic in video.topics"
            :key="topic.id"
            @click="goToTopic(topic.id)"
          >
            {{ topic.topicName }}
          </el-tag>
        </div>
        
        <div class="stats">
          <span><el-icon><View /></el-icon> {{ video.playCount }} 播放</span>
          <span><el-icon><Star /></el-icon> {{ video.likeCount }} 点赞</span>
          <span><el-icon><ChatDotRound /></el-icon> {{ video.commentCount }} 评论</span>
          <span><el-icon><Share /></el-icon> {{ video.shareCount }} 转发</span>
        </div>
        
        <div class="actions">
          <el-button @click="handleLike" :type="video.isLike ? 'primary' : 'default'">
            <el-icon><Star /></el-icon> {{ video.isLike ? '已点赞' : '点赞' }}
          </el-button>
          <el-button @click="handleCollect" :type="video.isCollect ? 'primary' : 'default'">
            <el-icon><Collection /></el-icon> {{ video.isCollect ? '已收藏' : '收藏' }}
          </el-button>
          <el-button @click="handleShare">
            <el-icon><Share /></el-icon> 分享
          </el-button>
        </div>
      </div>
      
      <div class="comment-section">
        <h3>评论 {{ comments.length }}</h3>
        
        <div class="comment-input">
          <el-input
            v-model="commentText"
            placeholder="写下你的评论..."
            type="textarea"
            :rows="3"
          />
          <el-button type="primary" @click="handleComment">发表评论</el-button>
        </div>
        
        <div class="comment-list">
          <div v-for="comment in comments" :key="comment.id" class="comment-item">
            <el-avatar :src="comment.user.avatar" :size="40" />
            <div class="comment-content">
              <div class="comment-header">
                <span class="nickname">{{ comment.user.nickname }}</span>
                <span class="time">{{ comment.createTime }}</span>
              </div>
              <div class="content">{{ comment.content }}</div>
              <div class="comment-actions">
                <span :class="{ active: comment.isLike }" @click="toggleCommentLike(comment)">
                  <el-icon><Star /></el-icon>
                  {{ comment.isLike ? '已赞' : '点赞' }} {{ comment.likeCount }}
                </span>
                <span @click="handleReply(comment)">回复</span>
                <span v-if="comment.replyCount > 0" @click="toggleReplies(comment)">
                  {{ comment.showReplies ? '收起回复' : `查看回复（${comment.replyCount}）` }}
                </span>
              </div>
              <div v-if="comment.showReplies && comment.replies?.length" class="reply-list">
                <div v-for="reply in comment.replies" :key="reply.id" class="reply-item">
                  <span class="reply-user">{{ reply.user.nickname }}</span>
                  <span v-if="reply.replyToUser"> 回复 {{ reply.replyToUser.nickname }}</span>
                  <span>：{{ reply.content }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="related-section">
        <div class="section-header">
          <h3>相关推荐</h3>
          <span>看看同话题或同作者的更多内容</span>
        </div>
        <div v-if="relatedVideos.length > 0" class="related-grid">
          <div
            v-for="item in relatedVideos"
            :key="item.id"
            class="related-card"
            @click="goToDetail(item.id)"
          >
            <img :src="item.coverUrl" alt="">
            <div class="related-body">
              <div class="related-title">{{ item.title }}</div>
              <div class="related-meta">
                <span>{{ item.user?.nickname || '匿名作者' }}</span>
                <span>{{ item.playCount }} 播放</span>
              </div>
            </div>
          </div>
        </div>
        <el-empty v-else description="暂无相关推荐" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  collectVideo,
  getRelatedVideos,
  getVideoDetail,
  likeVideo,
  recordPlay,
  shareVideo,
  uncollectVideo,
  unlikeVideo
} from '@/api/video'
import {
  getCommentList,
  getReplies,
  likeComment,
  publishComment,
  unlikeComment
} from '@/api/comment'
import { followUser } from '@/api/user'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const videoId = ref(route.params.id)
const video = ref(null)
const comments = ref([])
const relatedVideos = ref([])
const commentText = ref('')

const normalizeComment = (comment) => ({
  ...comment,
  showReplies: false,
  replies: comment.replies || []
})

const fetchVideo = async () => {
  try {
    const res = await getVideoDetail(videoId.value)
    video.value = res.data
    await recordPlay(videoId.value)
    video.value.playCount += 1
  } catch (error) {
    ElMessage.error('获取视频失败')
  }
}

const fetchComments = async () => {
  try {
    const res = await getCommentList({ videoId: videoId.value, page: 1, size: 50 })
    comments.value = (res.data.records || []).map(normalizeComment)
  } catch (error) {
    ElMessage.error('获取评论失败')
  }
}

const fetchRelatedVideos = async () => {
  try {
    const res = await getRelatedVideos(videoId.value, { size: 6 })
    relatedVideos.value = res.data || []
  } catch (error) {
    relatedVideos.value = []
  }
}

const handleLike = async () => {
  try {
    if (video.value.isLike) {
      await unlikeVideo(videoId.value)
      video.value.isLike = false
      video.value.likeCount -= 1
    } else {
      await likeVideo(videoId.value)
      video.value.isLike = true
      video.value.likeCount += 1
    }
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const handleCollect = async () => {
  try {
    if (video.value.isCollect) {
      await uncollectVideo(videoId.value)
      video.value.isCollect = false
      video.value.collectCount -= 1
    } else {
      await collectVideo(videoId.value)
      video.value.isCollect = true
      video.value.collectCount += 1
    }
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const handleFollow = async () => {
  try {
    await followUser(video.value.user.id)
    video.value.user.isFollow = true
    ElMessage.success('关注成功')
  } catch (error) {
    ElMessage.error('关注失败')
  }
}

const handleShare = async () => {
  try {
    const { value } = await ElMessageBox.prompt(
      '可以写一句分享语，也可以直接留空提交。',
      '转发视频',
      {
        inputPlaceholder: '说点什么吧',
        confirmButtonText: '立即转发',
        cancelButtonText: '取消'
      }
    )
    await shareVideo(videoId.value, { shareText: value || '' })
    video.value.shareCount += 1
    ElMessage.success('转发成功')
  } catch (error) {
  }
}

const handleComment = async () => {
  if (!commentText.value.trim()) {
    return
  }
  
  try {
    await publishComment({
      videoId: videoId.value,
      content: commentText.value,
      parentId: 0
    })
    commentText.value = ''
    video.value.commentCount += 1
    ElMessage.success('评论成功')
    fetchComments()
  } catch (error) {
    ElMessage.error('评论失败')
  }
}

const toggleCommentLike = async (comment) => {
  try {
    if (comment.isLike) {
      await unlikeComment(comment.id)
      comment.isLike = false
      comment.likeCount -= 1
    } else {
      await likeComment(comment.id)
      comment.isLike = true
      comment.likeCount += 1
    }
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const handleReply = async (comment) => {
  try {
    const { value } = await ElMessageBox.prompt(
      `回复 ${comment.user.nickname}`,
      '发表评论',
      {
        inputType: 'textarea',
        inputPlaceholder: '输入回复内容',
        confirmButtonText: '发布回复',
        cancelButtonText: '取消'
      }
    )
    if (!value || !value.trim()) {
      return
    }
    await publishComment({
      videoId: videoId.value,
      parentId: comment.id,
      replyToUserId: comment.user.id,
      content: value.trim()
    })
    video.value.commentCount += 1
    comment.replyCount += 1
    comment.showReplies = true
    await loadReplies(comment)
    ElMessage.success('回复成功')
  } catch (error) {
  }
}

const loadReplies = async (comment) => {
  try {
    const res = await getReplies(comment.id)
    comment.replies = res.data || []
  } catch (error) {
    comment.replies = []
  }
}

const toggleReplies = async (comment) => {
  comment.showReplies = !comment.showReplies
  if (comment.showReplies && (!comment.replies || comment.replies.length === 0)) {
    await loadReplies(comment)
  }
}

const goToUser = (userId) => {
  router.push(`/user/${userId}`)
}

const goToTopic = (topicId) => {
  router.push(`/topic/${topicId}`)
}

const goToDetail = (targetVideoId) => {
  router.push(`/video/${targetVideoId}`)
}

const reloadPageData = async (targetVideoId) => {
  videoId.value = targetVideoId
  await Promise.all([
    fetchVideo(),
    fetchComments(),
    fetchRelatedVideos()
  ])
}

watch(
  () => route.params.id,
  (id) => {
    if (id) {
      reloadPageData(id)
    }
  }
)

onMounted(() => {
  reloadPageData(route.params.id)
})
</script>

<style scoped>
.detail-container {
  display: flex;
  height: 100%;
}

.video-section {
  flex: 0 0 58%;
  background: #000;
  display: flex;
  align-items: center;
  justify-content: center;
}

.video-player {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.info-section {
  flex: 1;
  background: #fff;
  overflow-y: auto;
  padding: 20px;
}

.video-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.author-info {
  display: flex;
  align-items: center;
  gap: 15px;
  cursor: pointer;
}

.nickname {
  font-weight: 500;
  font-size: 16px;
}

.time {
  font-size: 12px;
  color: #999;
  margin-top: 4px;
}

.video-info h2 {
  margin: 0 0 15px 0;
}

.description {
  color: #666;
  line-height: 1.6;
  margin-bottom: 15px;
}

.topics {
  margin-bottom: 15px;
}

.topics .el-tag {
  margin-right: 8px;
  cursor: pointer;
}

.stats {
  display: flex;
  flex-wrap: wrap;
  gap: 18px;
  color: #666;
  font-size: 14px;
  margin-bottom: 20px;
}

.stats span {
  display: flex;
  align-items: center;
  gap: 5px;
}

.actions {
  display: flex;
  gap: 10px;
  padding-bottom: 20px;
  border-bottom: 1px solid #f0f0f0;
}

.comment-section,
.related-section {
  margin-top: 24px;
}

.comment-section h3,
.section-header h3 {
  margin: 0 0 15px 0;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  gap: 16px;
}

.section-header span {
  color: #94a3b8;
  font-size: 12px;
}

.comment-input {
  margin-bottom: 20px;
}

.comment-input .el-button {
  margin-top: 10px;
}

.comment-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.comment-item {
  display: flex;
  gap: 15px;
}

.comment-content {
  flex: 1;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.comment-header .nickname {
  font-weight: 500;
}

.comment-header .time {
  font-size: 12px;
  color: #999;
}

.content {
  color: #333;
  line-height: 1.6;
  margin-bottom: 10px;
}

.comment-actions {
  display: flex;
  gap: 20px;
  font-size: 14px;
  color: #666;
}

.comment-actions span {
  display: flex;
  align-items: center;
  gap: 4px;
  cursor: pointer;
}

.comment-actions .active,
.comment-actions span:hover {
  color: #409eff;
}

.reply-list {
  margin-top: 12px;
  padding: 12px 14px;
  border-radius: 12px;
  background: #f8fafc;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.reply-item {
  font-size: 13px;
  color: #475569;
  line-height: 1.6;
}

.reply-user {
  font-weight: 600;
  color: #334155;
}

.related-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 14px;
}

.related-card {
  border-radius: 14px;
  overflow: hidden;
  background: #f8fafc;
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.related-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 24px rgba(15, 23, 42, 0.08);
}

.related-card img {
  width: 100%;
  height: 140px;
  object-fit: cover;
}

.related-body {
  padding: 12px;
}

.related-title {
  font-weight: 600;
  color: #111827;
  margin-bottom: 8px;
  line-height: 1.5;
}

.related-meta {
  display: flex;
  justify-content: space-between;
  gap: 10px;
  color: #64748b;
  font-size: 12px;
}
</style>
