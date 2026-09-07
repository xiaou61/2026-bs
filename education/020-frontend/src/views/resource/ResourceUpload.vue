<template>
  <div class="upload-container">
    <el-card>
      <template #header>
        <h3>上传学习资源</h3>
      </template>

      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="资源标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入资源标题" />
        </el-form-item>

        <el-form-item label="资源描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="4"
            placeholder="请输入资源描述"
          />
        </el-form-item>

        <el-form-item label="资源分类" prop="category">
          <el-select v-model="form.category" placeholder="请选择分类">
            <el-option label="编程" value="编程" />
            <el-option label="数学" value="数学" />
            <el-option label="算法" value="算法" />
            <el-option label="数据库" value="数据库" />
          </el-select>
        </el-form-item>

        <el-form-item label="所需积分" prop="points">
          <el-input-number v-model="form.points" :min="0" :max="100" />
          <span style="margin-left: 10px; color: #909399">设置为0表示免费</span>
        </el-form-item>

        <el-form-item label="文件链接" prop="fileUrl">
          <el-input v-model="form.fileUrl" placeholder="请输入文件下载链接" />
        </el-form-item>

        <el-form-item label="文件名" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入文件名" />
        </el-form-item>

        <el-form-item label="文件大小" prop="fileSize">
          <el-input-number v-model="form.fileSize" :min="0" />
          <span style="margin-left: 10px; color: #909399">单位：字节</span>
        </el-form-item>

        <el-form-item label="文件类型" prop="fileType">
          <el-select v-model="form.fileType" placeholder="请选择文件类型">
            <el-option label="PDF" value="pdf" />
            <el-option label="Word" value="doc" />
            <el-option label="PPT" value="ppt" />
            <el-option label="Excel" value="xls" />
            <el-option label="压缩包" value="zip" />
          </el-select>
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
import { uploadResource } from '@/api/resource'
import { ElMessage } from 'element-plus'

const router = useRouter()
const formRef = ref(null)
const loading = ref(false)

const form = ref({
  title: '',
  description: '',
  category: '',
  points: 0,
  fileUrl: '',
  fileName: '',
  fileSize: 0,
  fileType: ''
})

const rules = {
  title: [{ required: true, message: '请输入资源标题', trigger: 'blur' }],
  description: [{ required: true, message: '请输入资源描述', trigger: 'blur' }],
  category: [{ required: true, message: '请选择分类', trigger: 'change' }],
  fileUrl: [{ required: true, message: '请输入文件链接', trigger: 'blur' }],
  fileName: [{ required: true, message: '请输入文件名', trigger: 'blur' }],
  fileType: [{ required: true, message: '请选择文件类型', trigger: 'change' }]
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        await uploadResource(form.value)
        ElMessage.success('上传成功')
        router.push('/resource')
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
.upload-container {
  max-width: 800px;
  margin: 0 auto;
}
</style>

