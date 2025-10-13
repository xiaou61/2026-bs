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
                <span @click="handleCommentLike(comment)">
                  <el-icon><Star /></el-icon>
                  {{ comment.likeCount }}
                </span>
                <span @click="handleReply(comment)">回复</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getVideoDetail, likeVideo, unlikeVideo, collectVideo, uncollectVideo } from '@/api/video'
import { getCommentList, publishComment, likeComment } from '@/api/comment'
import { followUser } from '@/api/user'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const videoId = ref(route.params.id)
const video = ref(null)
const comments = ref([])
const commentText = ref('')

const fetchVideo = async () => {
  try {
    const res = await getVideoDetail(videoId.value)
    video.value = res.data
  } catch (error) {
    ElMessage.error('获取视频失败')
  }
}

const fetchComments = async () => {
  try {
    const res = await getCommentList({ videoId: videoId.value, page: 1, size: 50 })
    comments.value = res.data.records
  } catch (error) {
    ElMessage.error('获取评论失败')
  }
}

const handleLike = async () => {
  try {
    if (video.value.isLike) {
      await unlikeVideo(videoId.value)
      video.value.isLike = false
      video.value.likeCount--
    } else {
      await likeVideo(videoId.value)
      video.value.isLike = true
      video.value.likeCount++
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
      video.value.collectCount--
    } else {
      await collectVideo(videoId.value)
      video.value.isCollect = true
      video.value.collectCount++
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

const handleShare = () => {
  ElMessage.info('分享功能开发中')
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
    ElMessage.success('评论成功')
    fetchComments()
  } catch (error) {
    ElMessage.error('评论失败')
  }
}

const handleCommentLike = async (comment) => {
  try {
    await likeComment(comment.id)
    comment.likeCount++
    ElMessage.success('点赞成功')
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const handleReply = (comment) => {
  ElMessage.info('回复功能开发中')
}

const goToUser = (userId) => {
  router.push(`/user/${userId}`)
}

const goToTopic = (topicId) => {
  router.push(`/topic/${topicId}`)
}

onMounted(() => {
  fetchVideo()
  fetchComments()
})
</script>

<style scoped>
.detail-container {
  display: flex;
  height: 100%;
}

.video-section {
  flex: 0 0 60%;
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
  gap: 20px;
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

.comment-section {
  margin-top: 20px;
}

.comment-section h3 {
  margin: 0 0 15px 0;
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

.comment-actions span:hover {
  color: #409eff;
}
</style>

