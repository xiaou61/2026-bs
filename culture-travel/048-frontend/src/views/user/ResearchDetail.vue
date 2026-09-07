<template>
  <el-card v-if="research">
    <h2>{{ research.title }}</h2>
    <div style="color:#909399;margin:10px 0">作者: {{ research.authorName }} | 发布时间: {{ research.publishDate }} | 浏览: {{ research.viewCount }}</div>
    <el-divider />
    <h4>摘要</h4>
    <p style="color:#666;line-height:1.8">{{ research.summary }}</p>
    <h4>正文</h4>
    <div style="white-space:pre-wrap;line-height:1.8;color:#333">{{ research.content }}</div>
    <el-button style="margin-top:20px" @click="router.back()">返回</el-button>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getResearchDetail } from '@/api'
const route = useRoute(), router = useRouter()
const research = ref<any>(null)
onMounted(async () => { const res:any = await getResearchDetail(Number(route.params.id)); research.value = res.data })
</script>
