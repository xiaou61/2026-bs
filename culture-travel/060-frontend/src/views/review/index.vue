<template>
  <div>
    <el-card>
      <div style="display:flex;gap:10px;margin-bottom:15px">
        <el-input v-model="query.movieTitle" placeholder="搜索电影名" style="width:200px" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="username" label="用户" width="100" />
        <el-table-column prop="movieTitle" label="电影" width="150" />
        <el-table-column prop="rating" label="评分" width="70" />
        <el-table-column prop="content" label="评论内容" show-overflow-tooltip />
        <el-table-column prop="createTime" label="时间" width="160" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-popconfirm title="确认删除?" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top:15px;justify-content:flex-end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" @current-change="loadData" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getReviewPage, deleteReview } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, movieTitle: '' })

const loadData = async () => { loading.value = true; try { const res = await getReviewPage(query); tableData.value = res.data.list; total.value = res.data.total } finally { loading.value = false } }
const handleDelete = async (id) => { await deleteReview(id); ElMessage.success('删除成功'); loadData() }
onMounted(loadData)
</script>
