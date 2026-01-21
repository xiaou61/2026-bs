<template>
  <el-card>
    <template #header><el-input v-model="keyword" placeholder="搜索用户" style="width:200px" @keyup.enter="loadData"><template #append><el-button @click="loadData">搜索</el-button></template></el-input></template>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="realName" label="姓名" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="role" label="角色"><template #default="{row}"><el-tag>{{ roleMap[row.role] }}</el-tag></template></el-table-column>
      <el-table-column prop="status" label="状态"><template #default="{row}"><el-tag :type="row.status===1?'success':'danger'">{{ row.status===1?'正常':'禁用' }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="{row}"><el-button :type="row.status===1?'danger':'success'" link @click="handleStatus(row)">{{ row.status===1?'禁用':'启用' }}</el-button></template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="current" v-model:page-size="size" @change="loadData" />
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getUsers, updateUserStatus } from '@/api'
import { ElMessage } from 'element-plus'
const roleMap:Record<number,string> = {0:'游客',1:'讲解员',2:'研究员',3:'管理员'}
const loading = ref(false), list = ref<any[]>([]), total = ref(0), current = ref(1), size = ref(10), keyword = ref('')
const loadData = async () => { loading.value=true; const res:any=await getUsers({current:current.value,size:size.value,keyword:keyword.value}); list.value=res.data.records; total.value=res.data.total; loading.value=false }
const handleStatus = async (row:any) => { await updateUserStatus(row.id, row.status===1?0:1); ElMessage.success('操作成功'); loadData() }
onMounted(loadData)
</script>
