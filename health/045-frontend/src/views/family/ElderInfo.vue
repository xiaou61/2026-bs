<template>
  <div>
    <el-card v-if="elder">
      <template #header>老人信息</template>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="姓名">{{ elder.name }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ elder.gender === 1 ? '男' : '女' }}</el-descriptions-item>
        <el-descriptions-item label="年龄">{{ elder.age }}</el-descriptions-item>
        <el-descriptions-item label="护理等级">{{ careLevelMap[elder.careLevel] }}</el-descriptions-item>
        <el-descriptions-item label="入住日期">{{ elder.checkInDate }}</el-descriptions-item>
        <el-descriptions-item label="健康状况">{{ elder.healthStatus }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card v-else>
      <el-empty description="暂未关联老人信息">
        <el-button type="primary">联系管理员</el-button>
      </el-empty>
    </el-card>

    <el-card style="margin-top: 20px;" v-if="elder">
      <template #header>最近健康记录</template>
      <el-table :data="healthRecords">
        <el-table-column prop="recordTime" label="记录时间" />
        <el-table-column label="血压">
          <template #default="{ row }">{{ row.bloodPressureHigh }}/{{ row.bloodPressureLow }}</template>
        </el-table-column>
        <el-table-column prop="heartRate" label="心率" />
        <el-table-column prop="temperature" label="体温" />
        <el-table-column prop="symptoms" label="症状" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { getElderDetail, getHealthRecords } from '@/api'

const userStore = useUserStore()
const careLevelMap: Record<number, string> = { 1: '自理', 2: '半护理', 3: '全护理', 4: '特护' }
const elder = ref<any>(null)
const healthRecords = ref<any[]>([])

onMounted(async () => {
  const elderId = userStore.userInfo.elderId
  if (elderId) {
    const [elderRes, healthRes]: any = await Promise.all([
      getElderDetail(elderId),
      getHealthRecords(elderId, { current: 1, size: 10 })
    ])
    elder.value = elderRes.data
    healthRecords.value = healthRes.data.records
  }
})
</script>
