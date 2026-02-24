<template>
  <el-card>
    <div style="display:flex;gap:10px;margin-bottom:15px">
      <el-input v-model="query.name" placeholder="站点名称" style="width:200px" clearable />
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="handleAdd">新增</el-button>
    </div>
    <el-table :data="tableData" v-loading="loading" stripe>
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column prop="name" label="站点名称" />
      <el-table-column prop="address" label="地址" />
      <el-table-column label="车辆" width="120"><template #default="{ row }">{{ row.currentCount }} / {{ row.capacity }}</template></el-table-column>
      <el-table-column label="状态" width="100"><template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : row.status === 2 ? 'warning' : 'danger'" size="small">{{ { 1: '运营中', 2: '维护中', 3: '已关闭' }[row.status] }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="160">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:15px;justify-content:end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" />
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑站点' : '新增站点'" width="500px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="站点名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="地址"><el-input v-model="form.address" /></el-form-item>
        <el-form-item label="经度"><el-input-number v-model="form.longitude" :precision="6" :step="0.001" /></el-form-item>
        <el-form-item label="纬度"><el-input-number v-model="form.latitude" :precision="6" :step="0.001" /></el-form-item>
        <el-form-item label="容量"><el-input-number v-model="form.capacity" :min="1" /></el-form-item>
        <el-form-item label="状态"><el-select v-model="form.status" style="width:100%"><el-option label="运营中" :value="1" /><el-option label="维护中" :value="2" /><el-option label="已关闭" :value="3" /></el-select></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getStationList, addStation, updateStation, deleteStation } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, name: '' })
const form = reactive({ id: null, name: '', address: '', longitude: null, latitude: null, capacity: 50, status: 1 })

const loadData = async () => { loading.value = true; try { const res = await getStationList(query); tableData.value = res.data.list; total.value = res.data.total } finally { loading.value = false } }
const handleAdd = () => { Object.assign(form, { id: null, name: '', address: '', longitude: null, latitude: null, capacity: 50, status: 1 }); dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }
const handleSubmit = async () => { if (form.id) { await updateStation(form.id, form) } else { await addStation(form) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleDelete = async (id) => { await deleteStation(id); ElMessage.success('删除成功'); loadData() }
onMounted(loadData)
</script>
