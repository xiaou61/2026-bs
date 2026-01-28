<template>
  <el-container class="layout-container">
    <el-aside width="220px" class="aside">
      <div class="logo">
        <span>农业生产技术管理</span>
      </div>
      <el-menu
        :default-active="$route.path"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
      >
        <el-menu-item index="/dashboard">
          <el-icon><HomeFilled /></el-icon>
          <span>首页</span>
        </el-menu-item>
        <el-menu-item index="/user" v-if="userInfo?.role === 'admin'">
          <el-icon><User /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
        <el-sub-menu index="crop-menu">
          <template #title>
            <el-icon><Grape /></el-icon>
            <span>作物管理</span>
          </template>
          <el-menu-item index="/crop">作物列表</el-menu-item>
          <el-menu-item index="/crop/category">作物分类</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/knowledge">
          <el-icon><Collection /></el-icon>
          <span>知识库</span>
        </el-menu-item>
        <el-menu-item index="/plan">
          <el-icon><Calendar /></el-icon>
          <span>生产计划</span>
        </el-menu-item>
        <el-menu-item index="/task">
          <el-icon><List /></el-icon>
          <span>生产任务</span>
        </el-menu-item>
        <el-sub-menu index="pest-menu">
          <template #title>
            <el-icon><Warning /></el-icon>
            <span>病虫害管理</span>
          </template>
          <el-menu-item index="/pest">病虫害库</el-menu-item>
          <el-menu-item index="/pest/warning">预警信息</el-menu-item>
          <el-menu-item index="/pest/treatment">防治记录</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/material">
          <el-icon><Box /></el-icon>
          <span>农资管理</span>
        </el-menu-item>
        <el-menu-item index="/consultation">
          <el-icon><ChatDotRound /></el-icon>
          <span>技术咨询</span>
        </el-menu-item>
        <el-menu-item index="/appointment">
          <el-icon><Timer /></el-icon>
          <span>专家预约</span>
        </el-menu-item>
        <el-menu-item index="/notice" v-if="userInfo?.role === 'admin'">
          <el-icon><Notification /></el-icon>
          <span>公告管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="32">{{ userInfo?.realName?.[0] || 'U' }}</el-avatar>
              <span class="username">{{ userInfo?.realName || '用户' }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="password">修改密码</el-dropdown-item>
                <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>

    <el-dialog v-model="passwordVisible" title="修改密码" width="400px">
      <el-form :model="passwordForm" :rules="passwordRules" ref="passwordRef" label-width="80px">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="passwordForm.oldPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="passwordForm.confirmPassword" type="password" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="passwordVisible = false">取消</el-button>
        <el-button type="primary" @click="submitPassword">确定</el-button>
      </template>
    </el-dialog>
  </el-container>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { updatePassword } from '@/api/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const userInfo = computed(() => userStore.userInfo)

const passwordVisible = ref(false)
const passwordRef = ref()
const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const validateConfirm = (rule, value, callback) => {
  if (value !== passwordForm.value.newPassword) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const passwordRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    { validator: validateConfirm, trigger: 'blur' }
  ]
}

onMounted(() => {
  userStore.fetchUserInfo()
})

const handleCommand = (command) => {
  if (command === 'logout') {
    userStore.logout()
    router.push('/login')
  } else if (command === 'password') {
    passwordVisible.value = true
    passwordForm.value = { oldPassword: '', newPassword: '', confirmPassword: '' }
  }
}

const submitPassword = async () => {
  await passwordRef.value.validate()
  await updatePassword(passwordForm.value)
  ElMessage.success('密码修改成功')
  passwordVisible.value = false
}
</script>

<style scoped lang="scss">
.layout-container {
  height: 100vh;
}

.aside {
  background-color: #304156;
  overflow-y: auto;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  background-color: #263445;
}

.header {
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;

  .username {
    margin-left: 8px;
  }
}

.main {
  background-color: #f0f2f5;
  overflow-y: auto;
}
</style>
