<template>
  <el-card>
    <template #header>系统公告</template>
    <el-table :data="list" v-loading="loading" @row-click="showDetail">
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="type" label="类型" width="120"><template #default="{row}"><el-tag :type="row.type===1?'primary':'success'">{{ row.type===1?'系统公告':'活动通知' }}</el-tag></template></el-table-column>
      <el-table-column prop="publishTime" label="发布时间" width="180" />
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    <el-dialog v-model="dialogVisible" :title="currentItem.title" width="600">
      <div style="white-space:pre-wrap;line-height:1.8">{{ currentItem.content }}</div>
      <div style="text-align:right;color:#909399;margin-top:20px">发布时间: {{ currentItem.publishTime }}</div>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getNotices, getNoticeDetail } from '@/api'
const loading = ref(false), list = ref<any[]>([]), total = ref(0), dialogVisible = ref(false), currentItem = ref<any>({})
const query = reactive({ current:1, size:10 })
const loadData = async () => { loading.value=true; const res:any=await getNotices(query); list.value=res.data.records; total.value=res.data.total; loading.value=false }
const showDetail = async (row:any) => { const res:any = await getNoticeDetail(row.id); currentItem.value = res.data; dialogVisible.value = true }
onMounted(loadData)
</script>
