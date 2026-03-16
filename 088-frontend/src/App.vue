<template>
  <router-view />
</template>

<style>
:root {
  color-scheme: light;
  font-family: "Microsoft YaHei", "PingFang SC", sans-serif;
  --bg: #f7f1e3;
  --paper: rgba(255, 250, 240, 0.82);
  --panel: rgba(255, 255, 255, 0.92);
  --ink: #1f2937;
  --subtle: #6b7280;
  --line: rgba(146, 64, 14, 0.18);
  --brand: #9a3412;
  --brand-strong: #7c2d12;
  --accent: #0f766e;
  --shadow: 0 18px 40px rgba(120, 53, 15, 0.12);
}

* {
  box-sizing: border-box;
}

body {
  margin: 0;
  background:
    radial-gradient(circle at top left, rgba(255, 237, 213, 0.95), transparent 32%),
    radial-gradient(circle at top right, rgba(209, 250, 229, 0.7), transparent 28%),
    linear-gradient(135deg, #f8f5ee 0%, #f3ede2 45%, #f7f3eb 100%);
  color: var(--ink);
}

#app {
  min-height: 100vh;
}
</style>
