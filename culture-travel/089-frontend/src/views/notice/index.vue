<template>
  <div>
    <el-card class="mb16" v-if="!userStore.isUser">
      <div class="toolbar">
        <el-input v-model="query.title" placeholder="公告标题" clearable style="width: 200px" />
        <el-select v-model="query.publishStatus" placeholder="发布状态" clearable style="width: 140px">
          <el-option :value="1" label="已发布" />
          <el-option :value="0" label="未发布" />
        </el-select>
        <el-button type="primary" @click="loadManageList">查询</el-button>
        <el-button type="success" @click="handleAdd">新增公告</el-button>
      </div>

      <el-table :data="manageList" border v-loading="loading">
        <el-table-column prop="title" label="标题" min-width="180" />
        <el-table-column prop="publishStatus" label="状态" width="100">
          <template #default="{ row }">{{ row.publishStatus === 1 ? '已发布' : '未发布' }}</template>
        </el-table-column>
        <el-table-column prop="sortNo" label="排序" width="80" />
        <el-table-column prop="publishTime" label="发布时间" min-width="160" />
        <el-table-column label="操作" width="160">
          <template #default="{ row }">
            <el-button link @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card>
      <template #header>公告资讯</template>
      <el-empty v-if="publicList.length === 0" description="暂无公告" />
      <el-timeline v-else>
        <el-timeline-item v-for="item in publicList" :key="item.id" :timestamp="item.publishTime">
          <strong>{{ item.title }}</strong>
          <div class="notice-content">{{ item.content }}</div>
        </el-timeline-item>
      </el-timeline>
    </el-card>

    <el-dialog v-model="dialogVisible" title="公告信息" width="620px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="公告标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="公告内容"><el-input v-model="form.content" type="textarea" :rows="5" /></el-form-item>
        <el-form-item label="排序号"><el-input-number v-model="form.sortNo" :min="0" /></el-form-item>
        <el-form-item label="发布状态">
          <el-radio-group v-model="form.publishStatus">
            <el-radio :label="1">发布</el-radio>
            <el-radio :label="0">草稿</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deleteNotice, getNoticeList, getPublicNoticeList, saveNotice } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const loading = ref(false)
const dialogVisible = ref(false)
const publicList = ref([])
const manageList = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, title: '', publishStatus: null })
const form = reactive({ id: null, title: '', content: '', sortNo: 0, publishStatus: 1 })

const loadPublicList = async () => {
  const res = await getPublicNoticeList()
  publicList.value = res.data || []
}

const loadManageList = async () => {
  if (userStore.isUser) return
  loading.value = true
  try {
    const res = await getNoticeList(query)
    manageList.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, title: '', content: '', sortNo: 0, publishStatus: 1 })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await saveNotice(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  await Promise.all([loadManageList(), loadPublicList()])
}

const handleDelete = async id => {
  await deleteNotice(id)
  ElMessage.success('删除成功')
  await Promise.all([loadManageList(), loadPublicList()])
}

onMounted(async () => {
  await Promise.all([loadPublicList(), loadManageList()])
})
</script>

<style scoped>
.mb16 {
  margin-bottom: 16px;
}

.toolbar {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
}

.notice-content {
  margin-top: 6px;
  line-height: 1.8;
  color: #666;
}
</style>
