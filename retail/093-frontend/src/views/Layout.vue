<template>
  <div class="layout">
    <el-container>
      <el-aside width="228px" class="aside">
        <div class="logo">自助售货 093</div>
        <el-menu :default-active="route.path" router>
          <el-menu-item index="/dashboard">首页</el-menu-item>
          <el-menu-item v-if="isManager" index="/user">账号管理</el-menu-item>
          <el-menu-item v-if="isManager" index="/location">点位管理</el-menu-item>
          <el-menu-item index="/machine">设备管理</el-menu-item>
          <el-menu-item v-if="isManager" index="/category">分类管理</el-menu-item>
          <el-menu-item index="/product">商品管理</el-menu-item>
          <el-menu-item v-if="isManager" index="/slot">货道管理</el-menu-item>
          <el-menu-item v-if="isManager" index="/replenish">补货记录</el-menu-item>
          <el-menu-item index="/order">{{ isCustomer ? '我的订单' : '订单管理' }}</el-menu-item>
          <el-menu-item index="/payment">{{ isCustomer ? '钱包中心' : '支付记录' }}</el-menu-item>
          <el-menu-item index="/shipment">{{ isCustomer ? '我的出货' : '出货记录' }}</el-menu-item>
          <el-menu-item index="/fault">{{ isCustomer ? '问题反馈' : '故障处理' }}</el-menu-item>
          <el-menu-item index="/notice">公告通知</el-menu-item>
          <el-menu-item v-if="isManager" index="/statistics">经营看板</el-menu-item>
          <el-menu-item index="/profile">个人中心</el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header class="header">
          <div class="title">蓝橙自助售货管理系统</div>
          <div class="right">
            <span>{{ roleText }}</span>
            <span>{{ user?.nickname || user?.username }}</span>
            <el-button type="danger" link @click="handleLogout">退出</el-button>
          </div>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../store/user'
import { logout } from '../api'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const user = computed(() => userStore.user)
const role = computed(() => (user.value?.role || '').toUpperCase())
const isManager = computed(() => ['ADMIN', 'STAFF'].includes(role.value))
const isCustomer = computed(() => role.value === 'CUSTOMER')
const roleTextMap = {
  ADMIN: '当前角色：管理员',
  STAFF: '当前角色：补货员',
  CUSTOMER: '当前角色：顾客'
}
const roleText = computed(() => roleTextMap[role.value] || '当前角色：访客')

const handleLogout = async () => {
  try {
    await logout()
  } catch (e) {
  }
  userStore.clearLogin()
  router.push('/login')
}
</script>

<style scoped>
.layout {
  min-height: 100vh;
}

.aside {
  background: linear-gradient(180deg, #14324b 0%, #0f2233 100%);
  color: #fff;
}

.logo {
  height: 58px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  font-weight: 700;
  letter-spacing: 1px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.14);
}

:deep(.el-menu) {
  border-right: none;
  background: transparent;
}

:deep(.el-menu-item) {
  color: #d4e0ea;
}

:deep(.el-menu-item.is-active) {
  background: rgba(255, 255, 255, 0.12);
  color: #fff;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  border-bottom: 1px solid #ebeef5;
}

.title {
  font-size: 18px;
  font-weight: 600;
  color: #17324d;
}

.right {
  display: flex;
  align-items: center;
  gap: 14px;
  color: #475467;
}
</style>
