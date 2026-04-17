<template>
  <div class="publish-page">
    <section class="panel form-panel">
      <div class="section-head">
        <div>
          <span class="eyebrow">{{ isEdit ? '编辑商品' : '发布商品' }}</span>
          <h2>{{ isEdit ? '更新你的商品信息' : '让你的闲置被更多同学看到' }}</h2>
          <p>目前采用图片链接方式管理商品图片，后续可以继续扩展为本地上传。</p>
        </div>
      </div>

      <el-form ref="formRef" :model="form" :rules="rules" label-position="top" class="form-grid">
        <div class="two-col">
          <el-form-item label="商品标题" prop="title">
            <el-input v-model="form.title" maxlength="100" show-word-limit placeholder="如：高数教材第七版" />
          </el-form-item>
          <el-form-item label="商品分类" prop="categoryId">
            <el-select v-model="form.categoryId" placeholder="请选择分类">
              <el-option
                v-for="category in categories"
                :key="category.id"
                :label="category.categoryName"
                :value="category.id"
              />
            </el-select>
          </el-form-item>
        </div>

        <el-form-item label="商品描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="6"
            maxlength="1000"
            show-word-limit
            placeholder="请尽量写清楚使用情况、购入时间、交易方式等"
          />
        </el-form-item>

        <div class="three-col">
          <el-form-item label="售价" prop="price">
            <el-input-number v-model="form.price" :min="0.01" :precision="2" class="full-width" />
          </el-form-item>
          <el-form-item label="原价">
            <el-input-number v-model="form.originalPrice" :min="0" :precision="2" class="full-width" />
          </el-form-item>
          <el-form-item label="成色" prop="condition">
            <el-select v-model="form.condition" placeholder="请选择成色">
              <el-option v-for="item in conditions" :key="item" :label="item" :value="item" />
            </el-select>
          </el-form-item>
        </div>

        <el-form-item label="图片链接">
          <div class="upload-tools">
            <el-upload
              :show-file-list="false"
              :http-request="handleCustomUpload"
              accept="image/*"
            >
              <el-button type="primary" plain :loading="uploading">上传本地图片</el-button>
            </el-upload>
            <span>或继续粘贴外部图片链接</span>
          </div>
          <el-input
            v-model="imageText"
            type="textarea"
            :rows="5"
            placeholder="每行一个图片 URL，最多 6 张"
          />
          <div class="tip">支持逗号或换行分隔，未填写时会使用默认占位图。</div>
          <div v-if="previewImages.length" class="image-pills">
            <button
              v-for="(image, index) in previewImages"
              :key="`${image}-${index}`"
              type="button"
              class="image-pill"
              @click="removeImageAt(index)"
            >
              {{ index + 1 }}. {{ image }}
            </button>
          </div>
        </el-form-item>

        <div class="actions">
          <el-button @click="router.push('/my-products')">返回我的发布</el-button>
          <el-button type="primary" :loading="submitting" @click="handleSubmit">
            {{ isEdit ? '保存修改' : '立即发布' }}
          </el-button>
        </div>
      </el-form>
    </section>

    <section class="panel preview-panel">
      <div class="section-head">
        <div>
          <span class="eyebrow">实时预览</span>
          <h3>{{ form.title || '你的商品标题会显示在这里' }}</h3>
        </div>
        <strong>{{ formatPrice(form.price) }}</strong>
      </div>

      <div class="preview-card">
        <img :src="previewImages[0]" :alt="form.title || '商品预览'" />
        <div class="preview-content">
          <div class="tags">
            <el-tag size="small">{{ selectedCategoryName }}</el-tag>
            <el-tag size="small" type="warning">{{ form.condition || '待选择成色' }}</el-tag>
          </div>
          <p>{{ form.description || '填写描述后，这里会展示给买家看到的商品介绍。' }}</p>
          <div class="thumb-list">
            <img v-for="(image, index) in previewImages" :key="`${image}-${index}`" :src="image" alt="preview" />
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { productApi } from '@/api/product'
import { uploadApi } from '@/api/upload'
import { useUserStore } from '@/stores/user'
import { fallbackImage, formatPrice, normalizeImageList } from '@/utils/market'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const formRef = ref()
const submitting = ref(false)
const uploading = ref(false)
const categories = ref([])
const imageText = ref('')

const conditions = ['全新', '九成新', '八成新', '七成新']

const form = reactive({
  categoryId: null,
  title: '',
  description: '',
  price: null,
  originalPrice: null,
  condition: ''
})

const rules = {
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
  title: [{ required: true, message: '请输入商品标题', trigger: 'blur' }],
  description: [{ required: true, message: '请输入商品描述', trigger: 'blur' }],
  price: [{ required: true, message: '请输入售价', trigger: 'change' }],
  condition: [{ required: true, message: '请选择成色', trigger: 'change' }]
}

const isEdit = computed(() => !!route.query.edit)

