<template>
  <div class="page-container">
    <el-row :gutter="15">
      <el-col :span="10">
        <el-card>
          <template #header>
            <div style="display: flex; justify-content: space-between">
              <span>届次管理</span>
              <el-button type="primary" size="small" @click="openGradeDialog()">新增</el-button>
            </div>
          </template>
          <el-table :data="grades" @row-click="selectGrade" highlight-current-row>
            <el-table-column prop="name" label="届次名称" />
            <el-table-column prop="year" label="入学年份" />
            <el-table-column label="操作" width="120">
              <template #default="{ row }">
                <el-button link @click.stop="openGradeDialog(row)">编辑</el-button>
                <el-popconfirm title="确定删除?" @confirm="handleDeleteGrade(row.id)">
                  <template #reference><el-button link type="danger" @click.stop>删除</el-button></template>
                </el-popconfirm>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="14">
        <el-card>
          <template #header>
            <div style="display: flex; justify-content: space-between">
              <span>班级管理 {{ currentGrade ? `- ${currentGrade.name}` : '' }}</span>
              <el-button type="primary" size="small" @click="openClassDialog()" :disabled="!currentGrade">新增</el-button>
            </div>
          </template>
          <el-table :data="classes">
            <el-table-column prop="name" label="班级名称" />
            <el-table-column prop="major" label="专业" />
            <el-table-column prop="studentCount" label="人数" />
            <el-table-column label="操作" width="120">
              <template #default="{ row }">
                <el-button link @click="openClassDialog(row)">编辑</el-button>
                <el-popconfirm title="确定删除?" @confirm="handleDeleteClass(row.id)">
                  <template #reference><el-button link type="danger">删除</el-button></template>
                </el-popconfirm>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog v-model="gradeDialog" :title="gradeForm.id ? '编辑届次' : '新增届次'" width="400px">
      <el-form :model="gradeForm" label-width="80px">
        <el-form-item label="届次名称"><el-input v-model="gradeForm.name" /></el-form-item>
        <el-form-item label="入学年份"><el-input-number v-model="gradeForm.year" :min="2000" :max="2030" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="gradeForm.description" type="textarea" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="gradeDialog = false">取消</el-button>
        <el-button type="primary" @click="handleGradeSubmit">确定</el-button>
      </template>
    </el-dialog>
    <el-dialog v-model="classDialog" :title="classForm.id ? '编辑班级' : '新增班级'" width="400px">
      <el-form :model="classForm" label-width="80px">
        <el-form-item label="班级名称"><el-input v-model="classForm.name" /></el-form-item>
        <el-form-item label="专业"><el-input v-model="classForm.major" /></el-form-item>
        <el-form-item label="学生人数"><el-input-number v-model="classForm.studentCount" :min="0" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="classDialog = false">取消</el-button>
        <el-button type="primary" @click="handleClassSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getGradeList, addGrade, updateGrade, deleteGrade, getClassList, addClass, updateClass, deleteClass } from '../../api'

const grades = ref([])
const classes = ref([])
const currentGrade = ref(null)
const gradeDialog = ref(false)
const classDialog = ref(false)
const gradeForm = reactive({ id: null, name: '', year: 2020, description: '' })
const classForm = reactive({ id: null, gradeId: null, name: '', major: '', studentCount: 0 })

const loadGrades = async () => {
  const res = await getGradeList()
  grades.value = res.data
}

const selectGrade = async (row) => {
  currentGrade.value = row
  const res = await getClassList({ gradeId: row.id })
  classes.value = res.data
}

const openGradeDialog = (row = null) => {
  if (row) {
    Object.assign(gradeForm, row)
  } else {
    Object.assign(gradeForm, { id: null, name: '', year: 2020, description: '' })
  }
  gradeDialog.value = true
}

const handleGradeSubmit = async () => {
  if (gradeForm.id) {
    await updateGrade(gradeForm)
  } else {
    await addGrade(gradeForm)
  }
  ElMessage.success('操作成功')
  gradeDialog.value = false
  loadGrades()
}

const handleDeleteGrade = async (id) => {
  await deleteGrade(id)
  ElMessage.success('删除成功')
  loadGrades()
  classes.value = []
  currentGrade.value = null
}

const openClassDialog = (row = null) => {
  if (row) {
    Object.assign(classForm, row)
  } else {
    Object.assign(classForm, { id: null, gradeId: currentGrade.value.id, name: '', major: '', studentCount: 0 })
  }
  classDialog.value = true
}

const handleClassSubmit = async () => {
  if (classForm.id) {
    await updateClass(classForm)
  } else {
    await addClass(classForm)
  }
  ElMessage.success('操作成功')
  classDialog.value = false
  selectGrade(currentGrade.value)
}

const handleDeleteClass = async (id) => {
  await deleteClass(id)
  ElMessage.success('删除成功')
  selectGrade(currentGrade.value)
}

onMounted(loadGrades)
</script>

<style scoped>
.page-container { padding: 10px; }
</style>
