<template>
  <div class="publish-house">
    <h2>{{ isEdit ? '编辑房源' : '发布房源' }}</h2>
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" style="max-width: 700px">
      <el-form-item label="房源标题" prop="title">
        <el-input v-model="form.title" placeholder="例如：阳光花园精装两室一厅" />
      </el-form-item>
      <el-form-item label="详细地址" prop="address">
        <el-input v-model="form.address" placeholder="例如：北京市朝阳区xxx街道xxx小区" />
      </el-form-item>
      <el-form-item label="月租金" prop="price">
        <el-input-number v-model="form.price" :min="0" :step="100" />
        <span style="margin-left:8px">元/月</span>
      </el-form-item>
      <el-form-item label="面积" prop="area">
        <el-input-number v-model="form.area" :min="0" />
        <span style="margin-left:8px">㎡</span>
      </el-form-item>
      <el-form-item label="户型">
        <el-input-number v-model="form.roomCount" :min="1" :max="10" style="width:80px" />室
        <el-input-number v-model="form.hallCount" :min="0" :max="5" style="width:80px;margin-left:12px" />厅
        <el-input-number v-model="form.bathCount" :min="0" :max="5" style="width:80px;margin-left:12px" />卫
      </el-form-item>
      <el-form-item label="楼层">
        <el-input-number v-model="form.floor" :min="1" style="width:80px" />
        <span style="margin:0 8px">/</span>
        <el-input-number v-model="form.totalFloor" :min="1" style="width:80px" />层
      </el-form-item>
      <el-form-item label="朝向">
        <el-select v-model="form.orientation" style="width:150px">
          <el-option v-for="o in orientations" :key="o" :label="o" :value="o" />
        </el-select>
      </el-form-item>
      <el-form-item label="租赁方式">
        <el-radio-group v-model="form.rentType">
          <el-radio value="整租">整租</el-radio>
          <el-radio value="合租">合租</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="装修情况">
        <el-select v-model="form.decoration" style="width:150px">
          <el-option v-for="d in decorations" :key="d" :label="d" :value="d" />
        </el-select>
      </el-form-item>
      <el-form-item label="配套设施">
        <el-checkbox-group v-model="facilitiesList">
          <el-checkbox v-for="f in allFacilities" :key="f" :value="f">{{ f }}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="房源图片">
        <el-upload
          v-model:file-list="fileList"
          list-type="picture-card"
          :action="uploadUrl"
          :on-success="handleUploadSuccess"
          :on-remove="handleRemove"
          accept="image/*"
        >
          <el-icon><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="房源描述">
        <el-input v-model="form.description" type="textarea" rows="4" placeholder="描述房源特点、周边配套等" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :loading="submitting" @click="handleSubmit">{{ isEdit ? '保存' : '发布' }}</el-button>
        <el-button @click="$router.back()">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { houseApi } from '@/api'

const route = useRoute()
const router = useRouter()

const isEdit = computed(() => !!route.params.id)
const formRef = ref()
const submitting = ref(false)
const fileList = ref([])
const facilitiesList = ref([])
const uploadUrl = '/api/file/upload'

const orientations = ['朝南', '朝北', '朝东', '朝西', '南北通透', '东西通透']
const decorations = ['毛坯', '简装', '精装', '豪装']
const allFacilities = ['空调', '冰箱', '洗衣机', '热水器', '电视', 'WiFi', '床', '衣柜', '沙发', '天然气']

const form = reactive({
  title: '', address: '', price: 2000, area: 80,
  roomCount: 2, hallCount: 1, bathCount: 1,
  floor: 5, totalFloor: 20, orientation: '朝南',
  rentType: '整租', decoration: '精装',
  facilities: '', images: '', description: ''
})

const rules = {
  title: [{ required: true, message: '请输入房源标题', trigger: 'blur' }],
  address: [{ required: true, message: '请输入详细地址', trigger: 'blur' }],
  price: [{ required: true, message: '请输入月租金', trigger: 'blur' }],
  area: [{ required: true, message: '请输入面积', trigger: 'blur' }]
}

onMounted(async () => {
  if (isEdit.value) {
    const res = await houseApi.getDetail(route.params.id)
    Object.assign(form, res.data)
    facilitiesList.value = res.data.facilities?.split(',').filter(Boolean) || []
    if (res.data.images) {
      fileList.value = res.data.images.split(',').map((url, idx) => ({ name: `img${idx}`, url }))
    }
  }
})

const handleUploadSuccess = (res) => {
  if (res.code === 200) {
    fileList.value.push({ name: res.data.filename, url: res.data.url })
  }
}

const handleRemove = (file) => {
  const idx = fileList.value.findIndex(f => f.url === file.url)
  if (idx > -1) fileList.value.splice(idx, 1)
}

const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    submitting.value = true
    form.facilities = facilitiesList.value.join(',')
    form.images = fileList.value.map(f => f.url).join(',')
    
    if (isEdit.value) {
      await houseApi.update(route.params.id, form)
      ElMessage.success('保存成功')
    } else {
      await houseApi.publish(form)
      ElMessage.success('发布成功')
    }
    router.push('/landlord/houses')
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.publish-house {
  background: #fff;
  border-radius: 12px;
  padding: 30px;
}

.publish-house h2 {
  margin-bottom: 24px;
}
</style>
