<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.keyword" placeholder="搜索竞赛名称" style="width: 200px" clearable />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px">
          <el-option label="草稿" :value="0" />
          <el-option label="进行中" :value="1" />
          <el-option label="已结束" :value="2" />
          <el-option label="已下架" :value="3" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="$router.push('/competition/add')">新增竞赛</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="title" label="竞赛名称" min-width="200" />
        <el-table-column prop="categoryName" label="分类" width="100" />
        <el-table-column prop="workCount" label="作品数" width="80" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusText[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="startTime" label="开始时间" width="160" />
        <el-table-column prop="endTime" label="结束时间" width="160" />
        <el-table-column label="操作" width="280">
          <template #default="{ row }">
            <el-button link type="primary" @click="$router.push(`/competition/edit/${row.id}`)">编辑</el-button>
            <el-button link type="success" v-if="row.status === 0" @click="handleStatus(row.id, 1)">发布</el-button>
            <el-button link type="warning" v-if="row.status === 1" @click="handleStatus(row.id, 2)">结束</el-button>
            <el-button link type="info" @click="showJudgeDialog(row)">分配评委</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
    <el-dialog v-model="judgeDialogVisible" title="分配评委" width="500px">
      <el-select v-model="selectedJudges" multiple placeholder="选择评委" style="width: 100%">
        <el-option v-for="j in judgeList" :key="j.id" :label="j.nickname" :value="j.id" />
      </el-select>
      <template #footer>
        <el-button @click="judgeDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAssignJudges">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getCompetitionList, deleteCompetition, updateCompetitionStatus, getJudges, getJudgeAssignments, assignJudges } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, keyword: '', status: null })
const statusText = { 0: '草稿', 1: '进行中', 2: '已结束', 3: '已下架' }
const statusType = { 0: 'info', 1: 'success', 2: 'warning', 3: 'danger' }
const judgeDialogVisible = ref(false)
const judgeList = ref([])
const selectedJudges = ref([])
const currentCompetitionId = ref(null)

const loadData = async () => {
  loading.value = true
  try {
    const res = await getCompetitionList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleStatus = async (id, status) => {
  await updateCompetitionStatus(id, status)
  ElMessage.success('操作成功')
  loadData()
}

const handleDelete = async (id) => {
  await deleteCompetition(id)
  ElMessage.success('删除成功')
  loadData()
}

const showJudgeDialog = async (row) => {
  currentCompetitionId.value = row.id
  const [judges, assignments] = await Promise.all([getJudges(), getJudgeAssignments(row.id)])
  judgeList.value = judges.data
  selectedJudges.value = assignments.data.map(a => a.judgeId)
  judgeDialogVisible.value = true
}

const handleAssignJudges = async () => {
  await assignJudges({ competitionId: currentCompetitionId.value, judgeIds: selectedJudges.value })
  ElMessage.success('分配成功')
  judgeDialogVisible.value = false
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
