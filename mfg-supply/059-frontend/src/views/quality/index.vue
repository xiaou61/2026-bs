<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.result" placeholder="检测结果" clearable style="width:130px">
          <el-option label="合格" value="passed" /><el-option label="不合格" value="failed" /><el-option label="待复检" value="recheck" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="orderNo" label="工单号" width="180" />
        <el-table-column prop="productName" label="产品" width="120" />
        <el-table-column prop="inspectQuantity" label="检测数量" width="90" />
        <el-table-column prop="qualifiedQuantity" label="合格数量" width="90" />
        <el-table-column prop="unqualifiedQuantity" label="不合格数量" width="100" />
        <el-table-column prop="result" label="结果" width="80">
          <template #default="{row}"><el-tag :type="{passed:'success',failed:'danger',recheck:'warning'}[row.result]">{{ {passed:'合格',failed:'不合格',recheck:'待复检'}[row.result] }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="inspector" label="检测员" width="90" />
        <el-table-column prop="remark" label="备注" show-overflow-tooltip />
        <el-table-column prop="inspectTime" label="检测时间" width="170" />
        <el-table-column label="操作" width="150">
          <template #default="{row}">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" style="margin-top:15px" @current-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑检测':'新增检测'" width="600px">
      <el-form :model="form" ref="formRef" label-width="90px">
        <el-form-item label="工单" prop="orderId"><el-select v-model="form.orderId" style="width:100%" filterable><el-option v-for="o in orders" :key="o.id" :label="o.orderNo" :value="o.id" /></el-select></el-form-item>
        <el-form-item label="产品" prop="productId"><el-select v-model="form.productId" style="width:100%"><el-option v-for="p in products" :key="p.id" :label="p.name" :value="p.id" /></el-select></el-form-item>
        <el-form-item label="检测数量"><el-input-number v-model="form.inspectQuantity" :min="0" /></el-form-item>
        <el-form-item label="合格数量"><el-input-number v-model="form.qualifiedQuantity" :min="0" /></el-form-item>
        <el-form-item label="不合格数量"><el-input-number v-model="form.unqualifiedQuantity" :min="0" /></el-form-item>
        <el-form-item label="结果"><el-select v-model="form.result" style="width:100%"><el-option label="合格" value="passed" /><el-option label="不合格" value="failed" /><el-option label="待复检" value="recheck" /></el-select></el-form-item>
        <el-form-item label="检测员"><el-input v-model="form.inspector" /></el-form-item>
        <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" :rows="2" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getQualityInspectionPage, addQualityInspection, updateQualityInspection, deleteQualityInspection, getProductionOrderPage, getProductList } from '../../api'

const loading = ref(false); const tableData = ref([]); const total = ref(0); const dialogVisible = ref(false); const formRef = ref()
const orders = ref([]); const products = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, result: '', orderId: null })
const form = reactive({ id: null, orderId: null, productId: null, inspectQuantity: 0, qualifiedQuantity: 0, unqualifiedQuantity: 0, result: 'passed', inspector: '', remark: '' })

const loadData = async () => { loading.value = true; try { const res = await getQualityInspectionPage(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false } }
const handleAdd = () => { Object.assign(form, { id: null, orderId: null, productId: null, inspectQuantity: 0, qualifiedQuantity: 0, unqualifiedQuantity: 0, result: 'passed', inspector: '', remark: '' }); dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }
const handleSubmit = async () => { await formRef.value.validate(); if (form.id) { await updateQualityInspection(form) } else { await addQualityInspection(form) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleDelete = async (id) => { await deleteQualityInspection(id); ElMessage.success('删除成功'); loadData() }
onMounted(async () => { const r1 = await getProductionOrderPage({ pageNum: 1, pageSize: 200 }); orders.value = r1.data.records; const r2 = await getProductList(); products.value = r2.data; loadData() })
</script>

<style scoped>.page-container { padding: 10px; } .search-bar { display: flex; gap: 10px; margin-bottom: 15px; }</style>
