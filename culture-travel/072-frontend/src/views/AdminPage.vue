<template>
  <div class="admin-page">
    <el-tabs v-model="activeTab">
      <el-tab-pane label="用户管理" name="users">
        <el-table :data="users" v-loading="loading">
          <el-table-column prop="id" label="ID" width="80" />
          <el-table-column prop="username" label="用户名" width="120" />
          <el-table-column prop="nickname" label="昵称" width="120" />
          <el-table-column prop="phone" label="手机号" width="130" />
          <el-table-column prop="email" label="邮箱" />
          <el-table-column prop="balance" label="余额" width="100">
            <template #default="{ row }"><span style="color:#f56c6c">¥{{ row.balance }}</span></template>
          </el-table-column>
          <el-table-column prop="role" label="角色" width="100">
            <template #default="{ row }"><el-tag :type="row.role === 'admin' ? 'danger' : ''">{{ row.role === 'admin' ? '管理员' : '用户' }}</el-tag></template>
          </el-table-column>
          <el-table-column prop="createTime" label="注册时间" width="170" />
          <el-table-column label="操作" width="120" fixed="right">
            <template #default="{ row }">
              <el-button type="danger" link @click="deleteUser(row)" :disabled="row.role === 'admin'">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="景点管理" name="spots">
        <div class="toolbar"><el-button type="primary" @click="openSpotForm()">添加景点</el-button></div>
        <el-table :data="spots" v-loading="loading">
          <el-table-column prop="id" label="ID" width="80" />
          <el-table-column prop="name" label="名称" width="150" />
          <el-table-column prop="category" label="分类" width="100" />
          <el-table-column prop="address" label="地址" show-overflow-tooltip />
          <el-table-column prop="ticketPrice" label="票价" width="80" />
          <el-table-column prop="viewCount" label="浏览量" width="80" />
          <el-table-column label="操作" width="150" fixed="right">
            <template #default="{ row }">
              <el-button type="primary" link @click="openSpotForm(row)">编辑</el-button>
              <el-button type="danger" link @click="deleteSpot(row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="订单管理" name="orders">
        <el-table :data="orders" v-loading="loading">
          <el-table-column prop="orderNo" label="订单号" width="200" />
          <el-table-column prop="nickname" label="用户" width="100" />
          <el-table-column prop="spotName" label="景点" width="150" />
          <el-table-column prop="ticketName" label="票种" width="100" />
          <el-table-column prop="quantity" label="数量" width="60" />
          <el-table-column prop="totalAmount" label="金额" width="80">
            <template #default="{ row }"><span style="color:#f56c6c">¥{{ row.totalAmount }}</span></template>
          </el-table-column>
          <el-table-column prop="status" label="状态" width="80">
            <template #default="{ row }"><el-tag :type="statusType[row.status]">{{ statusText[row.status] }}</el-tag></template>
          </el-table-column>
          <el-table-column prop="visitDate" label="游玩日期" width="110" />
          <el-table-column prop="createTime" label="下单时间" width="170" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="公告管理" name="announcements">
        <div class="toolbar"><el-button type="primary" @click="openAnnouncementForm()">发布公告</el-button></div>
        <el-table :data="announcements" v-loading="loading">
          <el-table-column prop="id" label="ID" width="80" />
          <el-table-column prop="title" label="标题" />
          <el-table-column prop="isTop" label="置顶" width="80">
            <template #default="{ row }"><el-tag v-if="row.isTop" type="danger">是</el-tag></template>
          </el-table-column>
          <el-table-column prop="createTime" label="发布时间" width="170" />
          <el-table-column label="操作" width="150" fixed="right">
            <template #default="{ row }">
              <el-button type="primary" link @click="openAnnouncementForm(row)">编辑</el-button>
              <el-button type="danger" link @click="deleteAnnouncement(row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
    <el-dialog v-model="spotDialog" :title="spotForm.id ? '编辑景点' : '添加景点'" width="600px">
      <el-form :model="spotForm" label-width="100px" :rules="spotRules" ref="spotFormRef">
        <el-form-item label="景点名称" prop="name"><el-input v-model="spotForm.name" /></el-form-item>
        <el-form-item label="分类" prop="category">
          <el-select v-model="spotForm.category">
            <el-option label="自然风光" value="自然风光" />
            <el-option label="历史文化" value="历史文化" />
            <el-option label="冰雪乐园" value="冰雪乐园" />
            <el-option label="城市地标" value="城市地标" />
            <el-option label="休闲娱乐" value="休闲娱乐" />
          </el-select>
        </el-form-item>
        <el-form-item label="地址" prop="address"><el-input v-model="spotForm.address" /></el-form-item>
        <el-form-item label="票价" prop="ticketPrice"><el-input-number v-model="spotForm.ticketPrice" :min="0" /></el-form-item>
        <el-form-item label="开放时间" prop="openTime"><el-input v-model="spotForm.openTime" placeholder="如：08:00-17:00" /></el-form-item>
        <el-form-item label="封面图片" prop="coverImage"><el-input v-model="spotForm.coverImage" /></el-form-item>
        <el-form-item label="景点介绍" prop="description"><el-input v-model="spotForm.description" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="spotDialog = false">取消</el-button>
        <el-button type="primary" @click="saveSpot">保存</el-button>
      </template>
    </el-dialog>
    <el-dialog v-model="announcementDialog" :title="announcementForm.id ? '编辑公告' : '发布公告'" width="600px">
      <el-form :model="announcementForm" label-width="80px" :rules="announcementRules" ref="announcementFormRef">
        <el-form-item label="标题" prop="title"><el-input v-model="announcementForm.title" /></el-form-item>
        <el-form-item label="内容" prop="content"><el-input v-model="announcementForm.content" type="textarea" :rows="5" /></el-form-item>
        <el-form-item label="置顶"><el-switch v-model="announcementForm.isTop" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="announcementDialog = false">取消</el-button>
        <el-button type="primary" @click="saveAnnouncement">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { getUserList, deleteUserById, getSpotList, createSpot, updateSpot, deleteSpotById, getAllOrders, getAnnouncementList, createAnnouncement, updateAnnouncement, deleteAnnouncementById } from '../api'
