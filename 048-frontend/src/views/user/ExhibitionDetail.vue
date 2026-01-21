<template>
  <el-card v-if="exhibition">
    <h2>{{ exhibition.title }} <el-tag :type="['info','success','warning'][exhibition.status]">{{ ['未开始','进行中','已结束'][exhibition.status] }}</el-tag></h2>
    <el-descriptions :column="2" border style="margin:20px 0">
      <el-descriptions-item label="展厅">{{ exhibition.hallName }}</el-descriptions-item>
      <el-descriptions-item label="票价">¥{{ exhibition.ticketPrice }}</el-descriptions-item>
      <el-descriptions-item label="开始日期">{{ exhibition.startDate }}</el-descriptions-item>
      <el-descriptions-item label="结束日期">{{ exhibition.endDate }}</el-descriptions-item>
    </el-descriptions>
    <h4>展览介绍</h4>
    <p style="line-height:1.8;color:#666">{{ exhibition.description }}</p>
    <div style="margin-top:20px">
      <el-button type="primary" @click="router.push('/user/reserve/'+exhibition.id)">预约参观</el-button>
      <el-button @click="router.back()">返回</el-button>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getExhibitionDetail } from '@/api'
const route = useRoute(), router = useRouter()
const exhibition = ref<any>(null)
onMounted(async () => { const res:any = await getExhibitionDetail(Number(route.params.id)); exhibition.value = res.data })
</script>
