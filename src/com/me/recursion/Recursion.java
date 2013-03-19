package com.me.recursion;





import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;


public class Recursion implements ApplicationListener {
	
	
	public int worldHeight = 10;
	public int worldWidth = 10;
	public Block[][] block;
	private int spacing = 3;
	private int blockSize = 52;
	public Color[] colors = { Color.BLUE, Color.RED, Color.CYAN,
			Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA,
			Color.GRAY, Color.WHITE };
	public ShapeRenderer test;
	boolean reset = true;
	InputHandler input;
	int colorTemp;
	Block blockTemp;
	Array<Block> toDelete;
	Label label2;
	int score;
	BitmapFont white;
	Stage stage;
	SpriteBatch batch;
	private boolean first = true;

	@Override
	public void create() {	
		test = new ShapeRenderer();
		//blockSize = Gdx.graphics.getHeight()/worldHeight-(spacing * worldHeight);
		
		input = new InputHandler(this);
		Gdx.input.setInputProcessor(input);
		toDelete = new Array<Block>();
		batch= new SpriteBatch();
		define();
		
		
		
		/*
		int[][] butt =  new int[10][10];
		int i;
		int j;
		for (i = 0; i < butt.length; i++) {
			for (j = 0; j < butt[0].length; j++)
				butt[i][j] = i + j;
		}
		for (i = 0; i < butt.length; i++){
			for (j = 0; j < butt[0].length; j++)
				if (j == butt[0].length-1) {
					System.out.println(butt[i][j] + " ");
				} else { 
					System.out.print(butt[i][j] + " ");
				}
		}
		
		
		buttSwap(0, 0, 1, 1, butt);
		
		for (i = 0; i < butt.length; i++)
			for (j = 0; j < butt[0].length; j++)
				if (j == butt[0].length-1) {
					System.out.println(butt[i][j] + " ");
				} else { 
					System.out.print(butt[i][j] + " ");
				}
		*/
	}

	@Override
	public void dispose() {
	}
	
	public void deleteEvent() {
		horizontalSearch();
		verticalSearch();
		for (int z = 0; z < toDelete.size; z++) {
			block[toDelete.get(z).yc][toDelete.get(z).xc] = null;
		}
		cleanUp();
	}
	
	public void cleanUp () {
		moveBlocks();
		score += toDelete.size;
		label2.setText(" score: " + String.valueOf(score));
		if (toDelete.size > 0) {
			toDelete.clear();
			deleteEvent();
		}
		toDelete.clear();
	}

	@Override
	public void render() {	
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		
		
		batch.begin();
		stage.draw();
		batch.end();
		
		if (first = true) {
			deleteEvent();
			first = false;
		}
		
		for(int y = 0; y < block.length; y++){
			for (int x = 0; x < block[0].length; x++){
				if (block[y][x]!= null) {
					Color temp = colors[block[y][x].color];
					test.setColor(temp);
					test.begin(ShapeType.FilledRectangle);
					test.filledRect( block[y][x].x,  block[y][x].y,  block[y][x].width,  block[y][x].height);
					//System.out.println(block[y][x].y);
					test.end();
					//System.out.println("rendering");
				}
			}
		}
		
		if (Gdx.input.isKeyPressed(Keys.W)) {
			if (reset == true) {
			reset = false;
			buttSwap(0, 0, 0, 1, block);
			}
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			moveBlocks();
			score += toDelete.size;
			label2.setText(" score: " + String.valueOf(score));
			toDelete.clear();
			reset = true;
			
		}
		if (Gdx.input.isKeyPressed(Keys.A) ) {
			for (int z = 0; z < toDelete.size; z++) {
				block[toDelete.get(z).yc][toDelete.get(z).xc] = toDelete.get(z);
			}
		}
		
		
		
		
		if (Gdx.input.isKeyPressed(Keys.D) ) {
			horizontalSearch();
			verticalSearch();
			for (int z = 0; z < toDelete.size; z++) {
				block[toDelete.get(z).yc][toDelete.get(z).xc] = null;
			}
			//System.out.println("color = " + toDelete.get(0).color);
			//System.out.println(toDelete.get(0).xc);
			//System.out.println(toDelete.get(0).yc);
		}
		
		if (Gdx.input.isKeyPressed(Keys.F) ) {
			block[0][0] = null;
			//System.out.println(block[1][0].y);
		}
		
		if (Gdx.input.isTouched() && reset == true) {
			for(int y = 0; y < block.length; y++){
				for (int x = 0; x < block[0].length; x++) {
					if (notNull(y, x) && block[y][x].contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
						reset = false;
						blockTemp = block[y][x];
						colorTemp = block[y][x].getColor();
						System.out.println("The clicked color is " + colorTemp);
					}
				}
			}
		}
		
	}
	
	public boolean isNeighborValid(int x, int y) {
		if (x-1 > -1 && y-1 > -1 && x+1 <= worldWidth-1 && y+1 <= worldHeight-1)
			return true;
		else
		return false;
	}
	
	//world height and width must be the same
	public boolean isValid(int x) {
		if (x > -1 && x <= worldWidth-1)
			return true;
		else
		return false;
	}

