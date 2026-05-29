const fs = require("fs");
const path = require("path");
const {
  PDFDocument,
  PDFHexString,
  PDFName,
  PDFBool,
  PDFArray,
} = require("../tools/pdf-lib.min.js");

function usage() {
  console.error(
    "Usage: node apply_pdf_translation_annotations.js <input_pdf> <annotations_json> <output_pdf>"
  );
  process.exit(1);
}

function rectsOverlap(a, b) {
  return !(
    a.x + a.w <= b.x ||
    b.x + b.w <= a.x ||
    a.y + a.h <= b.y ||
    b.y + b.h <= a.y
  );
}

function placeNote(pageWidth, pageHeight, bbox, usedRects) {
  const noteSize = 18;
  const margin = 6;

  let x = bbox.x + bbox.width + margin;
  if (x + noteSize > pageWidth - margin) {
    x = bbox.x - noteSize - margin;
  }
  x = Math.max(margin, Math.min(x, pageWidth - noteSize - margin));

  let y = bbox.y + Math.max(0, bbox.height / 2 - noteSize / 2);
  y = Math.max(margin, Math.min(y, pageHeight - noteSize - margin));

  const tryPlacements = [];
  for (let i = 0; i < 10; i += 1) {
    tryPlacements.push({ x, y: Math.max(margin, y - i * (noteSize + 2)) });
    tryPlacements.push({
      x,
      y: Math.min(pageHeight - noteSize - margin, y + i * (noteSize + 2)),
    });
  }

  const fallbackX = x === bbox.x + bbox.width + margin
    ? Math.max(margin, bbox.x - noteSize - margin)
    : Math.min(pageWidth - noteSize - margin, bbox.x + bbox.width + margin);

  for (let i = 0; i < 10; i += 1) {
    tryPlacements.push({
      x: fallbackX,
      y: Math.max(margin, y - i * (noteSize + 2)),
    });
  }

  for (const candidate of tryPlacements) {
    const rect = { x: candidate.x, y: candidate.y, w: noteSize, h: noteSize };
    if (!usedRects.some((used) => rectsOverlap(rect, used))) {
      usedRects.push(rect);
      return rect;
    }
  }

  const rect = { x, y, w: noteSize, h: noteSize };
  usedRects.push(rect);
  return rect;
}

function ensureAnnotsArray(pdfDoc, page) {
  let annots = page.node.Annots();
  if (!annots) {
    annots = pdfDoc.context.obj([]);
    page.node.set(PDFName.of("Annots"), annots);
  }
  return annots;
}

async function main() {
  if (process.argv.length !== 5) {
    usage();
  }

  const [, , inputPdf, annotationsJson, outputPdf] = process.argv;
  const pdfBytes = fs.readFileSync(inputPdf);
  const data = JSON.parse(fs.readFileSync(annotationsJson, "utf8"));
  const pdfDoc = await PDFDocument.load(pdfBytes);
  const pages = pdfDoc.getPages();
  const usedRectsByPage = new Map();

  for (const item of data.annotations) {
    const page = pages[item.page_index];
    if (!page) {
      continue;
    }

    const pageWidth = page.getWidth();
    const pageHeight = page.getHeight();
    const bbox = item.bbox_pdf;
    const usedRects = usedRectsByPage.get(item.page_index) || [];
    usedRectsByPage.set(item.page_index, usedRects);
    const noteRect = placeNote(pageWidth, pageHeight, bbox, usedRects);

    const annotationBody = `原文：${item.phrase}\n译文：${item.translation}`;
    const annotation = pdfDoc.context.obj({
      Type: PDFName.of("Annot"),
      Subtype: PDFName.of("Text"),
      Rect: [
        noteRect.x,
        noteRect.y,
        noteRect.x + noteRect.w,
        noteRect.y + noteRect.h,
      ],
      Contents: PDFHexString.fromText(annotationBody),
      T: PDFHexString.fromText("中文译注"),
      Name: PDFName.of("Comment"),
      Open: PDFBool.False,
      C: [1, 0.95, 0.35],
    });

    const annots = ensureAnnotsArray(pdfDoc, page);
    const annotationRef = pdfDoc.context.register(annotation);

    if (annots instanceof PDFArray) {
      annots.push(annotationRef);
    } else {
      page.node.addAnnot(annotationRef);
    }
  }

  fs.writeFileSync(outputPdf, await pdfDoc.save());
  console.log(`Saved annotated PDF to ${path.resolve(outputPdf)}`);
}

main().catch((error) => {
  console.error(error);
  process.exit(1);
});
