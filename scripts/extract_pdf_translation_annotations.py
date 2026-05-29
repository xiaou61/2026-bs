import json
import re
import sys
import xml.etree.ElementTree as ET
from pathlib import Path


TARGETS = [
    {
        "phrase": "Design and Development of C Language Teaching System Based on Vue",
        "translation": "基于 Vue 的 C 语言教学系统的设计与开发。",
    },
    {
        "phrase": "Compared with the improvement of hardware, how to use computer means to promote the quality of teaching in college education is the key research topic.",
        "translation": "与硬件条件的改进相比，如何利用计算机手段提升高校教学质量，是当前研究的关键课题。",
    },
    {
        "phrase": "The whole teaching system is divided into front-end and back-end.",
        "translation": "整个教学系统分为前端和后端两部分。",
    },
    {
        "phrase": "Spring Boot and Netty network framework",
        "translation": "后端基于 Spring Boot 和 Netty 网络框架进行部署与开发，最终通过 WebSocket 协议进行通信。",
    },
    {
        "phrase": "Keywords: Vue framework; Spring Boot; C language.",
        "translation": "关键词：Vue 框架；Spring Boot；C 语言。",
    },
    {
        "phrase": "The reason is that the design of the course is boring, and the teaching mode is monotonous, which cannot adapt to the needs of the new generation of children, resulting in students unable to apply what they have learned",
        "translation": "原因在于课程设计较为枯燥、教学模式单一，难以适应新一代学生的需求，导致学生无法学以致用。",
    },
    {
        "phrase": "Vue is a JavaScript framework for building user interfaces.",
        "translation": "Vue 是一个用于构建用户界面的 JavaScript 框架。",
    },
    {
        "phrase": "The whole framework is built based on standard HTML, CSS, and JavaScript",
        "translation": "整个框架建立在标准 HTML、CSS 和 JavaScript 之上。",
    },
    {
        "phrase": "Especially in the teacher end interface, a large number of log information needs to be called to help teachers grasp the programming situation of students in real time.",
        "translation": "尤其是在教师端界面中，需要调用大量日志信息，以帮助教师实时掌握学生的编程情况。",
    },
    {
        "phrase": "For back-end development, a combination of SpringBoot and Netty was chosen",
        "translation": "在后端开发中，作者选择了 SpringBoot 与 Netty 的组合方案。",
    },
    {
        "phrase": "Data communication and synchronization is the biggest difficulty",
        "translation": "数据通信与同步是整个系统开发过程中最大的难点。",
    },
    {
        "phrase": "Tomcat is a free open source Web server",
        "translation": "Tomcat 是一个免费、开源的 Web 服务器。",
    },
    {
        "phrase": "The system functions are divided into student end and teacher end",
        "translation": "系统功能分为学生端和教师端。",
    },
    {
        "phrase": "At present, 11 kinds of teaching case scenarios have been created according to the system functions",
        "translation": "目前已根据系统功能设计了 11 种教学案例场景。",
    },
    {
        "phrase": "All the running results of the front-end will also be displayed through the log management tool",
        "translation": "前端的所有运行结果也会通过日志管理工具展示出来。",
    },
    {
        "phrase": "11 teaching scene cases have been set up",
        "translation": "目前已依据教学内容设置了 11 个教学场景案例。",
    },
    {
        "phrase": "SocketSend function",
        "translation": "SocketSend 函数负责控制指令的发送。",
    },
    {
        "phrase": "Such a smart home teaching example not only exercises students' code example writing",
        "translation": "这样的智能家居教学案例不仅训练学生的代码编写能力，也能通过直观调试界面让编程学习更生动。",
    },
    {
        "phrase": "The difficulty of the whole system lies in the data transmission and synchronization",
        "translation": "整个系统的难点在于数据传输与同步。",
    },
    {
        "phrase": "The Websocket data is uniformly converted into json format",
        "translation": "WebSocket 数据会被统一转换为 JSON 格式。",
    },
    {
        "phrase": "The teacher end will first store the database after receiving the data",
        "translation": "教师端在接收到数据后，会先将数据存入数据库。",
    },
    {
        "phrase": "Case teaching simulates real-life scenarios",
        "translation": "案例教学通过模拟真实生活场景与问题开展教学。",
    },
    {
        "phrase": "This process can cultivate their ability to analyze and solve problems.",
        "translation": "这一过程能够培养学生分析问题和解决问题的能力。",
    },
    {
        "phrase": "Students begin to participate more actively in learning",
        "translation": "学生开始更加主动地参与学习。",
    },
    {
        "phrase": "The implementation of case teaching has significantly improved the quality of teaching.",
        "translation": "案例教学的实施显著提升了教学质量。",
    },
    {
        "phrase": "The C language teaching system mainly simulates the teaching hardware scene through the front-end and back-end deployment",
        "translation": "该 C 语言教学系统主要通过前后端部署来模拟教学硬件场景。",
    },
    {
        "phrase": "The difficulty of the whole teaching system",
        "translation": "整个教学系统的难点在于数据的同步与传输。",
    },
    {
        "phrase": "In the subsequent development, more simulation equipment can be added",
        "translation": "在后续开发中，还可以加入更多模拟设备。",
    },
]


