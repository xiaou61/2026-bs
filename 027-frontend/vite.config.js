import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default defineConfig({
    plugins: [vue()],

    // 开发服务器配置
    server: {
        port: 5173,
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                changeOrigin: true
            }
        }
    },

    // 生产构建配置 - 打包到后端resources/static目录
    build: {
        outDir: '../027-backend/src/main/resources/static',
        emptyOutDir: true,
        rollupOptions: {
            output: {
                manualChunks: {
                    'element-plus': ['element-plus'],
                    'vue-vendor': ['vue', 'vue-router', 'pinia']
                }
            }
        }
    },

    resolve: {
        alias: {
            '@': resolve(__dirname, 'src')
        }
    }
})
