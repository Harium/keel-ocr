package com.harium.keel.ocr;

import com.harium.keel.core.Processor;
import com.harium.keel.core.source.ImageSource;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tess4jOcrProcessor implements Processor<String> {

    private static final String DEFAULT_LANG = "eng";

    private String language = DEFAULT_LANG;
    private String dataPath = "/usr/share/tesseract/tessdata";

    @Override
    public String apply(ImageSource feature) {
        Tesseract instance = new Tesseract();
        instance.setDatapath(dataPath);
        instance.setLanguage(language);
        instance.setOcrEngineMode(1);

        BufferedImage image = getBufferedImage(feature);
        Rectangle rectangle = new Rectangle();
        rectangle.x = 0;
        rectangle.y = 0;
        rectangle.width = image.getWidth();
        rectangle.height = image.getHeight();

        try {
            return instance.doOCR(image, rectangle);
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        return "";
    }

    private BufferedImage getBufferedImage(ImageSource feature) {
        BufferedImage image = new BufferedImage(feature.getWidth(), feature.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        for (int y = 0; y < feature.getHeight(); y++) {
            for (int x = 0; x < feature.getWidth(); x++) {
                int rgb = feature.getRGB(x, y);
                g.setColor(new Color(rgb));
                g.fillRect(x, y, 1, 1);
            }
        }

        return image;
    }

    public Tess4jOcrProcessor dataPath(String dataPath) {
        this.dataPath = dataPath;
        return this;
    }

    public Tess4jOcrProcessor language(String language) {
        this.language = language;
        return this;
    }

}
