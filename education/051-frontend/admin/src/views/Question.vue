<template>
  <div>
    <el-card>
      <template #header><div style="display:flex;justify-content:space-between"><span>题库管理</span><el-button type="primary" @click="openDialog()">新增题目</el-button></div></template>
      <el-table :data="list" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="content" label="题目" show-overflow-tooltip />
        <el-table-column prop="answer" label="答案" width="80" />
        <el-table-column prop="difficulty" label="难度" width="80" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="primary" link @click="openDialog(row)">编辑</el-button>
            <el-button type="danger" link @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="page" :page-size="10" :total="total" @current-change="loadData" style="margin-top: 20px" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑题目' : '新增题目'" width="600px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="分类"><el-select v-model="form.categoryId"><el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" /></el-select></el-form-item>
        <el-form-item label="题目"><el-input v-model="form.content" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="选项"><el-input v-model="form.options" placeholder='["A. xxx", "B. xxx", "C. xxx", "D. xxx"]' /></el-form-item>
        <el-form-item label="答案"><el-input v-model="form.answer" placeholder="A/B/C/D" /></el-form-item>
        <el-form-item label="解析"><el-input v-model="form.explanation" type="textarea" /></el-form-item>
        <el-form-item label="难度"><el-select v-model="form.difficulty"><el-option :value="1" label="简单" /><el-option :value="2" label="中等" /><el-option :value="3" label="困难" /></el-select></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '../api'

const list = ref([])
const categories = ref([])
const total = ref(0)
const page = ref(1)
const dialogVisible = ref(false)
const form = ref({ categoryId: '', content: '', options: '', answer: '', explanation: '', difficulty: 1 })

const loadData = async () => {
  const res = await api.getQuestionList({ page: page.value, size: 10 })
  list.value = res.records
  total.value = res.total
}

const loadCategories = async () => {
  const res = await api.getCategoryList({ page: 1, size: 100 })
  categories.value = res.records
}

const openDialog = (row) => {
  form.value = row ? { ...row } : { categoryId: '', content: '', options: '', answer: '', explanation: '', difficulty: 1 }
  dialogVisible.value = true
}

const handleSave = async () => {
  await api.saveQuestion(form.value)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('确定删除？')
  await api.deleteQuestion(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(() => { loadData(); loadCategories() })
</script>
