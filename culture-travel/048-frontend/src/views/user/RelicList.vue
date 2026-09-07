<template>
  <div>
    <el-card>
      <el-form inline>
        <el-form-item><el-input v-model="query.keyword" placeholder="搜索文物" clearable /><el-button type="primary" @click="loadData">搜索</el-button></el-form-item>
        <el-form-item label="分类"><el-select v-model="query.categoryId" clearable @change="loadData"><el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" /></el-select></el-form-item>
        <el-form-item label="朝代"><el-select v-model="query.dynastyId" clearable @change="loadData"><el-option v-for="d in dynasties" :key="d.id" :label="d.name" :value="d.id" /></el-select></el-form-item>
      </el-form>
    </el-card>
    <el-row :gutter="20" style="margin-top:20px">
      <el-col :span="6" v-for="r in list" :key="r.id">
        <el-card shadow="hover" style="margin-bottom:20px;cursor:pointer" @click="router.push('/user/relic/'+r.id)">
          <h4 style="margin:0 0 10px">{{ r.name }}</h4>
          <p style="color:#909399;font-size:13px">{{ r.categoryName }} · {{ r.dynastyName }}</p>
          <p style="color:#666;font-size:14px;margin:10px 0">{{ r.description?.slice(0,50) }}...</p>
          <div style="display:flex;justify-content:space-between;color:#909399;font-size:12px"><span>浏览 {{ r.viewCount }}</span><span>点赞 {{ r.likeCount }}</span></div>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination :total="total" v-model:current-page="query.current" @change="loadData" />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getRelics, getCategories, getDynasties } from '@/api'
const router = useRouter()
const categories = ref<any[]>([]), dynasties = ref<any[]>([]), list = ref<any[]>([]), total = ref(0)
const query = reactive({ current:1, size:12, keyword:'', categoryId:undefined as any, dynastyId:undefined as any })
const loadData = async () => { const res:any = await getRelics(query); list.value = res.data.records; total.value = res.data.total }
onMounted(async () => { const [c,d]:any = await Promise.all([getCategories(), getDynasties()]); categories.value = c.data; dynasties.value = d.data; loadData() })
</script>
