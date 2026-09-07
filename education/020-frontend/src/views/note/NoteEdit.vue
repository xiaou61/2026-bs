<template>
  <div class="note-edit-container">
    <el-card>
      <template #header>
        <h3>{{ isEdit ? '编辑笔记' : '创建笔记' }}</h3>
      </template>

      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="笔记标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入笔记标题" />
        </el-form-item>

        <el-form-item label="笔记内容" prop="content">
          <el-input
            v-model="form.content"
            type="textarea"
            :rows="15"
            placeholder="支持Markdown格式，记录你的学习笔记..."
          />
        </el-form-item>

        <el-form-item label="笔记分类" prop="category">
          <el-select v-model="form.category" placeholder="请选择分类">
            <el-option label="编程" value="编程" />
            <el-option label="数学" value="数学" />
            <el-option label="算法" value="算法" />
          </el-select>
        </el-form-item>

        <el-form-item label="标签" prop="tags">
          <el-input v-model="form.tags" placeholder="多个标签用逗号分隔" />
        </el-form-item>

        <el-form-item label="是否公开" prop="isPublic">
          <el-switch v-model="form.isPublic" :active-value="1" :inactive-value="0" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">保存</el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { createNote, updateNote, getNoteDetail } from '@/api/note'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const formRef = ref(null)
const loading = ref(false)
const isEdit = ref(false)

const form = ref({
  id: null,
  title: '',
  content: '',
  category: '',
  tags: '',
  isPublic: 0
})

const rules = {
  title: [{ required: true, message: '请输入笔记标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入笔记内容', trigger: 'blur' }],
  category: [{ required: true, message: '请选择分类', trigger: 'change' }]
}

const loadData = async () => {
  if (route.query.id) {
    isEdit.value = true
    try {
      const res = await getNoteDetail(route.query.id)
      form.value = res.data
    } catch (error) {
      console.error(error)
    }
  }
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        if (isEdit.value) {
          await updateNote(form.value)
          ElMessage.success('更新成功')
        } else {
          await createNote(form.value)
          ElMessage.success('创建成功')
        }
        router.push('/note')
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

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.note-edit-container {
  max-width: 1000px;
  margin: 0 auto;
}
</style>

