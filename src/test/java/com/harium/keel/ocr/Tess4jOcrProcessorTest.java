package com.harium.keel.ocr;

import com.harium.keel.awt.source.BufferedImageSource;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Tess4jOcrProcessorTest {

    Tess4jOcrProcessor processor;

    @Before
    public void setUp() {
        processor = new Tess4jOcrProcessor();
    }

    @Test
    public void testOcr() throws IOException {
        File file = new File("src/main/resources/slack.png");
        BufferedImage image = ImageIO.read(file);

        String text = processor.apply(new BufferedImageSource(image));
        assertEquals("someone 7:52 PM\n" + "Isso é apenas uma mensagem de teste.\n", text);
    }

    //@Test
    public void testOcrCaptcha1() throws IOException {
        File file = new File("src/main/resources/captcha1.png");
        BufferedImage image = ImageIO.read(file);

        processor.whitelist(Tess4jOcrProcessor.Whitelist.LETTERS_UPPER_CASE);
        String text = processor.apply(new BufferedImageSource(image));
        assertEquals("IBTWG", text);
    }

    //@Test
    public void testOcrCaptcha2() throws IOException {
        File file = new File("src/main/resources/captcha2.png");
        BufferedImage image = ImageIO.read(file);

        processor.whitelist(Tess4jOcrProcessor.Whitelist.LETTERS_UPPER_CASE);
        String text = processor.apply(new BufferedImageSource(image));
        assertEquals("MVBLB", text);
    }

}
