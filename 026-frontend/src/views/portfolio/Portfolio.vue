<template>
  <div>
    <el-button type="primary" @click="showAdd = true" v-if="isArtist">上传作品</el-button>
    
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="6" v-for="work in portfolios" :key="work.id">
        <el-card :body-style="{ padding: '0px' }">
          <img :src="work.imageUrl || 'https://via.placeholder.com/300'" style="width: 100%; height: 200px; object-fit: cover;">
          <div style="padding: 14px;">
            <span>{{ work.title }}</span>
            <p style="font-size: 12px; color: #999; margin-top: 5px;">{{ work.description }}</p>
            <div v-if="isMyWork(work)">
              <el-button size="small" type="danger" @click="deleteWork(work.id)">删除</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-dialog v-model="showAdd" title="上传作品" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" />
        </el-form-item>
        <el-form-item label="图片URL">
          <el-input v-model="form.imageUrl" />
        </el-form-item>
        <el-form-item label="分类">
          <el-input v-model="form.category" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAdd = false">取消</el-button>
        <el-button type="primary" @click="submitWork">上传</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import request from '../../utils/request'
import { ElMessage } from 'element-plus'

const user = JSON.parse(localStorage.getItem('user') || '{}')
const showAdd = ref(false)
const portfolios = ref([])
const myArtist = ref(null)

const form = reactive({
  artistId: null,
  title: '',
  description: '',
  imageUrl: '',
  category: ''
})

const isArtist = computed(() => user.role === 'ARTIST')

const isMyWork = (work) => {
  return myArtist.value && work.artistId === myArtist.value.id
}

const loadPortfolios = async () => {
  try {
    portfolios.value = await request.get('/portfolio/list')
  } catch (error) {
    console.error(error)
  }
}

const loadMyArtist = async () => {
  if (isArtist.value) {
    try {
      myArtist.value = await request.get(`/artist/user/${user.id}`)
      form.artistId = myArtist.value.id
    } catch (error) {
      console.error(error)
    }
  }
}

const submitWork = async () => {
  try {
    await request.post('/portfolio/add', form)
    ElMessage.success('上传成功')
    showAdd.value = false
    loadPortfolios()
  } catch (error) {
    console.error(error)
  }
}

const deleteWork = async (id) => {
  try {
    await request.delete(`/portfolio/${id}`)
    ElMessage.success('删除成功')
    loadPortfolios()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadMyArtist()
  loadPortfolios()
})
</script>
