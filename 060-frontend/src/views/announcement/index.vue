<template>
  <div>
    <el-card>
      <div style="display:flex;gap:10px;margin-bottom:15px">
        <el-input v-model="query.title" placeholder="搜索公告" style="width:200px" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd" v-if="userStore.isAdmin()">发布公告</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="content" label="内容" show-overflow-tooltip />
        <el-table-column prop="createTime" label="发布时间" width="160" />
        <el-table-column label="操作" width="150" v-if="userStore.isAdmin()">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top:15px;justify-content:flex-end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" @current-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑公告' : '发布公告'" width="500px">
      <el-form :model="form" ref="formRef" label-width="80px">
        <el-form-item label="标题" prop="title" :rules="[{required:true,message:'请输入标题'}]"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="内容" prop="content" :rules="[{required:true,message:'请输入内容'}]"><el-input v-model="form.content" type="textarea" :rows="4" /></el-form-item>
        <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getAnnouncementPage, addAnnouncement, updateAnnouncement, deleteAnnouncement } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, title: '' })
const form = reactive({ id: null, title: '', content: '', status: 1 })

const loadData = async () => { loading.value = true; try { const res = await getAnnouncementPage(query); tableData.value = res.data.list; total.value = res.data.total } finally { loading.value = false } }
const handleAdd = () => { Object.assign(form, { id: null, title: '', content: '', status: 1 }); dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }
const handleSubmit = async () => { await formRef.value.validate(); form.id ? await updateAnnouncement(form) : await addAnnouncement(form); ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleDelete = async (id) => { await deleteAnnouncement(id); ElMessage.success('删除成功'); loadData() }
onMounted(loadData)
</script>
