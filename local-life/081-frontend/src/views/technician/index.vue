<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="技师姓名" style="width: 200px" />
        <el-select v-model="query.level" placeholder="等级" style="width: 120px" clearable>
          <el-option label="高级" value="高级" />
          <el-option label="中级" value="中级" />
          <el-option label="初级" value="初级" />
        </el-select>
        <el-select v-model="query.workStatus" placeholder="状态" style="width: 120px" clearable>
          <el-option label="在岗" :value="1" />
          <el-option label="休息" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="skillTags" label="技能标签" />
        <el-table-column prop="serviceArea" label="服务区域" />
        <el-table-column prop="level" label="等级" width="90" />
        <el-table-column prop="rating" label="评分" width="90" />
        <el-table-column prop="orderCount" label="接单量" width="100" />
        <el-table-column label="状态" width="90">
          <template #default="{ row }">
            <el-tag :type="row.workStatus === 1 ? 'success' : 'info'">{{ row.workStatus === 1 ? '在岗' : '休息' }}</el-tag>
          </template>
        </el-table-column>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑技师' : '新增技师'" width="600px">
      <el-form :model="form" label-width="90px">
        <el-row :gutter="12">
          <el-col :span="12"><el-form-item label="姓名"><el-input v-model="form.name" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="技能标签"><el-input v-model="form.skillTags" placeholder="多个用逗号分隔" /></el-form-item>
        <el-form-item label="服务区域"><el-input v-model="form.serviceArea" /></el-form-item>
        <el-row :gutter="12">
          <el-col :span="8"><el-form-item label="等级"><el-select v-model="form.level"><el-option label="高级" value="高级" /><el-option label="中级" value="中级" /><el-option label="初级" value="初级" /></el-select></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="评分"><el-input-number v-model="form.rating" :min="0" :max="5" :step="0.1" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="接单量"><el-input-number v-model="form.orderCount" :min="0" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="状态">
          <el-radio-group v-model="form.workStatus">
            <el-radio :label="1">在岗</el-radio>
            <el-radio :label="0">休息</el-radio>
          </el-radio-group>
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
import { getTechnicianList, addTechnician, updateTechnician, deleteTechnician } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  name: '',
  level: '',
  workStatus: null
})

const form = reactive({
  id: null,
  userId: null,
  name: '',
  phone: '',
  skillTags: '',
  serviceArea: '',
  level: '中级',
  workStatus: 1,
  rating: 5,
  orderCount: 0
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await getTechnicianList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, {
    id: null,
    userId: null,
    name: '',
    phone: '',
    skillTags: '',
    serviceArea: '',
    level: '中级',
    workStatus: 1,
    rating: 5,
    orderCount: 0
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (form.id) {
    await updateTechnician(form)
  } else {
    await addTechnician(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteTechnician(id)
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
