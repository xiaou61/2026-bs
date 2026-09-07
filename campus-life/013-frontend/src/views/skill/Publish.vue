<template>
  <div class="skill-publish">
    <el-card>
      <template #header>
        <span>发布技能服务</span>
      </template>

      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="服务标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入服务标题" />
        </el-form-item>

        <el-form-item label="服务分类" prop="category">
          <el-select v-model="form.category" placeholder="请选择分类">
            <el-option label="学业辅导" value="学业辅导" />
            <el-option label="设计服务" value="设计服务" />
            <el-option label="摄影服务" value="摄影服务" />
            <el-option label="音乐教学" value="音乐教学" />
            <el-option label="健身指导" value="健身指导" />
            <el-option label="代做服务" value="代做服务" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>

        <el-form-item label="服务描述" prop="description">
          <el-input type="textarea" v-model="form.description" :rows="4" placeholder="请详细描述您的服务内容" />
        </el-form-item>

        <el-form-item label="服务时长" prop="serviceDuration">
          <el-input-number v-model="form.serviceDuration" :min="30" :step="30" />
          <span style="margin-left: 10px;">分钟</span>
        </el-form-item>

        <el-form-item label="服务价格" prop="hourlyPrice">
          <el-input-number v-model="form.hourlyPrice" :min="0" :precision="2" />
          <span style="margin-left: 10px;">元/小时</span>
        </el-form-item>

        <el-form-item label="服务地点类型" prop="locationType">
          <el-radio-group v-model="form.locationType">
            <el-radio :label="0">上门服务</el-radio>
            <el-radio :label="1">指定地点</el-radio>
            <el-radio :label="2">线上服务</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="服务地点" prop="serviceLocation" v-if="form.locationType !== 2">
          <el-input v-model="form.serviceLocation" placeholder="请输入服务地点" />
        </el-form-item>

        <el-form-item label="个人简介" prop="introduction">
          <el-input type="textarea" v-model="form.introduction" :rows="3" placeholder="请介绍您的专业背景、经验等" />
        </el-form-item>

        <el-form-item label="展示图片">
          <el-input v-model="form.images" placeholder='请输入图片URL，多个用英文逗号分隔' />
        </el-form-item>

        <el-form-item label="作品集">
          <el-input v-model="form.portfolio" placeholder='请输入作品图片URL，多个用英文逗号分隔' />
        </el-form-item>

        <el-form-item label="资质证明">
          <el-input v-model="form.certificate" placeholder='如有相关证书，请输入图片URL' />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handlePublish">发布</el-button>
          <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { publishSkill } from '@/api/skill'
import { ElMessage } from 'element-plus'

const router = useRouter()

const formRef = ref()
const loading = ref(false)

const form = reactive({
  title: '',
  category: '',
  description: '',
  serviceDuration: 60,
  hourlyPrice: null,
  locationType: 2,
  serviceLocation: '',
  introduction: '',
  images: '',
  portfolio: '',
  certificate: ''
})

const rules = {
  title: [{ required: true, message: '请输入服务标题', trigger: 'blur' }],
  category: [{ required: true, message: '请选择分类', trigger: 'change' }],
  description: [{ required: true, message: '请输入服务描述', trigger: 'blur' }],
  hourlyPrice: [{ required: true, message: '请输入服务价格', trigger: 'blur' }],
  introduction: [{ required: true, message: '请输入个人简介', trigger: 'blur' }]
}

const handlePublish = async () => {
  await formRef.value.validate()
  
  loading.value = true
  try {
    const data = {
      ...form,
      images: form.images ? JSON.stringify(form.images.split(',').map(url => url.trim())) : '[]',
      portfolio: form.portfolio ? JSON.stringify(form.portfolio.split(',').map(url => url.trim())) : '[]',
      certificate: form.certificate ? JSON.stringify(form.certificate.split(',').map(url => url.trim())) : '[]'
    }
    await publishSkill(data)
    ElMessage.success('发布成功')
    router.push('/my-publish')
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.skill-publish {
  max-width: 900px;
  margin: 0 auto;
}
</style>


