<template>
  <div class="portal-container profile-page">
    <el-card shadow="never">
      <template #header>
        <div class="page-title">申请人资料</div>
      </template>
      <el-form :model="form" label-width="110px">
        <el-row :gutter="16">
          <el-col :span="12"><el-form-item label="姓名"><el-input v-model="form.realName" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="身份证号"><el-input v-model="form.idCard" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="婚姻情况"><el-input v-model="form.maritalStatus" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="职业"><el-input v-model="form.occupation" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="收入水平"><el-input v-model="form.incomeLevel" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="地址"><el-input v-model="form.address" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="家庭情况"><el-input v-model="form.familyInfo" type="textarea" :rows="3" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="收养意愿"><el-input v-model="form.adoptionReason" type="textarea" :rows="4" /></el-form-item></el-col>
        </el-row>
        <el-button type="primary" @click="handleSave">保存资料</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { getProfile, updateProfile } from '../../api'

const form = reactive({})

const loadData = async () => {
  const res = await getProfile()
  Object.assign(form, res.data || {})
}

const handleSave = async () => {
  await updateProfile(form)
  ElMessage.success('保存成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.profile-page {
  padding-top: 28px;
}

.page-title {
  font-size: 22px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}
</style>
