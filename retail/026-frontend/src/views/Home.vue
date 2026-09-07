<template>
  <div class="home-container">
    <el-card>
      <h2>欢迎使用画师接稿平台</h2>
      <p>当前用户：{{ user.username }}</p>
      <p>角色：{{ getUserRole() }}</p>
      
      <div v-if="user.role === 'USER' && !artist" style="margin-top: 20px">
        <el-button type="primary" @click="showArtistApply = true">申请成为画师</el-button>
      </div>
      
      <div v-if="artist && artist.status === 'PENDING'" style="margin-top: 20px">
        <el-alert title="您的画师认证申请正在审核中" type="info" :closable="false" />
      </div>
    </el-card>
    
    <el-dialog v-model="showArtistApply" title="申请成为画师" width="500px">
      <el-form :model="artistForm" label-width="100px">
        <el-form-item label="真实姓名">
          <el-input v-model="artistForm.realName" />
        </el-form-item>
        <el-form-item label="擅长风格">
          <el-input v-model="artistForm.style" />
        </el-form-item>
        <el-form-item label="价格区间">
          <el-input-number v-model="artistForm.priceMin" :min="0" style="width: 120px" />
          <span style="margin: 0 10px">-</span>
          <el-input-number v-model="artistForm.priceMax" :min="0" style="width: 120px" />
        </el-form-item>
        <el-form-item label="交稿周期">
          <el-input-number v-model="artistForm.deliveryDays" :min="1" />
          <span style="margin-left: 10px">天</span>
        </el-form-item>
        <el-form-item label="接受类型">
          <el-input v-model="artistForm.acceptTypes" placeholder="如:插画,漫画,设计" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showArtistApply = false">取消</el-button>
        <el-button type="primary" @click="submitArtistApply">提交申请</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import request from '../utils/request'
import { ElMessage } from 'element-plus'

const user = JSON.parse(localStorage.getItem('user') || '{}')
const artist = ref(null)
const showArtistApply = ref(false)

const artistForm = reactive({
  userId: user.id,
  realName: '',
  style: '',
  priceMin: 100,
  priceMax: 1000,
  deliveryDays: 7,
  acceptTypes: ''
})

const getUserRole = () => {
  const roleMap = {
    'USER': '普通用户',
    'ARTIST': '画师',
    'ADMIN': '管理员'
  }
  return roleMap[user.role] || '未知'
}

const loadArtistInfo = async () => {
  try {
    artist.value = await request.get(`/artist/user/${user.id}`)
  } catch (error) {
    // 用户还不是画师
  }
}

const submitArtistApply = async () => {
  try {
    await request.post('/artist/apply', artistForm)
    ElMessage.success('申请已提交，请等待审核')
    showArtistApply.value = false
    loadArtistInfo()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadArtistInfo()
})
</script>
