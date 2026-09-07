<template>
  <div class="profile">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <div class="user-info">
            <el-avatar :size="80">{{ userInfo?.nickname?.charAt(0) }}</el-avatar>
            <h3>{{ userInfo?.nickname }}</h3>
            <p>{{ userInfo?.username }}</p>
            
            <div class="auth-status">
              <el-tag v-if="userInfo?.authStatus === 2" type="success">已实名认证</el-tag>
              <el-tag v-else-if="userInfo?.authStatus === 1" type="warning">认证审核中</el-tag>
              <el-tag v-else type="info">未认证</el-tag>
            </div>

            <div class="stats">
              <div class="stat-item">
                <div class="value">{{ userInfo?.creditScore }}</div>
                <div class="label">信用分</div>
              </div>
              <div class="stat-item">
                <div class="value">{{ userInfo?.totalOrders }}</div>
                <div class="label">订单数</div>
              </div>
            </div>

            <el-button type="primary" @click="dialogVisible = true">编辑资料</el-button>
          </div>
        </el-card>

        <el-card style="margin-top: 20px;">
          <template #header>
            <span>快捷入口</span>
          </template>
          <el-menu>
            <el-menu-item @click="$router.push('/order')">
              <el-icon><Document /></el-icon>
              <span>我的订单</span>
            </el-menu-item>
            <el-menu-item @click="$router.push('/my-publish')">
              <el-icon><Box /></el-icon>
              <span>我的发布</span>
            </el-menu-item>
            <el-menu-item @click="$router.push('/favorite')">
              <el-icon><Star /></el-icon>
              <span>我的收藏</span>
            </el-menu-item>
            <el-menu-item @click="$router.push('/wallet')">
              <el-icon><Wallet /></el-icon>
              <span>我的钱包</span>
            </el-menu-item>
            <el-menu-item @click="$router.push('/credit')">
              <el-icon><Medal /></el-icon>
              <span>信用中心</span>
            </el-menu-item>
            <el-menu-item @click="$router.push('/income')">
              <el-icon><Money /></el-icon>
              <span>收益中心</span>
            </el-menu-item>
          </el-menu>
        </el-card>
      </el-col>

      <el-col :span="16">
        <el-card>
          <template #header>
            <span>个人信息</span>
          </template>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="用户名">{{ userInfo?.username }}</el-descriptions-item>
            <el-descriptions-item label="昵称">{{ userInfo?.nickname }}</el-descriptions-item>
            <el-descriptions-item label="手机号">{{ userInfo?.phone }}</el-descriptions-item>
            <el-descriptions-item label="学号">{{ userInfo?.studentId || '-' }}</el-descriptions-item>
            <el-descriptions-item label="学校">{{ userInfo?.school || '-' }}</el-descriptions-item>
            <el-descriptions-item label="学院">{{ userInfo?.college || '-' }}</el-descriptions-item>
            <el-descriptions-item label="押金余额">¥{{ userInfo?.depositBalance }}</el-descriptions-item>
            <el-descriptions-item label="账户余额">¥{{ userInfo?.accountBalance }}</el-descriptions-item>
            <el-descriptions-item label="累计收益">¥{{ userInfo?.totalIncome }}</el-descriptions-item>
            <el-descriptions-item label="注册时间">{{ userInfo?.createTime }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" title="编辑资料" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" />
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="form.studentId" />
        </el-form-item>
        <el-form-item label="学校">
          <el-input v-model="form.school" />
        </el-form-item>
        <el-form-item label="学院">
          <el-input v-model="form.college" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleUpdate">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { getProfile, updateProfile } from '@/api/user'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()

const userInfo = ref(null)
const dialogVisible = ref(false)

const form = reactive({
  nickname: '',
  studentId: '',
  school: '',
  college: ''
})

const loadProfile = async () => {
  try {
    const res = await getProfile()
    userInfo.value = res.data
    form.nickname = res.data.nickname
    form.studentId = res.data.studentId
    form.school = res.data.school
    form.college = res.data.college
  } catch (error) {
    console.error(error)
  }
}

const handleUpdate = async () => {
  try {
    await updateProfile(form)
    ElMessage.success('更新成功')
    dialogVisible.value = false
    loadProfile()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadProfile()
})
</script>

<style scoped>
.user-info {
  text-align: center;
  padding: 20px;
}

.user-info h3 {
  margin: 15px 0 5px;
  font-size: 20px;
}

.user-info p {
  color: #909399;
  margin-bottom: 15px;
}

.auth-status {
  margin-bottom: 20px;
}

.stats {
  display: flex;
  justify-content: space-around;
  margin: 30px 0;
  padding: 20px 0;
  border-top: 1px solid #ebeef5;
  border-bottom: 1px solid #ebeef5;
}

.stat-item {
  text-align: center;
}

.stat-item .value {
  font-size: 24px;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 5px;
}

.stat-item .label {
  font-size: 14px;
  color: #909399;
}
</style>


