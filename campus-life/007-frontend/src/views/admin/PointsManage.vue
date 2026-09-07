<template>
  <div>
    <el-card>
      <el-button type="primary" @click="showDialog = true">积分调整</el-button>
    </el-card>

    <el-card style="margin-top: 20px">
      <el-table :data="records" border>
        <el-table-column prop="userName" label="用户" width="120" />
        <el-table-column prop="type" label="类型" width="120">
          <template #default="{ row }">
            <el-tag :type="getTypeTag(row.type)">{{ getTypeText(row.type) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="points" label="积分变动" width="120">
          <template #default="{ row }">
            <span :style="{ color: row.points > 0 ? '#67c23a' : '#f56c6c', fontWeight: 'bold' }">
              {{ row.points > 0 ? '+' : '' }}{{ row.points }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="balance" label="余额" width="120" />
        <el-table-column prop="relatedTitle" label="关联" />
        <el-table-column prop="remark" label="备注" />
        <el-table-column prop="createTime" label="时间" width="180" />
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="积分调整" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="志愿者">
          <el-select v-model="form.userId" placeholder="选择志愿者" style="width: 100%" filterable>
            <el-option v-for="user in users" :key="user.id" :label="user.name" :value="user.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="积分变动">
          <el-input-number v-model="form.points" placeholder="正数为增加，负数为减少" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSave">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getPointsRecords, adjustPoints } from '@/api/points'
import { getUserList } from '@/api/user'

const records = ref([])
const users = ref([])
const showDialog = ref(false)

const form = reactive({
  userId: null,
  points: 0,
  remark: ''
})

const getTypeTag = (type) => {
  const tags = { 1: 'success', 2: 'danger', 3: 'warning' }
  return tags[type]
}

const getTypeText = (type) => {
  const texts = { 1: '活动获得', 2: '兑换扣除', 3: '管理员调整' }
  return texts[type]
}

const loadRecords = async () => {
  const res = await getPointsRecords()
  records.value = res.data
}

const loadUsers = async () => {
  const res = await getUserList({ page: 1, size: 100, role: 'VOLUNTEER' })
  users.value = res.data.records
}

const handleSave = async () => {
  try {
    await adjustPoints(form)
    ElMessage.success('调整成功')
    showDialog.value = false
    Object.assign(form, {
      userId: null,
      points: 0,
      remark: ''
    })
    loadRecords()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadRecords()
  loadUsers()
})
</script>

