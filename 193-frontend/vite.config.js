import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  root: process.cwd(),
  plugins: [vue()],
  server: { port: 3193, proxy: { '/api': { target: 'http://localhost:8193', changeOrigin: true } } }
})
