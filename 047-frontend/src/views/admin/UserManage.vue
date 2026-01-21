<template>
  <el-card>
    <template #header>用户管理</template>
    <el-form inline>
      <el-form-item label="角色">
        <el-select v-model="query.role" placeholder="全部" clearable @change="loadData">
          <el-option label="玩家" :value="0" /><el-option label="店家" :value="1" /><el-option label="作者" :value="2" /><el-option label="管理员" :value="3" />
        </el-select>
      </el-form-item>
    </el-form>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="realName" label="姓名" />
      <el-table-column prop="phone" label="手机" />
      <el-table-column prop="role" label="角色"><template #default="{row}">{{ roleMap[row.role] }}</template></el-table-column>
      <el-table-column prop="status" label="状态"><template #default="{row}"><el-tag :type="row.status===1?'success':'danger'">{{ row.status===1?'正常':'禁用' }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="{row}">
          <el-button v-if="row.status===1" type="danger" link size="small" @click="handleStatus(row.id,0)">禁用</el-button>
          <el-button v-else type="success" link size="small" @click="handleStatus(row.id,1)">启用</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getAdminUsers, updateUserStatus } from '@/api'
import { ElMessage } from 'element-plus'
const roleMap: Record<number,string> = {0:'玩家',1:'店家',2:'作者',3:'管理员'}
const loading = ref(false), list = ref<any[]>([]), total = ref(0)
const query = reactive({ current:1, size:10, role:undefined as number|undefined })
const loadData = async () => { loading.value=true; const res:any=await getAdminUsers(query); list.value=res.data.records; total.value=res.data.total; loading.value=false }
const handleStatus = async (id:number,status:number) => { await updateUserStatus(id,status); ElMessage.success('操作成功'); loadData() }
onMounted(loadData)
</script>
