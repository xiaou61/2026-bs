<template>
  <el-container class="layout-container">
    <el-aside width="220px">
      <div class="logo">招生管理系统</div>
      <el-menu :default-active="route.path" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF">
        <el-menu-item index="/dashboard"><el-icon><DataAnalysis /></el-icon>首页</el-menu-item>
        <el-sub-menu index="system">
          <template #title><el-icon><Setting /></el-icon>系统管理</template>
          <el-menu-item index="/admin">管理员管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="base">
          <template #title><el-icon><School /></el-icon>基础数据</template>
          <el-menu-item index="/department">院系管理</el-menu-item>
          <el-menu-item index="/major">专业管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="enrollment">
          <template #title><el-icon><Calendar /></el-icon>招生管理</template>
          <el-menu-item index="/plan">招生计划</el-menu-item>
          <el-menu-item index="/scoreline">分数线管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="student">
          <template #title><el-icon><User /></el-icon>考生管理</template>
          <el-menu-item index="/student">考生信息</el-menu-item>
          <el-menu-item index="/application">报名管理</el-menu-item>
          <el-menu-item index="/score">成绩管理</el-menu-item>
          <el-menu-item index="/admission">录取管理</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/notice"><el-icon><Bell /></el-icon>通知公告</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-right">
          <span>欢迎，{{ userStore.admin?.name }}</span>
          <el-dropdown @command="handleCommand">
            <el-avatar :size="36" icon="UserFilled" />
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="password">修改密码</el-dropdown-item>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
    <el-dialog v-model="pwdVisible" title="修改密码" width="400px">
      <el-form :model="pwdForm" :rules="pwdRules" ref="pwdFormRef" label-width="80px">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="pwdForm.oldPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="pwdForm.newPassword" type="password" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="pwdVisible = false">取消</el-button>
        <el-button type="primary" @click="handleUpdatePwd">确定</el-button>
      </template>
    </el-dialog>
  </el-container>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { logout, updatePassword } from '../api'
import { useUserStore } from '../store/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const pwdVisible = ref(false)
const pwdFormRef = ref()
const pwdForm = reactive({ oldPassword: '', newPassword: '' })
const pwdRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }]
}

const handleCommand = (cmd) => {
  if (cmd === 'logout') {
    logout().finally(() => {
      userStore.clearUser()
      router.push('/login')
    })
  } else if (cmd === 'password') {
    pwdForm.oldPassword = ''
    pwdForm.newPassword = ''
    pwdVisible.value = true
  }
}

const handleUpdatePwd = async () => {
  await pwdFormRef.value.validate()
  await updatePassword(pwdForm)
  ElMessage.success('密码修改成功')
  pwdVisible.value = false
}
</script>

<style scoped>
.layout-container { height: 100%; }
.el-aside { background: #304156; }
.logo { height: 60px; line-height: 60px; text-align: center; color: #fff; font-size: 18px; font-weight: bold; }
.el-header { display: flex; justify-content: flex-end; align-items: center; background: #fff; box-shadow: 0 1px 4px rgba(0,0,0,0.1); }
.header-right { display: flex; align-items: center; gap: 15px; }
.el-main { background: #f0f2f5; }
</style>
