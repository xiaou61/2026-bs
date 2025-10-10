<template>
  <div class="chat-container">
    <div class="chat-list">
      <div class="chat-list-header">
        <h3>聊天列表</h3>
        <el-button @click="showAddFriendDialog = true" type="primary" size="small" circle>
          <el-icon><Plus /></el-icon>
        </el-button>
      </div>
      
      <el-input
        v-model="searchKeyword"
        placeholder="搜索好友"
        prefix-icon="Search"
        size="small"
        class="search-input"
      />
      
      <div class="friend-list">
        <div 
          v-for="friend in filteredFriends" 
          :key="friend.friendId"
          class="friend-item"
          :class="{ active: selectedFriend?.friendId === friend.friendId }"
          @click="selectFriend(friend)"
        >
          <el-avatar :size="45" :src="friend.avatar || ''">
            {{ friend.nickname?.[0] || 'U' }}
          </el-avatar>
          <div class="friend-info">
            <div class="friend-name">{{ friend.nickname }}</div>
            <div class="last-message">{{ getLastMessage(friend.friendId) }}</div>
          </div>
        </div>
        
        <el-empty v-if="friends.length === 0" description="暂无好友" />
      </div>
    </div>
    
    <div class="chat-main">
      <div v-if="selectedFriend" class="chat-window">
        <div class="chat-header">
          <div class="friend-info-header">
            <el-avatar :size="40" :src="selectedFriend.avatar || ''">
              {{ selectedFriend.nickname?.[0] || 'U' }}
            </el-avatar>
            <span class="friend-name">{{ selectedFriend.nickname }}</span>
          </div>
          <el-button @click="loadHistory" type="primary" size="small" plain>
            <el-icon><Clock /></el-icon>
            历史记录
          </el-button>
        </div>
        
        <div class="message-list" ref="messageListRef">
          <div 
            v-for="msg in messages" 
            :key="msg.id"
            class="message-item"
            :class="{ 'is-mine': msg.fromUserId === userStore.userInfo.id }"
          >
            <el-avatar :size="40" :src="msg.fromUserId === userStore.userInfo.id ? '' : selectedFriend.avatar">
              {{ msg.fromUserId === userStore.userInfo.id ? userStore.userInfo.nickname?.[0] : selectedFriend.nickname?.[0] }}
            </el-avatar>
            <div class="message-content">
              <div class="message-text">{{ msg.content }}</div>
              <div class="message-time">{{ formatTime(msg.createTime) }}</div>
            </div>
          </div>
          
          <el-empty v-if="messages.length === 0" description="暂无消息" />
        </div>
        
        <div class="input-area">
          <el-input
            v-model="inputMessage"
            type="textarea"
            :rows="3"
            placeholder="输入消息..."
            @keydown.enter.prevent="handleSend"
          />
          <el-button type="primary" @click="handleSend" :disabled="!inputMessage.trim()">
            发送
          </el-button>
        </div>
      </div>
      
      <el-empty v-else description="请选择一个好友开始聊天" />
    </div>
    
    <el-dialog v-model="showAddFriendDialog" title="添加好友" width="400px">
      <el-input
        v-model="searchFriendKeyword"
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
          <el-avatar :size="40" :src="user.avatar || ''">
            {{ user.nickname?.[0] || 'U' }}
          </el-avatar>
          <div class="user-info">
            <div>{{ user.nickname }}</div>
            <div class="username">@{{ user.username }}</div>
          </div>
          <el-button type="primary" size="small" @click="handleAddFriend(user.id)">
            添加
          </el-button>
        </div>
        
        <el-empty v-if="searchResults.length === 0 && searchFriendKeyword" description="未找到用户" />
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick, watch } from 'vue'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import { getFriendList, addFriend } from '@/api/friend'
import { getChatHistory, markAllAsRead } from '@/api/chat'
import { searchUsers } from '@/api/user'
import websocket from '@/utils/websocket'

const userStore = useUserStore()

const friends = ref([])
const selectedFriend = ref(null)
const messages = ref([])
const inputMessage = ref('')
const searchKeyword = ref('')
const showAddFriendDialog = ref(false)
const searchFriendKeyword = ref('')
const searchResults = ref([])
const messageListRef = ref()
const lastMessageMap = ref({})
const lastMessageTimeMap = ref({})

const filteredFriends = computed(() => {
  let list = friends.value
  
  if (searchKeyword.value) {
    list = list.filter(f => 
      f.nickname.includes(searchKeyword.value) || 
      f.username.includes(searchKeyword.value)
    )
  }
  
  return list.sort((a, b) => {
    const timeA = lastMessageTimeMap.value[a.friendId] || 0
    const timeB = lastMessageTimeMap.value[b.friendId] || 0
    return timeB - timeA
  })
})

const loadFriends = async () => {
  try {
    const data = await getFriendList()
    friends.value = data || []
  } catch (error) {
    console.error('加载好友列表失败', error)
  }
}

const selectFriend = async (friend) => {
  selectedFriend.value = friend
  await loadChatHistory()
}

