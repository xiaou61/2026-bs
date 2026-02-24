<template>
  <div class="activity-list">
    <el-card class="search-card">
      <el-form :inline="true" :model="query">
        <el-form-item label="活动名称"><el-input v-model="query.name" placeholder="请输入活动名称" clearable /></el-form-item>
        <el-form-item label="活动状态">
          <el-select v-model="query.status" placeholder="全部" clearable>
            <el-option label="报名中" value="open" />
            <el-option label="已结束" value="closed" />
          </el-select>
        </el-form-item>
        <el-form-item><el-button type="primary" @click="loadData">搜索</el-button></el-form-item>
      </el-form>
    </el-card>
    <el-row :gutter="20">
      <el-col :span="8" v-for="activity in list" :key="activity.id">
        <el-card class="activity-card" shadow="hover">
          <el-image :src="activity.coverImage" class="activity-img" fit="cover" />
          <div class="activity-info">
            <h3>{{ activity.name }}</h3>
            <p class="time"><el-icon><Clock /></el-icon>{{ activity.startTime }} ~ {{ activity.endTime }}</p>
            <p class="location"><el-icon><Location /></el-icon>{{ activity.location }}</p>
            <p class="desc">{{ activity.description }}</p>
            <div class="footer">
              <span class="quota">已报名 {{ activity.registeredCount }}/{{ activity.maxParticipants }}</span>
              <el-button type="primary" size="small" @click="register(activity)" :disabled="activity.status !== 'open' || activity.registeredCount >= activity.maxParticipants">
                {{ activity.status !== 'open' ? '已结束' : (activity.registeredCount >= activity.maxParticipants ? '已满员' : '立即报名') }}
              </el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination v-if="total > 0" background layout="prev, pager, next" :total="total" :page-size="query.pageSize" v-model:current-page="query.pageNum" @current-change="loadData" class="pagination" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getActivityList, registerActivity } from '../api'
import { ElMessage, ElMessageBox } from 'element-plus'

const query = ref({ name: '', status: '', pageNum: 1, pageSize: 6 })
const list = ref([])
const total = ref(0)

const loadData = async () => {
  const res = await getActivityList(query.value)
  list.value = res.data.records || []
  total.value = res.data.total || 0
}

const register = async (activity) => {
  await ElMessageBox.confirm(`确认报名参加"${activity.name}"？`, '报名确认')
  await registerActivity(activity.id)
  ElMessage.success('报名成功')
  loadData()
}

onMounted(() => loadData())
</script>

<style scoped>
.search-card { margin-bottom: 20px; }
.activity-card { margin-bottom: 20px; }
.activity-img { width: 100%; height: 200px; }
.activity-info { padding: 15px 0; }
.activity-info h3 { margin: 0 0 10px; font-size: 18px; }
.time, .location { color: #999; font-size: 13px; display: flex; align-items: center; gap: 4px; margin-bottom: 8px; }
.desc { color: #666; font-size: 14px; margin-bottom: 15px; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; }
.footer { display: flex; justify-content: space-between; align-items: center; }
.quota { color: #409eff; font-size: 14px; }
.pagination { margin-top: 20px; justify-content: center; }
</style>
