<template>
  <div class="auth-container">
    <el-card>
      <template #header>
        <span>实名认证</span>
      </template>

      <div v-if="authStatus?.authStatus === 1" class="status-info">
        <el-result icon="info" title="审核中" sub-title="您的认证申请正在审核中，请耐心等待">
          <template #extra>
            <el-button type="primary" @click="$router.push('/profile')">返回个人中心</el-button>
          </template>
        </el-result>
      </div>

      <div v-else-if="authStatus?.authStatus === 2" class="status-info">
        <el-result icon="success" title="已认证" sub-title="您已完成实名认证">
          <template #extra>
            <el-button type="primary" @click="$router.push('/profile')">返回个人中心</el-button>
          </template>
        </el-result>
      </div>

      <div v-else>
        <el-alert v-if="authStatus?.authStatus === 3" 
                  :title="`认证未通过：${authStatus.rejectReason}`" 
                  type="error" 
                  :closable="false"
                  style="margin-bottom: 20px" />

        <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
          <el-form-item label="真实姓名" prop="realName">
            <el-input v-model="form.realName" placeholder="请输入真实姓名" />
          </el-form-item>

          <el-form-item label="身份证号" prop="idCard">
            <el-input v-model="form.idCard" placeholder="请输入身份证号" />
          </el-form-item>

          <el-form-item label="学生证照片" prop="studentCardImg">
            <el-input v-model="form.studentCardImg" placeholder="请输入学生证图片URL" />
            <small>示例：https://example.com/student-card.jpg</small>
          </el-form-item>

          <el-form-item label="人脸照片" prop="faceImg">
            <el-input v-model="form.faceImg" placeholder="请输入人脸照片URL" />
            <small>示例：https://example.com/face.jpg</small>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" :loading="loading" @click="handleSubmit">提交认证</el-button>
            <el-button @click="$router.push('/profile')">返回</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getAuthStatus, submitAuth } from '@/api/user'
import { ElMessage } from 'element-plus'

const router = useRouter()

const formRef = ref()
const loading = ref(false)
const authStatus = ref(null)

const form = reactive({
  realName: '',
  idCard: '',
  studentCardImg: '',
  faceImg: ''
})

const rules = {
  realName: [{ required: true, message: '请输入真实姓名', trigger: 'blur' }],
  idCard: [
    { required: true, message: '请输入身份证号', trigger: 'blur' },
    { pattern: /^[1-9]\d{5}(18|19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[\dXx]$/, message: '请输入正确的身份证号', trigger: 'blur' }
  ],
  studentCardImg: [{ required: true, message: '请输入学生证照片URL', trigger: 'blur' }],
  faceImg: [{ required: true, message: '请输入人脸照片URL', trigger: 'blur' }]
}

const loadAuthStatus = async () => {
  try {
    const res = await getAuthStatus()
    authStatus.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const handleSubmit = async () => {
  await formRef.value.validate()
  
  loading.value = true
  try {
    await submitAuth(form)
    ElMessage.success('提交成功，请等待审核')
    router.push('/profile')
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadAuthStatus()
})
</script>

<style scoped>
.auth-container {
  max-width: 800px;
  margin: 0 auto;
}

.status-info {
  padding: 40px 0;
}

small {
  color: #909399;
  font-size: 12px;
}
</style>