const loadChatHistory = async () => {
  if (!selectedFriend.value) return
  
  try {
    const data = await getChatHistory(selectedFriend.value.friendId)
    messages.value = data?.records || []
    
    if (messages.value.length > 0) {
      const lastMsg = messages.value[messages.value.length - 1]
      lastMessageMap.value[selectedFriend.value.friendId] = lastMsg.content
      lastMessageTimeMap.value[selectedFriend.value.friendId] = new Date(lastMsg.createTime).getTime()
    }
    
    await scrollToBottom()
  } catch (error) {
    console.error('加载聊天记录失败', error)
  }
}

const loadHistory = async () => {
  await loadChatHistory()
  ElMessage.success('已加载历史记录')
}

const handleSend = () => {
  if (!inputMessage.value.trim() || !selectedFriend.value) return
  
  const content = inputMessage.value.trim()
  websocket.sendMessage(selectedFriend.value.friendId, content)
  
  const now = Date.now()
  const newMessage = {
    id: now,
    fromUserId: userStore.userInfo.id,
    toUserId: selectedFriend.value.friendId,
    content: content,
    createTime: new Date()
  }
  
  messages.value.push(newMessage)
  lastMessageMap.value[selectedFriend.value.friendId] = content
  lastMessageTimeMap.value[selectedFriend.value.friendId] = now
  inputMessage.value = ''
  
  scrollToBottom()
}

const handleSearchUser = async () => {
  const keyword = searchFriendKeyword.value.trim()
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

const handleAddFriend = async (userId) => {
  try {
    await addFriend({ friendId: userId })
    ElMessage.success('添加成功')
    showAddFriendDialog.value = false
    searchFriendKeyword.value = ''
    searchResults.value = []
    await loadFriends()
  } catch (error) {
    console.error('添加好友失败', error)
  }
}

const formatTime = (time) => {
  if (!time) return ''
  const date = new Date(time)
  const now = new Date()
  
  if (date.toDateString() === now.toDateString()) {
    return date.toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' })
  }
  
  return date.toLocaleDateString('zh-CN', { month: '2-digit', day: '2-digit' }) + ' ' +
         date.toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' })
}

const scrollToBottom = async () => {
  await nextTick()
  if (messageListRef.value) {
    messageListRef.value.scrollTop = messageListRef.value.scrollHeight
  }
}

const getLastMessage = (friendId) => {
  return lastMessageMap.value[friendId] || '开始聊天吧~'
}

onMounted(() => {
  loadFriends()
  
  websocket.onMessage((message) => {
    const friendId = message.fromUserId === userStore.userInfo.id ? message.toUserId : message.fromUserId
    lastMessageMap.value[friendId] = message.content
    lastMessageTimeMap.value[friendId] = new Date(message.createTime).getTime()
    
    if (selectedFriend.value && 
        (message.fromUserId === selectedFriend.value.friendId || message.toUserId === selectedFriend.value.friendId)) {
      messages.value.push(message)
      scrollToBottom()
    }
  })
})
</script>

<style scoped>
.chat-container {
  display: flex;
  height: 100%;
}

.chat-list {
  width: 300px;
  border-right: 1px solid #e0e0e0;
  display: flex;
  flex-direction: column;
}

.chat-list-header {
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e0e0e0;
}

.chat-list-header h3 {
  margin: 0;
  font-size: 18px;
}

.search-input {
  margin: 15px;
}

.friend-list {
  flex: 1;
  overflow-y: auto;
}

.friend-item {
  display: flex;
  align-items: center;
  padding: 15px 20px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.friend-item:hover,
.friend-item.active {
  background-color: #f5f5f5;
}

.friend-info {
  margin-left: 15px;
  flex: 1;
}

.friend-name {
  font-weight: 500;
  margin-bottom: 5px;
}

.last-message {
  font-size: 12px;
  color: #999;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.chat-main {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.chat-window {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.chat-header {
  padding: 20px;
  border-bottom: 1px solid #e0e0e0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.friend-info-header {
  display: flex;
  align-items: center;
  gap: 15px;
}

.friend-info-header .friend-name {
  font-size: 18px;
  font-weight: 500;
}

.message-list {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
}

.message-item {
  display: flex;
  margin-bottom: 20px;
  gap: 15px;
}

.message-item.is-mine {
  flex-direction: row-reverse;
}

.message-content {
  max-width: 60%;
}

.message-item.is-mine .message-content {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

.message-text {
  background-color: #f5f5f5;
  padding: 10px 15px;
  border-radius: 10px;
  word-break: break-word;
}

.message-item.is-mine .message-text {
  background-color: #667eea;
  color: white;
}

.message-time {
  font-size: 12px;
  color: #999;
  margin-top: 5px;
}

.input-area {
  padding: 20px;
  border-top: 1px solid #e0e0e0;
  display: flex;
  gap: 10px;
}

.input-area .el-input {
  flex: 1;
}

.user-search-list {
  margin-top: 20px;
  max-height: 300px;
  overflow-y: auto;
}

.user-item {
  display: flex;
  align-items: center;
  padding: 10px;
  gap: 15px;
  border-bottom: 1px solid #e0e0e0;
}

.user-item:last-child {
  border-bottom: none;
}

.user-info {
  flex: 1;
}

.username {
  font-size: 12px;
  color: #999;
}
</style>

