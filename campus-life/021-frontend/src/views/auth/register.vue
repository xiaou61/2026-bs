<template>
  <div class="auth-page">
    <el-card class="form-card" shadow="never">
      <template #header>
        <div class="card-header">
          <div>
            <span class="badge">New Account</span>
            <h2>创建你的校园交易账号</h2>
            <p>注册信息会直接用于校内信用和交易身份识别。</p>
          </div>
        </div>
      </template>

      <el-form ref="formRef" :model="form" :rules="rules" label-position="top" @submit.prevent>
        <div class="grid two-col">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" placeholder="如：xiaoming" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" type="password" show-password placeholder="至少 6 位" />
          </el-form-item>
        </div>

        <div class="grid two-col">
          <el-form-item label="学号" prop="studentId">
            <el-input v-model="form.studentId" placeholder="请输入学号" />
          </el-form-item>
          <el-form-item label="真实姓名" prop="realName">
            <el-input v-model="form.realName" placeholder="请输入真实姓名" />
          </el-form-item>
        </div>

        <div class="grid two-col">
          <el-form-item label="学院" prop="college">
            <el-input v-model="form.college" placeholder="如：计算机学院" />
          </el-form-item>
          <el-form-item label="宿舍" prop="dorm">
            <el-input v-model="form.dorm" placeholder="如：3-402" />
          </el-form-item>
        </div>

        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>

        <el-button type="primary" :loading="submitting" class="submit-btn" @click="handleRegister">
          完成注册
        </el-button>
      </el-form>

      <div class="footer">
        <span>已经有账号？</span>
        <el-link type="primary" @click="router.push('/login')">返回登录</el-link>
      </div>
    </el-card>

    <section class="aside">
      <h1>把闲置流转给更需要的同学</h1>
      <p>
        账号注册完成后，你就可以发布商品、发起收藏、与卖家沟通，并在交易完成后建立自己的信用记录。
      </p>
      <div class="tips">
        <div>
          <strong>实名认证</strong>
          <span>用学号和真实姓名建立可信身份</span>
        </div>
        <div>
          <strong>信用体系</strong>
          <span>双方互评后自动增减信用分</span>
        </div>
        <div>
          <strong>站内沟通</strong>
          <span>聊天与议价全都围绕商品会话展开</span>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const submitting = ref(false)

const form = reactive({
  username: '',
  password: '',
  studentId: '',
  realName: '',
  college: '',
  dorm: '',
  phone: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码至少 6 位', trigger: 'blur' }
  ],
  studentId: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  realName: [{ required: true, message: '请输入真实姓名', trigger: 'blur' }],
  college: [{ required: true, message: '请输入学院', trigger: 'blur' }],
  dorm: [{ required: true, message: '请输入宿舍号', trigger: 'blur' }],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ]
}

const handleRegister = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) {
    return
  }

  submitting.value = true
  try {
    await userStore.register(form)
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: grid;
  grid-template-columns: minmax(460px, 560px) minmax(0, 1fr);
  gap: 40px;
  align-items: center;
  padding: 40px 48px;
  background:
    radial-gradient(circle at top left, rgba(255, 202, 125, 0.24), transparent 28%),
    radial-gradient(circle at bottom right, rgba(125, 211, 252, 0.22), transparent 26%),
    #f8fafc;
}

.form-card {
  border-radius: 30px;
  border: 1px solid rgba(148, 163, 184, 0.16);
  background: rgba(255, 255, 255, 0.94);
}

.badge {
  display: inline-block;
  margin-bottom: 12px;
  color: #b45309;
  background: rgba(251, 191, 36, 0.18);
  padding: 6px 12px;
  border-radius: 999px;
  font-size: 13px;
}

.card-header h2 {
  margin-bottom: 8px;
  font-size: 30px;
}

.card-header p {
  color: #64748b;
}

.grid {
  display: grid;
  gap: 18px;
}

.two-col {
  grid-template-columns: repeat(2, minmax(0, 1fr));
}

.submit-btn {
  width: 100%;
  height: 48px;
  margin-top: 10px;
}

.footer {
  margin-top: 24px;
  display: flex;
  justify-content: center;
  gap: 6px;
  color: #64748b;
}

.aside h1 {
  font-size: 44px;
  line-height: 1.15;
  margin-bottom: 16px;
}

.aside p {
  color: #475569;
  line-height: 1.8;
  max-width: 560px;
}

.tips {
  margin-top: 28px;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 16px;
}

.tips div {
  padding: 18px;
  border-radius: 22px;
  background: rgba(255, 255, 255, 0.75);
  border: 1px solid rgba(148, 163, 184, 0.14);
}

.tips strong {
  display: block;
  margin-bottom: 8px;
}

.tips span {
  color: #64748b;
  line-height: 1.7;
  font-size: 14px;
}

@media (max-width: 1080px) {
  .auth-page {
    grid-template-columns: 1fr;
    padding: 24px;
  }

  .aside h1 {
    font-size: 34px;
  }

  .tips {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 720px) {
  .two-col {
    grid-template-columns: 1fr;
  }
}
</style>
