<template>
  <div class="providers">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>寄养服务商</span>
          <el-input v-model="keyword" placeholder="搜索服务商" style="width: 300px" @keyup.enter="loadProviders">
            <template #append>
              <el-button @click="loadProviders"><el-icon><Search /></el-icon></el-button>
            </template>
          </el-input>
        </div>
      </template>
      <el-row :gutter="20" v-loading="loading">
        <el-col :span="8" v-for="item in providerList" :key="item.id" style="margin-bottom: 20px">
          <el-card shadow="hover" class="provider-card" @click="router.push(`/provider/${item.id}`)">
            <div class="provider-info">
              <h3>{{ item.name }}</h3>
              <div class="rating">
                <el-rate v-model="item.rating" disabled show-score />
              </div>
              <p class="address"><el-icon><Location /></el-icon>{{ item.address }}</p>
              <p class="phone"><el-icon><Phone /></el-icon>{{ item.phone }}</p>
              <p class="desc">{{ item.description }}</p>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        layout="total, prev, pager, next"
        @current-change="loadProviders"
      />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getProviderPage } from '../api'
import { Search, Location, Phone } from '@element-plus/icons-vue'

const router = useRouter()
const providerList = ref<any[]>([])
const loading = ref(false)
const keyword = ref('')
const pagination = reactive({ current: 1, size: 9, total: 0 })

const loadProviders = async () => {
  loading.value = true
  try {
    const res: any = await getProviderPage({
      current: pagination.current,
      size: pagination.size,
      keyword: keyword.value
    })
    providerList.value = res.data?.records || []
    pagination.total = res.data?.total || 0
  } finally {
    loading.value = false
  }
}

onMounted(() => loadProviders())
</script>

<style scoped>
.providers {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.provider-card {
  cursor: pointer;
}
.provider-info h3 {
  margin: 0 0 10px;
  color: #333;
}
.rating {
  margin-bottom: 10px;
}
.address, .phone {
  color: #666;
  font-size: 14px;
  margin: 5px 0;
  display: flex;
  align-items: center;
  gap: 5px;
}
.desc {
  color: #999;
  font-size: 13px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
