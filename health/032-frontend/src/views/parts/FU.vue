<template>
  <el-space direction="vertical" fill>
    <el-form v-if="!isElder" inline :model="f">
      <el-form-item><el-input v-model.number="f.elderId" placeholder="老人ID" /></el-form-item>
      <el-form-item><el-input v-model.number="f.doctorUserId" placeholder="医生用户ID" /></el-form-item>
      <el-form-item><el-select v-model="f.type" placeholder="类型"><el-option label="电话" value="TEL" /><el-option label="上门" value="HOME" /></el-select></el-form-item>
      <el-form-item><el-date-picker v-model="f.dueDate" type="date" value-format="YYYY-MM-DD" placeholder="到期日期" /></el-form-item>
      <el-form-item><el-input v-model="f.note" placeholder="备注" /></el-form-item>
      <el-button type="primary" @click="create">创建</el-button>
      <el-button @click="loadElder">查询老人</el-button>
    </el-form>
    <el-button v-else @click="loadMy">刷新我的随访</el-button>
    <el-table :data="list">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="elderId" label="老人ID" />
      <el-table-column prop="doctorUserId" label="医生ID" />
      <el-table-column prop="type" label="类型" />
      <el-table-column prop="dueDate" label="到期" />
      <el-table-column prop="status" label="状态" />
    </el-table>
  </el-space>
</template>
<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import api from '../../api'

const currentUser = JSON.parse(localStorage.getItem('user') || '{}')
const isElder = currentUser.role === 'ELDER'
const f = reactive({ elderId:null, doctorUserId:null, type:'TEL', dueDate:'', note:'' })
const list = ref([])

const create = async () => {
  const { data } = await api.post('/api/followups', f)
  if (data.code !== 0) {
    ElMessage.error(data.message)
    return
  }
  ElMessage.success('创建成功')
  loadElder()
}

const loadElder = async () => {
  const { data } = await api.get(`/api/followups/elder/${f.elderId}`)
  if (data.code === 0) list.value = data.data
}

const loadMy = async () => {
  const { data } = await api.get('/api/followups/me')
  if (data.code === 0) list.value = data.data
}

onMounted(() => {
  if (isElder) {
    loadMy()
  }
})
</script>
