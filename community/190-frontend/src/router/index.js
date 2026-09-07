import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  PROPERTY: '/building',
  DISPATCH: '/ticket',
  TECHNICIAN: '/repair',
  INSPECTOR: '/inspection',
  TENANT: '/ticket'
}

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'TECHNICIAN', 'INSPECTOR', 'TENANT'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'building', component: () => import('../views/BuildingProfile.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'INSPECTOR'] } },
      { path: 'equipment', component: () => import('../views/EquipmentAsset.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'TECHNICIAN', 'INSPECTOR'] } },
      { path: 'tenant', component: () => import('../views/TenantProfile.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'TENANT'] } },
      { path: 'ticket', component: () => import('../views/VisitRepairTicket.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'TECHNICIAN', 'TENANT'] } },
      { path: 'repair', component: () => import('../views/RepairDispatch.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'TECHNICIAN'] } },
      { path: 'plan', component: () => import('../views/MaintenancePlan.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'TECHNICIAN', 'INSPECTOR'] } },
      { path: 'task', component: () => import('../views/MaintenanceTask.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'TECHNICIAN', 'INSPECTOR'] } },
      { path: 'alert', component: () => import('../views/FaultAlert.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'TECHNICIAN', 'INSPECTOR'] } },
      { path: 'inspection', component: () => import('../views/InspectionRecord.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'TECHNICIAN', 'INSPECTOR'] } },
      { path: 'spare', component: () => import('../views/SparePartStock.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'TECHNICIAN'] } },
      { path: 'feedback', component: () => import('../views/ServiceFeedback.vue'), meta: { roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'TENANT'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN'] } }
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
