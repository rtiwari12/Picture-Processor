package commands;

import java.util.Scanner;

import model.Flip;
import model.IPixel;
import model.ImageModel;
import model.ImageModelImpl;
import model.ImageModelMap;
import model.Pixel;

/**
 * This class represents a generic flip command that can be performed
 * on an object of an image model.
 */
public abstract class AbstractFlipCommand extends AbstractImageCommand {

  /**
   * Constructs a generic command using a given output, map of image models, and scanner.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map from which to choose an image to run the command on
   * @param scanner       takes input from the user to perform the command as intended
   * @throws IllegalArgumentException if any given parameters are null
   */
  public AbstractFlipCommand(Appendable output, ImageModelMap imageModelMap,
                             Scanner scanner) throws IllegalArgumentException {
    super(output, imageModelMap, scanner);
  }

  /**
   * Creates an image model that results from flipping the image model that the method is
   * being performed on.
   *
   * @param model     the image model that is being flipped
   * @param direction the direction that the image model is being flipped in
   * @return the resulting image model
   */
  public ImageModel flip(ImageModel model, Flip direction) {
    int height = model.getImageHeight();
    int width = model.getImageWidth();
    IPixel[][] newPixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (direction == Flip.HORIZONTAL) {
          newPixels[i][width - 1 - j] = model.getPixelAt(i, j);
        } else if (direction == Flip.VERTICAL) {
          newPixels[height - 1 - i][j] = model.getPixelAt(i, j);
        }
      }
    }
    return new ImageModelImpl(newPixels, height, width);
  }

}
