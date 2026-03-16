<template>
  <div class="page-container">
    <el-card shadow="never">
      <template #header>
        <div class="page-title">申请人档案</div>
      </template>
      <el-table :data="list">
        <el-table-column prop="realName" label="姓名" min-width="100" />
        <el-table-column prop="phone" label="手机号" min-width="120" />
        <el-table-column prop="maritalStatus" label="婚姻情况" min-width="100" />
        <el-table-column prop="occupation" label="职业" min-width="120" />
        <el-table-column prop="incomeLevel" label="收入水平" min-width="120" />
        <el-table-column prop="address" label="住址" min-width="180" show-overflow-tooltip />
        <el-table-column prop="adoptionReason" label="收养意愿" min-width="220" show-overflow-tooltip />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getAdopterList } from '../../../api'

const list = ref([])

onMounted(async () => {
  const res = await getAdopterList({ pageNum: 1, pageSize: 50 })
  list.value = res.data?.list || []
})
</script>

<style scoped>
.page-title {
  font-size: 22px;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}
</style>
