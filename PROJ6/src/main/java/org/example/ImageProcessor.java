package org.example;

import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class ImageProcessor {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.exit(1);
        }

        String inputDirectory = args[0];
        String outputDirectory = args[1];
        for (int thredNum = 1; thredNum < 10; thredNum++) {
            try {
                List<Path> files;
                Path source = Path.of(inputDirectory);
                try (var stream = Files.list(source)) {
                    files = stream.collect(Collectors.toList());
                }

                ForkJoinPool customThreadPool = new ForkJoinPool(thredNum);

                long time = System.currentTimeMillis();

                customThreadPool.submit(() ->
                        files.parallelStream()
                                .map(path -> Pair.of(path.getFileName().toString(), loadImage(path)))
                                .map(pair -> Pair.of(pair.getLeft(), processImage(pair.getRight())))
                                .forEach(pair -> saveImage(pair.getRight(), outputDirectory, pair.getLeft()))
                ).get();

                System.out.println("For: " + thredNum +" Time: " + (System.currentTimeMillis() - time) + "ms");

                customThreadPool.shutdown();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static BufferedImage loadImage(Path path) {
        try {
            return ImageIO.read(path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static BufferedImage processImage(BufferedImage original) {
        BufferedImage processedImage = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                int rgb = original.getRGB(i, j);
                Color color = new Color(rgb);
                int red = color.getRed();
                int blue = color.getBlue();
                int green = color.getGreen();
                Color outColor = new Color(red, blue, green);
                processedImage.setRGB(original.getWidth() - i -1,original.getHeight() - j-1, outColor.getRGB());
            }
        }
        return processedImage;
    }

    private static void saveImage(BufferedImage image, String outputDirectory, String fileName) {
        try {
            Path outputPath = Path.of(outputDirectory, fileName);
            ImageIO.write(image, "jpg", outputPath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
