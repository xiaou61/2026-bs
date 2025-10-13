<template>
  <div class="publish-container">
    <el-card class="publish-card">
      <h2>发布视频</h2>
      
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="视频文件" prop="videoUrl">
          <el-upload
            class="video-uploader"
            :show-file-list="false"
            :before-upload="beforeVideoUpload"
            :http-request="handleVideoUpload"
            accept="video/*"
          >
            <video v-if="form.videoUrl" :src="form.videoUrl" class="video-preview" controls></video>
            <el-icon v-else class="uploader-icon"><Plus /></el-icon>
          </el-upload>
          <div class="upload-tip">支持MP4、MOV格式，最大100MB</div>
        </el-form-item>
        
        <el-form-item label="封面图片" prop="coverUrl">
          <el-upload
            class="cover-uploader"
            :show-file-list="false"
            :before-upload="beforeCoverUpload"
            :http-request="handleCoverUpload"
            accept="image/*"
          >
            <img v-if="form.coverUrl" :src="form.coverUrl" class="cover-preview">
            <el-icon v-else class="uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        
        <el-form-item label="视频标题" prop="title">
          <el-input
            v-model="form.title"
            placeholder="请输入视频标题"
            maxlength="50"
            show-word-limit
          />
        </el-form-item>
        
        <el-form-item label="视频描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            placeholder="请输入视频描述"
            :rows="4"
            maxlength="500"
            show-word-limit
          />
        </el-form-item>
        
        <el-form-item label="选择话题">
          <el-select
            v-model="form.topicIds"
            multiple
            placeholder="请选择话题（最多3个）"
            style="width: 100%"
          >
            <el-option
              v-for="topic in topics"
              :key="topic.id"
              :label="topic.topicName"
              :value="topic.id"
              :disabled="form.topicIds.length >= 3 && !form.topicIds.includes(topic.id)"
            />
          </el-select>
        </el-form-item>
        
        <el-form-item label="观看权限">
          <el-radio-group v-model="form.permission">
            <el-radio :label="1">公开</el-radio>
            <el-radio :label="2">仅粉丝可见</el-radio>
            <el-radio :label="0">私密</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" :loading="publishing" @click="handlePublish">
            发布视频
          </el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { uploadVideo, uploadCover, publishVideo } from '@/api/video'
import { getHotTopics } from '@/api/topic'

const router = useRouter()
const formRef = ref()
const publishing = ref(false)
const topics = ref([])

const form = ref({
  videoUrl: '',
  coverUrl: '',
  title: '',
  description: '',
  topicIds: [],
  permission: 1
})

const rules = {
  videoUrl: [{ required: true, message: '请上传视频', trigger: 'change' }],
  coverUrl: [{ required: true, message: '请上传封面', trigger: 'change' }],
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }]
}

const beforeVideoUpload = (file) => {
  const isVideo = file.type.startsWith('video/')
  const isLt100M = file.size / 1024 / 1024 < 100
  
  if (!isVideo) {
    ElMessage.error('只能上传视频文件')
    return false
  }
  if (!isLt100M) {
    ElMessage.error('视频大小不能超过100MB')
    return false
  }
  return true
}

const handleVideoUpload = async (options) => {
  try {
    const res = await uploadVideo(options.file)
    form.value.videoUrl = res.data
    ElMessage.success('视频上传成功')
  } catch (error) {
    ElMessage.error('视频上传失败')
  }
}

const beforeCoverUpload = (file) => {
  const isImage = file.type.startsWith('image/')
  const isLt2M = file.size / 1024 / 1024 < 2
  
  if (!isImage) {
    ElMessage.error('只能上传图片文件')
    return false
  }
  if (!isLt2M) {
    ElMessage.error('图片大小不能超过2MB')
    return false
  }
  return true
}

const handleCoverUpload = async (options) => {
  try {
    const res = await uploadCover(options.file)
    form.value.coverUrl = res.data
    ElMessage.success('封面上传成功')
  } catch (error) {
    ElMessage.error('封面上传失败')
  }
}

const handlePublish = async () => {
  await formRef.value.validate()
  
  publishing.value = true
  try {
    await publishVideo(form.value)
    ElMessage.success('发布成功')
    router.push('/profile')
  } catch (error) {
    ElMessage.error('发布失败')
  } finally {
    publishing.value = false
  }
}

const handleReset = () => {
  formRef.value.resetFields()
  form.value.videoUrl = ''
  form.value.coverUrl = ''
}

const fetchTopics = async () => {
  try {
    const res = await getHotTopics()
    topics.value = res.data
  } catch (error) {
    console.error('获取话题失败:', error)
  }
}

onMounted(() => {
  fetchTopics()
})
</script>

<style scoped>
.publish-container {
  padding: 20px;
  height: 100%;
  overflow-y: auto;
}

.publish-card {
  max-width: 800px;
  margin: 0 auto;
}

h2 {
  text-align: center;
  margin-bottom: 30px;
}

.video-uploader,
.cover-uploader {
  width: 300px;
  height: 200px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}

.video-uploader:hover,
.cover-uploader:hover {
  border-color: #409eff;
}

.video-preview,
.cover-preview {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.uploader-icon {
  font-size: 50px;
  color: #8c939d;
}

.upload-tip {
  margin-top: 8px;
  color: #999;
  font-size: 12px;
}
</style>

