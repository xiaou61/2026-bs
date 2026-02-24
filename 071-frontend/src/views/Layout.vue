<template>
  <el-container style="height:100vh">
    <el-aside :width="isCollapse ? '64px' : '220px'" style="background:#304156;transition:width .3s">
      <div class="logo">{{ isCollapse ? '骑' : '共享单车系统' }}</div>
      <el-menu :default-active="$route.path" :collapse="isCollapse" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF" :collapse-transition="false">
        <el-menu-item index="/dashboard"><el-icon><DataAnalysis /></el-icon><span>数据看板</span></el-menu-item>
        <el-menu-item v-if="role === 'user'" index="/ride/start"><el-icon><Bicycle /></el-icon><span>开始骑行</span></el-menu-item>
        <el-menu-item v-if="role === 'user'" index="/ride/status"><el-icon><Position /></el-icon><span>骑行中</span></el-menu-item>
        <el-menu-item v-if="role === 'user'" index="/ride/history"><el-icon><List /></el-icon><span>骑行记录</span></el-menu-item>
        <el-menu-item v-if="role !== 'user'" index="/ride/orders"><el-icon><Tickets /></el-icon><span>全部订单</span></el-menu-item>
        <el-menu-item v-if="role !== 'user'" index="/bike/list"><el-icon><Van /></el-icon><span>单车管理</span></el-menu-item>
        <el-menu-item v-if="role !== 'user'" index="/station/list"><el-icon><MapLocation /></el-icon><span>站点管理</span></el-menu-item>
        <el-menu-item v-if="role === 'user'" index="/wallet/my"><el-icon><Wallet /></el-icon><span>我的钱包</span></el-menu-item>
        <el-menu-item v-if="role === 'user'" index="/wallet/records"><el-icon><Document /></el-icon><span>流水记录</span></el-menu-item>
        <el-menu-item v-if="role === 'user'" index="/fault/report"><el-icon><WarningFilled /></el-icon><span>故障上报</span></el-menu-item>
        <el-menu-item v-if="role !== 'user'" index="/fault/list"><el-icon><Warning /></el-icon><span>故障管理</span></el-menu-item>
        <el-menu-item v-if="role === 'admin'" index="/pricing/list"><el-icon><PriceTag /></el-icon><span>计费规则</span></el-menu-item>
        <el-menu-item v-if="role === 'user'" index="/credit/info"><el-icon><Medal /></el-icon><span>信用信息</span></el-menu-item>
        <el-menu-item v-if="role === 'admin'" index="/user/list"><el-icon><UserFilled /></el-icon><span>用户管理</span></el-menu-item>
        <el-menu-item index="/announcement/list"><el-icon><Bell /></el-icon><span>公告管理</span></el-menu-item>
        <el-menu-item index="/feedback/list"><el-icon><ChatDotRound /></el-icon><span>反馈管理</span></el-menu-item>
        <el-menu-item index="/user/profile"><el-icon><User /></el-icon><span>个人中心</span></el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="display:flex;align-items:center;justify-content:space-between;background:#fff;box-shadow:0 1px 4px rgba(0,21,41,.08)">
        <el-icon style="cursor:pointer;font-size:20px" @click="isCollapse = !isCollapse"><Fold v-if="!isCollapse" /><Expand v-else /></el-icon>
        <div style="display:flex;align-items:center;gap:15px">
          <span>{{ userStore.user?.realName || userStore.user?.username }}</span>
          <el-tag>{{ roleLabel }}</el-tag>
          <el-button type="danger" link @click="handleLogout">退出</el-button>
        </div>
      </el-header>
      <el-main style="background:#f0f2f5;padding:15px">
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
const role = computed(() => userStore.user?.role)
const roleMap = { admin: '管理员', operator: '运维人员', user: '普通用户' }
const roleLabel = computed(() => roleMap[role.value] || '')

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.logo { height: 60px; display: flex; align-items: center; justify-content: center; color: #fff; font-size: 18px; font-weight: bold; background: #263445; white-space: nowrap; overflow: hidden; }
.el-menu { border-right: none; }
</style>
