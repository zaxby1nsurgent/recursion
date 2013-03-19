package com.me.recursion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor{
	
	Recursion game;
	
	public InputHandler(Recursion game) {
		this.game = game;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (game.reset == false) {
			game.reset = true;
			System.out.println("step0");
			for(int i = 0; i < game.block.length; i++){
				for (int j = 0; j < game.block[0].length; j++){
					if (game.notNull(j, i) && game.block[j][i].contains(x, Gdx.graphics.getHeight() - y)) {
						System.out.println("step1");
						for(int y2 = 0; y2 < game.block.length; y2++){
							for (int x2 = 0; x2 < game.block[0].length; x2++) {
								if (game.notNull(y2, x2) && game.block[y2][x2].equals(game.blockTemp) && !game.block[y2][x2].contains(x, Gdx.graphics.getHeight() - y)) {
									System.out.println("step2");
								
										if (game.isValid(x2+1) && game.block[y2][x2+1] == game.block[j][i]) {
											if (checkR(y2, x2)) {
												swap(j, i, y2, x2);
											}
											
										}
										if (game.isValid(x2-1) && game.block[y2][x2-1] == game.block[j][i]) {
											if (checkL(y2, x2)) {
												swap(j, i, y2, x2);
											}
										}
										if (game.isValid(y2+1) && game.block[y2+1][x2] == game.block[j][i]) {
											if (checkU(y2, x2)) {
												swap(j, i, y2, x2);
											}
										}
										if (game.isValid(y2-1) && game.block[y2-1][x2] == game.block[j][i]) {
											System.out.println("check down");
											if (checkD(y2, x2)) {
												swap(j, i, y2, x2);
											}
										}
											
									
								}
							}
						}
						
					}
				}
			}
		}
		
		
		return false;
	}

	//second step check methods

	private boolean checkLeft(int y2, int x2, int y) { //y is the original block y coordinate; y2 is the shifted y coordinate
		if (game.isValid(x2 - 1) && game.notNull(y, x2) && game.notNull(y2, x2 -1) && game.block[y][x2].color == game.block[y2][x2-1].color) {
			if(game.isValid(x2 - 2) && game.notNull(y2, x2 -2) && game.block[y][x2].color == game.block[y2][x2-2].color) {
				System.out.println("Left Valid");
				return true;
			}
		}
		return false;
	}

	private boolean checkRight(int y2, int x2, int y) {
		if (game.isValid(x2 + 1) && game.notNull(y, x2) && game.notNull(y2, x2 +1) && game.block[y][x2].color == game.block[y2][x2+1].color) {
			if(game.isValid(x2 + 2) && game.notNull(y2, x2 +2) && game.block[y][x2].color == game.block[y2][x2+2].color) {
				System.out.println("Right Valid");
				return true;
			}
		}
		return false;
	}

	private boolean checkMiddle(int y2, int x2, int y) {
		if (game.isValid(x2 - 1) && game.notNull(y, x2) && game.notNull(y2, x2 -1) &&  game.block[y][x2].color == game.block[y2][x2-1].color) {
			if(game.isValid(x2 + 1) && game.notNull(y2, x2 +1) && game.block[y][x2].color == game.block[y2][x2+1].color) {
				System.out.println("Middle Valid");
				return true;
			}
		}
		return false;
	}
	
	//these methods are for horizontal shifts and vertical checks
	
	private boolean checkDown(int y2, int x2, int x) {
		if (game.isValid(y2 - 1) && game.notNull(y2, x) && game.notNull(y2 -1, x2) &&  game.block[y2][x].color == game.block[y2-1][x2].color) {
			if(game.isValid(y2 - 2) && game.notNull(y2-2, x2) && game.block[y2][x].color == game.block[y2-2][x2].color) {
				System.out.println("Down Valid");
				return true;
			}
		}
		return false;
	}
	
	
	
	private boolean checkCenter(int y2, int x2, int x) {
		if (game.isValid(y2 - 1) && game.notNull(y2, x) && game.notNull(y2 -1, x2) && game.block[y2][x].color == game.block[y2-1][x2].color) {
			if(game.isValid(y2 + 1) && game.notNull(y2+1, x2) && game.block[y2][x].color == game.block[y2+1][x2].color) {
				System.out.println("Center Valid");
				return true;
			}
		}
		return false;
	}
	
	private boolean checkUp(int y2, int x2, int x) {
		System.out.println("y2: " + y2);
		System.out.println("x2: " + x2);
		System.out.println( "y2-1: " + (x));
		if (game.isValid(y2 + 1) && game.notNull(y2, x) && game.notNull(y2+1, x2) && game.block[y2][x].color == game.block[y2+1][x2].color) {
			if(game.isValid(y2 + 2) && game.notNull(y2+2, x2) && game.block[y2][x].color == game.block[y2+2][x2].color) {
				System.out.println("Up Valid");
				return true;
			}
		}
		return false;
	}
	
	private boolean checkUp2(int y2, int x2, int x) {
		System.out.println("y2: " + y2);
		System.out.println("x2: " + x2);
		System.out.println( "y2-1: " + (x));
		if (game.isValid(y2 + 1) && game.notNull(x, x2) && game.notNull(y2+1, x2) && game.block[x][x2].color == game.block[y2+1][x2].color) {
			if(game.isValid(y2 + 2) && game.notNull(y2+2, x2) && game.block[x][x2].color == game.block[y2+2][x2].color) {
				System.out.println("Up Valid");
				return true;
			}
		}
		return false;
	}
	
	private boolean checkLeft2(int y2, int x2, int y) { 
		System.out.println("y2: " + y2);
		System.out.println("x2: " + x2);
		System.out.println( "y2-1: " + (y));
		if (game.isValid(x2 - 1) && game.notNull(y2, y) && game.notNull(y2, x2 -1) && game.block[y2][y].color == game.block[y2][x2-1].color) {
			if(game.isValid(x2 - 2) && game.notNull(y2, x2 -2) && game.block[y2][y].color == game.block[y2][x2-2].color) {
				System.out.println("Left Valid");
				return true;
			}
		}
		return false;
	}
	
	private boolean checkRight2(int y2, int x2, int y) { 
		System.out.println("y2: " + y2);
		System.out.println("x2: " + x2);
		System.out.println( "y2-1: " + (y));
		if (game.isValid(x2 + 1) && game.notNull(y2, y) && game.notNull(y2, x2+1) &&game.block[y2][y].color == game.block[y2][x2+1].color) {
			if(game.isValid(x2 + 2) && game.notNull(y2, x2 +2) && game.block[y2][y].color == game.block[y2][x2+2].color) {
				System.out.println("Right Valid");
				return true;
			}
		}
		return false;
	}
	
	private boolean checkDown2(int y2, int x2, int x) {
		System.out.println("y2: " + y2);
		System.out.println("x2: " + x2);
		System.out.println( "y2-1: " + (x));
		if (game.isValid(y2 - 1) && game.notNull(x, x2) && game.notNull(y2-1, x2) &&game.block[x][x2].color == game.block[y2-1][x2].color) {
			if(game.isValid(y2 - 2) && game.notNull(y2-2, x2) && game.block[x][x2].color == game.block[y2-2][x2].color) {
				System.out.println("Down Valid");
				return true;
			}
		}
		return false;
	}
	
	//first step check methods call second step check methods above

	private boolean checkU(int y2, int x2) {
		System.out.println("y2: " + y2);
		System.out.println("x2: " + x2);
		System.out.println( "y2-1: " + (y2-1));
		if(checkMiddle(y2, x2, y2-1)){
			return true;
		}
		if (checkRight(y2, x2, y2-1)) {
			return true;
		}
		if (checkLeft(y2, x2, y2-1)) {
			return true;
		}
		if (checkUp2(y2, x2, y2-1)) {
			return true;
		}
		return false;
	}

	private boolean checkL(int y2, int x2) {
		if(checkCenter(y2, x2, x2+1)){
			return true;
		}
		if (checkUp(y2, x2, x2+1)) {
			return true;
		}
		if (checkDown(y2, x2, x2+1)) {
			return true;
		}
		if (checkLeft2(y2, x2, x2+1)) {
			return true;
		}
		return false;
	}

	private boolean checkR(int y2, int x2) {
		if(checkCenter(y2, x2, x2-1)){
			return true;
		}
		if (checkUp(y2, x2, x2-1)) {
			return true;
		}
		if (checkDown(y2, x2, x2-1)) {
			return true;
		}
		if (checkRight2(y2, x2, x2-1)) {
			return true;
		}
		return false;
	}
	
	private boolean checkD(int y2, int x2) {
		if(checkMiddle(y2, x2, y2+1)){
			return true;
		}
		if (checkRight(y2, x2, y2+1)) {
			return true;
		}
		if (checkLeft(y2, x2, y2+1)) {
			return true;
		}
		if (checkDown2(y2, x2, y2+1)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		
			
			for(int i = 0; i < game.block.length; i++){
				for (int j = 0; j < game.block[0].length; j++){
					if (game.notNull(j, i) && game.block[j][i].contains(x, Gdx.graphics.getHeight() - y)) {
						//System.out.println("step1");
						for(int y2 = 0; y2 < game.block.length; y2++){
							for (int x2 = 0; x2 < game.block[0].length; x2++) {
								if (game.notNull(y2, x2) && game.block[y2][x2].equals(game.blockTemp) && !game.block[y2][x2].contains(x, Gdx.graphics.getHeight() - y)) {
									//System.out.println("step2");
									if (game.reset == false) {
										game.reset = true;
										if (game.isValid(x2+1) && game.block[y2][x2+1] == game.block[j][i] && checkR(y2, x2+1)) {
											System.out.println("checkR " + (y2) + " " + (x2+1));
											
											swap(j, i, y2, x2);
											game.deleteEvent();
										}
										if (game.isValid(x2-1) && game.block[y2][x2-1] == game.block[j][i] && checkL(y2, x2-1)) {
											System.out.println("checkL " + (y2) + " " + (x2-1));
											
											swap(j, i, y2, x2);
											game.deleteEvent();
										}
										if (game.isValid(y2+1) && game.block[y2+1][x2] == game.block[j][i] && checkU(y2+1, x2)) {
											System.out.println("checkU " + (y2+1) + " " + x2);
											
											
											swap(j, i, y2, x2);
											game.deleteEvent();
										}
										if (game.isValid(y2-1) && game.block[y2-1][x2] == game.block[j][i] && checkD(y2-1, x2)) {
											System.out.println("checkD " + (y2-1) + " " + x2);
											
											
											swap(j, i, y2, x2);
											game.deleteEvent();
										}
											
									
								}
							}
						}
					}
				}	
			}
		}
		
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void swap(int j, int i, int y2, int x2) {
		int temp = game.block[j][i].color;
		game.block[j][i].color = game.colorTemp;
		game.block[y2][x2].color = temp;
		game.blockTemp = null;
	}

}
