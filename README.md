The design of our Image Processor begins with the model, which starts with the ImageModel interface, used to represent
operations that can be used to monitor the state of an image model, without the ability to modify the model itself.
it. We decided to remove the ImageModelState interface and move its methods to the ImageModel interface while removing
the ImageModel interface methods that manipulated the image models themselves so that the transformation logic would
be confined to the command function objects. The ImageModelImpl class is an implementation of the ImageModel
interface, and one object represents one image that can be transformed through different operations. One instance of
an ImageModelImpl contains a private, final 2-D array of Pixels used to represent each individual pixel in the PPM
image. An individual pixel is represented by the Pixel class, which implements the IPixel interface that allows us to
access but not modify the individual red, green, and blue values of each Pixel. Within the ImageModelImpl class is a
method 'getPixelAt(int row, int col)' which returns a direct reference to the specified pixel from our 2-D array
representation, but this causes no issue because all field variable in the Pixel class are final, meaning it is not
possible to modify an existing Pixel. We have removed our ImageUtil class which contained our load method, and
transferred that logic over into the load command function object as we did with the ImageModel interface methods.
We have added in an ImageModelMap interface, which contains methods that utilize and modify a Map<String, ImageModel>,
which our command function objects now use instead of passing in a Map<String, ImageModel> through the controller.
Implementing this interface is the ImageModelMapImpl class, and one object represents a map of strings to image
models. One instance of this class contains a field with a Map<String, ImageModel> that the methods can both add to
and find entries from.

The design of our Image Processor continues with the controller, which begins with the ImageController interface, used
to represent the controller aspect of our image processor. The ImageControllerImpl is an implementation of
ImageController and is designed to run a suitable controller for our image processor. This class contains as fields an
Appendable object to display feedback and a Map<String, Function<Scanner, ImageCommand>> that stores each command that
is offered under the string to run it. When initialized, a chosen output Appendable to write to and ImageModelMap for
the command function objects to find image models from and add image models to are inputted as parameters. A lack of a
unique output parameter will result in the controller using System.out as the default output. A user is able to input
"q" of "Q" to quit the session at any time or can enter any amount of commands using the appropriate string and
surrounding inputs, and after a command is run, the new image model and its name will be added into the ImageModelMap
which already contains the existing models. In our program, a user is not limited to only typing commands into the
console, but can also provide a text file as a command line argument with appropriate commands. After the file has
been read from, the console will let the user input additional commands, at which point they may either do so or quit
the program.

The design of our Image Processor takes advantage of the command pattern by utilizing an ImageCommand interface,
which represents performing an operation of an image. The AbstractImageCommand class, which implements ImageCommand,
provides a template for numerous commands that are similar. Extending the AbstractImageCommand class are the
AbstractTransformationCommand, which is extended by function objects that deal with color transformations,
AbstractFlipCommand, which is extended by function objects that deal with flipping images, and AbstractFilterCommand,
which is extended by function objects that deal with filtering images. An Appendable object, a Scanner object that
reads the name of the existing model and the name that the new model will be saved to, and an ImageModelMap
are passed into a chosen command, at which point the model being modified is located in the map, operated on by the
specific command, and finally the given ImageModelMap that contains existing key value pairs is added to so that it
also contains the new model and its name. An identical process to this is performed by the function objects. The color
transformation function objects, represented by RedGreyscale, GreenGreyscale, BlueGreyscale, MaxValueGreyscale,
LumaGreyscale, IntensityGreyscale, and SepiaTone, use AbstractTransformationCommand's protected transform method to
create the new images. Similarly, HorizontalFlip and VerticalFlip use AbstractFlipCommand's protected flip method,
while BlurFilter and SharpenFilter use AbstractFilterCommand's protected filter method. The remaining three commands
follow a similar format; however, Brighten takes in an additional constant from the scanner, SaveImage creates a new
PPM file instead of altering an ImageModelMap, and LoadImage does not take in an existing image model but instead
reads from an existing file. If a command is attempted to run on an image model that has not yet been loaded in, or if
any other erroneous inputs are entered, an output stream message will convey this to the user.

The design of our ImageProcessor is completed by the view, which utilizes Java's Swing toolkit to generate a 
GUI for our interpretation of an image processor.