<template>
  <el-container class="layout-container">
    <el-aside width="220px" class="aside">
      <div class="logo">OA管理系统</div>
      <el-menu :default-active="route.path" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF">
        <el-menu-item index="/dashboard">
          <el-icon><HomeFilled /></el-icon>
          <span>首页</span>
        </el-menu-item>
        <el-sub-menu index="org" v-if="userStore.isAdmin">
          <template #title>
            <el-icon><OfficeBuilding /></el-icon>
            <span>组织管理</span>
          </template>
          <el-menu-item index="/user">用户管理</el-menu-item>
          <el-menu-item index="/department">部门管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="attendance">
          <template #title>
            <el-icon><Clock /></el-icon>
            <span>考勤管理</span>
          </template>
          <el-menu-item index="/attendance">考勤记录</el-menu-item>
          <el-menu-item index="/leave">请假管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="meeting">
          <template #title>
            <el-icon><Calendar /></el-icon>
            <span>会议管理</span>
          </template>
          <el-menu-item index="/meeting-room" v-if="userStore.isAdmin">会议室管理</el-menu-item>
          <el-menu-item index="/meeting">会议预约</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/notice">
          <el-icon><Bell /></el-icon>
          <span>公告通知</span>
        </el-menu-item>
        <el-menu-item index="/schedule">
          <el-icon><Calendar /></el-icon>
          <span>日程安排</span>
        </el-menu-item>
        <el-menu-item index="/document">
          <el-icon><Folder /></el-icon>
          <span>文档管理</span>
        </el-menu-item>
        <el-menu-item index="/salary">
          <el-icon><Money /></el-icon>
          <span>薪资管理</span>
        </el-menu-item>
        <el-menu-item index="/work-log">
          <el-icon><Document /></el-icon>
          <span>工作日志</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-right">
          <span class="username">{{ userStore.userInfo.realName }}</span>
          <el-dropdown @command="handleCommand">
            <el-avatar :size="36" :src="userStore.userInfo.avatar">
              <el-icon><UserFilled /></el-icon>
            </el-avatar>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人中心</el-dropdown-item>
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
  </el-container>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { useUserStore } from '../store/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const handleCommand = (command) => {
  if (command === 'profile') {
    router.push('/profile')
  } else if (command === 'logout') {
    ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      type: 'warning'
    }).then(() => {
      userStore.logout()
      router.push('/login')
    })
  }
}
</script>

<style scoped>
.layout-container {
  height: 100%;
}
.aside {
  background-color: #304156;
}
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 18px;
  font-weight: bold;
  color: #fff;
  background-color: #263445;
}
.header {
  background: #fff;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
}
.header-right {
  display: flex;
  align-items: center;
  gap: 15px;
}
.username {
  color: #333;
}
.main {
  background: #f0f2f5;
}
</style>
