<template>
  <div>
    <el-card>
      <template #header>环保知识</template>
      <el-tabs v-model="activeTab" @tab-change="loadData">
        <el-tab-pane label="全部" name="0" />
        <el-tab-pane v-for="(v, k) in categoryMap" :key="k" :label="v" :name="String(k)" />
      </el-tabs>
      <el-table :data="list" v-loading="loading" @row-click="showDetail">
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="category" label="分类" width="120">
          <template #default="{ row }">
            <el-tag>{{ categoryMap[row.category] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="viewCount" label="浏览" width="80" />
        <el-table-column prop="publishTime" label="发布时间" width="180" />
      </el-table>
      <el-pagination style="margin-top: 16px;" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="currentItem.title" width="700">
      <div style="white-space: pre-wrap; line-height: 1.8;">{{ currentItem.content }}</div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getKnowledgeList, getKnowledgeDetail } from '@/api'

const categoryMap: Record<number, string> = { 1: '分类知识', 2: '环保资讯', 3: '政策法规' }
const activeTab = ref('0')
const loading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const query = reactive({ current: 1, size: 10, category: undefined as number | undefined })
const dialogVisible = ref(false)
const currentItem = ref<any>({})

const loadData = async () => {
  loading.value = true
  query.category = activeTab.value === '0' ? undefined : Number(activeTab.value)
  try {
    const res: any = await getKnowledgeList(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const showDetail = async (row: any) => {
  const res: any = await getKnowledgeDetail(row.id)
  currentItem.value = res.data
  dialogVisible.value = true
}

onMounted(loadData)
</script>
