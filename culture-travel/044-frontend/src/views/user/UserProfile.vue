<template>
  <div class="user-profile container">
    <el-card>
      <template #header>
        <h2>个人中心</h2>
      </template>
      <div class="profile-content">
        <el-avatar :size="100" :src="userStore.userInfo?.avatar || ''">
          {{ userStore.userInfo?.nickname?.charAt(0) }}
        </el-avatar>
        <div class="info">
          <p><strong>用户名：</strong>{{ userStore.userInfo?.username }}</p>
          <p><strong>昵称：</strong>{{ userStore.userInfo?.nickname }}</p>
          <p><strong>角色：</strong>{{ roleText }}</p>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

const roleText = computed(() => {
  const role = userStore.userInfo?.role
  if (role === 0) return '游客'
  if (role === 1) return '房东'
  if (role === 2) return '管理员'
  return '未知'
})
</script>

<style scoped lang="scss">
.user-profile {
  padding-top: 20px;
  h2 { margin: 0; }
  .profile-content {
    display: flex;
    align-items: center;
    gap: 40px;
    padding: 20px;
  }
  .info p {
    margin: 12px 0;
    font-size: 16px;
  }
}
</style>