import { ElMessage, ElMessageBox } from 'element-plus'

const activeTab = ref('users')
const loading = ref(false)
const users = ref([])
const spots = ref([])
const orders = ref([])
const announcements = ref([])

const statusText = { pending: '待支付', paid: '已支付', used: '已使用', cancelled: '已取消', refunded: '已退款' }
const statusType = { pending: 'warning', paid: 'success', used: 'info', cancelled: 'info', refunded: 'danger' }

const spotDialog = ref(false)
const spotFormRef = ref()
const spotForm = ref({ name: '', category: '', address: '', ticketPrice: 0, openTime: '', coverImage: '', description: '' })
const spotRules = { name: [{ required: true, message: '请输入名称', trigger: 'blur' }], category: [{ required: true, message: '请选择分类', trigger: 'change' }] }

const announcementDialog = ref(false)
const announcementFormRef = ref()
const announcementForm = ref({ title: '', content: '', isTop: false })
const announcementRules = { title: [{ required: true, message: '请输入标题', trigger: 'blur' }], content: [{ required: true, message: '请输入内容', trigger: 'blur' }] }

const loadUsers = async () => { loading.value = true; const res = await getUserList({ pageNum: 1, pageSize: 100 }); users.value = res.data.records || []; loading.value = false }
const loadSpots = async () => { loading.value = true; const res = await getSpotList({ pageNum: 1, pageSize: 100 }); spots.value = res.data.records || []; loading.value = false }
const loadOrders = async () => { loading.value = true; const res = await getAllOrders({ pageNum: 1, pageSize: 100 }); orders.value = res.data.records || []; loading.value = false }
const loadAnnouncements = async () => { loading.value = true; const res = await getAnnouncementList({ pageNum: 1, pageSize: 100 }); announcements.value = res.data.records || []; loading.value = false }

watch(activeTab, (val) => {
  if (val === 'users') loadUsers()
  else if (val === 'spots') loadSpots()
  else if (val === 'orders') loadOrders()
  else if (val === 'announcements') loadAnnouncements()
}, { immediate: true })

const deleteUser = async (row) => { await ElMessageBox.confirm('确认删除该用户？', '提示'); await deleteUserById(row.id); ElMessage.success('删除成功'); loadUsers() }
const openSpotForm = (row) => { spotForm.value = row ? { ...row } : { name: '', category: '', address: '', ticketPrice: 0, openTime: '', coverImage: '', description: '' }; spotDialog.value = true }
const saveSpot = async () => { await spotFormRef.value.validate(); if (spotForm.value.id) { await updateSpot(spotForm.value.id, spotForm.value) } else { await createSpot(spotForm.value) }; ElMessage.success('保存成功'); spotDialog.value = false; loadSpots() }
const deleteSpot = async (row) => { await ElMessageBox.confirm('确认删除该景点？', '提示'); await deleteSpotById(row.id); ElMessage.success('删除成功'); loadSpots() }
const openAnnouncementForm = (row) => { announcementForm.value = row ? { ...row } : { title: '', content: '', isTop: false }; announcementDialog.value = true }
const saveAnnouncement = async () => { await announcementFormRef.value.validate(); if (announcementForm.value.id) { await updateAnnouncement(announcementForm.value.id, announcementForm.value) } else { await createAnnouncement(announcementForm.value) }; ElMessage.success('保存成功'); announcementDialog.value = false; loadAnnouncements() }
const deleteAnnouncement = async (row) => { await ElMessageBox.confirm('确认删除该公告？', '提示'); await deleteAnnouncementById(row.id); ElMessage.success('删除成功'); loadAnnouncements() }
</script>

<style scoped>
.toolbar { margin-bottom: 15px; }
</style>
