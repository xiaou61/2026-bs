#!/usr/bin/env python3
"""Simple coverage audit - count pages with meaningful content"""
import os, re, sys
sys.stdout.reconfigure(encoding='utf-8')

root = os.getcwd()
dir = os.path.join(root, 'docs-site', 'projects')
files = sorted([f for f in os.listdir(dir) if f.endswith('.md')])
total = len(files)

stats = {
    'roles': 0, 'accounts': 0, 'desc': 0, 'structure': 0,
    'devnotes': 0, 'highlights': 0, 'database': 0, 'frontend': 0, 'api': 0
}
no_accounts = []
no_roles = []

for f in files:
    c = open(os.path.join(dir, f), encoding='utf-8').read()
    num = f.replace('.md','')

    # Roles
    if '## 角色权限' in c:
        stats['roles'] += 1
    else:
        no_roles.append(num)

    # Accounts - any non-placeholder account data
    am = re.search(r'### 默认账号(.*?)(?=\n## |\Z)', c, re.DOTALL)
    if am:
        sec = am.group(1)
        # Has any backtick-quoted data in table
        has_table_data = bool(re.search(r'\|\s*.+?`.+?`.+?`', sec))
        # Has line format: - xxx：`username/password`
        has_line_data = bool(re.search(r'`\w+/\w+`', sec))
        # Not just placeholder
        is_placeholder = '请查看项目 README' in sec
        if (has_table_data or has_line_data) and not is_placeholder:
            stats['accounts'] += 1
        else:
            no_accounts.append(num)
    else:
        no_accounts.append(num)

    # Project description
    dm = re.search(r'## 项目简介\s*\n\n(.+?)(?=\n## )', c, re.DOTALL)
    if dm and len(dm.group(1).strip()) > 10:
        stats['desc'] += 1

    if '## 项目结构' in c: stats['structure'] += 1
    if '### 开发注意事项' in c: stats['devnotes'] += 1
    if re.search(r'(特色亮点|技术亮点)', c): stats['highlights'] += 1
    if '## 数据库设计' in c: stats['database'] += 1
    if '## 前端页面' in c: stats['frontend'] += 1
    if '## API 接口' in c: stats['api'] += 1

print(f'总项目: {total}')
for k, v in stats.items():
    print(f'{k}: {v} ({v/total*100:.1f}%)')

print(f'\n缺少账号({len(no_accounts)}个):')
# Group by range
ranges = {}
for n in no_accounts:
    r = int(n) // 10 * 10
    ranges[r] = ranges.get(r, 0) + 1
for r in sorted(ranges.keys()):
    print(f'  {r:03d}-{r+9:03d}: {ranges[r]}个项目')

print(f'\n缺少角色({len(no_roles)}个):')
ranges = {}
for n in no_roles:
    r = int(n) // 10 * 10
    ranges[r] = ranges.get(r, 0) + 1
for r in sorted(ranges.keys()):
    print(f'  {r:03d}-{r+9:03d}: {ranges[r]}个项目')