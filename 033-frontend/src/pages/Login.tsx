import { Card, Form, Input, Button, message } from 'antd'
import { useNavigate } from 'react-router-dom'
import { login } from '../api'
import './Login.css'

function Login() {
  const navigate = useNavigate()

  const onFinish = async (values: any) => {
    const res = await login(values)
    const token = res.data.data.token
    localStorage.setItem('token', token)
    message.success('登录成功')
    navigate('/dashboard')
  }

  return (
    <div className="login-container">
      {/* 背景装饰 */}
      <div className="bg-decoration">
        <div className="circle circle-1"></div>
        <div className="circle circle-2"></div>
        <div className="circle circle-3"></div>
        <div className="love-icon icon-1">💍</div>
        <div className="love-icon icon-2">👰</div>
        <div className="love-icon icon-3">📸</div>
      </div>
      
      <div className="login-box">
        {/* 头部 */}
        <div className="login-header">
          <div className="logo-icon">
            <span className="love-emoji">💒</span>
          </div>
          <h1 className="login-title">婚纱写真馆</h1>
          <p className="login-subtitle">Wedding Photo Studio</p>
        </div>
        
        <Form onFinish={onFinish} layout="vertical" className="login-form">
          <Form.Item name="username" label="账号" rules={[{ required: true, message: '请输入账号' }]}>
            <Input placeholder="请输入账号" size="large" className="custom-input" />
          </Form.Item>
          <Form.Item name="password" label="密码" rules={[{ required: true, message: '请输入密码' }]}>
            <Input.Password placeholder="请输入密码" size="large" className="custom-input" />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" block size="large" className="login-btn">
              进入管理后台
            </Button>
          </Form.Item>
        </Form>
        
        {/* 底部 */}
        <div className="login-bottom">
          <p>💒 记录幸福 · 定格美好</p>
        </div>
      </div>
    </div>
  )
}

export default Login
