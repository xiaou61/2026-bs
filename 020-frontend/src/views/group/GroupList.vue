<template>
  <div class="group-container">
    <el-card>
      <template #header>
        <div class="header-actions">
          <h3>学习小组</h3>
          <el-button type="primary" @click="showCreateDialog = true">
            <el-icon><Plus /></el-icon>
            创建小组
          </el-button>
        </div>
      </template>

      <el-row :gutter="20">
        <el-col :span="6" v-for="group in groupList" :key="group.id">
          <el-card class="group-card" @click="goToDetail(group.id)">
            <div class="group-info">
              <h4>{{ group.name }}</h4>
              <p class="group-desc">{{ group.description }}</p>
              <div class="group-meta">
                <el-tag size="small">{{ group.category }}</el-tag>
                <span>{{ group.currentMembers }}/{{ group.maxMembers }} 人</span>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>

    <el-dialog v-model="showCreateDialog" title="创建学习小组" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="小组名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入小组名称" />
        </el-form-item>
        <el-form-item label="小组描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="小组分类" prop="category">
          <el-select v-model="form.category" placeholder="请选择分类">
            <el-option label="编程" value="编程" />
            <el-option label="数学" value="数学" />
            <el-option label="算法" value="算法" />
          </el-select>
        </el-form-item>
        <el-form-item label="最大人数" prop="maxMembers">
          <el-input-number v-model="form.maxMembers" :min="2" :max="200" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCreateDialog = false">取消</el-button>
        <el-button type="primary" @click="handleCreate">创建</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getGroupList, createGroup } from '@/api/group'
import { ElMessage } from 'element-plus'

const router = useRouter()
const groupList = ref([])
const currentPage = ref(1)
const pageSize = ref(12)
const total = ref(0)
const showCreateDialog = ref(false)
const formRef = ref(null)

const form = ref({
  name: '',
  description: '',
  category: '',
  maxMembers: 50
})

const rules = {
  name: [{ required: true, message: '请输入小组名称', trigger: 'blur' }],
  description: [{ required: true, message: '请输入小组描述', trigger: 'blur' }],
  category: [{ required: true, message: '请选择分类', trigger: 'change' }]
}

const loadData = async () => {
  try {
    const res = await getGroupList({
      page: currentPage.value,
      size: pageSize.value
    })
    groupList.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error(error)
  }
}

const handleCreate = async () => {
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await createGroup(form.value)
        ElMessage.success('创建成功')
        showCreateDialog.value = false
        loadData()
      } catch (error) {
        console.error(error)
      }
    }
  })
}

const goToDetail = (id) => {
  router.push(`/group/${id}`)
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.group-container {
  max-width: 1400px;
  margin: 0 auto;
}

.header-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-actions h3 {
  margin: 0;
}

.group-card {
  cursor: pointer;
  margin-bottom: 20px;
  transition: transform 0.3s;
}

.group-card:hover {
  transform: translateY(-5px);
}

.group-info h4 {
  margin: 0 0 10px 0;
}

.group-desc {
  color: #606266;
  font-size: 14px;
  margin: 10px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.group-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #909399;
  font-size: 12px;
}
</style>

