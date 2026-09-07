<template>
  <div class="wrong-container">
    <el-card>
      <template #header>
        <h3>我的错题本</h3>
      </template>

      <el-table :data="wrongList">
        <el-table-column prop="questionId" label="题目ID" width="100" />
        <el-table-column prop="userAnswer" label="我的答案" width="150" />
        <el-table-column prop="wrongCount" label="错误次数" width="100">
          <template #default="{ row }">
            <el-tag type="danger">{{ row.wrongCount }} 次</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="最后错误时间" width="180" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="viewQuestion(row.questionId)">查看题目</el-button>
            <el-button link type="danger" @click="removeWrong(row.id)">移除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getWrongQuestionList, removeWrongQuestion } from '@/api/question'
import { ElMessage, ElMessageBox } from 'element-plus'

const wrongList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const loadData = async () => {
  try {
    const res = await getWrongQuestionList({
      page: currentPage.value,
      size: pageSize.value
    })
    wrongList.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error(error)
  }
}

const viewQuestion = (questionId) => {
  ElMessage.info('查看题目功能')
}

const removeWrong = async (id) => {
  try {
    await ElMessageBox.confirm('确定要移除这道错题吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await removeWrongQuestion(id)
    ElMessage.success('移除成功')
    loadData()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.wrong-container {
  max-width: 1400px;
  margin: 0 auto;
}
</style>

