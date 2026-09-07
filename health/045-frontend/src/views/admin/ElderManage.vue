<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>老人管理</span>
          <el-button type="primary" @click="showCheckInDialog">入住登记</el-button>
        </div>
      </template>
      <el-form inline>
        <el-form-item label="姓名">
          <el-input v-model="query.name" placeholder="请输入姓名" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="query.status" placeholder="全部" clearable>
            <el-option label="在住" :value="1" />
            <el-option label="已退住" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">搜索</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="gender" label="性别">
          <template #default="{ row }">{{ row.gender === 1 ? '男' : '女' }}</template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" />
        <el-table-column prop="careLevel" label="护理等级">
          <template #default="{ row }">{{ careLevelMap[row.careLevel] }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '在住' : '已退住' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="checkInDate" label="入住日期" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button type="primary" link @click="editElder(row)">编辑</el-button>
            <el-button v-if="row.status === 1" type="danger" link @click="handleCheckOut(row.id)">退住</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top: 16px;" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑老人' : '入住登记'" width="600">
      <el-form :model="form" label-width="100px">
        <el-form-item label="姓名" required><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="性别" required>
          <el-radio-group v-model="form.gender">
            <el-radio :value="1">男</el-radio>
            <el-radio :value="2">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" required><el-input-number v-model="form.age" :min="0" /></el-form-item>
        <el-form-item label="身份证号"><el-input v-model="form.idCard" /></el-form-item>
        <el-form-item label="联系电话"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="护理等级" required>
          <el-select v-model="form.careLevel">
            <el-option v-for="(v, k) in careLevelMap" :key="k" :label="v" :value="Number(k)" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="!form.id" label="床位" required>
          <el-select v-model="form.bedId" placeholder="选择床位">
            <el-option v-for="b in beds" :key="b.id" :label="`${b.bedNo}`" :value="b.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="健康状况"><el-input v-model="form.healthStatus" type="textarea" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getElderList, elderCheckIn, elderCheckOut, updateElder, getAvailableBeds } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'

const careLevelMap: Record<number, string> = { 1: '自理', 2: '半护理', 3: '全护理', 4: '特护' }
const loading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const query = reactive({ current: 1, size: 10, name: '', status: undefined as number | undefined })
const dialogVisible = ref(false)
const form = ref<any>({})
const beds = ref<any[]>([])

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getElderList(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const showCheckInDialog = async () => {
  form.value = { gender: 1, careLevel: 1 }
  const res: any = await getAvailableBeds()
  beds.value = res.data
  dialogVisible.value = true
}

const editElder = (row: any) => {
  form.value = { ...row }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (form.value.id) {
    await updateElder(form.value.id, form.value)
  } else {
    await elderCheckIn(form.value)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleCheckOut = async (id: number) => {
  await ElMessageBox.confirm('确认办理退住？', '提示')
  await elderCheckOut(id)
  ElMessage.success('退住成功')
  loadData()
}

onMounted(loadData)
</script>
