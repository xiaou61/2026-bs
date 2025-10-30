<template>
  <div class="profile">
    <el-card>
      <template #header>
        <span>个人信息</span>
      </template>
      
      <el-form :model="form" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled />
        </el-form-item>
        
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" />
        </el-form-item>
        
        <el-form-item label="性别">
          <el-radio-group v-model="form.gender">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="手机号">
          <el-input v-model="form.phone" />
        </el-form-item>
        
        <el-form-item label="邮箱">
          <el-input v-model="form.email" />
        </el-form-item>
        
        <el-form-item label="身高(cm)">
          <el-input-number v-model="form.height" :min="100" :max="250" />
        </el-form-item>
        
        <el-form-item label="体重(kg)">
          <el-input-number v-model="form.weight" :min="30" :max="200" />
        </el-form-item>
        
        <el-form-item label="当前积分">
          <el-tag type="success" size="large">{{ form.points }}</el-tag>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleUpdate">保存修改</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getUserInfo, updateUser } from '@/api/user'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()

const form = ref({
  username: '',
  nickname: '',
  gender: '',
  phone: '',
  email: '',
  height: 0,
  weight: 0,
  points: 0
})

const loadUserInfo = async () => {
  try {
    const res = await getUserInfo()
    Object.assign(form.value, res.data)
  } catch (error) {
    console.error(error)
  }
}

const handleUpdate = async () => {
  try {
    await updateUser(form.value)
    userStore.setUserInfo(form.value)
    ElMessage.success('更新成功')
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadUserInfo()
})
</script>

<style scoped>
.profile {
  max-width: 600px;
}
</style>

