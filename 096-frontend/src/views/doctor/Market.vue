<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.departmentId" placeholder="所属科室" clearable style="width: 180px">
          <el-option v-for="item in departments" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-input v-model="query.keyword" placeholder="搜索医生姓名、擅长方向" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>

      <el-row :gutter="16">
        <el-col :span="8" v-for="item in doctors" :key="item.id" style="margin-bottom: 16px">
          <el-card shadow="hover">
            <div class="doctor-card">
              <div class="doctor-name">{{ item.doctorName }}</div>
              <div class="doctor-meta">{{ item.departmentName }} / {{ item.title || '门诊医生' }}</div>
              <div class="doctor-fee">挂号费：¥{{ item.consultationFee }}</div>
              <div class="doctor-desc">{{ item.specialty || '暂无擅长方向说明' }}</div>
              <el-button type="primary" @click="openSchedule(item)">查看排班并挂号</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>

    <el-dialog v-model="scheduleVisible" :title="selectedDoctor?.doctorName + ' 排班信息'" width="820px">
      <div class="search-bar">
        <el-date-picker v-model="scheduleDate" type="date" value-format="YYYY-MM-DD" placeholder="选择日期" />
        <el-button type="primary" @click="loadSchedules">查询排班</el-button>
      </div>
      <el-table :data="schedules" v-loading="scheduleLoading">
        <el-table-column prop="scheduleDate" label="日期" />
        <el-table-column prop="timeSlot" label="时段" />
        <el-table-column prop="clinicRoom" label="诊室" />
        <el-table-column prop="remainingSource" label="剩余号源" />
        <el-table-column prop="fee" label="挂号费" />
        <el-table-column label="操作" width="160">
          <template #default="{ row }">
            <el-button type="primary" link @click="openCreate(row)">立即挂号</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog v-model="createVisible" title="确认挂号" width="520px">
      <el-form :model="createForm" label-width="90px">
        <el-form-item label="就诊卡">
          <el-select v-model="createForm.cardId" style="width: 100%">
            <el-option v-for="item in cards" :key="item.id" :label="`${item.patientName}（${item.cardNo}）`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="createForm.remark" type="textarea" :rows="3" placeholder="如为复诊或特殊情况可填写" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="createVisible = false">取消</el-button>
        <el-button type="primary" @click="submitCreate">提交挂号</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createAppointment, getDepartmentEnabled, getDoctorPublicList, getMyCards, getPublicSchedules } from '../../api'

const doctors = ref([])
const departments = ref([])
const schedules = ref([])
const cards = ref([])
const selectedDoctor = ref(null)
const scheduleVisible = ref(false)
const createVisible = ref(false)
const scheduleLoading = ref(false)
const scheduleDate = ref('')
const query = reactive({ departmentId: undefined, keyword: '' })
const createForm = reactive({ scheduleId: null, cardId: null, remark: '' })

const loadData = async () => {
  const res = await getDoctorPublicList(query)
  doctors.value = res.data || []
}

const loadDepartments = async () => {
  const res = await getDepartmentEnabled()
  departments.value = res.data || []
}

const loadCards = async () => {
  const res = await getMyCards()
  cards.value = res.data || []
}

const openSchedule = async (doctor) => {
  selectedDoctor.value = doctor
  scheduleVisible.value = true
  await loadSchedules()
}

const loadSchedules = async () => {
  if (!selectedDoctor.value) return
  scheduleLoading.value = true
  try {
    const params = { doctorId: selectedDoctor.value.id }
    if (scheduleDate.value) params.scheduleDate = scheduleDate.value
    const res = await getPublicSchedules(params)
    schedules.value = res.data || []
  } finally {
    scheduleLoading.value = false
  }
}

const openCreate = async (row) => {
  await loadCards()
  if (!cards.value.length) {
    ElMessage.warning('请先在就诊卡页面新增就诊卡')
    return
  }
  Object.assign(createForm, { scheduleId: row.id, cardId: cards.value.find(item => item.isDefault === 1)?.id || cards.value[0].id, remark: '' })
  createVisible.value = true
}

const submitCreate = async () => {
  await createAppointment(createForm)
  ElMessage.success('挂号已创建，请前往支付订单完成支付')
  createVisible.value = false
  scheduleVisible.value = false
}

onMounted(async () => {
  await loadDepartments()
  await loadData()
})
</script>

<style scoped>
.page-container { padding: 4px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 16px; }
.doctor-card { display: flex; flex-direction: column; gap: 10px; }
.doctor-name { font-size: 18px; font-weight: 700; color: #0f172a; }
.doctor-meta { color: #475569; }
.doctor-fee { color: #0ea5e9; }
.doctor-desc { color: #334155; min-height: 42px; }
</style>
