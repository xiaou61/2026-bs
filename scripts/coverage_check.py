#!/usr/bin/env python3
"""Coverage audit for project pages"""
import os, re

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
PROJ_DIR = os.path.join(ROOT, 'docs-site', 'projects')

files = [f for f in os.listdir(PROJ_DIR) if f.endswith('.md')]
stats = dict(total=0, hasRoles=0, hasAccounts=0, hasDesc=0, hasStructure=0,
             hasDevNotes=0, hasTechHL=0, hasDB=0, hasFrontendPages=0, hasAPI=0)
no_accounts = []
no_roles = []
no_desc = []

for f in files:
    c = open(os.path.join(PROJ_DIR, f), encoding='utf-8').read()
    num = f.replace('.md', '')
    stats['total'] += 1

    # Roles - check for ## 角色权限 with actual role names (### headings)
    role_match = re.search(r'## 角色权限', c)
    if role_match:
        stats['hasRoles'] += 1
    else:
        no_roles.append(num)

    # Accounts - check for real account data (table with usernames/passwords)
    acc_match = re.search(r'### 默认账号\s*\n(.*?)(?=\n## |\n### |$)', c, re.DOTALL)
    if acc_match:
        section = acc_match.group(1)
        # Has real data if there's a table row with username/password in backticks
        if re.search(r'\|.*`.*`\s*\|\s*`.*`', section) and '请查看项目 README' not in section:
            stats['hasAccounts'] += 1
        elif re.search(r'`(\w+)\s*/\s*(\w+)`', section) and '请查看项目 README' not in section:
            stats['hasAccounts'] += 1
        else:
            no_accounts.append(num)
    else:
        no_accounts.append(num)

    # Project description
    desc_match = re.search(r'## 项目简介\s*\n\n(.*?)(?=\n## )', c, re.DOTALL)
    if desc_match and len(desc_match.group(1).strip()) > 10:
        stats['hasDesc'] += 1
    else:
        no_desc.append(num)

    if '## 项目结构' in c:
        stats['hasStructure'] += 1
    if '### 开发注意事项' in c:
        stats['hasDevNotes'] += 1
    if re.search(r'(特色亮点|技术亮点)', c):
        stats['hasTechHL'] += 1
    if '## 数据库设计' in c:
        stats['hasDB'] += 1
    if '## 前端页面' in c:
        stats['hasFrontendPages'] += 1
    if '## API 接口' in c:
        stats['hasAPI'] += 1

print('=== 覆盖率统计 ===')
print(f'总项目数: {stats["total"]}')
pct = lambda n: f'{n/stats["total"]*100:.1f}%'
for k in ['hasRoles','hasAccounts','hasDesc','hasStructure','hasDevNotes',
           'hasTechHL','hasDB','hasFrontendPages','hasAPI']:
    print(f'{k}: {stats[k]} {pct(stats[k])}')

print('\n=== 缺少账号的项目 (前40) ===')
print(', '.join(no_accounts[:40]))
print(f'总计: {len(no_accounts)}')

print('\n=== 缺少角色的项目 (前40) ===')
print(', '.join(no_roles[:40]))
print(f'总计: {len(no_roles)}')

print('\n=== 缺少简介的项目 (前40) ===')
print(', '.join(no_desc[:40]))
print(f'总计: {len(no_desc)}')

# Check a few specific projects
print('\n=== 具体项目检查 ===')
for num in ['052', '060', '075', '100']:
    c = open(os.path.join(PROJ_DIR, f'{num}.md'), encoding='utf-8').read()
    has_r = '## 角色权限' in c
    has_a = bool(re.search(r'### 默认账号.*?`(\w+)`.*?`(\w+)`', c))
    has_d = bool(re.search(r'## 项目简介\s*\n\n(.*?)(?=\n## )', c, re.DOTALL))
    print(f'{num}: 角色={has_r}, 账号={has_a}, 简介={bool(has_d)}')