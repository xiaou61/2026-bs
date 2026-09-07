<template>
  <div class="page-shell portal-container">
    <el-card shadow="never">
      <div class="toolbar">
        <el-input v-model="query.name" placeholder="按姓名搜索" clearable />
        <el-select v-model="query.gender" placeholder="性别" clearable>
          <el-option :value="1" label="男" />
          <el-option :value="2" label="女" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
      <div class="child-grid">
        <article v-for="item in list" :key="item.id" class="child-card" @click="goDetail(item.id)">
          <img :src="item.avatarUrl" :alt="item.name">
          <div class="child-info">
            <div class="child-name">{{ item.name }}</div>
            <div class="child-meta">{{ item.age }} 岁 · {{ item.gender === 1 ? '男' : '女' }} · {{ adoptionStatusMap[item.adoptionStatus] }}</div>
            <div class="child-summary">{{ item.summary }}</div>
          </div>
        </article>
      </div>
      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        layout="total, prev, pager, next"
        :total="total"
        @current-change="loadData"
      />
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { getPublicChildList } from '../../api'
import { adoptionStatusMap } from '../../utils'

const router = useRouter()
const list = ref([])
const total = ref(0)
const query = reactive({
  pageNum: 1,
  pageSize: 8,
  name: '',
  gender: null
})

const loadData = async () => {
  const res = await getPublicChildList(query)
  list.value = res.data?.list || []
  total.value = res.data?.total || 0
}

const goDetail = (id) => {
  router.push(`/children/${id}`)
}

onMounted(loadData)
</script>

<style scoped>
.page-shell {
  padding-top: 28px;
}

.toolbar {
  display: flex;
  gap: 12px;
  margin-bottom: 20px;
}

.child-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 18px;
  margin-bottom: 20px;
}

.child-card {
  overflow: hidden;
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.88);
  cursor: pointer;
}

.child-card img {
  width: 100%;
  height: 220px;
  object-fit: cover;
}

.child-info {
  padding: 16px;
}

.child-name {
  font-size: 18px;
  font-weight: 700;
}

.child-meta,
.child-summary {
  margin-top: 8px;
  color: var(--subtle);
  line-height: 1.7;
}
</style>
