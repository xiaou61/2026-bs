<template>
  <div class="page-shell user-page">
    <div class="section-header">
      <div>
        <div class="section-eyebrow">我的投稿</div>
        <h1 class="display-title section-title">管理你提交到社区的作品</h1>
      </div>
      <el-button type="primary" round @click="handleAdd">新增投稿</el-button>
    </div>

    <div class="upload-list">
      <div class="upload-card glass-card" v-for="item in list" :key="item.id">
        <img :src="item.coverUrl || item.imageUrl" :alt="item.title">
        <div class="upload-info">
          <div class="upload-top">
            <div>
              <div class="upload-title">{{ item.title }}</div>
              <div class="upload-meta">{{ item.wallpaperType === 'mobile' ? '手机' : '桌面' }} · {{ item.resolution || '高清' }}</div>
            </div>
            <div class="status-pair">
              <el-tag :type="auditType(item.auditStatus)">{{ auditText(item.auditStatus) }}</el-tag>
              <el-tag :type="item.publishStatus === 1 ? 'success' : 'info'">{{ item.publishStatus === 1 ? '已上架' : '未上架' }}</el-tag>
            </div>
          </div>
          <div class="upload-actions">
            <el-button text type="primary" @click="handleEdit(item)">编辑</el-button>
          </div>
        </div>
      </div>
    </div>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑投稿' : '新增投稿'" width="760px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="副标题">
          <el-input v-model="form.subtitle" />
        </el-form-item>
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
        <el-form-item label="分辨率">
          <el-input v-model="form.resolution" placeholder="如 1920x1080" />
        </el-form-item>
        <el-form-item label="主色">
          <el-input v-model="form.colorHex" placeholder="如 #0f172a" />
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
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" :rows="4" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addWallpaper, getCategoryEnabled, getMyWallpaperList, getTagEnabled, getWallpaperDetail, updateWallpaper, uploadImage } from '../../api'

const formRef = ref()
const dialogVisible = ref(false)
const list = ref([])
const categoryOptions = ref([])
const tagOptions = ref([])

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
  description: ''
})

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
  imageUrl: [{ required: true, message: '请上传原图', trigger: 'blur' }]
}

const auditText = (status) => (status === 1 ? '已通过' : status === 2 ? '已驳回' : '待审核')
const auditType = (status) => (status === 1 ? 'success' : status === 2 ? 'danger' : 'warning')

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
    description: ''
  })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = async (row) => {
  const res = await getWallpaperDetail(row.id)
  Object.assign(form, {
    ...(res.data.wallpaper || row),
    tagIds: (res.data.tags || []).map(item => item.id)
  })
  dialogVisible.value = true
}

const handleUpload = async (uploadFile, field) => {
  const res = await uploadImage(uploadFile.raw)
  form[field] = res.data.url
  ElMessage.success('上传成功')
}

const loadBase = async () => {
  const [categoryRes, tagRes] = await Promise.all([getCategoryEnabled(), getTagEnabled()])
  categoryOptions.value = categoryRes.data || []
  tagOptions.value = tagRes.data || []
}

const loadData = async () => {
  const res = await getMyWallpaperList({ pageNum: 1, pageSize: 50 })
  list.value = res.data.records || []
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateWallpaper(form)
  } else {
    await addWallpaper(form)
  }
  ElMessage.success('提交成功')
  dialogVisible.value = false
  loadData()
}

onMounted(async () => {
  await loadBase()
  await loadData()
})
</script>

<style scoped>
.user-page {
  padding-top: 32px;
}

.section-title {
  margin: 10px 0 0;
  font-size: 42px;
}

.upload-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.upload-card {
  display: grid;
  grid-template-columns: 260px 1fr;
  overflow: hidden;
  border-radius: 24px;
}

.upload-card img {
  width: 100%;
  height: 220px;
  object-fit: cover;
}

.upload-info {
  padding: 22px;
}

.upload-top {
  display: flex;
  justify-content: space-between;
  gap: 16px;
}

.upload-title {
  font-size: 22px;
  font-weight: 600;
}

.upload-meta {
  margin-top: 10px;
  color: var(--text-sub);
}

.status-pair {
  display: flex;
  gap: 8px;
}

.upload-actions {
  margin-top: 20px;
}

@media (max-width: 900px) {
  .upload-card {
    grid-template-columns: 1fr;
  }

  .upload-top {
    flex-direction: column;
  }

  .status-pair {
    flex-wrap: wrap;
  }
}
</style>
