<template>
  <div class="profile-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <div class="user-card">
            <el-avatar :size="100" :src="userInfo?.avatar || '/default-avatar.png'" />
            <h3>{{ userInfo?.nickname }}</h3>
            <p>@{{ userInfo?.username }}</p>
            <el-divider />
            <div class="user-stats">
              <div class="stat-item">
                <div class="stat-value">{{ userInfo?.points || 0 }}</div>
                <div class="stat-label">积分</div>
              </div>
              <div class="stat-item">
                <div class="stat-value">{{ userInfo?.level || 1 }}</div>
                <div class="stat-label">等级</div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="16">
        <el-card>
          <template #header>
            <h3>个人信息</h3>
          </template>
          <el-form :model="form" label-width="100px">
            <el-form-item label="昵称">
              <el-input v-model="form.nickname" />
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="form.email" />
            </el-form-item>
            <el-form-item label="角色">
              <el-tag>{{ form.role }}</el-tag>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleUpdate">保存修改</el-button>
            </el-form-item>
          </el-form>
        </el-card>

        <el-card style="margin-top: 20px">
          <template #header>
            <h3>我的数据</h3>
          </template>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="我的资源" name="resource">
              <el-button type="primary" @click="goToMyResources">查看我的资源</el-button>
            </el-tab-pane>
            <el-tab-pane label="我的小组" name="group">
              <el-button type="primary" @click="goToMyGroups">查看我的小组</el-button>
            </el-tab-pane>
            <el-tab-pane label="我的提问" name="qa">
              <el-button type="primary" @click="goToMyQuestions">查看我的提问</el-button>
            </el-tab-pane>
            <el-tab-pane label="我的笔记" name="note">
              <el-button type="primary" @click="goToMyNotes">查看我的笔记</el-button>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getUserInfo, updateUserInfo } from '@/api/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const userInfo = ref(null)
const activeTab = ref('resource')

const form = ref({
  nickname: '',
  email: '',
  role: ''
})

const loadData = async () => {
  try {
    const res = await getUserInfo()
    userInfo.value = res.data
    form.value = { ...res.data }
  } catch (error) {
    console.error(error)
  }
}

const handleUpdate = async () => {
  try {
    await updateUserInfo(form.value)
    ElMessage.success('更新成功')
    userStore.setUserInfo(form.value)
    loadData()
  } catch (error) {
    console.error(error)
  }
}

const goToMyResources = () => router.push('/resource?my=1')
const goToMyGroups = () => router.push('/group?my=1')
const goToMyQuestions = () => router.push('/qa?my=1')
const goToMyNotes = () => router.push('/note?my=1')

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.profile-container {
  max-width: 1400px;
  margin: 0 auto;
}

.user-card {
  text-align: center;
}

.user-card h3 {
  margin: 15px 0 5px 0;
}

.user-card p {
  color: #909399;
  margin: 0;
}

.user-stats {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

.stat-item {
  text-align: center;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
}

.stat-label {
  color: #909399;
  font-size: 14px;
  margin-top: 5px;
}
</style>

