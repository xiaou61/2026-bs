<template>
  <div class="repairs-page">
    <div class="page-header">
      <h2>我的报修</h2>
      <el-button type="primary" @click="showDialog = true"><el-icon><Plus /></el-icon>新建报修</el-button>
    </div>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="房源" min-width="180">
        <template #default="{ row }">{{ row.house?.title }}</template>
      </el-table-column>
      <el-table-column label="报修类型" width="100" prop="type" />
      <el-table-column label="问题描述" min-width="200" prop="description" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="处理结果" min-width="150" prop="result" />
      <el-table-column label="提交时间" width="170" prop="createTime" />
    </el-table>
    <el-pagination v-if="total > 0" v-model:current-page="page" :total="total" :page-size="10" layout="total, prev, pager, next" style="margin-top: 20px; justify-content: center" @current-change="loadData" />

    <el-dialog v-model="showDialog" title="新建报修" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="房源" required>
          <el-select v-model="form.houseId" placeholder="选择房源" style="width:100%">
            <el-option v-for="h in myHouses" :key="h.id" :label="h.title" :value="h.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="类型" required>
          <el-select v-model="form.type" style="width:100%">
            <el-option v-for="t in types" :key="t" :label="t" :value="t" />
          </el-select>
        </el-form-item>
        <el-form-item label="描述" required>
          <el-input v-model="form.description" type="textarea" rows="3" placeholder="描述问题详情" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="submitRepair">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { repairApi, contractApi } from '@/api'

const loading = ref(false)
const list = ref([])
const page = ref(1)
const total = ref(0)
const showDialog = ref(false)
const myHouses = ref([])
const types = ['水电', '门窗', '家电', '家具', '网络', '其他']

const form = reactive({ houseId: '', type: '水电', description: '' })

const statusText = (s) => ({ 0: '待处理', 1: '处理中', 2: '已完成' }[s])
const statusType = (s) => ({ 0: 'warning', 1: 'primary', 2: 'success' }[s])

onMounted(async () => {
  loadData()
  const res = await contractApi.getList({ page: 1, size: 100, status: 1 })
  myHouses.value = (res.data?.records || []).map(c => c.house).filter(Boolean)
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await repairApi.getList({ page: page.value, size: 10 })
    list.value = res.data?.records || []
    total.value = res.data?.total || 0
  } finally { loading.value = false }
}

const submitRepair = async () => {
  if (!form.houseId || !form.description) {
    return ElMessage.warning('请填写完整')
  }
  await repairApi.create(form)
  ElMessage.success('提交成功')
  showDialog.value = false
  form.houseId = ''
  form.description = ''
  loadData()
}
</script>

<style scoped>
.page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
</style>