	@Override
	public void resize(int width, int height) {
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();
		
		white = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"), false);
		LabelStyle ls = new LabelStyle(white, Color.WHITE);
		label2 = new Label(" score: " + String.valueOf(score), ls);
		label2.x = 0;
		label2.y = (Gdx.graphics.getHeight()-50);
		label2.width = (Gdx.graphics.getWidth());
		label2.setAlignment(Align.LEFT);
		
		stage.addActor(label2);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	public void buttSwap(int x, int y, int x2, int y2, Block[][] butt) {
		Block tempVal = butt[x][y];
		butt[x][y] = butt[x2][y2];
		butt[x2][y2] = tempVal;
		System.out.println("rendering");
	}
	
	public void horizontalSearch() {
		boolean noAdd = false;
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length-2; x++) {
				if (isValid(x + 1) && notNull(y, x) && notNull(y, x+1) && block[y][x].color == block[y][x+1].color) {
					if(isValid(x + 2) && notNull(y, x+2) && block[y][x].color == block[y][x+2].color) {
						for (int z = 0; z < toDelete.size; z++) {
							if(block[y][x].equals(toDelete.get(z)))
								noAdd = true;
						}
						if (noAdd == false) toDelete.add(block[y][x]);
						noAdd = false;
						for (int z = 0; z < toDelete.size; z++) {
							if(block[y][x+1].equals(toDelete.get(z)))
								noAdd = true;
						}
						if (noAdd == false) toDelete.add(block[y][x+1]);
						noAdd = false;
						for (int z = 0; z < toDelete.size; z++) {
							if(block[y][x+2].equals(toDelete.get(z)))
								noAdd = true;
						}
						if (noAdd == false) toDelete.add(block[y][x+2]);
					}
				}
			}
		}
	}
	
	public void verticalSearch() {
		boolean noAdd = false;
		for (int y = 0; y < block.length-2; y++) {
			for (int x = 0; x < block[0].length; x++) {
				if (isValid(y + 1) && notNull(y, x) && notNull(y+1, x) && block[y][x].color == block[y+1][x].color) {
					if(isValid(y + 2) && notNull(y+2, x) && block[y][x].color == block[y+2][x].color) {
						for (int z = 0; z < toDelete.size; z++) {
							if(block[y][x].equals(toDelete.get(z)))
								noAdd = true;
						}
						if (noAdd == false) toDelete.add(block[y][x]);
						noAdd = false;
						for (int z = 0; z < toDelete.size; z++) {
							if(block[y+1][x].equals(toDelete.get(z)))
								noAdd = true;
						}
						if (noAdd == false) toDelete.add(block[y+1][x]);
						noAdd = false;
						for (int z = 0; z < toDelete.size; z++) {
							if(block[y+2][x].equals(toDelete.get(z)))
								noAdd = true;
						}
						if (noAdd == false) toDelete.add(block[y+2][x]);
					}
				}
			}
		}
	}
	
	
	
	public void moveBlocks() {
		//System.out.print("movin");
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				if(block[y][x] == null) {
					//System.out.print("movin2");
					shiftDown(y, x);
				}
			}
		}
	}
	
	private void shiftDown(int y,int x) {
		if(isValid(y+1) && notNull(y+1, x)) {
			pushBlock(y, x);
		} else if (isValid(y+1)) {	
			System.out.println("recursive");
			shiftDown((y+1), x);
		} else {
			System.out.println("block created");
			block[y][x] = new Block((Gdx.graphics.getWidth() / 2)
					- ((worldWidth * blockSize) / 2) + (x * blockSize) + (x * spacing), (Gdx.graphics.getHeight() / 2)
					- ((worldHeight * blockSize) / 2) + (y * blockSize) + (y * spacing), blockSize, blockSize, x, y);
		}
	}

	private void pushBlock(int y,int x) {
		if(!notNull(y, x) && notNull(y+1,x)) {
			System.out.println("block moved");
			
			block[y][x] = block[y+1][x];
			//System.out.println(block[y][x].y);
			
			block[y][x].setY(block[y][x].y - (blockSize) + (spacing));
			block[y][x].y -= (blockSize) + (spacing);
			block[y][x].yc -= 1;
			//System.out.println(block[y][x].y);
			block[y+1][x] = null;
		}
			
	}

	public void define() {
		block = new Block[worldHeight][worldWidth];

		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				block[y][x] = new Block((Gdx.graphics.getWidth() / 2)
						- ((worldWidth * blockSize) / 2) + (x * blockSize)
						+ (x * spacing), (Gdx.graphics.getHeight() / 2)
						- ((worldHeight * blockSize) / 2) + (y * blockSize)
						+ (y * spacing), blockSize, blockSize, x, y);
				// System.out.println("block created");
			}
		}

		// System.out.println(gameScreen.player.getY());
	}
	
	public boolean notNull(int y, int x) {
		//System.out.println("not null");
		if (isValid(y) && isValid(x) && block[y][x] != null)
		return true;
		return false;
	}
}
