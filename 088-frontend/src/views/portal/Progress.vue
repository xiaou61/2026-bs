<template>
  <div class="portal-container progress-page">
    <el-card shadow="never">
      <template #header>
        <div class="page-title">申请进度查询</div>
      </template>
      <el-table :data="list">
        <el-table-column prop="applicationNo" label="申请编号" min-width="160" />
        <el-table-column prop="childName" label="儿童" min-width="100" />
        <el-table-column label="当前状态" min-width="120">
          <template #default="{ row }">{{ applicationStatusMap[row.status] }}</template>
        </el-table-column>
        <el-table-column prop="reviewRemark" label="审核说明" min-width="200" show-overflow-tooltip />
        <el-table-column prop="submitTime" label="提交时间" min-width="160" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getMyApplicationList } from '../../api'
import { applicationStatusMap } from '../../utils'

const list = ref([])

onMounted(async () => {
  const res = await getMyApplicationList({ pageNum: 1, pageSize: 20 })
  list.value = res.data?.list || []
})
</script>

<style scoped>
.progress-page {
  padding-top: 28px;
}

.page-title {
  font-size: 22px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}
</style>
