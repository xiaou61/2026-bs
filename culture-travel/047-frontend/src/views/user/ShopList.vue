<template>
  <div>
    <el-card><el-input v-model="keyword" placeholder="搜索店铺" style="width:300px" @keyup.enter="loadData"><template #append><el-button @click="loadData">搜索</el-button></template></el-input></el-card>
    <el-row :gutter="20" style="margin-top:20px">
      <el-col :span="8" v-for="s in list" :key="s.id">
        <el-card shadow="hover" style="margin-bottom:20px;cursor:pointer" @click="router.push('/user/shop/'+s.id)">
          <h3 style="margin:0 0 10px">{{ s.name }}</h3>
          <p style="color:#909399;font-size:13px">{{ s.address }}</p>
          <p style="color:#909399;font-size:13px">{{ s.phone }}</p>
          <div style="display:flex;justify-content:space-between;margin-top:10px">
            <span style="color:#f56c6c;font-weight:bold">评分 {{ s.rating }}</span>
            <el-button type="primary" size="small" @click.stop="router.push('/user/reserve/'+s.id)">预约</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination :total="total" v-model:current-page="current" @change="loadData" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getShops } from '@/api'
const router = useRouter()
const list = ref<any[]>([]), total = ref(0), current = ref(1), keyword = ref('')
const loadData = async () => { const res:any = await getShops({ current:current.value, size:9, keyword:keyword.value }); list.value = res.data.records; total.value = res.data.total }
onMounted(loadData)
</script>
