<template>
  <div class="portal-container notice-page">
    <div class="notice-grid">
      <el-card v-for="item in list" :key="item.id" shadow="never">
        <div class="notice-title">{{ item.title }}</div>
        <div class="notice-time">{{ formatDateTime(item.publishTime || item.createTime) }}</div>
        <div class="notice-content">{{ item.content }}</div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getPublicNoticeList } from '../../api'
import { formatDateTime } from '../../utils'

const list = ref([])

onMounted(async () => {
  const res = await getPublicNoticeList()
  list.value = res.data || []
})
</script>

<style scoped>
.notice-page {
  padding-top: 28px;
}

.notice-grid {
  display: grid;
  gap: 16px;
}

.notice-title {
  font-size: 20px;
  font-weight: 700;
}

.notice-time,
.notice-content {
  margin-top: 10px;
  color: var(--subtle);
  line-height: 1.8;
}
</style>
