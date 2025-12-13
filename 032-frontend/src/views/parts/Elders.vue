<template>
  <el-space direction="vertical" fill>
    <el-form inline :model="f">
      <el-form-item><el-input v-model="f.name" placeholder="姓名" /></el-form-item>
      <el-form-item><el-select v-model="f.gender" placeholder="性别"><el-option label="男" value="男" /><el-option label="女" value="女" /></el-select></el-form-item>
      <el-form-item><el-date-picker v-model="f.birthDate" type="date" placeholder="出生日期" value-format="YYYY-MM-DD" /></el-form-item>
      <el-form-item><el-input v-model="f.phone" placeholder="电话" /></el-form-item>
      <el-form-item><el-input v-model="f.address" placeholder="地址" style="width:260px" /></el-form-item>
      <el-form-item><el-input v-model="f.emergencyContact" placeholder="紧急联系人" /></el-form-item>
      <el-button type="primary" @click="create">新增</el-button>
    </el-form>
    <el-table :data="list" style="width:100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="gender" label="性别" width="80" />
      <el-table-column prop="birthDate" label="出生" />
      <el-table-column prop="phone" label="电话" />
      <el-table-column prop="address" label="地址" />
    </el-table>
  </el-space>
</template>
<script setup>
import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import api from '../../api'
const f = reactive({ name:'', gender:'男', birthDate:'', phone:'', address:'', emergencyContact:'' })
const list = ref([])
const load = async()=>{ const { data } = await api.get('/api/elders'); if(data.code===0) list.value = data.data }
const create = async()=>{ const { data } = await api.post('/api/elders', f); if(data.code!==0){ ElMessage.error(data.message); return } ElMessage.success('ok'); load() }
onMounted(load)
</script>
