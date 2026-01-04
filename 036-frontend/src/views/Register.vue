&lt;template&gt;
  &lt;div class="register-page"&gt;
    &lt;el-card class="register-card"&gt;
      &lt;h2&gt;注册&lt;/h2&gt;
      &lt;el-form :model="form" :rules="rules" ref="formRef"&gt;
        &lt;el-form-item prop="username"&gt;
          &lt;el-input v-model="form.username" placeholder="用户名" /&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item prop="password"&gt;
          &lt;el-input v-model="form.password" type="password" placeholder="密码" /&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item prop="email"&gt;
          &lt;el-input v-model="form.email" placeholder="邮箱" /&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item prop="phone"&gt;
          &lt;el-input v-model="form.phone" placeholder="手机号" /&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item&gt;
          &lt;el-button type="primary" @click="handleRegister" style="width: 100%"&gt;注册&lt;/el-button&gt;
        &lt;/el-form-item&gt;
        &lt;div class="links"&gt;
          &lt;router-link to="/login"&gt;已有账号？立即登录&lt;/router-link&gt;
        &lt;/div&gt;
      &lt;/el-form&gt;
    &lt;/el-card&gt;
  &lt;/div&gt;
&lt;/template&gt;

&lt;script setup&gt;
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { register } from '@/api/user'
import { useUserStore } from '@/store/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const form = ref({
  username: '',
  password: '',
  email: '',
  phone: ''
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 50, message: '用户名长度在3-50之间', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码至少6位', trigger: 'blur' }
  ],
  email: [{ type: 'email', message: '请输入正确的邮箱', trigger: 'blur' }]
}

const handleRegister = async () =&gt; {
  await formRef.value.validate()
  try {
    const res = await register(form.value)
    userStore.setToken(res.data.token)
    userStore.setUserInfo(res.data.user)
    ElMessage.success('注册成功')
    router.push('/')
  } catch (error) {
    console.error(error)
  }
}
&lt;/script&gt;

&lt;style scoped&gt;
.register-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.register-card {
  width: 400px;
  padding: 20px;
}

h2 {
  text-align: center;
  margin-bottom: 30px;
}

.links {
  text-align: center;
  margin-top: 10px;
}

.links a {
  color: #409EFF;
  text-decoration: none;
}
&lt;/style&gt;
