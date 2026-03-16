<template>
  <el-card>
    <div class="toolbar">
      <el-input v-model="query.title" placeholder="壁纸标题" style="width: 220px" clearable />
      <el-select v-model="query.categoryId" placeholder="分类" style="width: 160px" clearable>
        <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-select v-model="query.auditStatus" placeholder="审核" style="width: 140px" clearable>
        <el-option label="待审核" :value="0" />
        <el-option label="已通过" :value="1" />
        <el-option label="已驳回" :value="2" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="handleAdd">新增壁纸</el-button>
    </div>
    <el-table :data="tableData">
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column label="预览" width="120">
        <template #default="{ row }"><img :src="row.coverUrl || row.imageUrl" class="thumb"></template>
      </el-table-column>
      <el-table-column prop="title" label="标题" min-width="200" />
      <el-table-column label="分类" width="140">
        <template #default="{ row }">{{ categoryName(row.categoryId) }}</template>
      </el-table-column>
      <el-table-column prop="auditStatus" label="审核" width="100">
        <template #default="{ row }">
          <el-tag :type="auditType(row.auditStatus)">{{ auditText(row.auditStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="publishStatus" label="上架" width="100">
        <template #default="{ row }">
          <el-tag :type="row.publishStatus === 1 ? 'success' : 'info'">{{ row.publishStatus === 1 ? '已上架' : '未上架' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="downloadCount" label="下载" width="80" />
      <el-table-column label="操作" width="280">
        <template #default="{ row }">
          <el-button text type="primary" @click="handleEdit(row.id)">编辑</el-button>
          <el-button text type="success" @click="handlePublish(row, 1)">上架</el-button>
          <el-button text type="warning" @click="handlePublish(row, 0)">下架</el-button>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑壁纸' : '新增壁纸'" width="860px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="标题" prop="title"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="副标题"><el-input v-model="form.subtitle" /></el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="form.categoryId" style="width: 100%">
            <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="标签">
          <el-select v-model="form.tagIds" multiple style="width: 100%">
            <el-option v-for="item in tagOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="类型">
          <el-radio-group v-model="form.wallpaperType">
            <el-radio label="pc">桌面</el-radio>
            <el-radio label="mobile">手机</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="分辨率"><el-input v-model="form.resolution" /></el-form-item>
        <el-form-item label="主色"><el-input v-model="form.colorHex" /></el-form-item>
        <el-form-item label="精选">
          <el-radio-group v-model="form.featured">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="封面图">
          <el-upload :show-file-list="false" :auto-upload="false" :on-change="(file) => handleUpload(file, 'coverUrl')">
            <el-button>上传封面</el-button>
          </el-upload>
          <el-input v-model="form.coverUrl" style="margin-top: 10px" />
        </el-form-item>
        <el-form-item label="原图" prop="imageUrl">
          <el-upload :show-file-list="false" :auto-upload="false" :on-change="(file) => handleUpload(file, 'imageUrl')">
            <el-button type="primary" plain>上传原图</el-button>
          </el-upload>
          <el-input v-model="form.imageUrl" style="margin-top: 10px" />
        </el-form-item>
        <el-form-item label="预览图">
          <el-upload :show-file-list="false" :auto-upload="false" :on-change="(file) => handleUpload(file, 'previewUrl')">
            <el-button>上传预览</el-button>
          </el-upload>
          <el-input v-model="form.previewUrl" style="margin-top: 10px" />
        </el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" :rows="4" /></el-form-item>
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
import { addWallpaper, deleteWallpaper, getCategoryEnabled, getTagEnabled, getWallpaperDetail, getWallpaperList, publishWallpaper, updateWallpaper, uploadImage } from '../../api'

const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const categoryOptions = ref([])
const tagOptions = ref([])

const query = reactive({ pageNum: 1, pageSize: 10, title: '', categoryId: null, auditStatus: null })
const form = reactive({
  id: null,
  title: '',
  subtitle: '',
  categoryId: null,
  tagIds: [],
  wallpaperType: 'pc',
  resolution: '',
  colorHex: '',
  coverUrl: '',
  imageUrl: '',
  previewUrl: '',
  description: '',
  featured: 0
})

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
  imageUrl: [{ required: true, message: '请上传原图', trigger: 'blur' }]
}

const auditText = (status) => (status === 1 ? '已通过' : status === 2 ? '已驳回' : '待审核')
const auditType = (status) => (status === 1 ? 'success' : status === 2 ? 'danger' : 'warning')
const categoryName = (id) => categoryOptions.value.find(item => item.id === id)?.name || '-'

const resetForm = () => {
  Object.assign(form, {
    id: null,
    title: '',
    subtitle: '',
    categoryId: null,
    tagIds: [],
    wallpaperType: 'pc',
    resolution: '',
    colorHex: '',
    coverUrl: '',
    imageUrl: '',
    previewUrl: '',
    description: '',
    featured: 0
  })
}

const loadBase = async () => {
  const [categoryRes, tagRes] = await Promise.all([getCategoryEnabled(), getTagEnabled()])
  categoryOptions.value = categoryRes.data || []
  tagOptions.value = tagRes.data || []
}

const loadData = async () => {
  const res = await getWallpaperList(query)
  tableData.value = res.data.records || []
  total.value = res.data.total || 0
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = async (id) => {
  const res = await getWallpaperDetail(id)
  Object.assign(form, {
    ...(res.data.wallpaper || {}),
    tagIds: (res.data.tags || []).map(item => item.id)
  })
  dialogVisible.value = true
}

const handleUpload = async (uploadFile, field) => {
  const res = await uploadImage(uploadFile.raw)
  form[field] = res.data.url
  ElMessage.success('上传成功')
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) await updateWallpaper(form)
  else await addWallpaper(form)
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handlePublish = async (row, status) => {
  await publishWallpaper(row.id, status)
  ElMessage.success('状态更新成功')
  loadData()
}

const handleDelete = async (id) => {
  await deleteWallpaper(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadBase()
  await loadData()
})
</script>

<style scoped>
.toolbar { display: flex; gap: 12px; margin-bottom: 16px; }
.pager { display: flex; justify-content: center; margin-top: 18px; }
.thumb { width: 80px; height: 48px; object-fit: cover; border-radius: 8px; }
</style>
