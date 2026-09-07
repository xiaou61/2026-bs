<template>
  <el-card>
    <template #header>店铺管理</template>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="name" label="店铺名称" />
      <el-table-column prop="address" label="地址" />
      <el-table-column prop="phone" label="电话" />
      <el-table-column prop="rating" label="评分" />
      <el-table-column prop="status" label="状态"><template #default="{row}"><el-tag :type="row.status===1?'success':'danger'">{{ row.status===1?'营业':'关闭' }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="{row}">
          <el-button v-if="row.status===1" type="danger" link size="small" @click="handleStatus(row.id,0)">关闭</el-button>
          <el-button v-else type="success" link size="small" @click="handleStatus(row.id,1)">开启</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getAdminShops, updateShopStatus } from '@/api'
import { ElMessage } from 'element-plus'
const loading = ref(false), list = ref<any[]>([]), total = ref(0)
const query = reactive({ current:1, size:10 })
const loadData = async () => { loading.value=true; const res:any=await getAdminShops(query); list.value=res.data.records; total.value=res.data.total; loading.value=false }
const handleStatus = async (id:number,status:number) => { await updateShopStatus(id,status); ElMessage.success('操作成功'); loadData() }
onMounted(loadData)
</script>
