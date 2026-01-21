<template>
  <el-tabs v-model="activeTab">
    <el-tab-pane label="参观预约" name="reservation">
      <el-table :data="reservations">
        <el-table-column prop="exhibitionTitle" label="展览" />
        <el-table-column prop="visitDate" label="参观日期" width="120" />
        <el-table-column prop="timeSlot" label="时间段" width="120" />
        <el-table-column prop="visitorCount" label="人数" width="80" />
        <el-table-column prop="totalPrice" label="总价" width="100"><template #default="{row}">¥{{ row.totalPrice }}</template></el-table-column>
        <el-table-column label="状态" width="100"><template #default="{row}"><el-tag :type="statusType[row.status]">{{ statusText[row.status] }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="{row}"><el-button v-if="row.status===0" type="danger" link @click="handleCancel(row.id)">取消</el-button></template>
        </el-table-column>
      </el-table>
    </el-tab-pane>
    <el-tab-pane label="讲解预约" name="guide">
      <el-table :data="guideBookings">
        <el-table-column prop="guideName" label="讲解员" width="120" />
        <el-table-column prop="visitDate" label="预约日期" width="120" />
        <el-table-column prop="startTime" label="开始时间" width="100" />
        <el-table-column prop="duration" label="时长" width="100"><template #default="{row}">{{ row.duration }}分钟</template></el-table-column>
        <el-table-column prop="language" label="语言" width="80" />
        <el-table-column prop="price" label="价格" width="100"><template #default="{row}">¥{{ row.price }}</template></el-table-column>
        <el-table-column label="状态" width="100"><template #default="{row}"><el-tag :type="guideStatusType[row.status]">{{ guideStatusText[row.status] }}</el-tag></template></el-table-column>
      </el-table>
    </el-tab-pane>
  </el-tabs>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getMyReservations, getMyGuideBookings, cancelReservation } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'
const activeTab = ref('reservation')
const reservations = ref<any[]>([]), guideBookings = ref<any[]>([])
const statusText = ['待确认','已确认','已完成','已取消'], statusType = ['warning','primary','success','info'] as const
const guideStatusText = ['待确认','已确认','进行中','已完成','已取消'], guideStatusType = ['warning','primary','','success','info'] as const
const loadData = async () => { const [r,g]:any = await Promise.all([getMyReservations({}), getMyGuideBookings({})]); reservations.value = r.data; guideBookings.value = g.data }
const handleCancel = async (id:number) => { await ElMessageBox.confirm('确认取消?'); await cancelReservation(id); ElMessage.success('已取消'); loadData() }
onMounted(loadData)
</script>
