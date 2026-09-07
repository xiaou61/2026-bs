<template>
  <el-card v-if="relic">
    <h2>{{ relic.name }}</h2>
    <el-descriptions :column="3" border style="margin:20px 0">
      <el-descriptions-item label="编号">{{ relic.relicNo }}</el-descriptions-item>
      <el-descriptions-item label="分类">{{ relic.categoryName }}</el-descriptions-item>
      <el-descriptions-item label="朝代">{{ relic.dynastyName }}</el-descriptions-item>
      <el-descriptions-item label="等级">{{ levelMap[relic.level] }}</el-descriptions-item>
      <el-descriptions-item label="材质">{{ relic.material }}</el-descriptions-item>
      <el-descriptions-item label="尺寸">{{ relic.size }}</el-descriptions-item>
      <el-descriptions-item label="重量">{{ relic.weight }}</el-descriptions-item>
      <el-descriptions-item label="出土地点">{{ relic.origin }}</el-descriptions-item>
      <el-descriptions-item label="展厅">{{ relic.hallName }}</el-descriptions-item>
    </el-descriptions>
    <h4>文物简介</h4>
    <p style="line-height:1.8;color:#666">{{ relic.description }}</p>
    <h4>历史价值</h4>
    <p style="line-height:1.8;color:#666">{{ relic.historicalValue }}</p>
    <div style="margin-top:20px">
      <el-button type="primary" @click="handleLike">点赞 ({{ relic.likeCount }})</el-button>
      <el-button @click="handleFavorite">收藏</el-button>
      <el-button @click="router.back()">返回</el-button>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getRelicDetail, likeRelic, addFavorite } from '@/api'
import { ElMessage } from 'element-plus'
const route = useRoute(), router = useRouter()
const levelMap:Record<number,string> = {1:'一级',2:'二级',3:'三级',4:'一般'}
const relic = ref<any>(null)
onMounted(async () => { const res:any = await getRelicDetail(Number(route.params.id)); relic.value = res.data })
const handleLike = async () => { await likeRelic(relic.value.id); relic.value.likeCount++; ElMessage.success('点赞成功') }
const handleFavorite = async () => { await addFavorite({ relicId:relic.value.id }); ElMessage.success('收藏成功') }
</script>
