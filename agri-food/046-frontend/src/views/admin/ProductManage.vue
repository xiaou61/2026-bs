<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>积分商品管理</span>
          <el-button type="primary" @click="showAddDialog">添加商品</el-button>
        </div>
      </template>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="name" label="商品名称" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="points" label="所需积分" />
        <el-table-column prop="stock" label="库存" />
        <el-table-column prop="exchangeCount" label="已兑换" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '上架' : '下架' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button type="primary" link @click="editProduct(row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top: 16px;" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑商品' : '添加商品'" width="500">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称" required><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" /></el-form-item>
        <el-form-item label="所需积分" required><el-input-number v-model="form.points" :min="1" /></el-form-item>
        <el-form-item label="库存" required><el-input-number v-model="form.stock" :min="0" /></el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
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
import { getAdminProducts, saveProduct } from '@/api'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const query = reactive({ current: 1, size: 10 })
const dialogVisible = ref(false)
const form = ref<any>({})

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getAdminProducts(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const showAddDialog = () => {
  form.value = { points: 100, stock: 100, status: 1 }
  dialogVisible.value = true
}

const editProduct = (row: any) => {
  form.value = { ...row }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await saveProduct(form.value)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

onMounted(loadData)
</script>
