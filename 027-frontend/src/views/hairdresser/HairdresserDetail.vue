<template>
  <div class="hairdresser-detail-page">
    <el-card v-loading="loading">
      <!-- 理发师信息 -->
      <div class="hairdresser-header">
        <el-avatar :size="120" :src="hairdresser.avatar" />
        <div class="hairdresser-info">
          <h2>{{ hairdresser.name }}</h2>
          <el-rate v-model="hairdresser.rating" disabled show-score />
          <p><el-icon><Briefcase /></el-icon> {{ hairdresser.workYears }}年从业经验</p>
          <p><el-icon><Star /></el-icon> 工号：{{ hairdresser.workNo }}</p>
          <el-tag v-if="hairdresser.status === 1" type="success" size="large">在岗</el-tag>
          <el-tag v-else-if="hairdresser.status === 0" type="info" size="large">休息</el-tag>
          <el-tag v-else type="warning" size="large">请假</el-tag>
        </div>
      </div>

      <el-divider />

      <!-- 个人简介 -->
      <div class="intro-section">
        <h3>个人简介</h3>
        <p>{{ hairdresser.intro || '暂无简介' }}</p>
      </div>

      <!-- 预约按钮 -->
      <div style="text-align: center; margin-top: 30px">
        <el-button type="primary" size="large" @click="handleAppointment">
          预约TA
        </el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getHairdresserDetail } from '@/api/hairdresser'
import { Briefcase, Star } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const hairdresser = ref({})

// 获取理发师详情
const fetchDetail = async () => {
  loading.value = true
  try {
    const res = await getHairdresserDetail(route.params.id)
    hairdresser.value = res.data
  } catch (error) {
    console.error('获取理发师详情失败：', error)
  } finally {
    loading.value = false
  }
}

// 预约
const handleAppointment = () => {
  router.push({
    path: '/appointment/create',
    query: {
      storeId: hairdresser.value.storeId,
      hairdresserId: hairdresser.value.id
    }
  })
}

onMounted(() => {
  fetchDetail()
})
</script>

<style scoped>
.hairdresser-detail-page {
  max-width: 800px;
  margin: 30px auto;
  padding: 0 20px;
}

.hairdresser-header {
  display: flex;
  align-items: center;
  gap: 30px;
}

.hairdresser-info h2 {
  margin: 0 0 10px;
}

.hairdresser-info p {
  margin: 8px 0;
  color: #666;
  display: flex;
  align-items: center;
}

.hairdresser-info p .el-icon {
  margin-right: 8px;
}

.intro-section h3 {
  margin-bottom: 15px;
}

.intro-section p {
  line-height: 1.8;
  color: #666;
}
</style>
