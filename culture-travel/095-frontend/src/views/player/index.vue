<template>
  <div class="page-container">
    <el-card v-if="!isFan">
      <div class="search-bar">
        <el-select v-model="query.teamId" placeholder="球队" clearable>
          <el-option v-for="item in teamOptions" :key="item.id" :label="item.teamName" :value="item.id" />
        </el-select>
        <el-input v-model="query.name" placeholder="球员姓名" clearable />
        <el-select v-model="query.position" placeholder="位置" clearable>
          <el-option label="前锋" value="前锋" />
          <el-option label="中场" value="中场" />
          <el-option label="后卫" value="后卫" />
          <el-option label="门将" value="门将" />
        </el-select>
        <el-button type="primary" @click="loadManagerData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增球员</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="playerNo" label="球员编号" min-width="150" />
        <el-table-column prop="name" label="姓名" min-width="100" />
        <el-table-column prop="teamName" label="所属球队" min-width="140" />
        <el-table-column prop="jerseyNumber" label="号码" min-width="80" />
        <el-table-column prop="position" label="位置" min-width="80" />
        <el-table-column prop="goalCount" label="进球" min-width="80" />
        <el-table-column prop="assistCount" label="助攻" min-width="80" />
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
        <el-card class="player-card" shadow="hover">
          <h3>{{ item.name }}</h3>
          <p>球队：{{ item.teamName || '-' }}</p>
          <p>位置：{{ item.position || '-' }}</p>
          <p>号码：{{ item.jerseyNumber || '-' }}</p>
          <p>进球 / 助攻：{{ item.goalCount || 0 }} / {{ item.assistCount || 0 }}</p>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑球员' : '新增球员'" width="640px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="球员编号">
          <el-input v-model="form.playerNo" />
        </el-form-item>
        <el-form-item label="所属球队" prop="teamId">
          <el-select v-model="form.teamId" style="width: 100%">
            <el-option v-for="item in teamOptions" :key="item.id" :label="item.teamName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="号码">
          <el-input-number v-model="form.jerseyNumber" :min="1" :max="99" style="width: 100%" />
        </el-form-item>
        <el-form-item label="位置">
          <el-select v-model="form.position" style="width: 100%">
            <el-option label="前锋" value="前锋" />
            <el-option label="中场" value="中场" />
            <el-option label="后卫" value="后卫" />
            <el-option label="门将" value="门将" />
          </el-select>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number v-model="form.age" :min="15" :max="45" style="width: 100%" />
        </el-form-item>
        <el-form-item label="国籍">
          <el-input v-model="form.nationality" />
        </el-form-item>
        <el-form-item label="进球">
          <el-input-number v-model="form.goalCount" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="助攻">
          <el-input-number v-model="form.assistCount" :min="0" style="width: 100%" />
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
import { deletePlayer, getPlayerList, getPlayerPublicList, getTeamOptions, savePlayer } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isFan = computed(() => (userStore.user?.role || '').toUpperCase() === 'FAN')
const loading = ref(false)
const tableData = ref([])
const publicList = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const teamOptions = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, teamId: null, name: '', position: '' })
const form = reactive({ id: null, playerNo: '', teamId: null, name: '', jerseyNumber: 9, position: '前锋', age: 24, nationality: '中国', goalCount: 0, assistCount: 0, status: 1 })
const rules = {
  teamId: [{ required: true, message: '请选择球队', trigger: 'change' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
}

const loadOptions = async () => {
  const res = await getTeamOptions({})
  teamOptions.value = res.data || []
}

const loadManagerData = async () => {
  loading.value = true
  try {
    const res = await getPlayerList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const loadPublicData = async () => {
  const res = await getPlayerPublicList()
  publicList.value = res.data || []
}

const handleAdd = () => {
  Object.assign(form, { id: null, playerNo: '', teamId: null, name: '', jerseyNumber: 9, position: '前锋', age: 24, nationality: '中国', goalCount: 0, assistCount: 0, status: 1 })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await savePlayer(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadManagerData()
}

const handleDelete = async id => {
  await deletePlayer(id)
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

.player-card h3 {
  margin: 0 0 10px;
  color: #14365d;
}

.player-card p {
  margin: 6px 0;
  color: #667085;
}
</style>
