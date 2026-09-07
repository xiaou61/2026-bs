import SockJS from 'sockjs-client'
import Stomp from 'stompjs'

class WebSocketService {
  constructor() {
    this.stompClient = null
    this.connected = false
    this.userId = null
    this.messageCallback = null
    this.notificationCallback = null
    this.connectCallback = null
  }

  connect(userId, token) {
    this.userId = userId
    
    const socket = new SockJS('http://localhost:8004/ws')
    this.stompClient = Stomp.over(socket)
    
    this.stompClient.connect(
      { Authorization: `Bearer ${token}` },
      () => {
        this.connected = true
        console.log('WebSocket 连接成功')
        
        this.stompClient.subscribe(`/user/${userId}/queue/messages`, (message) => {
          if (this.messageCallback) {
            this.messageCallback(JSON.parse(message.body))
          }
        })
        
        this.stompClient.subscribe(`/user/${userId}/queue/notifications`, (notification) => {
          if (this.notificationCallback) {
            this.notificationCallback(JSON.parse(notification.body))
          }
        })
        
        this.sendConnect()
        
        if (this.connectCallback) {
          this.connectCallback()
        }
      },
      (error) => {
        this.connected = false
        console.error('WebSocket 连接失败:', error)
      }
    )
  }

  sendConnect() {
    if (this.connected && this.stompClient) {
      this.stompClient.send('/app/user.connect', {}, JSON.stringify({
        userId: this.userId
      }))
    }
  }

  sendMessage(toUserId, content) {
    if (this.connected && this.stompClient) {
      this.stompClient.send('/app/chat.send', {}, JSON.stringify({
        type: 'CHAT',
        fromUserId: this.userId,
        toUserId: toUserId,
        content: content
      }))
    }
  }

  sendNotification(toUserId, notifyType, title, content, linkId) {
    if (this.connected && this.stompClient) {
      this.stompClient.send('/app/chat.send', {}, JSON.stringify({
        type: 'NOTIFICATION',
        fromUserId: this.userId,
        toUserId: toUserId,
        notifyType: notifyType,
        title: title,
        content: content,
        linkId: linkId
      }))
    }
  }

  disconnect() {
    if (this.connected && this.stompClient) {
      this.stompClient.send('/app/user.disconnect', {}, JSON.stringify({
        userId: this.userId
      }))
      this.stompClient.disconnect()
      this.connected = false
    }
  }

  onMessage(callback) {
    this.messageCallback = callback
  }

  onNotification(callback) {
    this.notificationCallback = callback
  }

  onConnect(callback) {
    this.connectCallback = callback
  }
}

export default new WebSocketService()

