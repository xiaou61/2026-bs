<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.orderNo" placeholder="工单号" style="width:200px" clearable />
        <el-select v-model="query.status" placeholder="状态" clearable style="width:130px">
          <el-option label="待排产" value="pending" /><el-option label="生产中" value="producing" />
          <el-option label="已完工" value="completed" /><el-option label="已取消" value="cancelled" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="orderNo" label="工单号" width="180" />
        <el-table-column prop="productName" label="产品" width="120" />
        <el-table-column prop="equipmentName" label="设备" width="140" />
        <el-table-column prop="planQuantity" label="计划数量" width="90" />
        <el-table-column prop="actualQuantity" label="实际数量" width="90" />
        <el-table-column prop="status" label="状态" width="90">
          <template #default="{row}"><el-tag :type="orderStatusType[row.status]">{{ orderStatusMap[row.status] }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="planStartDate" label="计划开始" width="110" />
        <el-table-column prop="planEndDate" label="计划结束" width="110" />
        <el-table-column label="操作" width="220">
          <template #default="{row}">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-dropdown trigger="click" @command="(cmd) => handleStatus(row.id, cmd)">
              <el-button link type="warning">状态</el-button>
              <template #dropdown><el-dropdown-menu>
                <el-dropdown-item command="pending">待排产</el-dropdown-item><el-dropdown-item command="producing">生产中</el-dropdown-item>
                <el-dropdown-item command="completed">已完工</el-dropdown-item><el-dropdown-item command="cancelled">已取消</el-dropdown-item>
              </el-dropdown-menu></template>
            </el-dropdown>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" style="margin-top:15px" @current-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑工单':'新增工单'" width="600px">
      <el-form :model="form" ref="formRef" label-width="90px">
        <el-form-item label="工单号" prop="orderNo" :rules="[{required:true,message:'请输入'}]"><el-input v-model="form.orderNo" /></el-form-item>
        <el-form-item label="产品"><el-select v-model="form.productId" style="width:100%"><el-option v-for="p in products" :key="p.id" :label="p.name" :value="p.id" /></el-select></el-form-item>
        <el-form-item label="设备"><el-select v-model="form.equipmentId" style="width:100%"><el-option v-for="e in equipments" :key="e.id" :label="e.name" :value="e.id" /></el-select></el-form-item>
        <el-form-item label="计划数量"><el-input-number v-model="form.planQuantity" :min="0" /></el-form-item>
        <el-form-item label="实际数量"><el-input-number v-model="form.actualQuantity" :min="0" /></el-form-item>
        <el-form-item label="计划日期">
          <el-date-picker v-model="form.planStartDate" type="date" value-format="YYYY-MM-DD" placeholder="开始" style="width:48%" />
          <el-date-picker v-model="form.planEndDate" type="date" value-format="YYYY-MM-DD" placeholder="结束" style="width:48%;margin-left:4%" />
        </el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getProductionOrderPage, addProductionOrder, updateProductionOrder, deleteProductionOrder, updateProductionOrderStatus, getProductList, getEquipmentPage } from '../../api'

const loading = ref(false); const tableData = ref([]); const total = ref(0); const dialogVisible = ref(false); const formRef = ref()
const products = ref([]); const equipments = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, orderNo: '', status: '' })
const form = reactive({ id: null, orderNo: '', productId: null, equipmentId: null, planQuantity: 0, actualQuantity: 0, planStartDate: '', planEndDate: '' })
const orderStatusMap = { pending: '待排产', producing: '生产中', completed: '已完工', cancelled: '已取消' }
const orderStatusType = { pending: 'info', producing: '', completed: 'success', cancelled: 'danger' }

const loadData = async () => { loading.value = true; try { const res = await getProductionOrderPage(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false } }
const handleAdd = () => { Object.assign(form, { id: null, orderNo: '', productId: null, equipmentId: null, planQuantity: 0, actualQuantity: 0, planStartDate: '', planEndDate: '' }); dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }
const handleSubmit = async () => { await formRef.value.validate(); if (form.id) { await updateProductionOrder(form) } else { await addProductionOrder(form) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleDelete = async (id) => { await deleteProductionOrder(id); ElMessage.success('删除成功'); loadData() }
const handleStatus = async (id, status) => { await updateProductionOrderStatus({ id, status }); ElMessage.success('状态已更新'); loadData() }
onMounted(async () => { const r1 = await getProductList(); products.value = r1.data; const r2 = await getEquipmentPage({ pageNum: 1, pageSize: 100 }); equipments.value = r2.data.records; loadData() })
</script>

<style scoped>.page-container { padding: 10px; } .search-bar { display: flex; gap: 10px; margin-bottom: 15px; }</style>
