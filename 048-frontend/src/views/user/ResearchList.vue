<template>
  <div>
    <el-card><el-input v-model="keyword" placeholder="搜索研究成果" style="width:300px" @keyup.enter="loadData"><template #append><el-button @click="loadData">搜索</el-button></template></el-input></el-card>
    <el-table :data="list" style="margin-top:20px">
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="authorName" label="作者" width="120" />
      <el-table-column prop="relicName" label="关联文物" />
      <el-table-column prop="publishDate" label="发布日期" width="120" />
      <el-table-column prop="viewCount" label="浏览" width="80" />
      <el-table-column label="操作" width="100">
        <template #default="{row}"><el-button type="primary" link @click="router.push('/user/research/'+row.id)">查看</el-button></template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="current" @change="loadData" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getResearches } from '@/api'
const router = useRouter()
const list = ref<any[]>([]), total = ref(0), current = ref(1), keyword = ref('')
const loadData = async () => { const res:any = await getResearches({current:current.value,size:10,keyword:keyword.value}); list.value = res.data.records; total.value = res.data.total }
onMounted(loadData)
</script>
