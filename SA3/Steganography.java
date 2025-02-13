import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Short Assignment 3 (SA-3) provided code
 * Use steganography to hide a message in an image by subtly altering the red component of a pixel.
 * Complete hideMessage and getMessage methods.
 *
 * @author `Tim Pierson`, Dartmouth CS10, Winter 2024
 * @author Ben Sheppard
 */
public class Steganography {
    private Character STOP_CHARACTER = '*';
    private int BITS_PER_CHARACTER = 7;
    private int MAX_COLOR = 255;

    /**
     * Hides a message in an image.  First add a stop character to the end of the message so when a message is recovered
     * the recovery operations knows it is at the end of the message when it encounters the stop character.
     * Convert each character in message to "1" and "0" bit.
     * For each bit, alter the red component so that "0" bits result in an even red color, while "1" bits result in an odd
     * red color.  Use Integer.toBinaryString(c) to create a String of "1" and "0" bits from Character c.
     * @param original - BufferedImage holding the image to hide the message in
     * @param message - String message to hide
     * @return - altered BufferedImage result where pixels are even if bit is "0" and odd if bit is "1"
     * @throws Exception for file not found
     */
    public BufferedImage hideMessage(BufferedImage original, String message) throws Exception {
        int x = 0, y = 0;
        message += STOP_CHARACTER;

        //make copy of original image so we don't alter the original image
        BufferedImage result = new BufferedImage(original.getColorModel(), original.copyData(null), original.getColorModel().isAlphaPremultiplied(), null);

        for(int i = 0; i < message.length(); i++){ //iterates through each character of the message
            Character c = message.charAt(i); //creates character at position i of the message
            String bits = Integer.toBinaryString(c); //converts that character to binary

            while(bits.length() < 7) { //if the binary is less than 7 bits long, prepends a 0, as required.
                bits = "0" + bits;
            }

            for(int j = 0; j < bits.length(); j++){ //iterates through each bit of the binary rep. of character
                Color clr = new Color(original.getRGB(x, y)); //gets Color object of the photo at (x, y) (original)
                if(bits.charAt(j) == '0'){
                    if(clr.getRed() % 2 == 1) { //if odd, makes it even
                        if(clr.getRed() != MAX_COLOR){
                            Color newClr = new Color(clr.getRed() + 1,clr.getGreen() ,clr.getBlue() );
                            result.setRGB(x, y, newClr.getRGB());
                        }
                        else{
                            Color newClr = new Color(clr.getRed() - 1,clr.getGreen() ,clr.getBlue() );
                            result.setRGB(x, y, newClr.getRGB());
                        }
                    }
                }
                else{
                    if(clr.getRed() % 2 == 0){ //if even, makes it odd
                        Color newClr = new Color(clr.getRed() + 1,clr.getGreen() ,clr.getBlue() );
                        result.setRGB(x, y, newClr.getRGB());
                    }
                }
                x++;
                if(x > result.getWidth() - 1){
                    y++;
                    if(y > result.getHeight() - 1 ){
                        System.err.println("Error, not enough pixels in image.");
                    }
                    else{
                        x = 0;
                    }
                }
            }
        }
        return result;
    }


    /**
     * Recover message hidden in image.  Loop until stop character is encountered.
     * @param img - BufferedImage with hidden message
     * @return String with recovered message
     */
    public String getMessage(BufferedImage img) {
        String message = "";
        String temp = "";
        int x = 0, y = 0;
        boolean condition = true;

        while(condition){
            for(int i = 0; i < BITS_PER_CHARACTER; i++){
                Color readColor = new Color(img.getRGB(x, y));
                if(readColor.getRed() % 2 == 0){
                    temp += '0';
                }
                else{
                    temp += '1';
                }
                x++;
                if(x > img.getWidth() - 1){
                    x = 0;
                    y++;
                }
            }
            Character lastCharacter = (char)Integer.parseInt(temp, 2);
            if(!(lastCharacter == STOP_CHARACTER)){
                message += lastCharacter;
                temp = "";
            }
            else{
                condition = false;
            }


        }

        return message;
    }

    public static void main(String[] args) throws Exception {
        String originalImageFileName = "pictures/baker.png";
        String hiddenImageFileName = "pictures/hidden.png"; //do not use lossy jpg format, corrupts message, use png
        String message = "Ben Sheppard";
        //hide message in image
        System.out.println("Hiding message: " + message);
        BufferedImage image = ImageIOLibrary.loadImage(originalImageFileName);
        Steganography s = new Steganography();
        BufferedImage hiddenMessageImage = s.hideMessage(image, message);
        ImageGUI gui = new ImageGUI("SA-3  Can you tell the difference between images?",image, hiddenMessageImage);

        //save image with hidden message to disk
        ImageIOLibrary.saveImage(hiddenMessageImage, hiddenImageFileName,"png");

        //read image from disk and retrieve message from image
        BufferedImage img = ImageIOLibrary.loadImage(hiddenImageFileName);
        String recoveredMessage = s.getMessage(hiddenMessageImage);
        System.out.println("Recovered message: " + recoveredMessage);
    }
}
