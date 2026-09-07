<template>
  <div class="page-container">
    <el-card style="max-width: 600px; margin: 0 auto">
      <template #header>店铺信息</template>
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="店铺名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="店铺Logo">
          <el-input v-model="form.logo" />
        </el-form-item>
        <el-form-item label="店铺描述">
          <el-input v-model="form.description" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="联系人" prop="contact">
          <el-input v-model="form.contact" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="店铺地址">
          <el-input v-model="form.address" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">保存修改</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMerchantInfo, updateMerchant } from '../../api'

const formRef = ref()
const loading = ref(false)
const form = reactive({ name: '', logo: '', description: '', contact: '', phone: '', address: '' })
const rules = {
  name: [{ required: true, message: '请输入店铺名称', trigger: 'blur' }],
  contact: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
}

onMounted(async () => {
  const res = await getMerchantInfo()
  if (res.data) {
    Object.assign(form, res.data)
  }
})

const handleSubmit = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    await updateMerchant(form)
    ElMessage.success('保存成功')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.page-container { padding: 20px; }
</style>
