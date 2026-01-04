// pages/login/login.js
const app = getApp();
const request = require('../../utils/request');

Page({
  data: {
    agreed: true
  },

  /**
   * 微信一键登录
   */
  handleWxLogin() {
    if (!this.data.agreed) {
      wx.showToast({
        title: '请先同意用户协议',
        icon: 'none'
      });
      return;
    }

    // 获取用户信息
    wx.getUserProfile({
      desc: '用于完善用户资料',
      success: (res) => {
        console.log('获取用户信息成功', res);
        const { nickName, avatarUrl } = res.userInfo;
        
        // 登录
        wx.login({
          success: (loginRes) => {
            if (loginRes.code) {
              this.doLogin(loginRes.code, nickName, avatarUrl);
            } else {
              wx.showToast({
                title: '登录失败',
                icon: 'none'
              });
            }
          }
        });
      },
      fail: () => {
        wx.showToast({
          title: '获取用户信息失败',
          icon: 'none'
        });
      }
    });
  },

  /**
   * 调用后端登录接口
   */
  doLogin(code, nickname, avatar) {
    wx.showLoading({
      title: '登录中...'
    });

    request.post('/auth/wxlogin', {
      code: code,
      nickname: nickname,
      avatar: avatar,
      ip: '0.0.0.0'
    }).then(res => {
      wx.hideLoading();
      
      // 保存登录信息
      app.setLoginInfo(res.token, {
        userId: res.userId,
        nickname: res.nickname,
        avatar: res.avatar,
        role: res.role,
        score: res.score,
        level: res.level
      });

      wx.showToast({
        title: '登录成功',
        icon: 'success',
        duration: 1500
      });

      // 跳转到首页
      setTimeout(() => {
        wx.switchTab({
          url: '/pages/index/index'
        });
      }, 1500);
    }).catch(err => {
      wx.hideLoading();
      console.error('登录失败', err);
    });
  },

  /**
   * 同意协议变化
   */
  handleAgreeChange(e) {
    this.setData({
      agreed: e.detail.value.includes('agree')
    });
  },

  /**
   * 查看用户协议
   */
  handleViewAgreement() {
    wx.showModal({
      title: '用户协议',
      content: '这里是用户协议的内容...',
      showCancel: false
    });
  },

  /**
   * 查看隐私政策
   */
  handleViewPrivacy() {
    wx.showModal({
      title: '隐私政策',
      content: '这里是隐私政策的内容...',
      showCancel: false
    });
  }
});
