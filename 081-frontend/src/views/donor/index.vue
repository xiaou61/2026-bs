<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.donorType" placeholder="捐赠人类型" style="width: 150px" clearable>
          <el-option label="个人" value="personal" />
          <el-option label="企业" value="enterprise" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="donorType" label="类型">
          <template #default="{ row }">
            {{ row.donorType === 'personal' ? '个人' : '企业' }}
          </template>
        </el-table-column>
        <el-table-column prop="companyName" label="企业名称" />
        <el-table-column prop="contactPerson" label="联系人" />
        <el-table-column prop="contactPhone" label="联系电话" />
        <el-table-column prop="totalAmount" label="累计捐赠金额" />
        <el-table-column prop="donationCount" label="捐赠次数" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        @current-change="loadData"
        layout="total, prev, pager, next"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑捐赠人' : '新增捐赠人'" width="500px">
      <el-form :model="form" ref="formRef" label-width="100px">
        <el-form-item label="类型" prop="donorType">
          <el-radio-group v-model="form.donorType">
            <el-radio label="personal">个人</el-radio>
            <el-radio label="enterprise">企业</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="企业名称" prop="companyName" v-if="form.donorType === 'enterprise'">
          <el-input v-model="form.companyName" />
        </el-form-item>
        <el-form-item label="联系人" prop="contactPerson">
          <el-input v-model="form.contactPerson" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" />
        </el-form-item>
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
import { getDonorList, addDonor, updateDonor, deleteDonor } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  donorType: ''
})

const form = reactive({
  id: null,
  donorType: 'personal',
  companyName: '',
  contactPerson: '',
  contactPhone: ''
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await getDonorList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, {
    id: null,
    donorType: 'personal',
    companyName: '',
    contactPerson: '',
    contactPhone: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (form.id) {
    await updateDonor(form)
  } else {
    await addDonor(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteDonor(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container {
  padding: 10px;
}

.search-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}
</style>
