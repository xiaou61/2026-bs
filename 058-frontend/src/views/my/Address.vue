<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>地址管理</span>
          <el-button type="success" @click="handleAdd">新增地址</el-button>
        </div>
      </template>
      <el-empty v-if="list.length === 0" description="暂无地址" />
      <el-table :data="list" v-loading="loading" stripe v-else>
        <el-table-column prop="contactName" label="联系人" width="80" />
        <el-table-column prop="contactPhone" label="电话" width="130" />
        <el-table-column label="地址">
          <template #default="{ row }">{{ row.province }}{{ row.city }}{{ row.district }}{{ row.detail }}</template>
        </el-table-column>
        <el-table-column prop="isDefault" label="默认" width="80">
          <template #default="{ row }">
            <el-tag v-if="row.isDefault === 1" type="success">默认</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="warning" @click="handleSetDefault(row.id)" v-if="row.isDefault !== 1">设为默认</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑地址' : '新增地址'" width="500px">
      <el-form :model="form" ref="formRef" label-width="80px" :rules="rules">
        <el-form-item label="联系人" prop="contactName"><el-input v-model="form.contactName" /></el-form-item>
        <el-form-item label="电话" prop="contactPhone"><el-input v-model="form.contactPhone" /></el-form-item>
        <el-form-item label="省"><el-input v-model="form.province" /></el-form-item>
        <el-form-item label="市"><el-input v-model="form.city" /></el-form-item>
        <el-form-item label="区"><el-input v-model="form.district" /></el-form-item>
        <el-form-item label="详细地址" prop="detail"><el-input v-model="form.detail" /></el-form-item>
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

const loading = ref(false)
const list = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({ id: null, contactName: '', contactPhone: '', province: '', city: '', district: '', detail: '' })
const rules = {
  contactName: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
  contactPhone: [{ required: true, message: '请输入电话', trigger: 'blur' }],
  detail: [{ required: true, message: '请输入详细地址', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try { const res = await getAddressList(); list.value = res.data } finally { loading.value = false }
}

const handleAdd = () => {
  Object.assign(form, { id: null, contactName: '', contactPhone: '', province: '', city: '', district: '', detail: '' })
  dialogVisible.value = true
}
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) { await updateAddress(form) } else { await addAddress(form) }
  ElMessage.success('操作成功'); dialogVisible.value = false; loadData()
}

const handleDelete = async (id) => { await deleteAddress(id); ElMessage.success('删除成功'); loadData() }
const handleSetDefault = async (id) => { await setDefaultAddress(id); ElMessage.success('设置成功'); loadData() }
onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
</style>
