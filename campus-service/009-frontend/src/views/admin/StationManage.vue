<template>
  <div class="station-manage-container">
    <el-page-header title="返回" @back="$router.back()">
      <template #content>
        <span class="page-title">代收点管理</span>
      </template>
      <template #extra>
        <el-button type="primary" @click="handleAdd">新增代收点</el-button>
      </template>
    </el-page-header>

    <el-card class="content-card">
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="name" label="代收点名称" />
        <el-table-column prop="address" label="地址" />
        <el-table-column prop="contactPhone" label="联系电话" width="120" />
        <el-table-column prop="businessHours" label="营业时间" width="120" />
        <el-table-column prop="managerName" label="负责人" width="100" />
        <el-table-column label="库存情况" width="200">
          <template #default="{ row }">
            <el-progress
              :percentage="Math.round((row.currentStock / row.shelfCapacity) * 100)"
              :color="getProgressColor(row)"
            >
              <span>{{ row.currentStock }}/{{ row.shelfCapacity }}</span>
            </el-progress>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '营业中' : '暂停' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" :title="dialogTitle" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="代收点名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" />
        </el-form-item>
        <el-form-item label="营业时间" prop="businessHours">
          <el-input v-model="form.businessHours" placeholder="例如：8:00-20:00" />
        </el-form-item>
        <el-form-item label="负责人姓名" prop="managerName">
          <el-input v-model="form.managerName" />
        </el-form-item>
        <el-form-item label="货架容量" prop="shelfCapacity">
          <el-input-number v-model="form.shelfCapacity" :min="1" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :value="1">营业中</el-radio>
            <el-radio :value="0">暂停</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitLoading">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getStationList, addStation, updateStation, deleteStation } from '@/api/station'

const loading = ref(false)
const submitLoading = ref(false)
const showDialog = ref(false)
const formRef = ref()
const tableData = ref([])
const isEdit = ref(false)

const form = reactive({
  id: null,
  name: '',
  address: '',
  contactPhone: '',
  businessHours: '8:00-20:00',
  managerName: '',
  shelfCapacity: 500,
  status: 1
})

const rules = {
  name: [{ required: true, message: '请输入代收点名称', trigger: 'blur' }],
  address: [{ required: true, message: '请输入地址', trigger: 'blur' }],
  contactPhone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
  shelfCapacity: [{ required: true, message: '请输入货架容量', trigger: 'blur' }]
}

const dialogTitle = computed(() => isEdit.value ? '编辑代收点' : '新增代收点')

const getProgressColor = (row) => {
  const percent = (row.currentStock / row.shelfCapacity) * 100
  if (percent < 50) return '#67C23A'
  if (percent < 80) return '#E6A23C'
  return '#F56C6C'
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(form, {
    id: null,
    name: '',
    address: '',
    contactPhone: '',
    businessHours: '8:00-20:00',
    managerName: '',
    shelfCapacity: 500,
    status: 1
  })
  showDialog.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  Object.assign(form, row)
  showDialog.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  submitLoading.value = true
  try {
    if (isEdit.value) {
      await updateStation(form.id, form)
      ElMessage.success('修改成功')
    } else {
      await addStation(form)
      ElMessage.success('添加成功')
    }
    showDialog.value = false
    loadData()
  } catch (error) {
    console.error(error)
  } finally {
    submitLoading.value = false
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该代收点吗？', '提示', { type: 'warning' })
    await deleteStation(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getStationList({ page: 1, size: 100 })
    tableData.value = res.data.records
  } catch (error) {
    console.error(error)
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.station-manage-container {
  padding: 20px;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
}

.content-card {
  margin-top: 20px;
}
</style>

