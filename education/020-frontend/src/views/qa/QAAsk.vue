<template>
  <div class="ask-container">
    <el-card>
      <template #header>
        <h3>提问</h3>
      </template>

      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="问题标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入问题标题" />
        </el-form-item>

        <el-form-item label="问题描述" prop="content">
          <el-input
            v-model="form.content"
            type="textarea"
            :rows="8"
            placeholder="详细描述你的问题..."
          />
        </el-form-item>

        <el-form-item label="问题分类" prop="category">
          <el-select v-model="form.category" placeholder="请选择分类">
            <el-option label="编程" value="编程" />
            <el-option label="数学" value="数学" />
            <el-option label="算法" value="算法" />
            <el-option label="数据库" value="数据库" />
          </el-select>
        </el-form-item>

        <el-form-item label="悬赏积分" prop="bounty">
          <el-input-number v-model="form.bounty" :min="0" :max="500" />
          <span style="margin-left: 10px; color: #909399">
            设置悬赏可以获得更多优质回答
          </span>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">提交</el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { askQuestion } from '@/api/qa'
import { ElMessage } from 'element-plus'

const router = useRouter()
const formRef = ref(null)
const loading = ref(false)

const form = ref({
  title: '',
  content: '',
  category: '',
  bounty: 0
})

const rules = {
  title: [{ required: true, message: '请输入问题标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入问题描述', trigger: 'blur' }],
  category: [{ required: true, message: '请选择分类', trigger: 'change' }]
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        await askQuestion(form.value)
        ElMessage.success('提问成功')
        router.push('/qa')
      } catch (error) {
        console.error(error)
      } finally {
        loading.value = false
      }
    }
  })
}

const handleCancel = () => {
  router.back()
}
</script>

<style scoped>
.ask-container {
  max-width: 1000px;
  margin: 0 auto;
}
</style>

