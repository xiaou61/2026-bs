<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">CROSS BORDER 115</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/merchant'>商家店铺</el-menu-item>
        <el-menu-item index='/customer'>客户档案</el-menu-item>
        <el-menu-item index='/sku'>商品SKU</el-menu-item>
        <el-menu-item index='/order'>跨境订单</el-menu-item>
        <el-menu-item index='/declaration'>清关申报</el-menu-item>
        <el-menu-item index='/document'>清关单证</el-menu-item>
        <el-menu-item index='/tax'>税费记录</el-menu-item>
        <el-menu-item index='/rate'>汇率牌价</el-menu-item>
        <el-menu-item index='/settlement'>结算账单</el-menu-item>
        <el-menu-item index='/payment'>支付记录</el-menu-item>
        <el-menu-item index='/logistics'>物流跟踪</el-menu-item>
        <el-menu-item index='/reconciliation'>订单对账</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>跨境电商清关订单与汇率结算平台</strong><span>订单清关、单证税费、汇率结算、物流对账一体化管理</span></div>
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
