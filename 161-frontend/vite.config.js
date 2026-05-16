import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  root: process.cwd(),
  plugins: [vue()],
  server: { port: 3161, proxy: { '/api': { target: 'http://localhost:8161', changeOrigin: true } } }
})
