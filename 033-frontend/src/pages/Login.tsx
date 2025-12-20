import { Card, Form, Input, Button, message } from 'antd'
import { useNavigate } from 'react-router-dom'
import { login } from '../api'

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
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh', background: '#f0f2f5' }}>
      <Card title="婚纱写真馆后台登录" style={{ width: 360 }}>
        <Form onFinish={onFinish} layout="vertical">
          <Form.Item name="username" label="账号" rules={[{ required: true }]}>
            <Input placeholder="输入账号" />
          </Form.Item>
          <Form.Item name="password" label="密码" rules={[{ required: true }]}>
            <Input.Password placeholder="输入密码" />
          </Form.Item>
          <Button type="primary" htmlType="submit" block>登录</Button>
        </Form>
      </Card>
    </div>
  )
}

export default Login
