<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>护理记录</span>
          <el-button type="primary" @click="showAddDialog">添加记录</el-button>
        </div>
      </template>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="elderId" label="老人ID" />
        <el-table-column prop="careTime" label="护理时间" />
        <el-table-column prop="careType" label="护理类型" />
        <el-table-column prop="careContent" label="护理内容" />
        <el-table-column prop="remark" label="备注" />
      </el-table>
      <el-pagination style="margin-top: 16px;" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    </el-card>

    <el-dialog v-model="dialogVisible" title="添加护理记录" width="500">
      <el-form :model="form" label-width="100px">
        <el-form-item label="老人">
          <el-select v-model="form.elderId" placeholder="选择老人">
            <el-option v-for="e in elders" :key="e.id" :label="e.name" :value="e.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="护理类型"><el-input v-model="form.careType" placeholder="如：日常护理、用药护理" /></el-form-item>
        <el-form-item label="护理内容"><el-input v-model="form.careContent" type="textarea" /></el-form-item>
        <el-form-item label="备注"><el-input v-model="form.remark" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getCareRecords, addCareRecord, getElderList } from '@/api'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const query = reactive({ current: 1, size: 10 })
const dialogVisible = ref(false)
const form = ref<any>({})
const elders = ref<any[]>([])

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getCareRecords(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const showAddDialog = async () => {
  const res: any = await getElderList({ current: 1, size: 100, status: 1 })
  elders.value = res.data.records
  form.value = {}
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await addCareRecord(form.value)
  ElMessage.success('添加成功')
  dialogVisible.value = false
  loadData()
}

onMounted(loadData)
</script>
