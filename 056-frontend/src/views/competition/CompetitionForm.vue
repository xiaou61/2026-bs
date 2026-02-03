<template>
  <div class="page-container">
    <el-card>
      <template #header>{{ route.params.id ? '编辑竞赛' : '新增竞赛' }}</template>
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px" style="max-width: 800px">
        <el-form-item label="竞赛名称" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="竞赛分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择分类">
            <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="封面图">
          <el-upload action="/api/file/upload" :show-file-list="false" :on-success="handleUpload">
            <img v-if="form.cover" :src="form.cover" style="width: 200px; height: 120px; object-fit: cover" />
            <el-button v-else type="primary">上传封面</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="竞赛描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="4" />
        </el-form-item>
        <el-form-item label="参赛要求" prop="requirement">
          <el-input v-model="form.requirement" type="textarea" :rows="4" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker v-model="form.startTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" />
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker v-model="form.endTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" />
        </el-form-item>
        <el-form-item label="提交截止时间" prop="submitDeadline">
          <el-date-picker v-model="form.submitDeadline" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" />
        </el-form-item>
        <el-form-item label="字数限制">
          <el-input-number v-model="form.minWords" :min="0" /> - <el-input-number v-model="form.maxWords" :min="0" /> 字
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">保存</el-button>
          <el-button @click="$router.back()">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getAllCategories, getCompetition, saveCompetition } from '../../api'

const route = useRoute()
const router = useRouter()
const formRef = ref()
const categories = ref([])
const form = reactive({
  id: null, title: '', categoryId: null, cover: '', description: '', requirement: '',
  startTime: '', endTime: '', submitDeadline: '', minWords: 100, maxWords: 5000
})
const rules = {
  title: [{ required: true, message: '请输入竞赛名称', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }]
}

const handleUpload = (res) => {
  form.cover = res.data
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await saveCompetition(form)
  ElMessage.success('保存成功')
  router.push('/competition')
}

onMounted(async () => {
  const res = await getAllCategories()
  categories.value = res.data
  if (route.params.id) {
    const detail = await getCompetition(route.params.id)
    Object.assign(form, detail.data)
  }
})
</script>

<style scoped>
.page-container { padding: 10px; }
</style>
