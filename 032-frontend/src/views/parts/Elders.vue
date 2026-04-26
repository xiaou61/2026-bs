<template>
  <el-space direction="vertical" fill>
    <template v-if="isElder">
      <el-button @click="load">刷新档案</el-button>
      <el-card v-if="profile">
        <el-descriptions :column="2" border title="我的健康档案">
          <el-descriptions-item label="姓名">{{ profile.name }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{ genderLabel(profile.gender) }}</el-descriptions-item>
          <el-descriptions-item label="出生日期">{{ profile.birthDate }}</el-descriptions-item>
          <el-descriptions-item label="联系电话">{{ profile.phone }}</el-descriptions-item>
          <el-descriptions-item label="居住地址">{{ profile.address || '-' }}</el-descriptions-item>
          <el-descriptions-item label="紧急联系人">{{ profile.emergencyContact || '-' }}</el-descriptions-item>
        </el-descriptions>
      </el-card>
      <el-empty v-else description="当前账号还没有关联老人档案" />
    </template>
    <template v-else>
      <el-form inline :model="f">
        <el-form-item><el-input v-model="f.name" placeholder="姓名" /></el-form-item>
        <el-form-item>
          <el-select v-model="f.gender" placeholder="性别">
            <el-option label="男" value="MALE" />
            <el-option label="女" value="FEMALE" />
          </el-select>
        </el-form-item>
        <el-form-item><el-date-picker v-model="f.birthDate" type="date" placeholder="出生日期" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item><el-input v-model="f.phone" placeholder="电话" /></el-form-item>
        <el-form-item><el-input v-model="f.address" placeholder="地址" style="width:260px" /></el-form-item>
        <el-form-item><el-input v-model="f.emergencyContact" placeholder="紧急联系人" /></el-form-item>
        <el-button type="primary" @click="create">新增</el-button>
      </el-form>
      <el-table :data="list" style="width:100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column label="性别" width="80">
          <template #default="{ row }">{{ genderLabel(row.gender) }}</template>
        </el-table-column>
        <el-table-column prop="birthDate" label="出生" />
        <el-table-column prop="phone" label="电话" />
        <el-table-column prop="address" label="地址" />
      </el-table>
    </template>
  </el-space>
</template>
<script setup>
import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import api from '../../api'

const currentUser = JSON.parse(localStorage.getItem('user') || '{}')
const isElder = currentUser.role === 'ELDER'
const f = reactive({ name:'', gender:'FEMALE', birthDate:'', phone:'', address:'', emergencyContact:'' })
const list = ref([])
const profile = ref(null)

const genderLabel = (value) => {
  if (value === 'MALE') return '男'
  if (value === 'FEMALE') return '女'
  return value || '-'
}

const load = async () => {
  const { data } = await api.get(isElder ? '/api/elders/me' : '/api/elders')
  if (data.code !== 0) {
    ElMessage.error(data.message)
    return
  }
  if (isElder) {
    profile.value = data.data
    return
  }
  list.value = data.data
}

const create = async () => {
  const { data } = await api.post('/api/elders', f)
  if (data.code !== 0) {
    ElMessage.error(data.message)
    return
  }
  ElMessage.success('新增成功')
  load()
}

onMounted(load)
</script>
