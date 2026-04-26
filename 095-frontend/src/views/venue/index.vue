<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="球场名称" clearable />
        <el-input v-model="query.city" placeholder="城市" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增球场</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="venueNo" label="球场编号" min-width="150" />
        <el-table-column prop="name" label="球场名称" min-width="150" />
        <el-table-column prop="city" label="城市" min-width="100" />
        <el-table-column prop="address" label="地址" min-width="180" />
        <el-table-column prop="capacity" label="容量" min-width="90" />
        <el-table-column prop="turfType" label="草皮类型" min-width="100" />
        <el-table-column label="操作" width="180">
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑球场' : '新增球场'" width="620px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="球场编号">
          <el-input v-model="form.venueNo" />
        </el-form-item>
        <el-form-item label="球场名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="城市">
          <el-input v-model="form.city" />
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" />
        </el-form-item>
        <el-form-item label="容量">
          <el-input-number v-model="form.capacity" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="草皮类型">
          <el-input v-model="form.turfType" />
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
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deleteVenue, getVenueList, saveVenue } from '../../api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, name: '', city: '' })
const form = reactive({ id: null, venueNo: '', name: '', city: '', address: '', capacity: 20000, turfType: '', status: 1 })
const rules = { name: [{ required: true, message: '请输入球场名称', trigger: 'blur' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res = await getVenueList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, venueNo: '', name: '', city: '', address: '', capacity: 20000, turfType: '', status: 1 })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await saveVenue(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteVenue(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
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
</style>
