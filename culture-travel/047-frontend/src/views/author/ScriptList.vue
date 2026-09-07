<template>
  <el-card>
    <template #header><div style="display:flex;justify-content:space-between"><span>我的剧本</span><el-button type="primary" @click="router.push('/author/create')">创作新剧本</el-button></div></template>
    <el-row :gutter="20" style="margin-bottom:20px">
      <el-col :span="8"><el-card shadow="never"><div class="stat-item"><div class="num">{{ stats.total||0 }}</div><div class="label">总剧本</div></div></el-card></el-col>
      <el-col :span="8"><el-card shadow="never"><div class="stat-item"><div class="num" style="color:#67c23a">{{ stats.online||0 }}</div><div class="label">已上架</div></div></el-card></el-col>
      <el-col :span="8"><el-card shadow="never"><div class="stat-item"><div class="num" style="color:#e6a23c">{{ stats.pending||0 }}</div><div class="label">待审核</div></div></el-card></el-col>
    </el-row>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="title" label="剧本名称" />
      <el-table-column prop="playerCount" label="人数" />
      <el-table-column prop="duration" label="时长" />
      <el-table-column prop="price" label="授权价格" />
      <el-table-column prop="viewCount" label="浏览" />
      <el-table-column prop="likeCount" label="点赞" />
      <el-table-column prop="status" label="状态"><template #default="{row}"><el-tag :type="statusType[row.status]">{{ statusMap[row.status] }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="100"><template #default="{row}"><el-button type="primary" link @click="router.push('/author/edit/'+row.id)">编辑</el-button></template></el-table-column>
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getMyScripts, getAuthorStats } from '@/api'
const router = useRouter()
const statusMap:Record<number,string> = {0:'待审核',1:'已上架',2:'已下架',3:'审核拒绝'}
const statusType:Record<number,string> = {0:'warning',1:'success',2:'info',3:'danger'}
const loading = ref(false), list = ref<any[]>([]), total = ref(0), stats = ref<any>({})
const query = reactive({ current:1, size:10 })
const loadData = async () => { loading.value=true; const res:any=await getMyScripts(query); list.value=res.data.records; total.value=res.data.total; loading.value=false }
onMounted(async () => { loadData(); const s:any = await getAuthorStats(); stats.value = s.data })
</script>

<style scoped>
.stat-item { text-align:center; padding:10px; }
.stat-item .num { font-size:28px; font-weight:bold; }
.stat-item .label { color:#909399; margin-top:5px; }
</style>
