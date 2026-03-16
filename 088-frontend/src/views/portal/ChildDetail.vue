<template>
  <div class="portal-container detail-page">
    <el-card shadow="never" v-if="detail">
      <div class="detail-grid">
        <img class="detail-image" :src="detail.avatarUrl" :alt="detail.name">
        <div>
          <div class="detail-title">{{ detail.name }}</div>
          <div class="detail-sub">{{ detail.age }} 岁 · {{ detail.gender === 1 ? '男' : '女' }} · {{ adoptionStatusMap[detail.adoptionStatus] }}</div>
          <p class="detail-summary">{{ detail.summary }}</p>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="监护机构">{{ detail.guardianInfo }}</el-descriptions-item>
            <el-descriptions-item label="入院日期">{{ detail.admissionDate }}</el-descriptions-item>
            <el-descriptions-item label="健康状况">{{ detail.healthRecord?.healthStatus || '-' }}</el-descriptions-item>
            <el-descriptions-item label="疫苗情况">{{ detail.healthRecord?.vaccinationInfo || '-' }}</el-descriptions-item>
            <el-descriptions-item label="病史说明">{{ detail.healthRecord?.medicalHistory || '-' }}</el-descriptions-item>
          </el-descriptions>
          <div class="detail-actions">
            <router-link to="/application"><el-button type="primary">去提交申请</el-button></router-link>
            <router-link to="/children"><el-button plain>返回列表</el-button></router-link>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { getChildDetail } from '../../api'
import { adoptionStatusMap } from '../../utils'

const route = useRoute()
const detail = ref(null)

onMounted(async () => {
  const res = await getChildDetail(route.params.id)
  detail.value = res.data
})
</script>

<style scoped>
.detail-page {
  padding-top: 28px;
}

.detail-grid {
  display: grid;
  grid-template-columns: 360px 1fr;
  gap: 24px;
}

.detail-image {
  width: 100%;
  border-radius: 26px;
  object-fit: cover;
  min-height: 420px;
}

.detail-title {
  font-size: 34px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.detail-sub,
.detail-summary {
  margin-top: 12px;
  color: var(--subtle);
  line-height: 1.8;
}

.detail-actions {
  margin-top: 20px;
  display: flex;
  gap: 12px;
}

@media (max-width: 900px) {
  .detail-grid {
    grid-template-columns: 1fr;
  }
}
</style>
