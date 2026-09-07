<script setup>
import { ref, onMounted } from 'vue'
import request from '@/api/request'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()
const tableData = ref([])
const search = ref({
  type: '',
  status: ''
})

const loadData = () => {
  request.get('/pet/list', { params: search.value }).then(res => {
    tableData.value = res.data
  })
}

const handleAdd = () => {
  router.push('/pet/add')
}

const handleEdit = (row) => {
  router.push(`/pet/edit/${row.id}`)
}

const handleDelete = (row) => {
  ElMessageBox.confirm('Are you sure to delete this pet?', 'Warning', {
    confirmButtonText: 'Yes',
    cancelButtonText: 'No',
    type: 'warning'
  }).then(() => {
    request.delete(`/pet/delete/${row.id}`).then(res => {
      if (res.code === 200) {
        ElMessage.success('Deleted successfully')
        loadData()
      } else {
        ElMessage.error(res.msg)
      }
    })
  })
}

onMounted(() => {
  loadData()
})
</script>

<template>
  <div class="pet-list-container">
    <div class="search-bar">
      <el-input v-model="search.type" placeholder="Type (e.g., Cat, Dog)" style="width: 200px; margin-right: 10px" />
      <el-select v-model="search.status" placeholder="Status" style="width: 200px; margin-right: 10px" clearable>
        <el-option label="Available" value="AVAILABLE" />
        <el-option label="Pending" value="PENDING" />
        <el-option label="Adopted" value="ADOPTED" />
      </el-select>
      <el-button type="primary" @click="loadData">Search</el-button>
      <el-button type="success" @click="handleAdd">Add Pet</el-button>
    </div>

    <el-table :data="tableData" style="width: 100%; margin-top: 20px" border stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column label="Image" width="120">
        <template #default="scope">
          <el-image :src="scope.row.imageUrl" style="width: 100px; height: 100px" fit="cover" />
        </template>
      </el-table-column>
      <el-table-column prop="name" label="Name" width="120" />
      <el-table-column prop="type" label="Type" width="100" />
      <el-table-column prop="breed" label="Breed" width="120" />
      <el-table-column prop="gender" label="Gender" width="80" />
      <el-table-column prop="age" label="Age" width="80" />
      <el-table-column prop="status" label="Status" width="120">
        <template #default="scope">
          <el-tag :type="scope.row.status === 'AVAILABLE' ? 'success' : (scope.row.status === 'ADOPTED' ? 'info' : 'warning')">
            {{ scope.row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Actions">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">Edit</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style scoped>
.pet-list-container {
  padding: 20px;
}
</style>
