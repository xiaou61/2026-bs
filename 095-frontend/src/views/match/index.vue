<template>
  <div class="page-container">
    <el-card v-if="!isFan">
      <div class="search-bar">
        <el-select v-model="query.seasonId" placeholder="赛季" clearable>
          <el-option v-for="item in seasonOptions" :key="item.id" :label="item.seasonName" :value="item.id" />
        </el-select>
        <el-select v-model="query.teamId" placeholder="球队" clearable>
          <el-option v-for="item in teamOptions" :key="item.id" :label="item.teamName" :value="item.id" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable>
          <el-option label="SCHEDULED" value="SCHEDULED" />
          <el-option label="FINISHED" value="FINISHED" />
        </el-select>
        <el-button type="primary" @click="loadManagerData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增比赛</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="matchNo" label="比赛编号" min-width="150" />
        <el-table-column prop="seasonName" label="赛季" min-width="120" />
        <el-table-column prop="roundNo" label="轮次" min-width="80" />
        <el-table-column label="对阵" min-width="220">
          <template #default="{ row }">{{ row.homeTeamName }} vs {{ row.awayTeamName }}</template>
        </el-table-column>
        <el-table-column prop="venueName" label="球场" min-width="140" />
        <el-table-column prop="kickOffTime" label="开球时间" min-width="160" />
        <el-table-column label="比分" min-width="100">
          <template #default="{ row }">{{ row.homeScore || 0 }} : {{ row.awayScore || 0 }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" min-width="100" />
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="success" @click="handleResult(row)">录入赛果</el-button>
            <el-popconfirm title="确认删除吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card v-else>
      <el-table :data="publicList">
        <el-table-column prop="seasonName" label="赛季" min-width="120" />
        <el-table-column prop="roundNo" label="轮次" min-width="80" />
        <el-table-column label="对阵" min-width="220">
          <template #default="{ row }">{{ row.homeTeamName }} vs {{ row.awayTeamName }}</template>
        </el-table-column>
        <el-table-column prop="kickOffTime" label="开球时间" min-width="160" />
        <el-table-column label="比分" min-width="100">
          <template #default="{ row }">{{ row.homeScore || 0 }} : {{ row.awayScore || 0 }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" min-width="100" />
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑比赛' : '新增比赛'" width="700px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="比赛编号">
          <el-input v-model="form.matchNo" />
        </el-form-item>
        <el-form-item label="赛季" prop="seasonId">
          <el-select v-model="form.seasonId" style="width: 100%">
            <el-option v-for="item in seasonOptions" :key="item.id" :label="item.seasonName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="轮次">
          <el-input-number v-model="form.roundNo" :min="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="主队" prop="homeTeamId">
          <el-select v-model="form.homeTeamId" style="width: 100%">
            <el-option v-for="item in teamOptions" :key="item.id" :label="item.teamName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="客队" prop="awayTeamId">
          <el-select v-model="form.awayTeamId" style="width: 100%">
            <el-option v-for="item in teamOptions" :key="item.id" :label="item.teamName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="球场" prop="venueId">
          <el-select v-model="form.venueId" style="width: 100%">
            <el-option v-for="item in venueOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="开球时间" prop="kickOffTime">
          <el-date-picker v-model="form.kickOffTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
        </el-form-item>
        <el-form-item label="裁判">
          <el-input v-model="form.referee" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="resultVisible" title="录入赛果" width="480px">
      <el-form :model="resultForm" label-width="90px">
        <el-form-item label="主队进球">
          <el-input-number v-model="resultForm.homeScore" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="客队进球">
          <el-input-number v-model="resultForm.awayScore" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="裁判">
          <el-input v-model="resultForm.referee" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="resultForm.remark" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="resultVisible = false">取消</el-button>
        <el-button type="primary" @click="handleResultSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deleteMatch, getMatchList, getMatchPublicList, getSeasonOptions, getTeamOptions, getVenueOptions, saveMatch, updateMatchResult } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isFan = computed(() => (userStore.user?.role || '').toUpperCase() === 'FAN')
const loading = ref(false)
const tableData = ref([])
const publicList = ref([])
const dialogVisible = ref(false)
const resultVisible = ref(false)
const editingId = ref(null)
const formRef = ref()
const seasonOptions = ref([])
const teamOptions = ref([])
const venueOptions = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, seasonId: null, status: '', teamId: null })
const form = reactive({ id: null, matchNo: '', seasonId: null, roundNo: 1, homeTeamId: null, awayTeamId: null, venueId: null, kickOffTime: '', referee: '', remark: '', status: 'SCHEDULED' })
const resultForm = reactive({ homeScore: 0, awayScore: 0, referee: '', remark: '' })
const rules = {
  seasonId: [{ required: true, message: '请选择赛季', trigger: 'change' }],
  homeTeamId: [{ required: true, message: '请选择主队', trigger: 'change' }],
  awayTeamId: [{ required: true, message: '请选择客队', trigger: 'change' }],
  venueId: [{ required: true, message: '请选择球场', trigger: 'change' }],
  kickOffTime: [{ required: true, message: '请选择开球时间', trigger: 'change' }]
}

const loadOptions = async () => {
  const [seasonRes, teamRes, venueRes] = await Promise.all([getSeasonOptions({}), getTeamOptions({}), getVenueOptions()])
  seasonOptions.value = seasonRes.data || []
  teamOptions.value = teamRes.data || []
  venueOptions.value = venueRes.data || []
}

const loadManagerData = async () => {
  loading.value = true
  try {
    const res = await getMatchList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const loadPublicData = async () => {
  const res = await getMatchPublicList({})
  publicList.value = res.data || []
}

const handleAdd = () => {
  Object.assign(form, { id: null, matchNo: '', seasonId: null, roundNo: 1, homeTeamId: null, awayTeamId: null, venueId: null, kickOffTime: '', referee: '', remark: '', status: 'SCHEDULED' })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await saveMatch(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadManagerData()
}

const handleResult = row => {
  editingId.value = row.id
  Object.assign(resultForm, { homeScore: row.homeScore || 0, awayScore: row.awayScore || 0, referee: row.referee || '', remark: row.remark || '' })
  resultVisible.value = true
}

const handleResultSubmit = async () => {
  await updateMatchResult(editingId.value, resultForm)
  ElMessage.success('赛果已更新')
  resultVisible.value = false
  loadManagerData()
}

const handleDelete = async id => {
  await deleteMatch(id)
  ElMessage.success('删除成功')
  loadManagerData()
}

onMounted(async () => {
  await loadOptions()
  if (isFan.value) {
    await loadPublicData()
  } else {
    await loadManagerData()
  }
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
