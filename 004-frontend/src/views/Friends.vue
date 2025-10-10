<template>
  <div class="friends-container">
    <div class="friends-header">
      <h2>好友管理</h2>
      <el-button type="primary" @click="showAddDialog = true">
        <el-icon><Plus /></el-icon>
        添加好友
      </el-button>
    </div>
    
    <div class="friends-content">
      <el-tabs v-model="activeTab">
        <el-tab-pane label="好友列表" name="list">
          <el-input
            v-model="searchKeyword"
            placeholder="搜索好友"
            prefix-icon="Search"
            class="search-input"
          />
          
          <div class="friend-cards">
            <el-card 
              v-for="friend in filteredFriends" 
              :key="friend.friendId"
              class="friend-card"
              shadow="hover"
            >
              <div class="friend-card-content">
                <el-avatar :size="60" :src="friend.avatar || ''">
                  {{ friend.nickname?.[0] || 'U' }}
                </el-avatar>
                <div class="friend-info">
                  <div class="friend-name">{{ friend.nickname }}</div>
                  <div class="friend-username">@{{ friend.username }}</div>
                  <div class="friend-group">分组: {{ friend.groupName || '默认分组' }}</div>
                </div>
                <div class="friend-actions">
                  <el-button type="primary" size="small" @click="goChat(friend)">
                    <el-icon><ChatDotRound /></el-icon>
                    发消息
                  </el-button>
                  <el-button type="danger" size="small" plain @click="handleDelete(friend)">
                    <el-icon><Delete /></el-icon>
                    删除
                  </el-button>
                </div>
              </div>
            </el-card>
            
            <el-empty v-if="friends.length === 0" description="暂无好友" />
          </div>
        </el-tab-pane>
        
        <el-tab-pane label="分组管理" name="groups">
          <el-button type="primary" @click="showGroupDialog = true" style="margin-bottom: 20px">
            <el-icon><Plus /></el-icon>
            新建分组
          </el-button>
          
          <div class="group-list">
            <el-card 
              v-for="group in groups" 
              :key="group.id"
              class="group-card"
            >
              <div class="group-info">
                <el-icon><Folder /></el-icon>
                <span class="group-name">{{ group.groupName }}</span>
              </div>
            </el-card>
            
            <el-empty v-if="groups.length === 0" description="暂无分组" />
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    
    <el-dialog v-model="showAddDialog" title="添加好友" width="500px">
      <el-input
        v-model="searchUserKeyword"
        placeholder="输入用户名或昵称搜索"
        @keyup.enter="handleSearchUser"
      >
        <template #append>
          <el-button icon="Search" @click="handleSearchUser" />
        </template>
      </el-input>
      
      <div class="user-search-list">
        <div 
          v-for="user in searchResults" 
          :key="user.id"
          class="user-item"
        >
          <el-avatar :size="50" :src="user.avatar || ''">
            {{ user.nickname?.[0] || 'U' }}
          </el-avatar>
          <div class="user-info">
            <div class="user-name">{{ user.nickname }}</div>
            <div class="user-username">@{{ user.username }}</div>
          </div>
          <el-button type="primary" @click="handleAdd(user.id)">
            添加
          </el-button>
        </div>
        
        <el-empty v-if="searchResults.length === 0 && searchUserKeyword" description="未找到用户" />
      </div>
    </el-dialog>
    
    <el-dialog v-model="showGroupDialog" title="新建分组" width="400px">
      <el-form :model="groupForm" label-width="80px">
        <el-form-item label="分组名称">
          <el-input v-model="groupForm.groupName" placeholder="请输入分组名称" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showGroupDialog = false">取消</el-button>
        <el-button type="primary" @click="handleAddGroup">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getFriendList, addFriend, deleteFriend, getGroupList, addGroup } from '@/api/friend'
import { searchUsers } from '@/api/user'

const router = useRouter()

const activeTab = ref('list')
const friends = ref([])
const groups = ref([])
const searchKeyword = ref('')
const showAddDialog = ref(false)
const showGroupDialog = ref(false)
const searchUserKeyword = ref('')
const searchResults = ref([])

