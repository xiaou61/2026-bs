<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>探访预约</span>
          <el-button type="primary" @click="showApplyDialog">申请探访</el-button>
        </div>
      </template>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="visitorName" label="访客姓名" />
        <el-table-column prop="visitTime" label="预约时间" />
        <el-table-column prop="purpose" label="探访目的" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusMap[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" />
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" title="申请探访" width="500">
      <el-form :model="form" label-width="100px">
        <el-form-item label="访客姓名" required><el-input v-model="form.visitorName" /></el-form-item>
        <el-form-item label="联系电话" required><el-input v-model="form.visitorPhone" /></el-form-item>
        <el-form-item label="探访时间" required>
          <el-date-picker v-model="form.visitTime" type="datetime" placeholder="选择时间" />
        </el-form-item>
        <el-form-item label="探访目的"><el-input v-model="form.purpose" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">提交申请</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { getMyVisits, applyVisit } from '@/api'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const statusMap: Record<number, string> = { 0: '待审核', 1: '已通过', 2: '已拒绝', 3: '已完成', 4: '已取消' }
const statusType: Record<number, string> = { 0: 'warning', 1: 'success', 2: 'danger', 3: 'info', 4: 'info' }
const loading = ref(false)
const list = ref<any[]>([])
const dialogVisible = ref(false)
const form = ref<any>({})

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getMyVisits({ current: 1, size: 20 })
    list.value = res.data.records
  } finally {
    loading.value = false
  }
}

const showApplyDialog = () => {
  form.value = { elderId: userStore.userInfo.elderId }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await applyVisit(form.value)
  ElMessage.success('申请成功')
  dialogVisible.value = false
  loadData()
}

onMounted(loadData)
</script>
