<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-button type="success" @click="handleAdd">发起团购</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column label="商品" min-width="200">
          <template #default="{ row }">
            <div style="display: flex; align-items: center">
              <el-image :src="row.product?.cover" style="width: 50px; height: 50px; margin-right: 10px" fit="cover" />
              <span>{{ row.product?.name }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="groupPrice" label="团购价" width="100">
          <template #default="{ row }"><span style="color: #f56c6c">¥{{ row.groupPrice }}</span></template>
        </el-table-column>
        <el-table-column prop="minCount" label="成团人数" width="100" />
        <el-table-column prop="limitHours" label="时限(小时)" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.status === 0" type="info">未开始</el-tag>
            <el-tag v-else-if="row.status === 1" type="success">进行中</el-tag>
            <el-tag v-else type="danger">已结束</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button v-if="row.status === 1" link type="warning" @click="handleEnd(row.id)">结束</el-button>
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑活动' : '发起团购'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="商品" prop="productId">
          <el-select v-model="form.productId" filterable placeholder="选择商品">
            <el-option v-for="p in productOptions" :key="p.id" :label="p.name" :value="p.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="团购价" prop="groupPrice"><el-input-number v-model="form.groupPrice" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="成团人数" prop="minCount"><el-input-number v-model="form.minCount" :min="2" /></el-form-item>
        <el-form-item label="最大人数"><el-input-number v-model="form.maxCount" :min="form.minCount" /></el-form-item>
        <el-form-item label="成团时限"><el-input-number v-model="form.limitHours" :min="1" />小时</el-form-item>
        <el-form-item label="活动时间" prop="timeRange">
          <el-date-picker v-model="form.timeRange" type="datetimerange" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间" />
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
import { getActivityPage, addActivity, updateActivity, endActivity, getProductPage } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const productOptions = ref([])
const query = reactive({ pageNum: 1, pageSize: 10 })
const form = reactive({ id: null, productId: null, groupPrice: 0, minCount: 2, maxCount: 10, limitHours: 24, timeRange: [] })
const rules = {
  productId: [{ required: true, message: '请选择商品', trigger: 'change' }],
  groupPrice: [{ required: true, message: '请输入团购价', trigger: 'blur' }],
  minCount: [{ required: true, message: '请输入成团人数', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getActivityPage(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const loadProducts = async () => {
  const res = await getProductPage({ pageNum: 1, pageSize: 100, status: 1 })
  productOptions.value = res.data.records
}

const handleAdd = () => {
  Object.assign(form, { id: null, productId: null, groupPrice: 0, minCount: 2, maxCount: 10, limitHours: 24, timeRange: [] })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, { ...row, timeRange: [row.startTime, row.endTime] })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  const data = { ...form, startTime: form.timeRange[0], endTime: form.timeRange[1] }
  if (form.id) {
    await updateActivity(data)
  } else {
    await addActivity(data)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleEnd = async (id) => {
  await endActivity(id)
  ElMessage.success('已结束')
  loadData()
}

onMounted(() => {
  loadData()
  loadProducts()
})
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { margin-bottom: 15px; }
</style>
