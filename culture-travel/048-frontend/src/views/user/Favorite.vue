<template>
  <el-row :gutter="20">
    <el-col :span="6" v-for="f in list" :key="f.id">
      <el-card shadow="hover" style="margin-bottom:20px">
        <h4 style="margin:0 0 10px;cursor:pointer" @click="router.push('/user/relic/'+f.relicId)">{{ f.relicName }}</h4>
        <p style="color:#909399;font-size:13px">{{ f.categoryName }} · {{ f.dynastyName }}</p>
        <div style="display:flex;justify-content:space-between;margin-top:10px">
          <span style="color:#909399;font-size:12px">{{ f.createTime }}</span>
          <el-button type="danger" link @click="handleRemove(f.id)">取消收藏</el-button>
        </div>
      </el-card>
    </el-col>
  </el-row>
  <el-empty v-if="!list.length" description="暂无收藏" />
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getFavorites, removeFavorite } from '@/api'
import { ElMessage } from 'element-plus'
const router = useRouter()
const list = ref<any[]>([])
const loadData = async () => { const res:any = await getFavorites(); list.value = res.data }
const handleRemove = async (id:number) => { await removeFavorite(id); ElMessage.success('已取消'); loadData() }
onMounted(loadData)
</script>
