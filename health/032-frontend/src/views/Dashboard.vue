<template>
  <el-container style="height:100%">
    <el-aside width="220px">
      <el-menu :default-active="tab" @select="handleSelect">
        <el-menu-item v-if="!isElder" index="stats">概览</el-menu-item>
        <el-menu-item index="elders">{{ isElder ? '我的档案' : '老人档案' }}</el-menu-item>
        <el-menu-item index="measure">{{ isElder ? '我的健康' : '健康测量' }}</el-menu-item>
        <el-menu-item index="appt">{{ isElder ? '我的预约' : '预约管理' }}</el-menu-item>
        <el-menu-item index="fu">{{ isElder ? '我的随访' : '随访管理' }}</el-menu-item>
        <el-menu-item index="notice">通知</el-menu-item>
        <el-menu-item index="admin" v-if="isAdmin">用户管理</el-menu-item>
        <el-menu-item index="logout">退出</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div>{{ user.username }} {{ roleLabel }}</div>
      </el-header>
      <el-main>
        <Stats v-if="tab==='stats' && !isElder" />
        <Elders v-if="tab==='elders'" />
        <Measure v-if="tab==='measure'" />
        <Appt v-if="tab==='appt'" />
        <FU v-if="tab==='fu'" />
        <Notice v-if="tab==='notice'" />
        <Admin v-if="tab==='admin' && isAdmin" />
      </el-main>
    </el-container>
  </el-container>
</template>
<script setup>
import { computed, ref } from 'vue'
import Stats from './parts/Stats.vue'
import Elders from './parts/Elders.vue'
import Measure from './parts/Measure.vue'
import Appt from './parts/Appt.vue'
import FU from './parts/FU.vue'
import Notice from './parts/Notice.vue'
import Admin from './parts/Admin.vue'

const user = JSON.parse(localStorage.getItem('user') || '{}')
const isAdmin = user.role === 'ADMIN'
const isDoctor = user.role === 'DOCTOR'
const isElder = user.role === 'ELDER'
const roleLabelMap = {
  ADMIN: '管理员',
  DOCTOR: '医生',
  ELDER: '老人用户'
}
const roleLabel = computed(() => roleLabelMap[user.role] || user.role || '未登录')
const tab = ref(isDoctor ? 'appt' : isElder ? 'elders' : 'stats')

if (!localStorage.getItem('token') || !user.username) {
  location.href = '/login'
}

const handleSelect = (key) => {
  if (key === 'logout') {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    location.href = '/login'
    return
  }
  tab.value = key
}
</script>
