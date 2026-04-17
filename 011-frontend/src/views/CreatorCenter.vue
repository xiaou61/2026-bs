<template>
  <div class="creator-container">
    <div class="page-header">
      <div>
        <h2>创作中心</h2>
        <p>查看作品表现、近 7 日创作趋势和重点内容数据。</p>
      </div>
      <el-button type="primary" @click="goPublish">发布新视频</el-button>
    </div>

    <div class="summary-grid">
      <el-card class="summary-card">
        <div class="summary-label">作品数</div>
        <div class="summary-value">{{ summary.videoCount || 0 }}</div>
        <div class="summary-tip">近 7 日发布 {{ summary.weeklyVideoCount || 0 }} 条</div>
      </el-card>
      <el-card class="summary-card">
        <div class="summary-label">总播放</div>
        <div class="summary-value">{{ summary.totalPlays || 0 }}</div>
        <div class="summary-tip">累计互动 {{ totalInteractions }}</div>
      </el-card>
      <el-card class="summary-card">
        <div class="summary-label">粉丝 / 关注</div>
        <div class="summary-value">{{ summary.fansCount || 0 }} / {{ summary.followCount || 0 }}</div>
        <div class="summary-tip">当前等级 Lv{{ summary.level || 1 }}</div>
      </el-card>
      <el-card class="summary-card">
        <div class="summary-label">创作积分</div>
        <div class="summary-value">{{ summary.points || 0 }}</div>
        <div class="summary-tip">平均热度 {{ summary.averageHeat || 0 }}</div>
      </el-card>
    </div>

    <div class="content-grid">
      <el-card class="panel-card">
        <template #header>
          <div class="panel-title">近 7 日发布趋势</div>
        </template>
        <div v-if="publishTrend.length > 0" class="trend-list">
          <div v-for="item in publishTrend" :key="item.date" class="trend-item">
            <span class="trend-date">{{ item.date }}</span>
            <el-progress
              :percentage="getTrendPercent(item.count, publishMax)"
              :stroke-width="10"
              :show-text="false"
            />
            <span class="trend-value">{{ item.count }}</span>
          </div>
        </div>
        <el-empty v-else description="暂无发布数据" />
      </el-card>

      <el-card class="panel-card">
        <template #header>
          <div class="panel-title">创作任务建议</div>
        </template>
        <div class="task-list">
          <div v-for="task in tasks" :key="task.name" class="task-item">
            <div>
              <div class="task-name">{{ task.name }}</div>
              <div class="task-desc">{{ task.desc }}</div>
            </div>
            <el-tag :type="task.done ? 'success' : 'warning'">
              {{ task.done ? '已达成' : '进行中' }}
            </el-tag>
          </div>
        </div>
      </el-card>
    </div>

    <el-card class="panel-card">
      <template #header>
        <div class="panel-title">重点作品</div>
      </template>
      <el-table v-if="topVideos.length > 0" :data="topVideos" stripe>
        <el-table-column label="作品" min-width="260">
          <template #default="{ row }">
            <div class="video-cell">
              <img :src="row.coverUrl" alt="">
              <div>
                <div class="video-title">{{ row.title }}</div>
                <div class="video-meta">{{ row.publishTime }}</div>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="playCount" label="播放" width="90" />
        <el-table-column prop="likeCount" label="点赞" width="90" />
        <el-table-column prop="commentCount" label="评论" width="90" />
        <el-table-column prop="shareCount" label="转发" width="90" />
        <el-table-column prop="heatScore" label="热度" width="110" />
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button link type="primary" @click="showVideoStats(row.id)">查看数据</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-else description="还没有可分析的作品" />
    </el-card>

    <el-dialog v-model="statsDialogVisible" title="视频数据详情" width="560px">
      <div v-if="selectedVideoStats" class="video-stats-panel">
        <div class="video-stats-grid">
          <div class="stats-block">
            <span class="stats-block-label">播放量</span>
            <strong>{{ selectedVideoStats.playCount }}</strong>
          </div>
          <div class="stats-block">
            <span class="stats-block-label">点赞数</span>
            <strong>{{ selectedVideoStats.likeCount }}</strong>
          </div>
          <div class="stats-block">
            <span class="stats-block-label">评论数</span>
            <strong>{{ selectedVideoStats.commentCount }}</strong>
          </div>
          <div class="stats-block">
            <span class="stats-block-label">转发数</span>
            <strong>{{ selectedVideoStats.shareCount }}</strong>
          </div>
          <div class="stats-block">
            <span class="stats-block-label">收藏数</span>
            <strong>{{ selectedVideoStats.collectCount }}</strong>
          </div>
          <div class="stats-block">
            <span class="stats-block-label">互动率</span>
            <strong>{{ selectedVideoStats.engagementRate }}%</strong>
          </div>
        </div>
        <div class="video-detail-meta">
          <div>标题：{{ selectedVideoStats.title }}</div>
          <div>发布时间：{{ selectedVideoStats.publishTime }}</div>
          <div>热度分数：{{ selectedVideoStats.heatScore }}</div>
          <div>话题：{{ (selectedVideoStats.topicNames || []).join('、') || '暂无' }}</div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getCreatorStats, getVideoStats } from '@/api/stats'

