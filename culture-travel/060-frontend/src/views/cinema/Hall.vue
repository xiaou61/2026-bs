<template>
  <div>
    <el-card>
      <div style="display:flex;gap:10px;margin-bottom:15px">
        <el-select v-model="query.cinemaId" placeholder="选择影院" style="width:200px" clearable>
          <el-option v-for="c in cinemas" :key="c.id" :label="c.name" :value="c.id" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="cinemaName" label="所属影院" />
        <el-table-column prop="name" label="影厅名称" />
        <el-table-column prop="seatRows" label="行数" width="70" />
        <el-table-column prop="seatCols" label="列数" width="70" />
        <el-table-column prop="hallType" label="类型" width="100"><template #default="{ row }"><el-tag>{{ row.hallType }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top:15px;justify-content:flex-end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" @current-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑影厅' : '新增影厅'" width="500px">
      <el-form :model="form" ref="formRef" label-width="80px">
        <el-form-item label="影院" prop="cinemaId" :rules="[{required:true,message:'请选择影院'}]">
          <el-select v-model="form.cinemaId" style="width:100%"><el-option v-for="c in cinemas" :key="c.id" :label="c.name" :value="c.id" /></el-select>
        </el-form-item>
        <el-form-item label="厅名" prop="name" :rules="[{required:true,message:'请输入厅名'}]"><el-input v-model="form.name" /></el-form-item>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="行数"><el-input-number v-model="form.seatRows" :min="1" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="列数"><el-input-number v-model="form.seatCols" :min="1" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="类型"><el-select v-model="form.hallType" style="width:100%">
          <el-option label="普通厅" value="normal" /><el-option label="IMAX" value="IMAX" /><el-option label="巨幕" value="giant" /><el-option label="VIP" value="VIP" />
        </el-select></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getHallPage, getCinemaList, addHall, updateHall, deleteHall } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const cinemas = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, cinemaId: null })
const form = reactive({ id: null, cinemaId: null, name: '', seatRows: 8, seatCols: 10, hallType: 'normal', status: 1 })

const loadData = async () => { loading.value = true; try { const res = await getHallPage(query); tableData.value = res.data.list; total.value = res.data.total } finally { loading.value = false } }
const handleAdd = () => { Object.assign(form, { id: null, cinemaId: null, name: '', seatRows: 8, seatCols: 10, hallType: 'normal', status: 1 }); dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }
const handleSubmit = async () => { await formRef.value.validate(); form.id ? await updateHall(form) : await addHall(form); ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleDelete = async (id) => { await deleteHall(id); ElMessage.success('删除成功'); loadData() }
onMounted(async () => { const res = await getCinemaList(); cinemas.value = res.data; loadData() })
</script>
