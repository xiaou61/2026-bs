<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.type" placeholder="类型" clearable style="width:120px">
          <el-option label="入库" value="in" /><el-option label="出库" value="out" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleStockIn">入库</el-button>
        <el-button type="warning" @click="handleStockOut">出库</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="materialName" label="物料" width="150" />
        <el-table-column prop="type" label="类型" width="80">
          <template #default="{row}"><el-tag :type="row.type==='in'?'success':'danger'">{{ row.type==='in'?'入库':'出库' }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="quantity" label="数量" width="100" />
        <el-table-column prop="reason" label="原因" />
        <el-table-column prop="operator" label="操作人" width="100" />
        <el-table-column prop="createTime" label="时间" width="170" />
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" style="margin-top:15px" @current-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="stockType==='in'?'物料入库':'物料出库'" width="500px">
      <el-form :model="form" ref="formRef" label-width="80px">
        <el-form-item label="物料" prop="materialId" :rules="[{required:true,message:'请选择物料'}]">
          <el-select v-model="form.materialId" style="width:100%" filterable>
            <el-option v-for="m in materials" :key="m.id" :label="`${m.name}(库存:${m.stockQuantity})`" :value="m.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="quantity" :rules="[{required:true,message:'请输入数量'}]"><el-input-number v-model="form.quantity" :min="0.01" :precision="2" /></el-form-item>
        <el-form-item label="原因"><el-input v-model="form.reason" /></el-form-item>
        <el-form-item label="操作人"><el-input v-model="form.operator" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMaterialStockPage, materialStockIn, materialStockOut, getMaterialPage } from '../../api'

const loading = ref(false); const tableData = ref([]); const total = ref(0); const dialogVisible = ref(false); const formRef = ref()
const materials = ref([]); const stockType = ref('in')
const query = reactive({ pageNum: 1, pageSize: 10, materialId: null, type: '' })
const form = reactive({ materialId: null, quantity: 0, reason: '', operator: '' })

const loadMaterials = async () => { const res = await getMaterialPage({ pageNum: 1, pageSize: 200 }); materials.value = res.data.records }
const loadData = async () => { loading.value = true; try { const res = await getMaterialStockPage(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false } }
const handleStockIn = () => { stockType.value = 'in'; Object.assign(form, { materialId: null, quantity: 0, reason: '', operator: '' }); dialogVisible.value = true }
const handleStockOut = () => { stockType.value = 'out'; Object.assign(form, { materialId: null, quantity: 0, reason: '', operator: '' }); dialogVisible.value = true }
const handleSubmit = async () => {
  await formRef.value.validate()
  if (stockType.value === 'in') { await materialStockIn(form) } else { await materialStockOut(form) }
  ElMessage.success('操作成功'); dialogVisible.value = false; loadData(); loadMaterials()
}
onMounted(() => { loadMaterials(); loadData() })
</script>

<style scoped>.page-container { padding: 10px; } .search-bar { display: flex; gap: 10px; margin-bottom: 15px; }</style>
