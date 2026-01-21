<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="6"><el-card><div class="stat"><div class="num">{{ stats.users || 0 }}</div><div class="label">玩家数</div></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="stat"><div class="num">{{ stats.shops || 0 }}</div><div class="label">店铺数</div></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="stat"><div class="num">{{ stats.scripts || 0 }}</div><div class="label">剧本数</div></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="stat"><div class="num">{{ stats.reservations || 0 }}</div><div class="label">预约数</div></div></el-card></el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getStatistics } from '@/api'
const stats = ref<any>({})
onMounted(async () => { const res: any = await getStatistics(); stats.value = res.data })
</script>

<style scoped>
.stat { text-align: center; padding: 20px; }
.num { font-size: 36px; font-weight: bold; color: #409eff; }
.label { color: #909399; margin-top: 10px; }
</style>
