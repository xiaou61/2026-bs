#!/usr/bin/env python3
"""Analyze check reports for account info patterns"""
import os, re, sys
sys.stdout.reconfigure(encoding='utf-8')

root = os.getcwd()
checks_dir = os.path.join(root, 'docs', 'checks')

# Find account patterns in check reports
account_patterns = {}
for f in sorted(os.listdir(checks_dir)):
    num = f.split('-')[0]
    c = open(os.path.join(checks_dir, f), encoding='utf-8').read()

    # Pattern 1: 默认账号：`admin/123456`
    m1 = re.search(r'默认账号.*?`(\w+)/(\w+)`', c)
    if m1:
        account_patterns[num] = f'format1: {m1.group(0)[:50]}'
        continue

    # Pattern 2: username: admin, password: 123456
    m2 = re.search(r'(?:用户名|账号)[：:]\s*(\S+).*?(?:密码)[：:]\s*(\S+)', c, re.DOTALL)
    if m2:
        account_patterns[num] = f'format2: {m2.group(0)[:50]}'
        continue

    # Pattern 3: in environment section
    m3 = re.search(r'(?:默认账号|测试账号|账号)[^`\n]*`(\w+)`[^`\n]*`(\w+)`', c)
    if m3:
        account_patterns[num] = f'format3: {m3.group(0)[:50]}'
        continue

print(f'Found account info in {len(account_patterns)} check reports')

# Which projects without accounts have check reports?
no_account = []
for f in sorted(os.listdir(os.path.join(root, 'docs-site', 'projects'))):
    if not f.endswith('.md'):
        continue
    num = f.replace('.md', '')
    c = open(os.path.join(root, 'docs-site', 'projects', f), encoding='utf-8').read()
    if '## 角色权限' not in c and num in account_patterns:
        no_account.append(num)

print(f'\nProjects missing role section but have account info in check report: {len(no_account)}')
print(no_account[:20])

# Show examples
for num in list(account_patterns.keys())[:5]:
    print(f'\n{num}: {account_patterns[num]}')