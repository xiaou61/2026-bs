<template>
  <div class="page-container">
    <el-row :gutter="15">
      <el-col :span="6">
        <el-card>
          <template #header>
            <div style="display: flex; justify-content: space-between">
              <span>版块分类</span>
              <el-button type="primary" size="small" @click="openCategoryDialog()" v-if="userStore.isAdmin()">新增</el-button>
            </div>
          </template>
          <el-menu @select="selectCategory">
            <el-menu-item index="0">全部帖子</el-menu-item>
            <el-menu-item v-for="c in categories" :key="c.id" :index="String(c.id)">{{ c.name }}</el-menu-item>
          </el-menu>
        </el-card>
      </el-col>
      <el-col :span="18">
        <el-card>
          <div class="search-bar">
            <el-input v-model="query.title" placeholder="搜索帖子" style="width: 200px" clearable />
            <el-button type="primary" @click="loadPosts">搜索</el-button>
            <el-button type="success" @click="postDialog = true">发布帖子</el-button>
          </div>
          <el-table :data="posts" v-loading="loading">
            <el-table-column prop="title" label="标题">
              <template #default="{ row }">
                <el-tag size="small" v-if="row.isTop">置顶</el-tag>
                <el-link @click="$router.push(`/forum/${row.id}`)">{{ row.title }}</el-link>
              </template>
            </el-table-column>
            <el-table-column prop="categoryName" label="版块" width="120" />
            <el-table-column prop="userName" label="作者" width="100" />
            <el-table-column prop="viewCount" label="浏览" width="80" />
            <el-table-column prop="replyCount" label="回复" width="80" />
            <el-table-column prop="createTime" label="发布时间" width="180" />
          </el-table>
          <el-pagination background layout="total, prev, pager, next" :total="total" v-model:current-page="query.pageNum" @current-change="loadPosts" style="margin-top: 15px" />
        </el-card>
      </el-col>
    </el-row>
    <el-dialog v-model="categoryDialog" title="新增版块" width="400px">
      <el-form :model="categoryForm" label-width="80px">
        <el-form-item label="版块名称"><el-input v-model="categoryForm.name" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="categoryForm.sort" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="categoryForm.description" type="textarea" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="categoryDialog = false">取消</el-button>
        <el-button type="primary" @click="handleCategorySubmit">确定</el-button>
      </template>
    </el-dialog>
    <el-dialog v-model="postDialog" title="发布帖子" width="600px">
      <el-form :model="postForm" label-width="80px">
        <el-form-item label="版块">
          <el-select v-model="postForm.categoryId">
            <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="标题"><el-input v-model="postForm.title" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="postForm.content" type="textarea" :rows="8" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="postDialog = false">取消</el-button>
        <el-button type="primary" @click="handlePostSubmit">发布</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getForumCategories, addForumCategory, getForumPosts, addForumPost } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const loading = ref(false)
const categories = ref([])
const posts = ref([])
const total = ref(0)
const categoryDialog = ref(false)
const postDialog = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, categoryId: null, title: '' })
const categoryForm = reactive({ name: '', sort: 0, description: '' })
const postForm = reactive({ categoryId: null, title: '', content: '' })

const loadCategories = async () => {
  const res = await getForumCategories()
  categories.value = res.data
}

const loadPosts = async () => {
  loading.value = true
  try {
    const res = await getForumPosts(query)
    posts.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const selectCategory = (index) => {
  query.categoryId = index === '0' ? null : Number(index)
  query.pageNum = 1
  loadPosts()
}

const openCategoryDialog = () => {
  Object.assign(categoryForm, { name: '', sort: 0, description: '' })
  categoryDialog.value = true
}

const handleCategorySubmit = async () => {
  await addForumCategory(categoryForm)
  ElMessage.success('添加成功')
  categoryDialog.value = false
  loadCategories()
}

const handlePostSubmit = async () => {
  await addForumPost(postForm)
  ElMessage.success('发布成功')
  postDialog.value = false
  loadPosts()
}

onMounted(() => {
  loadCategories()
  loadPosts()
})
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
