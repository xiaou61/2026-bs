<template>
  <div>
    <el-card>
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="活动状态">
          <el-select v-model="searchForm.status" placeholder="全部" clearable style="width: 150px">
            <el-option label="报名中" :value="1" />
            <el-option label="进行中" :value="2" />
            <el-option label="已结束" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="活动标题">
          <el-input v-model="searchForm.title" placeholder="请输入活动标题" clearable style="width: 200px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadActivities">查询</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="8" v-for="activity in activities" :key="activity.id">
        <el-card class="activity-card" shadow="hover">
          <h3>{{ activity.title }}</h3>
          <p class="description">{{ activity.description }}</p>
          <div class="info-item">
            <el-icon><Location /></el-icon>
            <span>{{ activity.location }}</span>
          </div>
          <div class="info-item">
            <el-icon><Clock /></el-icon>
            <span>{{ activity.startTime }}</span>
          </div>
          <div class="info-item">
            <el-icon><User /></el-icon>
            <span>{{ activity.currentParticipants }}/{{ activity.maxParticipants }}</span>
          </div>
          <div class="info-item">
            <el-icon><Coin /></el-icon>
            <span style="color: #409eff; font-weight: bold">{{ activity.points }} 积分</span>
          </div>
          <div class="info-item">
            <el-icon><Timer /></el-icon>
            <span>{{ activity.hours }} 小时</span>
          </div>
          <el-tag :type="getStatusType(activity.status)" size="small">
            {{ getStatusText(activity.status) }}
          </el-tag>
          <div style="margin-top: 15px">
            <el-button type="primary" size="small" @click="viewDetail(activity.id)">查看详情</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-pagination
      v-if="total > 0"
      style="margin-top: 20px; text-align: center"
      background
      layout="total, prev, pager, next"
      :total="total"
      :page-size="pageSize"
      :current-page="currentPage"
      @current-change="handlePageChange"
    />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getActivityList } from '@/api/activity'

const router = useRouter()

const activities = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(9)

const searchForm = reactive({
  status: null,
  title: ''
})

const getStatusType = (status) => {
  const types = { 0: 'info', 1: 'success', 2: 'warning', 3: '', 4: 'danger' }
  return types[status]
}

const getStatusText = (status) => {
  const texts = { 0: '草稿', 1: '报名中', 2: '进行中', 3: '已结束', 4: '已取消' }
  return texts[status]
}

const loadActivities = async () => {
  const res = await getActivityList({
    page: currentPage.value,
    size: pageSize.value,
    ...searchForm
  })
  activities.value = res.data.records
  total.value = res.data.total
}

const handlePageChange = (page) => {
  currentPage.value = page
  loadActivities()
}

const viewDetail = (id) => {
  router.push(`/volunteer/activities/${id}`)
}

onMounted(() => {
  loadActivities()
})
</script>

<style scoped>
.activity-card {
  margin-bottom: 20px;
}

.activity-card h3 {
  margin: 0 0 10px 0;
  color: #303133;
}

.description {
  color: #606266;
  margin: 10px 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 8px;
  margin: 8px 0;
  color: #606266;
  font-size: 14px;
}
</style>

