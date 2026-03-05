<template>
  <div class="page-container">
    <el-card>
      <div class="header">
        <el-button type="primary" @click="handleAdd">新增地址</el-button>
      </div>
      <div v-for="item in addressList" :key="item.id" class="address-item">
        <div class="address-info">
          <div class="address-header">
            <span class="name">{{ item.name }}</span>
            <span class="phone">{{ item.phone }}</span>
            <el-tag v-if="item.isDefault === 1" size="small" type="danger">默认</el-tag>
          </div>
          <div class="address-detail">{{ item.province }}{{ item.city }}{{ item.district }}{{ item.detail }}</div>
        </div>
        <div class="address-actions">
          <el-button v-if="item.isDefault !== 1" link @click="handleSetDefault(item.id)">设为默认</el-button>
          <el-button link type="primary" @click="handleEdit(item)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(item.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </div>
      </div>
      <el-empty v-if="!addressList.length" description="暂无收货地址" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑地址' : '新增地址'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="收货人" prop="name"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="手机号" prop="phone"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="省份" prop="province"><el-input v-model="form.province" /></el-form-item>
        <el-form-item label="城市" prop="city"><el-input v-model="form.city" /></el-form-item>
        <el-form-item label="区县" prop="district"><el-input v-model="form.district" /></el-form-item>
        <el-form-item label="详细地址" prop="detail"><el-input v-model="form.detail" type="textarea" /></el-form-item>
        <el-form-item label="默认地址"><el-switch v-model="form.isDefault" :active-value="1" :inactive-value="0" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getAddressList, addAddress, updateAddress, deleteAddress, setDefaultAddress } from '../../api'

const addressList = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({ id: null, name: '', phone: '', province: '', city: '', district: '', detail: '', isDefault: 0 })
const rules = {
  name: [{ required: true, message: '请输入收货人', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
  province: [{ required: true, message: '请输入省份', trigger: 'blur' }],
  city: [{ required: true, message: '请输入城市', trigger: 'blur' }],
  detail: [{ required: true, message: '请输入详细地址', trigger: 'blur' }]
}

const loadData = async () => {
  const res = await getAddressList()
  addressList.value = res.data
}

const handleAdd = () => {
  Object.assign(form, { id: null, name: '', phone: '', province: '', city: '', district: '', detail: '', isDefault: 0 })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateAddress(form)
  } else {
    await addAddress(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteAddress(id)
  ElMessage.success('删除成功')
  loadData()
}

const handleSetDefault = async (id) => {
  await setDefaultAddress(id)
  ElMessage.success('设置成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 20px; }
.header { margin-bottom: 20px; }
.address-item { display: flex; justify-content: space-between; align-items: center; padding: 15px; border: 1px solid #eee; border-radius: 4px; margin-bottom: 10px; }
.address-header { display: flex; align-items: center; gap: 10px; margin-bottom: 8px; }
.name { font-weight: bold; }
.phone { color: #666; }
.address-detail { color: #666; font-size: 14px; }
</style>
