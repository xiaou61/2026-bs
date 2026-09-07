<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <el-button @click="$router.back()">返回</el-button>
        校友详情
      </template>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="姓名">{{ info.userName }}</el-descriptions-item>
        <el-descriptions-item label="学号">{{ info.studentNo }}</el-descriptions-item>
        <el-descriptions-item label="届次">{{ info.gradeName }}</el-descriptions-item>
        <el-descriptions-item label="班级">{{ info.className }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ info.gender === 1 ? '男' : '女' }}</el-descriptions-item>
        <el-descriptions-item label="出生日期">{{ info.birthday }}</el-descriptions-item>
        <el-descriptions-item label="籍贯">{{ info.nativePlace }}</el-descriptions-item>
        <el-descriptions-item label="政治面貌">{{ info.political }}</el-descriptions-item>
        <el-descriptions-item label="学历">{{ info.degree }}</el-descriptions-item>
        <el-descriptions-item label="专业">{{ info.major }}</el-descriptions-item>
        <el-descriptions-item label="毕业日期">{{ info.graduationDate }}</el-descriptions-item>
        <el-descriptions-item label="工作单位">{{ info.company }}</el-descriptions-item>
        <el-descriptions-item label="职位">{{ info.position }}</el-descriptions-item>
        <el-descriptions-item label="行业">{{ info.industry }}</el-descriptions-item>
        <el-descriptions-item label="所在城市">{{ info.city }}</el-descriptions-item>
        <el-descriptions-item label="手机">{{ info.userPhone }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ info.userEmail }}</el-descriptions-item>
        <el-descriptions-item label="个人简介" :span="2">{{ info.introduction }}</el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getAlumniById } from '../../api'

const route = useRoute()
const info = ref({})

onMounted(async () => {
  const res = await getAlumniById(route.params.id)
  info.value = res.data
})
</script>

<style scoped>
.page-container { padding: 10px; }
</style>
