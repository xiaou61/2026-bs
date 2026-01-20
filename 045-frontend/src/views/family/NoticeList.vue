<template>
  <div>
    <el-card>
      <template #header>通知公告</template>
      <el-table :data="list" v-loading="loading" @row-click="viewDetail">
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="type" label="类型">
          <template #default="{ row }">{{ typeMap[row.type] }}</template>
        </el-table-column>
        <el-table-column prop="publishTime" label="发布时间" />
      </el-table>
      <el-pagination style="margin-top: 16px;" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    </el-card>

    <el-dialog v-model="detailVisible" :title="currentNotice?.title" width="600">
      <div v-html="currentNotice?.content"></div>
      <p style="margin-top: 20px; color: #909399;">发布时间：{{ currentNotice?.publishTime }}</p>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getNoticeList, getNoticeDetail } from '@/api'

const typeMap: Record<number, string> = { 1: '通知', 2: '公告', 3: '活动' }
const loading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const query = reactive({ current: 1, size: 10 })
const detailVisible = ref(false)
const currentNotice = ref<any>(null)

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getNoticeList(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const viewDetail = async (row: any) => {
  const res: any = await getNoticeDetail(row.id)
  currentNotice.value = res.data
  detailVisible.value = true
}

onMounted(loadData)
</script>
