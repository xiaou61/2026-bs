<template>
  <div class="credit-history">
    <h2>信用记录</h2>
    <el-table :data="creditRecords" stripe>
      <el-table-column prop="scoreChange" label="分数变动" width="100">
        <template #default="{ row }">
          <span :style="{ color: row.scoreChange > 0 ? '#67c23a' : '#f56c6c' }">
            {{ row.scoreChange > 0 ? '+' : '' }}{{ row.scoreChange }}
          </span>
        </template>
      </el-table-column>
      <el-table-column prop="changeReason" label="变动原因" />
      <el-table-column prop="createTime" label="时间" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyCreditRecords } from '@/api/credit'

const creditRecords = ref([])

const loadCreditRecords = async () => {
  try {
    const res = await getMyCreditRecords()
    creditRecords.value = res.data
  } catch (error) {
    console.error('Failed to load credit records:', error)
  }
}

onMounted(() => {
  loadCreditRecords()
})
</script>