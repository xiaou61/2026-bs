<template>
  <div v-if="shop">
    <el-card>
      <h2>{{ shop.name }} <el-tag type="danger">评分 {{ shop.rating }}</el-tag></h2>
      <p>地址: {{ shop.address }}</p>
      <p>电话: {{ shop.phone }} | 营业时间: {{ shop.businessHours }}</p>
      <p style="color:#666;margin-top:15px">{{ shop.description }}</p>
      <el-button type="primary" style="margin-top:15px" @click="router.push('/user/reserve/'+shop.id)">立即预约</el-button>
      <el-button @click="handleFavorite">收藏店铺</el-button>
    </el-card>
    <el-card style="margin-top:20px"><template #header>房间列表</template>
      <el-table :data="rooms">
        <el-table-column prop="name" label="房间" />
        <el-table-column prop="capacity" label="容纳人数" />
        <el-table-column prop="price" label="价格" />
        <el-table-column prop="description" label="描述" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getShopDetail, getShopRooms, addFavorite } from '@/api'
import { ElMessage } from 'element-plus'
const route = useRoute(), router = useRouter()
const shop = ref<any>(null), rooms = ref<any[]>([])
onMounted(async () => {
  const id = Number(route.params.id)
  const [s, r]:any = await Promise.all([getShopDetail(id), getShopRooms(id)])
  shop.value = s.data; rooms.value = r.data
})
const handleFavorite = async () => { await addFavorite({ type:2, targetId:shop.value.id }); ElMessage.success('收藏成功') }
</script>
