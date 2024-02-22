package com.example.multhithradingconcurencyparfomance.image_repaint_ex;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Slf4j
public class PictureRecolored {
    public void colorImage(String sourcePath, String targetPath) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(sourcePath));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        long l = System.currentTimeMillis();
//        colorBatch(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
        colorBatch(originalImage, resultImage, 3);
        System.out.println(System.currentTimeMillis() - l);

        ImageIO.write(resultImage, "jpg", new File(targetPath));
    }

    private void colorBatch(BufferedImage originalImage, BufferedImage resultImage, int threadsNum) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight() / threadsNum;

        try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {
            IntStream.range(1, threadsNum)
                    .parallel()
                    .forEach(i -> executorService.submit(() -> colorBatch(originalImage, resultImage, 0, i * originalImage.getHeight() / threadsNum, width, height)));
        }
    }

    private void colorBatch(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner,
                            int width, int height) {
        for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++) {
            for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++) {
                recolorPixel(originalImage, resultImage, x, y);
            }
        }
    }

    private void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
        int rgb = originalImage.getRGB(x, y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        if (isShadeOfGray(red, green, blue)) {
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 20);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }
        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(resultImage, x, y, newRGB);
    }

    private void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    private static boolean isShadeOfGray(int red, int green, int blue) {
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
    }

    private static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;

        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;

        rgb |= 0xFF000000;

        return rgb;
    }

    private int getRed(int gbr) {
        return (gbr & 0x00FF0000) >> 16;
    }

    private int getGreen(int gbr) {
        return (gbr & 0x0000FF00) >> 8;
    }

    private int getBlue(int gbr) {
        return gbr & 0x000000FF;
    }

    private void joinThread(Thread th) {
        try {
            th.join(2000);
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
