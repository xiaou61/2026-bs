<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="企业名称" style="width: 200px" clearable />
        <el-input v-model="query.industry" placeholder="行业" style="width: 150px" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="openDialog()">登记企业</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="name" label="企业名称" />
        <el-table-column prop="industry" label="行业" />
        <el-table-column prop="scale" label="规模" />
        <el-table-column prop="city" label="城市" />
        <el-table-column prop="contactName" label="联系人" />
        <el-table-column prop="contactPhone" label="联系电话" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已认证' : '待认证' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link @click="openDialog(row)">编辑</el-button>
            <el-button link type="success" @click="handleVerify(row.id)" v-if="userStore.isAdmin() && row.status === 0">认证</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="total, prev, pager, next" :total="total" v-model:current-page="query.pageNum" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑企业' : '登记企业'" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="企业名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="行业"><el-input v-model="form.industry" /></el-form-item>
        <el-form-item label="规模">
          <el-select v-model="form.scale">
            <el-option label="1-50人" value="1-50人" />
            <el-option label="50-200人" value="50-200人" />
            <el-option label="200-500人" value="200-500人" />
            <el-option label="500人以上" value="500人以上" />
          </el-select>
        </el-form-item>
        <el-form-item label="城市"><el-input v-model="form.city" /></el-form-item>
        <el-form-item label="地址"><el-input v-model="form.address" /></el-form-item>
        <el-form-item label="联系人"><el-input v-model="form.contactName" /></el-form-item>
        <el-form-item label="联系电话"><el-input v-model="form.contactPhone" /></el-form-item>
        <el-form-item label="企业简介"><el-input v-model="form.description" type="textarea" :rows="4" /></el-form-item>
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
import { getCompanyList, addCompany, updateCompany, deleteCompany, verifyCompany } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, name: '', industry: '' })
const form = reactive({ id: null, name: '', industry: '', scale: '', city: '', address: '', contactName: '', contactPhone: '', description: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getCompanyList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const openDialog = (row = null) => {
  if (row) {
    Object.assign(form, row)
  } else {
    Object.assign(form, { id: null, name: '', industry: '', scale: '', city: '', address: '', contactName: '', contactPhone: '', description: '' })
  }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (form.id) {
    await updateCompany(form)
  } else {
    await addCompany(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleVerify = async (id) => {
  await verifyCompany(id)
  ElMessage.success('认证成功')
  loadData()
}

const handleDelete = async (id) => {
  await deleteCompany(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
