import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  PRODUCT: '/project',
  TESTER: '/test-case',
  DEVELOPER: '/project'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'PRODUCT', 'TESTER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'project', component: () => import('../views/ApiProject.vue'), meta: { roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] } },
      { path: 'group', component: () => import('../views/ApiGroup.vue'), meta: { roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] } },
      { path: 'endpoint', component: () => import('../views/ApiEndpoint.vue'), meta: { roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] } },
      { path: 'request-param', component: () => import('../views/RequestParam.vue'), meta: { roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] } },
      { path: 'response-field', component: () => import('../views/ResponseField.vue'), meta: { roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] } },
      { path: 'mock-rule', component: () => import('../views/MockRule.vue'), meta: { roles: ['ADMIN', 'TESTER', 'DEVELOPER'] } },
      { path: 'test-case', component: () => import('../views/TestCase.vue'), meta: { roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] } },
      { path: 'test-step', component: () => import('../views/TestStep.vue'), meta: { roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] } },
      { path: 'environment', component: () => import('../views/EnvironmentConfig.vue'), meta: { roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] } },
      { path: 'execution', component: () => import('../views/TestExecution.vue'), meta: { roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] } },
      { path: 'execution-result', component: () => import('../views/ExecutionResult.vue'), meta: { roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] } },
      { path: 'document', component: () => import('../views/DocumentSnapshot.vue'), meta: { roles: ['ADMIN', 'PRODUCT', 'TESTER', 'DEVELOPER'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN'] } },
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const role = userStore.user?.role
  const home = ROLE_HOME[role] || '/login'
  if (to.path !== '/login' && !userStore.token) {
    next('/login')
    return
  }
  if (to.path === '/login' && userStore.token) {
    next(home)
    return
  }
  if (to.meta?.roles && !canAccess(role, to.meta.roles)) {
    next(home)
    return
  }
  next()
})

export default router
