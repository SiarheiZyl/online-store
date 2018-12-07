package com.online_market.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;


/**
 * This Util exists for helping developers to save different images into
 * server physical memory inside apache tomcat catalog
 *
 * @author Siarhei
 * @version 1.0
 */
public class ImageUtil {

    /**
     * Apache log4j object is used to log all important info
     */
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ImageUtil.class);

    /**
     * Name of the folder where image will be saved
     */
    private static final String IMAGES = "images";

    /**
     * Property of the system which helps us to find tomcat root folder
     */
    private static final String TOMCAT_HOME_PROPERTY = "catalina.home";

    /**
     * Absolute path to the tomcat root folder
     */
    private static final String TOMCAT_HOME_PATH = System.getProperty(TOMCAT_HOME_PROPERTY);

    /**
     * Path to the images folder inside the tomcat root catalog
     */
    private static final String IMAGES_PATH = TOMCAT_HOME_PATH + File.separator + IMAGES;

    /**
     * Field which stores Java API of images folder
     */
    private static final File IMAGES_DIR = new File(IMAGES_PATH);

    /**
     * Absolute path to the images folder inside the tomcat root catalog
     */
    private static final String IMAGES_DIR_ABSOLUTE_PATH = IMAGES_DIR.getAbsolutePath() + File.separator;


    /**
     * Size in pixels for scaling original images(used for images in bucket and statistics)
     */
    private static final int SMALL_SIZE = 120;

    /**
     * Size in pixels for scaling original images(used for images in catalog)
     */
    private static final int MEDIUM_SIZE = 300;


    /**
     * Empty constructor
     */
    private ImageUtil() {

    }

    /**
     * Method saves some file into images folder(it is assumed the this will be a picture)
     *
     * @param name - Future name of the saving file
     * @param file - Directly, the file itself
     */
    public static void uploadImage(String name, MultipartFile file) {
        File image = new File(IMAGES_DIR_ABSOLUTE_PATH + name);
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(image))) {
            stream.write(file.getBytes());
            stream.close();
        } catch (Exception e) {
            log.error("Something was wrong during uploading image.", e);
        }

        try {
            saveScaledImages(file, name);
        } catch (IOException e) {
            log.error("IOException while uploading images");
        }
    }

    /**
     * Saving image in medium and small formats
     *
     * @param file image
     * @param name name
     * @throws IOException
     */
    private static void saveScaledImages(MultipartFile file, String name) throws IOException {
        InputStream in = new ByteArrayInputStream(file.getBytes());
        BufferedImage image = ImageIO.read(in);

        int height = image.getHeight();
        int width = image.getWidth();

        double scaleSmall = SMALL_SIZE / (double) width;
        double scaleMedium = MEDIUM_SIZE / (double) width;

        int smallWidth = (int) (width * scaleSmall);
        int smallHeight = (int) (height * scaleSmall);

        int mediumWidth = (int) (width * scaleMedium);
        int mediumHeight = (int) (height * scaleMedium);

        BufferedImage smallImage = new BufferedImage(smallWidth, smallHeight, BufferedImage.TYPE_INT_RGB);

        Graphics g = smallImage.createGraphics();
        g.drawImage(image, 0, 0, smallWidth, smallHeight, null);
        g.dispose();

        BufferedImage mediumImage = new BufferedImage(mediumWidth, mediumHeight, BufferedImage.TYPE_INT_RGB);


        Graphics gr = mediumImage.createGraphics();
        gr.drawImage(image, 0, 0, mediumWidth, mediumHeight, null);
        gr.dispose();

        File mediumFile = new File(IMAGES_DIR_ABSOLUTE_PATH + name + "medium");
        ImageIO.write(mediumImage, "jpg", mediumFile);

        File smallFile = new File(IMAGES_DIR_ABSOLUTE_PATH + name + "small");
        ImageIO.write(smallImage, "jpg", smallFile);
    }


    /**
     * Method creates images directory if it doesn't exist
     */
    public static void createImagesDirectoryIfNeeded() {
        if (!IMAGES_DIR.exists()) {
            IMAGES_DIR.mkdirs();
        }
    }

    /**
     * Method give absolute path to the image folder
     *
     * @return path to the folder.
     */
    public static String getImagesDirectoryAbsolutePath() {
        return IMAGES_DIR_ABSOLUTE_PATH;
    }
}