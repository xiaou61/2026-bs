<template>
  <el-space direction="vertical" fill>
    <el-form inline :model="f">
      <el-form-item><el-input v-model.number="f.elderId" placeholder="老人ID" /></el-form-item>
      <el-form-item><el-input v-model.number="f.doctorUserId" placeholder="医生用户ID" /></el-form-item>
      <el-form-item><el-date-picker v-model="f.startTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" placeholder="开始时间" /></el-form-item>
      <el-form-item><el-input v-model="f.reason" placeholder="原因" /></el-form-item>
      <el-button type="primary" @click="create">创建</el-button>
      <el-button @click="loadElder">查询老人</el-button>
      <el-button @click="loadDoctor">医生日程</el-button>
    </el-form>
    <el-table :data="list">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="elderId" label="老人ID" />
      <el-table-column prop="doctorUserId" label="医生ID" />
      <el-table-column prop="startTime" label="时间" />
      <el-table-column prop="status" label="状态" />
    </el-table>
  </el-space>
</template>
<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import api from '../../api'
const f = reactive({ elderId:null, doctorUserId:null, startTime:'', reason:'' })
const list = ref([])
const create = async()=>{ const { data } = await api.post('/api/appointments', f); if(data.code!==0){ ElMessage.error(data.message); return } ElMessage.success('ok') }
const loadElder = async()=>{ const { data } = await api.get('/api/appointments/elder/'+f.elderId); if(data.code===0) list.value = data.data }
const loadDoctor = async()=>{ const { data } = await api.get('/api/appointments/doctor/'+f.doctorUserId+'/upcoming'); if(data.code===0) list.value = data.data }
</script>
