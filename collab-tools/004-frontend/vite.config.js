import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  },
  define: {
    global: 'globalThis'
  },
  server: {
    port: 5004,
    proxy: {
      '/api': {
        target: 'http://localhost:8004',
        changeOrigin: true
      },
      '/ws': {
        target: 'ws://localhost:8004',
        ws: true,
        changeOrigin: true
      }
    }
  }
})

