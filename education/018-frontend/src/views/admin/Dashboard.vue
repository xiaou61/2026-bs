<template>
  <div class="dashboard">
    <el-row :gutter="20" class="stat-row">
      <el-col :span="8" v-for="item in stats" :key="item.label">
        <el-card>
          <div class="stat-label">{{ item.label }}</div>
          <div class="stat-value">{{ item.value }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-card>
      <template #header>
        <div class="header-bar">
          <h3>企业审核</h3>
          <el-select v-model="statusFilter" placeholder="筛选状态" clearable @change="handleFilterChange">
            <el-option label="全部" :value="undefined" />
            <el-option label="待审核" :value="0" />
            <el-option label="已通过" :value="1" />
            <el-option label="已拒绝" :value="2" />
          </el-select>
        </div>
      </template>

      <el-table :data="companies" v-loading="loading">
        <el-table-column prop="name" label="企业名称" min-width="180" />
        <el-table-column prop="industry" label="行业" width="120" />
        <el-table-column prop="location" label="地点" width="140" />
        <el-table-column prop="scale" label="规模" width="120" />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="{ row }">
            <el-tag v-if="row.status === 0" type="warning">待审核</el-tag>
            <el-tag v-else-if="row.status === 1" type="success">已通过</el-tag>
            <el-tag v-else type="danger">已拒绝</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="企业简介" min-width="240" show-overflow-tooltip />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button type="success" link @click="handleReview(row.id, 1)">通过</el-button>
            <el-button type="danger" link @click="handleReview(row.id, 2)">拒绝</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        layout="total, prev, pager, next"
        @current-change="loadCompanies"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getAdminCompanyList, getAdminOverview, reviewCompany } from '@/api/admin'

const loading = ref(false)
const overview = ref({})
const companies = ref([])
const statusFilter = ref(undefined)
const pagination = ref({
  page: 1,
  size: 10,
  total: 0
})

const stats = computed(() => [
  { label: '用户总数', value: overview.value.userCount || 0 },
  { label: '企业总数', value: overview.value.companyCount || 0 },
  { label: '待审核企业', value: overview.value.pendingCompanyCount || 0 },
  { label: '岗位总数', value: overview.value.jobCount || 0 },
  { label: '投递总数', value: overview.value.applicationCount || 0 },
  { label: '面试总数', value: overview.value.interviewCount || 0 }
])

const loadOverview = async () => {
  const res = await getAdminOverview()
  overview.value = res.data
}

const loadCompanies = async () => {
  loading.value = true
  try {
    const res = await getAdminCompanyList({
      page: pagination.value.page,
      size: pagination.value.size,
      status: statusFilter.value
    })
    companies.value = res.data.records
    pagination.value.total = res.data.total
  } finally {
    loading.value = false
  }
}

const handleFilterChange = () => {
  pagination.value.page = 1
  loadCompanies()
}

const handleReview = async (id, status) => {
  await reviewCompany({ id, status })
  ElMessage.success('审核状态已更新')
  await Promise.all([loadOverview(), loadCompanies()])
}

onMounted(async () => {
  await Promise.all([loadOverview(), loadCompanies()])
})
</script>

<style scoped>
.dashboard {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.stat-row {
  row-gap: 20px;
}

.stat-label {
  color: #909399;
  font-size: 14px;
}

.stat-value {
  margin-top: 8px;
  font-size: 30px;
  font-weight: bold;
  color: #303133;
}

.header-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
