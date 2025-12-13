<template>
  <el-space direction="vertical" fill>
    <el-form inline :model="f">
      <el-form-item><el-input v-model.number="f.elderId" placeholder="老人ID" /></el-form-item>
      <el-form-item><el-select v-model="f.type" placeholder="类型"><el-option label="BP" value="BP" /><el-option label="WEIGHT" value="WEIGHT" /><el-option label="HEIGHT" value="HEIGHT" /></el-select></el-form-item>
      <el-form-item><el-input v-model.number="f.value1" placeholder="值1" /></el-form-item>
      <el-form-item><el-input v-model.number="f.value2" placeholder="值2" /></el-form-item>
      <el-form-item><el-input v-model="f.unit" placeholder="单位" /></el-form-item>
      <el-button type="primary" @click="create">录入</el-button>
      <el-button @click="load">最近</el-button>
    </el-form>
    <el-table :data="list">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="type" label="类型" />
      <el-table-column prop="value1" label="值1" />
      <el-table-column prop="value2" label="值2" />
      <el-table-column prop="unit" label="单位" />
      <el-table-column prop="measuredAt" label="测量时间" />
    </el-table>
  </el-space>
</template>
<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import dayjs from 'dayjs'
import api from '../../api'
const f = reactive({ elderId:null, type:'BP', value1:null, value2:null, unit:'', measuredAt:null })
const list = ref([])
const create = async()=>{ f.measuredAt = new Date().toISOString().slice(0,19); const { data } = await api.post('/api/measurements', f); if(data.code!==0){ ElMessage.error(data.message); return } ElMessage.success('ok') }
const load = async()=>{ const { data } = await api.get('/api/measurements/elder/'+f.elderId+'/latest?size=10&type='+f.type); if(data.code===0) list.value = data.data }
</script>
