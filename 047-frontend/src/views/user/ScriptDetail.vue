<template>
  <el-card v-if="script">
    <h2>{{ script.title }}</h2>
    <el-descriptions :column="3" border style="margin:20px 0">
      <el-descriptions-item label="类型">{{ typeMap[script.type] }}</el-descriptions-item>
      <el-descriptions-item label="难度">{{ diffMap[script.difficulty] }}</el-descriptions-item>
      <el-descriptions-item label="人数">{{ script.playerCount }}</el-descriptions-item>
      <el-descriptions-item label="时长">{{ script.duration }}</el-descriptions-item>
      <el-descriptions-item label="浏览">{{ script.viewCount }}</el-descriptions-item>
      <el-descriptions-item label="点赞">{{ script.likeCount }}</el-descriptions-item>
    </el-descriptions>
    <h4>剧本简介</h4>
    <p style="line-height:1.8;color:#666">{{ script.description }}</p>
    <div style="margin-top:20px">
      <el-button type="primary" @click="handleLike">点赞</el-button>
      <el-button @click="handleFavorite">收藏</el-button>
      <el-button @click="router.back()">返回</el-button>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getScriptDetail, likeScript, addFavorite } from '@/api'
import { ElMessage } from 'element-plus'
const route = useRoute(), router = useRouter()
const typeMap:Record<number,string> = {1:'情感',2:'推理',3:'恐怖',4:'欢乐',5:'机制'}
const diffMap:Record<number,string> = {1:'新手',2:'进阶',3:'硬核'}
const script = ref<any>(null)
onMounted(async () => { const res:any = await getScriptDetail(Number(route.params.id)); script.value = res.data })
const handleLike = async () => { await likeScript(script.value.id); script.value.likeCount++; ElMessage.success('点赞成功') }
const handleFavorite = async () => { await addFavorite({ type:1, targetId:script.value.id }); ElMessage.success('收藏成功') }
</script>
