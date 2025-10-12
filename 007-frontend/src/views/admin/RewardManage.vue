<template>
  <div>
    <el-card>
      <el-button type="primary" @click="showDialog = true">添加商品</el-button>
    </el-card>

    <el-card style="margin-top: 20px">
      <el-table :data="rewards" border>
        <el-table-column prop="name" label="商品名称" />
        <el-table-column prop="description" label="商品描述" />
        <el-table-column prop="points" label="所需积分" width="120">
          <template #default="{ row }">
            <span style="color: #409eff; font-weight: bold">{{ row.points }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="stock" label="库存" width="100" />
        <el-table-column prop="exchangeCount" label="兑换次数" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '上架' : '下架' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" :type="row.status === 1 ? 'warning' : 'success'" @click="handleStatus(row)">
              {{ row.status === 1 ? '下架' : '上架' }}
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" :title="form.id ? '编辑商品' : '添加商品'" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="商品名称">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="商品描述">
          <el-input v-model="form.description" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="所需积分">
          <el-input-number v-model="form.points" :min="0" />
        </el-form-item>
        <el-form-item label="库存数量">
          <el-input-number v-model="form.stock" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getRewardList, createReward, updateReward, deleteReward, updateRewardStatus } from '@/api/reward'

const rewards = ref([])
const showDialog = ref(false)

const form = reactive({
  id: null,
  name: '',
  description: '',
  points: 50,
  stock: 100
})

const loadRewards = async () => {
  const res = await getRewardList({ page: 1, size: 100 })
  rewards.value = res.data.records
}

const handleEdit = (row) => {
  Object.assign(form, row)
  showDialog.value = true
}

const handleSave = async () => {
  try {
    if (form.id) {
      await updateReward(form.id, form)
      ElMessage.success('更新成功')
    } else {
      await createReward(form)
      ElMessage.success('添加成功')
    }
    showDialog.value = false
    resetForm()
    loadRewards()
  } catch (error) {
    console.error(error)
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除吗?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteReward(id)
    ElMessage.success('删除成功')
    loadRewards()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

const handleStatus = async (row) => {
  try {
    const newStatus = row.status === 1 ? 0 : 1
    await updateRewardStatus(row.id, newStatus)
    ElMessage.success('状态更新成功')
    loadRewards()
  } catch (error) {
    console.error(error)
  }
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    name: '',
    description: '',
    points: 50,
    stock: 100
  })
}

onMounted(() => {
  loadRewards()
})
</script>

