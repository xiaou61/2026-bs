<template>
  <div class="publish-page">
    <el-card>
      <template #header><span class="card-title">发布民歌</span></template>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入民歌标题" />
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择分类" style="width: 100%;">
            <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="地区">
          <el-input v-model="form.region" placeholder="如：云南、四川" />
        </el-form-item>
        <el-form-item label="民族">
          <el-input v-model="form.ethnic" placeholder="如：苗族、藏族" />
        </el-form-item>
        <el-form-item label="封面图片">
          <el-upload action="#" :auto-upload="false" :show-file-list="false" :on-change="handleCoverChange">
            <img v-if="form.coverImage" :src="form.coverImage" class="cover-preview" />
            <el-button v-else type="primary">上传封面</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="音频文件">
          <el-upload action="#" :auto-upload="false" :show-file-list="false" :on-change="handleAudioChange" accept="audio/*">
            <el-button type="primary">{{ form.audioUrl ? '已上传' : '上传音频' }}</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="视频文件">
          <el-upload action="#" :auto-upload="false" :show-file-list="false" :on-change="handleVideoChange" accept="video/*">
            <el-button type="primary">{{ form.videoUrl ? '已上传' : '上传视频' }}</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="歌词">
          <el-input v-model="form.lyrics" type="textarea" :rows="6" placeholder="请输入歌词内容" />
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="form.introduction" type="textarea" :rows="3" placeholder="简要介绍这首民歌" />
        </el-form-item>
        <el-form-item label="详细内容">
          <el-input v-model="form.content" type="textarea" :rows="6" placeholder="可以介绍民歌的背景、历史等" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">发布</el-button>
          <el-button @click="router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { categoryApi, songApi, fileApi } from '@/api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const formRef = ref()
const loading = ref(false)
const categories = ref([])

const form = reactive({
  title: '',
  categoryId: null,
  region: '',
  ethnic: '',
  coverImage: '',
  audioUrl: '',
  videoUrl: '',
  lyrics: '',
  introduction: '',
  content: ''
})

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }]
}

onMounted(async () => {
  const res = await categoryApi.getAll()
  categories.value = res.data
})

const handleCoverChange = async (file) => {
  const res = await fileApi.uploadImage(file.raw)
  form.coverImage = res.data
}

const handleAudioChange = async (file) => {
  const res = await fileApi.uploadAudio(file.raw)
  form.audioUrl = res.data
}

const handleVideoChange = async (file) => {
  const res = await fileApi.uploadVideo(file.raw)
  form.videoUrl = res.data
}

const handleSubmit = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    await songApi.create(form)
    ElMessage.success('发布成功，等待审核')
    router.push('/my-songs')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
.publish-page {
  max-width: 800px;
  margin: 0 auto;
}

.cover-preview {
  width: 200px;
  height: 150px;
  object-fit: cover;
  border-radius: 4px;
}
</style>
