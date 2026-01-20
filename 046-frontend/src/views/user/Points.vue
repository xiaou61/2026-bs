<template>
  <div>
    <el-card>
      <template #header>我的积分</template>
      <div style="text-align: center; padding: 30px 0;">
        <div style="font-size: 48px; font-weight: bold; color: #67c23a;">{{ points }}</div>
        <div style="color: #909399; margin-top: 10px;">当前可用积分</div>
        <el-button type="primary" style="margin-top: 20px;" @click="router.push('/user/exchange')">去兑换</el-button>
      </div>
    </el-card>

    <el-card style="margin-top: 20px;">
      <template #header>积分记录</template>
      <el-table :data="records" v-loading="loading">
        <el-table-column prop="type" label="类型">
          <template #default="{ row }">
            <el-tag :type="row.type === 1 ? 'success' : row.type === 2 ? 'danger' : 'warning'">
              {{ typeMap[row.type] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="points" label="积分变动">
          <template #default="{ row }">
            <span :style="{ color: row.type === 1 || row.type === 3 ? '#67c23a' : '#f56c6c' }">
              {{ row.type === 1 || row.type === 3 ? '+' : '-' }}{{ row.points }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="createTime" label="时间" />
      </el-table>
      <el-pagination style="margin-top: 16px;" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadRecords" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getMyPoints, getPointsRecords } from '@/api'

const router = useRouter()
const typeMap: Record<number, string> = { 1: '回收获得', 2: '兑换消费', 3: '签到奖励' }
const points = ref(0)
const loading = ref(false)
const records = ref<any[]>([])
const total = ref(0)
const query = reactive({ current: 1, size: 10 })

const loadPoints = async () => {
  const res: any = await getMyPoints()
  points.value = res.data
}

const loadRecords = async () => {
  loading.value = true
  try {
    const res: any = await getPointsRecords(query)
    records.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadPoints()
  loadRecords()
})
</script>
