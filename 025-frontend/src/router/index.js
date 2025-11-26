import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/',
        name: 'Layout',
        component: () => import('../views/Layout.vue'),
        redirect: '/home',
        children: [
            {
                path: 'home',
                name: 'Home',
                component: () => import('../views/Home.vue')
            },
            {
                path: 'owner',
                name: 'Owner',
                component: () => import('../views/Owner.vue')
            },
            {
                path: 'fee',
                name: 'Fee',
                component: () => import('../views/Fee.vue')
            },
            {
                path: 'repair',
                name: 'Repair',
                component: () => import('../views/Repair.vue')
            },
            {
                path: 'notice',
                name: 'Notice',
                component: () => import('../views/Notice.vue')
            },
            {
                path: 'visitor',
                name: 'Visitor',
                component: () => import('../views/Visitor.vue')
            },
            {
                path: 'parking',
                name: 'Parking',
                component: () => import('../views/Parking.vue')
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
