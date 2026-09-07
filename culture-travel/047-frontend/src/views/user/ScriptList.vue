<template>
  <div>
    <el-card>
      <el-form inline>
        <el-form-item><el-input v-model="query.keyword" placeholder="搜索剧本" clearable /><el-button type="primary" @click="loadData">搜索</el-button></el-form-item>
        <el-form-item label="分类"><el-select v-model="query.categoryId" placeholder="全部" clearable @change="loadData"><el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" /></el-select></el-form-item>
        <el-form-item label="类型"><el-select v-model="query.type" placeholder="全部" clearable @change="loadData"><el-option label="情感" :value="1" /><el-option label="推理" :value="2" /><el-option label="恐怖" :value="3" /><el-option label="欢乐" :value="4" /></el-select></el-form-item>
      </el-form>
    </el-card>
    <el-row :gutter="20" style="margin-top:20px">
      <el-col :span="6" v-for="s in list" :key="s.id">
        <el-card shadow="hover" style="margin-bottom:20px;cursor:pointer" @click="router.push('/user/script/'+s.id)">
          <h3 style="margin:0 0 10px">{{ s.title }}</h3>
          <p style="color:#909399;font-size:13px">{{ typeMap[s.type] }} · {{ diffMap[s.difficulty] }} · {{ s.playerCount }}</p>
          <p style="color:#666;font-size:14px;margin:10px 0">{{ s.description?.slice(0,50) }}...</p>
          <div style="display:flex;justify-content:space-between;color:#909399;font-size:12px"><span>浏览 {{ s.viewCount }}</span><span>点赞 {{ s.likeCount }}</span></div>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getScripts, getCategories } from '@/api'
const router = useRouter()
const typeMap:Record<number,string> = {1:'情感',2:'推理',3:'恐怖',4:'欢乐',5:'机制'}
const diffMap:Record<number,string> = {1:'新手',2:'进阶',3:'硬核'}
const categories = ref<any[]>([]), list = ref<any[]>([]), total = ref(0)
const query = reactive({ current:1, size:12, keyword:'', categoryId:undefined as any, type:undefined as any })
const loadData = async () => { const res:any = await getScripts(query); list.value = res.data.records; total.value = res.data.total }
onMounted(async () => { const c:any = await getCategories(); categories.value = c.data; loadData() })
</script>
