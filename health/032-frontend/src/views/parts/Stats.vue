<template>
  <el-row :gutter="16">
    <el-col :span="6"><el-statistic title="用户" :value="d.users" /></el-col>
    <el-col :span="6"><el-statistic title="老人" :value="d.elders" /></el-col>
    <el-col :span="6"><el-statistic title="预约" :value="d.upcomingAppointments" /></el-col>
    <el-col :span="6"><el-statistic title="7天随访" :value="d.pendingFollowUps7d" /></el-col>
  </el-row>
</template>
<script setup>
import { reactive, onMounted } from 'vue'
import api from '../../api'
const d = reactive({ users:0, elders:0, upcomingAppointments:0, pendingFollowUps7d:0 })
onMounted(async()=>{ const { data } = await api.get('/api/stats/overview'); if(data.code===0) Object.assign(d, data.data) })
</script>
