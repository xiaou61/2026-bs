<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>垃圾分类管理</span>
          <el-button type="primary" @click="showAddDialog">添加分类</el-button>
        </div>
      </template>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="name" label="分类名称" />
        <el-table-column prop="code" label="编码" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="price" label="回收单价(元/kg)" />
        <el-table-column prop="pointsRate" label="积分比例(/kg)" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button type="primary" link @click="editCategory(row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top: 16px;" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑分类' : '添加分类'" width="500">
      <el-form :model="form" label-width="100px">
        <el-form-item label="名称" required><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="编码" required><el-input v-model="form.code" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" /></el-form-item>
        <el-form-item label="回收单价"><el-input-number v-model="form.price" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="积分比例"><el-input-number v-model="form.pointsRate" :min="0" /></el-form-item>
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
import { getAdminCategories, saveCategory } from '@/api'
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
    const res: any = await getAdminCategories(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const showAddDialog = () => {
  form.value = { price: 0, pointsRate: 1 }
  dialogVisible.value = true
}

const editCategory = (row: any) => {
  form.value = { ...row }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await saveCategory(form.value)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

onMounted(loadData)
</script>
