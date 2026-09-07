<template>
  <div class="page-container">
    <el-card>
      <template #header><span>今日配送任务</span></template>
      <el-empty v-if="tasks.length === 0" description="暂无待配送任务" />
      <el-table :data="tasks" v-loading="loading" stripe v-else>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="orderId" label="订单ID" width="80" />
        <el-table-column prop="routeId" label="路线ID" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag type="warning">待配送</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button type="success" size="small" @click="handleComplete(row.id)">确认完成</el-button>
            <el-button type="danger" size="small" @click="handleException(row)">标记异常</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog v-model="dialogVisible" title="标记异常" width="400px">
      <el-form :model="exForm" label-width="60px">
        <el-form-item label="备注"><el-input v-model="exForm.remark" type="textarea" :rows="3" placeholder="请输入异常原因" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitException">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getTodayTasks, completeDelivery, exceptionDelivery } from '../../api'

const loading = ref(false)
const tasks = ref([])
const dialogVisible = ref(false)
const exForm = reactive({ id: null, remark: '' })

const loadData = async () => {
  loading.value = true
  try { const res = await getTodayTasks(); tasks.value = res.data } finally { loading.value = false }
}

const handleComplete = async (id) => {
  await completeDelivery(id)
  ElMessage.success('配送完成')
  loadData()
}

const handleException = (row) => {
  exForm.id = row.id
  exForm.remark = ''
  dialogVisible.value = true
}

const submitException = async () => {
  await exceptionDelivery(exForm.id, { remark: exForm.remark })
  ElMessage.success('已标记异常')
  dialogVisible.value = false
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
</style>
