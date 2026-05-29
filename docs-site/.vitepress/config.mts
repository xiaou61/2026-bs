import { defineConfig } from 'vitepress'
import fs from 'fs'
import path from 'path'

// 读取项目分类数据
const projectsData = JSON.parse(
  fs.readFileSync(path.join(__dirname, 'projects-data.json'), 'utf-8')
)

// 构建侧边栏
const sidebar = [
  {
    text: '快速开始',
    items: [
      { text: '项目介绍', link: '/guide/getting-started' },
    ]
  },
  {
    text: '选择指南',
    items: [
      { text: '对比选择指南', link: '/guide/compare' },
      { text: '按技术栈筛选', link: '/guide/tech-stack' },
      { text: '按功能场景筛选', link: '/guide/scenario' },
    ]
  },
  {
    text: '项目分类',
    items: Object.entries(projectsData).map(([key, val]) => ({
      text: `${val.name} (${val.items.length})`,
      link: `/categories/${key}`,
      collapsed: true,
      items: val.items.map(p => ({
        text: `${p.number} - ${p.title}`,
        link: `/projects/${p.number}`
      }))
    }))
  }
]

export default defineConfig({
  title: '2026毕设项目合集',
  description: '200+ Spring Boot + Vue3 毕业设计项目合集',
  lang: 'zh-CN',
  ignoreDeadLinks: true,

  head: [
    ['link', { rel: 'icon', href: '/favicon.ico' }]
  ],

  themeConfig: {
    nav: [
      { text: '首页', link: '/' },
      { text: '选择指南', items: [
        { text: '对比选择', link: '/guide/compare' },
        { text: '技术栈筛选', link: '/guide/tech-stack' },
        { text: '功能场景筛选', link: '/guide/scenario' },
      ]},
      { text: '项目', items: Object.entries(projectsData).slice(0, 5).map(([key, val]) => ({
        text: val.name,
        link: `/categories/${key}`
      }))},
      { text: '更多分类', items: Object.entries(projectsData).slice(5).map(([key, val]) => ({
        text: val.name,
        link: `/categories/${key}`
      }))}
    ],

    sidebar,

    search: {
      provider: 'local',
      options: {
        locales: {
          root: {
            translations: {
              button: { buttonText: '搜索项目', buttonAriaLabel: '搜索项目' },
              modal: {
                noResultsText: '无法找到相关结果',
                resetButtonTitle: '清除查询条件',
                footer: { selectText: '选择', navigateText: '切换', closeText: '关闭' }
              }
            }
          }
        }
      }
    },

    footer: {
      message: '2026 毕业设计项目合集',
      copyright: 'MIT License'
    },

    docFooter: {
      prev: '上一篇',
      next: '下一篇'
    },

    outline: {
      label: '页面导航',
      level: [2, 3]
    },

    lastUpdated: {
      text: '最后更新于'
    },

    returnToTopLabel: '回到顶部',
    sidebarMenuLabel: '菜单',
    darkModeSwitchLabel: '主题',
    lightModeSwitchTitle: '切换到浅色模式',
    darkModeSwitchTitle: '切换到深色模式'
  },

  vite: {
    server: {
      fs: {
        allow: ['..']
      }
    }
  }
})
