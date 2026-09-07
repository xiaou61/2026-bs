<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <template #header>楼栋列表</template>
          <el-button type="primary" size="small" @click="addBuilding" style="margin-bottom: 10px;">添加楼栋</el-button>
          <el-menu @select="selectBuilding">
            <el-menu-item v-for="b in buildings" :key="b.id" :index="String(b.id)">{{ b.name }}</el-menu-item>
          </el-menu>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card>
          <template #header>
            <div style="display: flex; justify-content: space-between; align-items: center;">
              <span>房间与床位</span>
              <el-button v-if="selectedBuilding" type="primary" size="small" @click="addRoom">添加房间</el-button>
            </div>
          </template>
          <el-table :data="rooms" v-loading="loading">
            <el-table-column prop="roomNo" label="房间号" />
            <el-table-column prop="roomType" label="房间类型">
              <template #default="{ row }">{{ roomTypeMap[row.roomType] }}</template>
            </el-table-column>
            <el-table-column label="床位">
              <template #default="{ row }">
                <el-tag v-for="bed in row.beds" :key="bed.id" :type="bed.status === 0 ? 'success' : 'danger'" style="margin-right: 5px;">
                  {{ bed.bedNo }} - {{ bed.status === 0 ? '空闲' : '占用' }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getBuildingList, getRoomList, getRoomBeds } from '@/api'
import request from '@/utils/request'
import { ElMessage, ElMessageBox } from 'element-plus'

const roomTypeMap: Record<number, string> = { 1: '单人间', 2: '双人间', 3: '三人间', 4: '多人间' }
const buildings = ref<any[]>([])
const rooms = ref<any[]>([])
const loading = ref(false)
const selectedBuilding = ref<number | null>(null)

const loadBuildings = async () => {
  const res: any = await getBuildingList()
  buildings.value = res.data
}

const selectBuilding = async (id: string) => {
  selectedBuilding.value = Number(id)
  loading.value = true
  try {
    const res: any = await getRoomList(Number(id))
    rooms.value = res.data
    for (const room of rooms.value) {
      const bedRes: any = await getRoomBeds(room.id)
      room.beds = bedRes.data
    }
  } finally {
    loading.value = false
  }
}

const addBuilding = async () => {
  const { value } = await ElMessageBox.prompt('请输入楼栋名称', '添加楼栋')
  if (value) {
    await request.post('/admin/building', { name: value })
    ElMessage.success('添加成功')
    loadBuildings()
  }
}

const addRoom = async () => {
  const { value } = await ElMessageBox.prompt('请输入房间号', '添加房间')
  if (value) {
    await request.post('/admin/room', { buildingId: selectedBuilding.value, roomNo: value, roomType: 1 })
    ElMessage.success('添加成功')
    selectBuilding(String(selectedBuilding.value))
  }
}

onMounted(loadBuildings)
</script>
