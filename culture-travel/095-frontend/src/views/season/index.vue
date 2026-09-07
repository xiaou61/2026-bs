<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.leagueId" placeholder="联赛" clearable>
          <el-option v-for="item in leagueOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-input v-model="query.seasonName" placeholder="赛季名称" clearable />
        <el-select v-model="query.status" placeholder="状态" clearable>
          <el-option label="启用" :value="1" />
          <el-option label="停用" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增赛季</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="seasonNo" label="赛季编号" min-width="160" />
        <el-table-column prop="seasonName" label="赛季名称" min-width="150" />
        <el-table-column prop="leagueName" label="所属联赛" min-width="150" />
        <el-table-column prop="yearLabel" label="年份标识" min-width="120" />
        <el-table-column prop="rounds" label="轮次" min-width="80" />
        <el-table-column label="赛期" min-width="180">
          <template #default="{ row }">{{ row.startDate || '-' }} 至 {{ row.endDate || '-' }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" min-width="80">
          <template #default="{ row }">{{ row.status === 1 ? '启用' : '停用' }}</template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑赛季' : '新增赛季'" width="620px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="赛季编号">
          <el-input v-model="form.seasonNo" />
        </el-form-item>
        <el-form-item label="所属联赛" prop="leagueId">
          <el-select v-model="form.leagueId" style="width: 100%">
            <el-option v-for="item in leagueOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="赛季名称" prop="seasonName">
          <el-input v-model="form.seasonName" />
        </el-form-item>
        <el-form-item label="年份标识">
          <el-input v-model="form.yearLabel" />
        </el-form-item>
        <el-form-item label="开始日期">
          <el-date-picker v-model="form.startDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="结束日期">
          <el-date-picker v-model="form.endDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="轮次">
          <el-input-number v-model="form.rounds" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
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
import { deleteSeason, getLeagueOptions, getSeasonList, saveSeason } from '../../api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const leagueOptions = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, leagueId: null, seasonName: '', status: null })
const form = reactive({ id: null, seasonNo: '', leagueId: null, seasonName: '', yearLabel: '', startDate: '', endDate: '', rounds: 0, status: 1 })
const rules = {
  leagueId: [{ required: true, message: '请选择联赛', trigger: 'change' }],
  seasonName: [{ required: true, message: '请输入赛季名称', trigger: 'blur' }]
}

const loadOptions = async () => {
  const res = await getLeagueOptions()
  leagueOptions.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getSeasonList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, seasonNo: '', leagueId: null, seasonName: '', yearLabel: '', startDate: '', endDate: '', rounds: 0, status: 1 })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await saveSeason(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteSeason(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadOptions()
  loadData()
})
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}
</style>
