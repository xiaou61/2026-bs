<template>
  <div class="page">
    <div v-if="canEdit" class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item label="剧目名称">
          <el-input v-model="filters.repertoireName" placeholder="请输入剧目名称" clearable />
        </el-form-item>
        <el-form-item label="主讲艺术家">
          <el-select v-model="filters.artistId" placeholder="全部艺术家" clearable style="width: 160px">
            <el-option v-for="item in artistOptions" :key="item.id" :label="item.realName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="文化专题">
          <el-select v-model="filters.seasonId" placeholder="全部文化专题" clearable style="width: 180px">
            <el-option v-for="item in seasonOptions" :key="item.id" :label="item.seasonName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="场馆">
          <el-select v-model="filters.classId" placeholder="全部场馆" clearable style="width: 180px">
            <el-option v-for="item in classOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="filters.status" placeholder="全部状态" clearable style="width: 140px">
            <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="toolbar-actions">
        <el-button @click="resetFilters">重置</el-button>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="openDialog()">新增排期</el-button>
      </div>
    </div>

    <el-card v-else shadow="never" class="summary-card">
      <div class="summary-title">{{ pageTitle }}</div>
      <div class="summary-desc">{{ pageDesc }}</div>
    </el-card>

    <el-card shadow="never">
      <el-table :data="list" stripe>
        <el-table-column prop="repertoireName" label="剧目名称" min-width="160" />
        <el-table-column prop="artistName" label="主讲艺术家" min-width="110" />
        <el-table-column prop="seasonName" label="文化专题" min-width="180" />
        <el-table-column prop="className" label="场馆" min-width="130" />
        <el-table-column label="上课时间" min-width="160">
          <template #default="{ row }">
            {{ row.weekDay }} {{ row.startSection }}-{{ row.endSection }}节
          </template>
        </el-table-column>
        <el-table-column prop="classroom" label="场地" min-width="100" />
        <el-table-column label="人数" width="120">
          <template #default="{ row }">
            {{ row.selectedCount || 0 }}/{{ row.maxMemberCount || 0 }}
          </template>
        </el-table-column>
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ getOptionLabel(statusOptions, row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column v-if="canEdit" label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openDialog(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div v-if="canEdit" class="pagination">
        <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50]"
          :total="total"
          layout="total, sizes, prev, pager, next"
          @current-change="loadData"
          @size-change="loadData"
        />
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑排期' : '新增排期'" width="640px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="92px">
        <el-form-item label="剧目" prop="repertoireId">
          <el-select v-model="form.repertoireId" placeholder="请选择剧目" style="width: 100%">
            <el-option v-for="item in repertoireOptions" :key="item.id" :label="item.repertoireName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="文化专题" prop="seasonId">
          <el-select v-model="form.seasonId" placeholder="请选择文化专题" style="width: 100%">
            <el-option v-for="item in seasonOptions" :key="item.id" :label="item.seasonName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="艺术家" prop="artistId">
          <el-select v-model="form.artistId" placeholder="请选择艺术家" style="width: 100%">
            <el-option v-for="item in artistOptions" :key="item.id" :label="item.realName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="场馆">
          <el-select v-model="form.classId" placeholder="请选择场馆" style="width: 100%">
            <el-option v-for="item in classOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="上课场地">
          <el-input v-model="form.classroom" placeholder="请输入场地" />
        </el-form-item>
        <el-form-item label="星期">
          <el-select v-model="form.weekDay" placeholder="请选择上课日" style="width: 100%">
            <el-option v-for="item in weekDayOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="开始节次">
          <el-input-number v-model="form.startSection" :min="1" :max="12" style="width: 100%" />
        </el-form-item>
        <el-form-item label="结束节次">
          <el-input-number v-model="form.endSection" :min="1" :max="12" style="width: 100%" />
        </el-form-item>
        <el-form-item label="人数上限">
          <el-input-number v-model="form.maxMemberCount" :min="1" :max="500" style="width: 100%" />
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
        <el-button type="primary" @click="submitForm">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { addSchedule, deleteSchedule, getClassEnabled, getCourseEnabled, getScheduleList, getMemberSchedule, getArtistSchedule, getTermEnabled, getUserOptions, updateSchedule } from '../../api'
import { getOptionLabel, resolvePage, statusOptions, weekDayOptions } from '../../utils'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const canEdit = computed(() => userStore.role === 'admin')
const pageTitle = computed(() => userStore.role === 'artist' ? '我的主讲安排' : '我的个人行程')
const pageDesc = computed(() => userStore.role === 'artist' ? '这里展示当前艺术家的全部主讲排期。' : '这里展示会员已选剧目形成的个人行程。')

const repertoireOptions = ref([])
const seasonOptions = ref([])
const classOptions = ref([])
const artistOptions = ref([])
const filters = reactive({
  repertoireName: '',
  artistId: undefined,
  seasonId: undefined,
  classId: undefined,
  status: undefined
})
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const list = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({
  id: null,
  repertoireId: null,
  seasonId: null,
  artistId: null,
  classId: null,
  classroom: '',
  weekDay: '周一',
  startSection: 1,
  endSection: 2,
  maxMemberCount: 60,
  status: 1
})
const rules = {
  repertoireId: [{ required: true, message: '请选择剧目', trigger: 'change' }],
  seasonId: [{ required: true, message: '请选择文化专题', trigger: 'change' }],
  artistId: [{ required: true, message: '请选择艺术家', trigger: 'change' }]
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    repertoireId: null,
    seasonId: null,
    artistId: null,
    classId: null,
    classroom: '',
    weekDay: '周一',
    startSection: 1,
    endSection: 2,
    maxMemberCount: 60,
    status: 1
  })
}

const loadOptions = async () => {
  const [repertoireRes, seasonRes, classRes, artistRes] = await Promise.all([
    getCourseEnabled(),
    getTermEnabled(),
    getClassEnabled(),
    getUserOptions('artist')
  ])
  repertoireOptions.value = repertoireRes.data || []
  seasonOptions.value = seasonRes.data || []
  classOptions.value = classRes.data || []
  artistOptions.value = artistRes.data || []
}

const loadData = async () => {
  if (canEdit.value) {
    const res = await getScheduleList({
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      ...filters
    })
    const page = resolvePage(res.data)
    list.value = page.list
    total.value = page.total
    return
  }
  const res = userStore.role === 'artist' ? await getArtistSchedule() : await getMemberSchedule()
  list.value = res.data || []
}

const resetFilters = () => {
  filters.repertoireName = ''
  filters.artistId = undefined
  filters.seasonId = undefined
  filters.classId = undefined
  filters.status = undefined
  pageNum.value = 1
  loadData()
}

const openDialog = (row) => {
  resetForm()
  if (row) {
    Object.assign(form, { ...row })
  }
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateSchedule(form)
    ElMessage.success('排期更新成功')
  } else {
    await addSchedule(form)
    ElMessage.success('排期新增成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('删除后不可恢复，确定继续吗？', '提示', { type: 'warning' })
  await deleteSchedule(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadOptions()
  await loadData()
})
</script>

<style scoped>
.page {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  flex-wrap: wrap;
}

.toolbar-actions,
.pagination {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.summary-card {
  border-radius: 24px;
}

.summary-title {
  font-size: 22px;
  color: #3f2f1f;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.summary-desc {
  margin-top: 10px;
  color: var(--subtle);
}

.pagination {
  margin-top: 16px;
}
</style>


