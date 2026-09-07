<template>
  <div>
    <el-card>
      <template #header>故障上报</template>
      <el-form :model="form" label-width="100px" style="max-width:600px" :rules="rules" ref="formRef">
        <el-form-item label="车辆编号" prop="bikeId"><el-input v-model="form.bikeId" placeholder="请输入车辆ID" /></el-form-item>
        <el-form-item label="故障类型" prop="type">
          <el-select v-model="form.type" style="width:100%" placeholder="请选择">
            <el-option label="车锁故障" :value="1" /><el-option label="轮胎破损" :value="2" /><el-option label="刹车失灵" :value="3" /><el-option label="车身损坏" :value="4" /><el-option label="其他" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="故障描述" prop="description"><el-input v-model="form.description" type="textarea" :rows="4" placeholder="请描述故障详情" /></el-form-item>
        <el-form-item><el-button type="primary" @click="handleSubmit" :loading="loading">提交上报</el-button></el-form-item>
      </el-form>
    </el-card>
    <el-card style="margin-top:15px">
      <template #header>我的上报记录</template>
      <el-table :data="myList" stripe>
        <el-table-column prop="bikeNo" label="车辆" width="120" />
        <el-table-column label="类型" width="100"><template #default="{ row }"><el-tag size="small">{{ faultTypeMap[row.type] }}</el-tag></template></el-table-column>
        <el-table-column prop="description" label="描述" />
        <el-table-column label="状态" width="90"><template #default="{ row }"><el-tag :type="{ 0: 'warning', 1: '', 2: 'success' }[row.status]" size="small">{{ { 0: '待处理', 1: '处理中', 2: '已处理' }[row.status] }}</el-tag></template></el-table-column>
        <el-table-column prop="handleResult" label="处理结果" />
        <el-table-column prop="createTime" label="上报时间" width="160" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { reportFault, getMyFaults } from '../../api'

const faultTypeMap = { 1: '车锁故障', 2: '轮胎破损', 3: '刹车失灵', 4: '车身损坏', 5: '其他' }
const formRef = ref()
const loading = ref(false)
const myList = ref([])
const form = reactive({ bikeId: '', type: null, description: '' })
const rules = { bikeId: [{ required: true, message: '请输入车辆ID' }], type: [{ required: true, message: '请选择故障类型' }] }

const handleSubmit = async () => {
  await formRef.value.validate()
  loading.value = true
  try { await reportFault(form); ElMessage.success('上报成功'); Object.assign(form, { bikeId: '', type: null, description: '' }); loadMyList() } finally { loading.value = false }
}

const loadMyList = async () => { const res = await getMyFaults({ pageNum: 1, pageSize: 20 }); myList.value = res.data.list }
onMounted(loadMyList)
</script>
