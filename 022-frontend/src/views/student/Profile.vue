<template>
  <div class="profile">
    <h2>个人中心</h2>
    <div class="profile-content">
      <el-card>
        <template #header>
          <div class="card-header">
            <span>个人信息</span>
          </div>
        </template>
        <el-descriptions :column="2" border>
          <el-descriptions-item label="学号">
            {{ userStore.userInfo?.username }}
          </el-descriptions-item>
          <el-descriptions-item label="姓名">
            {{ userStore.userInfo?.realName }}
          </el-descriptions-item>
          <el-descriptions-item label="院系">
            {{ userStore.userInfo?.department }}
          </el-descriptions-item>
          <el-descriptions-item label="年级">
            {{ userStore.userInfo?.grade }}
          </el-descriptions-item>
          <el-descriptions-item label="手机号">
            {{ userStore.userInfo?.phone }}
          </el-descriptions-item>
          <el-descriptions-item label="信用分">
            {{ userStore.userInfo?.creditScore }}
          </el-descriptions-item>
        </el-descriptions>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
</script>

<style lang="scss" scoped>
.profile {
  .profile-content {
    margin-top: 20px;
  }
}
</style>