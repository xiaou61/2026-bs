<template>
  <el-card>
    <div style="display:flex;gap:10px;margin-bottom:15px">
      <el-input v-model="query.title" placeholder="搜索标题" style="width:200px" clearable />
      <el-select v-model="query.type" placeholder="类型" style="width:130px" clearable><el-option label="系统通知" :value="1" /><el-option label="活动公告" :value="2" /><el-option label="维护通知" :value="3" /></el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button v-if="isAdmin" type="success" @click="handleAdd">新增</el-button>
    </div>
    <el-table :data="tableData" v-loading="loading" stripe>
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column prop="title" label="标题" />
      <el-table-column label="类型" width="100"><template #default="{ row }"><el-tag :type="{ 1: '', 2: 'success', 3: 'warning' }[row.type]" size="small">{{ { 1: '系统通知', 2: '活动公告', 3: '维护通知' }[row.type] }}</el-tag></template></el-table-column>
      <el-table-column label="状态" width="80"><template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'" size="small">{{ row.status === 1 ? '发布' : '草稿' }}</el-tag></template></el-table-column>
      <el-table-column prop="createTime" label="发布时间" width="170" />
      <el-table-column label="操作" width="150" v-if="isAdmin">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:15px;justify-content:end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" />
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑公告' : '新增公告'" width="600px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="类型"><el-select v-model="form.type" style="width:100%"><el-option label="系统通知" :value="1" /><el-option label="活动公告" :value="2" /><el-option label="维护通知" :value="3" /></el-select></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="5" /></el-form-item>
        <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" active-text="发布" inactive-text="草稿" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getAnnouncementList, addAnnouncement, updateAnnouncement, deleteAnnouncement } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'admin')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, title: '', type: null })
const form = reactive({ id: null, title: '', content: '', type: 1, status: 1 })

const loadData = async () => { loading.value = true; try { const res = await getAnnouncementList(query); tableData.value = res.data.list; total.value = res.data.total } finally { loading.value = false } }
const handleAdd = () => { Object.assign(form, { id: null, title: '', content: '', type: 1, status: 1 }); dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }
const handleSubmit = async () => { if (form.id) { await updateAnnouncement(form.id, form) } else { await addAnnouncement(form) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleDelete = async (id) => { await deleteAnnouncement(id); ElMessage.success('删除成功'); loadData() }
onMounted(loadData)
</script>
