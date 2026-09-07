<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="6"><el-card><el-statistic title="文物总数" :value="stats.relicCount" /></el-card></el-col>
      <el-col :span="6"><el-card><el-statistic title="展览数量" :value="stats.exhibitionCount" /></el-card></el-col>
      <el-col :span="6"><el-card><el-statistic title="预约数量" :value="stats.reservationCount" /></el-card></el-col>
      <el-col :span="6"><el-card><el-statistic title="游客数量" :value="stats.userCount" /></el-card></el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getStatistics } from '@/api'
const stats = ref<any>({})
onMounted(async () => { const res: any = await getStatistics(); stats.value = res.data })
</script>
