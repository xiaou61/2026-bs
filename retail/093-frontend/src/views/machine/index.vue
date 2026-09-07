<template>
  <div class="page-container">
    <el-card v-if="isManager">
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="设备名称" clearable />
        <el-select v-model="query.locationId" placeholder="点位" clearable>
          <el-option v-for="item in locationOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable>
          <el-option label="ONLINE" value="ONLINE" />
          <el-option label="OFFLINE" value="OFFLINE" />
          <el-option label="MAINTAIN" value="MAINTAIN" />
        </el-select>
        <el-button type="primary" @click="loadManagerData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增设备</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="machineNo" label="设备编号" min-width="150" />
        <el-table-column prop="name" label="设备名称" min-width="160" />
        <el-table-column prop="locationName" label="点位" min-width="160" />
        <el-table-column prop="temperatureType" label="温区" min-width="100" />
        <el-table-column prop="status" label="状态" min-width="100" />
        <el-table-column prop="managerName" label="负责人" min-width="100" />
        <el-table-column prop="managerPhone" label="联系电话" min-width="120" />
        <el-table-column prop="lastReplenishTime" label="最近补货" min-width="180" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-row v-else :gutter="16">
      <el-col v-for="item in publicList" :key="item.id" :span="8">
        <el-card class="machine-card" shadow="hover">
          <h3>{{ item.name }}</h3>
          <p>设备编号：{{ item.machineNo }}</p>
          <p>点位：{{ item.locationName || '-' }}</p>
          <p>温区：{{ item.temperatureType || '-' }}</p>
          <p>状态：{{ item.status }}</p>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑设备' : '新增设备'" width="620px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="90px">
        <el-form-item label="设备编号" prop="machineNo">
          <el-input v-model="form.machineNo" />
        </el-form-item>
        <el-form-item label="设备名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="所属点位" prop="locationId">
          <el-select v-model="form.locationId" style="width: 100%">
            <el-option v-for="item in locationOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="温区类型">
          <el-select v-model="form.temperatureType" style="width: 100%">
            <el-option label="COLD" value="COLD" />
            <el-option label="NORMAL" value="NORMAL" />
            <el-option label="HOT" value="HOT" />
          </el-select>
        </el-form-item>
        <el-form-item label="负责人">
          <el-input v-model="form.managerName" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.managerPhone" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width: 100%">
            <el-option label="ONLINE" value="ONLINE" />
            <el-option label="OFFLINE" value="OFFLINE" />
            <el-option label="MAINTAIN" value="MAINTAIN" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deleteMachine, getLocationOptions, getMachineList, getMachinePublicList, saveMachine } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isManager = computed(() => ['ADMIN', 'STAFF'].includes((userStore.user?.role || '').toUpperCase()))

const loading = ref(false)
const tableData = ref([])
const publicList = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const locationOptions = ref([])
const query = reactive({
  pageNum: 1,
  pageSize: 10,
  name: '',
  locationId: null,
  status: ''
})
const form = reactive({
  id: null,
  machineNo: '',
  locationId: null,
  name: '',
  temperatureType: 'NORMAL',
  status: 'ONLINE',
  managerName: '',
  managerPhone: '',
  remark: ''
})
const rules = {
  machineNo: [{ required: true, message: '请输入设备编号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入设备名称', trigger: 'blur' }],
  locationId: [{ required: true, message: '请选择点位', trigger: 'change' }]
}

const loadOptions = async () => {
  const res = await getLocationOptions()
  locationOptions.value = res.data || []
}

const loadManagerData = async () => {
  loading.value = true
  try {
    const res = await getMachineList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const loadPublicData = async () => {
  const res = await getMachinePublicList()
  publicList.value = res.data || []
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    machineNo: '',
    locationId: null,
    name: '',
    temperatureType: 'NORMAL',
    status: 'ONLINE',
    managerName: '',
    managerPhone: '',
    remark: ''
  })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await saveMachine(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadManagerData()
}

const handleDelete = async id => {
  await deleteMachine(id)
  ElMessage.success('删除成功')
  loadManagerData()
}

onMounted(async () => {
  await loadOptions()
  if (isManager.value) {
    await loadManagerData()
  } else {
    await loadPublicData()
  }
})
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}

.machine-card h3 {
  margin: 0 0 10px;
  color: #17324d;
}

.machine-card p {
  margin: 6px 0;
  color: #667085;
}
</style>
