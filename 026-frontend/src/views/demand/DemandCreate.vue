<template>
  <el-card>
    <template #header>
      <span>发布需求</span>
    </template>
    <el-form :model="form" label-width="100px">
      <el-form-item label="标题">
        <el-input v-model="form.title" />
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="form.description" type="textarea" :rows="4" />
      </el-form-item>
      <el-form-item label="类型">
        <el-input v-model="form.type" placeholder="如:插画、漫画、设计" />
      </el-form-item>
      <el-form-item label="尺寸规格">
        <el-input v-model="form.size" placeholder="如:1920x1080" />
      </el-form-item>
      <el-form-item label="风格要求">
        <el-input v-model="form.style" />
      </el-form-item>
      <el-form-item label="预算范围">
        <el-input-number v-model="form.budgetMin" :min="0" style="width: 150px" />
        <span style="margin: 0 10px">-</span>
        <el-input-number v-model="form.budgetMax" :min="0" style="width: 150px" />
      </el-form-item>
      <el-form-item label="截止日期">
        <el-date-picker v-model="form.deadline" type="date" value-format="YYYY-MM-DD" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitDemand">发布</el-button>
        <el-button @click="$router.back()">取消</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import request from '../../utils/request'
import { ElMessage } from 'element-plus'

const router = useRouter()
const user = JSON.parse(localStorage.getItem('user') || '{}')

const form = reactive({
  userId: user.id,
  title: '',
  description: '',
  type: '',
  size: '',
  style: '',
  budgetMin: 100,
  budgetMax: 1000,
  deadline: ''
})

const submitDemand = async () => {
  try {
    await request.post('/demand/create', form)
    ElMessage.success('需求发布成功')
    router.push('/demand')
  } catch (error) {
    console.error(error)
  }
}
</script>
