<template>
  <div class="express-list-container">
    <el-page-header title="返回" @back="$router.back()">
      <template #content>
        <span class="page-title">快递管理</span>
      </template>
    </el-page-header>

    <el-card class="content-card">
      <el-form :inline="true" :model="queryForm">
        <el-form-item label="状态">
          <el-select v-model="queryForm.status" placeholder="全部" clearable style="width: 150px">
            <el-option label="待取件" :value="0" />
            <el-option label="已取件" :value="1" />
            <el-option label="超期" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="快递公司">
          <el-select v-model="queryForm.expressCompany" placeholder="全部" clearable style="width: 150px">
            <el-option v-for="item in companyList" :key="item.id" :label="item.name" :value="item.name" />
          </el-select>
        </el-form-item>
        <el-form-item label="关键词">
          <el-input v-model="queryForm.keyword" placeholder="单号/收件人/手机号" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" v-loading="loading" style="margin-top: 20px">
        <el-table-column prop="trackingNumber" label="快递单号" width="180" />
        <el-table-column prop="expressCompany" label="快递公司" width="120" />
        <el-table-column prop="pickupCode" label="取件码" width="100" />
        <el-table-column prop="recipientName" label="收件人" width="100" />
        <el-table-column prop="recipientPhone" label="手机号" width="120" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.status === 0" type="warning">待取件</el-tag>
            <el-tag v-else-if="row.status === 1" type="success">已取件</el-tag>
            <el-tag v-else-if="row.status === 2" type="danger">超期</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="inTime" label="入库时间" width="180" />
        <el-table-column prop="outTime" label="取件时间" width="180" />
      </el-table>

      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        :page-sizes="[10, 20, 50]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getExpressList } from '@/api/express'
import { getAllCompanies } from '@/api/company'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const tableData = ref([])
const companyList = ref([])

const queryForm = reactive({
  status: null,
  expressCompany: '',
  keyword: ''
})

const pagination = reactive({
  page: 1,
  size: 10,
  total: 0
})

const handleReset = () => {
  queryForm.status = null
  queryForm.expressCompany = ''
  queryForm.keyword = ''
  loadData()
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      size: pagination.size,
      ...queryForm
    }
    const res = await getExpressList(params)
    tableData.value = res.data.records
    pagination.total = res.data.total
  } catch (error) {
    console.error(error)
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

const loadCompanies = async () => {
  try {
    const res = await getAllCompanies()
    companyList.value = res.data
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadData()
  loadCompanies()
})
</script>

<style scoped>
.express-list-container {
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

