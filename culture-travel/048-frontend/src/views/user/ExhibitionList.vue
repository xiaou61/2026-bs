<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8" v-for="e in list" :key="e.id">
        <el-card shadow="hover" style="margin-bottom:20px;cursor:pointer" @click="router.push('/user/exhibition/'+e.id)">
          <h3 style="margin:0 0 10px">{{ e.title }}</h3>
          <p style="color:#909399;font-size:13px">{{ e.hallName }}</p>
          <p style="color:#666">{{ e.startDate }} 至 {{ e.endDate }}</p>
          <div style="display:flex;justify-content:space-between;margin-top:10px">
            <el-tag :type="['info','success','warning'][e.status]">{{ ['未开始','进行中','已结束'][e.status] }}</el-tag>
            <span style="color:#f56c6c;font-weight:bold">¥{{ e.ticketPrice }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination :total="total" v-model:current-page="current" @change="loadData" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getExhibitions } from '@/api'
const router = useRouter()
const list = ref<any[]>([]), total = ref(0), current = ref(1)
const loadData = async () => { const res:any = await getExhibitions({current:current.value,size:9}); list.value = res.data.records; total.value = res.data.total }
onMounted(loadData)
</script>
