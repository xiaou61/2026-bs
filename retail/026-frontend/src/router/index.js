import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../views/Layout.vue'
import Login from '../views/Login.vue'

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/',
        component: Layout,
        children: [
            {
                path: '',
                redirect: '/home'
            },
            {
                path: '/home',
                name: 'Home',
                component: () => import('../views/Home.vue')
            },
            {
                path: '/artist-list',
                name: 'ArtistList',
                component: () => import('../views/artist/ArtistList.vue')
            },
            {
                path: '/artist-detail/:id',
                name: 'ArtistDetail',
                component: () => import('../views/artist/ArtistDetail.vue')
            },
            {
                path: '/portfolio',
                name: 'Portfolio',
                component: () => import('../views/portfolio/Portfolio.vue')
            },
            {
                path: '/demand',
                name: 'Demand',
                component: () => import('../views/demand/DemandList.vue')
            },
            {
                path: '/demand/create',
                name: 'DemandCreate',
                component: () => import('../views/demand/DemandCreate.vue')
            },
            {
                path: '/order',
                name: 'Order',
                component: () => import('../views/order/OrderList.vue')
            },
            {
                path: '/order/:id',
                name: 'OrderDetail',
                component: () => import('../views/order/OrderDetail.vue')
            },
            {
                path: '/profile',
                name: 'Profile',
                component: () => import('../views/Profile.vue')
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
