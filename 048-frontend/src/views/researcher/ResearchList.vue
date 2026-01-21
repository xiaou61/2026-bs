<template>
  <el-card>
    <template #header>我的研究成果</template>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="relicName" label="关联文物" />
      <el-table-column prop="publishDate" label="发布日期" />
      <el-table-column prop="viewCount" label="浏览" width="80" />
      <el-table-column prop="status" label="状态"><template #default="{row}"><el-tag :type="['warning','success','danger'][row.status]">{{ ['待审核','已发布','已拒绝'][row.status] }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{row}"><el-button type="primary" link @click="router.push('/researcher/edit/'+row.id)">编辑</el-button></template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="current" @change="loadData" />
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getResearcherResearches } from '@/api'
const router = useRouter()
const loading = ref(false), list = ref<any[]>([]), total = ref(0), current = ref(1)
const loadData = async () => { loading.value=true; const res:any = await getResearcherResearches({current:current.value,size:10}); list.value=res.data.records; total.value=res.data.total; loading.value=false }
onMounted(loadData)
</script>
