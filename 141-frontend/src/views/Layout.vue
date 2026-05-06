<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 141</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/asset'>资产档案</el-menu-item>
        <el-menu-item index='/category'>资产分类</el-menu-item>
        <el-menu-item index='/tag'>RFID标签</el-menu-item>
        <el-menu-item index='/location'>存放位置</el-menu-item>
        <el-menu-item index='/inventory-task'>盘点任务</el-menu-item>
        <el-menu-item index='/inventory-record'>盘点明细</el-menu-item>
        <el-menu-item index='/borrow-apply'>借用申请</el-menu-item>
        <el-menu-item index='/return-record'>借用归还</el-menu-item>
        <el-menu-item index='/repair'>维修记录</el-menu-item>
        <el-menu-item index='/depreciation'>折旧记录</el-menu-item>
        <el-menu-item index='/disposal'>闲置处置</el-menu-item>
        <el-menu-item index='/notice'>预警通知</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>固定资产 RFID 盘点与借用归还系统</strong><span>面向固定资产管理的RFID盘点、借用归还、维修折旧和预警通知一体化管理</span></div>
        <div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div>
      </el-header>
      <el-main><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { logout } from '../api'
import { useUserStore } from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const handleLogout = async () => {
  await logout().catch(() => null)
  userStore.clear()
  router.push('/login')
}
</script>







