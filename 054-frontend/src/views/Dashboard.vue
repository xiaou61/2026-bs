<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="stat-card" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%)">
          <div class="stat-icon"><el-icon><User /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.userCount || 0 }}</div>
            <div class="stat-label">用户总数</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card" style="background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%)">
          <div class="stat-icon"><el-icon><Grape /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.cropCount || 0 }}</div>
            <div class="stat-label">作物种类</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card" style="background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)">
          <div class="stat-icon"><el-icon><Calendar /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.planCount || 0 }}</div>
            <div class="stat-label">生产计划</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card" style="background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)">
          <div class="stat-icon"><el-icon><Collection /></el-icon></div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.knowledgeCount || 0 }}</div>
            <div class="stat-label">知识文章</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card header="最新公告">
          <el-empty v-if="!notices.length" description="暂无公告" />
          <div v-else class="notice-list">
            <div v-for="item in notices" :key="item.id" class="notice-item">
              <span class="notice-title">{{ item.title }}</span>
              <span class="notice-time">{{ item.createTime?.substring(0, 10) }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card header="病虫害预警">
          <el-empty v-if="!warnings.length" description="暂无预警" />
          <div v-else class="warning-list">
            <div v-for="item in warnings" :key="item.id" class="warning-item">
              <el-tag :type="getWarningType(item.warningLevel)" size="small">
                {{ ['', '轻度', '中度', '重度'][item.warningLevel] }}
              </el-tag>
              <span class="warning-name">{{ item.pestName }}</span>
              <span class="warning-region">{{ item.region }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card header="待处理任务">
          <el-empty v-if="!tasks.length" description="暂无待处理任务" />
          <div v-else class="task-list">
            <div v-for="item in tasks" :key="item.id" class="task-item">
              <span class="task-name">{{ item.taskName }}</span>
              <el-tag size="small">{{ ['待执行', '执行中'][item.status] }}</el-tag>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card header="库存预警">
          <el-empty v-if="!materialWarnings.length" description="库存充足" />
          <div v-else class="material-list">
            <div v-for="item in materialWarnings" :key="item.id" class="material-item">
              <span class="material-name">{{ item.name }}</span>
              <span class="material-stock">
                库存: {{ item.currentStock }} {{ item.unit }} (预警: {{ item.warningStock }})
              </span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getStatsOverview, getPublishedNotices } from '@/api/notice'
import { getWarningPage } from '@/api/pest'
import { getTaskPage } from '@/api/plan'
import { getWarningList } from '@/api/material'

const stats = ref({})
const notices = ref([])
const warnings = ref([])
const tasks = ref([])
const materialWarnings = ref([])

const getWarningType = (level) => {
  return ['', 'success', 'warning', 'danger'][level] || 'info'
}

onMounted(async () => {
  try {
    const [statsRes, noticeRes, warningRes, taskRes, materialRes] = await Promise.all([
      getStatsOverview(),
      getPublishedNotices(),
      getWarningPage({ pageNum: 1, pageSize: 5 }),
      getTaskPage({ pageNum: 1, pageSize: 5, status: 0 }),
      getWarningList()
    ])
    stats.value = statsRes.data || {}
    notices.value = noticeRes.data || []
    warnings.value = warningRes.data?.records || []
    tasks.value = taskRes.data?.records || []
    materialWarnings.value = materialRes.data || []
  } catch (e) {
    console.error(e)
  }
})
</script>

<style scoped lang="scss">
.dashboard {
  padding: 20px;
}

.stat-card {
  padding: 20px;
  border-radius: 8px;
  color: #fff;
  display: flex;
  align-items: center;

  .stat-icon {
    font-size: 48px;
    margin-right: 20px;
  }

  .stat-value {
    font-size: 32px;
    font-weight: bold;
  }

  .stat-label {
    font-size: 14px;
    opacity: 0.8;
  }
}

.notice-list, .warning-list, .task-list, .material-list {
  .notice-item, .warning-item, .task-item, .material-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 0;
    border-bottom: 1px solid #eee;

    &:last-child {
      border-bottom: none;
    }
  }

  .notice-title, .task-name, .material-name {
    flex: 1;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .notice-time, .material-stock {
    color: #999;
    font-size: 12px;
    margin-left: 10px;
  }

  .warning-name {
    flex: 1;
    margin: 0 10px;
  }

  .warning-region {
    color: #999;
    font-size: 12px;
  }
}
</style>
