<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-button type="success" @click="openDialog()" v-if="userStore.isAdmin()">新增项目</el-button>
      </div>
      <el-row :gutter="15">
        <el-col :span="8" v-for="item in tableData" :key="item.id">
          <el-card shadow="hover" class="project-card" @click="$router.push(`/donation/${item.id}`)">
            <h3>{{ item.name }}</h3>
            <el-progress :percentage="Math.round(item.currentAmount / item.targetAmount * 100)" :stroke-width="10" />
            <div class="amount">
              <span>已筹 ¥{{ item.currentAmount }}</span>
              <span>目标 ¥{{ item.targetAmount }}</span>
            </div>
            <div class="desc">{{ item.description?.substring(0, 50) }}...</div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>
    <el-dialog v-model="dialogVisible" title="新增捐赠项目" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="项目名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="目标金额"><el-input-number v-model="form.targetAmount" :min="0" /></el-form-item>
        <el-form-item label="开始时间"><el-date-picker v-model="form.startTime" type="datetime" /></el-form-item>
        <el-form-item label="结束时间"><el-date-picker v-model="form.endTime" type="datetime" /></el-form-item>
        <el-form-item label="项目描述"><el-input v-model="form.description" type="textarea" :rows="4" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getDonationProjects, addDonationProject } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const tableData = ref([])
const dialogVisible = ref(false)
const form = reactive({ name: '', description: '', targetAmount: 0, startTime: '', endTime: '' })

const loadData = async () => {
  const res = await getDonationProjects({ pageNum: 1, pageSize: 100 })
  tableData.value = res.data.records
}

const openDialog = () => {
  Object.assign(form, { name: '', description: '', targetAmount: 0, startTime: '', endTime: '' })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await addDonationProject(form)
  ElMessage.success('创建成功')
  dialogVisible.value = false
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { margin-bottom: 15px; }
.project-card { cursor: pointer; margin-bottom: 15px; }
.project-card h3 { margin-bottom: 15px; }
.amount { display: flex; justify-content: space-between; margin-top: 10px; color: #999; font-size: 14px; }
.desc { margin-top: 10px; color: #666; font-size: 14px; }
</style>
