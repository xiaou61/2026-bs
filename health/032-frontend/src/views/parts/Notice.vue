<template>
  <el-space direction="vertical" fill>
    <el-button @click="load">刷新</el-button>
    <el-table :data="list">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="content" label="内容" />
      <el-table-column prop="status" label="状态" />
      <el-table-column label="操作" width="120">
        <template #default="{row}">
          <el-button size="small" @click="read(row.id)" :disabled="row.status==='READ'">已读</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-space>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import api from '../../api'
const list = ref([])
const load = async()=>{ const { data } = await api.get('/api/notifications'); if(data.code===0) list.value = data.data }
const read = async(id)=>{ await api.post('/api/notifications/'+id+'/read'); load() }
onMounted(load)
</script>
