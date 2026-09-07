<template>
  <div class="pets">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>我的宠物</span>
          <el-button type="primary" @click="showDialog()"><el-icon><Plus /></el-icon>添加宠物</el-button>
        </div>
      </template>
      <el-table :data="petList" v-loading="loading">
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="type" label="类型" width="100" />
        <el-table-column prop="breed" label="品种" />
        <el-table-column prop="age" label="年龄" width="80">
          <template #default="{ row }">{{ row.age }}岁</template>
        </el-table-column>
        <el-table-column prop="gender" label="性别" width="80" />
        <el-table-column prop="weight" label="体重" width="100">
          <template #default="{ row }">{{ row.weight }}kg</template>
        </el-table-column>
        <el-table-column prop="healthStatus" label="健康状况" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="primary" link @click="showDialog(row)">编辑</el-button>
            <el-button type="danger" link @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑宠物' : '添加宠物'" width="500">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入宠物名称" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型" style="width: 100%">
            <el-option label="猫" value="猫" />
            <el-option label="狗" value="狗" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="品种" prop="breed">
          <el-input v-model="form.breed" placeholder="请输入品种" />
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="form.age" :min="0" :max="50" />
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.gender">
            <el-radio value="公">公</el-radio>
            <el-radio value="母">母</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="体重">
          <el-input-number v-model="form.weight" :min="0" :precision="1" />
        </el-form-item>
        <el-form-item label="健康状况">
          <el-input v-model="form.healthStatus" placeholder="请输入健康状况" />
        </el-form-item>
        <el-form-item label="疫苗接种">
          <el-input v-model="form.vaccination" placeholder="请输入疫苗接种情况" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitLoading">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getPetList, addPet, updatePet, deletePet } from '../api'
import { Plus } from '@element-plus/icons-vue'

const petList = ref<any[]>([])
const loading = ref(false)
const dialogVisible = ref(false)
const submitLoading = ref(false)
const formRef = ref()

const defaultForm = {
  id: null,
  name: '',
  type: '',
  breed: '',
  age: 1,
  gender: '公',
  weight: 5,
  healthStatus: '健康',
  vaccination: '',
  remark: ''
}

const form = reactive({ ...defaultForm })

const rules = {
  name: [{ required: true, message: '请输入宠物名称', trigger: 'blur' }],
  type: [{ required: true, message: '请选择类型', trigger: 'change' }],
  age: [{ required: true, message: '请输入年龄', trigger: 'blur' }]
}

const loadPets = async () => {
  loading.value = true
  try {
    const res: any = await getPetList()
    petList.value = res.data || []
  } finally {
    loading.value = false
  }
}

const showDialog = (row?: any) => {
  if (row) {
    Object.assign(form, row)
  } else {
    Object.assign(form, defaultForm)
  }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  submitLoading.value = true
  try {
    if (form.id) {
      await updatePet(form)
      ElMessage.success('修改成功')
    } else {
      await addPet(form)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    loadPets()
  } finally {
    submitLoading.value = false
  }
}

const handleDelete = async (id: number) => {
  await ElMessageBox.confirm('确定删除该宠物吗？', '提示', { type: 'warning' })
  await deletePet(id)
  ElMessage.success('删除成功')
  loadPets()
}

onMounted(() => loadPets())
</script>

<style scoped>
.pets {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
