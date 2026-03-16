<template>
  <div class="dashboard-page">
    <div class="stat-grid">
      <div class="stat-card" v-for="item in statCards" :key="item.label">
        <div class="stat-label">{{ item.label }}</div>
        <div class="stat-value">{{ item.value }}</div>
      </div>
    </div>

    <div class="panel-grid">
      <el-card class="panel-card">
        <template #header>
          <div class="panel-title">热门壁纸 TOP5</div>
        </template>
        <el-table :data="dashboard.topWallpapers || []">
          <el-table-column prop="title" label="标题" min-width="180" />
          <el-table-column prop="downloadCount" label="下载" width="90" />
          <el-table-column prop="favoriteCount" label="收藏" width="90" />
          <el-table-column prop="publishStatus" label="上架" width="90">
            <template #default="{ row }">
              <el-tag :type="row.publishStatus === 1 ? 'success' : 'info'">{{ row.publishStatus === 1 ? '已上架' : '未上架' }}</el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-card>

      <el-card class="panel-card">
        <template #header>
          <div class="panel-title">最新投稿</div>
        </template>
        <el-table :data="dashboard.latestUploads || []">
          <el-table-column prop="title" label="标题" min-width="180" />
          <el-table-column prop="wallpaperType" label="类型" width="90">
            <template #default="{ row }">{{ row.wallpaperType === 'mobile' ? '手机' : '桌面' }}</template>
          </el-table-column>
          <el-table-column prop="auditStatus" label="审核" width="90">
            <template #default="{ row }">
              <el-tag :type="row.auditStatus === 1 ? 'success' : row.auditStatus === 2 ? 'danger' : 'warning'">
                {{ row.auditStatus === 1 ? '通过' : row.auditStatus === 2 ? '驳回' : '待审' }}
              </el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive } from 'vue'
import { getDashboard } from '../../api'

const dashboard = reactive({
  userCount: 0,
  wallpaperCount: 0,
  pendingAuditCount: 0,
  publishCount: 0,
  favoriteCount: 0,
  bannerCount: 0,
  noticeCount: 0,
  topWallpapers: [],
  latestUploads: []
})

const statCards = computed(() => ([
  { label: '用户总数', value: dashboard.userCount || 0 },
  { label: '壁纸总数', value: dashboard.wallpaperCount || 0 },
  { label: '待审核', value: dashboard.pendingAuditCount || 0 },
  { label: '已上架', value: dashboard.publishCount || 0 },
  { label: '收藏记录', value: dashboard.favoriteCount || 0 },
  { label: '轮播数量', value: dashboard.bannerCount || 0 },
  { label: '公告数量', value: dashboard.noticeCount || 0 }
]))

const loadData = async () => {
  const res = await getDashboard()
  Object.assign(dashboard, res.data || {})
}

onMounted(loadData)
</script>

<style scoped>
.stat-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

.stat-card {
  padding: 22px;
  border-radius: 22px;
  background: linear-gradient(135deg, #0f1d33, #172a46);
  color: #fff;
  box-shadow: 0 20px 40px rgba(15, 29, 51, 0.15);
}

.stat-label {
  color: rgba(255, 255, 255, 0.72);
  font-size: 14px;
}

.stat-value {
  margin-top: 12px;
  font-size: 34px;
  font-weight: 700;
}

.panel-grid {
  margin-top: 22px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.panel-card {
  border-radius: 20px;
}

.panel-title {
  font-size: 18px;
  font-weight: 600;
}
</style>
