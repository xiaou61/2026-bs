<template>
  <div class="profile-page">
    <el-row :gutter="20">
      <!-- 用户信息卡片 -->
      <el-col :span="8">
        <el-card>
          <div class="user-info">
            <el-avatar :size="100" :src="userStore.userInfo?.avatar" />
            <h3>{{ userStore.userInfo?.nickname }}</h3>
            <p>{{ userStore.userInfo?.phone }}</p>
            
            <el-tag :type="getLevelType(userStore.userInfo?.level)" size="large" style="margin-top: 10px">
              {{ userStore.userInfo?.level }}
            </el-tag>
            
            <el-button type="primary" style="margin-top: 20px; width: 100%" @click="editDialogVisible = true">
              编辑资料
            </el-button>
            
            <el-button type="danger" plain style="margin-top: 10px; width: 100%" @click="handleLogout">
              退出登录
            </el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 积分余额卡片 -->
      <el-col :span="16">
        <el-row :gutter="20">
          <!-- 积分 -->
          <el-col :span="12">
            <el-card>
              <div class="info-item">
                <el-icon :size="50" color="#409EFF"><Star /></el-icon>
                <div class="info-content">
                  <h4>我的积分</h4>
                  <p class="number">{{ userStore.userInfo?.points || 0 }}</p>
                  <p class="desc">消费1元=1积分</p>
                </div>
              </div>
            </el-card>
          </el-col>

          <!-- 余额 -->
          <el-col :span="12">
            <el-card>
              <div class="info-item">
                <el-icon :size="50" color="#67C23A"><Wallet /></el-icon>
                <div class="info-content">
                  <h4>账户余额</h4>
                  <p class="number">¥{{ userStore.userInfo?.balance || 0 }}</p>
                  <el-button type="success" size="small" @click="rechargeDialogVisible = true">
                    充值
                  </el-button>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>

        <!-- 会员等级说明 -->
        <el-card style="margin-top: 20px">
          <h3>会员等级说明</h3>
          <el-descriptions :column="2" border style="margin-top: 15px">
            <el-descriptions-item label="普通会员">
              <el-tag>0-99积分</el-tag>
              <span style="margin-left: 10px">无折扣</span>
            </el-descriptions-item>
            <el-descriptions-item label="银卡会员">
              <el-tag type="info">100-499积分</el-tag>
              <span style="margin-left: 10px">9.5折</span>
            </el-descriptions-item>
            <el-descriptions-item label="金卡会员">
              <el-tag type="warning">500-1999积分</el-tag>
              <span style="margin-left: 10px">9折</span>
            </el-descriptions-item>
            <el-descriptions-item label="钻石会员">
              <el-tag type="danger">2000+积分</el-tag>
              <span style="margin-left: 10px">8.5折</span>
            </el-descriptions-item>
          </el-descriptions>
        </el-card>

        <!-- 积分获取方式 -->
        <el-card style="margin-top: 20px">
          <h3>积分获取方式</h3>
          <ul style="margin-top: 15px; line-height: 2">
            <li>注册账号：赠送 <el-tag size="small">10积分</el-tag></li>
            <li>每日签到：赠送 <el-tag size="small">5积分</el-tag></li>
            <li>消费获得：1元 = <el-tag size="small">1积分</el-tag></li>
            <li>评价服务：赠送 <el-tag size="small">10积分</el-tag></li>
          </ul>
        </el-card>
      </el-col>
    </el-row>

    <!-- 编辑资料对话框 -->
    <el-dialog v-model="editDialogVisible" title="编辑资料" width="500px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="昵称">
          <el-input v-model="editForm.nickname" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="editForm.gender">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="头像">
          <el-input v-model="editForm.avatar" placeholder="头像URL" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleUpdate" :loading="updating">保存</el-button>
      </template>
    </el-dialog>

    <!-- 充值对话框 -->
    <el-dialog v-model="rechargeDialogVisible" title="余额充值" width="400px">
      <el-form label-width="80px">
        <el-form-item label="充值金额">
          <el-input-number
            v-model="rechargeAmount"
            :min="1"
            :max="10000"
            :step="100"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item>
          <div style="color: #999; font-size: 14px">
            这是模拟充值，仅用于演示
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="rechargeDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleRecharge" :loading="recharging">确认充值</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/store/user'
import { updateProfile, rechargeBalance } from '@/api/auth'
import { Star, Wallet } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

const editDialogVisible = ref(false)
const rechargeDialogVisible = ref(false)
const updating = ref(false)
const recharging = ref(false)
const rechargeAmount = ref(100)

const editForm = reactive({
  nickname: userStore.userInfo?.nickname || '',
  gender: userStore.userInfo?.gender || 0,
  avatar: userStore.userInfo?.avatar || ''
})

// 获取等级标签类型
const getLevelType = (level) => {
  const typeMap = {
    '普通会员': '',
    '银卡会员': 'info',
    '金卡会员': 'warning',
    '钻石会员': 'danger'
  }
  return typeMap[level] || ''
}

// 更新资料
const handleUpdate = async () => {
  updating.value = true
  try {
    await updateProfile(editForm)
    ElMessage.success('更新成功')
    editDialogVisible.value = false
    
    // 刷新用户信息
    await userStore.fetchUserInfo()
  } catch (error) {
    console.error('更新失败：', error)
  } finally {
    updating.value = false
  }
}

// 充值
const handleRecharge = async () => {
  if (rechargeAmount.value <= 0) {
    ElMessage.error('充值金额必须大于0')
    return
  }

  recharging.value = true
  try {
    await rechargeBalance(rechargeAmount.value)
    ElMessage.success(`充值成功：¥${rechargeAmount.value}`)
    rechargeDialogVisible.value = false
    
    // 刷新用户信息
    await userStore.fetchUserInfo()
  } catch (error) {
    console.error('充值失败：', error)
  } finally {
    recharging.value = false
  }
}

// 退出登录
const handleLogout = () => {
  userStore.logout()
  ElMessage.success('已退出登录')
  router.push('/home')
}
</script>

<style scoped>
.profile-page {
  max-width: 1200px;
  margin: 30px auto;
  padding: 0 20px;
}

.user-info {
  text-align: center;
  padding: 20px;
}

.user-info h3 {
  margin: 15px 0 5px;
  font-size: 20px;
}

.user-info p {
  color: #666;
  margin: 5px 0;
}

.info-item {
  display: flex;
  align-items: center;
  padding: 20px;
}

.info-content {
  margin-left: 20px;
  flex: 1;
}

.info-content h4 {
  margin: 0 0 10px;
  color: #666;
}

.info-content .number {
  font-size: 28px;
  font-weight: bold;
  color: #333;
  margin: 10px 0;
}

.info-content .desc {
  font-size: 14px;
  color: #999;
  margin: 5px 0;
}

h3 {
  font-size: 18px;
  margin-bottom: 10px;
}

ul {
  padding-left: 20px;
}

li {
  list-style: disc;
}
</style>
