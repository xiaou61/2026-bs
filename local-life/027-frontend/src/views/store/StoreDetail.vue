<template>
  <div class="store-detail-page">
    <el-card v-loading="loading">
      <!-- 门店信息 -->
      <div class="store-header">
        <h2>{{ store.name }}</h2>
        <el-rate v-model="store.rating" disabled show-score />
      </div>

      <el-descriptions :column="2" border>
        <el-descriptions-item label="地址">
          <el-icon><Location /></el-icon> {{ store.address }}
        </el-descriptions-item>
        <el-descriptions-item label="电话">
          <el-icon><Phone /></el-icon> {{ store.phone }}
        </el-descriptions-item>
        <el-descriptions-item label="营业时间" :span="2">
          <el-icon><Clock /></el-icon> {{ store.openTime }} - {{ store.closeTime }}
        </el-descriptions-item>
        <el-descriptions-item label="简介" :span="2">
          {{ store.intro }}
        </el-descriptions-item>
      </el-descriptions>

      <!-- 理发师列表 -->
      <el-divider content-position="left">
        <h3>门店理发师</h3>
      </el-divider>
      <el-row :gutter="20">
        <el-col :span="6" v-for="hairdresser in hairdressers" :key="hairdresser.id">
          <el-card shadow="hover" class="hairdresser-card" @click="handleAppointment(hairdresser)">
            <div class="hairdresser-info">
              <el-avatar :size="80" :src="hairdresser.avatar" />
              <h4>{{ hairdresser.name }}</h4>
              <el-rate v-model="hairdresser.rating" disabled show-score />
              <p>{{ hairdresser.workYears }}年经验</p>
              <el-button type="primary" size="small">预约TA</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <!-- 服务项目 -->
      <el-divider content-position="left">
        <h3>服务项目</h3>
      </el-divider>
      <el-table :data="services" stripe>
        <el-table-column prop="category" label="分类" width="120" />
        <el-table-column prop="name" label="项目名称" />
        <el-table-column prop="price" label="价格" width="100">
          <template #default="{ row }">
            ¥{{ row.price }}
          </template>
        </el-table-column>
        <el-table-column prop="duration" label="时长" width="100">
          <template #default="{ row }">
            {{ row.duration }}分钟
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getStoreDetail, getStoreHairdressers, getStoreServices } from '@/api/store'
import { Location, Phone, Clock } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const store = ref({})
const hairdressers = ref([])
const services = ref([])

// 获取门店详情
const fetchStoreDetail = async () => {
  loading.value = true
  try {
    const storeId = route.params.id

    // 获取门店信息
    const storeRes = await getStoreDetail(storeId)
    store.value = storeRes.data

    // 获取理发师
    const hairdressersRes = await getStoreHairdressers(storeId)
    hairdressers.value = hairdressersRes.data

    // 获取服务项目
    const servicesRes = await getStoreServices(storeId)
    services.value = servicesRes.data
  } catch (error) {
    console.error('获取门店详情失败：', error)
  } finally {
    loading.value = false
  }
}

// 处理预约
const handleAppointment = (hairdresser) => {
  router.push({
    path: '/appointment/create',
    query: {
      storeId: store.value.id,
      hairdresserId: hairdresser.id
    }
  })
}

onMounted(() => {
  fetchStoreDetail()
})
</script>

<style scoped>
.store-detail-page {
  max-width: 1200px;
  margin: 30px auto;
  padding: 0 20px;
}

.store-header {
  margin-bottom: 20px;
}

.store-header h2 {
  margin-bottom: 10px;
}

.hairdresser-card {
  cursor: pointer;
  margin-bottom: 20px;
  transition: transform 0.3s;
}

.hairdresser-card:hover {
  transform: translateY(-5px);
}

.hairdresser-info {
  text-align: center;
}

.hairdresser-info h4 {
  margin: 15px 0 10px;
}

.hairdresser-info p {
  margin: 10px 0;
  color: #666;
}
</style>
