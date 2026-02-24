<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-input v-model="query.name" placeholder="影院名称" clearable style="width: 220px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="isAdmin" type="success" @click="handleAdd">新增影院</el-button>
      </div>
      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="影院名称" min-width="160" />
        <el-table-column prop="address" label="地址" min-width="200" />
        <el-table-column prop="phone" label="电话" width="140" />
        <el-table-column prop="businessHours" label="营业时间" width="140" />
        <el-table-column label="操作" width="170" v-if="isAdmin">
          <template #default="{ row }">
            <el-button link @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination class="mt16" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" @current-change="loadData" />
    </el-card>

    <el-dialog v-model="dialogVisible" title="影院信息" width="560px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="影院名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="地址"><el-input v-model="form.address" /></el-form-item>
        <el-form-item label="电话"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="营业时间"><el-input v-model="form.businessHours" /></el-form-item>
        <el-form-item label="设施"><el-input v-model="form.facilities" type="textarea" /></el-form-item>
        <el-form-item label="状态"><el-radio-group v-model="form.status"><el-radio :label="1">启用</el-radio><el-radio :label="0">停用</el-radio></el-radio-group></el-form-item>
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
import { useUserStore } from '../../store/user'
import { deleteCinema, getCinemaList, getPublicCinemaList, saveCinema } from '../../api'

const userStore = useUserStore()
const isAdmin = computed(() => (userStore.user?.role || '').toUpperCase() === 'ADMIN')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)

const query = reactive({ pageNum: 1, pageSize: 10, name: '' })
const form = reactive({ id: null, name: '', address: '', phone: '', businessHours: '09:00-23:00', facilities: '', status: 1 })

const loadData = async () => {
  loading.value = true
  try {
    if (isAdmin.value) {
      const res = await getCinemaList(query)
      tableData.value = res.data.records || []
      total.value = res.data.total || 0
    } else {
      const res = await getPublicCinemaList()
      tableData.value = res.data || []
      total.value = tableData.value.length
    }
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, name: '', address: '', phone: '', businessHours: '09:00-23:00', facilities: '', status: 1 })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await saveCinema(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteCinema(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
}

.mt16 {
  margin-top: 16px;
}
</style>
