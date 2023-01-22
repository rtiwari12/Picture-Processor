package commands;

import java.util.Scanner;

import model.Flip;
import model.ImageModel;
import model.ImageModelMap;

/**
 * This class represents a Horizontal Flip command that can be performed on an object
 * of an image model.
 */
public class HorizontalFlip extends AbstractFlipCommand {
  /**
   * Constructs a Horizontal Flip command using a given output, map of image models, and scanner.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map from which to choose an image to run the command on
   * @param scanner       takes input from the user to perform the command as intended
   * @throws IllegalArgumentException if any given parameters are null
   */
  public HorizontalFlip(Appendable output, ImageModelMap imageModelMap,
                        Scanner scanner) throws IllegalArgumentException {
    super(output, imageModelMap, scanner);
  }

  /**
   * Creates a new image model by performing a Horizontal Flip method on a given image model.
   *
   * @param oldModel the image model on which the method is performed
   * @return a new image model based on the method performed
   */
  @Override
  protected ImageModel newModel(ImageModel oldModel) {
    return flip(oldModel, Flip.HORIZONTAL);
  }
}