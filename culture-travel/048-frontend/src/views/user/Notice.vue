<template>
  <div>
    <el-card v-for="n in list" :key="n.id" style="margin-bottom:16px">
      <div style="display:flex;justify-content:space-between;align-items:center">
        <h3 style="margin:0">{{ n.title }}</h3>
        <el-tag :type="typeMap[n.type]">{{ typeText[n.type] }}</el-tag>
      </div>
      <p style="color:#666;line-height:1.8;margin:10px 0">{{ n.content }}</p>
      <div style="color:#909399;font-size:12px">{{ n.createTime }}</div>
    </el-card>
    <el-pagination :total="total" v-model:current-page="current" @change="loadData" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getNotices } from '@/api'
const list = ref<any[]>([]), total = ref(0), current = ref(1)
const typeText:Record<number,string> = {1:'系统公告',2:'展览通知',3:'闭馆通知'}
const typeMap:Record<number,string> = {1:'primary',2:'success',3:'warning'}
const loadData = async () => { const res:any = await getNotices({current:current.value,size:10}); list.value = res.data.records; total.value = res.data.total }
onMounted(loadData)
</script>
