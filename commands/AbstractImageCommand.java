package commands;

import java.io.IOException;
import java.util.Scanner;

import model.ImageModel;
import model.ImageModelMap;

/**
 * This class represents a generic command that can be performed on an object of an image model.
 */
public abstract class AbstractImageCommand implements ImageCommand {
  protected final Appendable output;
  protected final ImageModelMap imageModelMap;
  protected final Scanner scanner;

  /**
   * Constructs a generic command using a given output, map of image models, and scanner.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map from which to choose an image to run the command on
   * @param scanner       takes input from the user to perform the command as intended
   * @throws IllegalArgumentException if any given parameters are null
   */
  public AbstractImageCommand(Appendable output, ImageModelMap imageModelMap,
                              Scanner scanner) throws IllegalArgumentException {
    if (output == null || imageModelMap == null || scanner == null) {
      throw new IllegalArgumentException("No given parameters may contain null values.");
    }
    this.output = output;
    this.imageModelMap = imageModelMap;
    this.scanner = scanner;
  }

  /**
   * Edits an image model and adds a map with one entry containing a name of the new model as
   * the key and a corresponding image model derived from performing an image model method as
   * the value based on the type of command run.
   *
   * @throws IllegalStateException if writing to the output stream fails
   */
  @Override
  public void edit() throws IllegalStateException {
    try {
      String modelName = scanner.next();
      String newName = scanner.next();

      try {
        ImageModel model = this.imageModelMap.find(modelName);
        try {
          this.imageModelMap.add(newName, newModel(model), this.output);
          this.output.append("Edit completed.\n");
        } catch (IllegalArgumentException e) {
          this.output.append("Constant must be an integer.\n");
        }
      } catch (NullPointerException e) {
        this.output.append("Please load " + modelName + " before operating on it.\n");
      }

    } catch (IOException e) {
      throw new IllegalStateException("Writing to output stream failed.");
    }
  }

  /**
   * Creates a new image model by performing an image model method on a given image model.
   *
   * @param oldModel the image model on which the method is performed
   * @return a new image model based on the method performed
   */
  protected abstract ImageModel newModel(ImageModel oldModel);

}