&lt;template&gt;
  &lt;div class="layout"&gt;
    &lt;el-container&gt;
      &lt;el-header class="header"&gt;
        &lt;div class="header-content"&gt;
          &lt;div class="logo" @click="$router.push('/')"&gt;小梦想公益平台&lt;/div&gt;
          &lt;el-menu mode="horizontal" :default-active="activeMenu" class="nav-menu"&gt;
            &lt;el-menu-item index="/" @click="$router.push('/')"&gt;首页&lt;/el-menu-item&gt;
            &lt;el-menu-item index="/projects" @click="$router.push('/projects')"&gt;公益项目&lt;/el-menu-item&gt;
            &lt;el-menu-item index="/my" @click="$router.push('/my')"&gt;个人中心&lt;/el-menu-item&gt;
          &lt;/el-menu&gt;
          &lt;div class="user-info"&gt;
            &lt;el-button v-if="!userStore.token" @click="$router.push('/login')"&gt;登录&lt;/el-button&gt;
            &lt;el-dropdown v-else&gt;
              &lt;span class="el-dropdown-link"&gt;
                {{ userStore.userInfo?.username || '用户' }}
                &lt;el-icon class="el-icon--right"&gt;&lt;arrow-down /&gt;&lt;/el-icon&gt;
              &lt;/span&gt;
              &lt;template #dropdown&gt;
                &lt;el-dropdown-menu&gt;
                  &lt;el-dropdown-item @click="handleLogout"&gt;退出登录&lt;/el-dropdown-item&gt;
                &lt;/el-dropdown-menu&gt;
              &lt;/template&gt;
            &lt;/el-dropdown&gt;
          &lt;/div&gt;
        &lt;/div&gt;
      &lt;/el-header&gt;
      &lt;el-main class="main-content"&gt;
        &lt;router-view /&gt;
      &lt;/el-main&gt;
      &lt;el-footer class="footer"&gt;
        &lt;div&gt;© 2026 小梦想全球公益捐赠平台&lt;/div&gt;
      &lt;/el-footer&gt;
    &lt;/el-container&gt;
  &lt;/div&gt;
&lt;/template&gt;

&lt;script setup&gt;
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { ArrowDown } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() =&gt; route.path)

const handleLogout = () =&gt; {
  userStore.logout()
  router.push('/login')
}
&lt;/script&gt;

&lt;style scoped&gt;
.layout {
  min-height: 100vh;
}

.header {
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  padding: 0;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  padding: 0 20px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: #409EFF;
  cursor: pointer;
}

.nav-menu {
  flex: 1;
  margin: 0 40px;
  border-bottom: none;
}

.user-info {
  display: flex;
  align-items: center;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
  display: flex;
  align-items: center;
}

.main-content {
  min-height: calc(100vh - 120px);
  padding: 20px;
  background-color: #f5f7fa;
}

.footer {
  background-color: #fff;
  text-align: center;
  padding: 20px;
  color: #909399;
}
&lt;/style&gt;
