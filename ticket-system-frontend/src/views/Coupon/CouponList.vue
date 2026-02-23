<template>
  <div class="coupon-list-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>优惠券管理</span>
          <el-button v-if="isAdmin" type="primary" @click="handleAdd">新增优惠券</el-button>
        </div>
      </template>

      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="优惠券名称">
          <el-input v-model="searchForm.name" placeholder="请输入优惠券名称" clearable />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="searchForm.type" placeholder="请选择类型" clearable>
            <el-option label="满减券" value="full_reduction" />
            <el-option label="折扣券" value="discount" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" style="width: 100%" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="优惠券名称" width="200" />
        <el-table-column prop="type" label="类型" width="120">
          <template #default="{ row }">
            {{ row.type === 'full_reduction' ? '满减券' : '折扣券' }}
          </template>
        </el-table-column>
        <el-table-column prop="value" label="优惠值" width="120">
          <template #default="{ row }">
            {{ row.type === 'full_reduction' ? `¥${row.value}` : `${row.value}折` }}
          </template>
        </el-table-column>
        <el-table-column prop="minAmount" label="最低消费" width="120">
          <template #default="{ row }">
            ¥{{ row.minAmount }}
          </template>
        </el-table-column>
        <el-table-column prop="totalCount" label="发放总量" width="100" />
        <el-table-column prop="usedCount" label="已使用" width="100" />
        <el-table-column prop="startTime" label="生效时间" width="180" />
        <el-table-column prop="endTime" label="失效时间" width="180" />
        <el-table-column v-if="isAdmin" label="操作" fixed="right" width="200">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
        <el-table-column v-else label="操作" fixed="right" width="120">
          <template #default="{ row }">
            <el-button type="success" size="small" @click="handleClaim(row)">领取</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="formData" label-width="100px">
        <el-form-item label="优惠券名称">
          <el-input v-model="formData.name" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="formData.type">
            <el-option label="满减券" value="full_reduction" />
            <el-option label="折扣券" value="discount" />
          </el-select>
        </el-form-item>
        <el-form-item label="优惠值">
          <el-input-number v-model="formData.value" :min="0" :precision="formData.type === 'full_reduction' ? 2 : 1" />
          <span style="margin-left: 10px;">{{ formData.type === 'full_reduction' ? '元' : '折' }}</span>
        </el-form-item>
        <el-form-item label="最低消费">
          <el-input-number v-model="formData.minAmount" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="发放总量">
          <el-input-number v-model="formData.totalCount" :min="1" />
        </el-form-item>
        <el-form-item label="生效时间">
          <el-date-picker v-model="formData.startTime" type="datetime" placeholder="选择日期时间" value-format="YYYY-MM-DD HH:mm:ss" />
        </el-form-item>
        <el-form-item label="失效时间">
          <el-date-picker v-model="formData.endTime" type="datetime" placeholder="选择日期时间" value-format="YYYY-MM-DD HH:mm:ss" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { couponApi } from '@/api'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.userInfo?.role === 'admin')

const searchForm = reactive({
  name: '',
  type: ''
})

const tableData = ref([])

const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)

const formData = reactive({
  id: null,
  name: '',
  type: 'full_reduction',
  value: 0,
  minAmount: 0,
  totalCount: 100,
  startTime: '',
  endTime: ''
})

const loadData = async () => {
  try {
    const res = await couponApi.getCouponList({
      current: pagination.current,
      size: pagination.size,
      ...searchForm
    })
    tableData.value = res.data.records
    pagination.total = res.data.total
  } catch (error) {
    ElMessage.error('加载数据失败')
  }
}

const handleSearch = () => {
  pagination.current = 1
  loadData()
}

const handleReset = () => {
  searchForm.name = ''
  searchForm.type = ''
  pagination.current = 1
  loadData()
}

const handleAdd = () => {
  dialogTitle.value = '新增优惠券'
  isEdit.value = false
  Object.assign(formData, {
    id: null,
    name: '',
    type: 'full_reduction',
    value: 0,
    minAmount: 0,
    totalCount: 100,
    startTime: '',
    endTime: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑优惠券'
  isEdit.value = true
  Object.assign(formData, row)
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该优惠券吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await couponApi.deleteCoupon(row.id)
      ElMessage.success('删除成功')
      loadData()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  })
}

const handleClaim = (row) => {
  ElMessageBox.confirm('确定要领取该优惠券吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'info'
  }).then(async () => {
    try {
      await couponApi.claimCoupon(row.id)
      ElMessage.success('领取成功')
      loadData()
    } catch (error) {
      ElMessage.error('领取失败')
    }
  })
}

const handleSubmit = async () => {
  try {
    if (isEdit.value) {
      await couponApi.updateCoupon(formData.id, formData)
    } else {
      await couponApi.addCoupon(formData)
    }
    ElMessage.success('操作成功')
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.coupon-list-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  font-weight: bold;
}

.search-form {
  margin-bottom: 20px;
}

.el-pagination {
  margin-top: 20px;
  justify-content: flex-end;
}
</style>