const groupForm = ref({
  groupName: ''
})

const filteredFriends = computed(() => {
  if (!searchKeyword.value) return friends.value
  return friends.value.filter(f => 
    f.nickname.includes(searchKeyword.value) || 
    f.username.includes(searchKeyword.value)
  )
})

const loadFriends = async () => {
  try {
    const data = await getFriendList()
    friends.value = data || []
  } catch (error) {
    console.error('加载好友列表失败', error)
  }
}

const loadGroups = async () => {
  try {
    const data = await getGroupList()
    groups.value = data || []
  } catch (error) {
    console.error('加载分组列表失败', error)
  }
}

const handleSearchUser = async () => {
  const keyword = searchUserKeyword.value.trim()
  if (!keyword) {
    ElMessage.warning('请输入搜索关键词')
    searchResults.value = []
    return
  }
  
  try {
    const data = await searchUsers(keyword)
    searchResults.value = data || []
    if (searchResults.value.length === 0) {
      ElMessage.info('未找到匹配的用户')
    }
  } catch (error) {
    console.error('搜索用户失败', error)
    searchResults.value = []
  }
}

const handleAdd = async (friendId) => {
  try {
    await addFriend({ friendId })
    ElMessage.success('添加成功')
    showAddDialog.value = false
    searchUserKeyword.value = ''
    searchResults.value = []
    await loadFriends()
  } catch (error) {
    console.error('添加好友失败', error)
  }
}

const handleDelete = async (friend) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除好友 ${friend.nickname} 吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
    )
    
    await deleteFriend(friend.friendId)
    ElMessage.success('删除成功')
    await loadFriends()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除好友失败', error)
    }
  }
}

const handleAddGroup = async () => {
  if (!groupForm.value.groupName.trim()) {
    ElMessage.warning('请输入分组名称')
    return
  }
  
  try {
    await addGroup(groupForm.value)
    ElMessage.success('创建成功')
    showGroupDialog.value = false
    groupForm.value.groupName = ''
    await loadGroups()
  } catch (error) {
    console.error('创建分组失败', error)
  }
}

const goChat = (friend) => {
  router.push('/chat')
}

onMounted(() => {
  loadFriends()
  loadGroups()
})
</script>

<style scoped>
.friends-container {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.friends-header {
  padding: 20px 30px;
  border-bottom: 1px solid #e0e0e0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.friends-header h2 {
  margin: 0;
  font-size: 24px;
}

.friends-content {
  flex: 1;
  padding: 20px 30px;
  overflow-y: auto;
}

.search-input {
  margin-bottom: 20px;
}

.friend-cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 20px;
}

.friend-card {
  cursor: pointer;
  transition: transform 0.3s;
}

.friend-card:hover {
  transform: translateY(-5px);
}

.friend-card-content {
  display: flex;
  align-items: center;
  gap: 15px;
}

.friend-info {
  flex: 1;
}

.friend-name {
  font-size: 18px;
  font-weight: 500;
  margin-bottom: 5px;
}

.friend-username {
  font-size: 14px;
  color: #999;
  margin-bottom: 5px;
}

.friend-group {
  font-size: 12px;
  color: #666;
}

.friend-actions {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.group-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 15px;
}

.group-card {
  cursor: pointer;
  transition: all 0.3s;
}

.group-card:hover {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.group-info {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 16px;
}

.group-name {
  font-weight: 500;
}

.user-search-list {
  margin-top: 20px;
  max-height: 400px;
  overflow-y: auto;
}

.user-item {
  display: flex;
  align-items: center;
  padding: 15px;
  gap: 15px;
  border-bottom: 1px solid #e0e0e0;
  transition: background-color 0.3s;
}

.user-item:hover {
  background-color: #f5f5f5;
}

.user-item:last-child {
  border-bottom: none;
}

.user-info {
  flex: 1;
}

.user-name {
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 5px;
}

.user-username {
  font-size: 14px;
  color: #999;
}
</style>

