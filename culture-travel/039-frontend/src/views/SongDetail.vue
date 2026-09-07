<template>
  <div class="song-detail" v-if="song">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <div class="song-header">
            <img :src="song.coverImage || '/default-cover.png'" class="cover-image" />
            <div class="song-main-info">
              <h1>{{ song.title }}</h1>
              <div class="meta-row">
                <el-tag>{{ song.categoryName }}</el-tag>
                <span v-if="song.region"><el-icon><Location /></el-icon> {{ song.region }}</span>
                <span v-if="song.ethnic"><el-icon><User /></el-icon> {{ song.ethnic }}</span>
              </div>
              <div class="author-row">
                <el-avatar :size="32" :src="song.userAvatar">{{ song.userName?.charAt(0) }}</el-avatar>
                <span>{{ song.userName }}</span>
                <span class="time">发布于 {{ formatTime(song.createTime) }}</span>
              </div>
              <div class="stats-row">
                <span><el-icon><View /></el-icon> {{ song.viewCount }}</span>
                <span><el-icon><Star /></el-icon> {{ song.likeCount }}</span>
                <span><el-icon><CollectionTag /></el-icon> {{ song.collectCount }}</span>
                <span><el-icon><ChatDotRound /></el-icon> {{ song.commentCount }}</span>
              </div>
            </div>
          </div>

          <el-divider />

          <div v-if="song.audioUrl" class="media-section">
            <h3>音频播放</h3>
            <audio :src="song.audioUrl" controls style="width: 100%;" />
          </div>

          <div v-if="song.videoUrl" class="media-section">
            <h3>视频播放</h3>
            <video :src="song.videoUrl" controls style="width: 100%;" />
          </div>

          <div v-if="song.lyrics" class="lyrics-section">
            <h3>歌词</h3>
            <pre class="lyrics-content">{{ song.lyrics }}</pre>
          </div>

          <div v-if="song.introduction" class="intro-section">
            <h3>简介</h3>
            <p>{{ song.introduction }}</p>
          </div>

          <div v-if="song.content" class="content-section">
            <h3>详细内容</h3>
            <div v-html="song.content"></div>
          </div>

          <div class="action-buttons">
            <el-button :type="song.isLiked ? 'danger' : 'default'" @click="handleLike">
              <el-icon><Star /></el-icon> {{ song.isLiked ? '已点赞' : '点赞' }}
            </el-button>
            <el-button :type="song.isCollected ? 'warning' : 'default'" @click="handleCollect">
              <el-icon><CollectionTag /></el-icon> {{ song.isCollected ? '已收藏' : '收藏' }}
            </el-button>
          </div>
        </el-card>

        <el-card style="margin-top: 20px;">
          <template #header><span class="card-title">评论区</span></template>
          <div class="comment-input" v-if="userStore.isLoggedIn">
            <el-input v-model="commentContent" type="textarea" :rows="3" placeholder="写下你的评论..." />
            <el-button type="primary" style="margin-top: 10px;" @click="submitComment">发表评论</el-button>
          </div>
          <div v-else class="login-tip">
            <el-button type="primary" link @click="router.push('/login')">登录</el-button>后发表评论
          </div>
          <el-divider />
          <div class="comment-list">
            <div v-for="comment in comments" :key="comment.id" class="comment-item">
              <el-avatar :size="40" :src="comment.userAvatar">{{ comment.userName?.charAt(0) }}</el-avatar>
              <div class="comment-content">
                <div class="comment-header">
                  <span class="user-name">{{ comment.userName }}</span>
                  <span class="comment-time">{{ formatTime(comment.createTime) }}</span>
                </div>
                <p class="comment-text">{{ comment.content }}</p>
              </div>
            </div>
            <el-empty v-if="comments.length === 0" description="暂无评论" />
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card>
          <template #header><span class="card-title">相关推荐</span></template>
          <div v-for="item in hotSongs" :key="item.id" class="recommend-item" @click="router.push(`/song/${item.id}`)">
            <img :src="item.coverImage || '/default-cover.png'" class="recommend-cover" />
            <div class="recommend-info">
              <div class="recommend-title">{{ item.title }}</div>
              <div class="recommend-meta">{{ item.categoryName }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { songApi, commentApi } from '@/api'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const song = ref(null)
const comments = ref([])
const hotSongs = ref([])
const commentContent = ref('')

const fetchData = async () => {
  const id = route.params.id
  const [songRes, commentRes, hotRes] = await Promise.all([
    songApi.getDetail(id),
    commentApi.getBySongId(id, { pageNum: 1, pageSize: 20 }),
    songApi.getHot(5)
  ])
  song.value = songRes.data
  comments.value = commentRes.data.records
  hotSongs.value = hotRes.data
}

onMounted(fetchData)
watch(() => route.params.id, fetchData)

const formatTime = (time) => {
  return new Date(time).toLocaleDateString()
}

const handleLike = async () => {
  if (!userStore.isLoggedIn) {
    router.push('/login')
    return
  }
  if (song.value.isLiked) {
    await songApi.unlike(song.value.id)
    song.value.isLiked = false
    song.value.likeCount--
  } else {
    await songApi.like(song.value.id)
    song.value.isLiked = true
    song.value.likeCount++
  }
}

const handleCollect = async () => {
  if (!userStore.isLoggedIn) {
    router.push('/login')
    return
  }
  if (song.value.isCollected) {
    await songApi.uncollect(song.value.id)
    song.value.isCollected = false
    song.value.collectCount--
  } else {
    await songApi.collect(song.value.id)
    song.value.isCollected = true
    song.value.collectCount++
  }
}

const submitComment = async () => {
  if (!commentContent.value.trim()) {
    ElMessage.warning('请输入评论内容')
    return
  }
  await commentApi.create({ songId: song.value.id, content: commentContent.value })
  ElMessage.success('评论成功')
  commentContent.value = ''
  const res = await commentApi.getBySongId(song.value.id, { pageNum: 1, pageSize: 20 })
  comments.value = res.data.records
  song.value.commentCount++
}
</script>

<style scoped lang="scss">
.song-header {
  display: flex;
  gap: 20px;

  .cover-image {
    width: 200px;
    height: 200px;
    object-fit: cover;
    border-radius: 8px;
  }

  .song-main-info {
    flex: 1;

    h1 {
      margin: 0 0 15px;
      font-size: 24px;
    }

    .meta-row, .author-row, .stats-row {
      display: flex;
      align-items: center;
      gap: 15px;
      margin-bottom: 10px;
      color: #666;
    }

    .author-row {
      .time {
        color: #999;
        font-size: 12px;
      }
    }

    .stats-row span {
      display: flex;
      align-items: center;
      gap: 4px;
    }
  }
}

.media-section, .lyrics-section, .intro-section, .content-section {
  margin-top: 20px;

  h3 {
    margin-bottom: 10px;
    color: #333;
  }
}

.lyrics-content {
  background: #f5f5f5;
  padding: 15px;
  border-radius: 4px;
  white-space: pre-wrap;
  font-family: inherit;
}

.action-buttons {
  margin-top: 20px;
  display: flex;
  gap: 10px;
}

.comment-input {
  margin-bottom: 20px;
}

.login-tip {
  text-align: center;
  padding: 20px;
  color: #999;
}

.comment-list {
  .comment-item {
    display: flex;
    gap: 12px;
    padding: 15px 0;
    border-bottom: 1px solid #eee;

    .comment-content {
      flex: 1;

      .comment-header {
        display: flex;
        justify-content: space-between;
        margin-bottom: 8px;

        .user-name {
          font-weight: bold;
          color: #333;
        }

        .comment-time {
          color: #999;
          font-size: 12px;
        }
      }

      .comment-text {
        margin: 0;
        color: #666;
      }
    }
  }
}

.recommend-item {
  display: flex;
  gap: 10px;
  padding: 10px 0;
  border-bottom: 1px solid #eee;
  cursor: pointer;

  &:hover .recommend-title {
    color: #409eff;
  }

  .recommend-cover {
    width: 60px;
    height: 60px;
    object-fit: cover;
    border-radius: 4px;
  }

  .recommend-info {
    flex: 1;

    .recommend-title {
      font-weight: bold;
      margin-bottom: 5px;
    }

    .recommend-meta {
      color: #999;
      font-size: 12px;
    }
  }
}
</style>
