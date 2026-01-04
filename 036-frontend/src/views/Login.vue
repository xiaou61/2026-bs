&lt;template&gt;
  &lt;div class="login-page"&gt;
    &lt;el-card class="login-card"&gt;
      &lt;h2&gt;登录&lt;/h2&gt;
      &lt;el-form :model="form" :rules="rules" ref="formRef"&gt;
        &lt;el-form-item prop="username"&gt;
          &lt;el-input v-model="form.username" placeholder="用户名" /&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item prop="password"&gt;
          &lt;el-input v-model="form.password" type="password" placeholder="密码" /&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item&gt;
          &lt;el-button type="primary" @click="handleLogin" style="width: 100%"&gt;登录&lt;/el-button&gt;
        &lt;/el-form-item&gt;
        &lt;div class="links"&gt;
          &lt;router-link to="/register"&gt;还没有账号？立即注册&lt;/router-link&gt;
        &lt;/div&gt;
      &lt;/el-form&gt;
    &lt;/el-card&gt;
  &lt;/div&gt;
&lt;/template&gt;

&lt;script setup&gt;
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '@/api/user'
import { useUserStore } from '@/store/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const form = ref({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () =&gt; {
  await formRef.value.validate()
  try {
    const res = await login(form.value)
    userStore.setToken(res.data.token)
    userStore.setUserInfo(res.data.user)
    ElMessage.success('登录成功')
    router.push('/')
  } catch (error) {
    console.error(error)
  }
}
&lt;/script&gt;

&lt;style scoped&gt;
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
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
