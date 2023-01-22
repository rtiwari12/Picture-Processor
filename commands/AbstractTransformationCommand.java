package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import model.IPixel;
import model.ImageModel;
import model.ImageModelImpl;
import model.ImageModelMap;
import model.Pixel;
import model.Transformation;

/**
 * This class represents a generic transformation command that can be performed
 * on an object of an image model.
 */
public abstract class AbstractTransformationCommand extends AbstractImageCommand {

  /**
   * Constructs a generic command using a given output, map of image models, and scanner.
   *
   * @param output        the output that any prompts or error messages will be written to
   * @param imageModelMap the map from which to choose an image to run the command on
   * @param scanner       takes input from the user to perform the command as intended
   * @throws IllegalArgumentException if any given parameters are null
   */
  public AbstractTransformationCommand(Appendable output, ImageModelMap imageModelMap,
                                       Scanner scanner) throws IllegalArgumentException {
    super(output, imageModelMap, scanner);
  }

  /**
   * Creates an image model that results from transforming the red, green, and blue values
   * of each individual pixel based on the specified operation.
   *
   * @param model the image model being transformed
   * @param type  the type of transformation being done on the image model
   * @return the resulting image model
   */
  protected ImageModel transform(ImageModel model, Transformation type) {
    double[][] transformation = new double[3][3];

    switch (type) {
      case RED:
        createMatrix(transformation, new ArrayList<>(
                Arrays.asList(1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0)));
        break;
      case GREEN:
        createMatrix(transformation, new ArrayList<>(
                Arrays.asList(0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0)));
        break;
      case BLUE:
        createMatrix(transformation, new ArrayList<>(
                Arrays.asList(0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0)));
        break;
      case MAXVALUE: //nonsense matrix to signify we want max value component
        createMatrix(transformation, new ArrayList<>(
                Arrays.asList(-1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)));
        break;
      case LUMA:
        createMatrix(transformation, new ArrayList<>(
                Arrays.asList(0.2126, 0.7152, 0.0722, 0.2126, 0.7152, 0.0722,
                        0.2126, 0.7152, 0.0722)));
        break;
      case INTENSITY:
        createMatrix(transformation, new ArrayList<>(
                Arrays.asList(1.0 / 3, 1.0 / 3, 1.0 / 3, 1.0 / 3, 1.0 / 3, 1.0 / 3,
                        1.0 / 3, 1.0 / 3, 1.0 / 3)));
        break;
      case SEPIA:
        createMatrix(transformation, new ArrayList<>(
                Arrays.asList(0.393, 0.769, 0.189, 0.349, 0.686, 0.168,
                        0.272, 0.534, 0.131)));
        break;
      default:
        return model; // return original channel if invalid transformation type
    }
    return new ImageModelImpl(transformationHelper(model, transformation),
            model.getImageHeight(), model.getImageWidth());
  }

  // Creates a 2 dimensional array based on the values passed in
  private void createMatrix(double[][] matrix, ArrayList<Double> values) {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        matrix[i][j] = values.get(0);
        values.remove(0);
      }
    }
  }

  /**
   * Transform the image model using the supplied matrix.
   *
   * @param model          the image model being transformed
   * @param transformation represents a linear transformation
   * @return a list of Pixels that compose an Image after its transformation
   */
  private IPixel[][] transformationHelper(ImageModel model, double[][] transformation) {
    int height = model.getImageHeight();
    int width = model.getImageWidth();
    IPixel[][] newPixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int newRed;
        int newGreen;
        int newBlue;
        IPixel currentPixel = model.getPixelAt(i, j);
        if (transformation[0][0] == -1) {
          int maxValue = Math.max(currentPixel.getRed(),
                  Math.max(currentPixel.getGreen(), currentPixel.getBlue()));
          newPixels[i][j] = new Pixel(maxValue, maxValue, maxValue);
        }
        else {
          newRed = (int) ((currentPixel.getRed() * transformation[0][0])
                  + ((currentPixel.getGreen() * transformation[0][1]))
                  + ((currentPixel.getBlue() * transformation[0][2])));
          newGreen = (int) ((currentPixel.getRed() * transformation[1][0])
                  + ((currentPixel.getGreen() * transformation[1][1]))
                  + ((currentPixel.getBlue() * transformation[1][2])));
          newBlue = (int) ((currentPixel.getRed() * transformation[2][0])
                  + ((currentPixel.getGreen() * transformation[2][1]))
                  + ((currentPixel.getBlue() * transformation[2][2])));
          newPixels[i][j] = new Pixel(Math.max(Math.min(255, newRed), 0),
                  Math.max(Math.min(255, newGreen), 0),
                  Math.max(Math.min(255, newBlue), 0));
        }
      }
    }
    return newPixels;
  }
}
