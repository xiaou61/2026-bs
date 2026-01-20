<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>公告管理</span>
          <el-button type="primary" @click="showAddDialog">发布公告</el-button>
        </div>
      </template>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="type" label="类型">
          <template #default="{ row }">{{ typeMap[row.type] }}</template>
        </el-table-column>
        <el-table-column prop="publishTime" label="发布时间" />
      </el-table>
      <el-pagination style="margin-top: 16px;" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    </el-card>

    <el-dialog v-model="dialogVisible" title="发布公告" width="600">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题" required><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="类型" required>
          <el-select v-model="form.type">
            <el-option v-for="(v, k) in typeMap" :key="k" :label="v" :value="Number(k)" />
          </el-select>
        </el-form-item>
        <el-form-item label="内容" required><el-input v-model="form.content" type="textarea" :rows="5" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">发布</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getNoticeList, publishNotice } from '@/api'
import { ElMessage } from 'element-plus'

const typeMap: Record<number, string> = { 1: '系统公告', 2: '活动通知' }
const loading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const query = reactive({ current: 1, size: 10 })
const dialogVisible = ref(false)
const form = ref<any>({ type: 1 })

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

const showAddDialog = () => {
  form.value = { type: 1 }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await publishNotice(form.value)
  ElMessage.success('发布成功')
  dialogVisible.value = false
  loadData()
}

onMounted(loadData)
</script>
