<template>
  <el-card>
    <div class="toolbar">
      <el-input v-model="query.title" placeholder="轮播标题" style="width: 220px" clearable />
      <el-select v-model="query.status" placeholder="状态" style="width: 140px" clearable>
        <el-option label="启用" :value="1" />
        <el-option label="停用" :value="0" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="handleAdd">新增轮播</el-button>
    </div>
    <el-table :data="tableData">
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column prop="title" label="标题" min-width="180" />
      <el-table-column label="图片" width="120">
        <template #default="{ row }"><img :src="row.imageUrl" class="thumb"></template>
      </el-table-column>
      <el-table-column prop="linkUrl" label="跳转链接" min-width="180" />
      <el-table-column prop="sort" label="排序" width="80" />
      <el-table-column prop="status" label="状态" width="90">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button text type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button text type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div class="pager">
      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        layout="total, prev, pager, next"
        :total="total"
        @current-change="loadData"
      />
    </div>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑轮播' : '新增轮播'" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="标题" prop="title"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="图片" prop="imageUrl">
          <el-upload :show-file-list="false" :auto-upload="false" :on-change="handleUpload">
            <el-button>上传图片</el-button>
          </el-upload>
          <el-input v-model="form.imageUrl" style="margin-top: 10px" />
        </el-form-item>
        <el-form-item label="跳转链接"><el-input v-model="form.linkUrl" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sort" :min="0" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addBanner, deleteBanner, getBannerList, updateBanner, uploadImage } from '../../api'

const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({ pageNum: 1, pageSize: 10, title: '', status: null })
const form = reactive({ id: null, title: '', imageUrl: '', linkUrl: '', sort: 0, status: 1 })

const rules = {
  title: [{ required: true, message: '请输入轮播标题', trigger: 'blur' }],
  imageUrl: [{ required: true, message: '请上传轮播图', trigger: 'blur' }]
}

const resetForm = () => Object.assign(form, { id: null, title: '', imageUrl: '', linkUrl: '', sort: 0, status: 1 })
const handleAdd = () => { resetForm(); dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }

const loadData = async () => {
  const res = await getBannerList(query)
  tableData.value = res.data.records || []
  total.value = res.data.total || 0
}

const handleUpload = async (uploadFile) => {
  const res = await uploadImage(uploadFile.raw)
  form.imageUrl = res.data.url
  ElMessage.success('上传成功')
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) await updateBanner(form)
  else await addBanner(form)
  ElMessage.success('操作成功')
  dialogVisible.value = false
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
.toolbar { display: flex; gap: 12px; margin-bottom: 16px; }
.pager { display: flex; justify-content: center; margin-top: 18px; }
.thumb { width: 80px; height: 48px; object-fit: cover; border-radius: 8px; }
</style>
