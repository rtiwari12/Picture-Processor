import java.util.HashMap;
import java.util.Scanner;

import commands.LoadImage;
import controller.ImageController;
import controller.ImageControllerImpl;
import model.ImageModelMap;
import model.ImageModelMapImpl;
import view.ImageModelView;
import view.ImageModelViewImpl;

/**
 * This class creates a GUI view through which a user can interact with the image manipulator.
 */
public class ImageProgramGUI {
  /**
   * Creates the GUI view.
   *
   * @param args the input to specify a file that inputs can be read from
   */
  public static void main(String [] args) {
    ImageModelMap imageModelMap = new ImageModelMapImpl(new HashMap<>());

    Scanner scanner = new Scanner(System.in);
    LoadImage imageLoader = new LoadImage(System.out, imageModelMap, scanner);
    imageModelMap.add("koala",
            imageLoader.loadImage("StarterImages/koala.jpg"), System.out);
    imageModelMap.add("jellyfish",
            imageLoader.loadImage("StarterImages/jellyfish.jpg"), System.out);
    imageModelMap.add("penguins",
            imageLoader.loadImage("StarterImages/penguins.jpg"), System.out);

    ImageModelView view = new ImageModelViewImpl(imageModelMap);
    ImageController controller = new ImageControllerImpl(imageModelMap, view);
  }
}