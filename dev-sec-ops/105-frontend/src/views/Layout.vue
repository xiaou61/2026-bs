<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">API 105</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>API 接口文档生成与测试用例管理平台</strong><span>接口、Mock、用例、执行、文档全链路协同</span></div>
        <div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div>
      </el-header>
      <el-main><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { logout } from '../api'
import { useUserStore } from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const menus = [
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'PRODUCT', 'TESTER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/project', label: '接口项目', roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] },
  { index: '/group', label: '接口分组', roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] },
  { index: '/endpoint', label: '接口定义', roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] },
  { index: '/request-param', label: '请求参数', roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] },
  { index: '/response-field', label: '响应字段', roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] },
  { index: '/mock-rule', label: 'Mock规则', roles: ['ADMIN', 'TESTER', 'DEVELOPER'] },
  { index: '/test-case', label: '测试用例', roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] },
  { index: '/test-step', label: '用例步骤', roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] },
  { index: '/environment', label: '环境配置', roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] },
  { index: '/execution', label: '执行记录', roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] },
  { index: '/execution-result', label: '结果明细', roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] },
  { index: '/document', label: '文档快照', roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const visibleMenus = computed(() => {
  const role = userStore.user?.role
  return menus.filter(item => item.roles.includes(role))
})
const handleLogout = async () => {
  await logout().catch(() => null)
  userStore.clear()
  router.push('/login')
}
</script>
