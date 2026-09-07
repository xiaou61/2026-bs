<template>
  <el-space direction="vertical" fill>
    <el-button @click="load">刷新</el-button>
    <el-table :data="list">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="role" label="角色" />
      <el-table-column prop="status" label="状态" />
      <el-table-column label="操作" width="240">
        <template #default="{row}">
          <el-select v-model="row.role" style="width:100px" @change="setRole(row)">
            <el-option label="ADMIN" value="ADMIN" />
            <el-option label="DOCTOR" value="DOCTOR" />
            <el-option label="ELDER" value="ELDER" />
          </el-select>
          <el-select v-model="row.status" style="width:100px" @change="setStatus(row)">
            <el-option :label="1" :value="1" />
            <el-option :label="0" :value="0" />
          </el-select>
        </template>
      </el-table-column>
    </el-table>
  </el-space>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import api from '../../api'
const list = ref([])
const load = async()=>{ const { data } = await api.get('/api/admin/users'); if(data.code===0) list.value = data.data }
const setRole = async(row)=>{ await api.post('/api/admin/users/'+row.id+'/role?role='+row.role); load() }
const setStatus = async(row)=>{ await api.post('/api/admin/users/'+row.id+'/status?status='+row.status); load() }
onMounted(load)
</script>
