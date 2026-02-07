<template>
  <el-container style="height: 100vh">
    <el-aside :width="isCollapse ? '64px' : '220px'" style="background: #304156; transition: width 0.3s;">
      <div class="logo">
        <span v-if="!isCollapse">🥛 鲜牛奶订购</span>
        <span v-else>🥛</span>
      </div>
      <el-menu :default-active="$route.path" :collapse="isCollapse" background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF" router>
        <el-menu-item index="/dashboard">
          <el-icon><DataAnalysis /></el-icon>
          <span>首页</span>
        </el-menu-item>
        <template v-if="userRole === 'ADMIN'">
          <el-sub-menu index="sys">
            <template #title><el-icon><Setting /></el-icon><span>系统管理</span></template>
            <el-menu-item index="/user">用户管理</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="milk">
            <template #title><el-icon><GoodsFilled /></el-icon><span>产品管理</span></template>
            <el-menu-item index="/category">分类管理</el-menu-item>
            <el-menu-item index="/product">产品管理</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="delivery-mgr">
            <template #title><el-icon><Van /></el-icon><span>配送管理</span></template>
            <el-menu-item index="/area">区域管理</el-menu-item>
            <el-menu-item index="/route">路线管理</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="biz">
            <template #title><el-icon><List /></el-icon><span>业务管理</span></template>
            <el-menu-item index="/subscription">订阅管理</el-menu-item>
            <el-menu-item index="/order">订单管理</el-menu-item>
            <el-menu-item index="/complaint">投诉管理</el-menu-item>
          </el-sub-menu>
        </template>
        <template v-if="userRole === 'DELIVERY'">
          <el-menu-item index="/delivery/task">
            <el-icon><Coordinate /></el-icon>
            <span>今日任务</span>
          </el-menu-item>
          <el-menu-item index="/delivery/history">
            <el-icon><Document /></el-icon>
            <span>配送历史</span>
          </el-menu-item>
        </template>
        <template v-if="userRole === 'USER'">
          <el-menu-item index="/my/product">
            <el-icon><GoodsFilled /></el-icon>
            <span>产品浏览</span>
          </el-menu-item>
          <el-menu-item index="/my/subscription">
            <el-icon><Calendar /></el-icon>
            <span>我的订阅</span>
          </el-menu-item>
          <el-menu-item index="/my/order">
            <el-icon><List /></el-icon>
            <span>我的订单</span>
          </el-menu-item>
          <el-menu-item index="/my/address">
            <el-icon><Location /></el-icon>
            <span>地址管理</span>
          </el-menu-item>
          <el-menu-item index="/my/complaint">
            <el-icon><ChatLineRound /></el-icon>
            <span>我的反馈</span>
          </el-menu-item>
        </template>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="display: flex; align-items: center; justify-content: space-between; border-bottom: 1px solid #eee; background: #fff;">
        <el-icon style="cursor: pointer; font-size: 20px;" @click="isCollapse = !isCollapse"><Fold /></el-icon>
        <div style="display: flex; align-items: center; gap: 15px;">
          <span>{{ userStore.user?.nickname || userStore.user?.username }}</span>
          <el-tag size="small">{{ roleMap[userRole] }}</el-tag>
          <el-button type="danger" link @click="handleLogout">退出</el-button>
        </div>
      </el-header>
      <el-main style="background: #f0f2f5; padding: 15px;">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const isCollapse = ref(false)
const userRole = computed(() => userStore.user?.role || '')
const roleMap = { ADMIN: '管理员', DELIVERY: '配送员', USER: '用户' }

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  background: #263445;
}
</style>
