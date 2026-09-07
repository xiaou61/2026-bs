<template>
  <div class="fee-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>物业缴费</span>
          <el-button type="primary" @click="handleAdd">生成账单</el-button>
        </div>
      </template>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="ownerName" label="业主姓名" />
        <el-table-column prop="amount" label="金额" />
        <el-table-column prop="type" label="类型">
          <template #default="scope">
            <el-tag>{{ scope.row.type === 'PROPERTY' ? '物业费' : '停车费' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag :type="scope.row.status === 'PAID' ? 'success' : 'danger'">
              {{ scope.row.status === 'PAID' ? '已缴' : '未缴' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" />
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button 
              size="small" 
              type="success" 
              v-if="scope.row.status === 'UNPAID'"
              @click="handlePay(scope.row)"
            >缴费</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" title="生成账单">
      <el-form :model="form" label-width="80px">
        <el-form-item label="业主ID">
          <el-input v-model="form.ownerId" type="number" />
        </el-form-item>
        <el-form-item label="金额">
          <el-input v-model="form.amount" type="number" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="form.type" placeholder="请选择">
            <el-option label="物业费" value="PROPERTY" />
            <el-option label="停车费" value="PARKING" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import request from '../utils/request'
import { ElMessage } from 'element-plus'

const tableData = ref([])
const dialogVisible = ref(false)
const form = reactive({
  ownerId: '',
  amount: '',
  type: 'PROPERTY'
})

const loadData = async () => {
  const res = await request.get('/fee/list')
  if (res.code === '200') {
    tableData.value = res.data
  }
}

const handleAdd = () => {
  Object.assign(form, {
    ownerId: '',
    amount: '',
    type: 'PROPERTY'
  })
  dialogVisible.value = true
}

const handlePay = async (row) => {
  const res = await request.post(`/fee/pay/${row.id}`)
  if (res.code === '200') {
    ElMessage.success('缴费成功')
    loadData()
  }
}

const submitForm = async () => {
  const res = await request.post('/fee/create', form)
  if (res.code === '200') {
    ElMessage.success('生成成功')
    dialogVisible.value = false
    loadData()
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
