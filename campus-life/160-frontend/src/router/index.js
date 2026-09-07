import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  UNION: '/budget',
  CLUB: '/activity',
  TREASURER: '/reimbursement',
  WAREHOUSE: '/asset',
  MEMBER: '/borrow'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'UNION', 'CLUB', 'TREASURER', 'WAREHOUSE', 'MEMBER'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'club', component: () => import('../views/ClubProfile.vue'), meta: { roles: ['ADMIN', 'UNION'] } },
      { path: 'member', component: () => import('../views/MemberProfile.vue'), meta: { roles: ['ADMIN', 'UNION', 'CLUB'] } },
      { path: 'activity', component: () => import('../views/ActivityPlan.vue'), meta: { roles: ['ADMIN', 'UNION', 'CLUB', 'MEMBER'] } },
      { path: 'budget', component: () => import('../views/BudgetRequest.vue'), meta: { roles: ['ADMIN', 'UNION', 'CLUB', 'TREASURER'] } },
      { path: 'lineitem', component: () => import('../views/BudgetLineItem.vue'), meta: { roles: ['ADMIN', 'UNION', 'CLUB', 'TREASURER'] } },
      { path: 'approval', component: () => import('../views/ApprovalRecord.vue'), meta: { roles: ['ADMIN', 'UNION'] } },
      { path: 'reimbursement', component: () => import('../views/ReimbursementClaim.vue'), meta: { roles: ['ADMIN', 'UNION', 'CLUB', 'TREASURER', 'MEMBER'] } },
      { path: 'receipt', component: () => import('../views/ReceiptArchive.vue'), meta: { roles: ['ADMIN', 'UNION', 'TREASURER'] } },
      { path: 'asset', component: () => import('../views/MaterialAsset.vue'), meta: { roles: ['ADMIN', 'UNION', 'WAREHOUSE'] } },
      { path: 'borrow', component: () => import('../views/BorrowRequest.vue'), meta: { roles: ['ADMIN', 'UNION', 'CLUB', 'WAREHOUSE', 'MEMBER'] } },
      { path: 'returncheck', component: () => import('../views/ReturnInspection.vue'), meta: { roles: ['ADMIN', 'UNION', 'WAREHOUSE', 'CLUB'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN', 'UNION'] } }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const role = userStore.user?.role
  const home = ROLE_HOME[role] || '/login'
  if (to.path !== '/login' && !userStore.token) return next('/login')
  if (to.path === '/login' && userStore.token) return next(home)
  if (to.meta?.roles && !to.meta.roles.includes(role)) return next(home)
  next()
})
export default router
