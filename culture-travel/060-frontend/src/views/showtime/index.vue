<template>
  <div>
    <el-card>
      <div style="display:flex;gap:10px;margin-bottom:15px">
        <el-date-picker v-model="query.showDate" type="date" value-format="YYYY-MM-DD" placeholder="选择日期" style="width:160px" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增排片</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="movieTitle" label="电影" />
        <el-table-column prop="cinemaName" label="影院" />
        <el-table-column prop="hallName" label="影厅" width="90" />
        <el-table-column prop="showDate" label="日期" width="110" />
        <el-table-column label="时间" width="130"><template #default="{ row }">{{ row.startTime }}-{{ row.endTime }}</template></el-table-column>
        <el-table-column prop="price" label="票价" width="80" />
        <el-table-column prop="availableSeats" label="余座" width="70" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top:15px;justify-content:flex-end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" @current-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑排片' : '新增排片'" width="550px">
      <el-form :model="form" ref="formRef" label-width="80px">
        <el-form-item label="电影" prop="movieId" :rules="[{required:true,message:'请选择电影'}]">
          <el-select v-model="form.movieId" filterable style="width:100%"><el-option v-for="m in movies" :key="m.id" :label="m.title" :value="m.id" /></el-select>
        </el-form-item>
        <el-form-item label="影院" prop="cinemaId" :rules="[{required:true,message:'请选择影院'}]">
          <el-select v-model="form.cinemaId" @change="loadHalls" style="width:100%"><el-option v-for="c in cinemas" :key="c.id" :label="c.name" :value="c.id" /></el-select>
        </el-form-item>
        <el-form-item label="影厅" prop="hallId" :rules="[{required:true,message:'请选择影厅'}]">
          <el-select v-model="form.hallId" style="width:100%"><el-option v-for="h in halls" :key="h.id" :label="h.name" :value="h.id" /></el-select>
        </el-form-item>
        <el-form-item label="日期" prop="showDate" :rules="[{required:true,message:'请选择日期'}]"><el-date-picker v-model="form.showDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></el-form-item>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="开始"><el-input v-model="form.startTime" placeholder="10:00" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="结束"><el-input v-model="form.endTime" placeholder="12:00" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="票价" prop="price" :rules="[{required:true,message:'请输入票价'}]"><el-input-number v-model="form.price" :min="1" :precision="2" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getShowtimePage, addShowtime, updateShowtime, deleteShowtime, getMoviePage, getCinemaList, getHallList } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const movies = ref([])
const cinemas = ref([])
const halls = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, showDate: '' })
const form = reactive({ id: null, movieId: null, cinemaId: null, hallId: null, showDate: '', startTime: '', endTime: '', price: 50, status: 1 })

const loadData = async () => { loading.value = true; try { const res = await getShowtimePage(query); tableData.value = res.data.list; total.value = res.data.total } finally { loading.value = false } }
const loadHalls = async () => { if (form.cinemaId) { const res = await getHallList(form.cinemaId); halls.value = res.data } }
const handleAdd = () => { Object.assign(form, { id: null, movieId: null, cinemaId: null, hallId: null, showDate: '', startTime: '', endTime: '', price: 50, status: 1 }); halls.value = []; dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); loadHalls(); dialogVisible.value = true }
const handleSubmit = async () => { await formRef.value.validate(); form.id ? await updateShowtime(form) : await addShowtime(form); ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleDelete = async (id) => { await deleteShowtime(id); ElMessage.success('删除成功'); loadData() }
onMounted(async () => {
  const m = await getMoviePage({ pageNum: 1, pageSize: 100 }); movies.value = m.data.list
  const c = await getCinemaList(); cinemas.value = c.data
  loadData()
})
</script>
