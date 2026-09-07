<template>
  <div class="note-edit">
    <el-card>
      <template #header><span>{{ isEdit ? '编辑游记' : '发布游记' }}</span></template>
      <el-form :model="form" label-width="100px" :rules="rules" ref="formRef">
        <el-form-item label="游记标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入游记标题" maxlength="50" show-word-limit />
        </el-form-item>
        <el-form-item label="游记摘要" prop="summary">
          <el-input v-model="form.summary" type="textarea" :rows="2" placeholder="请输入游记摘要" maxlength="200" show-word-limit />
        </el-form-item>
        <el-form-item label="封面图片" prop="coverImage">
          <el-input v-model="form.coverImage" placeholder="请输入封面图片URL" />
        </el-form-item>
        <el-form-item label="游记内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="15" placeholder="请输入游记内容，支持多段落" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">{{ isEdit ? '保存修改' : '发布游记' }}</el-button>
          <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getNoteDetail, createNote, updateNote } from '../api'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const formRef = ref()
const form = ref({ title: '', summary: '', coverImage: '', content: '' })
const isEdit = computed(() => !!route.params.id)

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  summary: [{ required: true, message: '请输入摘要', trigger: 'blur' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
}

onMounted(async () => {
  if (route.params.id) {
    const res = await getNoteDetail(route.params.id)
    form.value = res.data
  }
})

const submitForm = async () => {
  await formRef.value.validate()
  if (isEdit.value) {
    await updateNote(route.params.id, form.value)
    ElMessage.success('修改成功')
  } else {
    await createNote(form.value)
    ElMessage.success('发布成功')
  }
  router.push('/notes')
}
</script>

<style scoped>
.note-edit { max-width: 900px; margin: 0 auto; }
</style>
