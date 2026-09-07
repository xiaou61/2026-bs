<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>我的反馈</span>
          <el-button type="success" @click="handleAdd">提交反馈</el-button>
        </div>
      </template>
      <el-empty v-if="list.length === 0" description="暂无反馈记录" />
      <el-table :data="list" v-loading="loading" stripe v-else>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="type" label="类型" width="80">
          <template #default="{ row }">
            <el-tag :type="row.type === 'COMPLAINT' ? 'danger' : 'primary'">{{ row.type === 'COMPLAINT' ? '投诉' : '反馈' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="内容" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已处理' : '待处理' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="reply" label="回复" show-overflow-tooltip />
        <el-table-column prop="createTime" label="时间" width="160" />
      </el-table>
    </el-card>
    <el-dialog v-model="dialogVisible" title="提交反馈" width="450px">
      <el-form :model="form" ref="formRef" label-width="60px" :rules="rules">
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" style="width: 100%;">
            <el-option label="投诉" value="COMPLAINT" />
            <el-option label="反馈" value="FEEDBACK" />
          </el-select>
        </el-form-item>
        <el-form-item label="内容" prop="content"><el-input v-model="form.content" type="textarea" :rows="4" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMyComplaints, addComplaint } from '../../api'

const loading = ref(false)
const list = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({ type: 'FEEDBACK', content: '' })
const rules = {
  type: [{ required: true, message: '请选择类型', trigger: 'change' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try { const res = await getMyComplaints(); list.value = res.data } finally { loading.value = false }
}

const handleAdd = () => {
  Object.assign(form, { type: 'FEEDBACK', content: '' })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await addComplaint(form)
  ElMessage.success('提交成功')
  dialogVisible.value = false
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
</style>
