<template>
  <el-container class="layout-container">
    <el-aside width="220px" class="aside">
      <div class="logo">人事管理系统</div>
      <el-menu :default-active="route.path" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF">
        <el-menu-item index="/dashboard"><el-icon><HomeFilled /></el-icon>首页</el-menu-item>
        <el-sub-menu index="org" v-if="user?.role !== 'employee'">
          <template #title><el-icon><OfficeBuilding /></el-icon>组织管理</template>
          <el-menu-item index="/department">部门管理</el-menu-item>
          <el-menu-item index="/position">职位管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="emp" v-if="user?.role !== 'employee'">
          <template #title><el-icon><User /></el-icon>员工管理</template>
          <el-menu-item index="/employee">员工信息</el-menu-item>
          <el-menu-item index="/contract">合同管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="work">
          <template #title><el-icon><Calendar /></el-icon>考勤管理</template>
          <el-menu-item index="/attendance">考勤记录</el-menu-item>
          <el-menu-item index="/leave">请假管理</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/salary" v-if="user?.role !== 'employee'"><el-icon><Money /></el-icon>薪资管理</el-menu-item>
        <el-sub-menu index="recruit" v-if="user?.role !== 'employee'">
          <template #title><el-icon><Briefcase /></el-icon>招聘管理</template>
          <el-menu-item index="/recruitment">招聘需求</el-menu-item>
          <el-menu-item index="/resume">简历管理</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/training"><el-icon><Reading /></el-icon>培训管理</el-menu-item>
        <el-menu-item index="/announcement"><el-icon><Bell /></el-icon>公告管理</el-menu-item>
        <el-menu-item index="/user" v-if="user?.role === 'admin'"><el-icon><Setting /></el-icon>用户管理</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-right">
          <span>欢迎，{{ user?.name }}</span>
          <el-dropdown @command="handleCommand">
            <el-avatar :size="36" style="cursor: pointer">{{ user?.name?.charAt(0) }}</el-avatar>
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
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { userApi } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const user = computed(() => userStore.user)

const handleCommand = async (command) => {
  if (command === 'profile') {
    router.push('/profile')
  } else if (command === 'logout') {
    await ElMessageBox.confirm('确定退出登录?', '提示')
    await userApi.logout()
    userStore.clearUser()
    ElMessage.success('已退出登录')
    router.push('/login')
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}
.aside {
  background: #304156;
}
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  background: #263445;
}
.header {
  background: #fff;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
}
.header-right {
  display: flex;
  align-items: center;
  gap: 15px;
}
.main {
  background: #f0f2f5;
}
</style>
