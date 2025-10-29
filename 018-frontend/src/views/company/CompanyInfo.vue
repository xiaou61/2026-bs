<template>
  <el-card>
    <template #header>
      <h3>企业信息</h3>
    </template>
    <el-form :model="form" label-width="100px" v-loading="loading">
      <el-form-item label="企业名称">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="所属行业">
        <el-input v-model="form.industry" />
      </el-form-item>
      <el-form-item label="企业规模">
        <el-select v-model="form.scale">
          <el-option label="0-20人" value="0-20人" />
          <el-option label="20-99人" value="20-99人" />
          <el-option label="100-499人" value="100-499人" />
          <el-option label="500-999人" value="500-999人" />
          <el-option label="1000-9999人" value="1000-9999人" />
          <el-option label="10000人以上" value="10000人以上" />
        </el-select>
      </el-form-item>
      <el-form-item label="企业地址">
        <el-input v-model="form.location" />
      </el-form-item>
      <el-form-item label="官网">
        <el-input v-model="form.website" />
      </el-form-item>
      <el-form-item label="企业简介">
        <el-input v-model="form.description" type="textarea" :rows="6" />
      </el-form-item>
      <el-form-item label="认证状态">
        <el-tag v-if="form.status === 0" type="warning">待审核</el-tag>
        <el-tag v-else-if="form.status === 1" type="success">已认证</el-tag>
        <el-tag v-else type="danger">已拒绝</el-tag>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { getCompanyById, updateCompany } from '@/api/company'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()

const form = ref({
  id: null,
  name: '',
  industry: '',
  scale: '',
  location: '',
  website: '',
  description: '',
  status: 0
})

const loading = ref(false)

const loadCompany = async () => {
  loading.value = true
  try {
    const companyId = userStore.user.companyId
    const res = await getCompanyById(companyId)
    form.value = res.data
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handleSave = async () => {
  try {
    await updateCompany(form.value)
    ElMessage.success('保存成功')
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadCompany()
})
</script>

