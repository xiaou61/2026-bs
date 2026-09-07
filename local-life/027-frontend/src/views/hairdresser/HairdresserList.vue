<template>
  <div class="hairdresser-list-page">
    <!-- 搜索和筛选 -->
    <div class="filter-bar">
      <el-select v-model="storeId" placeholder="选择门店" clearable style="width: 200px" @change="fetchHairdressers">
        <el-option label="全部门店" :value="null" />
        <!-- 这里应该动态加载门店列表 -->
      </el-select>
      
      <el-select v-model="sortBy" placeholder="排序方式" style="margin-left: 20px; width: 150px" @change="fetchHairdressers">
        <el-option label="按评分排序" value="rating" />
        <el-option label="最新发布" value="" />
      </el-select>
    </div>

    <!-- 理发师列表 -->
    <div class="hairdresser-list">
      <el-row :gutter="20" v-loading="loading">
        <el-col :span="6" v-for="hairdresser in hairdressers" :key="hairdresser.id">
          <el-card shadow="hover" class="hairdresser-card" @click="goToDetail(hairdresser.id)">
            <div class="hairdresser-content">
              <el-avatar :size="100" :src="hairdresser.avatar" />
              <h3>{{ hairdresser.name }}</h3>
              <el-rate v-model="hairdresser.rating" disabled show-score />
              <p><el-icon><Briefcase /></el-icon> {{ hairdresser.workYears }}年经验</p>
              <p class="intro">{{ hairdresser.intro }}</p>
              <el-tag v-if="hairdresser.status === 1" type="success">在岗</el-tag>
              <el-tag v-else type="info">休息中</el-tag>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="fetchHairdressers"
        style="margin-top: 30px; text-align: center"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getHairdresserList } from '@/api/hairdresser'
import { Briefcase } from '@element-plus/icons-vue'

const router = useRouter()

const loading = ref(false)
const hairdressers = ref([])
const currentPage = ref(1)
const pageSize = ref(12)
const total = ref(0)
const storeId = ref(null)
const sortBy = ref('rating')

// 获取理发师列表
const fetchHairdressers = async () => {
  loading.value = true
  try {
    const res = await getHairdresserList({
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      storeId: storeId.value,
      sortBy: sortBy.value
    })
    hairdressers.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error('获取理发师列表失败：', error)
  } finally {
    loading.value = false
  }
}

// 跳转详情
const goToDetail = (id) => {
  router.push(`/hairdresser/${id}`)
}

onMounted(() => {
  fetchHairdressers()
})
</script>

<style scoped>
.hairdresser-list-page {
  max-width: 1200px;
  margin: 0 auto;
  padding: 30px 20px;
}

.filter-bar {
  margin-bottom: 30px;
  text-align: center;
}

.hairdresser-card {
  cursor: pointer;
  margin-bottom: 20px;
  transition: transform 0.3s;
}

.hairdresser-card:hover {
  transform: translateY(-5px);
}

.hairdresser-content {
  text-align: center;
  padding: 20px 10px;
}

.hairdresser-content h3 {
  margin: 15px 0 10px;
  font-size: 18px;
}

.hairdresser-content p {
  margin: 8px 0;
  color: #666;
  display: flex;
  align-items: center;
  justify-content: center;
}

.hairdresser-content p .el-icon {
  margin-right: 5px;
}

.intro {
  font-size: 14px;
  color: #999;
  margin: 10px 0 !important;
  min-height: 40px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
</style>
