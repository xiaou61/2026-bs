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

    <el-pagination
      v-if="total > pageSize"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      layout="total, prev, pager, next"
      @current-change="handlePageChange"
      style="margin-top: 20px; text-align: center;"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyCreditRecords } from '@/api/credit'

const creditRecords = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const loadCreditRecords = async () => {
  try {
    const res = await getMyCreditRecords({
      current: currentPage.value,
      size: pageSize.value
    })
    creditRecords.value = res.data.records || []
    total.value = res.data.total || creditRecords.value.length
  } catch (error) {
    console.error('Failed to load credit records:', error)
  }
}

const handlePageChange = (page) => {
  currentPage.value = page
  loadCreditRecords()
}

onMounted(() => {
  loadCreditRecords()
})
</script>
