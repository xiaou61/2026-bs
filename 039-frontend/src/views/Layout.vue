<template>
  <div class="layout">
    <el-header class="header">
      <div class="logo" @click="router.push('/')">
        <el-icon><Headset /></el-icon>
        <span>民歌民谣交流平台</span>
      </div>
      <el-menu mode="horizontal" :ellipsis="false" class="nav-menu">
        <el-menu-item index="/" @click="router.push('/')">首页</el-menu-item>
        <el-sub-menu index="categories">
          <template #title>分类</template>
          <el-menu-item v-for="cat in categories" :key="cat.id" @click="router.push(`/category/${cat.id}`)">
            {{ cat.name }}
          </el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/announcements" @click="router.push('/announcements')">公告</el-menu-item>
      </el-menu>
      <div class="search-area">
        <el-input v-model="searchKeyword" placeholder="搜索民歌" @keyup.enter="handleSearch" clearable>
          <template #prefix><el-icon><Search /></el-icon></template>
        </el-input>
      </div>
      <div class="user-area">
        <template v-if="userStore.isLoggedIn">
          <el-dropdown>
            <span class="user-info">
              <el-avatar :size="32" :src="userStore.user?.avatar || ''">{{ userStore.user?.nickname?.charAt(0) }}</el-avatar>
              <span class="username">{{ userStore.user?.nickname }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="router.push('/profile')">个人中心</el-dropdown-item>
                <el-dropdown-item @click="router.push('/my-songs')">我的作品</el-dropdown-item>
                <el-dropdown-item @click="router.push('/my-favorites')">我的收藏</el-dropdown-item>
                <el-dropdown-item @click="router.push('/publish')">发布作品</el-dropdown-item>
                <el-dropdown-item v-if="userStore.isAdmin" divided @click="router.push('/admin')">管理后台</el-dropdown-item>
                <el-dropdown-item divided @click="userStore.logout()">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </template>
        <template v-else>
          <el-button type="primary" @click="router.push('/login')">登录</el-button>
          <el-button @click="router.push('/register')">注册</el-button>
        </template>
      </div>
    </el-header>
    <el-main class="main">
      <router-view />
    </el-main>
    <el-footer class="footer">
      <p>© 2024 民歌民谣交流平台 - 传承民族文化，弘扬民间艺术</p>
    </el-footer>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { categoryApi } from '@/api'

const router = useRouter()
const userStore = useUserStore()
const categories = ref([])
const searchKeyword = ref('')

onMounted(async () => {
  const res = await categoryApi.getAll()
  categories.value = res.data
})

const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    router.push({ path: '/search', query: { keyword: searchKeyword.value } })
  }
}
</script>

<style scoped lang="scss">
.layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  display: flex;
  align-items: center;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 0 20px;
  height: 60px;

  .logo {
    display: flex;
    align-items: center;
    font-size: 20px;
    font-weight: bold;
    color: #409eff;
    cursor: pointer;
    margin-right: 40px;

    .el-icon {
      font-size: 28px;
      margin-right: 8px;
    }
  }

  .nav-menu {
    flex: 1;
    border: none;
  }

  .search-area {
    width: 240px;
    margin: 0 20px;
  }

  .user-area {
    display: flex;
    align-items: center;
    gap: 10px;

    .user-info {
      display: flex;
      align-items: center;
      cursor: pointer;

      .username {
        margin-left: 8px;
        color: #333;
      }
    }
  }
}

.main {
  flex: 1;
  background: #f5f5f5;
  padding: 20px;
}

.footer {
  text-align: center;
  color: #999;
  background: #fff;
  border-top: 1px solid #eee;
}
</style>
