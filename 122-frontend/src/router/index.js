import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const ROLE_HOME = {
  ADMIN: '/dashboard',
  INSPECTOR: '/task',
  TEAM_LEADER: '/rectify',
  SUPERVISOR: '/project'
}

const canAccess = (role, allowedRoles) => !allowedRoles || allowedRoles.includes(role)

const routes = [
  { path: '/login', component: () => import('../views/Login.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/Dashboard.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'TEAM_LEADER', 'SUPERVISOR'] } },
      { path: 'user', component: () => import('../views/SysUser.vue'), meta: { roles: ['ADMIN'] } },
      { path: 'project', component: () => import('../views/ConstructionProject.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'SUPERVISOR'] } },
      { path: 'team', component: () => import('../views/WorkTeam.vue'), meta: { roles: ['ADMIN', 'TEAM_LEADER', 'SUPERVISOR'] } },
      { path: 'inspector', component: () => import('../views/SafetyInspector.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'SUPERVISOR'] } },
      { path: 'plan', component: () => import('../views/InspectionPlan.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'SUPERVISOR'] } },
      { path: 'task', component: () => import('../views/InspectionTask.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'SUPERVISOR'] } },
      { path: 'hazard', component: () => import('../views/HazardReport.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'SUPERVISOR'] } },
      { path: 'rectify', component: () => import('../views/RectificationOrder.vue'), meta: { roles: ['ADMIN', 'TEAM_LEADER', 'SUPERVISOR'] } },
      { path: 'acceptance', component: () => import('../views/AcceptanceRecord.vue'), meta: { roles: ['ADMIN', 'SUPERVISOR'] } },
      { path: 'training', component: () => import('../views/SafetyTraining.vue'), meta: { roles: ['ADMIN', 'TEAM_LEADER', 'SUPERVISOR'] } },
      { path: 'equipment', component: () => import('../views/EquipmentCheck.vue'), meta: { roles: ['ADMIN', 'INSPECTOR', 'SUPERVISOR'] } },
      { path: 'supply', component: () => import('../views/ProtectiveSupply.vue'), meta: { roles: ['ADMIN', 'TEAM_LEADER', 'SUPERVISOR'] } },
      { path: 'warning', component: () => import('../views/RiskWarning.vue'), meta: { roles: ['ADMIN', 'SUPERVISOR'] } },
      { path: 'log', component: () => import('../views/OperationLog.vue'), meta: { roles: ['ADMIN'] } }
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
