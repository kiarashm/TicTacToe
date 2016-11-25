import processing.core.PApplet;

public class Board extends PApplet {

	int height = 600;
	int width = 600;
	Piece[][] pieces = new Piece[3][3];
	boolean xTurn = true;
	boolean gameOver = false;
	int moves = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Board");
		
	}
	public void settings(){
    	size(height,width);
    }
    
	public void setup() {
		background(0);
		drawBoard();
	}
	
	public void drawBoard(){
		stroke(255);
		line(0, height/3, width, height/3);
		line(0, 2 * (height/3), width, 2 * (height/3));
		line(width/3, 0, width/3, height);
		line(2 * (width/3), 0, 2 * (width/3), height);
	}

	public int[] whichBox(int mouseX, int mouseY){
		int[] sol = new int[2];
		sol[0] = (int) Math.floor(mouseX/ (width/3));
		sol[1] = (int) Math.floor(mouseY/ (height/3)); 
		return sol;
	}
	
	public void move(){
		
		int[] whichB = whichBox(mouseX, mouseY);
		int x = whichB[0];
		int y = whichB[1];
		if (mousePressed){
			Piece newPiece = new Piece(xTurn);
			if (pieces[x][y] == null){
				pieces[x][y] = newPiece;
				xTurn = !xTurn;
				moves++;
			}
		}
		
	}
	
	public void drawPieces(){
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				Piece p = pieces[i][j];
				if (p != null){
					drawXO(p.isX(), i, j);
				}
			}
		}
	}
	
	public void drawXO(boolean isX, int i, int j){
		int xFactor = i * 200;
		int yFactor = j * 200;
		if (isX){
			line(xFactor, yFactor, xFactor + 200, yFactor + 200);
			line(xFactor, yFactor + 200, xFactor + 200, yFactor);
		}
		else{
			fill(0);
			ellipse(xFactor + 100, yFactor + 100, 190, 190);
		}
	}
	
	//First val is whether someone won, second value is whether X won or O, (X = true)
	public boolean[] checkWinVert(){
		boolean[] sol = new boolean[2];
		for (int i = 0; i < 3; i++){
			Piece fp = pieces[i][0];
			if (fp != null){
				boolean firstVal = fp.isX();
				int count = 0;
				for (int j = 0; j < 3; j++){
					Piece p = pieces[i][j];
					if (p != null){
						if (p.isX() != firstVal){
							break;
						}
						else{
							count++;
						}
					}
				}
				if (count == 3){
					sol[0] = true;
					sol[1] = firstVal;
					return sol;
				}
			}
			
		}
		return sol;
	}
	
	
	
	public boolean[] checkWinHoriz(){
		boolean[] sol = new boolean[2];
		for (int i = 0; i < 3; i++){
			Piece fp = pieces[0][i];
			if (fp != null){
				boolean firstVal = fp.isX();
				int count = 0;
				for (int j = 0; j < 3; j++){
					Piece p = pieces[j][i];
					if (p != null){
						if (p.isX() != firstVal){
							break;
						}
						else{
							count++;
						}
					}
				}
				if (count == 3){
					sol[0] = true;
					sol[1] = firstVal;
					return sol;
				}
			}
		}
		return sol;
	}
	
	public boolean[] checkWinDiag(){
		boolean[] sol = new boolean[2];
		Piece lp = pieces[0][0];
		if (lp != null){
			boolean firstVal = lp.isX();
			int count = 0;
			for (int i = 0; i < 3; i++){
				Piece p = pieces[i][i];
				if (p != null){
					if (p.isX() != firstVal){
						break;
					}
					else{
						count++;
					}
				}
			}
			if (count == 3){
				sol[0] = true;
				sol[1] = firstVal;
				return sol;
			}
		}
		Piece rp = pieces[2][0];
		if (rp != null){
			boolean firstVal = rp.isX();
			int count = 0;
			for (int i = 2; i >= 0; i--){
				Piece p = pieces[i][2 - i];
				if (p != null){
					if (p.isX() != firstVal){
						break;
					}
					else{
						count++;
					}
				}
			}
			if (count == 3){
				sol[0] = true;
				sol[1] = firstVal;
				return sol;
			}
		}

		return sol;
	}
	
	public void xWon(){
		textSize(50);
		fill(246, 50, 36);
		text("X WON!!!!", 200, 300); 
		
	}
	
	public void oWon(){
		textSize(50);
		fill(246, 50, 36);
		text("O WON!!!!", 200, 300); 
		
	}
	
	
	public void displayWinner(boolean[] vert){
		if (vert[0]){
			if (vert[1]){
				xWon();
				gameOver = true;
			}
			else{
				oWon();
				gameOver = true;
			}
		}
	}
	
	public void checkWin(){
		boolean[] vert = checkWinVert();
		displayWinner(vert);
		boolean[] horiz = checkWinHoriz();
		displayWinner(horiz);
		boolean[] diag = checkWinDiag();
		displayWinner(diag);
		
	}
	
	
	public void draw() {
		if(gameOver == false){
			move();
			drawPieces();
			checkWin();
		}
		if (moves == 9 && gameOver == false){
			textSize(50);
			fill(246, 50, 36);
			text("IT'S A TIE", 200, 300); 
		}
		
	}

}
