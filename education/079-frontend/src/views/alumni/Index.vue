<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="姓名" style="width: 150px" clearable />
        <el-select v-model="query.gradeId" placeholder="届次" clearable style="width: 120px">
          <el-option v-for="g in grades" :key="g.id" :label="g.name" :value="g.id" />
        </el-select>
        <el-input v-model="query.company" placeholder="公司" style="width: 150px" clearable />
        <el-input v-model="query.city" placeholder="城市" style="width: 120px" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="userName" label="姓名" />
        <el-table-column prop="studentNo" label="学号" />
        <el-table-column prop="gradeName" label="届次" />
        <el-table-column prop="className" label="班级" />
        <el-table-column prop="company" label="公司" />
        <el-table-column prop="position" label="职位" />
        <el-table-column prop="city" label="城市" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button link type="primary" @click="$router.push(`/alumni/${row.id}`)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="total, prev, pager, next" :total="total" v-model:current-page="query.pageNum" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getAlumniList, getGradeList } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const grades = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, name: '', gradeId: null, company: '', city: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getAlumniList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  const gradeRes = await getGradeList()
  grades.value = gradeRes.data
  loadData()
})
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
