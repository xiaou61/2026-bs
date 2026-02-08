<template>
  <el-container style="height:100vh">
    <el-aside width="220px" style="background:#304156">
      <div style="color:#fff;text-align:center;padding:20px 0;font-size:18px;font-weight:bold">电影订票系统</div>
      <el-menu :default-active="$route.path" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409eff">
        <el-menu-item index="/dashboard"><el-icon><Odometer /></el-icon><span>数据看板</span></el-menu-item>
        <template v-if="userStore.isAdmin()">
          <el-menu-item index="/user"><el-icon><User /></el-icon><span>用户管理</span></el-menu-item>
          <el-sub-menu index="movie-mgr">
            <template #title><el-icon><Film /></el-icon><span>电影管理</span></template>
            <el-menu-item index="/movie/category">电影分类</el-menu-item>
            <el-menu-item index="/movie">电影列表</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="cinema-mgr">
            <template #title><el-icon><OfficeBuilding /></el-icon><span>影院管理</span></template>
            <el-menu-item index="/cinema">影院列表</el-menu-item>
            <el-menu-item index="/hall">影厅管理</el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/showtime"><el-icon><Clock /></el-icon><span>排片管理</span></el-menu-item>
          <el-menu-item index="/order"><el-icon><Tickets /></el-icon><span>订单管理</span></el-menu-item>
          <el-menu-item index="/review"><el-icon><ChatDotRound /></el-icon><span>评论管理</span></el-menu-item>
          <el-menu-item index="/announcement"><el-icon><Bell /></el-icon><span>公告管理</span></el-menu-item>
        </template>
        <template v-else>
          <el-menu-item index="/movie"><el-icon><Film /></el-icon><span>电影浏览</span></el-menu-item>
          <el-menu-item index="/my-order"><el-icon><Tickets /></el-icon><span>我的订单</span></el-menu-item>
          <el-menu-item index="/favorite"><el-icon><Star /></el-icon><span>我的收藏</span></el-menu-item>
          <el-menu-item index="/announcement"><el-icon><Bell /></el-icon><span>系统公告</span></el-menu-item>
        </template>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="display:flex;align-items:center;justify-content:flex-end;background:#fff;box-shadow:0 1px 4px rgba(0,0,0,0.1)">
        <span style="margin-right:15px">{{ userStore.user?.nickname || userStore.user?.username }}</span>
        <el-tag :type="userStore.isAdmin() ? 'danger' : 'success'" size="small" style="margin-right:15px">
          {{ userStore.isAdmin() ? '管理员' : '用户' }}
        </el-tag>
        <el-button type="danger" link @click="handleLogout">退出</el-button>
      </el-header>
      <el-main style="background:#f0f2f5">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '../store/user'
import { Odometer, User, Film, OfficeBuilding, Clock, Tickets, ChatDotRound, Bell, Star } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}
</script>
