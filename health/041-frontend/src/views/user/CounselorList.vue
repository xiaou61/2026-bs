<template>
  <div class="page-container">
    <el-page-header @back="$router.back()" title="返回">
      <template #content>
        <span class="page-title">咨询师列表</span>
      </template>
    </el-page-header>

    <el-row :gutter="20" style="margin-top: 20px" v-loading="loading">
      <el-col :span="8" v-for="counselor in counselors" :key="counselor.id">
        <el-card shadow="hover" class="counselor-card">
          <h3>{{ counselor.realName }}</h3>
          <p class="specialties">擅长：{{ counselor.specialties }}</p>
          <p class="intro">{{ counselor.introduction }}</p>
          <div class="info">
            <span>评分：⭐{{ counselor.rating }}</span>
            <span>咨询：{{ counselor.consultCount }}次</span>
          </div>
          <div class="price">￥{{ counselor.price }}/次</div>
          <el-button type="primary" @click="viewDetail(counselor.id)" style="width: 100%">
            查看详情
          </el-button>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getCounselors } from '@/api/counselor'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loading = ref(false)
const counselors = ref([])

const loadCounselors = async () => {
  loading.value = true
  try {
    const res = await getCounselors()
    counselors.value = res.data
  } catch (error) {
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

const viewDetail = (id) => {
  router.push(`/counselor/${id}`)
}

onMounted(() => {
  loadCounselors()
})
</script>

<style scoped>
.page-container {
  padding: 20px;
  min-height: 100vh;
  background: #f0f2f5;
}

.page-title {
  font-size: 20px;
  font-weight: bold;
}

.counselor-card {
  margin-bottom: 20px;
}

.counselor-card h3 {
  margin-bottom: 10px;
}

.specialties {
  color: #409eff;
  margin-bottom: 10px;
}

.intro {
  color: #666;
  font-size: 14px;
  margin-bottom: 10px;
  min-height: 40px;
}

.info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  font-size: 14px;
}

.price {
  color: #f56c6c;
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}
</style>
