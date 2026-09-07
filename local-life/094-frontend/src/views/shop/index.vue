<template>
  <div class="page-container">
    <el-card v-if="isManager">
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="门店名称" clearable />
        <el-select v-model="query.areaId" placeholder="区域" clearable>
          <el-option v-for="item in areaOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.status" placeholder="营业状态" clearable>
          <el-option label="OPEN" value="OPEN" />
          <el-option label="REST" value="REST" />
          <el-option label="CLOSED" value="CLOSED" />
        </el-select>
        <el-button type="primary" @click="loadManagerData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增门店</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="shopNo" label="门店编号" min-width="150" />
        <el-table-column prop="name" label="门店名称" min-width="160" />
        <el-table-column prop="areaName" label="所属区域" min-width="140" />
        <el-table-column prop="theme" label="主题风格" min-width="140" />
        <el-table-column prop="status" label="营业状态" min-width="100" />
        <el-table-column prop="openTime" label="营业时间" min-width="120">
          <template #default="{ row }">{{ row.openTime || '--' }} - {{ row.closeTime || '--' }}</template>
        </el-table-column>
        <el-table-column prop="managerName" label="负责人" min-width="100" />
        <el-table-column prop="perCapita" label="客单价" min-width="100" />
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
        <el-card class="shop-card" shadow="hover">
          <h3>{{ item.name }}</h3>
          <p>区域：{{ item.areaName || '-' }}</p>
          <p>主题：{{ item.theme || '-' }}</p>
          <p>营业：{{ item.openTime || '--' }} - {{ item.closeTime || '--' }}</p>
          <p>参考客单：¥{{ Number(item.perCapita || 0).toFixed(2) }}</p>
          <p>负责人：{{ item.managerName || '-' }}</p>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑门店' : '新增门店'" width="660px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="90px">
        <el-form-item label="门店编号" prop="shopNo">
          <el-input v-model="form.shopNo" />
        </el-form-item>
        <el-form-item label="门店名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="所属区域" prop="areaId">
          <el-select v-model="form.areaId" style="width: 100%">
            <el-option v-for="item in areaOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="主题风格">
          <el-input v-model="form.theme" />
        </el-form-item>
        <el-form-item label="营业开始">
          <el-input v-model="form.openTime" placeholder="如 10:00" />
        </el-form-item>
        <el-form-item label="营业结束">
          <el-input v-model="form.closeTime" placeholder="如 21:30" />
        </el-form-item>
        <el-form-item label="负责人">
          <el-input v-model="form.managerName" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.managerPhone" />
        </el-form-item>
        <el-form-item label="评分">
          <el-input-number v-model="form.score" :min="1" :max="5" style="width: 100%" />
        </el-form-item>
        <el-form-item label="客单价">
          <el-input-number v-model="form.perCapita" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="封面图">
          <el-input v-model="form.cover" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width: 100%">
            <el-option label="OPEN" value="OPEN" />
            <el-option label="REST" value="REST" />
            <el-option label="CLOSED" value="CLOSED" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" />
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
import { deleteShop, getAreaOptions, getShopList, getShopPublicList, saveShop } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isManager = computed(() => ['ADMIN', 'STAFF'].includes((userStore.user?.role || '').toUpperCase()))

const loading = ref(false)
const tableData = ref([])
const publicList = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const areaOptions = ref([])
const query = reactive({
  pageNum: 1,
  pageSize: 10,
  name: '',
  areaId: null,
  status: ''
})
const form = reactive({
  id: null,
  shopNo: '',
  areaId: null,
  name: '',
  theme: '',
  openTime: '10:00',
  closeTime: '21:30',
  status: 'OPEN',
  cover: '',
  managerName: '',
  managerPhone: '',
  score: 5,
  perCapita: 68,
  remark: ''
})
const rules = {
  shopNo: [{ required: true, message: '请输入门店编号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入门店名称', trigger: 'blur' }],
  areaId: [{ required: true, message: '请选择区域', trigger: 'change' }]
}

const loadOptions = async () => {
  const res = await getAreaOptions()
  areaOptions.value = res.data || []
}

const loadManagerData = async () => {
  loading.value = true
  try {
    const res = await getShopList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const loadPublicData = async () => {
  const res = await getShopPublicList()
  publicList.value = res.data || []
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    shopNo: '',
    areaId: null,
    name: '',
    theme: '',
    openTime: '10:00',
    closeTime: '21:30',
    status: 'OPEN',
    cover: '',
    managerName: '',
    managerPhone: '',
    score: 5,
    perCapita: 68,
    remark: ''
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
  await saveShop(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadManagerData()
}

const handleDelete = async id => {
  await deleteShop(id)
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

.shop-card h3 {
  margin: 0 0 10px;
  color: #294234;
}

.shop-card p {
  margin: 6px 0;
  color: #667085;
}
</style>
