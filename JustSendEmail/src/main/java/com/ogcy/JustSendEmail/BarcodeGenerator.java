package com.ogcy.JustSendEmail;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.AlphaComposite;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class BarcodeGenerator {

    public static void main(String[] args) throws WriterException, IOException {
       Map hints = new HashMap();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
		    // Create a qr code with the url as content and a size of 250x250 px
		    bitMatrix = writer.encode("0dfa5fc5-cad5-4176-bcd5-6654219d2276", BarcodeFormat.QR_CODE, 250, 250, hints);
		    MatrixToImageConfig config = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);
		    // Load QR image
		    BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
		    // Load logo image
		    File file = new File("logo.png");
		    BufferedImage logoImage = ImageIO.read(file);
		    // Calculate the delta height and width between QR code and logo
		    int deltaHeight = qrImage.getHeight() - logoImage.getHeight();
		    int deltaWidth = qrImage.getWidth() - logoImage.getWidth();
		    // Initialize combined image
		    BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g = (Graphics2D) combined.getGraphics();
		    // Write QR code to new image at position 0/0
		    g.drawImage(qrImage, 0, 0, null);
		    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		    // Write logo into combine image at position (deltaWidth / 2) and
		    // (deltaHeight / 2). Background: Left/Right and Top/Bottom must be
		    // the same space for the logo to be centered
		    g.drawImage(logoImage, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);
		    // Write combined image as PNG to OutputStream
		    ImageIO.write(combined, "png", new File("output.png"));
		    //ImageIO.write(im, formatName, output)
		    System.out.println("done");
		} catch (Exception e) {
		    System.out.println(e);
		}  

    }

}

