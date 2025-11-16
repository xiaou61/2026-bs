package com.xiaou.studyroom.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Component
public class QRCodeUtil {

    @Value("${qrcode.width}")
    private int width;

    @Value("${qrcode.height}")
    private int height;

    /**
     * 生成二维码到文件
     */
    public String generateQRCodeImage(String text, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        BufferedImage bufferedImage = toBufferedImage(bitMatrix);
        File outputFile = new File(filePath);
        ImageIO.write(bufferedImage, "PNG", outputFile);

        return filePath;
    }

    /**
     * 生成二维码字节数组
     */
    public byte[] generateQRCodeBytes(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        BufferedImage bufferedImage = toBufferedImage(bitMatrix);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "PNG", baos);

        return baos.toByteArray();
    }

    /**
     * 生成二维码Base64字符串（用于前端显示）
     */
    public String generateQRCodeBase64(String text) throws WriterException, IOException {
        byte[] imageBytes = generateQRCodeBytes(text);
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
    }

    /**
     * 生成二维码内容字符串
     */
    public String generateQRCodeContent(Long reservationId, Long userId, String seatNumber) {
        return String.format("STUDY_ROOM:%d:%d:%s:%d", reservationId, userId, seatNumber, System.currentTimeMillis());
    }

    /**
     * 解析二维码内容并验证
     */
    public boolean validateQRCodeContent(String qrcodeContent, Long reservationId, Long userId) {
        if (qrcodeContent == null || !qrcodeContent.startsWith("STUDY_ROOM:")) {
            return false;
        }

        try {
            String[] parts = qrcodeContent.split(":");
            if (parts.length >= 5) {
                Long codeReservationId = Long.parseLong(parts[1]);
                Long codeUserId = Long.parseLong(parts[2]);
                String seatNumber = parts[3];

                // 验证预约ID和用户ID是否匹配
                return codeReservationId.equals(reservationId) && codeUserId.equals(userId);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * BitMatrix转BufferedImage
     */
    private BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
}