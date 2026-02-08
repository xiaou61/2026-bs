<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.status" placeholder="状态" clearable style="width:130px">
          <el-option label="启用" value="active" /><el-option label="停用" value="inactive" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="equipmentName" label="设备" width="160" />
        <el-table-column prop="planName" label="计划名称" />
        <el-table-column prop="cycleDays" label="周期(天)" width="90" />
        <el-table-column prop="lastMaintainDate" label="上次维保" width="110" />
        <el-table-column prop="nextMaintainDate" label="下次维保" width="110" />
        <el-table-column prop="content" label="维保内容" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{row}"><el-tag :type="row.status==='active'?'success':'info'">{{ row.status==='active'?'启用':'停用' }}</el-tag></template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{row}">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" style="margin-top:15px" @current-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑计划':'新增计划'" width="600px">
      <el-form :model="form" ref="formRef" label-width="100px">
        <el-form-item label="设备" prop="equipmentId" :rules="[{required:true,message:'请选择'}]"><el-select v-model="form.equipmentId" style="width:100%"><el-option v-for="e in equipments" :key="e.id" :label="e.name" :value="e.id" /></el-select></el-form-item>
        <el-form-item label="计划名称" prop="planName" :rules="[{required:true,message:'请输入'}]"><el-input v-model="form.planName" /></el-form-item>
        <el-form-item label="周期(天)"><el-input-number v-model="form.cycleDays" :min="1" /></el-form-item>
        <el-form-item label="上次维保"><el-date-picker v-model="form.lastMaintainDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></el-form-item>
        <el-form-item label="下次维保"><el-date-picker v-model="form.nextMaintainDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></el-form-item>
        <el-form-item label="维保内容"><el-input v-model="form.content" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="状态"><el-select v-model="form.status" style="width:100%"><el-option label="启用" value="active" /><el-option label="停用" value="inactive" /></el-select></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMaintenancePlanPage, addMaintenancePlan, updateMaintenancePlan, deleteMaintenancePlan, getEquipmentPage } from '../../api'

const loading = ref(false); const tableData = ref([]); const total = ref(0); const dialogVisible = ref(false); const formRef = ref()
const equipments = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, equipmentId: null, status: '' })
const form = reactive({ id: null, equipmentId: null, planName: '', cycleDays: 30, lastMaintainDate: '', nextMaintainDate: '', content: '', status: 'active' })

const loadData = async () => { loading.value = true; try { const res = await getMaintenancePlanPage(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false } }
const handleAdd = () => { Object.assign(form, { id: null, equipmentId: null, planName: '', cycleDays: 30, lastMaintainDate: '', nextMaintainDate: '', content: '', status: 'active' }); dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }
const handleSubmit = async () => { await formRef.value.validate(); if (form.id) { await updateMaintenancePlan(form) } else { await addMaintenancePlan(form) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleDelete = async (id) => { await deleteMaintenancePlan(id); ElMessage.success('删除成功'); loadData() }
onMounted(async () => { const res = await getEquipmentPage({ pageNum: 1, pageSize: 100 }); equipments.value = res.data.records; loadData() })
</script>

<style scoped>.page-container { padding: 10px; } .search-bar { display: flex; gap: 10px; margin-bottom: 15px; }</style>
