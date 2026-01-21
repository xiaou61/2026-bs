<template>
  <el-card>
    <template #header>我的收藏</template>
    <el-tabs v-model="activeTab" @tab-change="loadData">
      <el-tab-pane label="收藏的剧本" name="1" />
      <el-tab-pane label="收藏的店铺" name="2" />
    </el-tabs>
    <el-table :data="list">
      <el-table-column prop="targetId" label="ID" />
      <el-table-column prop="createTime" label="收藏时间" />
      <el-table-column label="操作" width="100">
        <template #default="{row}"><el-button type="danger" link @click="handleRemove(row.id)">取消收藏</el-button></template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getFavorites, removeFavorite } from '@/api'
import { ElMessage } from 'element-plus'
const activeTab = ref('1'), list = ref<any[]>([])
const loadData = async () => { const res:any = await getFavorites(Number(activeTab.value)); list.value = res.data || [] }
const handleRemove = async (id:number) => { await removeFavorite(id); ElMessage.success('已取消'); loadData() }
onMounted(loadData)
</script>
