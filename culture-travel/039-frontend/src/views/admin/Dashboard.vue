<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #409eff;"><el-icon><User /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.userCount || 0 }}</div>
            <div class="stat-label">用户总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #67c23a;"><el-icon><Headset /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.songCount || 0 }}</div>
            <div class="stat-label">民歌总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #e6a23c;"><el-icon><ChatDotRound /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.commentCount || 0 }}</div>
            <div class="stat-label">评论总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #f56c6c;"><el-icon><Warning /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.pendingCount || 0 }}</div>
            <div class="stat-label">待审核</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="margin-top: 20px;">
      <template #header><span class="card-title">待审核民歌</span></template>
      <el-table :data="pendingSongs" stripe>
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="userName" label="发布者" width="120" />
        <el-table-column prop="categoryName" label="分类" width="100" />
        <el-table-column prop="createTime" label="发布时间" width="120">
          <template #default="{ row }">{{ formatTime(row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="success" link @click="handleAudit(row.id, 1)">通过</el-button>
            <el-button type="danger" link @click="handleAudit(row.id, 2)">拒绝</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { adminApi } from '@/api'
import { ElMessage } from 'element-plus'

const stats = ref({})
const pendingSongs = ref([])

const fetchData = async () => {
  const [usersRes, songsRes, commentsRes, pendingRes] = await Promise.all([
    adminApi.getUsers({ pageNum: 1, pageSize: 1 }),
    adminApi.getSongs({ pageNum: 1, pageSize: 1 }),
    adminApi.getComments({ pageNum: 1, pageSize: 1 }),
    adminApi.getPendingSongs({ pageNum: 1, pageSize: 5 })
  ])
  stats.value = {
    userCount: usersRes.data.total,
    songCount: songsRes.data.total,
    commentCount: commentsRes.data.total,
    pendingCount: pendingRes.data.total
  }
  pendingSongs.value = pendingRes.data.records
}

onMounted(fetchData)

const formatTime = (time) => new Date(time).toLocaleDateString()

const handleAudit = async (id, status) => {
  await adminApi.auditSong(id, status)
  ElMessage.success('操作成功')
  fetchData()
}
</script>

<style scoped lang="scss">
.stat-card {
  display: flex;
  align-items: center;
  padding: 10px;

  .stat-icon {
    width: 60px;
    height: 60px;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
    font-size: 28px;
    margin-right: 15px;
  }

  .stat-info {
    .stat-value {
      font-size: 28px;
      font-weight: bold;
      color: #333;
    }

    .stat-label {
      color: #999;
      font-size: 14px;
    }
  }
}
</style>
