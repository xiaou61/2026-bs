<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.status" placeholder="状态" clearable style="width:130px">
          <el-option label="待执行" value="pending" /><el-option label="执行中" value="processing" /><el-option label="已完成" value="completed" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="equipmentName" label="设备" width="160" />
        <el-table-column prop="planName" label="关联计划" width="180" />
        <el-table-column prop="maintainType" label="类型" width="80">
          <template #default="{row}"><el-tag>{{ {regular:'定期',repair:'维修',emergency:'紧急'}[row.maintainType] }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="content" label="内容" show-overflow-tooltip />
        <el-table-column prop="maintainer" label="维保人" width="90" />
        <el-table-column prop="cost" label="费用" width="90" />
        <el-table-column prop="status" label="状态" width="90">
          <template #default="{row}"><el-tag :type="{pending:'info',processing:'',completed:'success'}[row.status]">{{ {pending:'待执行',processing:'执行中',completed:'已完成'}[row.status] }}</el-tag></template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{row}">
            <el-dropdown trigger="click" @command="(cmd) => handleStatus(row.id, cmd)" v-if="row.status!=='completed'">
              <el-button link type="primary">更新状态</el-button>
              <template #dropdown><el-dropdown-menu>
                <el-dropdown-item command="pending">待执行</el-dropdown-item>
                <el-dropdown-item command="processing">执行中</el-dropdown-item>
                <el-dropdown-item command="completed">已完成</el-dropdown-item>
              </el-dropdown-menu></template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" style="margin-top:15px" @current-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" title="新增维保记录" width="600px">
      <el-form :model="form" ref="formRef" label-width="90px">
        <el-form-item label="设备" prop="equipmentId" :rules="[{required:true,message:'请选择'}]"><el-select v-model="form.equipmentId" style="width:100%"><el-option v-for="e in equipments" :key="e.id" :label="e.name" :value="e.id" /></el-select></el-form-item>
        <el-form-item label="关联计划"><el-select v-model="form.planId" style="width:100%" clearable><el-option v-for="p in plans" :key="p.id" :label="p.planName" :value="p.id" /></el-select></el-form-item>
        <el-form-item label="类型"><el-select v-model="form.maintainType" style="width:100%"><el-option label="定期保养" value="regular" /><el-option label="维修" value="repair" /><el-option label="紧急抢修" value="emergency" /></el-select></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="维保人"><el-input v-model="form.maintainer" /></el-form-item>
        <el-form-item label="费用"><el-input-number v-model="form.cost" :min="0" :precision="2" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMaintenanceRecordPage, addMaintenanceRecord, updateMaintenanceRecordStatus, getEquipmentPage, getMaintenancePlanPage } from '../../api'

const loading = ref(false); const tableData = ref([]); const total = ref(0); const dialogVisible = ref(false); const formRef = ref()
const equipments = ref([]); const plans = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, equipmentId: null, status: '' })
const form = reactive({ equipmentId: null, planId: null, maintainType: 'regular', content: '', maintainer: '', cost: 0 })

const loadData = async () => { loading.value = true; try { const res = await getMaintenanceRecordPage(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false } }
const handleAdd = () => { Object.assign(form, { equipmentId: null, planId: null, maintainType: 'regular', content: '', maintainer: '', cost: 0 }); dialogVisible.value = true }
const handleSubmit = async () => { await formRef.value.validate(); await addMaintenanceRecord(form); ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleStatus = async (id, status) => { await updateMaintenanceRecordStatus({ id, status }); ElMessage.success('状态已更新'); loadData() }
onMounted(async () => { const r1 = await getEquipmentPage({ pageNum: 1, pageSize: 100 }); equipments.value = r1.data.records; const r2 = await getMaintenancePlanPage({ pageNum: 1, pageSize: 200 }); plans.value = r2.data.records; loadData() })
</script>

<style scoped>.page-container { padding: 10px; } .search-bar { display: flex; gap: 10px; margin-bottom: 15px; }</style>
