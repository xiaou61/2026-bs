<template>
  <el-card>
    <template #header>
      <div class="header">
        <span>轮播图管理</span>
        <el-button type="primary" @click="openDialog()">添加轮播图</el-button>
      </div>
    </template>
    <el-table :data="banners" stripe>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="image" label="图片" min-width="200">
        <template #default="{ row }">
          <el-image :src="row.image" style="width: 120px; height: 60px" fit="cover" />
        </template>
      </el-table-column>
      <el-table-column prop="url" label="链接" />
      <el-table-column prop="sort" label="排序" width="80" />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button link type="primary" @click="openDialog(row)">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑轮播图' : '添加轮播图'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="图片URL"><el-input v-model="form.image" /></el-form-item>
        <el-form-item label="跳转链接"><el-input v-model="form.url" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sort" :min="0" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '../api'

const banners = ref([])
const dialogVisible = ref(false)
const form = reactive({ id: null, title: '', image: '', url: '', sort: 0, status: 1 })

onMounted(() => loadData())

const loadData = async () => {
  banners.value = await api.getBannerList()
}

const openDialog = (row = null) => {
  if (row) {
    Object.assign(form, row)
  } else {
    Object.assign(form, { id: null, title: '', image: '', url: '', sort: 0, status: 1 })
  }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (form.id) {
    await api.updateBanner(form)
  } else {
    await api.addBanner(form)
  }
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async row => {
  await ElMessageBox.confirm('确定删除该轮播图？', '提示')
  await api.deleteBanner(row.id)
  ElMessage.success('删除成功')
  loadData()
}
</script>

<style scoped>
.header { display: flex; justify-content: space-between; align-items: center; }
</style>
