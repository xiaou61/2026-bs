<template>
  <div class="page-container">
    <el-card v-if="!isFan">
      <div class="search-bar">
        <el-input v-model="query.clubName" placeholder="俱乐部名称" clearable />
        <el-input v-model="query.city" placeholder="城市" clearable />
        <el-select v-model="query.status" placeholder="状态" clearable>
          <el-option label="启用" :value="1" />
          <el-option label="停用" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadManagerData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增俱乐部</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="clubNo" label="俱乐部编号" min-width="150" />
        <el-table-column prop="clubName" label="俱乐部名称" min-width="160" />
        <el-table-column prop="shortName" label="简称" min-width="100" />
        <el-table-column prop="city" label="城市" min-width="100" />
        <el-table-column prop="chairman" label="负责人" min-width="100" />
        <el-table-column prop="contactPhone" label="联系电话" min-width="120" />
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
        <el-card class="club-card" shadow="hover">
          <h3>{{ item.clubName }}</h3>
          <p>城市：{{ item.city || '-' }}</p>
          <p>成立：{{ item.foundedYear || '-' }}</p>
          <p>负责人：{{ item.chairman || '-' }}</p>
          <p>{{ item.description || '暂无简介' }}</p>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑俱乐部' : '新增俱乐部'" width="620px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="俱乐部编号">
          <el-input v-model="form.clubNo" />
        </el-form-item>
        <el-form-item label="俱乐部名称" prop="clubName">
          <el-input v-model="form.clubName" />
        </el-form-item>
        <el-form-item label="简称">
          <el-input v-model="form.shortName" />
        </el-form-item>
        <el-form-item label="城市">
          <el-input v-model="form.city" />
        </el-form-item>
        <el-form-item label="成立年份">
          <el-input-number v-model="form.foundedYear" :min="1900" :max="2100" style="width: 100%" />
        </el-form-item>
        <el-form-item label="负责人">
          <el-input v-model="form.chairman" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.contactPhone" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="form.description" type="textarea" />
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
import { deleteClub, getClubList, getClubPublicList, saveClub } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isFan = computed(() => (userStore.user?.role || '').toUpperCase() === 'FAN')
const loading = ref(false)
const tableData = ref([])
const publicList = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, clubName: '', city: '', status: null })
const form = reactive({ id: null, clubNo: '', clubName: '', shortName: '', city: '', foundedYear: 2020, chairman: '', contactPhone: '', status: 1, description: '' })
const rules = { clubName: [{ required: true, message: '请输入俱乐部名称', trigger: 'blur' }] }

const loadManagerData = async () => {
  loading.value = true
  try {
    const res = await getClubList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const loadPublicData = async () => {
  const res = await getClubPublicList()
  publicList.value = res.data || []
}

const handleAdd = () => {
  Object.assign(form, { id: null, clubNo: '', clubName: '', shortName: '', city: '', foundedYear: 2020, chairman: '', contactPhone: '', status: 1, description: '' })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await saveClub(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadManagerData()
}

const handleDelete = async id => {
  await deleteClub(id)
  ElMessage.success('删除成功')
  loadManagerData()
}

onMounted(async () => {
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

.club-card h3 {
  margin: 0 0 10px;
  color: #14365d;
}

.club-card p {
  margin: 6px 0;
  color: #667085;
}
</style>
