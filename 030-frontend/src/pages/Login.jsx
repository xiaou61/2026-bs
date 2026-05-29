import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { Form, Input, Button, Card, message } from 'antd'
import { UserOutlined, LockOutlined, HeartOutlined } from '@ant-design/icons'
import { login } from '../api/user'
import useUserStore from '../store/useUserStore'
import './Login.css'

const Login = () => {
  const navigate = useNavigate()
  const { setUser, setToken } = useUserStore()
  const [loading, setLoading] = useState(false)

  const onFinish = async (values) => {
    setLoading(true)
    try {
      const res = await login(values)
      setToken(res.data.token)
      setUser({
        userId: res.data.userId,
        username: res.data.username,
        role: res.data.role
      })
      message.success('登录成功')
      
      if (res.data.role === 'PATIENT') {
        navigate('/patient/dashboard')
      } else if (res.data.role === 'DOCTOR') {
        navigate('/doctor/dashboard')
      } else if (res.data.role === 'ADMIN') {
        navigate('/admin/dashboard')
      }
    } catch (error) {
      message.error(error.message || '登录失败')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="login-container">
      {/* 背景装饰 */}
      <div className="bg-decoration">
        <div className="circle circle-1"></div>
        <div className="circle circle-2"></div>
        <div className="circle circle-3"></div>
        <div className="health-icon icon-1">❤️</div>
        <div className="health-icon icon-2">🏥</div>
        <div className="health-icon icon-3">💊</div>
      </div>
      
      <div className="login-box">
        {/* 头部 */}
        <div className="login-header">
          <div className="logo-icon">
            <HeartOutlined />
          </div>
          <h1 className="login-title">健康管理平台</h1>
          <p className="login-subtitle">Smart Health Management</p>
        </div>
        
        <Form onFinish={onFinish} size="large" className="login-form">
          <Form.Item
            name="username"
            rules={[{ required: true, message: '请输入用户名' }]}
          >
            <Input 
              prefix={<UserOutlined />} 
              placeholder="请输入用户名"
              className="custom-input"
            />
          </Form.Item>
          <Form.Item
            name="password"
            rules={[{ required: true, message: '请输入密码' }]}
          >
            <Input.Password 
              prefix={<LockOutlined />} 
              placeholder="请输入密码"
              className="custom-input"
            />
          </Form.Item>
          <Form.Item>
            <Button 
              type="primary" 
              htmlType="submit" 
              loading={loading} 
              block
              className="login-btn"
            >
              {loading ? '登录中...' : '进入健康平台'}
            </Button>
          </Form.Item>
        </Form>
        
        <div className="login-footer">
          <Link to="/register">没有账号？立即注册</Link>
        </div>
        
        {/* 底部 */}
        <div className="login-bottom">
          <p>❤️ 关爱健康 · 智慧生活</p>
        </div>
      </div>
    </div>
  )
}

export default Login