const router = useRouter()
const stats = ref({})
const publishTrend = ref([])
const topVideos = ref([])
const statsDialogVisible = ref(false)
const selectedVideoStats = ref(null)

const summary = computed(() => stats.value.summary || {})
const totalInteractions = computed(() =>
  (summary.value.totalLikes || 0) +
  (summary.value.totalComments || 0) +
  (summary.value.totalShares || 0) +
  (summary.value.totalCollects || 0)
)
const publishMax = computed(() => Math.max(...publishTrend.value.map(item => Number(item.count || 0)), 1))
const tasks = computed(() => [
  {
    name: '本周保持创作节奏',
    desc: `近 7 日已发布 ${summary.value.weeklyVideoCount || 0} 条作品`,
    done: (summary.value.weeklyVideoCount || 0) >= 1
  },
  {
    name: '提升互动表现',
    desc: `累计互动 ${totalInteractions.value} 次，继续优化标题和封面`,
    done: totalInteractions.value >= 10
  },
  {
    name: '经营粉丝关系',
    desc: `当前粉丝 ${summary.value.fansCount || 0} 人，建议持续更新内容`,
    done: (summary.value.fansCount || 0) >= 10
  }
])

const fetchStats = async () => {
  try {
    const res = await getCreatorStats()
    stats.value = res.data
    publishTrend.value = res.data.publishTrend || []
    topVideos.value = res.data.topVideos || []
  } catch (error) {
    ElMessage.error('获取创作数据失败')
  }
}

const getTrendPercent = (count, max) => {
  if (!max) {
    return 0
  }
  return Math.round((Number(count || 0) / max) * 100)
}

const showVideoStats = async (videoId) => {
  try {
    const res = await getVideoStats(videoId)
    selectedVideoStats.value = res.data
    statsDialogVisible.value = true
  } catch (error) {
    ElMessage.error('获取视频数据失败')
  }
}

const goPublish = () => {
  router.push('/publish')
}

onMounted(() => {
  fetchStats()
})
</script>

<style scoped>
.creator-container {
  padding: 24px;
  height: 100%;
  overflow-y: auto;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 16px;
  margin-bottom: 24px;
}

.page-header h2 {
  margin: 0 0 8px;
  font-size: 28px;
}

.page-header p {
  margin: 0;
  color: #6b7280;
}

.summary-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 16px;
  margin-bottom: 20px;
}

.summary-card {
  border-radius: 16px;
}

.summary-label {
  color: #64748b;
  margin-bottom: 12px;
}

.summary-value {
  font-size: 28px;
  font-weight: 700;
  color: #111827;
  margin-bottom: 12px;
}

.summary-tip {
  color: #94a3b8;
  font-size: 13px;
}

.content-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 16px;
  margin-bottom: 20px;
}

.panel-card {
  border-radius: 16px;
  margin-bottom: 20px;
}

.panel-title {
  font-weight: 600;
}

.trend-list {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.trend-item {
  display: grid;
  grid-template-columns: 54px 1fr 36px;
  align-items: center;
  gap: 12px;
}

.trend-date,
.trend-value {
  color: #475569;
  font-size: 13px;
}

.task-list {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.task-item {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  padding: 14px 16px;
  border-radius: 12px;
  background: #f8fafc;
}

.task-name {
  font-weight: 600;
  margin-bottom: 4px;
}

.task-desc {
  color: #64748b;
  font-size: 13px;
}

.video-cell {
  display: flex;
  align-items: center;
  gap: 12px;
}

.video-cell img {
  width: 84px;
  height: 56px;
  border-radius: 10px;
  object-fit: cover;
}

.video-title {
  font-weight: 600;
  color: #111827;
  margin-bottom: 4px;
}

.video-meta {
  color: #94a3b8;
  font-size: 12px;
}

.video-stats-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 12px;
  margin-bottom: 20px;
}

.stats-block {
  padding: 16px;
  border-radius: 12px;
  background: #f8fafc;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.stats-block-label {
  color: #64748b;
  font-size: 13px;
}

.video-detail-meta {
  display: flex;
  flex-direction: column;
  gap: 10px;
  color: #475569;
}
</style>
