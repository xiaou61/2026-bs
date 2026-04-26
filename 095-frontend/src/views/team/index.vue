<template>
  <div class="page-container">
    <el-card v-if="!isFan">
      <div class="search-bar">
        <el-select v-model="query.seasonId" placeholder="赛季" clearable>
          <el-option v-for="item in seasonOptions" :key="item.id" :label="item.seasonName" :value="item.id" />
        </el-select>
        <el-select v-model="query.clubId" placeholder="俱乐部" clearable>
          <el-option v-for="item in clubOptions" :key="item.id" :label="item.clubName" :value="item.id" />
        </el-select>
        <el-input v-model="query.teamName" placeholder="球队名称" clearable />
        <el-button type="primary" @click="loadManagerData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增球队</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="teamNo" label="球队编号" min-width="150" />
        <el-table-column prop="teamName" label="球队名称" min-width="150" />
        <el-table-column prop="seasonName" label="赛季" min-width="140" />
        <el-table-column prop="clubName" label="俱乐部" min-width="140" />
        <el-table-column prop="venueName" label="主场" min-width="140" />
        <el-table-column label="球衣颜色" min-width="160">
          <template #default="{ row }">{{ row.homeColor || '-' }} / {{ row.awayColor || '-' }}</template>
        </el-table-column>
        <el-table-column prop="goalTarget" label="赛季目标" min-width="140" />
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

    <el-row v-else :gutter="16">
      <el-col v-for="item in publicList" :key="item.id" :span="8">
        <el-card class="team-card" shadow="hover">
          <h3>{{ item.teamName }}</h3>
          <p>赛季：{{ item.seasonName || '-' }}</p>
          <p>俱乐部：{{ item.clubName || '-' }}</p>
          <p>主场：{{ item.venueName || '-' }}</p>
          <p>目标：{{ item.goalTarget || '稳定发挥' }}</p>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑球队' : '新增球队'" width="680px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="球队编号">
          <el-input v-model="form.teamNo" />
        </el-form-item>
        <el-form-item label="赛季" prop="seasonId">
          <el-select v-model="form.seasonId" style="width: 100%">
            <el-option v-for="item in seasonOptions" :key="item.id" :label="item.seasonName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="俱乐部" prop="clubId">
          <el-select v-model="form.clubId" style="width: 100%">
            <el-option v-for="item in clubOptions" :key="item.id" :label="item.clubName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="主场" prop="venueId">
          <el-select v-model="form.venueId" style="width: 100%">
            <el-option v-for="item in venueOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="球队名称" prop="teamName">
          <el-input v-model="form.teamName" />
        </el-form-item>
        <el-form-item label="主场色">
          <el-input v-model="form.homeColor" />
        </el-form-item>
        <el-form-item label="客场色">
          <el-input v-model="form.awayColor" />
        </el-form-item>
        <el-form-item label="赛季目标">
          <el-input v-model="form.goalTarget" />
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
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deleteTeam, getClubOptions, getSeasonOptions, getTeamList, getTeamPublicList, getVenueOptions, saveTeam } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isFan = computed(() => (userStore.user?.role || '').toUpperCase() === 'FAN')
const loading = ref(false)
const tableData = ref([])
const publicList = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const seasonOptions = ref([])
const clubOptions = ref([])
const venueOptions = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, seasonId: null, clubId: null, teamName: '' })
const form = reactive({ id: null, teamNo: '', seasonId: null, clubId: null, venueId: null, teamName: '', homeColor: '', awayColor: '', goalTarget: '', status: 1 })
const rules = {
  seasonId: [{ required: true, message: '请选择赛季', trigger: 'change' }],
  clubId: [{ required: true, message: '请选择俱乐部', trigger: 'change' }],
  venueId: [{ required: true, message: '请选择球场', trigger: 'change' }],
  teamName: [{ required: true, message: '请输入球队名称', trigger: 'blur' }]
}

const loadOptions = async () => {
  const [seasonRes, clubRes, venueRes] = await Promise.all([getSeasonOptions({}), getClubOptions(), getVenueOptions()])
  seasonOptions.value = seasonRes.data || []
  clubOptions.value = clubRes.data || []
  venueOptions.value = venueRes.data || []
}

const loadManagerData = async () => {
  loading.value = true
  try {
    const res = await getTeamList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const loadPublicData = async () => {
  const res = await getTeamPublicList({})
  publicList.value = res.data || []
}

const handleAdd = () => {
  Object.assign(form, { id: null, teamNo: '', seasonId: null, clubId: null, venueId: null, teamName: '', homeColor: '', awayColor: '', goalTarget: '', status: 1 })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await saveTeam(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadManagerData()
}

const handleDelete = async id => {
  await deleteTeam(id)
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

.team-card h3 {
  margin: 0 0 10px;
  color: #14365d;
}

.team-card p {
  margin: 6px 0;
  color: #667085;
}
</style>
