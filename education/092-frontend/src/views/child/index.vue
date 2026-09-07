<template>
  <div class="page">
    <template v-if="isParent">
      <el-card v-for="item in myChildren" :key="item.id" shadow="never" class="child-card">
        <div class="child-head">
          <div>
            <div class="child-name">{{ item.childName }}</div>
            <div class="child-meta">{{ item.className }} / {{ item.teacherName || '未分配教师' }}</div>
          </div>
          <el-tag :type="item.profileStatus === 1 ? 'success' : 'info'">{{ item.profileStatus === 1 ? '在园' : '离园' }}</el-tag>
        </div>
        <div class="child-grid">
          <div>性别：{{ item.gender || '-' }}</div>
          <div>生日：{{ formatDate(item.birthday) }}</div>
          <div>园区：{{ item.campusName || '-' }}</div>
          <div>年级：{{ item.gradeName || '-' }}</div>
          <div>过敏信息：{{ item.allergyInfo || '-' }}</div>
          <div>接送人：{{ item.pickupPerson || '-' }}</div>
        </div>
      </el-card>
      <el-empty v-if="!myChildren.length" description="暂无孩子档案" />
    </template>

    <template v-else>
      <div class="toolbar">
        <el-form :inline="true" :model="filters">
          <el-form-item label="班级">
            <el-select v-model="filters.classId" placeholder="全部班级" clearable style="width: 220px">
              <el-option v-for="item in classOptions" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="filters.profileStatus" placeholder="全部状态" clearable style="width: 140px">
              <el-option :value="1" label="在园" />
              <el-option :value="0" label="离园" />
            </el-select>
          </el-form-item>
        </el-form>
        <div class="toolbar-actions">
          <el-button @click="resetFilters">重置</el-button>
          <el-button type="primary" @click="loadData">查询</el-button>
          <el-button type="success" @click="openDialog()">新增档案</el-button>
        </div>
      </div>

      <el-card shadow="never">
        <el-table :data="list" stripe>
          <el-table-column prop="childName" label="幼儿姓名" min-width="120" />
          <el-table-column prop="gender" label="性别" width="90" />
          <el-table-column prop="className" label="班级" min-width="140" />
          <el-table-column prop="parentName" label="家长账号" min-width="120" />
          <el-table-column prop="teacherName" label="负责教师" min-width="120" />
          <el-table-column prop="pickupPerson" label="接送人" min-width="120" />
          <el-table-column prop="allergyInfo" label="过敏信息" min-width="180" show-overflow-tooltip />
          <el-table-column label="状态" width="100">
            <template #default="{ row }">
              <el-tag :type="row.profileStatus === 1 ? 'success' : 'info'">{{ row.profileStatus === 1 ? '在园' : '离园' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="180" fixed="right">
            <template #default="{ row }">
              <el-button link type="primary" @click="openDialog(row)">编辑</el-button>
              <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination">
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

      <el-dialog v-model="dialogVisible" :title="form.id ? '编辑幼儿档案' : '新增幼儿档案'" width="640px">
        <el-form ref="formRef" :model="form" :rules="rules" label-width="92px">
          <el-form-item label="幼儿姓名" prop="childName">
            <el-input v-model="form.childName" placeholder="请输入幼儿姓名" />
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="form.gender">
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="出生日期">
            <el-date-picker v-model="form.birthday" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
          </el-form-item>
          <el-form-item label="所属班级" prop="classId">
            <el-select v-model="form.classId" placeholder="请选择班级" style="width: 100%" @change="handleClassChange">
              <el-option v-for="item in classOptions" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="家长账号" prop="parentId">
            <el-select v-model="form.parentId" placeholder="请选择家长账号" style="width: 100%">
              <el-option v-for="item in parentOptions" :key="item.id" :label="item.realName" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="负责教师">
            <el-select v-model="form.teacherId" placeholder="请选择负责教师" style="width: 100%">
              <el-option v-for="item in teacherOptions" :key="item.id" :label="item.realName" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="过敏信息">
            <el-input v-model="form.allergyInfo" placeholder="请输入过敏信息" />
          </el-form-item>
          <el-form-item label="接送人">
            <el-input v-model="form.pickupPerson" placeholder="请输入接送人" />
          </el-form-item>
          <el-form-item label="状态">
            <el-radio-group v-model="form.profileStatus">
              <el-radio :label="1">在园</el-radio>
              <el-radio :label="0">离园</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">保存</el-button>
        </template>
      </el-dialog>
    </template>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { addChild, deleteChild, getChildList, getClassEnabled, getMyChildren, getUserOptions, updateChild } from '../../api'
import { formatDate, resolvePage } from '../../utils'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isParent = computed(() => userStore.role === 'parent')
const myChildren = ref([])
const classOptions = ref([])
const parentOptions = ref([])
const teacherOptions = ref([])
const filters = reactive({
  classId: undefined,
  profileStatus: undefined
})
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const list = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({
  id: null,
  childName: '',
  gender: '男',
  birthday: '',
  campusId: null,
  gradeId: null,
  classId: null,
  parentId: null,
  teacherId: null,
  allergyInfo: '',
  pickupPerson: '',
  profileStatus: 1
})
const rules = {
  childName: [{ required: true, message: '请输入幼儿姓名', trigger: 'blur' }],
  classId: [{ required: true, message: '请选择所属班级', trigger: 'change' }],
  parentId: [{ required: true, message: '请选择家长账号', trigger: 'change' }]
}

const loadOptions = async () => {
  const [classRes, parentRes, teacherRes] = await Promise.all([
    getClassEnabled(),
    getUserOptions('parent'),
    getUserOptions('teacher')
  ])
  classOptions.value = classRes.data || []
  parentOptions.value = parentRes.data || []
  teacherOptions.value = teacherRes.data || []
}

const loadData = async () => {
  const res = await getChildList({
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    ...filters
  })
  const page = resolvePage(res.data)
  list.value = page.list
  total.value = page.total
}

const loadMine = async () => {
  const res = await getMyChildren()
  myChildren.value = res.data || []
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    childName: '',
    gender: '男',
    birthday: '',
    campusId: null,
    gradeId: null,
    classId: null,
    parentId: null,
    teacherId: null,
    allergyInfo: '',
    pickupPerson: '',
    profileStatus: 1
  })
}

const handleClassChange = (classId) => {
  const target = classOptions.value.find((item) => item.id === classId)
  form.gradeId = target?.majorId || null
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
    await updateChild(form)
    ElMessage.success('幼儿档案更新成功')
  } else {
    await addChild(form)
    ElMessage.success('幼儿档案新增成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('删除后不可恢复，确定继续吗？', '提示', { type: 'warning' })
  await deleteChild(id)
  ElMessage.success('删除成功')
  loadData()
}

const resetFilters = () => {
  filters.classId = undefined
  filters.profileStatus = undefined
  pageNum.value = 1
  loadData()
}

onMounted(async () => {
  if (isParent.value) {
    await loadMine()
    return
  }
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

.toolbar,
.child-head,
.toolbar-actions,
.pagination {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  flex-wrap: wrap;
}

.pagination {
  margin-top: 16px;
  justify-content: flex-end;
}

.child-card {
  border: none;
}

.child-name {
  font-size: 20px;
  font-weight: 700;
  color: #3f2f1f;
}

.child-meta {
  margin-top: 6px;
  color: var(--subtle);
}

.child-grid {
  margin-top: 16px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 12px;
  color: #4b5563;
}
</style>
