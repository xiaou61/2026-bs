import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  root: process.cwd(),
  plugins: [vue()],
  server: { port: 3183, proxy: { '/api': { target: 'http://localhost:8183', changeOrigin: true } } }
})
