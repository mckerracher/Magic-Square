/**
 * MagicSquareTester is the main driver class that uses the MagicSquare class to
 * both create and check 2D arrays. Its proper usage requires that the user
 * include either a -check or -create flag and a filename either to be created
 * or checked. When the user employs the -create flag, they are also required to
 * include an integer representing the size of the 2D array they want to create.
 * 
 * @author Joshua McKerracher
 *
 */
public class MagicSquareTester {

	public static void main(String[] args) {

		// Checks that the command line input includes either -create or -check
		// Checks that there's at least two commands being used.
		if (((args[0].compareToIgnoreCase("-create") == 0) || (args[0].compareToIgnoreCase("-check") == 0))
				&& (args.length < 4 && (args.length > 1))) {

			// Gets the file name and stores as the variable fileName.
			String fileName = new String();
			fileName = args[1];

			// Initializes variables to be used below.
			int arraySize = 0;
			String arraySizeString = new String();

			// Ensures a null pointer won't be thrown.
			// Gets the array size as a string and converts to an int.
			if ((args.length == 3)) {
				arraySizeString = args[2];
				arraySize = Integer.parseInt(arraySizeString);
			}

			// Executes the createMagicSquare if the user uses the -create flag.
			if (args[0].compareToIgnoreCase("-create") == 0) {
				MagicSquare.createMagicSquare(arraySize, fileName);
			}

			// Executes the checkMatrix method if the user uses the -check flag.
			if ((args[0].compareToIgnoreCase("-check") == 0)) {
				// Ensures the user isn't including a size.
				if (args.length < 3) {
					MagicSquare.checkMatrix(fileName);
				} else {
					System.out.println("When using the -check command, do not include [ |size]");
				}
			}

		} else {
			// Proper usage statement.
			System.err.println(
					"To operate correctly, MagicSquareTester should run using the following command-line statement: ");
			System.err.println("java MagicSquareTester [-check | -create] [filename] [ |size]");
			System.err.println("The size must be odd and greater than 2.");
			System.exit(1);
		}
	}
}