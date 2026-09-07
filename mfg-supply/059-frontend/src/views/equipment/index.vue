<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="设备名称" style="width:180px" clearable />
        <el-select v-model="query.status" placeholder="状态" clearable style="width:130px">
          <el-option label="运行中" value="running" /><el-option label="停机" value="stopped" />
          <el-option label="维修中" value="repairing" /><el-option label="已报废" value="scrapped" />
        </el-select>
        <el-select v-model="query.categoryId" placeholder="设备分类" clearable style="width:150px">
          <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="code" label="设备编号" width="140" />
        <el-table-column prop="name" label="设备名称" />
        <el-table-column prop="categoryName" label="分类" width="100" />
        <el-table-column prop="model" label="规格型号" width="100" />
        <el-table-column prop="manufacturer" label="制造商" width="100" />
        <el-table-column prop="location" label="安装位置" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{row}">
            <el-tag :type="statusType[row.status]">{{ statusMap[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{row}">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-dropdown trigger="click" @command="(cmd) => handleStatus(row.id, cmd)">
              <el-button link type="warning">状态</el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="running">运行中</el-dropdown-item>
                  <el-dropdown-item command="stopped">停机</el-dropdown-item>
                  <el-dropdown-item command="repairing">维修中</el-dropdown-item>
                  <el-dropdown-item command="scrapped">已报废</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" style="margin-top:15px" @current-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑设备':'新增设备'" width="600px">
      <el-form :model="form" ref="formRef" label-width="90px">
        <el-form-item label="设备编号" prop="code" :rules="[{required:true,message:'请输入'}]"><el-input v-model="form.code" /></el-form-item>
        <el-form-item label="设备名称" prop="name" :rules="[{required:true,message:'请输入'}]"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="设备分类"><el-select v-model="form.categoryId" style="width:100%"><el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" /></el-select></el-form-item>
        <el-form-item label="规格型号"><el-input v-model="form.model" /></el-form-item>
        <el-form-item label="制造商"><el-input v-model="form.manufacturer" /></el-form-item>
        <el-form-item label="购买日期"><el-date-picker v-model="form.purchaseDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></el-form-item>
        <el-form-item label="安装位置"><el-input v-model="form.location" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getEquipmentPage, addEquipment, updateEquipment, deleteEquipment, updateEquipmentStatus, getEquipmentCategoryList } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const categories = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, name: '', status: '', categoryId: null })
const form = reactive({ id: null, code: '', name: '', categoryId: null, model: '', manufacturer: '', purchaseDate: '', location: '' })
const statusMap = { running: '运行中', stopped: '停机', repairing: '维修中', scrapped: '已报废' }
const statusType = { running: 'success', stopped: 'info', repairing: 'warning', scrapped: 'danger' }

const loadCategories = async () => { const res = await getEquipmentCategoryList(); categories.value = res.data }
const loadData = async () => { loading.value = true; try { const res = await getEquipmentPage(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false } }
const handleAdd = () => { Object.assign(form, { id: null, code: '', name: '', categoryId: null, model: '', manufacturer: '', purchaseDate: '', location: '' }); dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }
const handleSubmit = async () => { await formRef.value.validate(); if (form.id) { await updateEquipment(form) } else { await addEquipment(form) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleDelete = async (id) => { await deleteEquipment(id); ElMessage.success('删除成功'); loadData() }
const handleStatus = async (id, status) => { await updateEquipmentStatus({ id, status }); ElMessage.success('状态已更新'); loadData() }
onMounted(() => { loadCategories(); loadData() })
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
