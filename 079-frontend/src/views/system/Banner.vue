<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-button type="success" @click="openDialog()">新增轮播图</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading">
        <el-table-column label="图片" width="200">
          <template #default="{ row }">
            <el-image :src="row.imageUrl" style="width: 150px; height: 80px" fit="cover" />
          </template>
        </el-table-column>
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="linkUrl" label="链接" />
        <el-table-column prop="sort" label="排序" width="80" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-switch :model-value="row.status === 1" @change="handleStatus(row)" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button link @click="openDialog(row)">编辑</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑轮播图' : '新增轮播图'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="图片">
          <el-upload :action="uploadUrl" :headers="uploadHeaders" :show-file-list="false" :on-success="handleUpload">
            <el-image v-if="form.imageUrl" :src="form.imageUrl" style="width: 200px; height: 100px" fit="cover" />
            <el-button v-else>点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="链接"><el-input v-model="form.linkUrl" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sort" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { getBannerList, addBanner, updateBanner, deleteBanner } from '../../api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const form = reactive({ id: null, title: '', imageUrl: '', linkUrl: '', sort: 0 })

const uploadUrl = '/api/upload/image'
const uploadHeaders = computed(() => ({ token: localStorage.getItem('token') }))

const loadData = async () => {
  loading.value = true
  try {
    const res = await getBannerList()
    tableData.value = res.data
  } finally {
    loading.value = false
  }
}

const openDialog = (row = null) => {
  if (row) {
    Object.assign(form, row)
  } else {
    Object.assign(form, { id: null, title: '', imageUrl: '', linkUrl: '', sort: 0 })
  }
  dialogVisible.value = true
}

const handleUpload = (res) => {
  if (res.code === 200) {
    form.imageUrl = res.data
  }
}

const handleSubmit = async () => {
  if (form.id) {
    await updateBanner(form)
  } else {
    await addBanner(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleStatus = async (row) => {
  await updateBanner({ ...row, status: row.status === 1 ? 0 : 1 })
  loadData()
}

const handleDelete = async (id) => {
  await deleteBanner(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { margin-bottom: 15px; }
</style>
