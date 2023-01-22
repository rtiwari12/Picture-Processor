package commands;

import java.util.Scanner;

import model.IPixel;
import model.ImageModel;
import model.ImageModelImpl;
import model.ImageModelMap;
import model.Pixel;

/**
 * This class represents a Brighten command that can be performed on an object of an image model.
 */
public class Brighten extends AbstractImageCommand {
  private final String constant;

  /**
   * Constructs a Brighten command using a given output, map of image models, and scanner.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map from which to choose an image to run the command on
   * @param scanner       takes input from the user to perform the command as intended
   * @throws IllegalArgumentException if any given parameters are null or if the constant
   *                                  value is not an integer
   */
  public Brighten(Appendable output, ImageModelMap imageModelMap,
                  Scanner scanner) throws IllegalArgumentException {
    super(output, imageModelMap, scanner);
    this.constant = scanner.next();
  }

  /**
   * Creates a new image model by performing a Brighten method on a given image model.
   *
   * @param oldModel the image model on which the method is performed
   * @throws IllegalArgumentException if the constant value is not an integer
   * @return a new image model based on the method performed
   */
  @Override
  protected ImageModel newModel(ImageModel oldModel) throws IllegalArgumentException {
    try {
      return brighten(oldModel, Integer.parseInt(this.constant));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Constant must be an integer.");
    }
  }

  /**
   * Creates an image model that results from brightening the image model that the method
   * is being performed on by a given constant.
   *
   * @param constant the constant by which to brighten the image model
   * @return the resulting image model
   */
  private ImageModel brighten(ImageModel model, int constant) {
    int height = model.getImageHeight();
    int width = model.getImageWidth();
    IPixel[][] newPixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red;
        int green;
        int blue;
        if (constant >= 0) {
          red = Math.min(model.getPixelAt(i, j).getRed() + constant, 255);
          green = Math.min(model.getPixelAt(i, j).getGreen() + constant, 255);
          blue = Math.min(model.getPixelAt(i, j).getBlue() + constant, 255);
        }
        else {
          red = Math.max(model.getPixelAt(i, j).getRed() + constant, 0);
          green = Math.max(model.getPixelAt(i, j).getGreen() + constant, 0);
          blue = Math.max(model.getPixelAt(i, j).getBlue() + constant, 0);
        }
        newPixels[i][j] = new Pixel(red, green, blue);
      }
    }
    return new ImageModelImpl(newPixels, height, width);
  }
}