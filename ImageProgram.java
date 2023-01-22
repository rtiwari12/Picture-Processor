import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import controller.ImageController;
import controller.ImageControllerImpl;
import model.ImageModelMap;
import model.ImageModelMapImpl;

/**
 * This class executes the controller's run method with optional initial argument inputs.
 */
public class ImageProgram {
  /**
   * Executes the controller's run method.
   *
   * @param args the input to specify a file that inputs can be read from
   */
  public static void main(String [] args) {
    ImageModelMap imageModelMap = new ImageModelMapImpl(new HashMap<>());
    ImageController controller = new ImageControllerImpl(imageModelMap);
    if (args.length > 1) {
      throw new IllegalArgumentException("Input must either be empty to use console "
              + "or contain a valid filepath to read from.");
    }
    if (args.length == 1) {
      try {
        FileReader fileReader = new FileReader(args[0]);
        controller.run(fileReader, true);
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("Input must either be empty to use console "
                + "or contain a valid filepath to read from.");
      }
      controller.run(new InputStreamReader(System.in), false);
    }
    if (args.length == 0) {
      controller.run(new InputStreamReader(System.in), true);
    }
  }
}