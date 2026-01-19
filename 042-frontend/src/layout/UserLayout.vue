<template>
  <el-container class="user-layout">
    <el-aside width="220px" class="aside">
      <div class="logo">
        <el-icon><HomeFilled /></el-icon>
        <span>房屋租赁系统</span>
      </div>
      <el-menu
        :default-active="$route.path"
        router
        background-color="#001529"
        text-color="#ffffffa6"
        active-text-color="#fff"
      >
        <template v-if="userStore.isLandlord">
          <el-menu-item index="/landlord">
            <el-icon><DataLine /></el-icon>
            <span>控制台</span>
          </el-menu-item>
          <el-menu-item index="/landlord/houses">
            <el-icon><House /></el-icon>
            <span>我的房源</span>
          </el-menu-item>
          <el-menu-item index="/landlord/appointments">
            <el-icon><Calendar /></el-icon>
            <span>预约管理</span>
          </el-menu-item>
          <el-menu-item index="/landlord/contracts">
            <el-icon><Document /></el-icon>
            <span>合同管理</span>
          </el-menu-item>
          <el-menu-item index="/landlord/bills">
            <el-icon><Money /></el-icon>
            <span>账单管理</span>
          </el-menu-item>
          <el-menu-item index="/landlord/repairs">
            <el-icon><Tools /></el-icon>
            <span>报修管理</span>
          </el-menu-item>
        </template>
        <template v-else>
          <el-menu-item index="/tenant">
            <el-icon><DataLine /></el-icon>
            <span>控制台</span>
          </el-menu-item>
          <el-menu-item index="/tenant/favorites">
            <el-icon><Star /></el-icon>
            <span>我的收藏</span>
          </el-menu-item>
          <el-menu-item index="/tenant/appointments">
            <el-icon><Calendar /></el-icon>
            <span>我的预约</span>
          </el-menu-item>
          <el-menu-item index="/tenant/contracts">
            <el-icon><Document /></el-icon>
            <span>我的合同</span>
          </el-menu-item>
          <el-menu-item index="/tenant/bills">
            <el-icon><Money /></el-icon>
            <span>我的账单</span>
          </el-menu-item>
          <el-menu-item index="/tenant/repairs">
            <el-icon><Tools /></el-icon>
            <span>我的报修</span>
          </el-menu-item>
        </template>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <router-link to="/" class="back-home">
            <el-icon><Back /></el-icon>
            返回首页
          </router-link>
        </div>
        <div class="header-right">
          <el-badge :value="unreadCount" :hidden="!unreadCount">
            <el-icon class="icon-btn" @click="showMessages = true"><Bell /></el-icon>
          </el-badge>
          <el-dropdown trigger="click">
            <span class="user-info">
              <el-avatar :size="32">{{ userStore.userInfo?.realName?.[0] }}</el-avatar>
              <span>{{ userStore.userInfo?.realName || userStore.userInfo?.username }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="$router.push('/profile')">个人设置</el-dropdown-item>
                <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
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

  <!-- 消息抽屉 -->
  <el-drawer v-model="showMessages" title="系统消息" size="400px">
    <div v-if="messages.length" class="message-list">
      <div v-for="msg in messages" :key="msg.id" class="message-item" :class="{ unread: !msg.isRead }">
        <div class="msg-title">{{ msg.title }}</div>
        <div class="msg-content">{{ msg.content }}</div>
        <div class="msg-time">{{ msg.createTime }}</div>
      </div>
    </div>
    <el-empty v-else description="暂无消息" />
  </el-drawer>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import request from '@/utils/request'

const router = useRouter()
const userStore = useUserStore()

const showMessages = ref(false)
const messages = ref([])
const unreadCount = ref(0)

onMounted(async () => {
  try {
    const res = await request.get('/message/unread-count')
    unreadCount.value = res.data
    const listRes = await request.get('/message/list')
    messages.value = listRes.data?.records || []
  } catch (e) {}
})

const handleLogout = () => {
  userStore.logout()
  router.push('/')
}
</script>

<style scoped>
.user-layout {
  min-height: 100vh;
}

.aside {
  background: #001529;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  border-bottom: 1px solid #ffffff20;
}

.header {
  background: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 1px 4px rgba(0,0,0,0.1);
}

.header-left .back-home {
  display: flex;
  align-items: center;
  gap: 4px;
  color: #666;
}

.header-left .back-home:hover {
  color: #409eff;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.icon-btn {
  font-size: 20px;
  cursor: pointer;
  color: #666;
}

.icon-btn:hover {
  color: #409eff;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.main {
  background: #f0f2f5;
}

.message-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.message-item {
  padding: 12px;
  background: #f5f5f5;
  border-radius: 8px;
}

.message-item.unread {
  background: #e6f7ff;
  border-left: 3px solid #1890ff;
}

.msg-title {
  font-weight: bold;
  margin-bottom: 4px;
}

.msg-content {
  color: #666;
  font-size: 14px;
}

.msg-time {
  color: #999;
  font-size: 12px;
  margin-top: 8px;
}
</style>
