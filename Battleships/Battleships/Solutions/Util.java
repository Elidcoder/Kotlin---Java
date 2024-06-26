package Solutions;

import java.util.Arrays;

public class Util {

  private static int letterToIndex(char letter) {
    assert (Character.isUpperCase(letter));
    return (int) letter - 'A';
  }

  private static int numberToIndex(char number) {
    assert (Character.isDigit(number));
    return (int) number - '0';
  }

  public static Coordinate parseCoordinate(String s) {
    assert (s.length() == 2);
    char letter = s.charAt(0);
    char number = s.charAt(1);
    assert (Character.isUpperCase(letter) && Character.isDigit(number));
    return new Coordinate(letterToIndex(letter), numberToIndex(number));
  }

  public static Piece hideShip(Piece piece) {
    return piece == Piece.SHIP ? Piece.WATER : piece;
  }

  public static void hideShips(Piece[][] grid) {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        grid[i][j] = hideShip(grid[i][j]);
      }
    }
  }

  public static Piece[][] deepClone(Piece[][] input) {
    Piece[][] output = new Piece[input.length][];
    for (int i = 0; i < input.length; i++) {
      output[i] = Arrays.copyOf(input[i], input[i].length);
    }
    return output;
  }

}