<template>
  <div>
    <el-card>
      <div style="display:flex;gap:10px;margin-bottom:15px">
        <el-input v-model="query.title" placeholder="搜索电影" style="width:200px" clearable />
        <el-select v-model="query.categoryId" placeholder="选择分类" style="width:150px" clearable>
          <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd" v-if="userStore.isAdmin()">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="title" label="电影名称" />
        <el-table-column prop="director" label="导演" width="120" />
        <el-table-column prop="categoryName" label="分类" width="80" />
        <el-table-column prop="duration" label="时长(分)" width="80" />
        <el-table-column prop="score" label="评分" width="70" />
        <el-table-column prop="status" label="状态" width="80"><template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '上映' : '下架' }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button link type="primary" @click="$router.push(`/movie/${row.id}`)">详情</el-button>
            <template v-if="userStore.isAdmin()">
              <el-button link type="warning" @click="handleEdit(row)">编辑</el-button>
              <el-popconfirm title="确认删除?" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm>
            </template>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top:15px;justify-content:flex-end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" @current-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑电影' : '新增电影'" width="600px">
      <el-form :model="form" ref="formRef" label-width="90px">
        <el-form-item label="电影名称" prop="title" :rules="[{required:true,message:'请输入名称'}]"><el-input v-model="form.title" /></el-form-item>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="导演"><el-input v-model="form.director" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="分类"><el-select v-model="form.categoryId" style="width:100%"><el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" /></el-select></el-form-item></el-col>
        </el-row>
        <el-form-item label="演员"><el-input v-model="form.actors" /></el-form-item>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="时长(分)"><el-input-number v-model="form.duration" :min="1" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="上映日期"><el-date-picker v-model="form.releaseDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="简介"><el-input v-model="form.description" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMoviePage, getCategoryList, addMovie, updateMovie, deleteMovie } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const categories = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, title: '', categoryId: null })
const form = reactive({ id: null, title: '', director: '', actors: '', categoryId: null, duration: 120, releaseDate: '', description: '', status: 1 })

const loadData = async () => {
  loading.value = true
  try { const res = await getMoviePage(query); tableData.value = res.data.list; total.value = res.data.total } finally { loading.value = false }
}
const handleAdd = () => { Object.assign(form, { id: null, title: '', director: '', actors: '', categoryId: null, duration: 120, releaseDate: '', description: '', status: 1 }); dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }
const handleSubmit = async () => {
  await formRef.value.validate()
  form.id ? await updateMovie(form) : await addMovie(form)
  ElMessage.success('操作成功'); dialogVisible.value = false; loadData()
}
const handleDelete = async (id) => { await deleteMovie(id); ElMessage.success('删除成功'); loadData() }

onMounted(async () => {
  const res = await getCategoryList(); categories.value = res.data
  loadData()
})
</script>
