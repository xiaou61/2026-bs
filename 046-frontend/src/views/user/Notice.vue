<template>
  <div>
    <el-card>
      <template #header>系统公告</template>
      <el-table :data="list" v-loading="loading" @row-click="showDetail">
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="type" label="类型" width="120">
          <template #default="{ row }">
            <el-tag :type="row.type === 1 ? 'primary' : 'success'">{{ typeMap[row.type] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="publishTime" label="发布时间" width="180" />
      </el-table>
      <el-pagination style="margin-top: 16px;" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="currentItem.title" width="600">
      <div style="white-space: pre-wrap; line-height: 1.8;">{{ currentItem.content }}</div>
      <div style="text-align: right; color: #909399; margin-top: 20px;">发布时间: {{ currentItem.publishTime }}</div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getNoticeList, getNoticeDetail } from '@/api'

const typeMap: Record<number, string> = { 1: '系统公告', 2: '活动通知' }
const loading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const query = reactive({ current: 1, size: 10 })
const dialogVisible = ref(false)
const currentItem = ref<any>({})

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

const showDetail = async (row: any) => {
  const res: any = await getNoticeDetail(row.id)
  currentItem.value = res.data
  dialogVisible.value = true
}

onMounted(loadData)
</script>
