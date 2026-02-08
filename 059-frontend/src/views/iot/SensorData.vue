<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.equipmentId" placeholder="选择设备" clearable style="width:200px" @change="loadData">
          <el-option v-for="e in equipments" :key="e.id" :label="e.name" :value="e.id" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="warning" @click="showTrend" :disabled="!query.equipmentId">查看趋势</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="equipmentName" label="设备" width="160" />
        <el-table-column prop="temperature" label="温度(°C)" width="100" />
        <el-table-column prop="pressure" label="压力(MPa)" width="100" />
        <el-table-column prop="vibration" label="振动(mm/s)" width="110" />
        <el-table-column prop="speed" label="转速(rpm)" width="100" />
        <el-table-column prop="collectTime" label="采集时间" />
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" style="margin-top:15px" @current-change="loadData" />
    </el-card>
    <el-dialog v-model="trendVisible" title="传感器数据趋势" width="800px">
      <div ref="trendChartRef" style="height:400px"></div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, nextTick } from 'vue'
import * as echarts from 'echarts'
import { getSensorDataPage, getSensorTrend, getEquipmentPage } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const equipments = ref([])
const trendVisible = ref(false)
const trendChartRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, equipmentId: null })

const loadEquipments = async () => { const res = await getEquipmentPage({ pageNum: 1, pageSize: 100 }); equipments.value = res.data.records }
const loadData = async () => { loading.value = true; try { const res = await getSensorDataPage(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false } }
const showTrend = async () => {
  trendVisible.value = true
  const res = await getSensorTrend(query.equipmentId, { limit: 20 })
  await nextTick()
  const chart = echarts.init(trendChartRef.value)
  const times = res.data.map(i => i.collectTime?.substring(11, 16) || '')
  chart.setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['温度', '压力', '振动', '转速'] },
    xAxis: { type: 'category', data: times },
    yAxis: [{ type: 'value', name: '温度/压力/振动' }, { type: 'value', name: '转速' }],
    series: [
      { name: '温度', type: 'line', smooth: true, data: res.data.map(i => i.temperature) },
      { name: '压力', type: 'line', smooth: true, data: res.data.map(i => i.pressure) },
      { name: '振动', type: 'line', smooth: true, data: res.data.map(i => i.vibration) },
      { name: '转速', type: 'line', smooth: true, yAxisIndex: 1, data: res.data.map(i => i.speed) }
    ]
  })
}
onMounted(() => { loadEquipments(); loadData() })
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
