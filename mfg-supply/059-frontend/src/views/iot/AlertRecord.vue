<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.alertLevel" placeholder="告警级别" clearable style="width:130px">
          <el-option label="一般" value="normal" /><el-option label="重要" value="important" /><el-option label="紧急" value="urgent" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable style="width:130px">
          <el-option label="未处理" :value="0" /><el-option label="已处理" :value="1" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="equipmentName" label="设备" width="160" />
        <el-table-column prop="alertType" label="告警类型" width="120" />
        <el-table-column prop="alertLevel" label="级别" width="80">
          <template #default="{row}">
            <el-tag :type="{normal:'',important:'warning',urgent:'danger'}[row.alertLevel]">{{ {normal:'一般',important:'重要',urgent:'紧急'}[row.alertLevel] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="告警内容" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="90">
          <template #default="{row}"><el-tag :type="row.status===1?'success':'danger'">{{ row.status===1?'已处理':'未处理' }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="createTime" label="告警时间" width="170" />
        <el-table-column label="操作" width="90">
          <template #default="{row}">
            <el-button link type="primary" @click="handleHandle(row.id)" v-if="row.status===0">处理</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" style="margin-top:15px" @current-change="loadData" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getAlertPage, handleAlert } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, equipmentId: null, alertLevel: '', status: null })

const loadData = async () => { loading.value = true; try { const res = await getAlertPage(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false } }
const handleHandle = async (id) => { await handleAlert(id); ElMessage.success('已处理'); loadData() }
onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
