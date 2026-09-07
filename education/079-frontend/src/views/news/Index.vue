<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.category" placeholder="分类" clearable style="width: 120px">
          <el-option label="新闻" value="news" />
          <el-option label="公告" value="notice" />
        </el-select>
        <el-input v-model="query.title" placeholder="标题" style="width: 200px" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="openDialog()" v-if="userStore.isAdmin()">发布</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="category" label="分类" width="100">
          <template #default="{ row }">
            <el-tag :type="row.category === 'notice' ? 'warning' : ''">{{ row.category === 'notice' ? '公告' : '新闻' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="authorName" label="作者" width="100" />
        <el-table-column prop="viewCount" label="浏览" width="80" />
        <el-table-column prop="createTime" label="发布时间" width="180" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="$router.push(`/news/${row.id}`)">查看</el-button>
            <el-button link @click="openDialog(row)" v-if="userStore.isAdmin()">编辑</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)" v-if="userStore.isAdmin()">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="total, prev, pager, next" :total="total" v-model:current-page="query.pageNum" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑新闻' : '发布新闻'" width="700px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="分类">
          <el-radio-group v-model="form.category">
            <el-radio value="news">新闻</el-radio>
            <el-radio value="notice">公告</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否置顶">
          <el-switch v-model="form.isTop" :active-value="1" :inactive-value="0" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="form.content" type="textarea" :rows="10" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getNewsList, addNews, updateNews, deleteNews } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, category: '', title: '' })
const form = reactive({ id: null, title: '', content: '', category: 'news', isTop: 0 })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getNewsList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const openDialog = (row = null) => {
  if (row) {
    Object.assign(form, row)
  } else {
    Object.assign(form, { id: null, title: '', content: '', category: 'news', isTop: 0 })
  }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (form.id) {
    await updateNews(form)
  } else {
    await addNews(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteNews(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
