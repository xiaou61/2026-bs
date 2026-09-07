<template>
  <el-card>
    <template #header>食物库</template>
    <el-input v-model="searchKey" placeholder="搜索食物" style="width: 300px; margin-bottom: 20px">
      <template #prefix><el-icon><Search /></el-icon></template>
    </el-input>
    <el-table :data="foodList" stripe>
      <el-table-column prop="name" label="食物名称" />
      <el-table-column prop="category" label="分类" width="100" />
      <el-table-column prop="calorie" label="热量(kcal/100g)" width="140" />
      <el-table-column prop="protein" label="蛋白质(g)" width="100" />
      <el-table-column prop="fat" label="脂肪(g)" width="100" />
      <el-table-column prop="carbohydrate" label="碳水(g)" width="100" />
    </el-table>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'
const searchKey = ref('')
const foodList = ref([
  { name: '米饭', category: '主食', calorie: 116, protein: 2.6, fat: 0.3, carbohydrate: 25.9 },
  { name: '鸡胸肉', category: '肉类', calorie: 133, protein: 19.4, fat: 5.0, carbohydrate: 2.5 }
])
</script>
