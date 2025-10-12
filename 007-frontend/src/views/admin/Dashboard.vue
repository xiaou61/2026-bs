<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <el-statistic title="总用户数" :value="stats.totalUsers">
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <el-statistic title="总活动数" :value="stats.totalActivities">
            <template #prefix>
              <el-icon><Trophy /></el-icon>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <el-statistic title="总志愿时长(小时)" :value="stats.totalVolunteerHours" :precision="1">
            <template #prefix>
              <el-icon><Timer /></el-icon>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="margin-top: 20px">
      <template #header>
        <h3>活动参与排行</h3>
      </template>
      <el-table :data="activityRanking" border>
        <el-table-column prop="title" label="活动名称" />
        <el-table-column prop="participants" label="参与人数" width="150">
          <template #default="{ row }">
            <el-progress :percentage="(row.participants / maxParticipants * 100)" />
            <span style="margin-left: 10px">{{ row.participants }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { getOverview, getActivityRanking } from '@/api/stats'

const stats = ref({
  totalUsers: 0,
  totalActivities: 0,
  totalVolunteerHours: 0
})

const activityRanking = ref([])

const maxParticipants = computed(() => {
  if (activityRanking.value.length === 0) return 1
  return Math.max(...activityRanking.value.map(a => a.participants))
})

const loadStats = async () => {
  const res = await getOverview()
  stats.value = res.data
}

const loadActivityRanking = async () => {
  const res = await getActivityRanking()
  activityRanking.value = res.data
}

onMounted(() => {
  loadStats()
  loadActivityRanking()
})
</script>

