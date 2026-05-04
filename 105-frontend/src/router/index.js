import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'user', component: () => import('../views/SysUser.vue') },
  { path: 'project', component: () => import('../views/ApiProject.vue') },
  { path: 'group', component: () => import('../views/ApiGroup.vue') },
  { path: 'endpoint', component: () => import('../views/ApiEndpoint.vue') },
  { path: 'request-param', component: () => import('../views/RequestParam.vue') },
  { path: 'response-field', component: () => import('../views/ResponseField.vue') },
  { path: 'mock-rule', component: () => import('../views/MockRule.vue') },
  { path: 'test-case', component: () => import('../views/TestCase.vue') },
  { path: 'test-step', component: () => import('../views/TestStep.vue') },
  { path: 'environment', component: () => import('../views/EnvironmentConfig.vue') },
  { path: 'execution', component: () => import('../views/TestExecution.vue') },
  { path: 'execution-result', component: () => import('../views/ExecutionResult.vue') },
  { path: 'document', component: () => import('../views/DocumentSnapshot.vue') },
  { path: 'log', component: () => import('../views/OperationLog.vue') },
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.path !== '/login' && !userStore.token) next('/login')
  else if (to.path === '/login' && userStore.token) next('/dashboard')
  else next()
})

export default router
