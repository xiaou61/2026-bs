<template>
  <div class="page-container">
    <el-card v-if="isManager">
      <div class="search-bar">
        <el-select v-model="query.shopId" placeholder="所属门店" clearable>
          <el-option v-for="item in shopOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-input v-model="query.name" placeholder="店宠名称" clearable />
        <el-select v-model="query.interactionStatus" placeholder="互动状态" clearable>
          <el-option label="OPEN" value="OPEN" />
          <el-option label="REST" value="REST" />
        </el-select>
        <el-button type="primary" @click="loadManagerData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增店宠</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="petNo" label="店宠编号" min-width="150" />
        <el-table-column prop="name" label="店宠名称" min-width="120" />
        <el-table-column prop="shopName" label="所属门店" min-width="150" />
        <el-table-column prop="petType" label="类型" min-width="100" />
        <el-table-column prop="breed" label="品种" min-width="120" />
        <el-table-column prop="personality" label="性格" min-width="140" />
        <el-table-column prop="starLevel" label="星级" min-width="80" />
        <el-table-column prop="interactionStatus" label="互动状态" min-width="100" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-row v-else :gutter="16">
      <el-col v-for="item in publicList" :key="item.id" :span="8">
        <el-card class="pet-card" shadow="hover">
          <h3>{{ item.name }}</h3>
          <p>门店：{{ item.shopName || '-' }}</p>
          <p>类型：{{ item.petType || '-' }} / {{ item.breed || '-' }}</p>
          <p>性格：{{ item.personality || '-' }}</p>
          <p>健康：{{ item.healthStatus || '-' }}</p>
          <p>互动状态：{{ item.interactionStatus }}</p>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑店宠' : '新增店宠'" width="660px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="90px">
        <el-form-item label="店宠编号" prop="petNo">
          <el-input v-model="form.petNo" />
        </el-form-item>
        <el-form-item label="店宠名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="所属门店" prop="shopId">
          <el-select v-model="form.shopId" style="width: 100%">
            <el-option v-for="item in shopOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="form.petType" style="width: 100%">
            <el-option label="CAT" value="CAT" />
            <el-option label="DOG" value="DOG" />
            <el-option label="RABBIT" value="RABBIT" />
            <el-option label="OTHER" value="OTHER" />
          </el-select>
        </el-form-item>
        <el-form-item label="品种">
          <el-input v-model="form.breed" />
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="form.gender" />
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number v-model="form.age" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="性格">
          <el-input v-model="form.personality" />
        </el-form-item>
        <el-form-item label="健康情况">
          <el-input v-model="form.healthStatus" />
        </el-form-item>
        <el-form-item label="星级">
          <el-input-number v-model="form.starLevel" :min="1" :max="5" style="width: 100%" />
        </el-form-item>
        <el-form-item label="互动状态">
          <el-select v-model="form.interactionStatus" style="width: 100%">
            <el-option label="OPEN" value="OPEN" />
            <el-option label="REST" value="REST" />
          </el-select>
        </el-form-item>
        <el-form-item label="头像地址">
          <el-input v-model="form.avatar" />
        </el-form-item>
        <el-form-item label="介绍">
          <el-input v-model="form.introduction" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deletePet, getPetList, getPetPublicList, getShopOptions, savePet } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isManager = computed(() => ['ADMIN', 'STAFF'].includes((userStore.user?.role || '').toUpperCase()))

const loading = ref(false)
const tableData = ref([])
const publicList = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const shopOptions = ref([])
const query = reactive({
  pageNum: 1,
  pageSize: 10,
  shopId: null,
  name: '',
  interactionStatus: ''
})
const form = reactive({
  id: null,
  petNo: '',
  shopId: null,
  name: '',
  petType: 'CAT',
  breed: '',
  gender: '',
  age: 1,
  personality: '',
  healthStatus: '',
  starLevel: 5,
  interactionStatus: 'OPEN',
  avatar: '',
  introduction: ''
})
const rules = {
  petNo: [{ required: true, message: '请输入店宠编号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入店宠名称', trigger: 'blur' }],
  shopId: [{ required: true, message: '请选择门店', trigger: 'change' }]
}

const loadOptions = async () => {
  const res = await getShopOptions()
  shopOptions.value = res.data || []
}

const loadManagerData = async () => {
  loading.value = true
  try {
    const res = await getPetList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const loadPublicData = async () => {
  const res = await getPetPublicList()
  publicList.value = res.data || []
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    petNo: '',
    shopId: null,
    name: '',
    petType: 'CAT',
    breed: '',
    gender: '',
    age: 1,
    personality: '',
    healthStatus: '',
    starLevel: 5,
    interactionStatus: 'OPEN',
    avatar: '',
    introduction: ''
  })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await savePet(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadManagerData()
}

const handleDelete = async id => {
  await deletePet(id)
  ElMessage.success('删除成功')
  loadManagerData()
}

onMounted(async () => {
  await loadOptions()
  if (isManager.value) {
    await loadManagerData()
  } else {
    await loadPublicData()
  }
})
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}

.pet-card h3 {
  margin: 0 0 10px;
  color: #294234;
}

.pet-card p {
  margin: 6px 0;
  color: #667085;
}
</style>