const previewImages = computed(() => {
  const list = normalizeImageList(imageText.value.replace(/\n/g, ',')).slice(0, 6)
  return list.length ? list : [fallbackImage(form.title)]
})

const selectedCategoryName = computed(() => {
  return categories.value.find((item) => item.id === form.categoryId)?.categoryName || '未选择分类'
})

const buildPayload = () => {
  return {
    ...form,
    images: getImageUrls()
  }
}

const getImageUrls = () => normalizeImageList(imageText.value.replace(/\n/g, ',')).slice(0, 6)

const loadCategories = async () => {
  const response = await productApi.getCategories()
  categories.value = response.data || []
}

const loadDetail = async () => {
  if (!isEdit.value) {
    return
  }

  const response = await productApi.getProductDetail(route.query.edit)
  const data = response.data || {}
  if (data.sellerId !== userStore.userInfo.id) {
    ElMessage.error('只能编辑自己发布的商品')
    router.push('/my-products')
    return
  }

  form.categoryId = data.categoryId
  form.title = data.title
  form.description = data.description
  form.price = Number(data.price)
  form.originalPrice = data.originalPrice ? Number(data.originalPrice) : null
  form.condition = data.condition
  imageText.value = normalizeImageList(data.imageList || data.images).join('\n')
}

const handleCustomUpload = async (option) => {
  if (getImageUrls().length >= 6) {
    ElMessage.warning('最多上传 6 张图片')
    return
  }

  uploading.value = true
  try {
    const response = await uploadApi.uploadImage(option.file)
    const imageUrls = [...getImageUrls(), response.data].slice(0, 6)
    imageText.value = imageUrls.join('\n')
    ElMessage.success('图片上传成功')
  } finally {
    uploading.value = false
  }
}

const removeImageAt = (index) => {
  const imageUrls = getImageUrls()
  imageUrls.splice(index, 1)
  imageText.value = imageUrls.join('\n')
}

const handleSubmit = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) {
    return
  }

  submitting.value = true
  try {
    const payload = buildPayload()
    if (isEdit.value) {
      await productApi.updateProduct(route.query.edit, payload)
      ElMessage.success('商品更新成功')
    } else {
      await productApi.publishProduct(payload)
      ElMessage.success('商品发布成功')
    }
    router.push('/my-products')
  } finally {
    submitting.value = false
  }
}

onMounted(async () => {
  if (!userStore.isLoggedIn) {
    router.push('/login')
    return
  }

  if (!userStore.userInfo.id) {
    await userStore.getUserInfo()
  }

  await loadCategories()
  await loadDetail()
})
</script>

<style scoped>
.publish-page {
  display: grid;
  grid-template-columns: minmax(0, 1.2fr) minmax(360px, 0.8fr);
  gap: 20px;
}

.panel {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 28px;
  border: 1px solid rgba(148, 163, 184, 0.16);
  padding: 24px;
}

.section-head {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 22px;
}

.eyebrow {
  display: inline-flex;
  padding: 6px 12px;
  border-radius: 999px;
  background: rgba(249, 115, 22, 0.12);
  color: #c2410c;
  margin-bottom: 12px;
}

.section-head h2,
.section-head h3 {
  font-size: 30px;
  line-height: 1.25;
  margin-bottom: 8px;
}

.section-head p {
  color: #64748b;
}

.form-grid {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.two-col,
.three-col {
  display: grid;
  gap: 18px;
}

.two-col {
  grid-template-columns: repeat(2, minmax(0, 1fr));
}

.three-col {
  grid-template-columns: repeat(3, minmax(0, 1fr));
}

.tip {
  color: #64748b;
  font-size: 13px;
  margin-top: 8px;
}

.upload-tools {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
  color: #64748b;
  font-size: 13px;
}

.image-pills {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-top: 12px;
}

.image-pill {
  text-align: left;
  border: 1px solid rgba(148, 163, 184, 0.18);
  background: rgba(248, 250, 252, 0.96);
  border-radius: 14px;
  padding: 10px 12px;
  color: #334155;
  cursor: pointer;
}

.actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 8px;
}

.preview-panel strong {
  color: #dc2626;
  font-size: 26px;
}

.preview-card {
  border-radius: 24px;
  overflow: hidden;
  background: #f8fafc;
  border: 1px solid rgba(148, 163, 184, 0.12);
}

.preview-card > img {
  width: 100%;
  height: 240px;
  object-fit: cover;
}

.preview-content {
  padding: 18px;
}

.tags {
  display: flex;
  gap: 8px;
  margin-bottom: 14px;
}

.preview-content p {
  color: #475569;
  line-height: 1.8;
  margin-bottom: 18px;
}

.thumb-list {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 10px;
}

.thumb-list img {
  width: 100%;
  height: 72px;
  object-fit: cover;
  border-radius: 14px;
}

.full-width {
  width: 100%;
}

@media (max-width: 1080px) {
  .publish-page {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 720px) {
  .two-col,
  .three-col {
    grid-template-columns: 1fr;
  }
}
</style>
