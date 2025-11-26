<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import request from '@/api/request'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const form = ref({
  status: 'AVAILABLE'
})
const isEdit = ref(false)

onMounted(() => {
  const id = route.params.id
  if (id) {
    isEdit.value = true
    request.get(`/pet/${id}`).then(res => {
      form.value = res.data
    })
  }
})

const save = () => {
  if (isEdit.value) {
    request.put('/pet/update', form.value).then(res => {
      if (res.code === 200) {
        ElMessage.success('Updated successfully')
        router.push('/pet/list')
      } else {
        ElMessage.error(res.msg)
      }
    })
  } else {
    request.post('/pet/add', form.value).then(res => {
      if (res.code === 200) {
        ElMessage.success('Added successfully')
        router.push('/pet/list')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }
}

const cancel = () => {
  router.back()
}
</script>

<template>
  <div class="pet-form-container">
    <h2>{{ isEdit ? 'Edit Pet' : 'Add New Pet' }}</h2>
    <el-form :model="form" label-width="120px" style="max-width: 600px">
      <el-form-item label="Name">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="Type">
        <el-select v-model="form.type" placeholder="Select Type">
          <el-option label="Cat" value="Cat" />
          <el-option label="Dog" value="Dog" />
          <el-option label="Other" value="Other" />
        </el-select>
      </el-form-item>
      <el-form-item label="Breed">
        <el-input v-model="form.breed" />
      </el-form-item>
      <el-form-item label="Age">
        <el-input v-model="form.age" />
      </el-form-item>
      <el-form-item label="Gender">
        <el-select v-model="form.gender" placeholder="Select Gender">
          <el-option label="Male" value="Male" />
          <el-option label="Female" value="Female" />
        </el-select>
      </el-form-item>
      <el-form-item label="Status">
        <el-select v-model="form.status">
          <el-option label="Available" value="AVAILABLE" />
          <el-option label="Pending" value="PENDING" />
          <el-option label="Adopted" value="ADOPTED" />
        </el-select>
      </el-form-item>
      <el-form-item label="Image URL">
        <el-input v-model="form.imageUrl" placeholder="http://..." />
      </el-form-item>
      <el-form-item label="Health Status">
        <el-input v-model="form.healthStatus" />
      </el-form-item>
      <el-form-item label="Vaccine Status">
        <el-input v-model="form.vaccineStatus" />
      </el-form-item>
      <el-form-item label="Description">
        <el-input v-model="form.description" type="textarea" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">Save</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped>
.pet-form-container {
  padding: 20px;
}
</style>
