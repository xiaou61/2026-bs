<template>
  <div class="profile">
    <el-card>
      <template #header>
        <span>个人中心</span>
      </template>
      <el-descriptions :column="1" border>
        <el-descriptions-item label="用户名">{{ userStore.user.username }}</el-descriptions-item>
        <el-descriptions-item label="昵称">{{ userStore.user.nickname }}</el-descriptions-item>
        <el-descriptions-item label="角色">
          <el-tag>{{ roleText(userStore.user.role) }}</el-tag>
        </el-descriptions-item>
      </el-descriptions>
      <div style="margin-top: 20px">
        <el-button type="danger" @click="handleLogout">退出登录</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const roleText = (role: string) => {
  const map: Record<string, string> = {
    ADMIN: '管理员',
    PROVIDER: '服务商',
    USER: '普通用户'
  }
  return map[role] || '普通用户'
}

const handleLogout = () => {
  userStore.logout()
  ElMessage.success('已退出登录')
  router.push('/login')
}
</script>

<style scoped>
.profile {
  padding: 20px;
  max-width: 600px;
}
</style>
