<template>
  <div class="page-container">
    <el-card v-if="isManager">
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="菜单名称" clearable />
        <el-select v-model="query.categoryId" placeholder="分类" clearable>
          <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable>
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadManagerData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增菜单</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="menuNo" label="菜单编号" min-width="150" />
        <el-table-column prop="name" label="菜单名称" min-width="160" />
        <el-table-column prop="categoryName" label="分类" min-width="120" />
        <el-table-column prop="taste" label="风味" min-width="120" />
        <el-table-column prop="price" label="售价" min-width="90" />
        <el-table-column prop="stock" label="库存" min-width="90" />
        <el-table-column prop="recommendFlag" label="推荐" min-width="80">
          <template #default="{ row }">
            <el-tag :type="row.recommendFlag === 1 ? 'success' : 'info'">{{ row.recommendFlag === 1 ? '推荐' : '普通' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" min-width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
          </template>
        </el-table-column>
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
        <el-card class="menu-card" shadow="hover">
          <h3>{{ item.name }}</h3>
          <p>分类：{{ item.categoryName || '-' }}</p>
          <p>风味：{{ item.taste || '-' }}</p>
          <p>售价：¥{{ Number(item.price || 0).toFixed(2) }}</p>
          <p>库存：{{ item.stock || 0 }}</p>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑菜单' : '新增菜单'" width="640px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="90px">
        <el-form-item label="菜单编号" prop="menuNo">
          <el-input v-model="form.menuNo" />
        </el-form-item>
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="所属分类" prop="categoryId">
          <el-select v-model="form.categoryId" style="width: 100%">
            <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="风味">
          <el-input v-model="form.taste" />
        </el-form-item>
        <el-form-item label="图片地址">
          <el-input v-model="form.cover" />
        </el-form-item>
        <el-form-item label="售价">
          <el-input-number v-model="form.price" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="库存">
          <el-input-number v-model="form.stock" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="推荐位">
          <el-radio-group v-model="form.recommendFlag">
            <el-radio :label="1">推荐</el-radio>
            <el-radio :label="0">普通</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" />
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
import { deleteMenu, getCategoryOptions, getMenuList, getMenuPublicList, saveMenu } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isManager = computed(() => ['ADMIN', 'STAFF'].includes((userStore.user?.role || '').toUpperCase()))

const loading = ref(false)
const tableData = ref([])
const publicList = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const categoryOptions = ref([])
const query = reactive({
  pageNum: 1,
  pageSize: 10,
  name: '',
  categoryId: null,
  status: null
})
const form = reactive({
  id: null,
  menuNo: '',
  name: '',
  categoryId: null,
  taste: '',
  cover: '',
  price: 0,
  stock: 0,
  recommendFlag: 0,
  status: 1,
  description: ''
})
const rules = {
  menuNo: [{ required: true, message: '请输入菜单编号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }]
}

const loadOptions = async () => {
  const res = await getCategoryOptions()
  categoryOptions.value = res.data || []
}

const loadManagerData = async () => {
  loading.value = true
  try {
    const res = await getMenuList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const loadPublicData = async () => {
  const res = await getMenuPublicList()
  publicList.value = res.data || []
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    menuNo: '',
    name: '',
    categoryId: null,
    taste: '',
    cover: '',
    price: 0,
    stock: 0,
    recommendFlag: 0,
    status: 1,
    description: ''
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
  await saveMenu(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadManagerData()
}

const handleDelete = async id => {
  await deleteMenu(id)
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

.menu-card h3 {
  margin: 0 0 10px;
  color: #294234;
}

.menu-card p {
  margin: 6px 0;
  color: #667085;
}
</style>
