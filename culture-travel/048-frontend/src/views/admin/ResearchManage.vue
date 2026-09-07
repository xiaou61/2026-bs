<template>
  <el-card>
    <template #header>研究成果审核</template>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="authorName" label="作者" />
      <el-table-column prop="relicName" label="关联文物" />
      <el-table-column prop="viewCount" label="浏览" width="80" />
      <el-table-column prop="status" label="状态"><template #default="{row}"><el-tag :type="['warning','success','danger'][row.status]">{{ ['待审核','已发布','已拒绝'][row.status] }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{row}">
          <el-button v-if="row.status===0" type="success" link @click="handleAudit(row.id,1)">通过</el-button>
          <el-button v-if="row.status===0" type="danger" link @click="handleAudit(row.id,2)">拒绝</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="current" @change="loadData" />
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getAdminResearches, auditResearch } from '@/api'
import { ElMessage } from 'element-plus'
const loading = ref(false), list = ref<any[]>([]), total = ref(0), current = ref(1)
const loadData = async () => { loading.value=true; const res:any = await getAdminResearches({current:current.value,size:10}); list.value=res.data.records; total.value=res.data.total; loading.value=false }
const handleAudit = async (id:number, status:number) => { await auditResearch(id, status); ElMessage.success('操作成功'); loadData() }
onMounted(loadData)
</script>
