import java.util.*;

public class numberSortPuzzle {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		int[][] board = new int[4][4];
		generateBoard(board);
		
		System.out.println("Creating board...");
		
		printBoard(board);
		
		System.out.println("Please move the tile you want to move by typing the number associated with the tile: ");
		
		boolean solved = false;
		while(!solved)
		{
			int choice;
			
			/* this will make sure that the value given is an integer value
			 * if not, it will keep running until the user has inputted
			 * a valid integer
			 */
			try
			{
				choice = input.nextInt();
				if((choice > 15) || (choice < 1))
				{
					System.out.println("That value is not on the board. Please try again.");
				}
				else
				{
					/* checks if there is an empty space next to the tile 
					 * and will move the tile there depending on if space
					 * to move tile is there or not
					 */
					board = checkSpace(board, choice);
					
					//checking if the board is in correct order
					solved = sortedBoard(board);
				}
			}
			catch(Exception e)
			{
				System.out.println("Input given is not a valid integer. Please try again.");
				input.next();
			}
			
			
		}
		System.out.println("Board has been solved!");

	}
	
	//generating a number board
	public static void generateBoard(int[][] board)
	{
		for(int row = 0; row < board.length; row++)
		{
			for(int col = 0; col < board[row].length; col++)
			{
				//make the bottom right corner of the box empty
				if((row == board.length-1) && (col == board[row].length - 1))
						board[row][col] = 0;
				else
				{
					board[row][col] = (int)(Math.random() * 15) + 1;
					//check if the number is unique after the first element
					boolean checkDuplicate = checkDuplicate(board, board[row][col], row, col);
					//if number is NOT unique, then it will keep generating a number until it is unique
					if(checkDuplicate == true)
					{
						col--;
					}
						
				}
			}
		}
		//check if board is solvable
		if(!isSolvable(board))
		{
			System.out.println("Board not solvable, creating new board");
			generateBoard(board);
		}
		
	}
	
	/*
	 * checks if the board is solvable through inversion
	 */
	public static boolean isSolvable(int[][]board)
	{
		//we want a 1D array of all the values to check if board is solvable
		//(might not be most efficient, but it is simple to do)
		int[] boardItems = to1DArray(board);

		//now, check for inversion
		int inversionIndex = 0;
		for(int i = 0; i < boardItems.length - 1; i++)
		{
			for(int j = i + 1; j < boardItems.length; j++)
			{
				if(boardItems[i] > boardItems[j])
					inversionIndex++;
			}
		}
		return (inversionIndex % 2 == 0);
	}
	
	/*
	 * checks if the value given is unique
	 * returns false if value is unique and true if it is a duplicate
	 */
	public static boolean checkDuplicate(int[][] board, int value, int row1, int col1)
	{
		//checks if the element being checked is the first element. will return false if it is
		if((row1 == 0) && (col1 == 0))
		{
			return false;
		}
		else
		{
			//row of value is needed so that the loop will stop checking until that value 
			for(int row = 0; row <= row1; row++)
			{
				for(int col = 0; col < board[row].length; col++)
				{
					/*
					 * if we have reached the element that we needed to check, 
					 * this will stop the loop and return false
					 */
					if((col == col1) && (row == row1))
						break;
					
					if(board[row][col] == value)
					{
						return true; //this stops the loop and returns 
									 //true since there is a duplicate
					}
				}
			}
			return false;
		}
	}
	
	//this function prints the board
	public static void printBoard(int[][] board)
	{
		for(int row = 0; row < board.length; row++)
		{
			for(int col = 0; col < board[row].length; col++)
			{
				if(board[row][col] == 0)
					System.out.print("[  ] ");
				else
					System.out.print("[ " + board[row][col] + " ] ");
			}
			System.out.println();
		}
	}
	
	/*
	 * this function checks if there is an empty space
	 * adjacent to the tile user wants to move in order 
	 * to properly move it
	 */
	public static int[][] checkSpace(int[][] board, int value)
	{
		//find the row and column of the element and stores these 
		//indices in colInd and rowInd
		int colInd = 0;
		int rowInd = 0;
		for(int row = 0; row < board.length; row++)
		{
			for(int col = 0; col < board[row].length; col++)
			{
				if(board[row][col] == value)
				{
					colInd = col;
					rowInd = row;
				}
			}
		}
		
		/*once found, function will check if space is there,
		 * and will switch places of both tiles once found
		 */
		int temp = value;
		
		if((rowInd-1 >= 0) && (board[rowInd-1][colInd] == 0))
		{
			board[rowInd-1][colInd] = temp;
			board[rowInd][colInd] = 0;
			System.out.println("Successfully moved tile!");
			
		}
		else if((rowInd+1 < board.length) && (board[rowInd+1][colInd] == 0))
		{
			board[rowInd+1][colInd] = temp;
			board[rowInd][colInd] = 0;
			System.out.println("Successfully moved tile!");
			
		}
		else if((colInd-1 >= 0) && (board[rowInd][colInd-1] == 0))
		{
			board[rowInd][colInd-1] = temp;
			board[rowInd][colInd] = 0;
			System.out.println("Successfully moved tile!");
			
		}
		else if((colInd+1 < board[rowInd].length) && (board[rowInd][colInd+1] == 0))
		{
			board[rowInd][colInd+1] = temp;
			board[rowInd][colInd] = 0;
			System.out.println("Successfully moved tile!");
			
		}
		else
		{
			System.out.println("There is no empty space next to that tile. Please try again :(");
		}

		printBoard(board);
		return board;
		
	}
	
	public static boolean sortedBoard(int[][] board)
	{
		//create a 1D array of the elements in board
		int[] boardElements = to1DArray(board);
		for(int i = 0; i < boardElements.length - 1; i++)
		{
			for(int j = i+1; j < boardElements.length; j++)
			{
				if(boardElements[i] > boardElements[j])
					return false;
			}
		}
		return true;
	}

	public static int[] to1DArray(int[][]board)
	{
		int[] boardItems = new int[15]; //there are 16 tiles in board, but one is empty
		int index = 0;
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++)
			{
				if(index < 15)
				{
					boardItems[index] = board[i][j];
					index++;
				}
			}
		}
		return boardItems;
	}

}