def normalize_token(text: str) -> str:
    text = (
        text.replace("’", "'")
        .replace("‘", "'")
        .replace("“", '"')
        .replace("”", '"')
        .replace("–", "-")
        .replace("—", "-")
    )
    text = re.sub(r"^[^0-9A-Za-z]+|[^0-9A-Za-z]+$", "", text)
    text = text.replace("-", "")
    return text.lower()


def tokenize(text: str):
    return [token for token in (normalize_token(part) for part in text.split()) if token]


def load_pages(xhtml_path: Path):
    tree = ET.parse(xhtml_path)
    root = tree.getroot()
    pages = []
    for page in root.findall(".//{*}page"):
        words = []
        for word in page.findall(".//{*}word"):
            raw = "".join(word.itertext())
            normalized = normalize_token(raw)
            if not normalized:
                continue
            words.append(
                {
                    "raw": raw,
                    "norm": normalized,
                    "xMin": float(word.attrib["xMin"]),
                    "yMin": float(word.attrib["yMin"]),
                    "xMax": float(word.attrib["xMax"]),
                    "yMax": float(word.attrib["yMax"]),
                }
            )
        pages.append(
            {
                "width": float(page.attrib["width"]),
                "height": float(page.attrib["height"]),
                "words": words,
            }
        )
    return pages


def find_match(pages, target):
    target_tokens = tokenize(target["phrase"])
    for page_index, page in enumerate(pages):
        words = page["words"]
        limit = len(words) - len(target_tokens) + 1
        for start in range(max(0, limit)):
            window = words[start : start + len(target_tokens)]
            if [word["norm"] for word in window] != target_tokens:
                continue

            x_min = min(word["xMin"] for word in window)
            y_min = min(word["yMin"] for word in window)
            x_max = max(word["xMax"] for word in window)
            y_max = max(word["yMax"] for word in window)

            return {
                "phrase": target["phrase"],
                "translation": target["translation"],
                "page_index": page_index,
                "page_width": page["width"],
                "page_height": page["height"],
                "bbox_top_left": {
                    "x": round(x_min, 3),
                    "y": round(y_min, 3),
                    "width": round(x_max - x_min, 3),
                    "height": round(y_max - y_min, 3),
                },
                "bbox_pdf": {
                    "x": round(x_min, 3),
                    "y": round(page["height"] - y_max, 3),
                    "width": round(x_max - x_min, 3),
                    "height": round(y_max - y_min, 3),
                },
            }
    return None


def main():
    if len(sys.argv) != 3:
        raise SystemExit("Usage: python extract_pdf_translation_annotations.py <bbox_xhtml> <output_json>")

    xhtml_path = Path(sys.argv[1])
    output_path = Path(sys.argv[2])

    pages = load_pages(xhtml_path)
    matches = []
    missing = []

    for target in TARGETS:
        match = find_match(pages, target)
        if match:
            matches.append(match)
        else:
            missing.append(target)

    output = {
        "source_bbox": str(xhtml_path),
        "annotation_count": len(matches),
        "missing_count": len(missing),
        "annotations": matches,
        "missing": missing,
    }
    output_path.write_text(json.dumps(output, ensure_ascii=False, indent=2), encoding="utf-8")

    print(f"Matched {len(matches)} annotations.")
    if missing:
        print(f"Missing {len(missing)} annotations.")
        for item in missing:
            print(f"- {item['phrase']}")


if __name__ == "__main__":
    main()
