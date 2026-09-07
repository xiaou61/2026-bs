<template>
  <el-card>
    <template #header>剧本审核</template>
    <el-form inline>
      <el-form-item label="状态">
        <el-select v-model="query.status" placeholder="全部" clearable @change="loadData">
          <el-option label="待审核" :value="0" /><el-option label="已上架" :value="1" /><el-option label="已下架" :value="2" /><el-option label="审核拒绝" :value="3" />
        </el-select>
      </el-form-item>
    </el-form>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="title" label="剧本名称" />
      <el-table-column prop="playerCount" label="人数" />
      <el-table-column prop="duration" label="时长" />
      <el-table-column prop="price" label="授权价格" />
      <el-table-column prop="status" label="状态"><template #default="{row}"><el-tag :type="statusType[row.status]">{{ statusMap[row.status] }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{row}">
          <el-button v-if="row.status===0" type="success" link size="small" @click="handleAudit(row.id,1)">通过</el-button>
          <el-button v-if="row.status===0" type="danger" link size="small" @click="handleAudit(row.id,3)">拒绝</el-button>
          <el-button v-if="row.status===1" type="warning" link size="small" @click="handleAudit(row.id,2)">下架</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getAdminScripts, auditScript } from '@/api'
import { ElMessage } from 'element-plus'
const statusMap:Record<number,string> = {0:'待审核',1:'已上架',2:'已下架',3:'审核拒绝'}
const statusType:Record<number,string> = {0:'warning',1:'success',2:'info',3:'danger'}
const loading = ref(false), list = ref<any[]>([]), total = ref(0)
const query = reactive({ current:1, size:10, status:undefined as number|undefined })
const loadData = async () => { loading.value=true; const res:any=await getAdminScripts(query); list.value=res.data.records; total.value=res.data.total; loading.value=false }
const handleAudit = async (id:number,status:number) => { await auditScript(id,status); ElMessage.success('操作成功'); loadData() }
onMounted(loadData)
</script>
