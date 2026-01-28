<template>
  <div class="page-container">
    <div class="search-form">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="全部" clearable>
            <el-option label="待确认" :value="0" />
            <el-option label="已确认" :value="1" />
            <el-option label="已完成" :value="2" />
            <el-option label="已取消" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
      </el-form>
    </div>
    <div class="table-container">
      <div style="margin-bottom: 15px"><el-button type="primary" @click="handleAdd">新增预约</el-button></div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="expertName" label="专家" />
        <el-table-column prop="appointmentDate" label="预约日期" width="120" />
        <el-table-column prop="appointmentTime" label="预约时间" width="120" />
        <el-table-column prop="purpose" label="预约主题" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }"><el-tag :type="['warning', 'info', 'success', 'danger'][row.status]">{{ ['待确认', '已确认', '已完成', '已取消'][row.status] }}</el-tag></template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button link type="primary" v-if="row.status === 0" @click="confirmAppointment(row.id)">确认</el-button>
            <el-button link type="success" v-if="row.status === 1" @click="completeAppointment(row.id)">完成</el-button>
            <el-button link type="danger" v-if="row.status < 2" @click="cancelAppointment(row.id)">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-container"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" layout="total, prev, pager, next" @change="loadData" /></div>
    </div>
    <el-dialog v-model="dialogVisible" title="新增预约" width="500px">
      <el-form :model="form" ref="formRef" label-width="100px">
        <el-form-item label="专家ID"><el-input-number v-model="form.expertId" :min="1" /></el-form-item>
        <el-form-item label="预约日期"><el-date-picker v-model="form.appointmentDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item>
        <el-form-item label="预约时间"><el-input v-model="form.appointmentTime" placeholder="如: 09:00-11:00" /></el-form-item>
        <el-form-item label="预约主题"><el-input v-model="form.purpose" type="textarea" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitForm">确定</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getAppointmentPage, addAppointment, confirmAppointment as confirmApi, completeAppointment as completeApi, cancelAppointment as cancelApi } from '@/api/appointment'
import { ElMessage } from 'element-plus'
const loading = ref(false), tableData = ref([]), total = ref(0), dialogVisible = ref(false), formRef = ref()
const queryParams = reactive({ pageNum: 1, pageSize: 10, status: null }), form = ref({})
const loadData = async () => { loading.value = true; try { const res = await getAppointmentPage(queryParams); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false } }
const handleAdd = () => { form.value = {}; dialogVisible.value = true }
const submitForm = async () => { await addAppointment(form.value); ElMessage.success('预约成功'); dialogVisible.value = false; loadData() }
const confirmAppointment = async (id) => { await confirmApi(id); ElMessage.success('确认成功'); loadData() }
const completeAppointment = async (id) => { await completeApi(id); ElMessage.success('完成'); loadData() }
const cancelAppointment = async (id) => { await cancelApi(id); ElMessage.success('已取消'); loadData() }
onMounted(() => loadData())
</script>